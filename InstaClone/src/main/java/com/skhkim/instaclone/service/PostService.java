package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.request.PostPageRequest;
import com.skhkim.instaclone.response.PostResponse;

public interface PostService {

    PostResponse getList(PostPageRequest postPageRequest, String name);
    void register(PostDTO postDTO);

    void modifyTitle(PostDTO postDTO);

    Long getPostNumber(String name);

    void removePost(Long pno);

    String getEmailByUserName(String userName);

    PostDTO getPostWithAllImage(Long postID);
}
