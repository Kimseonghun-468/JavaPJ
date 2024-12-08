package com.skhkim.instaclone.chatting.event;


import com.skhkim.instaclone.config.redis.RedisPublisher;
import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.dto.SocketSessionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final ChatRoomSessionManager chatRoomSessionManager;
    private final WebSocketSessionManager webSocketSessionManager;
    private final RedisPublisher redisPublisher;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String roomId = headerAccessor.getFirstNativeHeader("roomId");

        SocketSessionDTO socketSessionDTO = SocketSessionDTO.builder()
                .sessionId(sessionId)
                .eventType("connect")
                .roomId(roomId)
                .userId(LoginContext.getClubMember().getName())
                .build();

        redisPublisher.publishSessionEvent(socketSessionDTO);

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();


        List<String> chatSession = webSocketSessionManager.getChatSession(sessionId);
        if (chatSession != null) {
//            chatRoomSessionManager.removeUserFromRoom(chatSession.get(0), chatSession.get(1));
//            webSocketSessionManager.removeSession(sessionId);
            SocketSessionDTO socketSessionDTO = SocketSessionDTO.builder()
                    .sessionId(sessionId)
                    .eventType("disconnect")
                    .roomId(chatSession.get(0))
                    .userId(chatSession.get(1))
                    .build();

            redisPublisher.publishSessionEvent(socketSessionDTO);
        }



    }
}
