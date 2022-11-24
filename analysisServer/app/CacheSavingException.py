class CacheSavingException(Exception):

    def __init__(self):
        super().__init__("캐시 데이터 DB 저장 실패")

