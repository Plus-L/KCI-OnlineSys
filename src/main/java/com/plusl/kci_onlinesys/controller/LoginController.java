package com.plusl.kci_onlinesys.controller;

import com.google.code.kaptcha.Producer;
import com.plusl.kci_onlinesys.entity.User;
import com.plusl.kci_onlinesys.service.UserService;
import com.plusl.kci_onlinesys.util.CommunityConstant;
import com.plusl.kci_onlinesys.util.CommunityUtil;
import com.plusl.kci_onlinesys.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: kci_onlinesys
 * @description: 登录、注册控制器
 * @author: PlusL
 * @create: 2022-03-15 22:30
 **/
@Controller
public class LoginController implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Value("${community.path.domain}")
    private String domain;

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(path = "/register" , method = RequestMethod.GET)
    public String getRegisterPage(){
        return "register";
    }

    /**
     * 完成注册操作并发送激活邮件
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(path = "/register" , method = RequestMethod.POST)
    public String register(Model model , User user){
        Map<String, Object> map = userService.register(user);
        if(map == null || map.isEmpty()){
            model.addAttribute("msg" , "注册成功，我们已经向您的邮箱发送了一封激活邮件，请尽快激活");
            model.addAttribute("target", "/");
            return "result";
        }else {
            model.addAttribute("EmailMsg", map.get("EmailMsg"));
            model.addAttribute("PasswordMsg", map.get("PasswordMsg"));
            model.addAttribute("nicknameMsg" , map.get("nicknameMsg"));
            return "/register";
        }
    }

    /**
     * 激活操作
     * @param model
     * @param userId
     * @param code
     * @return
     */
    @RequestMapping(path = "/activation/{userId}/{code}" ,method = RequestMethod.GET)
    public String activationMsg(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code){
        int result = userService.activation(userId, code);
        if(result == ACTIVATION_SUCCESS){
            model.addAttribute("msg" , "激活成功，您的账号现在可以正常使用了");
            model.addAttribute("target", "/login");
        }else if(result == ACTIVATION_REPEAT){
            model.addAttribute("msg" , "无效操作，该账号已经激活过了");
            model.addAttribute("target", "/");
        }else{
            model.addAttribute("msg" , "激活失败，激活码错误");
            model.addAttribute("target", "/");
        }
        return "result";
    }

    /**
     * 1、生成验证码并将其存至session，生成图片
     * 2、使用Redis进行重构，性能更优
     * @param response Response
     */
    @RequestMapping(path = "/kaptcha" , method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response/*, HttpSession session*/){
        //生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        //将验证码存入Session
        //session.setAttribute("kaptcha", text);

        String kaptchaOwner = CommunityUtil.generateUUID();
        Cookie cookie = new Cookie("kaptchaOwner", kaptchaOwner);
        //cookie设置60s，超时自动报销
        cookie.setMaxAge(60);
        cookie.setPath(domain);
        response.addCookie(cookie);

        //存入Redis
        String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
        redisTemplate.opsForValue().set(redisKey, text, 60, TimeUnit.SECONDS);

        //将图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            logger.error("验证码失败" ,e.getMessage());
        }
    }

    /**
     * 登录验证
     * 迭代更新为Redis中取登录信息
     * @param email
     * @param password
     * @param code
     * @param rememberme
     * @param model
     * @param response
     * @return
     */
    @RequestMapping(path = "/login" , method = RequestMethod.POST)
    public String login(String email, String password, String code, boolean rememberme,
                      @CookieValue("kaptchaOwner") String kaptchaOwner, Model model,
            /*HttpSession session,*/ HttpServletResponse response){
//        String kaptcha = (String) session.getAttribute("kaptcha");
        //检查验证码
        String kaptcha = null;

        if (StringUtils.isNotBlank(kaptchaOwner)) {
            String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
            kaptcha = (String) redisTemplate.opsForValue().get(redisKey);
        }
        if(StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)){
            model.addAttribute("CodeMsg", "验证码错误");
            return "/login";
        }

        //检查账号密码
        int expiredSeconds = rememberme ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.loginWithTicket(email, password, expiredSeconds);
        if(map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            //整个路径有效
            cookie.setPath("/");
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            model.addAttribute("EmailMsg", map.get("EmailMsg"));
            model.addAttribute("PasswordMsg", map.get("PasswordMsg"));
            return "/login";
        }

    }

    /**
     * 退出登录状态，将status设置为1（失效）
     * @param ticket
     * @return
     */
    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/login";
    }


}
