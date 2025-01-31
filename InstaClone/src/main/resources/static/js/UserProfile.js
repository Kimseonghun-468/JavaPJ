document.addEventListener("DOMContentLoaded", ()=> {
    var userName = document.getElementById("userName").dataset.userName;
    var loginName = document.getElementById("loginName").dataset.loginName;

    UserProfileApp.init(userName, loginName)

    $(document).on('click', '.uploadButton-profile', function () {
        if (UserProfileApp.$data.loginName == UserProfileApp.$data.userName)
            $('#profile-option-modal').modal('show');
    });
    $(document).on('click', '#upload-profile-image', function () {
        $('.profile-Image-File').off('change');
        $('.profile-Image-File').click().on('change', function (){
            if (this.files && this.files.length > 0) {
                $('.Profile-File-Modal').css('z-index', 1060).modal('show');
                var files = $("input.profile-Image-File")[0].files;
                UserProfileApp.setFormData(files);
            }
        })
    });

    $(document).on('click', '.submitFile-Profile', function () {
        uploadProfile(UserProfileApp.$data.formData);
        alert('프로필 파일이 제출되었습니다.');
    });

    $(document).on('click', '#cancel-profile-image', function () {
        $.ajax({
            url: "/profileImage/deleteProfile",
            type: "POST",
            success: function () {
                self.location.reload();
            }
        });
    });

    $(document).on('click', ".profile-picture", function (){
        if (UserProfileApp.$data.loginName == UserProfileApp.$data.userName) {
            $('#profileImageModal').modal('show');
            var imageUrl = $(this).attr('src');
            var str = '<img src="' + imageUrl + '" alt="Profile Picture" class="post-image">';
            $("#modal-content-profile-imagebox").html(str);
        }
    });
})

function selectUserInfo(userName){
    $.ajax({
        url: '/member/selectUserInfo',
        type: "POST",
        data: {userName: userName},
        dataType: "JSON",
        success: function (response){
            UserProfileApp.setUserInfo(response.data);
            UserProfileApp.setProfile();
        }
    })
}

function uploadProfile(formData){
    $.ajax({
        url: '/uploadAjax',
        processData: false,
        contentType: false,
        data: formData,
        type: 'POST',
        dataType: 'json',
        success: function (response){
            UserProfileApp.insertProfile(response)
        }
    })
}

function selectPostNum(userName){
    $.ajax({
        url: '/post/selectPostNum',
        type: "POST",
        data: {userName: userName},
        dataType: "JSON",
        success: function (response){
            UserProfileApp.setReplyNum(response.data)
        }
    })
}

function selectFriendNum(userName){
    $.ajax({
        url: '/api/v1/friend/selectFriendNum',
        type: "POST",
        data: {userName: userName},
        dataType: "JSON",
        success: function (response){
            UserProfileApp.setFriendNum(response.data)
        }
    })

}
