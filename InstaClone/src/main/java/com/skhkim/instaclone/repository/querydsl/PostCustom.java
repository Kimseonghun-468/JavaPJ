package com.skhkim.instaclone.repository.querydsl;

import com.skhkim.instaclone.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostCustom {
    Slice<Post> selectList(Pageable pageable, String name);
    Post select(Long pno);
    Long getPostCount(String name);
    boolean validation(Long pno, Long userId);
    void delete(Long pno);
}
