package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.dto.PageResultDTO;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.repository.ChatMessageRepository;
import com.skhkim.instaclone.chatting.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatMessageDTO getNewChatMessageDTO(String name, String content){
        return ChatMessageDTO.builder().build();
    }

    @Override
    public Long register(ChatMessageDTO chatMessageDTO, String roomID){
        ChatMessage chatMessage = ChatMessage.builder()
                .cid(chatMessageDTO.getCid())
                .name(chatMessageDTO.getName())
                .content(chatMessageDTO.getContent())
                .chatRoom(ChatRoom.builder().id(roomID).build())
                .readStatus(chatMessageDTO.isReadStatus())
                .regDate(chatMessageDTO.getRegDate())
                .build();
        chatMessageRepository.save(chatMessage);

        return chatMessage.getCid();
    }
    @Override
    public void updateChatMessagesReadStatus(String roomID, String userName){
        Optional<ChatRoom> result = chatRoomRepository.getChatIdbyNames(roomID);
        if (result.isPresent()){
            ChatRoom chatRoom = result.get();
            if (chatRoom.getClubMemberUser1().getName().equals(userName))
                chatMessageRepository.updateByChatRoomIdAndDisConnectTime(roomID, userName, chatRoom.getLastDisConnect2());
            else
                chatMessageRepository.updateByChatRoomIdAndDisConnectTime(roomID, userName, chatRoom.getLastDisConnect1());
        }
    }

    @Override
    public List<ChatMessageDTO> getChatMessageListByRoomID(String roomID){
//        Pageable pageable =
        Optional<List<ChatMessage>> chatMessagesList = chatMessageRepository.findByChatRoomId(roomID);
        if(chatMessagesList.isPresent()) {
            List<ChatMessageDTO> chatMessageDTOList = chatMessagesList.get().stream().map
                    (chatMessage -> entityToDTO(chatMessage)).collect(Collectors.toList());
            return chatMessageDTOList;
        }
        else
            return Collections.emptyList();
    }
    @Override
    public PageResultDTO<ChatMessageDTO, Object[]> getChatMessageListByRoomIDPageBefore(PageRequestDTO pageRequestDTO, String roomID, String loginName){
        Pageable pageable = pageRequestDTO.getPageable();
        Function<Object[], ChatMessageDTO> fn = (arr -> entityToDTO(
                (ChatMessage)arr[0])
        );
        Page<Object[]> result;
        if (roomID.split("_")[0].equals(loginName)){
            result = chatMessageRepository.findByChatRoomIdInfixBefore(pageable, roomID);
        }
        else{
            result = chatMessageRepository.findByChatRoomIdPostfixBefore(pageable, roomID);
        }

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<ChatMessageDTO, Object[]> getChatMessageListByRoomIDPageAfter(PageRequestDTO pageRequestDTO, String roomID, String loginName){
        Pageable pageable = pageRequestDTO.getPageable();
        Function<Object[], ChatMessageDTO> fn = (arr -> entityToDTO(
                (ChatMessage)arr[0])
        );
        Page<Object[]> result;
        if (roomID.split("_")[0].equals(loginName)){
            result = chatMessageRepository.findByChatRoomIdInfixAfter(pageable, roomID);
        }
        else{
            result = chatMessageRepository.findByChatRoomIdPostfixAfter(pageable, roomID);
        }

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public Long getNotReadNum(String loginName, String friendName){
        String roomID = getNamesToId(loginName, friendName).get(2);
        return chatMessageRepository.getNotReadNum(roomID, friendName);
    }

    public List<String> getNamesToId(String loginName, String friendName){
        List<String> sortedID = new ArrayList<>();
        if(loginName.compareTo(friendName) < 0 ) {
            sortedID.add(loginName);
            sortedID.add(friendName);
            sortedID.add(loginName+ "_" + friendName);
        }
        else {
            sortedID.add(friendName);
            sortedID.add(loginName);
            sortedID.add(friendName + "_" + loginName);
        }
        return sortedID;
    }

}
