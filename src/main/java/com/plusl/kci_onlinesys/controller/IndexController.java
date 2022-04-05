package com.plusl.kci_onlinesys.controller;

import com.plusl.kci_onlinesys.entity.User;
import com.plusl.kci_onlinesys.mapper.UserMapper;
import com.plusl.kci_onlinesys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: kci_onlinesys
 * @description: 首页管理器
 * @author: PlusL
 * @create: 2022-03-09 16:08
 **/

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String toIndex(){
        return "index";
    }

    @GetMapping("/joinus")
    public String toJoinUs(){
        return "joinus";
    }

    @GetMapping("/login")
    public String toLogin(){
        return "login";
    }

    @GetMapping("/about")
    public String toAbout(){
        return "about";
    }

    @GetMapping("/faq")
    public String toFaq(){
        return "faq";
    }



}
