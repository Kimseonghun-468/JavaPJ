package com.skhkim.instaclone.chatting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int page;
    private int size;
    private String type;
    private String keyword;
    public PageRequestDTO(){
        this.page = 1;
        this.size = 12;

    }
    public Pageable getPageable(){
        return PageRequest.of(page -1, size);
    }
}