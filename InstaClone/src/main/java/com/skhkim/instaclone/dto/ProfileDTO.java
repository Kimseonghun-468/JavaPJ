package com.skhkim.instaclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private Long pno;
    private String coment;

    @Builder.Default
    private List<ProfileImageDTO> imageDTOList = new ArrayList<>();

    private int reviewCnt;
    private LocalDateTime regDate;
    private LocalDateTime modDate;


}
