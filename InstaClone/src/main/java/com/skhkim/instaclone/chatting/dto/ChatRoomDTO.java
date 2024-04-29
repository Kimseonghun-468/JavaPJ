package com.skhkim.instaclone.chatting.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private String id;
    private String userName;
    private String friendName;
}
