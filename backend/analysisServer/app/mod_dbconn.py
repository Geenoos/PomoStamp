import pymysql


class Database():
    def __init__(self):           # 접속할 데이터베이스의 주소 (같은 컴퓨터에 있는 데이터베이스에 접속하기 때문에 localhost)
        self.db = pymysql.connect(host='i7a608.p.ssafy.io',
                                  user='pomos',  # 데이터베이스에 접속할 사용자 아이디
                                  password='pomos1229',  # 사용자 비밀번호
                                  # 'port': 3306,  # 관계형 데이터베이스는 주로 3306 포트를 통해 연결됨
                                  db='pomos',  # 'database': 'pomos'  # 실제 사용할 데이터베이스 이름
                                  charset='utf8')
        self.cursor = self.db.cursor(pymysql.cursors.DictCursor)

    def execute(self, query, args={}):
        self.cursor.execute(query, args)

    def executeOne(self, query, args={}):
        self.cursor.execute(query, args)
        row = self.cursor.fetchone()
        return row

    def executeAll(self, query, args={}):
        self.cursor.execute(query, args)
        row = self.cursor.fetchall()
        return row

    def commit(self):
        self.db.commit()
