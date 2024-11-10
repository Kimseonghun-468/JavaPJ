package com.skhkim.instaclone.service;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.dto.UserInfoProjection;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.ProfileImage;
import lombok.Data;


@Data
public class EntityMapper {
    public static UserInfoDTO entityToDTO(UserInfoProjection projection){
        ProfileImage profileImage = projection.getClubMember().getProfileImage();

        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .imgName(profileImage != null ? profileImage.getImgName() : null)
                .uuid(profileImage != null ? profileImage.getUuid() : null)
                .path(profileImage != null ? profileImage.getPath() : null)
                .userName(projection.getClubMember().getName())
                .userEmail(projection.getClubMember().getEmail())
                .status(projection.getStatus())
                .build();
        return userInfoDTO;
    }

    public static UserInfoDTO entityToDTO(ClubMember clubMember){
        ProfileImage profileImage = clubMember.getProfileImage();

        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .imgName(profileImage != null ? profileImage.getImgName() : null)
                .uuid(profileImage != null ? profileImage.getUuid() : null)
                .path(profileImage != null ? profileImage.getPath() : null)
                .userName(clubMember.getName())
                .userEmail(clubMember.getEmail())
                .build();
        return userInfoDTO;
    }

    public static ChatMessageDTO entityToDTO(ChatMessage chatMessage){
        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .senderEmail(chatMessage.getSenderEmail())
                .content(chatMessage.getContent())
                .readStatus(chatMessage.getReadStatus())
                .regDate(chatMessage.getRegDate())
                .build();

        return chatMessageDTO;
    }
}

