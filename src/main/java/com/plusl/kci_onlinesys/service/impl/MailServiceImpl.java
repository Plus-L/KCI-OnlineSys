package com.plusl.kci_onlinesys.service.impl;

import com.plusl.kci_onlinesys.service.MailService;
import com.plusl.kci_onlinesys.util.CommunityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @program: kci_onlinesys
 * @description: 邮箱服务实现
 * @author: PlusL
 * @create: 2022-03-13 20:16
 **/
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from}")
    private String mailFrom;



    @Override
    public void sendSimpleMail(String mailTo) {
        SimpleMailMessage message=new SimpleMailMessage();

        String uuid = CommunityUtil.generateUUID();

        message.setFrom(mailFrom);
        message.setTo(mailTo);
        message.setSubject("KingCola-ICG");
        message.setText("【KingCola-ICG】验证码："+ uuid +" ,5分钟内有效。切勿将验证码提供给其他人，KingCola-ICG团队成员不会向您索要验证码，谨防被骗");
        mailSender.send(message);
        log.info("邮件已经发送");
    }

}