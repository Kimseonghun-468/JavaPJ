package com.skhkim.instaclone.service;

import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.dto.ProfileImageDTO;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProfileServiceImpl implements ProfileService{

    private final ProfileImageRepository profileImageRepository;
    private final ClubMemberRepository memberRepository;
    @Value("${instaclone.upload.path")
    private String uploadPath;

    @Override
    @Transactional
    public void register(ProfileImageDTO profileImageDTO){

        ProfileImage profileImage = EntityMapper.dtoToEntity(profileImageDTO);
        ProfileImage beforeImage = profileImageRepository.findByUserEmail(profileImage.getClubMember().getEmail());
        profileImageRepository.deleteByUserEmail(profileImage.getClubMember().getEmail());
        deleteImage(beforeImage);
        profileImageRepository.save(profileImage);
    }

    public void deleteImage(ProfileImage imageEntity){
        if (imageEntity != null) {
            try {
                Path path = Paths.get(uploadPath + imageEntity.getPath() + "/" + imageEntity.getUuid() + "_" + imageEntity.getImgName());
                Path path_s = Paths.get(uploadPath + imageEntity.getPath() + "/s_" + imageEntity.getUuid() + "_" + imageEntity.getImgName());
                Files.deleteIfExists(path);
                Files.deleteIfExists(path_s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @Transactional
    public void delete(){
        String loginName = LoginContext.getUserInfo().getUserName();
        ProfileImage beforeImage = profileImageRepository.findByUserName(loginName);
        profileImageRepository.deleteByUserName(loginName);
        deleteImage(beforeImage);
    }

}
