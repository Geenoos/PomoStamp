class NoFaceDetectedException(Exception):

    def __init__(self, frame_id):
        super().__init__("얼굴 인식 실패")
        self.frame_id = frame_id

