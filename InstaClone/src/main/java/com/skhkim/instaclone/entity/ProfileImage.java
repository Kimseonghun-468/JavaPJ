package com.skhkim.instaclone.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "post")

public class ProfileImage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pfino;

    private String userName;
    private String uuid;
    private String imgName;
    private String path;





}
