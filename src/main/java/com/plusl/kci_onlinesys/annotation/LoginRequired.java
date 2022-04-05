package com.plusl.kci_onlinesys.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: kci_onlinesys
 * @description: 自定义注解：登录后才能访问
 * @author: PlusL
 * @create: 2022-03-22 14:27
 **/
@Target(ElementType.METHOD)// 该注解作用在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时生效
public @interface LoginRequired {
    //仅做标记，实际业务处理在拦截器中执行
}
