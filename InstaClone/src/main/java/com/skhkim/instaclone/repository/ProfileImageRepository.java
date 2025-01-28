package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    @Query("SELECT pi FROM ProfileImage pi WHERE pi.clubMember.name = :userName ORDER BY pi.modDate DESC LIMIT 1")
    ProfileImage findByUserName(@Param("userName") String userName);

    @Query("SELECT pi FROM ProfileImage pi WHERE pi.clubMember.email = :userEmail")
    ProfileImage findByUserEmail(@Param("userEmail") String userEmail);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProfileImage pi WHERE pi.clubMember.email = :userEmail")
    void deleteByUserEmail(@Param("userEmail") String userEmail);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProfileImage pi WHERE pi.clubMember.name = :userName")
    void deleteByUserName(@Param("userName") String userName);
}
