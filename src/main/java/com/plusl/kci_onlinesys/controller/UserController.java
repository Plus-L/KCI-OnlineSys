package com.plusl.kci_onlinesys.controller;

import com.plusl.kci_onlinesys.annotation.LoginRequired;
import com.plusl.kci_onlinesys.entity.User;
import com.plusl.kci_onlinesys.service.UserService;
import com.plusl.kci_onlinesys.util.CommunityUtil;
import com.plusl.kci_onlinesys.util.HostHolder;
import jdk.nashorn.internal.ir.ReturnNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @program: kci_onlinesys
 * @description: 用户管理器
 * @author: PlusL
 * @create: 2022-03-20 19:51
 **/

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 账号设置界面GET原始数据
     * @param model
     * @return
     */
    @LoginRequired
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String getSettingPage(Model model) {
        User user = hostHolder.getUser();
        System.out.println(user);
        model.addAttribute("Email", user.getEmail());
        model.addAttribute("nickName", user.getNickname());
        model.addAttribute("realName", user.getRealname());
        model.addAttribute("Phone", user.getPhone());
        if(user.getSex() == 0){
            model.addAttribute("Sex", "男");
        }else if (user.getSex() == 1){
            model.addAttribute("Sex", "女");
        }
        model.addAttribute("Grade", user.getGrade());
        model.addAttribute("Major", user.getMajor());
        return "setting";
    }

    /**
     * 账号设置界面执行修改
     * @param user
     * @return
     */
    @RequestMapping(value = "/setting", method = RequestMethod.POST)
    public String currentSelfInformation(Model model, User user){
        if(user == null){
            model.addAttribute("UserError", "");
        }

        userService.updateUser(user);
        return "setting";
    }

    /**
     * 头像上传并更新
     * @param headImage
     * @param model
     * @return
     */
    @LoginRequired
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadHeadImage(MultipartFile headImage, Model model){

        if(headImage == null){
            model.addAttribute("error", "您还没有选择图片");
            return "setting";
        }

        String fileName = headImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)){
            model.addAttribute("error", "文件格式不正确");
            return "setting";
        }

        /**
         * 生成随机文件名,存到本机位置
         */
        fileName = CommunityUtil.generateUUID() + suffix;
        File dest = new File(uploadPath + "/" + fileName);
        try {
            headImage.transferTo(dest);//存储文件
        } catch (IOException e) {
            logger.error("上传文件失败" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常", e);
        }

        /**
         * 更新当前用户头像路径（WEB访问路径）
         */
        User user = hostHolder.getUser();
        String headUrl = domain + "/user/head/" + fileName;
        userService.updateHeadUrl(user.getId(), headUrl);

        return "redirect:/";
    }

    /**
     * 从本机读取头像
     * @param fileName
     * @param response
     */
    @RequestMapping(path = "/head/{fileName}", method = RequestMethod.GET)
    public void getHeadImage(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        fileName = uploadPath + "/" + fileName;

        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //响应图片
        response.setContentType("image/" + suffix);
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            //输出流，因为图片是二进制流
            OutputStream os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fileInputStream.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }

        } catch (IOException e) {
            logger.error("读取头像失败"+ e.getMessage());
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateSelfInformation(User user){
        User localUser = hostHolder.getUser();
        user.setId(localUser.getId());
        userService.updateUser(user);

        return "redirect:/user/setting";
    }

}
