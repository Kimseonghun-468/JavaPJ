package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.entity.type.FriendStatus;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import com.skhkim.instaclone.service.FriendShipService;
import com.skhkim.instaclone.service.MemberService;
import com.skhkim.instaclone.service.PostService;
import com.skhkim.instaclone.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class ProfileController {

    private final PostService postService;
    private final ProfileService profileService;
    private final MemberService memberService;
    private final FriendShipService friendShipService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sidebar/{name}")
    public String sidebar(@PathVariable("name") String name,
                          Model model){

        UserInfoDTO loginInfoDTO = LoginContext.getUserInfo();
        FriendStatus friendStatus = friendShipService.checkFriendShip(loginInfoDTO.getUserName(), name);
        model.addAttribute("friendshipStatus", friendStatus); // ## 필요,,?
        model.addAttribute("userExist", memberService.getUserExist(name)); // 이거는 만들자.. ##
        model.addAttribute("memberDTO", loginInfoDTO);
        model.addAttribute("userName", name); // 남겨놓기
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
//        model.addAttribute("userExist", memberService.getUserExist(name));
//        model.addAttribute("profileImageDTO", profileImageDTO);
//        model.addAttribute("memberDTO", clubAuthMemberDTO);
//        model.addAttribute("userName", name);
//        model.addAttribute("userEmail", userEamil);
//        model.addAttribute("postNum", postService.getPostNumber(userEamil));
//        model.addAttribute("friendNum", friendShipService.getFriendNum(name));
//        return "userinfo";
//    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping({"", "/sidebar","/sidebar/"})
    public String defaultPage(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) throws UnsupportedEncodingException{
        String encodedName = URLEncoder.encode(clubAuthMemberDTO.getName(), "UTF-8");
        return "redirect:/sidebar/"+encodedName;
    }
}
