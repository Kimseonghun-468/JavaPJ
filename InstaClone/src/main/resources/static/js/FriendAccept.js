

// FriendAcceptApp.js를 만들고, Init을 생성한다음에, 현재 Paging 값이랑, Next 값들 잘 생성해보자 후..
document.addEventListener("DOMContentLoaded", ()=> {
    $("#friendship-accepted").click(function (){
        $('#friendShip-acceptedModal').modal('show');
        var loginName = document.getElementById("loginName").dataset.loginName;
        FriendApp.init(loginName);
        selectUsersInfo(FriendApp.$data.loginName, FriendApp.$data.page);
        console.log("Click Accepted List");
    })
});


function selectUsersInfo(loginName, page){
    $.ajax({
        url: '/profileImageselectAcceptUsersInfo?page='+page,
        type: "POST",
        data: {loginName:loginName},
        dataType: "JSON",
        success: function (data){
            var friendshipResult = FriendApp.setUserInfo(data);
            $("#friendship-acceptedList").html(friendshipResult);
        }
    });
}

