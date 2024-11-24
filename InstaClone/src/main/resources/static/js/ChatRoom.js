document.addEventListener("DOMContentLoaded", ()=> {
    $("#chatting-roomList").click(function () {
        $('#chatroom-Modal').modal('show');

        ChatRoomApp.init(UserProfileApp.$data.loginName)

        selectChatRoom(ChatRoomApp.$data.page);

        ChatRoomApp.$event.scrollPagination = debounce(ChatRoomApp.scrollPaging, 300);
        ChatRoomApp.$object.scrollContainer.addEventListener('scroll', ChatRoomApp.$event.scrollPagination);


        $('#chatroom-Modal').on('hidden.bs.modal', function () {
            $('#chatroom-List').html(""); // Init할 때 초기화 하는게 맞나..?
            ChatRoomApp.$object.scrollContainer.removeEventListener('scroll', ChatRoomApp.$event.scrollPagination);
        })
    });
});


function selectChatRoom(page){
    $.ajax({
        url: '/chat/selectChatRoom?Page='+page,
        type: "POST",
        dataType: "JSON",
        success: function (data){
            ChatRoomApp.setChatRoom(data);
        }
    });
}



