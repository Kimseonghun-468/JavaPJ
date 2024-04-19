package com.example.chatting.Service;

import com.example.chatting.DTO.ChatRoomDTO;
import com.example.chatting.Entity.ChatRoom;

public interface ChatRoomService {

    default ChatRoomDTO entityToDTO(ChatRoom chatRoom){
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
                .id(chatRoom.getId())
                .messages(chatRoom.getMessages())
                .userId1(chatRoom.getUserId1())
                .userId2(chatRoom.getUserId2())
                .build();

        return chatRoomDTO;
    }
}
