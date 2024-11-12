document.addEventListener("DOMContentLoaded", ()=> {
    $('#invite-chatuser').click(function (){
        $('#inviteModal').css('z-index', 1053).modal('show');
        var loginName = document.getElementById("loginName").dataset.loginName;
        var loginEmail = document.getElementById("loginEmail").dataset.loginEmail;

        ChatInviteApp.init(loginName, loginEmail);

        selectInviteUserList(ChatInviteApp.$data.loginName, ChatInviteApp.$data.roomUsers, ChatInviteApp.$data.page)

        ChatInviteApp.$event.scrollPagination = debounce(ChatInviteApp.scrollPaging,300);
        ChatInviteApp.$object.scrollContainer.addEventListener('scroll', ChatInviteApp.$event.scrollPagination);

        document.getElementById('inviteSearchForm').onsubmit = function (event){
            // 여기를 chatInviteApp에서 하자
            event.preventDefault();
            ChatInviteApp.$data.searchTerm = document.getElementById('inviteSearchInput').value;
            document.getElementById('inviteSearchInput').value = "";

            $('#inviteSearchResult').html("");
            // app으로 가져가
            let roomUserList = Object.keys(ChattingApp.$data.nameAndEmailDict);
            selectInviteSearchUserList(loginName, ChatInviteApp.$data.searchTerm, roomUserList);

        }
    });

    $('#invite-submit').click(function (){
        // app으로 가져가
        let userNames = Object.keys(ChattingApp.$data.nameAndEmailDict);
        let userEmails = Object.keys(ChattingApp.$data.emailAndNameDict);
        inviteUsers(userNames, userEmails, ChattingApp.$data.roomId); // roomId처리
    });

    $(document).on('click', '.invite-button, .invite-cancel-button', function (){
        // 여기도 App에서 해야겠다.
        var inviteName = $(this).attr('data-name');
        var inviteEmail = $(this).attr('data-email');
        var inviteUrl = $(this).attr('data-url')
        var $this = $(this);
        if ($this.hasClass('invite-button')) {
            $this.removeClass('invite-button').addClass('invite-cancel-button');
            $this.text(' - ');

            ChatInviteApp.setInviteInfo(inviteName, inviteUrl)
            ChatInviteApp.$data.nameDict[inviteName] = true;
            ChatInviteApp.$data.emailDict[inviteEmail] = true

        } else {
            $this.removeClass('invite-cancel-button').addClass('invite-button');
            $this.text(' + ');
            $('#invite-container').find("div[user-name='" + inviteName + "']").remove();
            $('#invite-container').find("img[user-name='" + inviteName + "']").remove();
            delete ChatInviteApp.$data.nameDict[inviteName]
            delete ChatInviteApp.$data.emailDict[inviteEmail]
        }
    })

    $('#inviteModal').on('hidden.bs.modal', function (){
        $('#invite-container').html("");
        $('#inviteSearchResult').html("");
        ChatInviteApp.$object.scrollContainer.removeEventListener('scroll', ChattingApp.$event.scrollPagination);
    })
});

function selectInviteUserList(loginName, roomUsers, page){
    $.ajax({
        url: '/profileImage/inviteList?page=' + page,
        type: "POST",
        data: {loginName: loginName, roomUsers:roomUsers},
        dataType: "JSON",
        traditional: true,
        success: function (data) {
            ChatInviteApp.setInviteUserList(data)
        }
    })
}

function selectInviteSearchUserList(loginName, searchTerm, roomUsers, page){
    $.ajax({
        url: "/search/invite?page" + page,
        type: "POST",
        data: {loginName:loginName, inviteSearchTerm:searchTerm, roomUsers:roomUsers},
        dataType: "JSON",
        traditional: true,
        success: function (data){
            ChatInviteApp.setInviteUserList(data);
        }
    })
}

function inviteUsers(userNames, userEmails, roomId){
    $.ajax({
        url: '/chat/updateUserAndRoom',
        type: 'POST',
        data: {userEmails:userEmails, roomId:roomId, addNum:userEmails.length},
        traditional: true,
        success: function (data){
            console.log("성공")
            ChattingApp.$data.stompClient.send("/app/chat/inviteLoad/"+roomId, {}, JSON.stringify(userNames));
            $('#inviteModal').modal('hide');
        }
    })
}



