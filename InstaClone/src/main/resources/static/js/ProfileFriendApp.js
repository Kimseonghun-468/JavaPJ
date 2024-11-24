const ProfileFriendApp = {
    $data: {
        page: 1,
        hasNext: false,
        loginName: null,
        userName: null,
        noneImage: "/display?fileName=outprofile.png/",
    },

    $object: {
        profileFriendTable : null,
        scrollContainer : null,
    },

    $event: {
        scrollPagination : null,
    },

    init(loginName, userName) {
        console.log("Profile Friend App 초기화 중...");
        this.$data.page = 1;
        this.$data.loginName = loginName;
        this.$data.userName = userName;
        this.$object.profileFriendTable = $("#friend-List-UserBox");
        this.$object.scrollContainer = document.getElementById('profileFriendContainer');
        this.scrollPaging = this.scrollPaging.bind(this);
    },

    scrollPaging(){
        var container = this.$object.scrollContainer;
        var value = container.scrollHeight - container.scrollTop
        if (container.clientHeight -15 <= value  &&  value <= container.clientHeight +15) {
            if(this.$data.hasNext)
                selectProfileFriend(this.$data.userName, this.$data.page)
        }
    },

    setProfileFriend(data) {
        const container = document.createElement("div"); // 최종적으로 추가할 부모 컨테이너

        data.userInfoDTOS.forEach(item => {

            if (item.userName == this.$data.loginName)
                return;
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
            profileImage.src = item.imgName ? `/display?fileName=${item.imageURL}` : this.$data.noneImage;
            profileBox.appendChild(profileImage);

            // 사용자 이름 박스 생성
            const userNameBox = document.createElement("div");
            userNameBox.classList.add("user-name-box-multifriend");

            const userNameLink = document.createElement("a");
            userNameLink.href = `/sidebar/${item.userName}`;
            userNameLink.classList.add("comment-bold");
            userNameLink.textContent = item.userName;
            userNameBox.appendChild(userNameLink);

            // 버튼 박스 생성
            const buttonBox = document.createElement("div");
            buttonBox.classList.add("friend-delete-button-box");

            // 친구 상태에 따른 버튼 설정
            let button = document.createElement("button");
            button.dataset.name = item.userName;

            if (item.status == "NONE") {
                button.classList.add("request-Follow-button", "follow");
                button.textContent = "친구 요청";
            } else if (item.status == "REQUESTER") {
                button.classList.add("request-Cancel-button", "follow");
                button.textContent = "친구 대기";
            } else if (item.status == "ACCEPTED") {
                button.classList.add("request-Delete-button");
                button.textContent = "친구 삭제";
            } else if (item.status == "RECEIVER") {
                button.classList.add("request-Accept-button", "follow");
                button.textContent = "친구 수락";
            }

            buttonBox.appendChild(button);

            // 모든 요소를 userInfoBox에 추가
            userInfoBox.appendChild(profileBox);
            userInfoBox.appendChild(userNameBox);
            userInfoBox.appendChild(buttonBox);

            // userInfoBox를 최종 container에 추가
            container.appendChild(userInfoBox);
        });

        // 데이터가 비어 있는 경우 처리
        if (data.userInfoDTOS.length === 0) {
            const emptyMessage = document.createElement("div");
            emptyMessage.classList.add("not-exist-list");
            emptyMessage.textContent = "등록된 친구가 없습니다.";
            container.appendChild(emptyMessage);
        }

        this.$data.hasNext = data.hasNext;
        this.$data.page += 1
        this.$object.profileFriendTable.append(container.innerHTML);
    },

    setFirst(data) { // KSH EDIT : Status 처리가 안보임..?
        // 최종적으로 추가할 부모 컨테이너 생성
        const userInfoBox = document.createElement("div");
        userInfoBox.classList.add("user-info-box");

        // 프로필 박스 생성
        const profileBox = document.createElement("div");
        profileBox.classList.add("profile-box");

        // 프로필 이미지 설정
        const profileImage = document.createElement("img");
        profileImage.classList.add("profile-picture");
        profileImage.alt = "Profile Picture";
        profileImage.src = data.image
            ? `/display?fileName=${data.image}`
            : "/display?fileName=outprofile.png/";
        profileBox.appendChild(profileImage);

        // 사용자 이름 박스 생성
        const userNameBox = document.createElement("div");
        userNameBox.classList.add("user-name-box");

        const userNameLink = document.createElement("a");
        userNameLink.href = `/sidebar/${data.name}`;
        userNameLink.classList.add("comment-bold");
        userNameLink.textContent = data.name;
        userNameBox.appendChild(userNameLink);

        // 채팅 버튼 박스 생성
        const chatButtonBox = document.createElement("div");
        chatButtonBox.classList.add("user-chat-button-box");

        // 모든 요소를 userInfoBox에 추가
        userInfoBox.appendChild(profileBox);
        userInfoBox.appendChild(userNameBox);
        userInfoBox.appendChild(chatButtonBox);

        // HTML에 요소 추가 (이름이 null이 아닐 경우)
        if (data.name != null) {
            const friendListUserBox = document.getElementById("friend-List-UserBox");
            friendListUserBox.prepend(userInfoBox);
        }
    },

}