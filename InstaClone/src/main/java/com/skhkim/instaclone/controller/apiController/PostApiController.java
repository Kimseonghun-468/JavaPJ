package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.request.PostPageRequest;
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
    @PostMapping("/selectPostDetail")
    public ResponseEntity selectPostDetail(Long pno){
        PostDTO result = postService.selectPostDetail(pno);
        return ApiResponse.OK(result);
    }

    @PostMapping("/selectPostList")
    public ResponseEntity selectPostList(PostPageRequest postPageRequest, String userName){
        PostResponse result =  postService.selectPostList(postPageRequest, userName);
        return ApiResponse.OK(result);
    }
    @DeleteMapping("/delete")
    public ResponseEntity delete(Long pno){
        return ApiResponse.OK(postService.delete(pno));
    }

    @PostMapping("/insertPost")
    public ResponseEntity insertPost(@RequestBody PostDTO postDTO){
        return ApiResponse.OK(postService.insert(postDTO));
    }

    @PostMapping("/updatePost")
    public ResponseEntity updatePost(@RequestBody PostDTO postDTO){
        return ApiResponse.OK(postService.update(postDTO));
    }

    @PostMapping("/selectPostNum")
    public ResponseEntity selectPostNum(String userName) {
        return ApiResponse.OK(postService.selectPostNumber(userName));
    }
}
