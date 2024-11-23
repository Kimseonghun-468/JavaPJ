package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.response.ApiResponse;
import com.skhkim.instaclone.response.ReplyResponse;
import com.skhkim.instaclone.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/reply")
@Log4j2
@RequiredArgsConstructor
public class ReplyApiController {
    private final ReplyService replyService;

    @PostMapping("/selectReplyList")
    public ResponseEntity selectReplyList(PageRequestDTO pageRequestDTO, Long pno){
        ReplyResponse replyResponse = replyService.selectReplyList(pageRequestDTO, pno);

        return ResponseEntity.ok(replyResponse);

    }

    //    @PostMapping("/{pno}") // ksh edit
//    public ResponseEntity<Map<String, Object>> addReview(@RequestBody ReplyDTO postReplyDTO){
//
//        Map<String, Object> result = new HashMap<>();
//        Long rno = replyService.register(postReplyDTO);
//        Optional<ProfileImage> profileImage = profileImageRepository.getProfileImageByUserEmail(postReplyDTO.getEmail());
//        if(profileImage.isPresent())
//            result.put("imageUrl", profileImage.get().getImageURL());
//        else
//            result.put("imageUrl", null);
//        result.put("rno", rno);
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
    @DeleteMapping("/deleteReply")
    public ResponseEntity deleteReply(Long replynum){
        replyService.remove(replynum);
        return ApiResponse.OK();
    }
}
