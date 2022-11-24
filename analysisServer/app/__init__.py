import base64
import os

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

# from keras.preprocessing.image import img_to_array
from tensorflow.keras.utils import img_to_array  # keras 버전 2.90부터는 이렇게 import
import imutils
import cv2
from keras.models import load_model
import numpy as np
import time
import json

from flask import Flask, render_template, request, jsonify
from app import mod_dbconn, NoFaceDetectedException, CacheSavingException, ImageLoadingException
import app.EmotionClass as Emotion

import redis
from PIL import Image
import base64
import io
from io import BytesIO


app = Flask(__name__)
r = redis.Redis(host="i7a608.p.ssafy.io", password="pomo1229", port=6379, db=0)

# parameters for loading data and images
detection_model_path = 'haarcascade_files/haarcascade_frontalface_default.xml'
emotion_model_path = 'models/_mini_XCEPTION.102-0.66.hdf5'

# hyper-parameters for bounding boxes shape
# loading models
face_detection = cv2.CascadeClassifier(detection_model_path)
emotion_classifier = load_model(emotion_model_path, compile=False)
EMOTIONS = ["angry", "disgust", "scared", "happy", "sad", "surprised", "neutral"]


# @app.route('/')
# def home():
#     return render_template('home.html')

@app.errorhandler(NoFaceDetectedException.NoFaceDetectedException)
def error_handling_NoFaceDetectedException(error):
    json_data = {
        'state': "FAIL",
        'ERROR': str(error),
        'frame_id': error.frame_id
    }
    return jsonify(json_data)


@app.errorhandler(CacheSavingException.CacheSavingException)
def error_handling_CacheSavingException(error):
    json_data = {
        'state': "FAIL",
        'ERROR': str(error)
    }
    return jsonify(json_data)


@app.errorhandler(ImageLoadingException.ImageLoadingException)
def error_handling_ImageLoadingException(error):
    json_data = {
        'state': "FAIL",
        'ERROR': str(error)
    }
    return jsonify(json_data)


@app.route('/pomo/v1/emotion', methods=['POST'])
def save_concentration():
    param = request.get_json()
    frame = -1
    # frame = cv2.imread('app/image/img3.jpg', cv2.IMREAD_COLOR)

    try:
        get_image_sql = "SELECT image FROM pomoImage WHERE frame_id=%s"
        args = param['frame_id']
        db_class = mod_dbconn.Database()
        blob_data = db_class.executeOne(get_image_sql, args)

        encoded = base64.b64encode(blob_data['image'])
        binary_data = base64.b64decode(encoded)

        pil_image = Image.open(io.BytesIO(binary_data))
        # use numpy to convert the pil_image into a numpy array
        numpy_image = np.array(pil_image)

        # convert to a openCV2 image and convert from RGB to BGR format
        opencv_image = cv2.cvtColor(numpy_image, cv2.COLOR_RGB2BGR)
        frame = opencv_image
    except:
        raise ImageLoadingException.ImageLoadingException()

    frame = imutils.resize(frame, width=300)
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_detection.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30),
                                            flags=cv2.CASCADE_SCALE_IMAGE)

    # canvas = np.zeros((250, 300, 3), dtype="uint8")
    # frameClone = frame.copy()
    json_data = {}
    # start = time.time()

    if len(faces) > 0:
        faces = sorted(faces, reverse=True,
                       key=lambda x: (x[2] - x[0]) * (x[3] - x[1]))[0]
        (fX, fY, fW, fH) = faces
        # Extract the ROI of the face from the grayscale image, resize it to a fixed 28x28 pixels, and then prepare
        # the ROI for classification via the CNN
        roi = gray[fY:fY + fH, fX:fX + fW]
        roi = cv2.resize(roi, (64, 64))
        roi = roi.astype("float") / 255.0
        roi = img_to_array(roi)
        roi = np.expand_dims(roi, axis=0)

        preds = emotion_classifier.predict(roi)[0]
    else:
        raise NoFaceDetectedException.NoFaceDetectedException(param['frame_id'])

    # end = time.time()
    # print("감정 분석 시간 = ", end - start)

    new_data = Emotion.Emotion(preds, preds.argmax())  # redis 서버에 저장
    new_data.create(param['frame_id'], param['user_id'], param['pomo_id'])

    # flush 이후 첫 데이터라면
    if not r.exists("time"):
        r.set("time", time.time())
    else:
        last_insert_time = r.get("time")
        if time.time() - float(last_insert_time) > 1:  # 3분마다
            insert_sql = "INSERT INTO concentration(frame_id, user_id, pomo_id, angry, disgust, \
                        happy, neutral, sad, scared, surprised, max_column, concentration)\n VALUES "

            r.delete("time")
            val_list = [r.get(key) for key in r.keys("*")]
            map(str, val_list)

            insert_sql += ',\n'.join([val.decode() for val in val_list])

            try:
                # db에 연결
                db_class = mod_dbconn.Database()
                db_class.execute(insert_sql)
                db_class.commit()
                r.flushall()
            except:
                r.set("time", last_insert_time)  # 복구
                raise CacheSavingException.CacheSavingException()

    json_data["state"] = "SUCCESS"
    json_data["concentration"] = new_data.concentration
    return jsonify(json_data)


@app.route('/test')
def imageTest():
    # 더미 blob 이미지 삽입 코드
    path_to_file = os.path.join(os.path.dirname(__file__), 'image/man_test.png')
    with open(path_to_file, "rb") as image_file:
        binary_image = image_file.read()

    # Base64로 인코딩
    binary_image = base64.b64encode(binary_image)

    print(type(binary_image))

    # UTF-8로 디코딩
    binary_image = binary_image.decode("UTF-8")

    print(type(binary_image))

    # set_image_sql = "INSERT INTO pomoImage(frame_id, image, pomo_id, user_id) VALUES (%s, %s, %s, %s)"
    # args = ("1", binary_image, "2", "423LN410760Y3333")
    #
    # db_class = mod_dbconn.Database()
    # db_class.execute(set_image_sql, args)
    # db_class.commit()
    #
    # get_image_sql = "SELECT image FROM pomoImage WHERE pomo_id=%s"
    # args = (2)
    # db_class = mod_dbconn.Database()
    # blob_data = db_class.executeOne(get_image_sql, args)
    #
    # binary_data = base64.b64decode(blob_data['image'])
    # pil_image = Image.open(io.BytesIO(binary_data))
    #
    # # use numpy to convert the pil_image into a numpy array
    # numpy_image = np.array(pil_image)
    #
    # # convert to a openCV2 image and convert from RGB to BGR format
    # opencv_image = cv2.cvtColor(numpy_image, cv2.COLOR_RGB2BGR)
    #
    # # display image to GUI
    # cv2.imshow("PIL2OpenCV", opencv_image)
    # cv2.waitKey(0)

    return "SUCCESS"

# @app.route('/test/<param>')  # get echo api
# def get_echo_call(param):
#     return jsonify({"param": param})
#
# # db에 연결
# db_class = mod_dbconn.Database()
#
# sql = """
#     INSERT INTO test
#     VALUES(%s, %s)
#     """
#
# args = ('3', '최싸피')
#
# db_class.execute(sql, args)
# db_class.commit()  # commit을 해야 db에 새로운 값이 갱신됨
#
# # return render_template('db.html', resultData=row[0])


if __name__ == '__main__':
    app.run(debug=True)
