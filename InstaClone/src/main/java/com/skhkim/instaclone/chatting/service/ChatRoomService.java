package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatRoomDTO;
import com.skhkim.instaclone.chatting.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {

    ChatRoomDTO getORCreateChatRoomID(String loginName, String friendName);
    ChatRoom createChatRoomID(String loginName, String friendName);
    List<String> getChatroomListByName(String loginEmail);
    default ChatRoomDTO entityToDTO(ChatRoom chatRoom){
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
                .id(chatRoom.getId())
                .userName(chatRoom.getUserName())
                .friendName(chatRoom.getFriendName())
                .build();
        return chatRoomDTO;

    }
}
