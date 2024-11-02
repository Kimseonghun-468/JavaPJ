const WaitFriendApp = {
    $data: {
        page: 1,
        hasNext: false,
        loginName: null,
        noneImage: "/display?fileName=outprofile.png/",
    },

    $object: {
        waitTable : null,
        scrollContainer : null,

    },

    $event: {
        scrollPagination : null,
    },

    init(loginName) {
        console.log("Wait App 초기화 중...");
        this.$data.page = 1;
        this.$data.loginName = loginName;
        this.$object.waitTable = $("#friendship-requestedList"); // ksh 바꿔야함
        this.$object.scrollContainer = document.getElementById('waitContainer'); // 너도 바꿔야
        this.scrollPaging = this.scrollPaging.bind(this);
    },

    setUserInfo(data) {

        const container = document.createElement("div");

        data.userInfoDTOS.forEach(item => {
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
            userNameBox.classList.add("user-name-box", "waiting-info");

            const userNameLink = document.createElement("a");
            userNameLink.href = `/sidebar/${item.userName}`;
            userNameLink.classList.add("comment-bold");
            userNameLink.textContent = item.userName
            userNameBox.appendChild(userNameLink);

            // 친구 수락 버튼 박스 생성
            const acceptButtonBox = document.createElement("div");
            acceptButtonBox.classList.add("user-chat-button-box2");

            const acceptButton = document.createElement("button");
            acceptButton.classList.add("request-Accept-button", "follow");
            acceptButton.dataset.name = item.userName;
            acceptButton.textContent = "친구 수락";
            acceptButtonBox.appendChild(acceptButton);

            // 모든 요소를 userInfoBox에 추가
            userInfoBox.appendChild(profileBox);
            userInfoBox.appendChild(userNameBox);
            userInfoBox.appendChild(acceptButtonBox);

            // userInfoBox를 최종 container에 추가
            container.appendChild(userInfoBox);
        });

        // dtoList가 비어 있는 경우 처리
        if (data.userInfoDTOS.length === 0) {
            const emptyMessage = document.createElement("div");
            emptyMessage.classList.add("not-exist-list");
            emptyMessage.textContent = "현재 요청받은 친구가 없습니다.";
            container.appendChild(emptyMessage);
        }

        this.$data.hasNext = data.hasNext;
        this.$data.page += 1
        this.$object.waitTable.append(container.innerHTML);

    },

    scrollPaging(){
        var container = this.$object.scrollContainer;
        var value = container.scrollHeight - container.scrollTop
        if (container.clientHeight -15 <= value  &&  value <= container.clientHeight +15) {
            if(this.$data.hasNext)
                selectWaitUsersInfo(this.$data.loginName, this.$data.page)
        }
    }
}