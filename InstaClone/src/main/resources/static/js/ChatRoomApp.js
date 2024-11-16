const ChatRoomApp = {
    $data: {
        page: 1,
        hasNext: false,
        loginEmail: null,
        loginName: null,
        noneImage: "/display?fileName=outprofile.png/",
    },

    $object: {
        chatRoomTable : null,
        scrollContainer : null,

    },

    $event: {
        scrollPagination : null,
    },

    init(loginEmail, loginName) {
        console.log("ChatRoom App 초기화 중...");
        this.$data.page = 1;
        this.$data.loginEmail = loginEmail;
        this.$data.loginName = loginName;
        this.$object.chatRoomTable = $("#chatroom-List"); // ksh 바꿔야함
        this.$object.scrollContainer = document.getElementById('chatRoomContainer'); // 너도 바꿔야
        this.scrollPaging = this.scrollPaging.bind(this);
    },


    setChatRoom(data){
        const container = document.createElement("div"); // 최종적으로 추가할 부모 컨테이너

        data.chatRoomDTOS.forEach(value => {
            if (value.lastChat == null) return; // lastChat이 null일 경우 건너뜀

            getNotReadMessageNum(this.$data.loginEmail, value.roomId); // 메시지 수 가져오기
            const nameLength = value.userInfoDTOS.length

            // 개별 사용자 정보 박스 생성
            const userInfoBox = document.createElement("div");
            userInfoBox.classList.add("user-info-box");

            // 프로필 박스 생성
            const profileBox = document.createElement("div");
            profileBox.classList.add("profile-box");

            // 프로필 이미지 설정
            const profileImage = document.createElement("img");
            profileImage.classList.add("profile-picture");
            profileImage.alt = "Profile Picture";
            // 내가 아닌 첫번쨰꺼 그냥 써야겠다.
            profileImage.src = value.userInfoDTOS[1].imgName ? `/display?fileName=${value.userInfoDTOS[1].imageURL}` : this.$data.noneImage;
            profileBox.appendChild(profileImage);

            // 채팅 이름 컨테이너 생성
            const chatNameContainer = document.createElement("div");
            chatNameContainer.classList.add("chat-name-container");

            // 사용자 이름 박스 생성
            const userNameBox = document.createElement("div");
            userNameBox.classList.add("user-name-box-chatlist");

            const userName = document.createElement("div");

            var roomUserNames = '';
            value.userInfoDTOS.forEach(user => {
                if (user.userName != this.$data.loginName)
                roomUserNames += ', ' + user.userName;
            })
            userName.classList.add("user-name");
            userName.textContent = roomUserNames.length > 14 ? roomUserNames.slice(2, 14) + '...' : roomUserNames.slice(2);
            userNameBox.appendChild(userName);

            // 친구 숫자 표시
            if (nameLength > 2) {
                const chatUserNum = document.createElement("div");
                chatUserNum.classList.add("chat-user-num");
                chatUserNum.textContent = `${nameLength}`;
                userNameBox.appendChild(chatUserNum);
            }

            chatNameContainer.appendChild(userNameBox);

            // 마지막 채팅 코멘트 표시
            const lastChatComment = document.createElement("div");
            lastChatComment.classList.add("last-chat-comment");
            lastChatComment.textContent = value.lastChat.length > 20
                ? `${value.lastChat.slice(0, 20)}...`
                : value.lastChat;
            chatNameContainer.appendChild(lastChatComment);

            // 채팅 시간과 읽지 않은 메시지 수 표시
            const chatTimeNumContainer = document.createElement("div");
            chatTimeNumContainer.classList.add("chat-tiem-num-container");

            const lastChatTime = document.createElement("div");
            lastChatTime.classList.add("last-chat-time");
            lastChatTime.textContent = formatTimeDifference(value.lastChatTime);
            chatTimeNumContainer.appendChild(lastChatTime);

            const notReadNum = document.createElement("div");
            notReadNum.classList.add("not-read-num", "comment-bold");
            notReadNum.id = `num-${value.roomId}`;
            chatTimeNumContainer.appendChild(notReadNum);

            // 채팅방 열기 버튼 생성
            const chatButtonBox = document.createElement("div");
            chatButtonBox.classList.add("user-chat-button-box3");

            const openChatButton = document.createElement("button");
            openChatButton.classList.add("request-Chatroom-button");
            openChatButton.setAttribute("room-id", value.roomId);
            openChatButton.textContent = "대화창 열기";
            chatButtonBox.appendChild(openChatButton);

            // 모든 요소를 userInfoBox에 추가
            userInfoBox.appendChild(profileBox);
            userInfoBox.appendChild(chatNameContainer);
            userInfoBox.appendChild(chatTimeNumContainer);
            userInfoBox.appendChild(chatButtonBox);

            // userInfoBox를 최종 container에 추가
            container.appendChild(userInfoBox);
        });

        // dtoList가 비어 있는 경우 처리
        if (data.chatRoomDTOS.length === 0 || container.innerHTML.trim() === "") {
            const emptyMessage = document.createElement("div");
            emptyMessage.classList.add("not-exist-list");
            emptyMessage.textContent = "현재 등록된 채팅방이 없습니다.";
            container.appendChild(emptyMessage);
        }

        this.$data.hasNext = data.hasNext;
        this.$data.page += 1
        this.$object.chatRoomTable.append(container.innerHTML);
    },

    scrollPaging(){
        var container = this.$object.scrollContainer;
        var value = container.scrollHeight - container.scrollTop
        if (container.clientHeight -15 <= value  &&  value <= container.clientHeight +15) {
            if(this.$data.hasNext)
                selectChatRoom(this.$data.loginEmail, this.$data.page)
        }
    },
}