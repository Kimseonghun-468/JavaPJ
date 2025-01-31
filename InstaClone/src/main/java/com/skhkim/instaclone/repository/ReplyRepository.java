package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.Reply;
import com.skhkim.instaclone.repository.querydsl.ReplyCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long>, ReplyCustom {
}
