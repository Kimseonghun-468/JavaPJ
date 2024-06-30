package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.dto.PageResultDTO;
import com.skhkim.instaclone.chatting.entity.ChatMessage;

public interface ChatMessageService {

    Long getNotReadNum(String loginEmail, Long roomId);
    void register(ChatMessageDTO chatMessageDTO, Long roomID);

    void updateChatMessagesReadStatus(Long roomID, String userEmail);
    PageResultDTO<ChatMessageDTO, ChatMessage> getChatMessageListByRoomIDPageBefore(PageRequestDTO pageRequestDTO, Long roomID, String loginEmail);
    PageResultDTO<ChatMessageDTO, ChatMessage> getChatMessageListByRoomIDPageAfter(PageRequestDTO pageRequestDTO, Long roomID, String loginEmail);
    default ChatMessageDTO entityToDTO(ChatMessage chatMessage){

        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .cid(chatMessage.getId())
                .senderEmail(chatMessage.getSenderEmail())
                .content(chatMessage.getContent())
                .readStatus(chatMessage.getReadStatus())
                .regDate(chatMessage.getRegDate())
                .build();

        return chatMessageDTO;
    }

    default ChatMessage dtoToEntity(ChatMessageDTO chatMessageDTO, Long roomID){
        ChatMessage chatMessage = ChatMessage.builder()
                .id(chatMessageDTO.getCid())
                .roomId(roomID)
                .senderEmail(chatMessageDTO.getSenderEmail())
                .content(chatMessageDTO.getContent())
                .readStatus(chatMessageDTO.getReadStatus())
                .regDate(chatMessageDTO.getRegDate())
                .build();

        return chatMessage;
    }
}
