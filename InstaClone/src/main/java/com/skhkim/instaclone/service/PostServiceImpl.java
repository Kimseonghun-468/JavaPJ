package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.PageRequestDTO;
import com.skhkim.instaclone.dto.PageResultDTO;
import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.PostImage;
import com.skhkim.instaclone.repository.PostImageRepository;
import com.skhkim.instaclone.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    @Override
    @Transactional
    public Long register(PostDTO postDTO){

        Map<String, Object> entityMap = dtoToEntity(postDTO);
        Post post = (Post) entityMap.get("post");
        List<PostImage> postImageList = (List<PostImage>) entityMap.get("imgList");

        postRepository.save(post);

        postImageList.forEach(postImage -> {
            postImageRepository.save(postImage);
        });

            return post.getPno();
    }
    @Override
    public PageResultDTO<PostDTO, Object[]> getList(PageRequestDTO pageRequestDTO, String email){
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("pno").descending());

        Page<Object[]> result = postRepository.getListPage(pageable, email);
        Function<Object[], PostDTO> fn = (arr -> entitiesToDTO(
                (Post)arr[0],
                (List<PostImage>)(Arrays.asList((PostImage)arr[1])))
        );
        return new PageResultDTO<>(result, fn);
    }
    @Override
    public Long getPostNumber(String email){


        return postRepository.getPostCount(email);
    }
    @Override
    public String getEmailByUserName(String userName){

        return postRepository.getEmail(userName);
    }
}
