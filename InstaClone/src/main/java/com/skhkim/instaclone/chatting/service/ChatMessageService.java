package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.dto.PageResultDTO;
import com.skhkim.instaclone.chatting.entity.ChatMessage;

import java.util.List;

public interface ChatMessageService {

    Long register(ChatMessageDTO chatMessageDTO, String roomID);
    ChatMessageDTO getNewChatMessageDTO(String name, String content);
    List<ChatMessageDTO> getChatMessageListByRoomID(String roomID);

    PageResultDTO<ChatMessageDTO, Object[]> getChatMessageListByRoomIDPage(PageRequestDTO pageRequestDTO, String roomID);
    default ChatMessageDTO entityToDTO(ChatMessage chatMessage){

        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .cid(chatMessage.getCid())
                .name(chatMessage.getName())
                .content(chatMessage.getContent())
                .regDate(chatMessage.getRegDate())
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
