package com.skhkim.instaclone.chatting.controller;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.ChatRoomDTO;
import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.dto.PageResultDTO;
import com.skhkim.instaclone.chatting.event.ChatRoomSessionManager;
import com.skhkim.instaclone.chatting.service.ChatMessageService;
import com.skhkim.instaclone.chatting.service.ChatRoomService;
import com.skhkim.instaclone.dto.ProfilePageRequestDTO;
import com.skhkim.instaclone.dto.ProfilePageResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
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
    private final ChatRoomSessionManager chatRoomSessionManager;
    @MessageMapping("/chat/{roomID}")
    @SendTo("/topic/chat/{roomID}")
    public ChatMessageDTO sendMessage(@DestinationVariable String roomID, ChatMessageDTO chatMessageDTO, String email) {
        log.info("Room ID :"+ roomID);
        boolean readStatus;
        if (chatRoomSessionManager.getRoomJoinNum(roomID) == 2)
            readStatus = true;
        else
            readStatus = false;
        ChatMessageDTO result = ChatMessageDTO.builder()
                .name(chatMessageDTO.getName())
                .email(chatMessageDTO.getEmail())
                .content(chatMessageDTO.getContent())
                .regDate(LocalDateTime.now())
                .readStatus(readStatus)
                .build();
        chatMessageService.register(result, roomID);
        chatRoomService.registerLastChatTime(roomID, chatMessageDTO.getContent());

        return result;
    }

    @MessageMapping("/chat/accessLoad/{roomID}")
    @SendTo("/topic/chat/accessLoad/{roomID}")
    public String accessLoad(@DestinationVariable String roomID, String loginName) {
        log.info(roomID);
        log.info(loginName);
        return loginName;
    }

    @PostMapping("/chat/getORCreateChatRoom")
    public ResponseEntity<ChatRoomDTO> getORCreateChatRoom(String loginEmail, String friendEmail){
        log.info("Login Name : " +loginEmail);
        log.info("Friend Name : " +friendEmail);
        ChatRoomDTO chatRoomDTO = chatRoomService.getORCreateChatRoomID(loginEmail, friendEmail);
        List<String> roomID = chatRoomService.getNamesToId(loginEmail, friendEmail);
        chatMessageService.updateChatMessagesReadStatus(roomID.get(2), friendEmail);
        return new ResponseEntity<>(chatRoomDTO, HttpStatus.OK);
    }

    @PostMapping("/chat/getChatListByRoomIDPageBefore")
    public ResponseEntity<PageResultDTO> getChatListByRoomIDPageBefore(PageRequestDTO pageRequestDTO, String roomID, String loginName){
        PageResultDTO pageResultDTO = chatMessageService.getChatMessageListByRoomIDPageBefore(pageRequestDTO, roomID, loginName);
        return new ResponseEntity<>(pageResultDTO, HttpStatus.OK);
    }

    @PostMapping("/chat/getChatListByRoomIDPageAfter")
    public ResponseEntity<PageResultDTO> getChatListByRoomIDPageAfter(PageRequestDTO pageRequestDTO, String roomID, String loginName) {
        PageResultDTO pageResultDTO = chatMessageService.getChatMessageListByRoomIDPageAfter(pageRequestDTO, roomID, loginName);
        return new ResponseEntity<>(pageResultDTO, HttpStatus.OK);
    }

    @PostMapping("/chat/getChatRoomAndProfileImagePage")
    public ResponseEntity<ProfilePageResultDTO<Map<String, Object>, Object[]>> getChatroomAndProfileImagePage(ProfilePageRequestDTO profilePageRequestDTO, String loginName){
        ProfilePageResultDTO<Map<String, Object>, Object[]> result = chatRoomService.getChatroomAndProfileImageByLoginNamePage(profilePageRequestDTO, loginName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/chat/updateDisConnectTime")
    public ResponseEntity<String> updateDisconnectTime(String roomID, String loginName){
        chatRoomService.updateChatroomDisConnectTime(roomID, loginName);
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }

    @PostMapping("/chat/getNotReadNum")
    public ResponseEntity<Long> getNotReadNum(String loginName, String friendName){
        Long resultNum = chatMessageService.getNotReadNum(loginName, friendName);
        return new ResponseEntity<>(resultNum, HttpStatus.OK);
    }
}
