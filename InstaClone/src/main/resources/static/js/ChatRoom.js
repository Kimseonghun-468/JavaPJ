document.addEventListener("DOMContentLoaded", ()=> {
    $("#chatting-roomList").click(function () {
        $('#chatroom-Modal').modal('show');
        var loginEmail = document.getElementById("loginEmail").dataset.loginEmail;
        var loginName = document.getElementById("loginName").dataset.loginName;
        ChatRoomApp.init(loginEmail, loginName)

        selectChatRoom(ChatRoomApp.$data.loginEmail, ChatRoomApp.$data.page);

        ChatRoomApp.$event.scrollPagination = debounce(ChatRoomApp.scrollPaging, 300);
        ChatRoomApp.$object.scrollContainer.addEventListener('scroll', ChatRoomApp.$event.scrollPagination);


        $('#chatroom-Modal').on('hidden.bs.modal', function () {
            $('#chatroom-List').html(""); // Init할 때 초기화 하는게 맞나..?
            ChatRoomApp.$object.scrollContainer.removeEventListener('scroll', ChatRoomApp.$event.scrollPagination);
        })
    });
});


function selectChatRoom(loginEmail, page){
    $.ajax({
        url: '/chat/selectChatRoom?Page='+page,
        type: "POST",
        data: {loginEmail:loginEmail},
        dataType: "JSON",
        success: function (data){
            ChatRoomApp.setChatRoom(data);
        }
    });
}



