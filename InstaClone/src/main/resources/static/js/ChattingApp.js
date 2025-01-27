const ChattingApp = {
    $data: {
        upPage: 1,
        downPage: 1,
        hasNextUp: false,
        hasNextDown: false,
        loginName: null,
        roomId: null,
        noneImage: "outprofile.png/",
        upDayCheck: null,
        downDayCheck: null,
        timeFormat: null,
        socket: null,
        stompClient: null,
    },

    $object: {
        waitTable : null,
        scrollContainer : null,
    },

    $event: {
        scrollPagination : null,
    },


    init(loginName, roomId) {
        console.log("Chatting App 초기화 중...");
        this.$data.loginName = loginName;
        this.$data.upPage = 1;
        this.$data.downPage = 1;
        this.$data.upDayCheck = new Date().toISOString().slice(0, 10);
        this.$data.downDayCheck = new Date().toISOString().slice(0, 10);
        this.$data.roomId = roomId
        this.$data.socket = new SockJS('/ws');
        this.$data.stompClient = Stomp.over(this.$data.socket);
        this.$object.waitTable = $("#messages-box");
        this.$object.scrollContainer = document.getElementById('messages-modal');
        this.scrollPaging = this.scrollPaging.bind(this);
        this.connect()

    },

    scrollPaging(){
        var container = this.$object.scrollContainer;
        var value = container.scrollHeight - container.scrollTop
        if (container.clientHeight -15 <= value  &&  value <= container.clientHeight +15) {
            if(this.$data.hasNextDown)
                selectChattingDown(this.$data.roomId, this.$data.downPage)
        }

        if (container.scrollTop >=0 &&  container.scrollTop <= 200) {
            if (this.$data.hasNextUp)
                selectChattingUp(this.$data.roomId, this.$data.upPage)

        }
    },

    setChattingUp(data) {

        data.chatMessageDTOS.forEach(item => {

            const chatInfo = {
                "senderName": item.senderName,
                "content": item.content,
                "regDate": item.regDate,
                "readStatus": item.readStatus,
                "profileImageUrl": item.profileImageUrl,
                "inviterName": item.inviterName,
                "inviteNames": item.inviteNames
            };

            if (this.$data.upDayCheck != item.regDate.slice(0, 10)){
                $('#messages-box').prepend('<div class="chat-date-frame"><div class="chat-date">' + this.$data.upDayCheck +'</div></div>')
                this.$data.upDayCheck = item.regDate.slice(0, 10)
            }
            this.setChatMessage(chatInfo, inverse= true)

        })

        if (data.hasNext == false)
            $('#messages-box').prepend('<div class="chat-date-frame"><div class="chat-date">' + this.$data.upDayCheck +'</div></div>')


        this.$data.hasNextUp = data.hasNext;
        this.$data.upPage += 1
    },

    setChattingDown(data) {

        data.chatMessageDTOS.forEach(item => {
            const chatInfo = {
                "senderName": item.senderName,
                "content": item.content,
                "regDate": item.regDate,
                "readStatus": item.readStatus,
                "profileImageUrl": item.profileImageUrl,
                "inviterName": item.inviterName,
                "inviteNames": item.inviteNames
            };

            if (this.$data.downDayCheck != item.regDate.slice(0, 10)){
                this.$data.downDayCheck = item.regDate.slice(0, 10);
                $('#messages-box').prepend('<div class="chat-date-frame"><div class="chat-date">' + this.$data.downDayCheck +'</div></div>');
            }

            this.setChatMessage(chatInfo, inverse= false)
        })

        this.$data.hasNextDown = data.hasNext;
        this.$data.downPage += 1
    },


    connect() {
        var roomId = this.$data.roomId
        var client = this.$data.stompClient

        client.connect({'roomId': roomId}, () => {

            client.subscribe('/topic/chat/' + roomId, (chatMessage) => {
                const item = JSON.parse(chatMessage.body);  // 한 번만 파싱
                const chatInfo = {
                    "senderName": item.senderName,
                    "content": item.content,
                    "regDate": new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, 19),
                    "readStatus": item.readStatus,
                    "profileImageUrl": item.profileImageUrl,
                    "inviterName": item.inviterName,
                    "inviteNames": item.inviteNames
                };
                this.setChatMessage(chatInfo, inverse= false)

            });

            client.subscribe('/topic/chat/accessLoad/' + roomId, (response) => {
                var result = JSON.parse(response.body)
                if (result.userName != this.$data.loginName){
                    $('.status-true').each(function() {

                        let statusTime = ($(this).attr('data-time'));
                        let statusName = ($(this).attr('data-name'));
                        let readNum = $(this).text();

                        if (statusTime > result.disConnect && statusName.equals(result.userName)){
                            $(this).text(readNum-1);
                        }
                    });
                }
            })

            client.subscribe('/topic/chat/inviteLoad/' + roomId, (result) => {
                var invitedList = JSON.parse(result.body)
                var names = ""
                invitedList.forEach(item => {
                    names += item + ', '
                })
                result = ""
                result += '<div class="invite-list-container">'
                result += '<div class="invite-user-list">' + names.slice(0, names.length-2) + '님이 초대되었습니다.</div>'
                result += '</div>'
                $('#messages-box').append(result);


            })
        });

    },

    disConnect() {
        var roomId = this.$data.roomId
        var client = this.$data.stompClient

        if (client !== null) {
            client.disconnect({'roomId': roomId});
        }

        $.ajax({
            url: "/chat/updateDisConnectCid",
            type: "POST",
            data: {roomId: roomId},
            dataType: "JSON",
            success: (response) => {

            }
        })
    },


    setChatMessage(chatInfo, inverse) {
        const tag = document.createElement("div");
        const profileImage = chatInfo.profileImageUrl ? `/display?fileName=${chatInfo.profileImageUrl}` : "/display?fileName=outprofile.png";

        const timeFormat = formatter.format(new Date(chatInfo.regDate));
        const showedTime = timeFormat.slice(21, 23) + " " + timeFormat.slice(12, 20);
        const timeFormatted = showedTime.slice(0, 8);

        if (chatInfo.inviterName != null) {
            const inviteListContainer = document.createElement('div');
            inviteListContainer.classList.add('invite-list-container');

            const inviteUserList = document.createElement('div');
            inviteUserList.classList.add('invite-user-list');

            inviteUserList.textContent = `${chatInfo.inviterName}님이 ${chatInfo.inviteNames}님을 초대하였습니다.`;

            inviteListContainer.appendChild(inviteUserList);

            if (inverse === true) {
                $('#messages-box').prepend(inviteListContainer);
            } else {
                $('#messages-box').append(inviteListContainer);
            }
            return
        }


        if (chatInfo.senderName !== this.$data.loginName) {
            tag.classList.add("message", "received");

            const chatMain = document.createElement("div");
            chatMain.classList.add("chat-main");

            const img = document.createElement("img");
            img.src = profileImage;
            img.classList.add("chat-profile");

            const chatSide = document.createElement("div");
            chatSide.classList.add("chat-side");

            const sender = document.createElement("div");
            sender.classList.add("sender");
            sender.textContent = chatInfo.senderName;

            const contentWrapper = document.createElement("div");
            contentWrapper.classList.add("content-wrapper");

            const content = document.createElement("div");
            content.classList.add("content");
            content.textContent = chatInfo.content;

            const chatlog = document.createElement("div");
            chatlog.classList.add("chatlog");

            if (chatInfo.readStatus > 0) {
                const statusTrue = document.createElement("div");
                statusTrue.classList.add("status-true");
                statusTrue.dataset.time = chatInfo.regDate;
                statusTrue.dataset.name = chatInfo.senderName;
                statusTrue.textContent = chatInfo.readStatus;
                chatlog.appendChild(statusTrue);
            }

            const timeElement = document.createElement("div");
            timeElement.classList.add("time");
            timeElement.textContent = timeFormatted;

            chatlog.appendChild(timeElement);
            contentWrapper.appendChild(content);
            contentWrapper.appendChild(chatlog);
            chatSide.appendChild(sender);
            chatSide.appendChild(contentWrapper);
            chatMain.appendChild(img);
            chatMain.appendChild(chatSide);
            tag.appendChild(chatMain);
        } else {
            tag.classList.add("message", "sent");

            const chatMain = document.createElement("div");
            chatMain.classList.add("chat-main");

            const contentWrapper = document.createElement("div");
            contentWrapper.classList.add("content-wrapper");

            const chatSide = document.createElement("div");
            chatSide.classList.add("chat-side");

            const sender = document.createElement("div");
            sender.classList.add("sender");
            sender.textContent = chatInfo.senderName;

            const chatSideAdd = document.createElement("div");
            chatSideAdd.classList.add("chat-side-add");

            const chatlog = document.createElement("div");
            chatlog.classList.add("chatlog");

            if (chatInfo.readStatus > 0) {
                const statusTrue = document.createElement("div");
                statusTrue.classList.add("status-true");
                statusTrue.dataset.time = chatInfo.regDate;
                statusTrue.dataset.name = chatInfo.senderName;
                statusTrue.textContent = chatInfo.readStatus;
                chatlog.appendChild(statusTrue);
            }

            const timeElement = document.createElement("div");
            timeElement.classList.add("time");
            timeElement.textContent = timeFormatted;

            chatlog.appendChild(timeElement);
            chatSideAdd.appendChild(chatlog);

            const content = document.createElement("div");
            content.classList.add("content");
            content.textContent = chatInfo.content;

            chatSideAdd.appendChild(content);
            chatSide.appendChild(sender);
            chatSide.appendChild(chatSideAdd);
            contentWrapper.appendChild(chatSide);

            const img = document.createElement("img");
            img.src = profileImage;
            img.classList.add("chat-profile");

            chatMain.appendChild(contentWrapper);
            chatMain.appendChild(img);
            tag.appendChild(chatMain);
        }

        if (inverse === true)
            $('#messages-box').prepend(tag);
        else
            $('#messages-box').append(tag);
    }




}