package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatRoomDTO;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    @Override
    public ChatRoomDTO getORCreateChatRoomID(String loginName, String friendName){

        String checkId = getNamesToId(loginName, friendName);
        Optional<ChatRoom> chatRoom = chatRoomRepository.getChatIdbyEmails(checkId);
        if(chatRoom.isEmpty()){
            ChatRoom createdChatRoom = createChatRoomID(loginName, friendName);
            ChatRoomDTO createdChatRoomDTO = entityToDTO(createdChatRoom);
            return createdChatRoomDTO;
        }
        else{
            ChatRoomDTO chatRoomDTO = entityToDTO(chatRoom.get());
            return chatRoomDTO;
        }
    }
    @Override
    public ChatRoom createChatRoomID(String loginName, String friendName){
        String roomID = getNamesToId(loginName, friendName);
        ChatRoom chatRoom = ChatRoom.builder()
                .id(roomID)
                .userName(loginName)
                .friendName(friendName)
                .build();
        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }


    private String getNamesToId(String loginName, String friendName){
        if(loginName.compareTo(friendName) < 0 )
            return loginName + "_" + friendName;
        else
            return friendName + "_" + loginName;
    }
    // 여기서 중복된건 걸러야함

    @Override
    public List<String> getChatroomListByName(String loginName){
        List<ChatRoom> chatRoomList = chatRoomRepository.getChatRoomsListByName(loginName);
        List<String> nameList = chatRoomList.stream().map(chatRoom -> {
            if(!chatRoom.getUserName().equals(loginName)){
                return chatRoom.getUserName();
            }
            else
                return chatRoom.getFriendName();
        }).collect(Collectors.toList());
        return nameList;
    }
}
