package com.skhkim.instaclone.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private String email;
    private String name;
    private String password;
    private LocalDateTime regDate, modDate;
}
