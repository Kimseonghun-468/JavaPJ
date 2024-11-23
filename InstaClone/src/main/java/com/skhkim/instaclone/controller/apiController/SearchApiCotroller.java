package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.chatting.request.InviteRequest;
import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.response.UserInfoResponse;
import com.skhkim.instaclone.service.MemberService;
import com.skhkim.instaclone.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class SearchApiCotroller {
    private final MemberService memberService;
    private final ProfileService profileService;

    @PostMapping("/name/all")
    public ResponseEntity getListByNameAll
            (ProfilePageRequestDTO profilePageRequestDTO, String searchName, String loginName) {
        UserInfoResponse result =
                memberService.getClubMemberSearchbyNameAll(profilePageRequestDTO, searchName, loginName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity getInivteListByNameAll(ProfilePageRequestDTO profilePageRequestDTO, @RequestBody InviteRequest inviteRequest) {
        UserInfoResponse result = profileService.getInviteSearchListPage(profilePageRequestDTO, inviteRequest.getLoginName(), inviteRequest.getSearchTerm(), inviteRequest.getUserNames());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

