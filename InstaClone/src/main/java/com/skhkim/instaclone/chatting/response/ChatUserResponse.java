package com.skhkim.instaclone.chatting.response;

import com.skhkim.instaclone.chatting.dto.ChatUserDTO;
import lombok.Data;

import java.util.List;

@Data
public class ChatUserResponse {
    private List<ChatUserDTO> chatUserDTOS;

    public ChatUserResponse(List<ChatUserDTO> chatUserDTOS) {
        this.chatUserDTOS = chatUserDTOS;
    }
}
