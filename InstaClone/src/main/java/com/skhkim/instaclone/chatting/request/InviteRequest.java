package com.skhkim.instaclone.chatting.request;

import lombok.Data;

import java.util.List;

@Data
public class InviteRequest {
    private Long roomId;
    private List<String> userEmails;
    private List<String> userNames;
    private Integer addNum;
    private String searchTerm;
}
