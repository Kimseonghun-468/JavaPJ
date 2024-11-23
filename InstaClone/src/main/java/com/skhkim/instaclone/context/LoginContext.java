package com.skhkim.instaclone.context;

import com.skhkim.instaclone.dto.UserInfoDTO;
import org.springframework.stereotype.Component;


@Component
public class LoginContext {

    private static final ThreadLocal<UserInfoDTO> loginInfoThreadLocal = new ThreadLocal<>();

    public static void setUserInfo(UserInfoDTO userInfoDTO) {
        loginInfoThreadLocal.set(userInfoDTO);
    }

    public static UserInfoDTO getUserInfo(){
        return loginInfoThreadLocal.get();
    }

}