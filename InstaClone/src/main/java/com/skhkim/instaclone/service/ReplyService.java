package com.skhkim.instaclone.service;

import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.response.ReplyResponse;

public interface ReplyService {

//    Long register(ReplyDTO replyDTO);
    ReplyResponse selectReplyList(PageRequestDTO pageRequestDTO, Long pno);

    void remove(Long replynum);
//    default Reply dtoToEntity(ReplyDTO replyDTO){
//
//        Reply postReply = Reply.builder()
//                .text(replyDTO.getText())
//                .post(Post.builder().pno(replyDTO.getPno()).build())
//                .clubMember(ClubMember.builder().email(replyDTO.getEmail()).build())
//                .build();
//
//        return postReply;
//    }

//    default ReplyDTO entityToDTO(Reply postReply){
//        ReplyDTO postReplyDTO = ReplyDTO.builder()
//                .rno(postReply.getRno())
//                .pno(postReply.getPost().getPno())
//                .name(postReply.getClubMember().getName())
//                .email(postReply.getClubMember().getEmail())
//                .text(postReply.getText())
//                .regDate(postReply.getRegDate())
//                .modDate(postReply.getModDate())
//                .build();
//        return postReplyDTO;
//    }
}
