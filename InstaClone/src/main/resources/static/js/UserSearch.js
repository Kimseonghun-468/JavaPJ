document.addEventListener("DOMContentLoaded", ()=> {
    $("#search-user").click(function (){
        $('#SearchModal').css('z-index', 1060).modal('show');
        $('#searchResult').append('<div class="not-exist-list">사용자를 검색해주세요.</div>')

        UserSearchApp.init(UserProfileApp.$data.loginName);

        UserSearchApp.$event.scrollPagination = debounce(UserSearchApp.scrollPaging,300);
        UserSearchApp.$object.scrollContainer.addEventListener('scroll', UserSearchApp.$event.scrollPagination);


        // Make Search Event
        document.getElementById('searchForm').onsubmit = function(event) {
            event.preventDefault();
            UserSearchApp.$data.searchName = document.getElementById('searchInput').value;
            document.getElementById('searchInput').value = "";
            $("#searchResult").html("");
            UserSearchApp.$data.page = 1
            selectSearchUserInfo(UserSearchApp.$data.loginName, UserSearchApp.$data.searchName, UserSearchApp.$data.page);

        }
    });

    $('#SearchModal').on('hidden.bs.modal', function () {
        $("#searchResult").html("");
        UserSearchApp.$object.scrollContainer.removeEventListener('scroll', UserSearchApp.$event.scrollPagination);
    });

})

function selectSearchUserInfo(loginName, searchName, page){

    $.ajax({
        url: '/search/name/all?page='+page,
        type: "POST",
        data: {loginName:loginName, searchName:searchName},
        dataType: "JSON",
        success: function (data){
            UserSearchApp.setSearchUserInfo(data);
        }
    });
}
