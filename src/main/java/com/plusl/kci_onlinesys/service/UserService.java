package com.plusl.kci_onlinesys.service;

import com.plusl.kci_onlinesys.entity.LoginTicket;
import com.plusl.kci_onlinesys.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @program: kci_onlinesys
 * @description: 用户业务接口
 * @author: PlusL
 * @create: 2022-03-09 22:59
 **/
public interface UserService {
    List<User> findAllUser();

    User findById(int id);

    Long addUser(User user);

    Long updateUser(User user);

    Long deleteUserById(int id);

    Map<String, Object> register(User user);

    int updateStatus(int userId, int status);

    public int activation(int userid, String code);//激活

    public Map<String, Object> loginWithTicket(String email, String password, int expiredSeconds);//伴随凭证登录

    public void logout(String ticket);//登出操作

    public LoginTicket findLoginTicket(String ticket);

    int updateHeadUrl(int id, String headUrl);

    User getUserCache(int userId);

    User initCache(int userId);

    void clearCache(int userId);


}
