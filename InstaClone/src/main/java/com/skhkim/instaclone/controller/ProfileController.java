package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.entity.type.FriendStatus;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import com.skhkim.instaclone.service.FriendService;
import com.skhkim.instaclone.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final MemberService memberService;
    private final FriendService friendService;
    @GetMapping("/sidebar/{name}")
    public String sidebar(@PathVariable("name") String name,
                          Model model){

        ClubMemberDTO clubMemberDTO = LoginContext.getClubMember();

        FriendStatus friendStatus = friendService.checkFriendShip(clubMemberDTO.getName(), name);
        model.addAttribute("friendshipStatus", friendStatus.name());
        model.addAttribute("userExist", memberService.getUserExist(name));
        model.addAttribute("loginInfo", clubMemberDTO);
        model.addAttribute("userName", name);
        return "profile";
    }

//
//    @GetMapping("/userinfo/{name}")
//    public String userinfo(@PathVariable("name") String name,
//                          String nameError, String psError,
//                          Model model){
//        if ( nameError != null )
//            model.addAttribute("nameChangeError", true);
//        if ( psError != null )
//            model.addAttribute("psError", true);
//        model.addAttribute("friendshipStatus", friendStatus);
//        model.addAttribute("userExist", memberService.getUserExist(name));
//        model.addAttribute("loginInfo", loginInfoDTO);
//        model.addAttribute("userName", name);
//        return "userinfo";
//    }



    @GetMapping({"", "/sidebar","/sidebar/"})
    public String defaultPage(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) throws UnsupportedEncodingException{
        String encodedName = URLEncoder.encode(clubAuthMemberDTO.getName(), "UTF-8");
        return "redirect:/sidebar/"+encodedName;
    }
}
