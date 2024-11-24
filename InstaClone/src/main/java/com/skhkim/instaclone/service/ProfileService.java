package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.ProfileImageDTO;
import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.response.UserInfoResponse;

import java.util.List;

public interface ProfileService {
    void register(ProfileImageDTO profileImageDTO);
    void deleteByName(String name);
    ProfileImageDTO getProfileImage(String name);
    UserInfoResponse getWaitingFriendListPage(UserInfoPageRequest userInfoPageRequest);

    UserInfoResponse getAcceptFriendListPage(UserInfoPageRequest userInfoPageRequest);
    UserInfoResponse getFriendListPage(UserInfoPageRequest userInfoPageRequest, String userName);
    UserInfoResponse getInviteListPage(UserInfoPageRequest userInfoPageRequest, List<String> roomUsers);

    UserInfoResponse getInviteSearchListPage(UserInfoPageRequest userInfoPageRequest, String loginName, String inviteSearchTerm, List<String> roomUsers);
    UserInfoDTO getFirstUser(String userName);

}
