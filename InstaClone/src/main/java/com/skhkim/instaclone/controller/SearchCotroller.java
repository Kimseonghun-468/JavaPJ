package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.*;
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
    public ResponseEntity<Map<String, Object>> getListByName(@PathVariable("name") String name){
        ClubMemberDTO clubMemberDTO = loginService.getClubMemberSearchbyName(name);
        Map<String, Object> response = getClubMemberDTOAndProfileImageDTO(clubMemberDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> getListByEmail(@PathVariable("email") String email){
        ClubMemberDTO clubMemberDTO = loginService.getClubMemberSearchbyEmail(email);
        Map<String, Object> response = getClubMemberDTOAndProfileImageDTO(clubMemberDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/name/all/{name}")
    public ResponseEntity<ProfilePageResultDTO<Map<String, Object>, Object[]>> getListByNameAll(ProfilePageRequestDTO profilePageRequestDTO, @PathVariable("name") String name, String user){
        ProfilePageResultDTO<Map<String, Object>, Object[]> result = loginService.getClubMemberSearchbyNameAll(profilePageRequestDTO, name, user);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<ProfilePageResultDTO<Map<String, Object>, Object[]>> getInivteListByNameAll(ProfilePageRequestDTO profilePageRequestDTO, String inviteSearchTerm, String loginName, @RequestParam List<String> roomUsers){
        ProfilePageResultDTO<Map<String, Object>, Object[]> result = profileService.getInviteSearchListPage(profilePageRequestDTO, loginName, inviteSearchTerm, roomUsers);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    private Map<String, Object> getClubMemberDTOAndProfileImageDTO
            (ClubMemberDTO clubMemberDTO){

        String clubMemberEmail = clubMemberDTO.getEmail();
        String clubMemberName = clubMemberDTO.getName();
        ProfileImageDTO profileImageDTO = profileService.getProfileImage(clubMemberName);
        Map<String, Object> response = new HashMap<>();
        response.put("clubMemberEmail", clubMemberEmail);
        response.put("clubMemberName", clubMemberName);
        response.put("profileImageDTO", profileImageDTO);
        return response;
    }



//    @GetMapping("{name}")
//    public ResponseEntity<List<ClubMemberDTO>> getSearchList(@PathVariable("name") String name){
//        log.info("---------list-------------");
//        log.info("Search Tag : " + name);
//
//        List<ClubMemberDTO> clubMemberDTOList = loginService.//작업해야함-> list 형식으로 clubmember의 아이디를 찾아올 수 있는 무언가.
//        return new ResponseEntity<>(clubMemberDTOList, HttpStatus.OK);
//    }
}
