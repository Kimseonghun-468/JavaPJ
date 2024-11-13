const PostApp = {
    $data: {
        page: 1,
        hasNext: false,
        loginName: null,
        userName: null,
    },

    $object: {
        postTable: null,
        scrollContainer: null,

    },

    $event: {
        scrollPagination: null,
    },

    init(loginName, userName) {
        console.log("Post App 초기화 중...");
        this.$data.page = 1;
        this.$data.loginName = loginName;
        this.$data.userName = userName;
        this.$object.postTable = $("#post-grid-image-box");
        this.$object.scrollContainer = document.getElementById('post-frame');
        this.scrollPaging = this.scrollPaging.bind(this);
    },


    scrollPaging(){
        var container = this.$object.scrollContainer;
        var value = container.scrollHeight - container.scrollTop
        if (container.clientHeight -15 <= value  &&  value <= container.clientHeight +15) {
            if(this.$data.hasNext)
                selectPostList(this.$data.userName, this.$data.page)
        }
    },

    setPostList(data){
        let postResult = "";
        data.dtoList.forEach(item => {
            postResult += '<div class="post-item">';
            postResult += '<img class="post-list-img" ' +
                'src="/display?fileName=' + item.imageDTOList[0].imageURL + '/" ' +
                'data-url ="' + item.imageDTOList[0].imageURL +'" ' +
                'data-id="' +item.imageDTOList[0].pino + '" ' +
                'data-pid="' + item.pno + '">'
            postResult += '</div>';
        });

        $("#post-grid-image-box").append(postResult);
    },

}