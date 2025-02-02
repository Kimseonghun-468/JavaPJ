package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.repository.querydsl.ClubMemberCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long>, ClubMemberCustom {

    ClubMember findByName(@Param("name") String name);

    boolean existsByEmail(@Param("email") String email);

    boolean existsByName(@Param("name") String name);
}
