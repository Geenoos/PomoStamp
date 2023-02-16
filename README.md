# 목차
1. 프로젝트 개요
 - 기획 배경
 - 주요 기능
 - 주요 기술 스택
 - 아키텍처

2. 팀 소개
 - 팀원 소개
 - 협업 툴
 - 협업 환경

3. 산출물
 - 와이어 프레임(초기)
 - 기능 명세서
 - API
 - ERD
 - Notion

4. 결과물
 - 중간 발표 자료
 - 최종 발표 자료
 - 포팅 매뉴얼
 - 최종 빌드 파일



# 프로젝트 개요

---



## 프로젝트 진행 기간

---

- 2022.07.05(월) - 2022.08.19(금)
- SSAFY 7기 2학기 공통프로젝트



## 기획 배경

---

- SSAFY 2학기 첫번째 프로젝트. **WebRTC**기술을 이용한 비디오 컨퍼런스 서비스를 만드는 것이 목표였습니다. 6주간의 기간동안 WebRTC라이브러리를 직접 구현하는 것은 무리라고 판단하여 오픈소스 라이브러리 **OpenVidu**를 사용하였습니다.
- Front-End는 JavaScript 라이브러리 **React**를 선택했습니다. SSAFY 1학기 동안 Vue를 공부했지만 React 또한 Front-End에서 자주 쓰이는 기술이고 SPA를 구현함에 있어서 둘 다 부족하지 않다고 생각했어서 새로운 기술을 사용해보자는 쪽으로 의견이 모여 고르게 되었습니다. 상태관리 라이브러리는 **Recoil**을 사용했는데, 기존의 Redux는 보일러플레이트가 상당히 무겁고 프로젝트 적용이 처음엔 쉽지 않다는 점과 Recoil이 새로운 상태관리 라이브러리로 등장하여 사용성이 괜찮고 접근성이 좋다는 점에서 선택하게 되었습니다.
- 코로나가 심각한 상황에서 수험생이나 취준생 등 공부하는 사람들이 갈 수 있는 곳이 많이 줄었습니다. 사람들이 온라인상에서 학습이라는 공동의 목표로 모일 수 있는 공간을 만들고 싶었습니다. 이미 디스코드나 열품타와 같이 모여서 공부하는 문화가 생겨나고 있었기 때문에 새로운 아이디어는 아니었지만 **웹기반으로 운영**되는 서비스는 많지 않았습니다.
- 사람들은 다른사람들과 함께 있을 때 하고 있는 일에 조금 더 몰두하게 되고 집중하게 되는 사회적 촉진현상을 보이게 됩니다.
- 시간관리법으로 요새 인기를 끌고 있는 **[‘뽀모도로’](https://ko.wikipedia.org/wiki/%ED%8F%AC%EB%AA%A8%EB%8F%84%EB%A1%9C_%EA%B8%B0%EB%B2%95)**를 적용했습니다. 25분간 집중해서 공부하고 5분간 휴식시간을 가지고 총 4번의 반복을 한 뒤 30분 휴식하며 마무리합니다. 공부시간은 25분에서 본인의 입맛에 맞게 타이머를 설정할 수 있습니다.
- 공부하는 사람들은 시간을 어떻게 관리할 지가 상당히 중요한 문제입니다. 너무 공부만 하다가는 쉽게 지치고 번아웃이 오게되고, 시간관리를 따로 하지 않으면 비효율적으로 사용되는 시간이 많아지게 됩니다. 효율적인 시간관리와 여러사람과 함께 공부하며 촉진효과를 통해 학습능력 상승을 기대해볼 수 있습니다.


## 주요 기능

---

### 카카오 로그인

- 카카오 아이디로 간편하게 로그인

### 오늘의 To Do List 작성

- ToDoList 등록, 삭제, 수정
- 이전의 등록한 ToDoList 목록 조회

### 스터디룸 생성 및 입장

- 스터디룸 이름, 비밀번호여부, 비밀번호, 캠여부, 인원수, 스터디룸 설명을 기입하여 생성
- 입장 시 사용자 비디오 테스트 룸으로 이동
- 스터디룸 입장 시 ToDoList 작성,수정,삭제
- 뽀모도로 타이머 설정 및 학습 시작

### AI분석 집중도 체크

- 학습 중 일정 시간 간격으로 사용자의 비디오 소스 서버로 전송
- 비디오 소스를 기반으로 AI집중도 체크
- 학습 종료 후 AI분석 집중도 자료 제공

### 나의 정보 조회 및 수정

- 나의 뽀모시간 확인 및 수정
- 뽀모 학습 기록 잔디밭 제공
- 오늘의 공부시간 및 시간별 학습 주제 분석
- 오늘의 공부기록 확인


## 기술 스택

---

- Server: AWS EC2 Ubuntu 20.04 LTS
- Python: 3.8.10
- Redis: 7.0.4
- Java: open-JDK zulu 8.33.0.1
- MySQL: 8.0.29
- node.js: 16.16.0 (LTS)
- React: 18.2.0
- Recoil: 0.7.4
- React-query: 4.0.10
- MUI: v5.*
- openvidu: 2.22.0
- Nginx: 1.18.0
- VSCode: Stable Build
- PyCharm: 22.1.4
- IntelliJ version: 22.1.3
- MySQL Workbench: Stable Build
- FTP(File Transfer Protocol): FileZilla
- SSH client: Xshell
- API Test: Postman




## 시스템 아키텍처

---

![image](https://user-images.githubusercontent.com/34851254/204149946-00de983e-52c7-421c-a45c-749414196394.png)
![image](https://user-images.githubusercontent.com/34851254/204149961-a5026480-8956-4fa8-ad09-495ca228db47.png)




## 

# 팀 소개

---

## 팀원 소개 

---

- 이도연: 팀장
- 김선호: 팀원
- 김현지: 팀원
- 박현영: 팀원
- 최진우: 팀원



## 역할 분배

---
![image](https://user-images.githubusercontent.com/34851254/204149995-cb30b78a-66e7-4e07-b0a5-6632c955801f.png)




## 협업 툴 및 환경

---

- Jira
  - 매주 월요일 1주 단위 스프린트 시작
- Git
  - 컨벤션 설정
  - 같은 역할의 팀원을 Reviewer로 지정하여 코드 리뷰 진행
- Mattermost
- Notion
  - 개발일지, 회의 등 모든 작업 진행
  - 매일 오전 Scrum 회의 기록
- Webex
  - 매일 프로젝트 진행
  - 필요한 경우 세부세션 활용하여 세부 단위 별 진행





# 산출물

---



## 프로젝트 산출물

---

### 스토리보드 및 와이어 프레임
![image](https://user-images.githubusercontent.com/34851254/204149218-151c1c96-94e7-4417-9357-ca7c7c5ca1ca.png)
![image](https://user-images.githubusercontent.com/34851254/204149265-72dd4d01-cbf1-4ec9-bc06-135e2196b0ff.png)
![image](https://user-images.githubusercontent.com/34851254/204149299-e3db904e-4832-40a9-a64e-cb9ae191c3fa.png)
![image](https://user-images.githubusercontent.com/34851254/204149313-78391c5f-9fa7-4dca-a99d-95a20be3891a.png)

### API 명세서
![image](https://user-images.githubusercontent.com/34851254/204149405-5870400f-3a6f-4679-a1f2-39c930d8d369.png)
![image](https://user-images.githubusercontent.com/34851254/204149417-e77e1a80-383c-4405-b1a0-9366d6f5cf9c.png)
![image](https://user-images.githubusercontent.com/34851254/204149438-04adbfbc-368e-4cac-a30e-75d336c64923.png)
![image](https://user-images.githubusercontent.com/34851254/204149450-ac469393-4da6-4c17-b066-ef64322471e4.png)
![image](https://user-images.githubusercontent.com/34851254/204149465-0fe8a2d6-3fa9-42fa-9280-68a466d75e66.png)
![image](https://user-images.githubusercontent.com/34851254/204149475-5fe7071a-b841-4bc6-ad54-a046be02a0d8.png)
![image](https://user-images.githubusercontent.com/34851254/204149488-4d15e43f-0ce7-4baa-bec3-06889f9370ff.png)





### ERD

![뽀모스ERD](https://user-images.githubusercontent.com/34851254/204149631-1fd53bd6-289f-4244-8a90-ad098bc46a72.png)




## 프로젝트 결과물

---

- 포팅 매뉴얼
- 중간발표자료
- 최종발표자료
