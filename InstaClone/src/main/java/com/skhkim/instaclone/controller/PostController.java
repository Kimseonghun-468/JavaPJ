package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@Log4j2
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping("/postInfo")
    public ResponseEntity<PostDTO> getPostInfo(Long pno){
        log.info("Post number : " + pno);
        PostDTO postDTO = postService.getPostWithAllImage(pno);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }
}
