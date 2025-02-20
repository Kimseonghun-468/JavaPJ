package com.skhkim.instaclone.service;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.ChatRoomDTO;
import com.skhkim.instaclone.chatting.dto.ChatUserDTO;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Data
public class EntityMapper {
    public static UserInfoDTO entityToDTO(UserInfoProjection projection){
        ProfileImage profileImage = projection.getClubMember().getProfileImage();
        return UserInfoDTO.builder()
                .imgName(profileImage != null ? profileImage.getImgName() : null)
                .uuid(profileImage != null ? profileImage.getUuid() : null)
                .path(profileImage != null ? profileImage.getPath() : null)
                .userName(projection.getClubMember().getName())
                .status(projection.getStatus())
                .build();
    }

    public static UserInfoDTO entityToDTO(ClubMember clubMember){
        ProfileImage profileImage = clubMember.getProfileImage();
        return UserInfoDTO.builder()
                .imgName(profileImage != null ? profileImage.getImgName() : null)
                .uuid(profileImage != null ? profileImage.getUuid() : null)
                .path(profileImage != null ? profileImage.getPath() : null)
                .userName(clubMember.getName())
                .build();
    }
    
    public static ClubMemberDTO toLoginContext(ClubMember clubMember){
        return ClubMemberDTO.builder()
                .email(clubMember.getEmail())
                .name(clubMember.getName())
                .userId(clubMember.getId())
                .build();
    }

    public static ClubMember dtoToEntity(ClubMemberDTO dto){
        return ClubMember.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .fromSocial(dto.isFromSocial())
                .roleSet(dto.getRoleSet())
                .build();
    }

    public static ChatMessageDTO entityToDTO(ChatMessage chatMessage){
        return ChatMessageDTO.builder()
                .senderName(chatMessage.getSendUser().getName())
                .roomId(chatMessage.getRoomId())
                .content(chatMessage.getContent())
                .readStatus(chatMessage.getReadStatus())
                .regDate(chatMessage.getRegDate())
                .inviteNames(chatMessage.getInvitedUser())
                .profileImageUrl(chatMessage.getSendUser().getProfileImage() != null ?
                        chatMessage.getSendUser().getProfileImage().getImageURL() : null)
                .build();
    }

    public static ChatMessage dtoToEntity(ChatMessageDTO chatMessageDTO){
        return ChatMessage.builder()
                .cid(chatMessageDTO.getCid())
                .roomId(chatMessageDTO.getRoomId())
                .content(chatMessageDTO.getContent())
                .readStatus(chatMessageDTO.getReadStatus())
                .invitedUser(chatMessageDTO.getInviteNames())
                .build();
    }

    public static ChatUserDTO entityToDTO(ChatUser chatUser){
        return ChatUserDTO.builder()
                .userInfoDTO(entityToDTO(chatUser.getMember()))
                .roomId(chatUser.getChatRoom().getRoomId())
                .lastCid(chatUser.getLastCid())
                .regDate(chatUser.getRegDate())
                .build();
    }

    public static ChatRoomDTO entityToDTO(ChatRoom chatRoom){
        return ChatRoomDTO.builder()
                .roomId(chatRoom.getRoomId())
                .userNum(chatRoom.getUserNum())
                .lastChat(chatRoom.getLastChat())
                .lastCid(chatRoom.getLastCid())
                .userInfoDTOS(EntityMapper.entityToDTO(chatRoom.getChatUserList()))
                .build();
    }

    public static List<UserInfoDTO> entityToDTO(List<ChatUser> chatUserList){
        return chatUserList.stream().map(chatUser ->
                UserInfoDTO.builder()
                        .userName(chatUser.getMember().getName())
                        .imgName(chatUser.getMember().getProfileImage() != null ? chatUser.getMember().getProfileImage().getImgName() : null)
                        .uuid(chatUser.getMember().getProfileImage() != null ? chatUser.getMember().getProfileImage().getUuid() : null)
                        .path(chatUser.getMember().getProfileImage() != null ? chatUser.getMember().getProfileImage().getPath() : null)
                        .build()).collect(Collectors.toList());
    }

    public static PostImageDTO entityToDTO(PostImage postImage){
        return PostImageDTO.builder()
                .imgName(postImage.getImgName())
                .path(postImage.getPath())
                .uuid(postImage.getUuid())
                .pino(postImage.getPino())
                .build();
    }

    public static PostImage dtoToEntity(PostImageDTO postImageDTO){
        return PostImage.builder()
                .path(postImageDTO.getPath())
                .uuid(postImageDTO.getUuid())
                .imgName(postImageDTO.getImgName())
                .post(Post.builder().pno(postImageDTO.getPno()).build())
                .build();
    }

    public static PostDTO entityToDTO(Post post){
        return PostDTO.builder()
                .pno(post.getPno())
                .email(post.getClubMember().getEmail())
                .comment(post.getComment())
                .title(post.getTitle())
                .replyNum(post.getReplyNum())
                .likeNum(post.getLikeNum())
                .regDate(post.getRegDate())
                .modDate(post.getModDate())
                .imageDTOList(post.getPostImageList().stream().map
                        (postImage -> EntityMapper.entityToDTO(postImage)).toList())
                .build();
    }

    public static Map<String, Object> dtoToEntity(PostDTO postDTO){
        Map<String, Object> entityMap = new HashMap<>();

        Post post = Post.builder()
                .pno(postDTO.getPno())
                .title(postDTO.getTitle())
                .comment(postDTO.getComment())
                .clubMember(ClubMember.builder().id(postDTO.getUserId()).build())
                .build();
        entityMap.put("post", post);

        List<PostImageDTO> postImageDTOList = postDTO.getImageDTOList();
        if(postImageDTOList != null && postImageDTOList.size() > 0){
            List<PostImage> postImageList = postImageDTOList.stream().map(postImageDTO ->
                    EntityMapper.dtoToEntity(postImageDTO)).collect(Collectors.toList());

            entityMap.put("imgList", postImageList);
        }

        return entityMap;
    }

    public static ReplyDTO entityToDTO(Reply reply){

        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .userInfoDTO(EntityMapper.entityToDTO(reply.getClubMember()))
                .text(reply.getText())
                .regDate(reply.getRegDate())
                .build();
        return replyDTO;
    }

    public static Reply dtoToEntity(ReplyDTO replyDTO){

        Reply postReply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .post(Post.builder().pno(replyDTO.getPno()).build())
                .clubMember(ClubMember.builder().email(LoginContext.getClubMember().getEmail()).build())
                .build();
        return postReply;
    }

    public static ProfileImage dtoToEntity(ProfileImageDTO profileImageDTO){
        return ProfileImage.builder()
                .clubMember(ClubMember.builder().id(profileImageDTO.getUserId()).build())
                .path(profileImageDTO.getPath())
                .uuid(profileImageDTO.getUuid())
                .imgName(profileImageDTO.getImgName())
                .build();
    }

    public static ProfileImageDTO entityToDTO(ProfileImage profileImage){
        return ProfileImageDTO.builder()
                .pfino(profileImage.getPfino())
                .userName(profileImage.getClubMember().getName())
                .imgName(profileImage.getImgName())
                .userEmail(profileImage.getClubMember().getEmail())
                .path(profileImage.getPath())
                .uuid(profileImage.getUuid())
                .build();
    }



}

