package com.skhkim.instaclone.response;

import com.skhkim.instaclone.dto.ReplyDTO;
import lombok.Data;

import java.util.List;

@Data
public class ReplyResponse {
    private List<ReplyDTO> replyDTOS;
    private boolean hasNext;

    public ReplyResponse(List<ReplyDTO> replyDTOS, boolean hasNext){
        this.replyDTOS = replyDTOS;
        this.hasNext = hasNext;
    }
}
