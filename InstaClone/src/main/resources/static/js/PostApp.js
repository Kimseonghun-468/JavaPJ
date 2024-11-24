const PostApp = {
    $data: {
        page: 1,
        hasNext: false,
        loginName: null,
        userName: null,
        formData: null,
        uploadImages : [],
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
        data.postDTOS.forEach(item => {
            postResult += '<div class="post-item">';
            postResult += '<img class="post-list-img" ' +
                'src="/display?fileName=' + item.imageDTOList[0].thumbnailURL + '/" ' +
                'data-reply-num="' +item.replyNum + '" ' +
                'data-like-num="' +item.likeNum+ '" ' +
                'data-pid="' + item.pno + '">'

            postResult += '<div class="info-overlay">';
            postResult += '<span class="like-num">like' + item.likeNum +'</span>';
            postResult += '<br>';
            postResult += '<span class="reply-num">reply' + item.replyNum + '</span>';
            postResult += '</div>';

            postResult += '</div>';
        });

        this.$data.page += 1
        this.$data.hasNext = data.hasNext;
        this.$object.postTable.append(postResult)
    },

    setFormData(){
        this.$data.formData = new FormData();
        var files = $("input.post-Image-File")[0].files;

        for (var i = 0; i < files.length; i++){
            this.$data.formData.append("uploadFiles",  files[i]);
        }
    },

    setUploadForm(data){
        var str = "";
        var carouselTag = "";


        data.forEach((imageInfo, idx) => {
            this.$data.uploadImages.push({
                "imgName": imageInfo['fileName'],
                "path": imageInfo['folderPath'],
                "uuid": imageInfo['uuid']
            });

            if(idx ==0)
                carouselTag += '<div class="carousel-item active">'
            else
                carouselTag += '<div class="carousel-item">'
            carouselTag += '<img src=\"/display?fileName='+imageInfo.imageURL+'/\" alt=\"Profile Picture\" class=\"post-image\">';
            carouselTag += '</div>';
        })

        $.getJSON("/getWidthAndHeight?fileName=" +(data[0].imageURL), function (arr){
            console.log(arr)
            const styleBoxes = document.getElementById('upload-image-container');
            const header = document.getElementById('post-upload-header');
            if (arr[0] < arr[1])
            {
                header.style.maxWidth = 405 + ((900 / arr[1]) * arr[0]) + 'px';
                styleBoxes.style.maxWidth = ((900 / arr[1]) * arr[0]) + 'px';
                styleBoxes.style.maxHeight = '900px';
                styleBoxes.style.aspectRatio = arr[0] + '/' + arr[1];
                styleBoxes.style.flexBasis = ((900 / arr[1]) * arr[0]) + 'px';
            }
            else{
                header.style.maxWidth = 405 + 900 + 'px';
                styleBoxes.style.maxWidth = '900px'
                styleBoxes.style.maxHeight = '900px';
                styleBoxes.style.aspectRatio = '1/1'
                styleBoxes.style.flexBasis = '900px'
            }
        });

        if(data.length ==1){
            $(".carousel-control-next").html("")
            $(".carousel-control-prev").html("")
        }
        else{
            $(".carousel-control-prev").html('<span class="carousel-control-prev-icon" aria-hidden="true"></span>')
            $(".carousel-control-next").html('<span class="carousel-control-next-icon" aria-hidden="true"></span>')
        }

        $("#upload-form-image-box").html(str);
        $("#upload-carousel-image-box").html(carouselTag);
    }

}