package com.example.chatting.Service;

import com.example.chatting.DTO.ChatMessageDTO;
import com.example.chatting.DTO.PageRequestDTO;
import com.example.chatting.DTO.PageResultDTO;
import com.example.chatting.Entity.ChatMessage;

import java.util.List;
import java.util.Optional;

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
