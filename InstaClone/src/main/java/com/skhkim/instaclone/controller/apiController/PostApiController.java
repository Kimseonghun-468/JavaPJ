package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.dto.PostPageRequestDTO;
import com.skhkim.instaclone.response.ApiResponse;
import com.skhkim.instaclone.response.PostResponse;
import com.skhkim.instaclone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostApiController {

    private final PostService postService;
    @PostMapping("/postInfoWithImage")
    public ResponseEntity getPostInfoWithImage(Long pno){
        PostDTO result = postService.getPostWithAllImage(pno);
        return ApiResponse.OK(result);
    }

    @PostMapping("/getPostInfo")
    public ResponseEntity getPostInfo(PostPageRequestDTO postPageRequestDTO, String userName){
        PostResponse result =  postService.getList(postPageRequestDTO, userName);
        return ApiResponse.OK(result);
    }
    @DeleteMapping("/delete/{pno}")
    public ResponseEntity removePost(@PathVariable Long pno){
        postService.removePost(pno);
        return ApiResponse.OK(pno);
    }

    @PostMapping("/insertPost")
    public ResponseEntity insertPost(@RequestBody PostDTO postDTO){ // ksh edit : 이거 redirection은 그냥 js에서 하고 반환은 제대로 해야겠음.
        postService.register(postDTO);
        return ApiResponse.OK();
    }

    @PostMapping("/updatePost")
    public ResponseEntity updatePost(@RequestBody PostDTO postDTO){
        postService.modifyTitle(postDTO);
        return ApiResponse.OK();
    }

    @PostMapping("/selectPostNum")
    public ResponseEntity selectPostNum(String userName) {
        Long result = postService.getPostNumber(userName);
        return ApiResponse.OK(result);
    }
}
