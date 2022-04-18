package com.plusl.kci_onlinesys.controller;

import com.plusl.kci_onlinesys.entity.User;
import com.plusl.kci_onlinesys.service.FollowService;
import com.plusl.kci_onlinesys.util.CommunityUtil;
import com.plusl.kci_onlinesys.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: kci_onlinesys
 * @description: 关注控制器
 * @author: PlusL
 * @create: 2022-04-17 17:00
 **/
@Controller
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public String follow(int entityType, int entityId) {
        User user = hostHolder.getUser();

        followService.follow(user.getId(), entityType, entityId);

        return CommunityUtil.getJsonString(0, "已关注！");
    }

    @RequestMapping(path = "/unfollow", method = RequestMethod.POST)
    @ResponseBody
    public String unfollow(int entityType, int entityId) {
        User user = hostHolder.getUser();

        followService.unFollow(user.getId(), entityType, entityId);

        return CommunityUtil.getJsonString(0, "取关成功！");
    }

}
