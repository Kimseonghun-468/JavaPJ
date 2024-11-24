const ChatInviteApp = {
    $data: {
        page: 1,
        hasNext: false,
        loginName: null,
        loginEmail: null,
        noneImage: "/display?fileName=outprofile.png/",
        searchTerm: null,
        status: false, // 기본값 False, 검색중 True

        roomUsers : {},
        roomEmails : {},
        nameDict : {},
        emailDict: {},

    },

    $object: {
        inviteTable : null,
        scrollContainer : null,

    },

    $event: {
        scrollPagination : null,
    },

    init(loginName, loginEmail){
        this.$data.loginName = loginName;
        this.$data.loginEmail = loginEmail;
        this.$data.page = 1;
        this.$data.nameDict = {};
        this.$data.emailDict = {};
        this.makeUserDict()
        this.$object.inviteTable = $("#inviteSearchResult");
        this.$object.scrollContainer = document.getElementById('scroll-invite')
        this.scrollPaging = this.scrollPaging.bind(this);
    },

    scrollPaging(){
        var container = this.$object.scrollContainer;
        var value = container.scrollHeight - container.scrollTop
        if (container.clientHeight -15 <= value  &&  value <= container.clientHeight +15) {
            if(this.$data.hasNext) {
                if (this.$data.status == true) {
                    selectInviteSearchUserList(this.$data.loginName, this.$data.searchTerm, this.$data.roomUsers, this.$data.page)
                } else {
                    selectInviteUserList(this.$data.roomUsers, this.$data.page)
                }
            }
        }
    },

    makeUserDict() {
        this.$data.roomUsers = Object.keys(ChattingApp.$data.nameAndEmailDict);
        this.$data.roomEmails = Object.keys(ChattingApp.$data.emailAndNameDict);
    },

    setInviteUserList(data, status) {
        const inviteSearchResult = document.getElementById('inviteSearchResult');
        // inviteSearchResult.innerHTML = "";  // 기존 내용을 초기화합니다.

        data.userInfoDTOS.forEach(item => {
            const imageUrl = item.imgName ? `/display?fileName=${item.imageURL}` : this.$data.noneImage;
            const friendName = item.userName;
            const friendEmail = item.userEmail;

            // 사용자 정보 박스를 생성합니다.
            const userInfoBox = document.createElement("div");
            userInfoBox.classList.add("user-info-box", "invite-info");

            // 프로필 이미지 박스를 생성합니다.
            const profileBox = document.createElement("div");
            profileBox.classList.add("profile-box");

            const profileImg = document.createElement("img");
            profileImg.src = imageUrl;
            profileImg.alt = "Profile Picture";
            profileImg.classList.add("profile-picture");

            profileBox.appendChild(profileImg);

            // 사용자 이름 박스를 생성합니다.
            const userNameBoxInvite = document.createElement("div");
            userNameBoxInvite.classList.add("user-name-box-invite");

            const userNameLink = document.createElement("a");
            userNameLink.href = `/sidebar/${friendName}`;
            userNameLink.classList.add("comment-bold");
            userNameLink.textContent = friendName;

            userNameBoxInvite.appendChild(userNameLink);

            // 초대/취소 버튼 박스를 생성합니다.
            const userChatButtonBox = document.createElement("div");
            userChatButtonBox.classList.add("user-chat-button-box");

            const inviteButton = document.createElement("button");
            inviteButton.dataset.name = friendName;
            inviteButton.dataset.email = friendEmail;
            inviteButton.dataset.url = imageUrl;

            if (this.$data.nameDict[friendName] == null) {
                inviteButton.classList.add("invite-button", "follow");
                inviteButton.textContent = " + ";
            } else {
                inviteButton.classList.add("invite-cancel-button", "follow");
                inviteButton.textContent = " - ";
            }

            userChatButtonBox.appendChild(inviteButton);

            // 모든 요소를 userInfoBox에 추가합니다.
            userInfoBox.appendChild(profileBox);
            userInfoBox.appendChild(userNameBoxInvite);
            userInfoBox.appendChild(userChatButtonBox);

            // inviteSearchResult에 추가합니다.
            inviteSearchResult.appendChild(userInfoBox);
        });

        // 검색 결과가 없을 때 메시지를 추가합니다.
        if (data.userInfoDTOS.length === 0) {
            const notExistMessage = document.createElement("div");
            notExistMessage.classList.add("not-exist-list");
            notExistMessage.textContent = "검색한 친구가 없습니다.";
            inviteSearchResult.appendChild(notExistMessage);
        }

        this.$data.hasNext = data.hasNext;
        this.$data.page += 1
        this.$data.status = status

    },

    setInviteInfo(inviteName, imageUrl){
        let nameSlice
        if (inviteName.length > 5)
            nameSlice = inviteName.slice(0,5) + ' ...';
        else
            nameSlice = inviteName
        result = "";
        result += '<div class="invite-profile-container">'
        result += '<img src=' + imageUrl +' class="invite-profile" user-name="' + inviteName + '">';
        result += '<div class="invite-profile-name" user-name="' + inviteName +'">' + nameSlice + '</div>'
        result += '</div>'
        $('#invite-container').append(result)
    },
}