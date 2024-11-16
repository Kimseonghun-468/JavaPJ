package com.skhkim.instaclone.service;

import com.skhkim.instaclone.chatting.response.ChatMessageResponse;
import com.skhkim.instaclone.dto.PostPageRequestDTO;
import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.PostImage;
import com.skhkim.instaclone.repository.PostImageRepository;
import com.skhkim.instaclone.repository.PostRepository;
import com.skhkim.instaclone.repository.ReplyRepository;
import com.skhkim.instaclone.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final ReplyRepository replyRepository;
    @Value("/Users/gimseonghun/JavaPJ/InstaClone/data/")
    private String uploadPath;

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
    @Transactional
    public void modifyTitle(PostDTO postDTO){
        Map<String, Object> entityMap = dtoToEntity(postDTO);
        Post post = (Post) entityMap.get("post");
        postRepository.save(post);
    }

    @Override
    public PostResponse getList(PostPageRequestDTO postPageRequestDTO, String name){
        Pageable pageable = postPageRequestDTO.getPageable();
        Slice<Post> result = postRepository.getListPage(pageable, name);
        List<PostDTO> postDTOS = result.stream().map(post -> EntityMapper.entityToDTO(post)).toList();

        return new PostResponse(postDTOS, result.hasNext());
    }
    @Override
    public Long getPostNumber(String email){


        return postRepository.getPostCount(email);
    }
    @Override
    public String getEmailByUserName(String userName){

        return postRepository.getEmail(userName);
    }
    @Override
    public PostDTO getPostByID(Long pno){
        Post post = postRepository.findByPno(pno);
        PostDTO postDTO = postToDTO(post);
        return postDTO;
    }

    @Override
    public PostDTO getPostWithAllImage(Long postID){
        List<Object[]> result = postRepository.getPostWithAll(postID);

        Post post = (Post) result.get(0)[0];

        List<PostImage> postImageList =  result.stream().map(arr -> {
            return (PostImage) arr[1];
        }).collect(Collectors.toList());

        return entitiesToDTO(post, postImageList);
    }
    @Override
    @Transactional
    public void removePost(Long pno){
        replyRepository.deleteByPostPno(pno);
        List<PostImage> postImageList = postImageRepository.findByPno(pno);
        postImageRepository.deleteByPostPno(pno);
        postImageList.forEach(postImage -> {
            try {
                Path path = Paths.get(uploadPath + postImage.getPath() + "/" + postImage.getUuid() + "_" + postImage.getImgName());
                Path path_s = Paths.get(uploadPath + postImage.getPath() + "/s_" + postImage.getUuid() + "_" + postImage.getImgName());
                Files.deleteIfExists(path);
                Files.deleteIfExists(path_s);
            }catch (IOException e) {
                e.printStackTrace();
            }
        });
        postRepository.deleteByPno(pno);
    }
}
