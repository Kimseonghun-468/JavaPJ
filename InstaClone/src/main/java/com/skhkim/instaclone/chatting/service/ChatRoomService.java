package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatRoomDTO;
import com.skhkim.instaclone.chatting.entity.ChatRoom;

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
