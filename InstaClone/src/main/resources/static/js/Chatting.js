document.addEventListener("DOMContentLoaded", ()=> {
    $(document).on('click', '.request-Chatroom-button', function (){
        $('#chatMessageModal').css('z-index', 1052).modal('show');

        var roomId = $(this).attr('room-id')
        if(roomId == null){
            var userName = $(this).data('name');
            roomId = createChatRoom(userName)
        }

        ChattingApp.init(UserProfileApp.$data.loginName, roomId)
        selectChattingUp(ChattingApp.$data.roomId, ChattingApp.$data.upPage);
        selectChattingDown(ChattingApp.$data.roomId, ChattingApp.$data.downPage);
        ChattingApp.$event.scrollPagination = debounce(ChattingApp.scrollPaging,300);
        ChattingApp.$object.scrollContainer.addEventListener('scroll', ChattingApp.$event.scrollPagination);
    });

    $('#send-button-chat').click(function () {
        var messageContent = document.getElementById('messageInput').value.trim();
        var chatMessage = {
            content: messageContent,
        };
        sendChatMessage(ChattingApp.$data.roomId, chatMessage);
    })
    $('#chatMessageModal').on('hidden.bs.modal', function () {
      document.getElementById('messages-box').innerHTML = '';
      ChattingApp.$object.scrollContainer.removeEventListener('scroll', ChattingApp.$event.scrollPagination);
      ChattingApp.disConnect();
    });

});

function selectChattingUp(roomId ,page){
    $.ajax({
        url: '/chat/selectChatMessages?page=' + page,
        type: "POST",
        data: {roomId:roomId, dType:"up"},
        dataType: "JSON",
        success: function (response){
            ChattingApp.setChattingUp(response.data)
        }
    });
}

function selectChattingDown(roomId ,page){
    $.ajax({
        url: '/chat/selectChatMessages?page=' + page,
        type: "POST",
        data: {roomId:roomId, dType:"down"},
        dataType: "JSON",
        success: function (response){
            ChattingApp.setChattingDown(response.data)
        }
    });
}


function getNotReadMessageNum(roomId) {
    $.ajax({
        url: "/chat/getNotReadNum",
        type: "POST",
        dataType: "JSON",
        data: {roomId: roomId},
        success: function (response){
            if (response.data > 300)
                $('#num-'+roomId).html("300+");
            else if (response.data > 0)
                $('#num-'+roomId).html(response.data);
        }
    })
}

function sendChatMessage(roomId, chatMessage){
    $.ajax({
        url: "/chat/sendMessage",
        type: "POST",
        dataType: "JSON",
        data: JSON.stringify({roomId: roomId, chatMessageDTO: chatMessage}),
        contentType: "application/json",
        success: function (response){
        }
    })
}

function createChatRoom(userName) {
    var roomId = null;  // 반환할 roomId 변수 초기화

    $.ajax({
        url: "/chat/createChatRoom",
        type: "POST",
        dataType: "JSON",
        data: { userName: userName },
        async: false,
        success: function (response) {
            roomId = response.data;
        }
    });

    return roomId;
}

