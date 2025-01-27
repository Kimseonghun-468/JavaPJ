개발 기록

2025-01-27 ~ (적용 예정)

1. Request, Response DTO 재정의
    1. ChatMessage 
2. N+1 Fetch Join 최적화 적용
   * 2-2. Collection에 대한 Batch 적용 (Pagination을 위함)
3. QueryDSL 적용

***
2024-11-16 ~
리펙터링 예정

- 구조 관련 공부 진행 예정
    - html, Controller, Repository 정리 - o
    - page를 response에 - o
    - 프로젝트 상세 Config들 공부 및 정리 후 적용 - o
- 친절한 SQL 공부 진행 예정 - x (난이도 이슈)
    - Query 튜닝 공부 및 적용 예정 - x (이후 진행)

***
2024-11-01 ~ 2024-11-16
코드 리팩터링 시작

- Script File 분할 (완료)
    - 각 항목에 맞는 Script 파일 분할 (각 Modal별로 진행)

---
04-27 토

1. Chatting 기능 구현한거 붙이기.
   * Chatting Room 부분에서, ClubMember를 기준으로 사용할 수 있도록 추가하여 적용해야함
   * Script 부분도 추가 정리 하는 것이 좋아 보임
   
2. 기존 Script 정리하기
   * Script 정리는 일부 완료, Html Modal쪽 정리 하는것 확인예정

3. Spring Security 부분 이해 및 정리 복습하기
4. 기능 구현들 최종 정리 
5. 기능 다이어그램 작성 및 프로젝트 구조 그리고 PPT 작성하기

***
04-29 
1. 기본적인 Chatting 기능 적용 완료
2. FriendShip Table 수정 후 친구 목록 및 친구 추가 삭제 기능 작성

04-30
1. 1:1 Chatting 기능을 회원들이 사용 할 수 있도록 적용

05-05
1. 친구 조회 기능 및 대화 리스트, 게시판 페이징 처리 완료

05-07
1. 화면 규격에 안맞는 것들 Css 추가 및 정리

05-08
1. Search 부분에서 Search Term이 포함된 유저들 전부 불러오기로 변경 ( 기존에는 딱 맞는 하나만 처리해놨었음 )

05-09 
1. 다른 회원의 친구 리스트 부분을 스크롤 페이징으로 변경
2. 다른 회원의 친구 리스트의 경우, 다른 회원의 친구가 조회하는 유저와의 관계가 어떤지에 따라 띄워줘야 하는 관계창이 다르기 때문에 관계를 포함한 쿼리 추가
***


05-10 -05-11 금,토

Chatting 기능에서 메시지 조회 기능을 추가할 예정 
- Case1 : User가 모두 ChattingRoom에 참여중인 경우  ( # 완료 )
  - 상대가 Connection이 되어 있는지 확인이 가능하다면, Message를 보낼 때, ReadStatus를 True로 반환하며 전송하고, 기록한다.

    
- Case2 : User중 한 사람만 참여하는 순간 (완료)
   - Last DisConnect Time을 기준으로 그 이전의 Messages에 대해서는 모두 readStatus를 True로 반환하도록 한다.


- Case3 : User중 한 사람이 참여중인데, 나머지 유저가 참여하는 경우
   - Case1과 Case2를 진행하던 와중, Case3의 경우를 마주친다면 다음과 같이 한다.
     - Case2 동일하게 수행
     - 기존의 참여중인 User의 Chatting List를 새롭게 불러온다.
       - 여기서, 기존의 Chatting List들에 대해서 뽑아와야 하는데 Messages Id와 readStatus 상태만을 불러온 후, 수정할 수 있도록 한다.

  - 위 과정을 지나고 나면 Case1 을 수행한다. (완료)

    
***    
05-12
- Chatting 기능 완료
***
05-13 ~ 05-16
- Spring Security, 회원 가입 기본 설정에서 변경
***

05-17 ~ 05-22
- Spring Security + JWT 구현
- ProfileImage 등록 로직 수정
- Docker build Test 완료
- Token을 Cookie 형식으로 설정
- Chatting Service 기능 변경
- DB Key 참조 안한것들 정합성을 위해 참조 설정

***
06-18
Chatting 1:N Service로 변경 및 ChatMessage NoSQL(MongoDB) 사용 계획
- Table
    - ChatRoom : ChatRoom ID, Last Chat, Last Chat Time
    - ChatUser : UserEmail, Room ID, DisConnect Time
    - ChatMessage : Room ID, Chat Comment, User Email

  ChatRoom List는 해당 User와 연결된 ChatRoom 을 전부 보여준다. ( Chat User의 Room ID를 기준으로 Room ID가 같은 User를 가진 레코드를 조회한다. )

    1. Query로는 일단 들어가 있는 Room ID에서, Room ID를 공유하는 ChatUser들을 Join한다.
    2. Where 조건으로는, 첫 UserEmail을 자신으로, 두번째 UserEmail이 자신이 아닌것
    3. 그럼 Room ID를 통해 Group으로 묶고, 각 Name들을 표기하고 보여준다.
    4. Button엔 Room ID를 넣어서 Session을 열 수 있도록 한다.
    5. 추가 기능으로, Chatting Open (+) 를 넣고, 검색을 통해 방을 생성할 수 있도록 한다.

RDBMS로 설계 후, 처리 시간 확인 및 ChatMessage만 MongoDB로 수정 후 속도 확인 예정
-> 처리 시간을 어떻게 측정할지는 고민중




