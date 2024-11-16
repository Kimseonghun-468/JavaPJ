const options = {
    year: 'numeric', month: '2-digit', day: '2-digit',
    hour: '2-digit', minute: '2-digit', second: '2-digit',
    hour12: true,
    timeZone: "Asia/Seoul"  // 서버 시간대가 UTC라고 가정
};
const formatter = new Intl.DateTimeFormat('en-US', options);

function formatTimeDifference(pastTime) {
    const currentTime = new Date();
    const pastDate = new Date(pastTime);
    const diffInMilliseconds = currentTime - pastDate;
    const diffInMinutes = Math.floor(diffInMilliseconds / 1000 / 60);
    const diffInHours = Math.floor(diffInMinutes / 60);
    const diffInDays = Math.floor(diffInHours / 24);

    if (diffInDays > 0) {
        return `${diffInDays}일 전`;
    } else if (diffInHours > 0) {
        return `${diffInHours}시간 전`;
    } else if (diffInMinutes > 0) {
        return `${diffInMinutes}분 전`;
    } else {
        return `방금 전`;
    }
}

function debounce(func, wait) {
    let timeout;
    return function () {
        clearTimeout(timeout);
        timeout = setTimeout(func, wait);
    }
}

$(document).on('click', '.request-Follow-button', function (){
    var loadName = '[[${userName}]]';
    var name = $(this).attr('data-name');
    var $button = $(this);
    sendFollowRequest(name, $button);
});

$(document).on('click', '.request-Accept-button', function(){
    var loginName = '[[${memberDTO.name}]]';
    var requesterName = $(this).attr('data-name');
    var $button = $(this);
    sendAcceptFriendShipRequest(loginName, requesterName, $button);
})

$(document).on('click', '.request-Delete-button', function (){
    var loginName = '[[${memberDTO.name}]]';
    var requesterName = $(this).attr('data-name');
    var $button = $(this);
    sendDeleteFriendShipRequest(loginName, requesterName, $button)
})

function sendDeleteFriendShipRequest(loginName, requesterName, $button){
    var data = {loginName:loginName, requesterName:requesterName};
    $.ajax({
        url: '/friendShip/deleteFriendShipRequest',
        type: "POST",
        data: data,
        dataType: "TEXT",
        success: function (result){
            $button.text("삭제 완료");
            $button.prop('disabled, true');
        }
    })
}

function sendAcceptFriendShipRequest(loginName, requesterName, $button){
    var data = {loginName:loginName, requesterName:requesterName};
    $.ajax({
        url: '/friendShip/acceptFriendShipRequest',
        type: "POST",
        data: data,
        dataType: "TEXT",
        success: function (result) {
            $button.text('친구 수락 완료');
            $button.prop('disabled', true);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
        }
    })
}

function sendFollowRequest(name, $button){
    $.ajax({
        url: '/friendShip/' + name,
        type: 'POST',
        dataType: 'text',
        success: function (arr){
            $button.text("친구 요청 완료");
            $button.prop("disabled", true);
        }
    })
}