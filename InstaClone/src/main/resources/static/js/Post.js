document.addEventListener("DOMContentLoaded", ()=> {
    var loginName = document.getElementById("loginName").dataset.loginName;
    var userName = document.getElementById("userName").dataset.userName;

    PostApp.init(loginName, userName);

    selectPostList(PostApp.$data.userName, PostApp.$data.page)

    PostApp.$event.scrollPagination = debounce(PostApp.scrollPaging,300);
    PostApp.$object.scrollContainer.addEventListener('scroll', PostApp.$event.scrollPagination);

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
