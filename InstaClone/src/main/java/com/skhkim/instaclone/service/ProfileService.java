package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ProfileService {
    Long register(ProfileImageDTO profileImageDTO);
    void deleteByName(String name);
    ProfileImageDTO getProfileImage(String name);
    Map<String, Object> getWaitingFriendList(String loginName);
    Map<String, Object> getAcceptFriendList(String loginName);
    ProfilePageResultDTO<Map<String, Object>, Object[]> getWaitingFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName);
    ProfilePageResultDTO<Map<String, Object>, Object[]> getAcceptFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName);
    ProfilePageResultDTO<Map<String, Object>, Object[]> getFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String userName, String loginName);

    Map<String, Object> getFirstUser(String userName, String loginName);
    default ProfileImage dtoToEntity(ProfileImageDTO profileImageDTO){

        ProfileImage profileImage = ProfileImage.builder()
                .userName(profileImageDTO.getUserName())
                .path(profileImageDTO.getPath())
                .uuid(profileImageDTO.getUuid())
                .imgName(profileImageDTO.getImgName())
                .userEmail(profileImageDTO.getUserEmail())
                .build();

        return profileImage;
    }
    default ProfileImageDTO entityToDTO(ProfileImage profileImage){

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
