package com.example.chatting.Service;

import com.example.chatting.DTO.ChatMessageDTO;
import com.example.chatting.DTO.ChatRoomDTO;
import com.example.chatting.Entity.ChatMessage;
import com.example.chatting.Entity.ChatRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface ChatRoomService {

    ChatRoomDTO getORCreateChatRoomID(String loginName, String friendName);
    ChatRoom createChatRoomID(String loginName, String friendName);

    default ChatRoomDTO entityToDTO(ChatRoom chatRoom){
        List<ChatMessage> chatMessageList = chatRoom.getMessages();
        List<ChatMessageDTO> chatMessageDTOList = new ArrayList<>();
        if (!chatRoom.getMessages().isEmpty())
            chatMessageDTOList = chatMessageList.stream().map
                    (this::entityToDTOChatMessage).collect(Collectors.toList());

        ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
                .id(chatRoom.getId())
                .userName1(chatRoom.getUserName1())
                .userName2(chatRoom.getUserName2())
                .messages(chatMessageDTOList)
                .build();

        return chatRoomDTO;

    }

    default ChatMessageDTO entityToDTOChatMessage(ChatMessage chatMessage){

        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .cid(chatMessage.getCid())
                .name(chatMessage.getName())
                .content(chatMessage.getContent())
                .regDate(chatMessage.getRegDate())
                .build();

        return chatMessageDTO;
    }
}
