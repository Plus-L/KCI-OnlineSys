package com.plusl.kci_onlinesys.util;

import sun.security.util.Cache;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: kci_onlinesys
 * @description: Cookie工具类
 * @author: PlusL
 * @create: 2022-03-20 14:49
 **/
public class CookieUitl {

    public static String getValue(HttpServletRequest request, String name){
        if(request == null || name == null){
            throw new IllegalArgumentException("参数为空");
        }

        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
            for (Cookie cookie : cookies){
                if(cookie.getName().equals(name)){
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

}
