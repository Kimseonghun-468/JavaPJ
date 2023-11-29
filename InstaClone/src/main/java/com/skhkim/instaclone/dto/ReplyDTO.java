package com.skhkim.instaclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    private Long rno;
    private Long pno;
    private String name;
    private String email;
    private String text;
    private LocalDateTime regDate, modDate;
}
