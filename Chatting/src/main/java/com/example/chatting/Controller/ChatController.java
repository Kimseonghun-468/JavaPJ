package com.example.chatting.Controller;
import com.example.chatting.DTO.ChatMessageDTO;
import com.example.chatting.DTO.ChatRoomDTO;
import com.example.chatting.Entity.ChatMessage;
import com.example.chatting.Entity.ChatRoom;
import com.example.chatting.Service.ChatMessageService;
import com.example.chatting.Service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.HtmlUtils;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    @MessageMapping("/chat/{roomID}")
    @SendTo("/topic/chat/{roomID}")
//    @MessageMapping("/chat/")
//    @SendTo("/topic/chat/")
    public ChatMessageDTO sendMessage(@DestinationVariable String roomID, ChatMessageDTO chatMessageDTO) {
        log.info("Room ID :"+ roomID);
        log.info("SendMessage !");
        ChatMessageDTO reuslt = ChatMessageDTO.builder()
                .name(chatMessageDTO.getName())
                .content(chatMessageDTO.getContent())
                .regDate(LocalDateTime.now())
                .build();
        // dto result에 시간 넣는데, 시간 받아오는 방법 찾기
        chatMessageService.register(reuslt, roomID);
        return reuslt;
    }

    @PostMapping("/chat/getORCreateChatRoom")
    public ResponseEntity<ChatRoomDTO> getORCreateChatRoom(String loginName, String friendName){
        log.info("Login Name : " +loginName);
        log.info("Friend Name : " +friendName);
        ChatRoomDTO chatRoomDTO = chatRoomService.getORCreateChatRoomID(loginName, friendName);

        return new ResponseEntity<>(chatRoomDTO, HttpStatus.OK);
    }

    @PostMapping("/chat/getChatListbyRoomID")
    public ResponseEntity<List<ChatMessageDTO>> getChatListbyRoomID(String roomID){
        List<ChatMessageDTO> chatMessageDTOList = chatMessageService.getChatMessageListByRoomID(roomID);
        return new ResponseEntity<>(chatMessageDTOList, HttpStatus.OK);
    }
}
