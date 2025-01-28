package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, String> {

    @Query("SELECT p FROM Post p " +
            "WHERE p.clubMember.name = :name " +
            "ORDER BY p.pno DESC")
    Slice<Post> selectPostList(Pageable pageable, @Param("name") String name);

    @Query("SELECT p FROM Post p WHERE p.pno =:pno")
    Post selectPost(@Param("pno") Long pno);

    @Query("SELECT count(p) FROM Post p " +
            "where p.clubMember.name = :name ")
    Long getPostCount(@Param("name") String name);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Post p WHERE p.clubMember.email = :loginEmail and p.pno = :pno")
    boolean checkValidation(@Param("pno") Long pno, @Param("loginEmail") String loginEmail);

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.pno = :pno")
    void deleteByPno(@Param("pno") Long pno);
}
