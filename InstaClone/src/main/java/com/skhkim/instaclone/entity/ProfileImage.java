package com.skhkim.instaclone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProfileImage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pfino;

    private String userName;
    private String userEmail;
    private String uuid;
    private String imgName;
    private String path;

    public String getImageURL(){
        try {
            return URLEncoder.encode(path+"/"+uuid+"_"+imgName,"UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }
}
