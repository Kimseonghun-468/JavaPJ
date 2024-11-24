package com.skhkim.instaclone.service;

import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.dto.ReplyDTO;
import com.skhkim.instaclone.response.ReplyResponse;

public interface ReplyService {

    ReplyDTO register(ReplyDTO replyDTO);
    ReplyResponse selectReplyList(PageRequestDTO pageRequestDTO, Long pno);
    boolean remove(Long replynum);
}
