package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.dto.PostPageRequestDTO;
import com.skhkim.instaclone.response.PostResponse;
import com.skhkim.instaclone.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/post")
@Log4j2
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping("/postInfoWithImage")
    public ResponseEntity<PostDTO> getPostInfoWithImage(Long pno){
        log.info("Post number : " + pno);
        PostDTO postDTO = postService.getPostWithAllImage(pno);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping("/getPostInfo")
    public ResponseEntity getPostInfo(PostPageRequestDTO postPageRequestDTO, String userName){
        PostResponse result =  postService.getList(postPageRequestDTO, userName);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getPostInfobyID/{pno}")
    public ResponseEntity<PostDTO> getPostInfoByID(@PathVariable("pno") Long pno){
        PostDTO postDTO = postService.getPostByID(pno);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{pno}")
    public ResponseEntity<Long> removePost(@PathVariable Long pno){

        postService.removePost(pno);
        return new ResponseEntity<>(pno, HttpStatus.OK);
    }
}
