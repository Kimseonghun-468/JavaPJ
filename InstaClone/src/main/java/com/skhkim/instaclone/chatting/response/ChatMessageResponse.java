package com.skhkim.instaclone.chatting.response;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import lombok.Data;

import java.util.List;

@Data
public class ChatMessageResponse {

    private List<ChatMessageDTO> chatMessageDTOS;
    private boolean hasNext;

    public ChatMessageResponse(List<ChatMessageDTO> chatMessageDTOS, boolean hasNext){
        this.chatMessageDTOS = chatMessageDTOS;
        this.hasNext = hasNext;
    }

}
