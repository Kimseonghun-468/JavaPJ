package com.skhkim.instaclone.repository.querydsl;

import com.skhkim.instaclone.entity.PostImage;

import java.util.List;

public interface PostImageCustom {
    void delete(Long pno);

    List<PostImage> selectPostImages(Long pno);

}
