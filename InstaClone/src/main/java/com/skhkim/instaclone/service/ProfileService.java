package com.skhkim.instaclone.service;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.*;
import com.skhkim.instaclone.response.UserInfoResponse;

import java.util.List;
import java.util.Map;

public interface ProfileService {
    Long register(ProfileImageDTO profileImageDTO);
    void deleteByName(String name);
    ProfileImageDTO getProfileImage(String name);
    UserInfoResponse getWaitingFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName);

    UserInfoResponse getAcceptFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName);
    UserInfoResponse getFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String userName, String loginName);
    UserInfoResponse getInviteListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName, List<String> roomUsers);

    UserInfoResponse getInviteSearchListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName, String inviteSearchTerm, List<String> roomUsers);
    UserInfoDTO getFirstUser(String userName, String loginName);
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
