package com.skhkim.instaclone.chatting.dto;

import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.dto.UserInfoDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private Long roomId;
    private String lastChat;
    private Long userNum;
    private LocalDateTime lastChatTime;

    private List<UserInfoDTO> userInfoDTOS;

}
