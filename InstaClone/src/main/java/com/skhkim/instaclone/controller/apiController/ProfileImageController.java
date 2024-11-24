package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.dto.ProfileImageDTO;
import com.skhkim.instaclone.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/profileImage")
@Log4j2
@RequiredArgsConstructor

public class ProfileImageController {
    private final ProfileService profileService;

    @PostMapping("deleteProfile")
    public ResponseEntity<String> deleteByName(){
        profileService.delete();
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }
    @PostMapping("/{name}")
    public String profileImage(@PathVariable("name") String name, ProfileImageDTO profileImageDTO) throws UnsupportedEncodingException {
        profileService.register(profileImageDTO);
        String encodedName = URLEncoder.encode(name, "UTF-8");
        return "redirect:/sidebar/"+encodedName;
    }
}
