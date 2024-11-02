package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.response.ChatRoomResponse;
import com.skhkim.instaclone.dto.ProfileImageDTO;
import com.skhkim.instaclone.dto.ProfilePageRequestDTO;

import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.entity.ProfileImage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface ChatUserService {

    void register(String userEmail, Long roomId);
    void updateDisConnect(Long roomId, String loginEmail);

    List<Object[]> getEmailAndName(Long roomId);

    ChatRoomResponse getProfileAndUseByLoginNamePage(ProfilePageRequestDTO profilePageRequestDTO, String loginEmail);

    void insertChatUser(List<String> userEmails, Long roomId);
    LocalDateTime getDisConnectTime(Long roomId, String loginEmail);
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

    default List<UserInfoDTO> entityToDTOS(List<ChatUser> chatUserList){
        List<UserInfoDTO> userInfoDTOS = chatUserList.stream().map(chatUser ->
            UserInfoDTO.builder()
                    .userEmail(chatUser.getMember().getEmail())
                    .userName(chatUser.getMember().getName())
                    .imgName(chatUser.getMember().getProfileImage() != null ? chatUser.getMember().getProfileImage().getImgName() : null)
                    .uuid(chatUser.getMember().getProfileImage() != null ? chatUser.getMember().getProfileImage().getUuid() : null)
                    .path(chatUser.getMember().getProfileImage() != null ? chatUser.getMember().getProfileImage().getPath() : null)
                    .build()).collect(Collectors.toList());

        return userInfoDTOS;
    }

}

