package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.dto.PageResultDTO;
import com.skhkim.instaclone.chatting.entity.ChatMessage;

import java.util.List;

public interface ChatMessageService {

    Long getNotReadNum(String loginName, String friendName);
    Long register(ChatMessageDTO chatMessageDTO, String roomID);
    ChatMessageDTO getNewChatMessageDTO(String name, String content);
    List<ChatMessageDTO> getChatMessageListByRoomID(String roomID);

    void updateChatMessagesReadStatus(String roomID, String userName);

    PageResultDTO<ChatMessageDTO, Object[]> getChatMessageListByRoomIDPage(PageRequestDTO pageRequestDTO, String roomID);
    default ChatMessageDTO entityToDTO(ChatMessage chatMessage){

        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .cid(chatMessage.getCid())
                .name(chatMessage.getName())
                .content(chatMessage.getContent())
                .regDate(chatMessage.getRegDate())
                .readStatus(chatMessage.isReadStatus())
                .build();

        return chatMessageDTO;
    }

    default ChatMessage dtoToEntity(ChatMessageDTO chatMessageDTO){
        ChatMessage chatMessage = ChatMessage.builder()
                .name(chatMessageDTO.getName())
                .content(chatMessageDTO.getContent())
                .build();

        return chatMessage;
    }
}
