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
@Transactional
public class ProfileServiceImpl implements ProfileService{
    @Value("${instaclone.upload.path}")
    private String uploadPath;

    private final ProfileImageRepository profileImageRepository;

    public void deleteImage(Long userId){
        ProfileImage imageEntity = profileImageRepository.select(userId);
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
    public void register(ProfileImageDTO profileImageDTO){
        Long userId = LoginContext.getClubMember().getUserId();
        profileImageDTO.setUserId(userId);
        ProfileImage profileImage = EntityMapper.dtoToEntity(profileImageDTO);

        profileImageRepository.delete(userId);
        profileImageRepository.save(profileImage);
        this.deleteImage(userId);
    }



    @Override
    public void delete(){
        Long userId = LoginContext.getClubMember().getUserId();
        profileImageRepository.delete(userId);
        this.deleteImage(userId);
    }

}
