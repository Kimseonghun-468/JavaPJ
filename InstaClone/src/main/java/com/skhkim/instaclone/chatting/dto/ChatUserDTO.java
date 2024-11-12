package com.skhkim.instaclone.chatting.dto;

import com.skhkim.instaclone.dto.ProfileImageDTO;
import com.skhkim.instaclone.dto.UserInfoDTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserDTO {
    private Long roomId;
    private LocalDateTime disConnect;
    private UserInfoDTO userInfoDTO;


}
