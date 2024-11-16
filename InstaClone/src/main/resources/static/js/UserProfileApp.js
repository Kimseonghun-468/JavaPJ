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
    },

    $event: {
    },

    init(userName, loginName) {
        this.$data.loginName = loginName;
        this.$data.userName = userName;
        selectUserInfo(userName)
    },

    setUserInfo(data){
        this.$data.userEmail = data.userEmail;
        this.$data.profileImage = data.imageURL;


    },

    setFormData(){
        this.$data.formData = new FormData();
        var files = $("input.profile-Image-File")[0].files;
        this.$data.formData.append("uploadFiles", files[0]);
    },


    setProfile(){
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
    }
}