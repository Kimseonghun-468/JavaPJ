package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.dto.PageResultDTO;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.entity.ClubMember;

import java.util.List;

public interface ChatMessageService {

    Long getNotReadNum(String loginName, String friendName);
    Long register(ChatMessageDTO chatMessageDTO, String roomID);
    ChatMessageDTO getNewChatMessageDTO(String name, String content);
    List<ChatMessageDTO> getChatMessageListByRoomID(String roomID);

    void updateChatMessagesReadStatus(String roomID, String userEmail);

    PageResultDTO<ChatMessageDTO, Object[]> getChatMessageListByRoomIDPageBefore(PageRequestDTO pageRequestDTO, String roomID, String loginName);
    PageResultDTO<ChatMessageDTO, Object[]> getChatMessageListByRoomIDPageAfter(PageRequestDTO pageRequestDTO, String roomID, String loginName);
    default ChatMessageDTO entityToDTO(ChatMessage chatMessage){

        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .cid(chatMessage.getCid())
                .name(chatMessage.getClubMember().getName())
                .email(chatMessage.getClubMember().getEmail())
                .content(chatMessage.getContent())
                .regDate(chatMessage.getRegDate())
                .readStatus(chatMessage.isReadStatus())
                .build();

        return chatMessageDTO;
    }

    default ChatMessage dtoToEntity(ChatMessageDTO chatMessageDTO, String roomID){
        ChatMessage chatMessage = ChatMessage.builder()
                .cid(chatMessageDTO.getCid())
                .clubMember(ClubMember.builder().email(chatMessageDTO.getEmail()).build())
                .content(chatMessageDTO.getContent())
                .chatRoom(ChatRoom.builder().id(roomID).build())
                .readStatus(chatMessageDTO.isReadStatus())
                .regDate(chatMessageDTO.getRegDate())
                .build();

        return chatMessage;
    }
}
