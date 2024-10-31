document.addEventListener("DOMContentLoaded", ()=> {
    $("#friendship-accepted").click(function (){
        $('#friendShip-acceptedModal').modal('show');
        var loginName = document.getElementById("loginName").dataset.loginName;

        FriendApp.init(loginName);
        selectUsersInfo(FriendApp.$data.loginName, FriendApp.$data.page);

        // Make Infinite Scroll Pagination
        FriendApp.$event.scrollPagination = debounce(FriendApp.scrollPaging,300);
        FriendApp.$object.scrollContainer.addEventListener('scroll', FriendApp.$event.scrollPagination);

    })
    // Delete Modal Setting
    $('#friendShip-acceptedModal').on('hidden.bs.modal', function () {
        $('#friendship-acceptedList').html(""); // Init할 때 초기화 하는게 맞나..?
        FriendApp.$object.scrollContainer.removeEventListener('scroll', FriendApp.$event.scrollPagination);
    })
});

function selectUsersInfo(loginName, page){
    $.ajax({
        url: '/profileImage/selectAcceptUsersInfo?page='+page,
        type: "POST",
        data: {loginName:loginName},
        dataType: "JSON",
        success: function (data){
            FriendApp.setUserInfo(data);
        }
    });
}



