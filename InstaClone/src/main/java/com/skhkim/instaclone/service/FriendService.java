package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.entity.type.FriendStatus;
import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.response.UserInfoResponse;

import java.util.List;

public interface FriendService {

    boolean createFriendShip(String userName);

    boolean checkDuplication(String loginName, String userName);

    FriendStatus checkFriendShip(String loginName, String userName);

    boolean acceptFriendShip(String userName);

    boolean deleteFriendShip(String userName);

    int selectFriendNum(String userName);

    UserInfoResponse selectWaitingFriend(UserInfoPageRequest userInfoPageRequest);

    UserInfoResponse selectAcceptUsersInfo(UserInfoPageRequest userInfoPageRequest);

    UserInfoResponse selectUserFriendsInfo(UserInfoPageRequest userInfoPageRequest, String userName);

    UserInfoResponse selectInviteList(UserInfoPageRequest userInfoPageRequest, List<String> roomUsers);

    UserInfoResponse selectInviteSearchUsers(UserInfoPageRequest userInfoPageRequest, String inviteSearchTerm, List<String> roomUsers);

    UserInfoDTO selectFristFriend(String userName);
}
