package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.chatting.request.InviteRequest;
import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.response.ApiResponse;
import com.skhkim.instaclone.response.UserInfoResponse;
import com.skhkim.instaclone.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        UserInfoResponse result =
                friendService.selectWaitingFriend(userInfoPageRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("/selectAcceptUsersInfo")
    public ResponseEntity
    getProfileImageAcceptedList(UserInfoPageRequest userInfoPageRequest){
        UserInfoResponse result =
                friendService.selectAcceptUsersInfo(userInfoPageRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/selectUserFriendsInfo")
    public ResponseEntity
    selectUserFriendsInfo(UserInfoPageRequest userInfoPageRequest, String userName){
        UserInfoResponse result =
                friendService.selectUserFriendsInfo(userInfoPageRequest, userName);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/selectInviteList")
    public ResponseEntity
    selectInviteList(UserInfoPageRequest userInfoPageRequest, @RequestParam List<String> roomUsers){
        UserInfoResponse result =
                friendService.selectInviteList(userInfoPageRequest, roomUsers);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("selectFristFriend")
    public ResponseEntity selectFristFriend(String userName){
        UserInfoDTO result = friendService.selectFristFriend(userName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/selectInviteSearchUsers")
    public ResponseEntity selectInviteSearchUsers(UserInfoPageRequest userInfoPageRequest, @RequestBody InviteRequest inviteRequest) {
        UserInfoResponse result = friendService.selectInviteSearchUsers(userInfoPageRequest, inviteRequest.getSearchTerm(), inviteRequest.getUserNames());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/selectFriendNum")
    public ResponseEntity selectFriendNum(String userName) {
        int result = friendService.selectFriendNum(userName);
        return ApiResponse.OK(result);
    }

}
