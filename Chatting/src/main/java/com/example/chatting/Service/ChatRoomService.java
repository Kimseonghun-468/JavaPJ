package com.example.chatting.Service;

import com.example.chatting.DTO.ChatMessageDTO;
import com.example.chatting.DTO.ChatRoomDTO;
import com.example.chatting.Entity.ChatMessage;
import com.example.chatting.Entity.ChatRoom;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ChatRoomService {

    ChatRoomDTO getORCreateChatRoomID(String loginName, String friendName);
    ChatRoom createChatRoomID(String loginName, String friendName);

    default ChatRoomDTO entityToDTO(ChatRoom chatRoom){
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
                .id(chatRoom.getId())
                .userName1(chatRoom.getUserName1())
                .userName2(chatRoom.getUserName2())
                .build();
        return chatRoomDTO;

    }
}
