package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/profileImage")
@Log4j2
@RequiredArgsConstructor
public class ProfileImageController {
    private final ProfileService profileService;

//    @PostMapping("waitingList")
//    public ResponseEntity<Map<String, Object>>
//    getProfileImagebyClubMember(String loginName){
//        Map<String, Object> profileAndFriendMap =
//                profileService.getWaitingFriendList(loginName);
//        return new ResponseEntity<>(profileAndFriendMap, HttpStatus.OK);
//    }

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

}
