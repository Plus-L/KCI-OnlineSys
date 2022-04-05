package com.plusl.kci_onlinesys.controller.interceptor;

import com.plusl.kci_onlinesys.annotation.LoginRequired;
import com.plusl.kci_onlinesys.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @program: kci_onlinesys
 * @description: 要求登录拦截器，登录后才可继续执行
 * @author: PlusL
 * @create: 2022-03-22 14:32
 **/
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod) {//判断handler是否属于HandlerMethod这个类型，即方法类型，HandlerMethod由SpringMVC提供
            HandlerMethod handlerMethod = (HandlerMethod) handler;//转型
            Method method = handlerMethod.getMethod();
            LoginRequired annotation = method.getAnnotation(LoginRequired.class);
            if(annotation != null && hostHolder.getUser() == null){
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }

        return true;
    }
}
