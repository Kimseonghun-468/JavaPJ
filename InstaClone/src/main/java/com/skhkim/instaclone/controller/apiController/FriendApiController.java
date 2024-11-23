package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.dto.ProfilePageRequestDTO;
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
    @PostMapping("/request")
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




    @PostMapping("waitingList")
    public ResponseEntity
    getProfileImagebyClubMember(ProfilePageRequestDTO profilePageRequestDTO, String loginName){
        UserInfoResponse result =
                profileService.getWaitingFriendListPage(profilePageRequestDTO, loginName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("selectAcceptUsersInfo")
    public ResponseEntity
    getProfileImageAcceptedList(ProfilePageRequestDTO profilePageRequestDTO, String loginName){
        UserInfoResponse result =
                profileService.getAcceptFriendListPage(profilePageRequestDTO, loginName);
        return ResponseEntity.ok(result);
    }

    @PostMapping("friendList")
    public ResponseEntity
    getProfileImageFriendList(ProfilePageRequestDTO profilePageRequestDTO, String userName, String loginName){
        UserInfoResponse result =
                profileService.getFriendListPage(profilePageRequestDTO, userName, loginName);
        return ResponseEntity.ok(result);
    }

    @PostMapping("inviteList")
    public ResponseEntity
    getProfileImageInviteList(ProfilePageRequestDTO profilePageRequestDTO, String loginName,@RequestParam List<String> roomUsers){
        UserInfoResponse result =
                profileService.getInviteListPage(profilePageRequestDTO, loginName, roomUsers);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("firstList")
    public ResponseEntity getProfileFirst(String userName, String loginName){
        UserInfoDTO result = profileService.getFirstUser(userName, loginName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/selectFriendNum")
    public ResponseEntity selectFriendNum(String userName) {
        int result = friendShipService.selectFriendNum(userName);
        return ApiResponse.OK(result);
    }
}
