const UserProfileApp = {
    $data: {
        userName: null,
        userEmail: null,
        profileImage: "/display?fileName=outprofile.png/",
    },

    $object: {
    },

    $event: {
    },

    init(userName) {
        this.$data.userName = userName;
        selectUserInfo(userName)
    },

    setUserInfo(data){
        this.$data.userEmail = data.userEmail;
        this.$data.profileImage = data.imageURL;


    }


}