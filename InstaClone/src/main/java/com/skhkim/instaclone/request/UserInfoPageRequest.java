package com.skhkim.instaclone.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Builder
@AllArgsConstructor
@Data
public class UserInfoPageRequest {
    private int page;
    private int size;
    private String type;
    private String keyword;
    public UserInfoPageRequest(){
        this.page = 1;
        this.size = 12;

    }
    public Pageable getPageable(){
        return PageRequest.of(page -1, size);
    }
}
