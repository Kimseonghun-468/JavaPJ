package com.skhkim.instaclone.response;

import com.skhkim.instaclone.dto.UserInfoDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoResponse {
    private List<UserInfoDTO> userInfoDTOS;
    private boolean hasNext;

    public UserInfoResponse(List<UserInfoDTO> userInfoDTOS, boolean hasNext) {
        this.userInfoDTOS = userInfoDTOS;
        this.hasNext = hasNext;
    }
}
