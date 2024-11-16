package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
//    List<Reply> findByPost(Post post);
    @Query("SELECT r FROM Reply r where r.post.pno = :pno ORDER BY r.rno DESC")
    Page<Reply> findByPost(Pageable pageable, Long pno);

    @Query("SELECT r FROM Reply r where r.post.pno = :pno ORDER BY r.rno DESC")
    Slice<Reply> selectReplyList(Pageable pageable, Long pno);

    @Modifying
    @Transactional
    @Query("DELETE FROM Reply r WHERE r.post.pno = :pno")
    void deleteByPostPno(Long pno);
}
