package com.example.chatting.Service;

import com.example.chatting.DTO.ChatRoomDTO;
import com.example.chatting.Entity.ChatRoom;
import com.example.chatting.Repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    @Override
    public ChatRoomDTO getORCreateChatRoomID(String loginName, String friendName){

        String checkId = getEmailsToId(loginName, friendName);
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
        String roomID = getEmailsToId(loginName, friendName);
        ChatRoom chatRoom = ChatRoom.builder()
                .id(roomID)
                .userName1(loginName)
                .userName2(friendName)
                .build();
        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }


    private String getEmailsToId(String loginName, String friendName){
        if(loginName.compareTo(friendName) < 0 )
            return loginName + "_" + friendName;
        else
            return friendName + "_" + loginName;
    }
    // 여기서 중복된건 걸러야함

}
