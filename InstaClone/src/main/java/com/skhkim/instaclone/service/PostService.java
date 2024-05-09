package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.PostPageRequestDTO;
import com.skhkim.instaclone.dto.PostPageResultDTO;
import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.dto.PostImageDTO;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.PostImage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface PostService {

    PostPageResultDTO<PostDTO, Object[]> getList(PostPageRequestDTO postPageRequestDTO, String name);
    Long register(PostDTO postDTO);

    Long getPostNumber(String email);

    void removePost(Long pno);
    PostDTO getPostByID(Long pno);
    String getEmailByUserName(String userName);

    PostDTO getPostWithAllImage(Long postID);


    default Map<String, Object> dtoToEntity(PostDTO postDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> entityMap = new HashMap<>();

        Post post = Post.builder()
                .pno(postDTO.getPno())
                .title(postDTO.getTitle())
                .comment(postDTO.getComment())
                .clubMember(ClubMember.builder().email(authentication.getName()).build())
                .build();
        entityMap.put("post", post);

        List<PostImageDTO> postImageDTOList = postDTO.getImageDTOList();
        if(postImageDTOList != null && postImageDTOList.size() >0){
            List<PostImage> postImageList = postImageDTOList.stream().map(postImageDTO -> {
                        PostImage postImage = PostImage.builder()
                                .path(postImageDTO.getPath())
                                .uuid(postImageDTO.getUuid())
                                .imgName(postImageDTO.getImgName())
                                .post(post)
                                .build();
                        return postImage;
                    }).collect(Collectors.toList());

            entityMap.put("imgList", postImageList);
        }
        return entityMap;
    }

    default PostDTO entitiesToDTO(Post post, List<PostImage> postImages){
        PostDTO postDTO = PostDTO.builder()
                .pno(post.getPno())
                .email(post.getClubMember().getEmail())
                .comment(post.getComment())
                .title(post.getTitle())
                .regDate(post.getRegDate())
                .modDate(post.getModDate())
                .build();

        List<PostImageDTO> postImageDTOList = postImages.stream().map(postImage -> {
            return PostImageDTO.builder().imgName(postImage.getImgName())
                    .path(postImage.getPath())
                    .uuid(postImage.getUuid())
                    .pino(postImage.getPino())
                    .build();
        }).collect(Collectors.toList());

        postDTO.setImageDTOList(postImageDTOList);

//        postDTO.setAvg(avg);
//        postDTO.setReviewCnt(reviewCnt.intValue());
        return postDTO;
    }

    default PostDTO postToDTO(Post post){
        PostDTO postDTO = PostDTO.builder()
                .pno(post.getPno())
                .email(post.getClubMember().getEmail())
                .comment(post.getComment())
                .title(post.getTitle())
                .regDate(post.getRegDate())
                .modDate(post.getRegDate())
                .build();

        return postDTO;
    }
}
