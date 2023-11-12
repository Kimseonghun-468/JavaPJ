package com.skhkim.instaclone.dto;

import com.skhkim.instaclone.entity.ClubMemberRole;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubMemberDTO {

    private String email;
    private String name;
    private String password;
    private boolean fromSocial;
    private LocalDateTime regDate, modDate;
    private Set<ClubMemberRole> roleSet = new HashSet<>();

}
