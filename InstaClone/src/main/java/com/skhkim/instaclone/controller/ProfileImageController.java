package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.response.UserInfoResponse;
import com.skhkim.instaclone.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/profileImage")
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class ProfileImageController {
    private final ProfileService profileService;

    @PostMapping("waitingList")
    public ResponseEntity<ProfilePageResultDTO<Map<String, Object>, Object[]>>
    getProfileImagebyClubMember(ProfilePageRequestDTO profilePageRequestDTO, String loginName){
        ProfilePageResultDTO<Map<String, Object>, Object[]> profileAndFriendMap =
                profileService.getWaitingFriendListPage(profilePageRequestDTO, loginName);
        return new ResponseEntity<>(profileAndFriendMap, HttpStatus.OK);
    }


    @PostMapping("selectAcceptUsersInfo")
    public ResponseEntity
    getProfileImageAcceptedList(ProfilePageRequestDTO profilePageRequestDTO, String loginName){
        UserInfoResponse result =
                profileService.getAcceptFriendListPage(profilePageRequestDTO, loginName);
        return ResponseEntity.ok(result);
    }

    @PostMapping("friendList")
    public ResponseEntity<ProfilePageResultDTO<Map<String, Object>, Object[]>>
    getProfileImageFriendList(ProfilePageRequestDTO profilePageRequestDTO, String userName, String loginName){
        ProfilePageResultDTO<Map<String, Object>, Object[]> profileAndFriendMap =
                profileService.getFriendListPage(profilePageRequestDTO, userName, loginName);
        return new ResponseEntity<>(profileAndFriendMap, HttpStatus.OK);
    }

    @PostMapping("inviteList")
    public ResponseEntity<ProfilePageResultDTO<Map<String, Object>, Object[]>>
    getProfileImageInviteList(ProfilePageRequestDTO profilePageRequestDTO, String loginName,@RequestParam List<String> roomUsers){
        ProfilePageResultDTO<Map<String, Object>, Object[]> profileAndInviteMap =
                profileService.getInviteListPage(profilePageRequestDTO, loginName, roomUsers);// 여기 함수 바꾸
        return new ResponseEntity<>(profileAndInviteMap, HttpStatus.OK);
    }

    @PostMapping("firstList")
    public ResponseEntity<Map<String, Object>> getProfileFirst(String userName, String loginName){
        Map<String, Object> resultMap = profileService.getFirstUser(userName, loginName);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PostMapping("deleteProfile")
    public ResponseEntity<String> deleteByName(String userName){
        profileService.deleteByName(userName);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

}
