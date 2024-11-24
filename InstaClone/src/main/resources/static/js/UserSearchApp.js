const UserSearchApp = {
    $data: {
        page: 1,
        hasNext: false,
        loginName: null,
        noneImage: "/display?fileName=outprofile.png/",
        searchName: null,
    },

    $object: {
        searchTable : null,
        scrollContainer : null,

    },

    $event: {
        scrollPagination : null,
    },

    init(loginName) {
        console.log("Search App 초기화 중...");
        this.$data.page = 1;
        this.$data.loginName = loginName;
        this.$object.searchTable = $("#searchResult");
        this.$object.scrollContainer = document.getElementById('searchContainer');
        this.scrollPaging = this.scrollPaging.bind(this);
    },

    setSearchUserInfo(data) {
        const container = document.createElement("div"); // 최종적으로 추가할 부모 컨테이너

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
            userNameBox.classList.add("user-name-box");

            const userNameLink = document.createElement("a");
            userNameLink.href = `/sidebar/${item.userName}`;
            userNameLink.classList.add("comment-bold");
            userNameLink.textContent = item.userName;
            userNameBox.appendChild(userNameLink);

            // 모든 요소를 userInfoBox에 추가
            userInfoBox.appendChild(profileBox);
            userInfoBox.appendChild(userNameBox);

            // userInfoBox를 최종 container에 추가
            container.appendChild(userInfoBox);
        });

        // 검색 결과가 없는 경우 처리
        if (data.userInfoDTOS.length === 0) {
            const emptyMessage = document.createElement("div");
            emptyMessage.classList.add("not-exist-list");
            emptyMessage.textContent = "검색한 사용자가 없습니다.";
            container.appendChild(emptyMessage);
        }

        this.$data.hasNext = data.hasNext;
        this.$data.page += 1
        this.$object.searchTable.append(container.innerHTML);
    },
    scrollPaging(){
        var container = this.$object.scrollContainer;
        var value = container.scrollHeight - container.scrollTop
        if (container.clientHeight -15 <= value  &&  value <= container.clientHeight +15) {
            if(this.$data.hasNext)
                selectSearchUserInfo(this.$data.searchName, this.$data.page)
        }
    },
}