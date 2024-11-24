document.addEventListener("DOMContentLoaded", ()=> {
    $("#friendship-accepted").click(function (){
        $('#friendShip-acceptedModal').modal('show');
        AceeptFriendApp.init();
        selectUsersInfo(AceeptFriendApp.$data.page);

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

function selectUsersInfo(page){
    $.ajax({
        url: '/api/v1/friend/selectAcceptUsersInfo?page='+page,
        type: "POST",
        dataType: "JSON",
        success: function (response){
            AceeptFriendApp.setUserInfo(response.data);
        }
    });
}



