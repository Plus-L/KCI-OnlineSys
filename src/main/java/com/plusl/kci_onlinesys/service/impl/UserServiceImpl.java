package com.plusl.kci_onlinesys.service.impl;

import com.plusl.kci_onlinesys.entity.LoginTicket;
import com.plusl.kci_onlinesys.entity.User;
import com.plusl.kci_onlinesys.mapper.LoginTicketMapper;
import com.plusl.kci_onlinesys.mapper.UserMapper;
import com.plusl.kci_onlinesys.service.UserService;
import com.plusl.kci_onlinesys.util.CommunityConstant;
import com.plusl.kci_onlinesys.util.CommunityUtil;
import com.plusl.kci_onlinesys.util.MailClient;
import com.plusl.kci_onlinesys.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: kci_onlinesys
 * @description: 用户业务接口实现
 * @author: PlusL
 * @create: 2022-03-10 15:05
 **/
@Service
public class UserServiceImpl implements UserService, CommunityConstant {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RedisTemplate redisTemplate;


    @Value("${community.path.domain}")
    private String domain;

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

    @Override
    public User findById(int id) {
//        return userMapper.findById(id);
        User user = getUserCache(id);
        if (user == null) {
            user = initCache(id);
        }
        return user;
    }

    @Override
    public Long addUser(User user) {
        return userMapper.addUser(user);
    }

    /**
     * 账号设置-更新个人信息
     *
     * @param user
     * @return
     */
    @Override
    public Long updateUser(User user) {
        //处理特殊情况
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (user.getNickname() != null) {
            user.setNickname(user.getNickname());
        }
        if (user.getRealname() != null) {
            user.setRealname(user.getRealname());
        }
        if (user.getPhone() != null) {
            user.setPhone(user.getPhone());
        }
        if (user.getGrade() != 0) {
            user.setGrade(user.getGrade());
        }
        if (user.getMajor() != null) {
            user.setMajor(user.getMajor());
        }

        user.setSex(user.getSex());
        System.out.println(user.getSex());
        user.setUpdatetime(new Date());

        return userMapper.updateUser(user);
    }

    @Override
    public Long deleteUserById(int id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public int updateStatus(int userId, int status) {
        return userMapper.updateStatus(userId, status);
    }

    /**
     * 注册功能
     *
     * @param user
     * @return
     */
    @Override
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();

        //处理特殊情况
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("EmailMsg", "邮箱不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("PasswordMsg", "密码不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getNickname())) {
            map.put("nicknameMsg", "昵称不能为空");
        }

        //验证账号
        User userMapperByEmail = userMapper.findByEmail(user.getEmail());
        if (userMapperByEmail != null) {
            map.put("EmailMsg", "该邮箱已注册");
            return map;
        }
        User userMapperByNickname = userMapper.findByNickname(user.getNickname());
        if (userMapperByNickname != null) {
            map.put("nicknameMsg", "该昵称已存在");
            return map;
        }

        /**
         * 开始注册
         */
        //生成随机字符串，然后取前五位
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setUsertype(0);
        user.setStatus(0);
        user.setPhone(user.getPhone());
        //生成激活码
        user.setActivationCode(CommunityUtil.generateUUID());
        if (user.getSex() == 0) {
            //默认头像男
            user.setHeadurl("https://s2.loli.net/2022/03/16/IzRmq5aEfxb4giw.png");
        } else if (user.getSex() == 1) {
            //默认头像女
            user.setHeadurl("https://s2.loli.net/2022/03/16/jkRArL7D4ISVofl.png");
        }
        user.setCreatetime(new Date());
        System.out.println(user);
        userMapper.addUser(user);

        /**
         * 激活邮件
         */
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        //需要获取用户id知道是哪个用户在激活
        String url = domain + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "KingCola-ICG", content);

        return map;
    }

    /**
     * 执行激活
     *
     * @param userId
     * @param code
     * @return
     */
    @Override
    public int activation(int userId, String code) {
        User user = userMapper.findById(userId);
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId, 1);
            clearCache(userId);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    /**
     * 执行登录、生成Ticket
     *
     * @param email
     * @param password
     * @param expiredSeconds
     * @return
     */
    @Override
    public Map<String, Object> loginWithTicket(String email, String password, int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();

        //空值处理
        if (StringUtils.isBlank(email)) {
            map.put("EmailMsg", "邮箱不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("PasswordMsg", "密码不能为空");
            return map;
        }

        User user = userMapper.findByEmail(email);
        if (user == null) {
            map.put("EmailMsg", "该邮箱不存在");
            return map;
        }
        if (user.getStatus() == 0) {
            map.put("EmailMsg", "该邮箱未激活");
            return map;
        }

        //验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("PasswordMsg", "密码不正确");
            return map;
        }

        //生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));

        //loginTicketMapper.insertLoginTicket(loginTicket);
        //使用redis重构
        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(redisKey, loginTicket);

        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    /**
     * 登出操作
     *
     * @param ticket
     */
    @Override
    public void logout(String ticket) {
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(redisKey, loginTicket);
    }

    @Override
    public LoginTicket findLoginTicket(String ticket) {
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 更新头像
     *
     * @param id
     * @param headUrl
     * @return
     */
    @Override
    public int updateHeadUrl(int id, String headUrl) {
        int rows = userMapper.updateHeadUrl(id, headUrl);
        clearCache(id);
        return rows;
    }

    /**
     * 1、优先从缓存中获取User
     *
     * @param userId
     * @return
     */
    @Override
    public User getUserCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        return (User) redisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 2、未能从缓存中取到时初始化缓存数据
     *
     * @param userId
     * @return
     */
    @Override
    public User initCache(int userId) {
        User user = userMapper.findById(userId);
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.opsForValue().set(redisKey, user, 3600, TimeUnit.SECONDS);//保留3600秒
        return user;
    }

    /**
     * 3、清除缓存
     * @param userId
     */
    @Override
    public void clearCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }


}
