package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {
    @Query("SELECT p FROM Post p " +
            "WHERE p.clubMember.name = :name " +
            "ORDER BY p.pno DESC")
    Slice<Post> getListPage(Pageable pageable, String name);

    @Query("SELECT p, pi FROM Post p LEFT JOIN PostImage pi ON pi.post = p WHERE p.pno =:pno")
    List<Object[]> getPostWithAll(Long pno);

    @Query("SELECT p FROM Post p WHERE p.pno =:pno")
    Post selectPost(Long pno);

    @Query("SELECT count(p) FROM Post p " +
            "where p.clubMember.email = :email ")
    Long getPostCount(String email);

    @Query("SELECT m.email FROM ClubMember m " +
            "WHERE m.name = :name ")
    String getEmail(String name);

    Post findByPno(Long pno);

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.pno = :pno")
    void deleteByPno(Long pno);

}
