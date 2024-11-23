package com.skhkim.instaclone.context;

import com.skhkim.instaclone.dto.UserInfoDTO;
import lombok.Data;
import org.springframework.stereotype.Component;


@Component
@Data
public class LoginContext {

    private static final ThreadLocal<UserInfoDTO> loginInfoThreadLocal = new ThreadLocal<>();

    public static void setUserInfo(UserInfoDTO userInfoDTO) {
        loginInfoThreadLocal.set(userInfoDTO);
    }

    public static UserInfoDTO getUserInfo(){
        return loginInfoThreadLocal.get();
    }

}