const UserProfileApp = {
    $data: {
        userName: null,
        userEmail: null,
        loginName: null,
        loginEmail: null,
        profileImage: "/display?fileName=outprofile.png/",

        formData: null,
    },

    $object: {
        profileTable: null,
    },

    $event: {
    },

    init(userName, loginName) {
        this.$data.loginName = loginName;
        this.$data.userName = userName;
        this.$object.profileTable = $("#profile-table")
        selectUserInfo(userName)
        selectPostNum(userName)
        selectFriendNum(userName)
    },

    setUserInfo(data){
        this.$data.userEmail = data.userEmail;
        if(data.path != null)
            this.$data.profileImage = "/display?fileName=" + data.imageURL;
    },

    setFormData(files){
        this.$data.formData = new FormData();

        this.$data.formData.append("uploadFiles", files[0]);
    },


    insertProfile(result){
        var str = "";
        result.forEach((item, index) => {
            str += "<input type='hidden' name='imgName' value='" + item['fileName'] + "'>";
            str += "<input type='hidden' name='path' value='" + item['folderPath'] + "'>";
            str += "<input type='hidden' name='uuid' value='" + item['uuid'] + "'>";
            str += "<input type='hidden' name='userName' value='" + this.$data.userName + "'>";
            str += "<input type='hidden' name='userEmail' value='" + '[[${memberDTO.email}]]' + "'>";
        })
        $("#profile-box").html(str);
        $("#Profile-Image").submit();
    },

    setProfile() {
        var result = "";
        if (this.$data.userName == this.$data.loginName) {  // 비교 연산자를 사용
            result += '<input type="file" class="fileInput profile-Image-File"\n' +
                '                   accept=".png, .jpg, .jpeg" style="display: none;" multiple/>';

        }
        result += '<img src="' + this.$data.profileImage + '" alt="Profile Picture" ' +
            'class="profile-picture uploadButton-profile" style="width: 150px; height: 150px;" />';
        this.$object.profileTable.html(result);
    },


    setReplyNum(data) {
        $("#post-num").html('게시물 ' + data)
    },

    setFriendNum(data) {
        $("#friend-List").html("친구 " + data)
    }

}