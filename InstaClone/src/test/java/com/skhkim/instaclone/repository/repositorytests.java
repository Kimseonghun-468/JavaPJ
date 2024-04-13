package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.dto.ProfileImageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@SpringBootTest
public class repositorytests {

    @Autowired
    private PostRepository postRepository;
    private ProfileImageRepository profileImageRepository;
    @Test
    public void testListPage(){
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "pno"));
        Page<Object[]> result = postRepository.getListPage(pageRequest, "seonghun@naver.com");
//        Long count = postRepository.getPostCount("seonghun@naver.com");
//        System.out.println(count);
        for(Object[] objects : result.getContent()){
            System.out.println(Arrays.toString(objects));
        }
    }
//    @Test
//    public void testProfileImage(){
//        ProfileImageDTO =
//    }
}
