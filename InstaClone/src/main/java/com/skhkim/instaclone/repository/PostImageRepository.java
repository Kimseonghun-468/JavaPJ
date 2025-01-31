package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, String>, PostImageCustom {

}
