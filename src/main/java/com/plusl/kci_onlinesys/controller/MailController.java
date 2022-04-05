package com.plusl.kci_onlinesys.controller;

import com.plusl.kci_onlinesys.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: kci_onlinesys
 * @description: 邮箱控制器
 * @author: PlusL
 * @create: 2022-03-13 20:20
 **/

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @RequestMapping(value = "send" , method = RequestMethod.GET)
    public String sendMail(@RequestParam(value = "email")String email){
        mailService.sendSimpleMail(email);
        return "OK";
    }
}
