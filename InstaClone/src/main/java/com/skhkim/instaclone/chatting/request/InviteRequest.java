package com.skhkim.instaclone.chatting.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InviteRequest {
    private Long roomId;
    private List<String> userEmails;
    private List<String> userNames;
    private Integer addNum;
    private String searchTerm;
}
