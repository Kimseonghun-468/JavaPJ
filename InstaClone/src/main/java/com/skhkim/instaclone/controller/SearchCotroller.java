package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.dto.ReplyDTO;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.service.LoginService;
import com.skhkim.instaclone.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
public class SearchCotroller {
    private final LoginService loginService;
    private final ReplyService replyService;
    @GetMapping("{name}/all")
    public ResponseEntity<ClubMemberDTO> getList(@PathVariable("name") String name){
        log.info("---------list-------------");
        log.info("MNO : " + name);
        ClubMemberDTO clubMemberDTOList = loginService.getClubMemberSearch(name);
        log.info("member name : " + clubMemberDTOList);
        return new ResponseEntity<>(clubMemberDTOList, HttpStatus.OK);
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
