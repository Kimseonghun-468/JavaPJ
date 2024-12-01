package com.skhkim.instaclone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocketSessionDTO {
    private String eventType; // "connect" 또는 "disconnect"
    private String sessionId;
    private String roomId;
    private String userId;
}
