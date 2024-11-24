package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.response.ApiResponse;
import com.skhkim.instaclone.response.UserInfoResponse;
import com.skhkim.instaclone.service.FriendShipService;
import com.skhkim.instaclone.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/friend")
public class FriendApiController {

    private final FriendShipService friendShipService;
    private final ProfileService profileService;
    @PostMapping("/requestFriend")
    public ResponseEntity request(String userName){
        boolean result = friendShipService.createFriendShip(userName);
        return ApiResponse.OK(result);
    }

    @PostMapping("/acceptFriend")
    public ResponseEntity acceptFriend(String userName){
        boolean result = friendShipService.acceptFriendShip(userName);
        return ApiResponse.OK(result);
    }

    @PostMapping("/deleteFriend")
    public ResponseEntity deleteFriend(String userName){
        boolean result = friendShipService.deleteFriendShip(userName);
        return ApiResponse.OK(result);
    }

    @PostMapping("selectWaitingFriend")
    public ResponseEntity
    selectWaitingFriend(UserInfoPageRequest userInfoPageRequest){
        UserInfoResponse result =
                profileService.getWaitingFriendListPage(userInfoPageRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("selectAcceptUsersInfo")
    public ResponseEntity
    getProfileImageAcceptedList(UserInfoPageRequest userInfoPageRequest){
        UserInfoResponse result =
                profileService.getAcceptFriendListPage(userInfoPageRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("friendList")
    public ResponseEntity
    getProfileImageFriendList(UserInfoPageRequest userInfoPageRequest, String userName){
        UserInfoResponse result =
                profileService.getFriendListPage(userInfoPageRequest, userName);
        return ResponseEntity.ok(result);
    }

    @PostMapping("inviteList")
    public ResponseEntity
    getProfileImageInviteList(UserInfoPageRequest userInfoPageRequest, @RequestParam List<String> roomUsers){
        UserInfoResponse result =
                profileService.getInviteListPage(userInfoPageRequest, roomUsers);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("firstList")
    public ResponseEntity getProfileFirst(String userName){
        UserInfoDTO result = profileService.getFirstUser(userName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/selectFriendNum")
    public ResponseEntity selectFriendNum(String userName) {
        int result = friendShipService.selectFriendNum(userName);
        return ApiResponse.OK(result);
    }
}
