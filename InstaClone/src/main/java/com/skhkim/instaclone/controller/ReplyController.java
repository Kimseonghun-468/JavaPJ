package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.dto.PageResultDTO;
import com.skhkim.instaclone.dto.ProfileImageDTO;
import com.skhkim.instaclone.dto.ReplyDTO;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.repository.ProfileImageRepository;
import com.skhkim.instaclone.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reply")
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class ReplyController {
    private final ReplyService replyService;
    private final ProfileImageRepository profileImageRepository;
    @GetMapping("{pno}/all")
    public ResponseEntity<PageResultDTO> getList(PageRequestDTO pageRequestDTO, @PathVariable("pno") Long pno){
        log.info("MNO : " + pno);

        PageResultDTO reviewDTOList = replyService.getListOfPostPage(pageRequestDTO, pno);
        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }
    @PostMapping("/{pno}")
    public ResponseEntity<Map<String, Object>> addReview(@RequestBody ReplyDTO postReplyDTO){

        Map<String, Object> result = new HashMap<>();
        Long rno = replyService.register(postReplyDTO);
        Optional<ProfileImage> profileImage = profileImageRepository.getProfileImageByUserEmail(postReplyDTO.getEmail());
        if(profileImage.isPresent())
            result.put("imageUrl", profileImage.get().getImageURL());
        else
            result.put("imageUrl", null);
        result.put("rno", rno);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("/{replynum}")
    public ResponseEntity<Long> removeReply(@PathVariable Long replynum){
        log.info("replynum : " + replynum);

        replyService.remove(replynum);

        return new ResponseEntity<>(replynum, HttpStatus.OK);
    }
}
