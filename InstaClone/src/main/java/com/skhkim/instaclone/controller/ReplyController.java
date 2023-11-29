package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.ReplyDTO;
import com.skhkim.instaclone.entity.Reply;
import com.skhkim.instaclone.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/{pno}")
    public ResponseEntity<Long> addReview(@RequestBody ReplyDTO postReplyDTO){
        log.info("------------add MovieReivew------------");
        log.info("reviewDTO : " + postReplyDTO);

        Long rno = replyService.register(postReplyDTO);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }
}
