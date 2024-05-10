package com.skhkim.instaclone.chatting.controller;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.ChatRoomDTO;
import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.dto.PageResultDTO;
import com.skhkim.instaclone.chatting.service.ChatMessageService;
import com.skhkim.instaclone.chatting.service.ChatRoomService;
import com.skhkim.instaclone.dto.ProfilePageRequestDTO;
import com.skhkim.instaclone.dto.ProfilePageResultDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    @MessageMapping("/chat/{roomID}")
    @SendTo("/topic/chat/{roomID}")
    public ChatMessageDTO sendMessage(@DestinationVariable String roomID, ChatMessageDTO chatMessageDTO) {
        log.info("Room ID :"+ roomID);
        ChatMessageDTO reuslt = ChatMessageDTO.builder()
                .name(chatMessageDTO.getName())
                .content(chatMessageDTO.getContent())
                .regDate(LocalDateTime.now())
                .build();
        chatMessageService.register(reuslt, roomID);
        chatRoomService.registerLastChatTime(roomID, chatMessageDTO.getContent());
        // register Room ID를 통해 chatMessageDTO.getcontent랑, LocalDattime.now 두개.
        return reuslt;
    }

    @PostMapping("/chat/getORCreateChatRoom")
    public ResponseEntity<ChatRoomDTO> getORCreateChatRoom(String loginName, String friendName){
        log.info("Login Name : " +loginName);
        log.info("Friend Name : " +friendName);
        ChatRoomDTO chatRoomDTO = chatRoomService.getORCreateChatRoomID(loginName, friendName);
        return new ResponseEntity<>(chatRoomDTO, HttpStatus.OK);
    }

//    @PostMapping("/chat/getChatListbyRoomID")
//    public ResponseEntity<List<ChatMessageDTO>> getChatListbyRoomID(String roomID){
//        List<ChatMessageDTO> chatMessageDTOList = chatMessageService.getChatMessageListByRoomID(roomID);
//        return new ResponseEntity<>(chatMessageDTOList, HttpStatus.OK);
//    }

    @PostMapping("/chat/getChatListbyRoomIDPage")
    public ResponseEntity<PageResultDTO> getChatListbyRoomIDPage(PageRequestDTO pageRequestDTO, String roomID){
        PageResultDTO pageResultDTO = chatMessageService.getChatMessageListByRoomIDPage(pageRequestDTO, roomID);
        return new ResponseEntity<>(pageResultDTO, HttpStatus.OK);
    }

//    @PostMapping("/chat/getChatRoomAndProfileImage")
//    public ResponseEntity<Map<String, Object>> getChatroomAndProfileImageList(String loginName){
//        Map<String, Object> result = chatRoomService.getChatroomAndProfileImageByLoginName(loginName);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @PostMapping("/chat/getChatRoomAndProfileImagePage")
    public ResponseEntity<ProfilePageResultDTO<Map<String, Object>, Object[]>> getChatroomAndProfileImagePage(ProfilePageRequestDTO profilePageRequestDTO, String loginName){
        ProfilePageResultDTO<Map<String, Object>, Object[]> result = chatRoomService.getChatroomAndProfileImageByLoginNamePage(profilePageRequestDTO, loginName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/chat/updateDisConnectTime")
    public ResponseEntity<String> getTest(String roomID, String loginName){
        chatRoomService.updateChatroomDisConnectTime(roomID, loginName);
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }
}
