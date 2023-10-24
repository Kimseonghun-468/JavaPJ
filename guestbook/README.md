# GuestBook 연습

이번 Example 연습에서


데이터 DTO & Entity 
---
- 데이터 처리를 진행할 때, Data Transfer Object(DTO) 구조를 사용해서 Entity를 DTO로 변환해서 데이터를 적용
- Database에 등록하기 위해서는 DTO를 Entity로 변경하여 등록

Controller 
---
- 받을 인자들을 미리 정의하고, 활용해서 검색, 수정, 삭제, 리스팅 등을 진행
- GetMapping인지, PostMapping인지 정의하며 활용
- redirectAttributes.addAttribute()를 사용하여 Url로 인자 전달 및 html에서 적용, 여기서는 @ModelAttribute("requestDTO") PageRequestDTO requestDTO를 활용해서 Page url 인자를 사전 정의한 형식에 넣어서 받아올 수 있도록 한다.
- model.addAttribute()를 적용해서 Controller에서 View인 html로 데이터를 전달해서 적용

Service, Serviceimpl 
---
- Service에서는 Entity & DTO의 변환 함수를 정의하고, 동작할 함수들의 이름을 정의한 후, Impl에서 함수를 Override해서 정의한다.
- impl에서는 실제 service에서 동작할 함수들의 동작을 정의한다.
- impl에는 읽기 삭제 수정 검색 등이 구현되어 있다.

Entity
---
- BaseEntity에서 기본적으로 자동의로 정의되는 등록, 수정 시간에 관한 내용을 정의하고, 실제 사용할 Entity에서 상속 받아 사용한다.  
- Guestbook Entity에서는 BaseEntity를 상속하고, 추가로 Database에서 사용할 title, content, wirter들을 정의하고 Entity 자체에서 사용될 함수들을 정의한다.

DTO
---
- DTO는 Entity에서 가져오는 데이터 처리를 DTO라는 형태로 변환시켜 사용할 것이기 때문에 Entity를 기반으로 정의한다.
- PageRequestDTO는 page와 size만 기본 정의가 되어 있고, 이를 통해서 Database에 있는 내용을 Page처리할 수 있도록 한다. Database에서 Entity로 호출하고, PageRequestDTO에 사전 정의한 대로 Page 처리를 진행후, DTO로 변환하여 html에 전달하는 방식으로 List를 완료
- 여기서 getList를 통해, PageRequestDTO를 인자로 받아 Page 객체에 Database내용을 넣고, Entity를 DTO로 변환 후 model Attribute를 통해 controller에서 html로 전달

Repository
---
JPA 상속 받는 내용으로 구성, 실제로는 Serviceimpl에서 작동하기 때문에,
impl에서 final로 정의 -> final로 정의하면 초기화 이후 수정이 불가능함.