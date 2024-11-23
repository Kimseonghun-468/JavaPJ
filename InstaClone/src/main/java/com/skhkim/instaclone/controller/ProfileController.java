package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.dto.ProfileImageDTO;
import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.entity.type.FriendStatus;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import com.skhkim.instaclone.service.FriendShipService;
import com.skhkim.instaclone.service.LoginService;
import com.skhkim.instaclone.service.PostService;
import com.skhkim.instaclone.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class ProfileController {

    private final PostService postService;
    private final ProfileService profileService;
    private final LoginService loginService;
    private final FriendShipService friendShipService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sidebar/{name}")
    public String sidebar(@PathVariable("name") String name,
                          @AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO,
                          Model model){

        LoginContext.getUserInfo();
        String userEamil = postService.getEmailByUserName(name); // 삭제
        ProfileImageDTO profileImageDTO = profileService.getProfileImage(name); // 삭제
        FriendStatus friendStatus = friendShipService.checkFriendShip(clubAuthMemberDTO.getName(), name);
        model.addAttribute("friendshipStatus", friendStatus); // ## 필요,,?
        model.addAttribute("userExist", loginService.getUserExist(name)); // 이거는 만들자.. ##
        model.addAttribute("profileImageDTO", profileImageDTO); // 여것도 필요없음.
        model.addAttribute("memberDTO", clubAuthMemberDTO); // 이것도 이름만 남겨여함.
        model.addAttribute("userName", name); // 남겨놓기
        model.addAttribute("userEmail", userEamil); // 지워야ㅕ함
        model.addAttribute("postNum", postService.getPostNumber(userEamil)); // 이것도 js로 조회 할거임
        model.addAttribute("friendNum", friendShipService.getFriendNum(name)); // 이것도 js로 조회할거임
        return "profile";
    }

//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/userinfo/{name}")
//    public String userinfo(@PathVariable("name") String name,
//                          String nameError, String psError,
//                          @AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO,
//                          Model model){
//        if ( nameError != null )
//            model.addAttribute("nameChangeError", true);
//        if ( psError != null )
//            model.addAttribute("psError", true);
//        String userEamil = postService.getEmailByUserName(name);
//        ProfileImageDTO profileImageDTO = profileService.getProfileImage(name);
//        model.addAttribute("userExist", loginService.getUserExist(name));
//        model.addAttribute("profileImageDTO", profileImageDTO);
//        model.addAttribute("memberDTO", clubAuthMemberDTO);
//        model.addAttribute("userName", name);
//        model.addAttribute("userEmail", userEamil);
//        model.addAttribute("postNum", postService.getPostNumber(userEamil));
//        model.addAttribute("friendNum", friendShipService.getFriendNum(name));
//        return "userinfo";
//    }

    @PostMapping("/sidebar/profileImage/{name}")
    public String profileImage(@PathVariable("name") String name, ProfileImageDTO profileImageDTO) throws UnsupportedEncodingException{
        profileService.register(profileImageDTO);
        String encodedName = URLEncoder.encode(name, "UTF-8");
        return "redirect:/sidebar/"+encodedName;
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping({"", "/sidebar","/sidebar/"})
    public String defaultPage(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) throws UnsupportedEncodingException{
        String encodedName = URLEncoder.encode(clubAuthMemberDTO.getName(), "UTF-8");
        return "redirect:/sidebar/"+encodedName;
    }

    @PostMapping("/selectUserInfo")
    public ResponseEntity selectUserInfo(String userName){
        UserInfoDTO result = profileService.selectUserInfo(userName);

        return ResponseEntity.ok(result);
    }
}
