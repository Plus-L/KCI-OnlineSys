package com.plusl.kci_onlinesys.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: kci_onlinesys
 * @description: AOP统一记录日志
 * @author: PlusL
 * @create: 2022-04-06 20:32
 **/
@Component
@Aspect
public class ServiceLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Pointcut("execution(* com.plusl.kci_onlinesys.service.*.*(..))")
    public void pointcut(){

    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String IP = request.getRemoteHost();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        LOGGER.info(String.format("用户[%s],在[%s],访问了[%s]",IP ,now ,target));
    }


}
