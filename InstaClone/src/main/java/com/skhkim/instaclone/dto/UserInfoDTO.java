package com.skhkim.instaclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
public class UserInfoDTO {
    private String userName;
    private String userEmail;
    private String uuid;
    private String imgName;
    private String path;
    private String status;

    public String getImageURL(){
        try {
            return URLEncoder.encode(path+"/"+uuid+"_"+imgName,"UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL(){
        try{
            return URLEncoder.encode(path+"/s_"+uuid+"_"+imgName,"UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }
}
