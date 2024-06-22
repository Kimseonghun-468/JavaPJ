package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.repository.ChatRoomRepository;
import com.skhkim.instaclone.chatting.repository.ChatUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatUserRepository chatUserRepository;

    @Override
    public Map<String, Object> getORCreateChatRoomID(String loginEmail, String friendEmail) {
        Map<String, Object> roomIdAndOR = new HashMap<>();

        Optional<Long> roomId = chatUserRepository.getRoomIdByEmails(loginEmail, friendEmail);
        if (roomId.isEmpty()) {
            ChatRoom createdChatRoom = ChatRoom.builder()
                    .userNum(2L)
                    .build();
            chatRoomRepository.save(createdChatRoom);
            roomIdAndOR.put("roomId", createdChatRoom.getRoomId());
            roomIdAndOR.put("OR", true);
            return roomIdAndOR;
        } else {
            roomIdAndOR.put("roomId", roomId.get());
            roomIdAndOR.put("OR", false);
            return roomIdAndOR;
        }
    }

    @Override
    public Long getUserNum(Long roomId) {
        return chatRoomRepository.getUserNum(roomId);
    }

    @Override
    public void updateLastChatTime(Long roomID, String comment) {
        Optional<ChatRoom> result = chatRoomRepository.getChatRoombyRoomId(roomID);
        if (result.isPresent()) {
            ChatRoom chatRoom = result.get();
            chatRoom.setLastChat(comment);
            chatRoom.setLastChatTime(LocalDateTime.now());
            chatRoomRepository.save(chatRoom);
        }
    }
    @Override
    public void updateUserNum(Long roomId, Long addNum){
        chatRoomRepository.updateUserNumByRoomId(roomId, addNum);
    }
}