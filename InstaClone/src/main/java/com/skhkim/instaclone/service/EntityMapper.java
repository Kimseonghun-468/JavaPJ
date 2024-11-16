package com.skhkim.instaclone.service;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.ChatUserDTO;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.*;
import lombok.Data;

import java.util.List;


@Data
public class EntityMapper {
    public static UserInfoDTO entityToDTO(UserInfoProjection projection){
        ProfileImage profileImage = projection.getClubMember().getProfileImage();

        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .imgName(profileImage != null ? profileImage.getImgName() : null)
                .uuid(profileImage != null ? profileImage.getUuid() : null)
                .path(profileImage != null ? profileImage.getPath() : null)
                .userName(projection.getClubMember().getName())
                .userEmail(projection.getClubMember().getEmail())
                .status(projection.getStatus())
                .build();
        return userInfoDTO;
    }

    public static UserInfoDTO entityToDTO(ClubMember clubMember){
        ProfileImage profileImage = clubMember.getProfileImage();

        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .imgName(profileImage != null ? profileImage.getImgName() : null)
                .uuid(profileImage != null ? profileImage.getUuid() : null)
                .path(profileImage != null ? profileImage.getPath() : null)
                .userName(clubMember.getName())
                .userEmail(clubMember.getEmail())
                .build();
        return userInfoDTO;
    }

    public static ClubMember dtoToEntity(ClubMemberDTO dto){

        ClubMember clubMember = ClubMember.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .fromSocial(dto.isFromSocial())
                .roleSet(dto.getRoleSet())
                .build();

        return clubMember;
    }

    public static ChatMessageDTO entityToDTO(ChatMessage chatMessage){
        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .senderEmail(chatMessage.getSenderEmail())
                .content(chatMessage.getContent())
                .readStatus(chatMessage.getReadStatus())
                .regDate(chatMessage.getRegDate())
                .build();

        return chatMessageDTO;
    }

    public static ChatUserDTO entityToDTO(ChatUser chatUser){
        ChatUserDTO chatUserDTO = ChatUserDTO.builder()
                .userInfoDTO(entityToDTO(chatUser.getMember()))
                .roomId(chatUser.getChatRoom().getRoomId())
                .disConnect(chatUser.getDisConnect())
                .build();

        return chatUserDTO;
    }

    public static PostDTO entityToDTO(Post post){
        PostDTO postDTO = PostDTO.builder()
                .pno(post.getPno())
                .email(post.getClubMember().getEmail())
                .comment(post.getComment())
                .title(post.getTitle())
                .regDate(post.getRegDate())
                .modDate(post.getModDate())
                .build();

        List<PostImage> postImages = post.getPostImageList();

        List<PostImageDTO> postImageDTOList = postImages.stream().map(postImage ->
                PostImageDTO.builder().imgName(postImage.getImgName())
                .path(postImage.getPath())
                .uuid(postImage.getUuid())
                .pino(postImage.getPino())
                .build()).toList();

        postDTO.setImageDTOList(postImageDTOList);

        return postDTO;
    }

    public static ReplyDTO entityToDTO(Reply reply){

        ReplyDTO replyDTO = ReplyDTO.builder()
                .userInfoDTO(EntityMapper.entityToDTO(reply.getClubMember()))
                .text(reply.getText())
                .regDate(reply.getRegDate())
                .build();
        return replyDTO;
    }
}

