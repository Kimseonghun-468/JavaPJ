package com.skhkim.instaclone.chatting.response;

import com.skhkim.instaclone.chatting.dto.ChatRoomDTO;
import com.skhkim.instaclone.dto.UserInfoDTO;
import lombok.Data;

import java.util.List;

@Data
public class ChatRoomResponse {

    private List<ChatRoomDTO> chatRoomDTOS;
    private boolean hasNext;

    public ChatRoomResponse(List<ChatRoomDTO> chatRoomDTOS, boolean hasNext) {
        this.chatRoomDTOS = chatRoomDTOS;
        this.hasNext = hasNext;
    }
}
