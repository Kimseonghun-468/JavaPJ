package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatRoomDTO;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import java.util.Map;

public interface ChatRoomService {

    Map<String, Object> getORCreateChatRoomID(String loginEmail, String friendEmail);
    void updateLastChatTime(Long roomID, String comment);

    Long getUserNum(Long roomId);

    void updateUserNum(Long roomId, Long addNum);
    default ChatRoomDTO entityToDTO(ChatRoom chatRoom){
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
                .roomId(chatRoom.getRoomId())
                .userNum(chatRoom.getUserNum())
                .lastChat(chatRoom.getLastChat())
                .lastChatTime(chatRoom.getLastChatTime())
                .build();
        return chatRoomDTO;
    }
}
