package com.plusl.kci_onlinesys.controller.advice;

import com.plusl.kci_onlinesys.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: kci_onlinesys
 * @description: 统一处理异常
 * @author: PlusL
 * @create: 2022-04-06 14:24
 **/
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器异常" + e.getMessage());
        for(StackTraceElement stackTraceElement : e.getStackTrace()){
            logger.error(stackTraceElement.toString());
        }

        String SpecialRequestHead = "XMLHttpRequest";
        String xRequestedWith = request.getHeader("x-requested-with");
        if(SpecialRequestHead.equals(xRequestedWith)){
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJsonString(1, "服务器异常！"));
        } else {
            response.sendRedirect(request.getContextPath() + "/error");
        }


    }

}
