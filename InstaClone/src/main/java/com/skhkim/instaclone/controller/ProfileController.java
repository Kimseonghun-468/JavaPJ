package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.PageRequestDTO;
import com.skhkim.instaclone.dto.PostDTO;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import com.skhkim.instaclone.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ProfileController {

    private final PostService postService;
    @GetMapping("/profile")
    public void profile(){
        log.info("Profile...-----");

    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sidebar/{name}")
    public String sidebar(@PathVariable("name") String name, PageRequestDTO pageRequestDTO, @AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO, Model model){
        log.info("Sidebar...----");
        log.info("PageRequest DTO : " + pageRequestDTO);
        log.info("Sidebar name: " + name);


        String userEamil = postService.getEmailByUserName(name);
//       club...getEamil()부분을  PathVariable name으로 해결할 수 는 있을까?
//        Eamil은 name을 통해 조회할 수 있도록 하고,

//        model.addAttribute("result", postService.getList(pageRequestDTO, clubAuthMemberDTO.getEmail()));
//        model.addAttribute("memberDTO", clubAuthMemberDTO);
//        model.addAttribute("postNum", postService.getPostNumber(clubAuthMemberDTO.getEmail()));
        model.addAttribute("result", postService.getList(pageRequestDTO, userEamil));
        model.addAttribute("memberDTO", clubAuthMemberDTO);
        model.addAttribute("userName", name);
        model.addAttribute("userEmail", userEamil);
        model.addAttribute("postNum", postService.getPostNumber(userEamil));
        return "sidebar";
    }

    @PostMapping("/sidebar")
    public String sidevar(PostDTO postDTO, RedirectAttributes redirectAttributes){
        log.info("PostDTO : " + postDTO);

        Long pno = postService.register(postDTO);
        redirectAttributes.addFlashAttribute("msg", pno);
        return "redirect:/sidebar";
    }
    @GetMapping("/midle")
    public void midle(){
        log.info("midle...----");
    }
    @GetMapping("/srctest")
    public void srctest(){
        log.info("src...----");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public void list(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO, PageRequestDTO pageRequestDTO, Model model){
        log.info("pageRequestDTO : " + pageRequestDTO);
        model.addAttribute("result", postService.getList(pageRequestDTO, clubAuthMemberDTO.getEmail()));

        log.info("What!");
    }
}
