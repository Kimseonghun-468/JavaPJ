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

    List<String> getNamesToId(String loginName, String friendName);
    ChatRoomDTO getORCreateChatRoomID(String loginName, String friendName);
    ChatRoom createChatRoomID(List<String> sortedID);
    void registerLastChatTime(String roomID, String comment);
    ProfilePageResultDTO<Map<String, Object>, Object[]> getChatroomAndProfileImageByLoginNamePage(ProfilePageRequestDTO profilePageRequestDTO, String loginName);
    void updateChatroomDisConnectTime(String roomID, String loginName);
    default ChatRoomDTO entityToDTO(ChatRoom chatRoom){
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
                .id(chatRoom.getId())
                .userName1(chatRoom.getClubMemberUser1().getName())
                .userName2(chatRoom.getClubMemberUser2().getName())
                .lastChat(chatRoom.getLastChat())
                .lastChatTime(chatRoom.getLastChatTime())
                .lastDisConnect1(chatRoom.getLastDisConnect1())
                .lastDisConnect2(chatRoom.getLastDisConnect2())
                .build();
        return chatRoomDTO;
    }

    default ProfileImageDTO entityToDTOByProfileImage(ProfileImage profileImage){
        ProfileImageDTO profileImageDTO = ProfileImageDTO.builder()
                .pfino(profileImage.getPfino())
                .userName(profileImage.getClubMember().getName())
                .imgName(profileImage.getImgName())
                .userEmail(profileImage.getClubMember().getEmail())
                .path(profileImage.getPath())
                .uuid(profileImage.getUuid())
                .build();
        return profileImageDTO;
    }

}
