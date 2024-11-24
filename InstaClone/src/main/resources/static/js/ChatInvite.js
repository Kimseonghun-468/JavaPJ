document.addEventListener("DOMContentLoaded", ()=> {
    $('#invite-chatuser').click(function (){
        $('#inviteModal').css('z-index', 1053).modal('show');

        ChatInviteApp.init(UserProfileApp.$data.loginName, UserProfileApp.$data.loginEmail);

        selectInviteUserList(ChattingApp.$data.nameAndEmailDict, ChatInviteApp.$data.page)

        ChatInviteApp.$event.scrollPagination = debounce(ChatInviteApp.scrollPaging,300);
        ChatInviteApp.$object.scrollContainer.addEventListener('scroll', ChatInviteApp.$event.scrollPagination);

        document.getElementById('inviteSearchForm').onsubmit = function (event){
            // 여기를 chatInviteApp에서 하자
            event.preventDefault();
            ChatInviteApp.$data.searchTerm = document.getElementById('inviteSearchInput').value;
            document.getElementById('inviteSearchInput').value = "";
            ChatInviteApp.$data.page = 1
            $('#inviteSearchResult').html("");
            // app으로 가져가
            let roomUserList = Object.keys(ChattingApp.$data.nameAndEmailDict);
            selectInviteSearchUserList(ChatInviteApp.$data.searchTerm, roomUserList, ChatInviteApp.$data.page);

        }
    });

    $('#invite-submit').click(function (){
        // app으로 가져가
        let userNames = Object.keys(ChatInviteApp.$data.nameDict);
        let userEmails = Object.keys(ChatInviteApp.$data.emailDict);
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
        ChatInviteApp.$object.scrollContainer.removeEventListener('scroll', ChatInviteApp.$event.scrollPagination);
    })
});

function selectInviteUserList(roomUsers, page){
    $.ajax({
        url: '/api/v1/friend/selectInviteList?page=' + page,
        type: "POST",
        data: {roomUsers:Object.keys(roomUsers)},
        dataType: "JSON",
        traditional: true,
        success: function (response) {
            ChatInviteApp.setInviteUserList(response.data, false)
        }
    })
}

function selectInviteSearchUserList(searchTerm, roomUsers, page){
    $.ajax({
        url: "/api/v1/friend/selectInviteSearchUsers?page=" + page,
        type: "POST",
        data: JSON.stringify({searchTerm:searchTerm, userNames:roomUsers}),
        dataType: "JSON",
        contentType: "application/json",
        traditional: true,
        success: function (response){
            ChatInviteApp.setInviteUserList(response.data, true);
        }
    })
}

function inviteUsers(userNames, userEmails, roomId){
    $.ajax({
        url: "/chat/updateUserAndRoom",
        type: 'POST',
        data: JSON.stringify({userEmails:userEmails, roomId:roomId, addNum:userEmails.length}),
        dataType: "JSON",
        contentType: "application/json",
        success: function (data){
            console.log("성공")
            ChattingApp.$data.stompClient.send("/app/chat/inviteLoad/"+ roomId, {}, JSON.stringify({userNames:userNames, roomId:roomId}));
            $('#inviteModal').modal('hide');
        }
    })
}



