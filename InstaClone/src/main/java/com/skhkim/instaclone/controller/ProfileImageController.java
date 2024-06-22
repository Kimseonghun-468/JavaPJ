package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
@RestController
@RequestMapping("/profileImage")
@Log4j2
@RequiredArgsConstructor
public class ProfileImageController {
    private final ProfileService profileService;

    @PostMapping("waitingList")
    public ResponseEntity<ProfilePageResultDTO<Map<String, Object>, Object[]>>
    getProfileImagebyClubMember(ProfilePageRequestDTO profilePageRequestDTO, String loginName){
        ProfilePageResultDTO<Map<String, Object>, Object[]> profileAndFriendMap =
                profileService.getWaitingFriendListPage(profilePageRequestDTO, loginName);
        return new ResponseEntity<>(profileAndFriendMap, HttpStatus.OK);
    }


    @PostMapping("acceptedList")
    public ResponseEntity<ProfilePageResultDTO<Map<String, Object>, Object[]>>
    getProfileImageAcceptedList(ProfilePageRequestDTO profilePageRequestDTO, String loginName){
        ProfilePageResultDTO<Map<String, Object>, Object[]> profileAndFriendMap =
                profileService.getAcceptFriendListPage(profilePageRequestDTO, loginName);
        return new ResponseEntity<>(profileAndFriendMap, HttpStatus.OK);
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
    getProfileImageInviteList(ProfilePageRequestDTO profilePageRequestDTO, String userName, String loginName){
        ProfilePageResultDTO<Map<String, Object>, Object[]> profileAndInviteMap =
                profileService.getFriendListPage(profilePageRequestDTO, userName, loginName);// 여기 함수 바꾸
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
