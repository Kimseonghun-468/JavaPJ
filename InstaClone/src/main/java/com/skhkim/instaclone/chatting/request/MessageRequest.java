package com.skhkim.instaclone.chatting.request;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import lombok.Data;

@Data
public class MessageRequest {
    private Long roomId;
    private ChatMessageDTO chatMessageDTO;
}
