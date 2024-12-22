package com.skhkim.instaclone.config.redis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.event.ChatRoomSessionManager;
import com.skhkim.instaclone.chatting.event.WebSocketSessionManager;
import com.skhkim.instaclone.chatting.response.ChatUserResponse;
import com.skhkim.instaclone.dto.SocketSessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public void onInvite(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(message);
            JsonNode userNamesNode = rootNode.get("userNames");
            List<String> userNames = objectMapper.readValue(userNamesNode.get(1).toString(), List.class);
            rootNode.get("roomId").asLong();

            messagingTemplate.convertAndSend("/topic/chat/inviteLoad/" + rootNode.get("roomId").asLong(), userNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAccess(String message){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            ChatUserResponse chatUserResponse = objectMapper.readValue(message, ChatUserResponse.class);
            messagingTemplate.convertAndSend("/topic/chat/accessLoad/" + chatUserResponse.getRoomId(), chatUserResponse);
        } catch (Exception e) {
            e.printStackTrace();  // 예외 처리
        }

    }
}