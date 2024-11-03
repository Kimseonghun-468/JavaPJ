package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.response.UserInfoResponse;
import com.skhkim.instaclone.service.LoginService;
import com.skhkim.instaclone.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class SearchCotroller {
    private final LoginService loginService;
    private final ProfileService profileService;

    @GetMapping("/name/{name}")
    public ResponseEntity<Map<String, Object>> getListByName(@PathVariable("name") String name) {
        ClubMemberDTO clubMemberDTO = loginService.getClubMemberSearchbyName(name);
        Map<String, Object> response = getClubMemberDTOAndProfileImageDTO(clubMemberDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> getListByEmail(@PathVariable("email") String email) {
        ClubMemberDTO clubMemberDTO = loginService.getClubMemberSearchbyEmail(email);
        Map<String, Object> response = getClubMemberDTOAndProfileImageDTO(clubMemberDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/name/all")
    public ResponseEntity getListByNameAll
            (ProfilePageRequestDTO profilePageRequestDTO, String searchName, String loginName) {
        UserInfoResponse result =
                loginService.getClubMemberSearchbyNameAll(profilePageRequestDTO, searchName, loginName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity getInivteListByNameAll(ProfilePageRequestDTO profilePageRequestDTO, String inviteSearchTerm, String loginName, @RequestParam List<String> roomUsers) {
        UserInfoResponse result = profileService.getInviteSearchListPage(profilePageRequestDTO, loginName, inviteSearchTerm, roomUsers);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private Map<String, Object> getClubMemberDTOAndProfileImageDTO
            (ClubMemberDTO clubMemberDTO) {

        String clubMemberEmail = clubMemberDTO.getEmail();
        String clubMemberName = clubMemberDTO.getName();
        ProfileImageDTO profileImageDTO = profileService.getProfileImage(clubMemberName);
        Map<String, Object> response = new HashMap<>();
        response.put("clubMemberEmail", clubMemberEmail);
        response.put("clubMemberName", clubMemberName);
        response.put("profileImageDTO", profileImageDTO);
        return response;
    }
}

