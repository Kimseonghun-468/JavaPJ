document.addEventListener("DOMContentLoaded", ()=> {
    var loginName = document.getElementById("loginName").dataset.loginName;
    var userName = document.getElementById("userName").dataset.userName;

    PostApp.init(loginName, userName);

    selectPostList(PostApp.$data.userName, PostApp.$data.page)

    PostApp.$event.scrollPagination = debounce(PostApp.scrollPaging,300);
    PostApp.$object.scrollContainer.addEventListener('scroll', PostApp.$event.scrollPagination);


    $('.uploadButton-post').click(function() {
        $('.post-Image-File').off('change');
        $('.post-Image-File').click().on('change', function() {
            if (this.files && this.files.length > 0) {
                $('#post-upload-modal').modal('show');
                PostApp.setFormData()
                uploadPostImage(PostApp.$data.formData)
            }
        })
    })

    $('#upload-post-button').click(function() {
        postRequest = {
            "loginName" : UserProfileApp.$data.userName,
            "loginEmail" : UserProfileApp.$data.userEmail,
            "title" : document.getElementById('post-title').value,
            "uploadImages" : PostApp.$data.uploadImages,
        }
        insertPost(postRequest)
        alert('포스트 파일이 제출되었습니다.');

    });

})

function selectPostList(userName, page){
    $.ajax({
        url: "/post/getPostInfo?page=" + page,
        type: "POST",
        data: {userName: userName},
        dataType: "JSON",
        success: function (data){
            PostApp.setPostList(data);
        }
    })
}

function uploadPostImage(formData){
    $.ajax({
        url: '/uploadAjax',
        processData: false,
        contentType: false,
        data: formData,
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            PostApp.setUploadForm(data);
        },
    });
}

function insertPost(postRequest){

    sendData = {
        "email" : postRequest.loginEmail,
        "name" : postRequest.loginName,
        "title" : postRequest.title,
        "imageDTOList" : postRequest.uploadImages
    }

    $.ajax({
        url: "/post/insertPost",
        type: "POST",
        data: JSON.stringify(sendData),
        dataType: "JSON",
        contentType: "application/json",
        success: function (data){

        }
    })
}





