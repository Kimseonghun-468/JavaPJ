package com.skhkim.instaclone.chatting.request;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.ChatUserDTO;
import lombok.Data;

@Data
public class MessageRequest extends ChatUserDTO {
    private Long roomId;
    private Long userId;
    private ChatMessageDTO chatMessageDTO;
}
