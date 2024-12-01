package com.skhkim.instaclone.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.event.ChatRoomSessionManager;
import com.skhkim.instaclone.chatting.event.WebSocketSessionManager;
import com.skhkim.instaclone.dto.SocketSessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisSubscriber {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomSessionManager chatRoomSessionManager;
    private final WebSocketSessionManager webSocketSessionManager;

    public void onMessage(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ChatMessageDTO chatMessageDTO = objectMapper.readValue(message, ChatMessageDTO.class);
            messagingTemplate.convertAndSend("/topic/chat/" + chatMessageDTO.getRoomId(), chatMessageDTO);
        } catch (Exception e) {
            e.printStackTrace();  // 예외 처리
        }

    }

    public void onSession(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SocketSessionDTO socketSessionDTO = objectMapper.readValue(message, SocketSessionDTO.class);

            // socketSessionDTO 사용
            if (socketSessionDTO.getEventType().equals("connect")) {
                chatRoomSessionManager.addUserToRoom(socketSessionDTO.getRoomId(), socketSessionDTO.getUserId());
                webSocketSessionManager.addSessionToRoomAndName(socketSessionDTO.getSessionId(), socketSessionDTO.getRoomId(), socketSessionDTO.getUserId());
//                System.out.println(chatRoomSessionManager.getRoomJoinNum(socketSessionDTO.getRoomId()));
            } else if (socketSessionDTO.getEventType().equals("disconnect")) {
                chatRoomSessionManager.removeUserFromRoom(socketSessionDTO.getRoomId(), socketSessionDTO.getUserId());
                webSocketSessionManager.removeSession(socketSessionDTO.getSessionId());
//                System.out.println(socketSessionDTO.getRoomId() + ",   " + socketSessionDTO.getSessionId());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}