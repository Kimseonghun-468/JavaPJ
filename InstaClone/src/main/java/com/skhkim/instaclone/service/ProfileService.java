package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.*;
import java.util.Map;

public interface ProfileService {
    Long register(ProfileImageDTO profileImageDTO);
    void deleteByName(String name);
    ProfileImageDTO getProfileImage(String name);
    ProfilePageResultDTO<Map<String, Object>, Object[]> getWaitingFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName);
    ProfilePageResultDTO<Map<String, Object>, Object[]> getAcceptFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName);
    ProfilePageResultDTO<Map<String, Object>, Object[]> getFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String userName, String loginName);

    ProfilePageResultDTO<Map<String, Object>, Object[]> getInviteListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName, String inviteSearchTerm);
    Map<String, Object> getFirstUser(String userName, String loginName);
    default ProfileImage dtoToEntity(ProfileImageDTO profileImageDTO){

        ProfileImage profileImage = ProfileImage.builder()
                .clubMember(ClubMember.builder().email(profileImageDTO.getUserEmail()).build())
                .path(profileImageDTO.getPath())
                .uuid(profileImageDTO.getUuid())
                .imgName(profileImageDTO.getImgName())
                .build();

        return profileImage;
    }
    default ProfileImageDTO entityToDTO(ProfileImage profileImage){

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
