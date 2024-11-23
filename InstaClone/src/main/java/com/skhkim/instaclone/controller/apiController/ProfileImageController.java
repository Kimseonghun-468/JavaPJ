package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profileImage")
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class ProfileImageController {
    private final ProfileService profileService;

    @PostMapping("deleteProfile")
    public ResponseEntity<String> deleteByName(String userName){
        profileService.deleteByName(userName);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

}
