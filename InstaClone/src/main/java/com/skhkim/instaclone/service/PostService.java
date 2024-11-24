package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.request.PostPageRequest;
import com.skhkim.instaclone.response.PostResponse;

public interface PostService {

    PostResponse selectPostList(PostPageRequest postPageRequest, String name);
    boolean insert(PostDTO postDTO);

    boolean update(PostDTO postDTO);

    Long selectPostNumber(String name);

    boolean delete(Long pno);

    PostDTO selectPostDetail(Long postId);
}
