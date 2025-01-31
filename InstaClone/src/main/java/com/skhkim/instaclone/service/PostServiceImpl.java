package com.skhkim.instaclone.service;

import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.PostImage;
import com.skhkim.instaclone.repository.PostImageRepository;
import com.skhkim.instaclone.repository.PostRepository;
import com.skhkim.instaclone.repository.ReplyRepository;
import com.skhkim.instaclone.request.PostPageRequest;
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
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final ReplyRepository replyRepository;
    @Value("${instaclone.upload.path}")
    private String uploadPath;

    @Override
    public boolean insert(PostDTO postDTO){

        ClubMemberDTO loginUserDTO = LoginContext.getClubMember();
        postDTO.setUserId(loginUserDTO.getUserId());
        Map<String, Object> entityMap = EntityMapper.dtoToEntity(postDTO);
        Post post = (Post) entityMap.get("post");
        List<PostImage> postImageList = (List<PostImage>) entityMap.get("imgList");

        postRepository.save(post);
        postImageList.forEach(image -> image.setPost(post));
        postImageRepository.saveAll(postImageList);

        return true;
    }

    @Override
    public boolean update(PostDTO postDTO){
        ClubMemberDTO loginUserDTO = LoginContext.getClubMember();
        if(postRepository.validation(postDTO.getPno(), loginUserDTO.getUserId())) {
            postDTO.setUserId(loginUserDTO.getUserId());
            Map<String, Object> entityMap = EntityMapper.dtoToEntity(postDTO);
            Post post = (Post) entityMap.get("post");
            postRepository.save(post);
            return true;
        }
        else
            return false;
    }

    @Override
    public PostResponse selectPostList(PostPageRequest postPageRequest, String name){
        Pageable pageable = postPageRequest.getPageable();
        Slice<Post> result = postRepository.selectList(pageable, name);
        List<PostDTO> postDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new PostResponse(postDTOS, result.hasNext());
    }
    @Override
    public Long selectPostNumber(String name){
        return postRepository.getPostCount(name);
    }

    @Override
    public PostDTO selectPostDetail(Long postId){
        Post post = postRepository.select(postId);
        return EntityMapper.entityToDTO(post);
    }
    @Override
    public boolean delete(Long pno){

        ClubMemberDTO loginUserDTO = LoginContext.getClubMember();
        if(!postRepository.validation(pno, loginUserDTO.getUserId()))
            return false;

        List<PostImage> postImageList = postImageRepository.selectPostImages(pno);
        postImageList.forEach(postImage -> {
            try {
                Path path = Paths.get(uploadPath + postImage.getPath() + "/" + postImage.getUuid() + "_" + postImage.getImgName());
                Path path_s = Paths.get(uploadPath + postImage.getPath() + "/s_" + postImage.getUuid() + "_" + postImage.getImgName());
                Files.deleteIfExists(path);
                Files.deleteIfExists(path_s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        replyRepository.delete(pno);
        postImageRepository.delete(pno);
        postRepository.delete(pno);
        return true;

    }
}
