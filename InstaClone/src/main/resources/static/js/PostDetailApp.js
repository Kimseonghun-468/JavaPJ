const PostDetailApp = {
    $data: {
        page: 1,
        hasNext: false,
        loginName: null,
        loginEmail: null,
        userName: null,
        postId: null,
        noneImage: "/display?fileName=outprofile.png/",
    },

    $object: {
        replyTable : null,
        scrollContainer : null,
    },

    $event: {
        scrollPagination : null,
    },

    init(loginName, loginEmail, userEmail) {
        console.log("Detail App 초기화 중...");
        this.$data.page = 1;
        this.$data.loginName = loginName;
        this.$data.loginEmail = loginEmail;
        this.$data.userEmail = userEmail;

        this.$object.replyTable = $("#post-reply-container"); // ksh 바꿔야함
        this.$object.scrollContainer = document.getElementById('waitContainer'); // 너도 바꿔야
        this.scrollPaging = this.scrollPaging.bind(this);
    },

    scrollPaging(){
        var container = this.$object.scrollContainer;
        var value = container.scrollHeight - container.scrollTop
        if (container.clientHeight -15 <= value  &&  value <= container.clientHeight +15) {
            if(this.$data.hasNext)
                // selectWaitUsersInfo(this.$data.loginName, this.$data.page);
                ;
        }
    },

    setPostImageList(data){
        var carouselTag = "";
        var imageUrl = `[[${profileImageDTO.getImageURL()}]]`
        if ('[[${profileImageDTO.path}]]' == '')
            imageUrl = 'outprofile.png'
        var loadName = '[[${userName}]]';

        $.each(data.imageDTOList, function (idx, imageInfo){
            if(idx ==0)
                carouselTag += '<div class="carousel-item active">'
            else
                carouselTag += '<div class="carousel-item">'
            carouselTag += '<img src=\"/display?fileName='+imageInfo.imageURL+'/\" alt=\"Profile Picture\" class=\"post-image\">';
            carouselTag += '</div>';
        })

        $.getJSON("/getWidthAndHeight?fileName=" +(data.imageDTOList[0].imageURL), function (arr){
            console.log(arr)
            const styleBoxes = document.getElementsByClassName('modal-content-post-imagebox');
            const header = document.getElementById('post-modify-header');
            if (arr[0] < arr[1])
            {
                header.style.maxWidth = 405 + ((900 / arr[1]) * arr[0]) + 'px';
                for (let i = 0; i < styleBoxes.length; i++) {
                    styleBoxes[i].style.maxWidth = ((900 / arr[1]) * arr[0]) + 'px';
                    styleBoxes[i].style.maxHeight = '900px';
                    styleBoxes[i].style.aspectRatio = arr[0] + '/' + arr[1];
                    styleBoxes[i].style.flexBasis = ((900 / arr[1]) * arr[0]) + 'px';
                }
            }
            else{
                header.style.maxWidth = 405 + 900 +'px'
                for (let i = 0; i < styleBoxes.length; i++) {
                    styleBoxes[i].style.maxWidth = '900px'
                    styleBoxes[i].style.maxHeight = '900px';
                    styleBoxes[i].style.aspectRatio = '1/1'
                    styleBoxes[i].style.flexBasis = '900px'
                }
            }
        });

        if(data.imageDTOList.length ==1){
            $(".carousel-control-next").html("")
            $(".carousel-control-prev").html("")
        }
        else{
            $(".carousel-control-prev").html('<span class="carousel-control-prev-icon" aria-hidden="true"></span>')
            $(".carousel-control-next").html('<span class="carousel-control-next-icon" aria-hidden="true"></span>')
        }

        let postTitle = data.title.replaceAll("\n", "<br>")
        this.createTitleTag(loadName, postTitle, imageUrl, data.regDate);
        $("#carousel-image-box").html(carouselTag)
        $("#modify-carousel-image-box").html(carouselTag)
    },

    setReplyList(data){
        var str = "";
        var buttonTag = ""

        $.each(arr.dtoList, function (idx, reply){
            this.setReply(reply)
        });

        if (arr.dtoList.length == 0){
            str += '<div class="not-exist-list" id="post-not-exist">현재 댓글이 없습니다.</div>'
        }

        $("#post-reply-container").append(str);
        if (arr.totalPage >replyPage){
            buttonTag += '<div class="reply-paging-button">'
            buttonTag += '<svg aria-label="댓글 더 읽어들이기" class="x1lliihq x1n2onr6 x5n08af" fill="currentColor" height="24" role="img" viewBox="0 0 24 24" width="24"><title>댓글 더 읽어들이기</title><circle cx="12.001" cy="12.005" fill="none" r="10.5" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"></circle><line fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" x1="7.001" x2="17.001" y1="12.005" y2="12.005"></line><line fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" x1="12.001" x2="12.001" y1="7.005" y2="17.005"></line></svg>'
            buttonTag += '</div>'
        }
        $("#post-reply-container").append(buttonTag);
    },

    setReply(data){
        var str = "";
        str += '<div class="reply-main">';
        str += '<div class="reply-image-container">'
        if (data.profileImageUrl != null)
            str+= '<img src="/display?fileName=' + data.profileImageUrl +'/" class="reply-profile">';
        else
            str += '<img src="/display?fileName=outprofile.png/" class="reply-profile">'
        str += '</div>'
        str += '<div class="reply-wrapper">';
        str += '<div class="reply-name">' + data.name + '</div>';
        str += '<div class="reply-content">' + data.text + '</div>';
        str += '<div class="reply-side">';
        str += '<div class="reply-time">' + formatTimeDifference(data.regDate) + '</div>';
        if (loginName == data.name) {
            str += '<span class="options dots reply-option" data-rno="' + data.rno + '">...</span>'
        }
        str += '</div>'
        str += '</div>'
        str += '</div>'

        this.$object.replyTable.append(str)
    },

    createTitleTag(userName, title, imageUrl, regDate) {
        result = ""
        result += '<div class="reply-title">'
        result += '<img src="/display?fileName=' + imageUrl + '/" class="reply-profile">';
        result += '</div>'
        result += '<div class="reply-wrapper">'
        result += '<div class="reply-name">' + userName + '</div>'
        result += '<div class="reply-content">' + title + '</div>'
        result += '<div class="reply-side">'
        result += '<div class="reply-time">' + formatTimeDifference(regDate) + '</div>'
        result += '</div>'
        result += '</div>'
        result += '</div>'

        $("#post-title-container").html(result);
    },
}