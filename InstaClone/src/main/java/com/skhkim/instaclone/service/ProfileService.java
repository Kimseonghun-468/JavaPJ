package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.ProfileImageDTO;
import com.skhkim.instaclone.dto.ProfilePageRequestDTO;
import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.response.UserInfoResponse;

import java.util.List;

public interface ProfileService {
    void register(ProfileImageDTO profileImageDTO);
    void deleteByName(String name);
    ProfileImageDTO getProfileImage(String name);
    UserInfoResponse getWaitingFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName);

    UserInfoResponse getAcceptFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName);
    UserInfoResponse getFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String userName, String loginName);
    UserInfoResponse getInviteListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName, List<String> roomUsers);

    UserInfoResponse getInviteSearchListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName, String inviteSearchTerm, List<String> roomUsers);
    UserInfoDTO getFirstUser(String userName, String loginName);

}
