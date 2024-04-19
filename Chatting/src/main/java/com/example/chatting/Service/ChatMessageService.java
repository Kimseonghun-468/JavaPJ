package com.example.chatting.Service;

import com.example.chatting.DTO.ChatMessageDTO;
import com.example.chatting.Entity.ChatMessage;

public interface ChatMessageService {

    ChatMessageDTO getNewChatMessageDTO(String name, String content);

    default ChatMessageDTO entityToDTO(ChatMessage chatMessage){

        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .id(chatMessage.getId())
                .name(chatMessage.getName())
                .senderId(chatMessage.getSenderId())
                .content(chatMessage.getContent())
                .build();

        return chatMessageDTO;
    }
}
