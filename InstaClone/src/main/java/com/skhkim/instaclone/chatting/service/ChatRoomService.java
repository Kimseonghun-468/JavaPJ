package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatRoomDTO;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.dto.ProfileImageDTO;
import com.skhkim.instaclone.dto.ProfilePageRequestDTO;
import com.skhkim.instaclone.dto.ProfilePageResultDTO;
import com.skhkim.instaclone.entity.ProfileImage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChatRoomService {

    ChatRoomDTO getORCreateChatRoomID(String loginName, String friendName);
    ChatRoom createChatRoomID(String loginName, String friendName);
    List<String> getChatroomListByName(String loginEmail);

    Map<String, Object> getChatroomAndProfileImageByLoginName(String loginName);
    ProfilePageResultDTO<Map<String, Object>, Object[]> getChatroomAndProfileImageByLoginNamePage(ProfilePageRequestDTO profilePageRequestDTO, String loginName);

    default ChatRoomDTO entityToDTO(ChatRoom chatRoom){
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
                .id(chatRoom.getId())
                .userName(chatRoom.getUserName())
                .friendName(chatRoom.getFriendName())
                .build();
        return chatRoomDTO;
    }

    default ProfileImageDTO entityToDTOByProfileImage(ProfileImage profileImage){
        ProfileImageDTO profileImageDTO = ProfileImageDTO.builder()
                .pfino(profileImage.getPfino())
                .userName(profileImage.getUserName())
                .imgName(profileImage.getImgName())
                .userEmail(profileImage.getUserEmail())
                .path(profileImage.getPath())
                .uuid(profileImage.getUuid())
                .build();
        return profileImageDTO;
    }

}
