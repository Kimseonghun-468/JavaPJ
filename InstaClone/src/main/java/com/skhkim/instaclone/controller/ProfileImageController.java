package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.dto.FriendShipDTO;
import com.skhkim.instaclone.dto.FriendShipProfileDTO;
import com.skhkim.instaclone.dto.ProfileImageDTO;
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

@RestController
@RequestMapping("/profileImage")
@Log4j2
@RequiredArgsConstructor
public class ProfileImageController {
    private final ProfileService profileService;

    @PostMapping("waitingList")
    public ResponseEntity<List<FriendShipProfileDTO>>
    getProfileImagebyClubMember(@RequestBody String loginEmail){
        List<FriendShipProfileDTO> friendShipProfileDTOS =
                profileService.getProfileImageWaitingList(loginEmail);
        return new ResponseEntity<>(friendShipProfileDTOS, HttpStatus.OK);
    }

    @PostMapping("acceptedList")
    public ResponseEntity<List<FriendShipProfileDTO>>
    getProfileImageAcceptedList(@RequestBody String loginEmail){
        List<FriendShipProfileDTO> friendShipProfileDTOS =
                profileService.getProfileImageAcceptedList(loginEmail);
        return new ResponseEntity<>(friendShipProfileDTOS, HttpStatus.OK);
    }

}
