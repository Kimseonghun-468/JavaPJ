package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByPost(Post post);
    @Modifying
    @Transactional
    @Query("DELETE FROM Reply r WHERE r.post.pno = :pno")
    void deleteByPostPno(Long pno);
}
