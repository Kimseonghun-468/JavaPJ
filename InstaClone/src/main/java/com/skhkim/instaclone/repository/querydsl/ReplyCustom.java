package com.skhkim.instaclone.repository.querydsl;

import com.skhkim.instaclone.entity.Reply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReplyCustom {
    Slice<Reply> selectList(Pageable pageable, Long pno);

    boolean validation(Long rno, Long userId);

    void delete(Long pno);
}
