package com.skhkim.instaclone.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
public class ProfileController {

    @GetMapping("/profile")
    public void profile(){
        log.info("Profile...-----");

    }
    @GetMapping("/sidebar")
    public void sidevar(){
        log.info("Sidebar...----");
    }
    @GetMapping("/midle")
    public void midle(){
        log.info("midle...----");
    }
    @GetMapping("/srctest")
    public void srctest(){
        log.info("src...----");
    }
}
