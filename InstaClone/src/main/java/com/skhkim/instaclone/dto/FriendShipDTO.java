package com.skhkim.instaclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendShipDTO {

    private Long id;
    private String userEmail;
    private String friendEmail;
    private String userName;
    private String friendName;
    private boolean isFrom;

    private Long counterpartId;
}
