package com.skhkim.instaclone.chatting.event;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class WebSocketSessionManager {

    private Map<String, List<String>> sessionMap = new HashMap<>();

    public void addSessionToRoomAndName(String sessionId, String roomId, String userId){
        List<String> ids = Stream.of(roomId, userId).collect(Collectors.toList());
        sessionMap.put(sessionId, ids);
    }

    public void removeSession(String sessionId){
        sessionMap.remove(sessionId);
    }

    public List<String> getChatSession(String sessionId){
        return sessionMap.get(sessionId);
    }

}
