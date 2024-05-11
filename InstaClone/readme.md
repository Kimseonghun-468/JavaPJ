개발 기록

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

    
