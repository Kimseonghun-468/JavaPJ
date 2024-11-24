package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.request.PostPageRequest;
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
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final ReplyRepository replyRepository;
    @Value("${instaclone.upload.path")
    private String uploadPath;

    @Override
    @Transactional
    public void register(PostDTO postDTO){

        Map<String, Object> entityMap = EntityMapper.dtoToEntity(postDTO);
        Post post = (Post) entityMap.get("post");
        List<PostImage> postImageList = (List<PostImage>) entityMap.get("imgList");

        postRepository.save(post);
        postImageRepository.saveAll(postImageList);

    }

    @Override
    @Transactional
    public void modifyTitle(PostDTO postDTO){
        Map<String, Object> entityMap = EntityMapper.dtoToEntity(postDTO);
        Post post = (Post) entityMap.get("post");
        postRepository.save(post);
    }

    @Override
    public PostResponse getList(PostPageRequest postPageRequest, String name){
        Pageable pageable = postPageRequest.getPageable();
        Slice<Post> result = postRepository.getListPage(pageable, name);
        List<PostDTO> postDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

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
    public PostDTO getPostWithAllImage(Long postId){
        Post post = postRepository.selectPost(postId);
        return EntityMapper.entityToDTO(post);
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
