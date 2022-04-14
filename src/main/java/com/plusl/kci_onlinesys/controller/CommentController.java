package com.plusl.kci_onlinesys.controller;

import com.plusl.kci_onlinesys.entity.Comment;
import com.plusl.kci_onlinesys.service.CommentService;
import com.plusl.kci_onlinesys.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalTime;
import java.util.Date;

/**
 * @program: kci_onlinesys
 * @description: 评论控制器
 * @author: PlusL
 * @create: 2022-04-05 19:48
 **/
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(value = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment){
        //后面会做统一的异常处理
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        return "redirect:/community/detail/" + discussPostId;
    }
}
