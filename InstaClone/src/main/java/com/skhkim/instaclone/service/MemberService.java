package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.response.UserInfoResponse;

public interface MemberService {
    boolean checkName(String name);
    boolean checkEmail(String email);
    boolean checkDuplication(ClubMemberDTO memberDTO);

    boolean checkPassword(ClubMemberDTO memberDTO);

    void updatePassword(String memberName, String newPassword);
    void updateUserName(String changeName, String originalName);
    String register(ClubMemberDTO memberDTO);
    UserInfoResponse getClubMemberSearchbyNameAll(UserInfoPageRequest userInfoPageRequest, String searchName, String loginName);
    ClubMember getClubMemberSearchbyEmail(String Email);
    boolean getUserExist(String name);

    UserInfoDTO selectUserInfo(String userName);
}
