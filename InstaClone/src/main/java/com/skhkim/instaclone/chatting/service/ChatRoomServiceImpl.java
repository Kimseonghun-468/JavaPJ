package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatRoomDTO;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.repository.ChatRoomRepository;
import com.skhkim.instaclone.dto.ProfilePageRequestDTO;
import com.skhkim.instaclone.dto.ProfilePageResultDTO;
import com.skhkim.instaclone.entity.ProfileImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    @Override
    public ChatRoomDTO getORCreateChatRoomID(String loginName, String friendName){

        List<String> sortedID = getNamesToId(loginName, friendName);
        Optional<ChatRoom> chatRoom = chatRoomRepository.getChatIdbyNames(sortedID.get(2));
        if(chatRoom.isEmpty()){
            ChatRoom createdChatRoom = createChatRoomID(sortedID);
            return entityToDTO(createdChatRoom);
        }
        else{
            return entityToDTO(chatRoom.get());
        }
    }
    @Override
    public ChatRoom createChatRoomID(List<String> sortedID){
        String roomID = sortedID.get(2);
        ChatRoom chatRoom = ChatRoom.builder()
                .id(roomID)
                .userName1(sortedID.get(0))
                .userName2(sortedID.get(1))
                .build();
        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }


    @Override
    public List<String> getNamesToId(String loginName, String friendName){
        List<String> sortedID = new ArrayList<>();
        if(loginName.compareTo(friendName) < 0 ) {
            sortedID.add(loginName);
            sortedID.add(friendName);
            sortedID.add(loginName+ "_" + friendName);
            return sortedID;
        }
        else {
            sortedID.add(friendName);
            sortedID.add(loginName);
            sortedID.add(friendName + "_" + loginName);
            return sortedID;
        }
    }

    @Override
    public ProfilePageResultDTO<Map<String, Object>, Object[]>
            getChatroomAndProfileImageByLoginNamePage(ProfilePageRequestDTO profilePageRequestDTO, String loginName){

        Pageable pageable = profilePageRequestDTO.getPageable();
        Page<Object[]> result = chatRoomRepository.getChatroomAndProfileImage(pageable, loginName);
        Function<Object[], Map<String, Object>> fn = (arr ->{
            Map<String, Object> chatRoomAndProfileMap = new HashMap<>();
            chatRoomAndProfileMap.put("friendName", (arr[0]));

            if(arr[1] == null)
                chatRoomAndProfileMap.put("profileImage", null);
            else
                chatRoomAndProfileMap.put("profileImage", entityToDTOByProfileImage((ProfileImage) arr[1]));

            return chatRoomAndProfileMap;
        });
        return new ProfilePageResultDTO<>(result, fn);
    }
    @Override
    public void registerLastChatTime(String roomID, String comment){
        Optional<ChatRoom> result = chatRoomRepository.getChatIdbyNames(roomID);
        if (result.isPresent()){
            ChatRoom chatRoom = result.get();
            chatRoom.setLastChat(comment);
            chatRoom.setLastChatTime(LocalDateTime.now());
            chatRoomRepository.save(chatRoom);
        }
    }
    @Override
    public void updateChatroomDisConnectTime(String roomID, String loginName){
        Optional<ChatRoom> result = chatRoomRepository.getChatIdbyNames(roomID);
        String[] userNameList = roomID.split("_");
        if (result.isPresent()){
            ChatRoom chatRoom = result.get();
            if (loginName.equals(userNameList[0]))
                chatRoom.setLastDisConnect1(LocalDateTime.now());
            else
                chatRoom.setLastDisConnect2(LocalDateTime.now());
            chatRoomRepository.save(chatRoom);
        }
    }
}
