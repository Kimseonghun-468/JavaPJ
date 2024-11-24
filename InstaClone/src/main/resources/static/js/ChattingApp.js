const ChattingApp = {
    $data: {
        upPage: 1,
        downPage: 1,
        hasNextUp: false,
        hasNextDown: false,
        loginName: null,
        loginEmail: null,
        roomId: null,
        noneImage: "/display?fileName=outprofile.png/",
        nameAndEmailDict : {},
        emailAndNameDict : {},
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


    init(loginName, loginEmail, roomId) {
        console.log("Chatting App 초기화 중...");
        this.$data.loginName = loginName;
        this.$data.loginEmail = loginEmail;
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
        this.makeUserDict(roomId)
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
            const messageData = item;
            const senderName = this.$data.emailAndNameDict[messageData.senderEmail]
            const content = messageData.content;
            const regDate = messageData.regDate;
            const readStatus = messageData.readStatus;
            const profileImageUrl = messageData.profileImageUrl;
            if (this.$data.upDayCheck != regDate.slice(0, 10)){
                $('#messages-box').prepend('<div class="chat-date-frame"><div class="chat-date">' + this.$data.upDayCheck +'</div></div>')
                this.$data.upDayCheck = regDate.slice(0, 10)
            }
            this.setChatMessage(senderName, content, readStatus, regDate, profileImageUrl, inverse= true)

        })
        if (data.hasNext == false)
            $('#messages-box').prepend('<div class="chat-date-frame"><div class="chat-date">' + this.$data.upDayCheck +'</div></div>')


        this.$data.hasNextUp = data.hasNext;
        this.$data.upPage += 1
    },

    setChattingDown(data) {

        data.chatMessageDTOS.forEach(item => {
            const messageData = item;
            const senderName = this.$data.emailAndNameDict[messageData.senderEmail]
            const content = messageData.content;
            const regDate = messageData.regDate;
            const readStatus = messageData.readStatus;
            const profileImageUrl = messageData.profileImageUrl;
            if (this.$data.downDayCheck != regDate.slice(0, 10)){
                this.$data.downDayCheck = regDate.slice(0, 10);
                $('#messages-box').prepend('<div class="chat-date-frame"><div class="chat-date">' + this.$data.downDayCheck +'</div></div>');
            }
            this.setChatMessage(senderName, content, readStatus, regDate, profileImageUrl, inverse= false)
        })

        this.$data.hasNextDown = data.hasNext;
        this.$data.downPage += 1
    },
    makeUserDict(roomId) {
        $.ajax({
            url: '/chat/selectChatRoomUsers',
            type: 'POST',
            data: {roomId : roomId},
            dataType: "JSON",
            success: (data) => {
                data.userInfoDTOS.forEach((item, index) => {
                    this.$data.nameAndEmailDict[item.userName] = item.userEmail
                    this.$data.emailAndNameDict[item.userEmail] = item.userName
                })
            }
        })
    },

    connect() {
        var roomId = this.$data.roomId
        var client = this.$data.stompClient

        client.connect({'roomId': roomId}, () => {

            client.subscribe('/topic/chat/' + roomId, (chatMessage) => {

                const messageData = JSON.parse(chatMessage.body);  // 한 번만 파싱
                const senderName = this.$data.emailAndNameDict[messageData.senderEmail]
                const content = messageData.content;
                const regDate = messageData.regDate;
                const readStatus = messageData.readStatus;
                const profileImageUrl = messageData.profileImageUrl;

                this.setChatMessage(senderName, content, readStatus, regDate, profileImageUrl, inverse= false)

            });

            client.subscribe('/topic/chat/accessLoad/' + roomId, (result) => {
                var result = result.body
                if (result.userInfoDTO.userName != this.$data.loginName){
                    $('.status-true').each(function() {

                        let statusTime = ($(this).attr('data-time'));
                        let statusName = ($(this).attr('data-name'));
                        let readNum = $(this).text();

                        if (statusTime > disConnectTime && statusName != result.userInfoDTO.userName){
                            $(this).text(readNum-1);
                        }
                    });
                }
            })

            client.subscribe('/topic/chat/inviteLoad/' + roomId, (result) => {
                var invitedList = JSON.parse(result.body).body
                var names = ""
                invitedList.forEach(item => {
                    names += item.userName + ', '

                })
                result = ""
                result += '<div class="invite-list-container">'
                result += '<div class="invite-user-list">' + names.slice(0, names.length-2) + '님이 초대되었습니다.</div>'
                result += '</div>'
                this.makeUserDict(roomId)
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
            url: "/chat/updateDisConnectTime",
            type: "POST",
            data: {roomId: roomId},
            dataType: "JSON",
            success: (data) => {

            }
        })
    },


    setChatMessage(name, message, readStatus, time, imageUrl, inverse) {
        const tag = document.createElement("div");
        const profileImage = imageUrl ? `/display?fileName=${imageUrl}` : "/display?fileName=outprofile.png";
        const timeFormat = formatter.format(new Date(time));
        const showedTime = timeFormat.slice(21, 23)+" "+
            timeFormat.slice(12, 20)
        const timeFormatted = showedTime.slice(0, 8);

        if (name !== this.$data.loginName) {
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
            sender.textContent = name;

            const contentWrapper = document.createElement("div");
            contentWrapper.classList.add("content-wrapper");

            const content = document.createElement("div");
            content.classList.add("content");
            content.textContent = message;

            const chatlog = document.createElement("div");
            chatlog.classList.add("chatlog");

            if (readStatus > 0) {
                const statusTrue = document.createElement("div");
                statusTrue.classList.add("status-true");
                statusTrue.dataset.time = time;
                statusTrue.dataset.name = name;
                statusTrue.textContent = readStatus;
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
            sender.textContent = name;

            const chatSideAdd = document.createElement("div");
            chatSideAdd.classList.add("chat-side-add");

            const chatlog = document.createElement("div");
            chatlog.classList.add("chatlog");

            if (readStatus > 0) {
                const statusTrue = document.createElement("div");
                statusTrue.classList.add("status-true");
                statusTrue.dataset.time = time;
                statusTrue.dataset.name = name;
                statusTrue.textContent = readStatus;
                chatlog.appendChild(statusTrue);
            }

            const timeElement = document.createElement("div");
            timeElement.classList.add("time");
            timeElement.textContent = timeFormatted;

            chatlog.appendChild(timeElement);
            chatSideAdd.appendChild(chatlog);

            const content = document.createElement("div");
            content.classList.add("content");
            content.textContent = message;

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
        if (inverse == true)
            $('#messages-box').prepend(tag)
        else
            $('#messages-box').append(tag)
    },



}