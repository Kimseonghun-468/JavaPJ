document.addEventListener("DOMContentLoaded", ()=> {
    $("#friendship-requested").click(function () {
        $('#friendShip-requestedModal').modal('show');

        WaitFriendApp.init(UserProfileApp.$data.loginName);

        selectWaitUsersInfo(WaitFriendApp.$data.page);

        // Make Infinite Scroll Pagination
        WaitFriendApp.$event.scrollPagination = debounce(WaitFriendApp.scrollPaging,300);
        WaitFriendApp.$object.scrollContainer.addEventListener('scroll', WaitFriendApp.$event.scrollPagination);


        $('#friendShip-requestedModal').on('hidden.bs.modal', function () {
            $('#friendship-requestedList').html(""); // Init할 때 초기화 하는게 맞나..?
            WaitFriendApp.$object.scrollContainer.removeEventListener('scroll', WaitFriendApp.$event.scrollPagination);
        })
    });
});

function selectWaitUsersInfo(page){
    $.ajax({
        url: '/api/v1/friend/selectWaitingFriend?page='+page,
        type: "POST",
        dataType: "JSON",
        success: function (data){
            WaitFriendApp.setUserInfo(data);
        }
    });
}