package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.Reply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT r FROM Reply r WHERE r.post.pno = :pno ORDER BY r.rno DESC")
    Slice<Reply> selectReplyList(Pageable pageable, @Param("pno") Long pno);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Reply r WHERE r.clubMember.email = :loginEmail AND r.rno = :rno")
    boolean checkValidation(@Param("rno") Long rno, @Param("loginEmail") String loginEmail);

    @Modifying
    @Transactional
    @Query("DELETE FROM Reply r WHERE r.post.pno = :pno")
    void deleteByPostPno(@Param("pno") Long pno);
}
