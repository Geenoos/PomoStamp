from app import mod_dbconn
import redis

r = redis.Redis(host="i7a608.p.ssafy.io", port=6379, db=0, password="pomo1229")


class Emotion:
    EMOTIONS = ["angry",    "disgust",  "scared",   "happy",    "sad",  "surprised",    "neutral"]
    WEIGHTS = [ 0.25,       0.2,        0.3,        0.6,        0.3,    0.6,            0.9]
    plist = []
    max_column = -1
    concentration = -1

    def __init__(self, percent_list, max_column):
        # self.frame_id = frame_id
        self.plist = percent_list
        self.max_column = max_column
        self.calculateConcentration()

    def calculateConcentration(self):
        self.concentration = self.plist[self.max_column] * self.WEIGHTS[self.max_column] * 100
        # print("감정 = ", self.EMOTIONS[self.max_column])
        # print("확률 = ", self.plist[self.max_column])
        # print("집중률 = ", self.concentration)

    def create(self, frame_id, user_id, pomo_id):  # create new user
        # new_data = Emotion(frame_id, percent_list, max_column)
        # sql = f"INSERT INTO concentration(frame_id, user_id, pomo_id, \
        #       angry, disgust, happy, neutral, sad, scared, surprised, max_column, concentration) \
        #       VALUES ({frame_id}, {user_id}, {pomo_id}, {self.plist[0]}, se{lf.plist[1]}, {self.plist[2]}, \
        #       {self.plist[3]}, {self.plist[4]}, {self.plist[5]}, {self.plist[6]}, {self.max_column}, {self.concentration})"

        sql = f"({frame_id}, \"{user_id}\", {pomo_id}, {self.plist[0]}, {self.plist[1]}, {self.plist[2]}, \
            {self.plist[3]}, {self.plist[4]}, {self.plist[5]}, {self.plist[6]}, {self.max_column}, {self.concentration})"

        r.set(frame_id, sql)

        # db_class = mod_dbconn.Database()  # db에 연결
        # db_class.execute(sql, args)
        # db_class.commit()  # commit을 해야 db에 새로운 값이 갱신됨
        # return "success"

