const PostDetailApp = {
    $data: {
        page: 1,
        hasNext: false,
        loginName: null,
        userName: null,
        postId: null,
        noneImage: "/display?fileName=outprofile.png/",
    },

    $object: {
        replyTable : null,
        postHeader: null,
    },

    $event: {
        scrollPagination : null,
    },

    init(loginName, userName) {
        console.log("Detail App 초기화 중...");
        this.$data.page = 1;
        this.$data.loginName = loginName;
        this.$data.userName = userName;
        this.$object.replyTable = $("#post-reply-container"); // ksh 바꿔야함
        this.$object.postHeader = $("#post-header");
    },

    setPostDetail(data){
        var carouselTag = "";
        var imageUrl = UserProfileApp.$data.profileImage

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
        this.createTitleTag(this.$data.loginName, postTitle, imageUrl, data.regDate);
        this.setPostHeader(this.$data.userName, this.$data.loginName)
        $("#carousel-image-box").html(carouselTag)
        $("#modify-carousel-image-box").html(carouselTag)

    },

    setReplyList(data){
        var str = "";
        var buttonTag = ""

        data.replyDTOS.forEach(item => {
            this.setReply(item)
        })

        this.$data.hasNext = data.hasNext;
        this.$data.page += 1
        $("#post-reply-container").append(str);
        if (this.$data.hasNext == true){
            buttonTag += '<div class="reply-paging-button">'
            buttonTag += '<svg aria-label="댓글 더 읽어들이기" class="x1lliihq x1n2onr6 x5n08af" fill="currentColor" height="24" role="img" viewBox="0 0 24 24" width="24"><title>댓글 더 읽어들이기</title><circle cx="12.001" cy="12.005" fill="none" r="10.5" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"></circle><line fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" x1="7.001" x2="17.001" y1="12.005" y2="12.005"></line><line fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" x1="12.001" x2="12.001" y1="7.005" y2="17.005"></line></svg>'
            buttonTag += '</div>'
        }
        $("#post-reply-container").append(buttonTag);

        if (data.replyDTOS.length < 1){
            str += '<div class="not-exist-list" id="post-not-exist">현재 댓글이 없습니다.</div>'
        }
    },

    setReply(data){
        var str = "";
        str += '<div class="reply-main">';
        str += '<div class="reply-image-container">'

        str+= '<img src='+ (data.userInfoDTO.imgName ? `/display?fileName=${data.userInfoDTO.imageURL}` : this.$data.noneImage) +' class="reply-profile">';

        str += '</div>'
        str += '<div class="reply-wrapper">';
        str += '<div class="reply-name">' + data.userInfoDTO.userName + '</div>';
        str += '<div class="reply-content">' + data.text + '</div>';
        str += '<div class="reply-side">';
        str += '<div class="reply-time">' + formatTimeDifference(data.regDate) + '</div>';
        if (this.$data.loginName == data.userInfoDTO.userName) {
            str += '<span class="options dots reply-option" data-rno="' + data.rno + '">...</span>'
        }
        str += '</div>'
        str += '</div>'
        str += '</div>'

        this.$object.replyTable.append(str)
    },

    insertReply(data){
        var str = "";
        str += '<div class="reply-main">';
        str += '<div class="reply-image-container">'

        str+= '<img src='+ (UserProfileApp.$data.profileImage) +' class="reply-profile">';

        str += '</div>'
        str += '<div class="reply-wrapper">';
        str += '<div class="reply-name">' + UserProfileApp.$data.loginName + '</div>';
        str += '<div class="reply-content">' + data.text + '</div>';
        str += '<div class="reply-side">';
        str += '<div class="reply-time">' + formatTimeDifference(Date.now()) + '</div>';
        if (this.$data.loginName == UserProfileApp.$data.loginName) {
            str += '<span class="options dots reply-option" data-rno="' + data.rno + '">...</span>'
        }
        str += '</div>'
        str += '</div>'
        str += '</div>'

        this.$object.replyTable.prepend(str)
    },

    createTitleTag(userName, title, imageUrl, regDate) {
        var result = ""
        result += '<div class="reply-title">'
        result += '<img src="' + imageUrl + '" class="reply-profile">';
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

    setPostHeader(userName, loginName) {
        var imageContainer = document.querySelector('.reply-image-container');

        imageContainer.innerHTML = '<img src="' + UserProfileApp.$data.profileImage + '" class="reply-profile" />';

        var nameContainer = document.querySelector('.reply-wrapper');

        nameContainer.innerHTML = '<div class="reply-name">'+ userName +'</div>';

        var commentHeader = document.querySelector('#post-option');

        // 옵션 추가 (사용자 이름이 일치할 때)
        if (loginName === userName) {
            commentHeader.innerHTML = '<span class="options" id="post-option" style="margin-right: 16px; font-size: 28px;">...</span>'
        }
    },

}