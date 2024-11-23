document.addEventListener("DOMContentLoaded", ()=> {
    $("#friend-List").click(function (){
        $('#friend-List-UserModal').modal('show');
        var loginName = document.getElementById("loginName").dataset.loginName;
        var userName = document.getElementById("userName").dataset.userName;
        ProfileFriendApp.init(loginName, userName);


        selectFirstFriendProfile(userName, loginName)
        // then
        selectProfileFriend(ProfileFriendApp.$data.loginName, ProfileFriendApp.$data.userName, ProfileFriendApp.$data.page)

        ProfileFriendApp.$event.scrollPagination = debounce(ProfileFriendApp.scrollPaging, 300);
        ProfileFriendApp.$object.scrollContainer.addEventListener('scroll', ProfileFriendApp.$event.scrollPagination);
    })

    $('#friend-List-UserModal').on('hidden.bs.modal', function () {
        $('#friend-List-UserBox').html("");
        ProfileFriendApp.$object.scrollContainer.removeEventListener('scroll', ProfileFriendApp.$event.scrollPagination);

    })
});


function selectProfileFriend(loginName, userName, page){
    $.ajax({
        url: '/api/v1/friend/friendList?page=' + page,
        type: "POST",
        data: {userName: userName, loginName: loginName},
        dataType: "JSON",
        success: function (data){
            ProfileFriendApp.setProfileFriend(data);
        }
    });
}

function selectFirstFriendProfile(userName, loginName){
    $.ajax({
        url: "/api/v1/friend/firstList",
        type: "POST",
        data: {userName:userName, loginName:loginName},
        dataType: "JSON",
        success: function (data){
            ProfileFriendApp.setFirst(data);
        }
    })
}