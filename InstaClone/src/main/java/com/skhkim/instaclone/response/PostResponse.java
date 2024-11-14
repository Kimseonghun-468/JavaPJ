package com.skhkim.instaclone.response;

import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.dto.PostImageDTO;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {

    private List<PostDTO> postDTOS;
    private boolean hasNext;

    public PostResponse(List<PostDTO> postDTOS, boolean hasNext){
        this.postDTOS = postDTOS;
        this.hasNext = hasNext;
    }
}
