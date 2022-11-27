class ImageLoadingException(Exception):

    def __init__(self):
        super().__init__("이미지 로드 실패")

