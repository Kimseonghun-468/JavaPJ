package com.skhkim.instaclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class ProfilePageRequestDTO {
    private int page;
    private int size;
    private String type;
    private String keyword;
    public ProfilePageRequestDTO(){
        this.page = 1;
        this.size = 6;

    }
    public Pageable getPageable(){
        return PageRequest.of(page -1, size);
    }
}
