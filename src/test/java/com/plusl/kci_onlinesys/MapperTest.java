package com.plusl.kci_onlinesys;

import com.plusl.kci_onlinesys.entity.DiscussPost;
import com.plusl.kci_onlinesys.entity.LoginTicket;
import com.plusl.kci_onlinesys.entity.User;
import com.plusl.kci_onlinesys.mapper.DiscussPostMapper;
import com.plusl.kci_onlinesys.mapper.LoginTicketMapper;
import com.plusl.kci_onlinesys.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

/**
 * @program: kci_onlinesys
 * @description: 测试数据库操作
 * @author: PlusL
 * @create: 2022-03-13 10:57
 **/

@SpringBootTest
@ContextConfiguration(classes = KciOnlinesysApplication.class)
public class MapperTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.findById(1);
        System.out.println(user);

        User ema = userMapper.findByEmail("2732457087@qq.com");
        System.out.println(ema);

        User realname = userMapper.findByRealname("李嘉豪");
        System.out.println(realname);

        User nick = userMapper.findByNickname("PlusL");
        System.out.println(nick);
//        Assert.assertEquals("Plus", nick);
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setEmail("4642346874631@qq.com");
        user.setNickname("帅");
        user.setPassword("123123123132");
        user.setHeadurl("ashjdkajsh153435");
        user.setSex(2);
        Long aLong = userMapper.addUser(user);
        System.out.println(aLong);
        System.out.println(user.getId());
    }

    @Test
    public void testUpdate(){
        User user = userMapper.findById(2);
        user.setEmail("s45d6a354sd3a@qq.com");
        user.setNickname("帅B");
        user.setRealname("贾帅");
        user.setPhone("123456789");
        user.setPassword("123456");
        user.setSex(2);
        user.setGrade(20);
        user.setMajor("软件工程");
        user.setUpdatetime(new Date());
        userMapper.updateUser(user);
        System.out.println(user.getId());
    }

    @Test
    public void testDiscussPost(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPost(0, 0, 10);
        for (DiscussPost post : discussPosts){
            System.out.println(post);
        }

        System.out.println(discussPostMapper.selectDiscussPostRows(1));

    }

    @Test
    public void testTicket(){

        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(1);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+ 60*10*1000));

//        loginTicketMapper.insertLoginTicket(loginTicket);

//        System.out.println(loginTicket1);

        loginTicketMapper.updateStatus("abc",1);
        LoginTicket loginTicket1 = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket1);
    }

}
