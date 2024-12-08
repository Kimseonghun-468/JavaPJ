package com.skhkim.instaclone.context;

import com.skhkim.instaclone.dto.ClubMemberDTO;
import org.springframework.stereotype.Component;


@Component
public class LoginContext {

    private static final ThreadLocal<ClubMemberDTO> loginInfoThreadLocal = new ThreadLocal<>();

    public static void setUserInfo(ClubMemberDTO clubMemberDTO) {
        loginInfoThreadLocal.set(clubMemberDTO);
    }

    public static ClubMemberDTO getClubMember(){
        return loginInfoThreadLocal.get();
    }

}