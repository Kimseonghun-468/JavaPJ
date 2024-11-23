package com.skhkim.instaclone.chatting.event;


import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    // seesion id에 chatroomid랑, Name을 적을거임, 그럼 session id를 기준으로 나갈때 chatroom id랑 Name을 알 수 있음.
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) headerAccessor.getUser();
        String userId = ((ClubAuthMemberDTO) authenticationToken.getPrincipal()).getName();
        String roomId = headerAccessor.getFirstNativeHeader("roomId");
        String sessionId = headerAccessor.getSessionId();
        chatRoomSessionManager.addUserToRoom(roomId, userId);
        webSocketSessionManager.addSessionToRoomAndName(sessionId, roomId, userId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        List<String> chatSession = webSocketSessionManager.getChatSession(sessionId);
        if (chatSession != null) {
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) headerAccessor.getUser();
            String userId = ((ClubAuthMemberDTO) authenticationToken.getPrincipal()).getName();
            chatRoomSessionManager.removeUserFromRoom(chatSession.get(0), chatSession.get(1));
            webSocketSessionManager.removeSession(sessionId);
        }
    }
}
