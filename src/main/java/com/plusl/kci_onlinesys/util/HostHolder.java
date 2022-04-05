package com.plusl.kci_onlinesys.util;

import com.plusl.kci_onlinesys.entity.User;
import org.springframework.stereotype.Component;

/**
 * @program: kci_onlinesys
 * @description: 持有用户信息，用于代替session对象
 * @author: PlusL
 * @create: 2022-03-20 15:09
 **/
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }

}
