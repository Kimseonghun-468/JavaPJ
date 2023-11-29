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
public class PostDTO {
    private Long pno;
    private String email;
    private String comment;
    private String title;

    @Builder.Default
    private List<PostImageDTO> imageDTOList = new ArrayList<>();

    private int reviewCnt;
    private LocalDateTime regDate;
    private LocalDateTime modDate;


}
