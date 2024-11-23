document.addEventListener("DOMContentLoaded", ()=> {
    $(document).on('click', '.post-list-img',function() {
        $('#postModal').modal('show');

        PostDetailApp.init(UserProfileApp.$data.loginName);

        var commentBox = document.querySelector('#reply-box');
        commentBox.addEventListener('input', handleInput)
        commentBox.addEventListener('keydown', handleKeyDown);

        PostDetailApp.$data.postId = $(this).data('pid');
        selectPostImageList(PostDetailApp.$data.postId);
        selectReplyList(PostDetailApp.$data.postId, PostDetailApp.$data.page);

    });
    $(document).on('click', '.reply-paging-button', function (){
        selectReplyList(PostDetailApp.$data.postId, PostDetailApp.$data.page);
        this.remove()
    })


    $('#reply-send').off('click').on('click', function (){
        var commentBox = document.querySelector('#reply-box');
        if (commentBox.value.trim() === "")
            return ;
        insertReply(PostDetailApp.$data.postId, commentBox, UserProfileApp.$data.userEmail);
        $('#post-not-exist').html("")
    });

    $('#postModal').on('click', '#post-option', function (){
        $('#post-option-modal').css('z-index', 1051).modal('show');
        $('#remove-post').off('click').on('click', function (){
            deletePost(PostDetailApp.$data.postId);
            $('#post-option-modal').modal('hide');
            $('#postModal').modal('hide')
            alert("삭제가 완료되었습니다.")
            self.location.reload();
        });
    })
    $('#cancel-post').on('click', function (){
        $('#post-option-modal').modal('hide');
    })

    $('#postModal').on('hidden.bs.modal', function () {
        $('#post-reply-container').html("");
        $('#reply-box').val('');
        // $('#reply-send').removeClass("active")
        var commentBox = document.querySelector('#reply-box');
        commentBox.removeEventListener('input', handleInput)
        commentBox.removeEventListener('keydown', handleKeyDown)
    });

    $(document).on('click', '#post-modify-button',function() {
        $('#post-option-modal').modal('hide')
        $('#post-modify-modal').modal('show')

        $('#modify-post-button').click(function() {
            postRequest = {
                "loginName" : UserProfileApp.$data.userName,
                "loginEmail" : UserProfileApp.$data.userEmail,
                "postId" : PostDetailApp.$data.postId,
                "title" : document.getElementById('post-update-title').value,
            }

            updatePost(postRequest)
            alert('포스트 파일이 수정되었습니다.');

        });
    })

    $('#myModal').on('click', '.reply-option', function() {
        console.log('reply-option event');
        var rno = $(this).data("rno");
        $('#reply-option-modal').css('z-index', 1051).modal('show');
        $('#remove-reply').off('click').on('click', function(){
            deleteReply(rno);
            $('#reply-option-modal').modal('hide');
        });
        $('#cancel-reply').off('click').on('click' ,function() {
            $('#reply-option-modal').modal('hide');
        });
    });

});

function selectPostImageList(postId){
    $.ajax({
        url:'/post/postInfoWithImage',
        type:"POST",
        data: {pno:postId},
        dataType:"JSON",
        success: function (response) {
            PostDetailApp.setPostImageList(response.data)
        }
    })
}

function selectReplyList(postId, page){
    $.ajax({
        url:'/reply/selectReplyList?page=' +page,
        type:"POST",
        data: {pno:postId},
        dataType:"JSON",
        success: function (data){
            PostDetailApp.setReplyList(data);
        }
    })
}

function insertReply(postId, commentBox, userEmail){
    var replyText = commentBox.value.replaceAll('\n', '<br>');
    var data = {pno:postId, text:replyText, email:userEmail};
    $.ajax({
        url: '/reply/'+postId,
        type: "POST",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function (data){
            PostDetailApp.setReply(data);
            $('#reply-box').val('');
            $('#reply-comment-body').scrollTop(0);
        }
    })
}

function deletePost(pno){
    $.ajax({
        url: '/post/delete/' + pno,
        type: 'DELETE',
        success: function (response){
        }
    })
}

function deleteReply(rno){
    $.ajax({
        url: '/reply/'+rno,
        type: 'DELETE',
        contentType: "application/json; charset=utf-8",
        dataType: "text",
        success: function (data){
            $('[data-rno="' + rno + '"]').closest('.reply-main').remove();
        }
    })
}

function handleInput(){
    var text = commentBox.value.trim()
    var button = document.querySelector('#reply-send')

    if (text === ""){
        button.classList.remove('active')
    }
    else {
        button.classList.add('active')
    }
}
function handleKeyDown(event){

    if (!event.isComposing && event.key === 'Enter' && !event.shiftKey) {
        event.preventDefault();
        document.getElementById('reply-send').click();
        console.log(event.key)
        console.log(event.shiftKey)
    }
    console.log('!', event.isComposing)
}

function updatePost(postReuest){
    sendData = {
        "email" : postRequest.loginEmail,
        "name" : postRequest.loginName,
        "title" : postRequest.title,
        "pno" : postReuest.postId
    }

    $.ajax({
        url: "/post/updatePost",
        type: "POST",
        data: JSON.stringify(sendData),
        dataType: "JSON",
        contentType: "application/json",
        success: function (response){

        }
    })
}






