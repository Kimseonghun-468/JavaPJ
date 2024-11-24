package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.chatting.request.InviteRequest;
import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.response.ApiResponse;
import com.skhkim.instaclone.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/friend")
public class FriendApiController {

    private final FriendService friendService;

    @PostMapping("/requestFriend")
    public ResponseEntity request(String userName){
        boolean result = friendService.createFriendShip(userName);
        return ApiResponse.OK(result);
    }

    @PostMapping("/acceptFriend")
    public ResponseEntity acceptFriend(String userName){
        boolean result = friendService.acceptFriendShip(userName);
        return ApiResponse.OK(result);
    }

    @PostMapping("/deleteFriend")
    public ResponseEntity deleteFriend(String userName){
        boolean result = friendService.deleteFriendShip(userName);
        return ApiResponse.OK(result);
    }

    @PostMapping("/selectWaitingFriend")
    public ResponseEntity
    selectWaitingFriend(UserInfoPageRequest userInfoPageRequest){
        return ApiResponse.OK(friendService.selectWaitingFriend(userInfoPageRequest));
    }

    @PostMapping("/selectAcceptUsersInfo")
    public ResponseEntity
    selectAcceptUsersInf(UserInfoPageRequest userInfoPageRequest){
        return ApiResponse.OK(friendService.selectAcceptUsersInfo(userInfoPageRequest));
    }

    @PostMapping("/selectUserFriendsInfo")
    public ResponseEntity
    selectUserFriendsInfo(UserInfoPageRequest userInfoPageRequest, String userName){
        return ApiResponse.OK(friendService.selectUserFriendsInfo(userInfoPageRequest, userName));
    }

    @PostMapping("/selectInviteList")
    public ResponseEntity
    selectInviteList(UserInfoPageRequest userInfoPageRequest, @RequestParam List<String> roomUsers){
        return ApiResponse.OK(friendService.selectInviteList(userInfoPageRequest, roomUsers));
    }

    @PostMapping("selectFristFriend")
    public ResponseEntity selectFristFriend(String userName){
        return ApiResponse.OK(friendService.selectFristFriend(userName));
    }

    @PostMapping("/selectInviteSearchUsers")
    public ResponseEntity selectInviteSearchUsers(UserInfoPageRequest userInfoPageRequest, @RequestBody InviteRequest inviteRequest) {
        return ApiResponse.OK(friendService.selectInviteSearchUsers(userInfoPageRequest, inviteRequest.getSearchTerm(), inviteRequest.getUserNames()));
    }
    @PostMapping("/selectFriendNum")
    public ResponseEntity selectFriendNum(String userName) {
        return ApiResponse.OK(friendService.selectFriendNum(userName));
    }

}
