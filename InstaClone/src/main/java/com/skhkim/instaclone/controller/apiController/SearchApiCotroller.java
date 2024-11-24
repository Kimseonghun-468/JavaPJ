package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.response.UserInfoResponse;
import com.skhkim.instaclone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor

public class SearchApiCotroller {
    private final MemberService memberService;

    @PostMapping("/name/all")
    public ResponseEntity getListByNameAll
            (UserInfoPageRequest userInfoPageRequest, String searchName, String loginName) {
        UserInfoResponse result =
                memberService.getClubMemberSearchbyNameAll(userInfoPageRequest, searchName, loginName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}

