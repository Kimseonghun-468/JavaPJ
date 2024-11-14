document.addEventListener("DOMContentLoaded", ()=> {
    var userName = document.getElementById("userName").dataset.userName;

    UserProfileApp.init(userName);

    selectUserInfo(UserProfileApp.$data.userName);
})

function selectUserInfo(userName){
    $.ajax({
        url: '/selectUserInfo',
        type: "POST",
        data: {userName: userName},
        dataType: "JSON",
        success: function (data){
            UserProfileApp.setUserInfo(data);
        }
    })
}