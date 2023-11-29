package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.ReplyDTO;
import com.skhkim.instaclone.entity.Reply;
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
@RequestMapping("/reply")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
    @GetMapping("{pno}/all")
    public ResponseEntity<List<ReplyDTO>> getList(@PathVariable("pno") Long pno){
        log.info("---------list-------------");
        log.info("MNO : " + pno);

        List<ReplyDTO> reviewDTOList = replyService.getListOfPost(pno);
        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }
}
