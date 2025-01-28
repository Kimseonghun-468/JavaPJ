package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, String> {

    @Modifying
    @Transactional
    @Query("DELETE FROM PostImage pi WHERE pi.post.pno = :pno")
    void deleteByPostPno(@Param("pno") Long pno);

    @Query("SELECT pi FROM PostImage pi WHERE pi.post.pno =:pno")
    List<PostImage> findByPno(@Param("pno") Long pno);
}
