package com.skhkim.instaclone.chatting.event;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ChatRoomSessionManager {

    private Map<String, List<String>> roomToUsersMap = new HashMap<>();

    public void addUserToRoom(String roomId, String userId) {
        List<String> roomIdInUsers = roomToUsersMap.get(roomId);
        if (roomIdInUsers == null) {
            roomIdInUsers = new ArrayList<>();
            roomToUsersMap.put(roomId, roomIdInUsers);
        }
//        if (!roomIdInUsers.contains(userId)) {
        roomIdInUsers.add(userId);
//        }
    }

    public void removeUserFromRoom(String roomId, String userId) {
        List<String> roomIdInUsers = roomToUsersMap.get(roomId);
        if (roomIdInUsers != null) {
            roomIdInUsers.remove(userId);
            if (roomIdInUsers.isEmpty()) {
                roomToUsersMap.remove(roomId);
            }
        }
    }

    public Long getRoomJoinNum(String roomId) {
        List<String> result = roomToUsersMap.get(roomId);
        Set<String> set = new HashSet<>(result);

        return (long) set.size();
    }

}

