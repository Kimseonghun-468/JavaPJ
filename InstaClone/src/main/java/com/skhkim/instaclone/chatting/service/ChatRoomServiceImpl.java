package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatRoomDTO;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.repository.ChatRoomRepository;
import com.skhkim.instaclone.dto.ProfileImageDTO;
import com.skhkim.instaclone.dto.ProfilePageRequestDTO;
import com.skhkim.instaclone.dto.ProfilePageResultDTO;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
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

    @Override
    public Map<String, Object> getChatroomAndProfileImageByLoginName(String loginName){
        Map<String, Object> chatRoomAndProfileMap = new HashMap<>();

        List<String> nameList = new ArrayList<>();
        List<ProfileImage> profileImageList = new ArrayList<>();
        List<Object[]> chatRoomAndProfileImageByUserName = chatRoomRepository.getChatroomAndProfileImageByUserName(loginName);
        List<Object[]> chatRoomAndProfileImageByFriendName = chatRoomRepository.getChatroomAndProfileImageByFriendName(loginName);

        chatRoomAndProfileImageByFriendName.forEach(arr ->{
            nameList.add(((ChatRoom) arr[0]).getFriendName());
            profileImageList.add(((ProfileImage) arr[1]));
        });
        chatRoomAndProfileImageByUserName.forEach(arr ->{
            nameList.add(((ChatRoom) arr[0]).getUserName());
            profileImageList.add(((ProfileImage) arr[1]));
        });

        List<ProfileImageDTO> profileImageDTOList =
                profileImageList.stream().map(profileImage -> {
                    if (profileImage ==null)
                        return null;
                    else
                        return entityToDTOByProfileImage(profileImage);
                }).collect(Collectors.toList());
        chatRoomAndProfileMap.put("nameList", nameList);
        chatRoomAndProfileMap.put("profileImageList", profileImageDTOList);

        return chatRoomAndProfileMap;
    }

    @Override
    public ProfilePageResultDTO<Map<String, Object>, Object[]>
            getChatroomAndProfileImageByLoginNamePage(ProfilePageRequestDTO profilePageRequestDTO, String loginName){

        Pageable pageable = profilePageRequestDTO.getPageable(Sort.by("id"));
        Page<Object[]> result = chatRoomRepository.getChatroomAndProfileImage(pageable, loginName);
        Function<Object[], Map<String, Object>> fn = (arr ->{
            Map<String, Object> chatRoomAndProfileMap = new HashMap<>();
            chatRoomAndProfileMap.put("friendName", ((String) arr[0]));

            if(arr[1] == null)
                chatRoomAndProfileMap.put("profileImage", null);
            else
                chatRoomAndProfileMap.put("profileImage", entityToDTOByProfileImage((ProfileImage) arr[1]));

            return chatRoomAndProfileMap;
        });
        return new ProfilePageResultDTO<>(result, fn);
    }
}
