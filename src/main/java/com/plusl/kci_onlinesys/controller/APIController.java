package com.plusl.kci_onlinesys.controller;

import com.plusl.kci_onlinesys.entity.User;
import com.plusl.kci_onlinesys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @program: kci_onlinesys
 * @description: 用户管理器
 * @author: PlusL
 * @create: 2022-03-10 15:57
 **/
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * RESTful风格接口
     * @return
     */

    @RequestMapping(value = "/api/user/findall" , method = RequestMethod.GET)
    public List<User> findAllUser(){
        return userService.findAllUser();
    }

    @RequestMapping(value = "/api/user/findbyid/{id}" , method = RequestMethod.GET)
    public User findById(@PathVariable("id") int id){
        return userService.findById(id);
    }

    @RequestMapping(value = "/api/user/addUser" , method = RequestMethod.POST) //POST更多用于增加资源
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @RequestMapping(value = "/api/user/update" , method = RequestMethod.PUT) //PUT更多用于改资源
    public void updateUser(@RequestBody User user){
        userService.updateUser(user);
    }

    @RequestMapping(value = "/api/user/deletebyid/{id}" , method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable("id") int id){
        userService.deleteUserById(id);
    }



/*    @RequestMapping(value = "/text01" , method = RequestMethod.POST)
    @ResponseBody
    public String text01(String email, String password){//在方法的传入参数处，对应form表单中的名字即可取出对应数据
        System.out.println(email);
        System.out.println(password);
        return "测试成功，已在控制台读取数据";
    }*/

/*    @RequestMapping(value = "/dologin" , method = RequestMethod.POST)
    public ModelAndView login(String email, String password){
        ModelAndView mav = new ModelAndView();
        mav.addObject("email", email);
        mav.addObject("password", password);
        mav.setViewName("index");
        return mav;
    }*/

/*
    //  /text?current=1&limit=20
    @RequestMapping(value = "/text" , method = RequestMethod.GET)
    @ResponseBody
    public String getTexting(
            //@RequestParam可读取来自浏览器地址栏传输的数据
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "30") int limit){
        System.out.println(current);
        System.out.println(limit);
        return "这是一个测试用例，测试@RequestParam的作用";
    }*/


}
