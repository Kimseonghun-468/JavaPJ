package com.skhkim.instaclone.chatting.dto;

import com.skhkim.instaclone.dto.UserInfoDTO;
import lombok.*;

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
    private Long lastCid;

    private List<UserInfoDTO> userInfoDTOS;

}
