document.addEventListener("DOMContentLoaded", ()=> {
    $(document).on('click', '.request-Chatroom-button', function (){
        $('#chatMessageModal').css('z-index', 1052).modal('show');
        var loginName = document.getElementById("loginName").dataset.loginName;
        var loginEmail = document.getElementById("loginEmail").dataset.loginEmail;
        var roomId = $(this).attr('room-id')

        ChattingApp.init(loginName, loginEmail, roomId)

        selectChattingUp(ChattingApp.$data.loginEmail, ChattingApp.$data.roomId, ChattingApp.$data.upPage)
        selectChattingDown(ChattingApp.$data.loginEmail, ChattingApp.$data.roomId, ChattingApp.$data.downPage)

        ChattingApp.$event.scrollPagination = debounce(ChattingApp.scrollPaging,300);
        ChattingApp.$object.scrollContainer.addEventListener('scroll', ChattingApp.$event.scrollPagination);

        $('#send-button-modalroom').click(function () {
            var messageContent = document.getElementById('messageInput').value.trim();

            var chatMessage = {
                content: messageContent,
                senderEmail: ChattingApp.$data.loginEmail
            };
            ChattingApp.$data.stompClient.send("/app/chat/"+roomId, {}, JSON.stringify(chatMessage));
        })

    });

    $('#chatMessageModal').on('hidden.bs.modal', function () {
      document.getElementById('messages-box').innerHTML = '';
      ChattingApp.$object.scrollContainer.removeEventListener('scroll', ChattingApp.$event.scrollPagination);
      ChattingApp.disConnect();
    });

});

function selectChattingUp(loginEmail, roomId ,page){
    $.ajax({
        url: '/chat/selectChatMessageUp?page=' + page,
        type: "POST",
        data: {loginEmail: loginEmail, roomId:roomId},
        dataType: "JSON",
        success: function (data){
            ChattingApp.setChattingUp(data)
        }
    });
}

function selectChattingDown(loginEmail, roomId ,page){
    $.ajax({
        url: '/chat/selectChatMessageDown?page=' + page,
        type: "POST",
        data: {loginEmail: loginEmail, roomId:roomId},
        dataType: "JSON",
        success: function (data){
            ChattingApp.setChattingDown(data)
        }
    });
}


function getNotReadMessageNum(loginEmail, roomId) {
    $.ajax({
        url: "/chat/getNotReadNum",
        type: "POST",
        dataType: "JSON",
        data: {loginEmail: loginEmail, roomId: roomId},
        success: function (result){
            if (result > 300)
                $('#num-'+roomId).html("300+");
            else if (result > 0)
                $('#num-'+roomId).html(result);
        }
    })

}
