package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.dto.PostPageRequestDTO;
import com.skhkim.instaclone.dto.PostPageResultDTO;
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
    @PostMapping("/postInfoWithImage")
    public ResponseEntity<PostDTO> getPostInfoWithImage(Long pno){
        log.info("Post number : " + pno);
        PostDTO postDTO = postService.getPostWithAllImage(pno);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping("/getPostInfo")
    public ResponseEntity<PostPageResultDTO<PostDTO, Object[]>> getPostInfo(PostPageRequestDTO postPageRequestDTO, String loginName){
        PostPageResultDTO<PostDTO, Object[]> result =  postService.getList(postPageRequestDTO, loginName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
