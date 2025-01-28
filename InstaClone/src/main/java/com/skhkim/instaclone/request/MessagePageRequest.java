package com.skhkim.instaclone.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Builder
@AllArgsConstructor
@Data
public class MessagePageRequest {
    private int page;
    private int size;
    /**
     * direction Type - cid 기준 이전 기록 조회인지, 이후 기록 조회인지
     * */
    private String dType;
    public MessagePageRequest(){
        this.page = 1;
        this.size = 12;

    }
    public Pageable getPageable(){
        return PageRequest.of(page -1, size);
    }
}