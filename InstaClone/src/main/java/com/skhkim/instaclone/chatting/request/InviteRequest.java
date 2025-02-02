package com.skhkim.instaclone.chatting.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InviteRequest {
    private Long roomId;
    private List<String> userNames;
    private Long addNum;
    private String searchTerm;
}
