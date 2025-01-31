package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.repository.querydsl.PostCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String>, PostCustom {
}
