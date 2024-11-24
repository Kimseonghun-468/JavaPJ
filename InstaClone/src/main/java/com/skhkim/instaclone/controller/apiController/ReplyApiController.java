package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.dto.ReplyDTO;
import com.skhkim.instaclone.response.ApiResponse;
import com.skhkim.instaclone.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reply")
@Log4j2
@RequiredArgsConstructor
public class ReplyApiController {
    private final ReplyService replyService;

    @PostMapping("/selectReplyList")
    public ResponseEntity selectReplyList(PageRequestDTO pageRequestDTO, Long pno){
        return ApiResponse.OK(replyService.selectReplyList(pageRequestDTO, pno));
    }

    @PostMapping("/insert")
    public ResponseEntity insert(@RequestBody ReplyDTO replyDTO){
        return ApiResponse.OK(replyService.register(replyDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteReply(Long rno){
        return ApiResponse.OK(replyService.remove(rno));
    }
}
