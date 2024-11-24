package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.ReplyDTO;
import com.skhkim.instaclone.request.ReplyPageRequest;
import com.skhkim.instaclone.response.ReplyResponse;

public interface ReplyService {

    ReplyDTO register(ReplyDTO replyDTO);
    ReplyResponse selectReplyList(ReplyPageRequest replyPageRequest, Long pno);
    boolean remove(Long replynum);
}
