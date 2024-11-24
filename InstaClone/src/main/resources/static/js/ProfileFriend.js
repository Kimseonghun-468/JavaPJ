document.addEventListener("DOMContentLoaded", ()=> {
    $("#friend-List").click(function (){
        $('#friend-List-UserModal').modal('show');
        var loginName = document.getElementById("loginName").dataset.loginName;
        var userName = document.getElementById("userName").dataset.userName;
        ProfileFriendApp.init(loginName, userName);


        selectFirstFriendProfile(ProfileFriendApp.$data.userName)
        selectProfileFriend(ProfileFriendApp.$data.userName, ProfileFriendApp.$data.page)

        ProfileFriendApp.$event.scrollPagination = debounce(ProfileFriendApp.scrollPaging, 300);
        ProfileFriendApp.$object.scrollContainer.addEventListener('scroll', ProfileFriendApp.$event.scrollPagination);
    })

    $('#friend-List-UserModal').on('hidden.bs.modal', function () {
        $('#friend-List-UserBox').html("");
        ProfileFriendApp.$object.scrollContainer.removeEventListener('scroll', ProfileFriendApp.$event.scrollPagination);

    })
});


function selectProfileFriend(userName, page){
    $.ajax({
        url: '/api/v1/friend/selectUserFriendsInfo?page=' + page,
        type: "POST",
        data: {userName: userName},
        dataType: "JSON",
        success: function (response){
            ProfileFriendApp.setProfileFriend(response.data);
        }
    });
}

function selectFirstFriendProfile(userName){
    $.ajax({
        url: "/api/v1/friend/selectFristFriend",
        type: "POST",
        data: {userName:userName},
        dataType: "JSON",
        success: function (response){
            ProfileFriendApp.setFirst(response.data);
        }
    })
}