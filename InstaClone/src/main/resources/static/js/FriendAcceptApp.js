const FriendApp = {
    $data: {
        page: 1,
        loginName: null,
        noneImage: "/display?fileName=outprofile.png/",
    },

    $object: {

    },
    init(loginName) {
        console.log("FriendApp 초기화 중...");
        this.$data.page = 1;
        this.$data.loginName = loginName;
    },

    // 필요한 다른 메서드를 추가할 수 있습니다.
    nextPage() {
        this.$data.page++;
        console.log("다음 페이지로 이동:", this.$data.page);
    },

    setUserInfo(data) {
        const container = document.createElement("div"); // 최종적으로 추가할 부모 컨테이너

        data.forEach(value => {
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
            profileImage.src = value.imgName ? `/display?fileName=${value.imageURL}` : FriendApp.$data.noneImage;
            profileBox.appendChild(profileImage);

            // 사용자 이름 박스 생성
            const userNameBox = document.createElement("div");
            userNameBox.classList.add("user-name-box-friend");

            const userNameLink = document.createElement("a");
            userNameLink.href = `/sidebar/${value.userName}`;
            userNameLink.classList.add("comment-bold");
            userNameLink.textContent = value.userName;
            userNameBox.appendChild(userNameLink);

            // 채팅 버튼 박스 생성
            const chatButtonBox = document.createElement("div");
            chatButtonBox.classList.add("user-chat-button-box2");

            const chatButton = document.createElement("button");
            chatButton.classList.add("request-Chatroom-button", "follow");
            chatButton.dataset.name = value.userEmail;
            chatButton.textContent = "1:1 채팅하기";
            chatButtonBox.appendChild(chatButton);

            // 친구 삭제 버튼 박스 생성
            const friendButtonBox = document.createElement("div");
            friendButtonBox.classList.add("user-friend-button-box2");

            const deleteButton = document.createElement("button");
            deleteButton.classList.add("request-Delete-button", "follow");
            deleteButton.dataset.name = value.userEmail;
            deleteButton.textContent = "친구 삭제";
            friendButtonBox.appendChild(deleteButton);

            // 모든 요소를 userInfoBox에 추가
            userInfoBox.appendChild(profileBox);
            userInfoBox.appendChild(userNameBox);
            userInfoBox.appendChild(chatButtonBox);
            userInfoBox.appendChild(friendButtonBox);

            // userInfoBox를 최종 container에 추가
            container.appendChild(userInfoBox);
        });

        // data가 비어 있는 경우 처리
        if (data.length === 0) {
            const emptyMessage = document.createElement("div");
            emptyMessage.classList.add("not-exist-list");
            emptyMessage.textContent = "현재 등록된 친구가 없습니다.";
            container.appendChild(emptyMessage);
        }

        return container.innerHTML;
    }
};