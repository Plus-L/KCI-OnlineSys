package com.plusl.kci_onlinesys.service.impl;

import com.plusl.kci_onlinesys.entity.DiscussPost;
import com.plusl.kci_onlinesys.mapper.DiscussPostMapper;
import com.plusl.kci_onlinesys.service.DiscussPostService;
import com.plusl.kci_onlinesys.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @program: kci_onlinesys
 * @description: 帖子业务实现
 * @author: PlusL
 * @create: 2022-03-14 16:37
 **/
@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Override
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPost(userId,offset,limit);
    }

    @Override
    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    @Override
    public DiscussPost findeDidcussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    @Override
    public int addDiscussPost(DiscussPost discussPost) {
        if (discussPost == null){
            throw new IllegalArgumentException("参数不能为空");
        }

        discussPost.setTitle(discussPost.getTitle());
        discussPost.setContent(discussPost.getContent());
        discussPost.setGroupId(discussPost.getGroupId());
        discussPost.setUserId(discussPost.getUserId());
        discussPost.setStatus(1);
        discussPost.setType(1);
        //过滤敏感词
        discussPost.setTitle(sensitiveFilter.filter(discussPost.getTitle()));
        discussPost.setContent(sensitiveFilter.filter(discussPost.getContent()));

        return discussPostMapper.insertDiscussPost(discussPost);
    }

    @Override
    public int updateCommentCount(int id, int commentCount) {
        return discussPostMapper.updateCommentCount(id, commentCount);
    }
}
