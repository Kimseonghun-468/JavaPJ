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
//    private Long userId;
//    private String userEmail; //
//    private String userName; //
//    private Long roomId;
//    private LocalDateTime disConnect;
    private UserInfoDTO userInfoDTO;

}
