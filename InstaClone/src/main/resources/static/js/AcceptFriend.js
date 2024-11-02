document.addEventListener("DOMContentLoaded", ()=> {
    $("#friendship-accepted").click(function (){
        $('#friendShip-acceptedModal').modal('show');
        var loginName = document.getElementById("loginName").dataset.loginName;

        AceeptFriendApp.init(loginName);
        selectUsersInfo(AceeptFriendApp.$data.loginName, AceeptFriendApp.$data.page);

        // Make Infinite Scroll Pagination
        AceeptFriendApp.$event.scrollPagination = debounce(AceeptFriendApp.scrollPaging,300);
        AceeptFriendApp.$object.scrollContainer.addEventListener('scroll', AceeptFriendApp.$event.scrollPagination);

    })
    // Delete Modal Setting
    $('#friendShip-acceptedModal').on('hidden.bs.modal', function () {
        $('#friendship-acceptedList').html(""); // Init할 때 초기화 하는게 맞나..?
        AceeptFriendApp.$object.scrollContainer.removeEventListener('scroll', AceeptFriendApp.$event.scrollPagination);
    })
});

function selectUsersInfo(loginName, page){
    $.ajax({
        url: '/profileImage/selectAcceptUsersInfo?page='+page,
        type: "POST",
        data: {loginName:loginName},
        dataType: "JSON",
        success: function (data){
            AceeptFriendApp.setUserInfo(data);
        }
    });
}



