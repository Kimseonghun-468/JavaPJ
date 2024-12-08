package com.skhkim.instaclone.chatting.controller;

import com.skhkim.instaclone.chatting.dto.ChatUserDTO;
import com.skhkim.instaclone.chatting.service.ChatMessageService;
import com.skhkim.instaclone.chatting.service.ChatRoomService;
import com.skhkim.instaclone.chatting.service.ChatUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final ChatUserService chatUserService;
    private final RedisTemplate<String, Object> redisTemplate;

    @MessageMapping("/chat/accessLoad/{roomID}")
    @SendTo("/topic/chat/accessLoad/{roomID}")
    public ChatUserDTO accessLoad(@DestinationVariable Long roomId) {
        ChatUserDTO result = chatUserService.selectChatUser(roomId);
        return result;
    }

    @PostMapping("/chat/getORCreateChatRoom")
    public ResponseEntity<Long> getORCreateChatRoom(String loginEmail, String friendEmail, Long requestRoomId){

        if (requestRoomId != null){
            chatMessageService.updateReadStatus(requestRoomId);
            return new ResponseEntity<>(requestRoomId, HttpStatus.OK);
        }
        Map<String, Object> chatRoomIDAndOR = chatRoomService.getORCreateChatRoomID(loginEmail, friendEmail);
        Long roomId = (Long) chatRoomIDAndOR.get("roomId");
        if ((boolean) chatRoomIDAndOR.get("OR")) {
            chatUserService.register(loginEmail, roomId);
            chatUserService.register(friendEmail, roomId);
        }
        chatMessageService.updateReadStatus(roomId);
        return new ResponseEntity<>(roomId, HttpStatus.OK);
    }




}
