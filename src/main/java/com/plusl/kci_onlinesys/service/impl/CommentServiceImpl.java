package com.plusl.kci_onlinesys.service.impl;

import com.plusl.kci_onlinesys.entity.Comment;
import com.plusl.kci_onlinesys.mapper.CommentMapper;
import com.plusl.kci_onlinesys.service.CommentService;
import com.plusl.kci_onlinesys.service.DiscussPostService;
import com.plusl.kci_onlinesys.util.CommunityConstant;
import com.plusl.kci_onlinesys.util.SensitiveFilter;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @program: kci_onlinesys
 * @description: 评论业务实现
 * @author: PlusL
 * @create: 2022-04-02 11:06
 **/
@Service
public class CommentServiceImpl implements CommentService, CommunityConstant {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private DiscussPostService discussPostService;

    @Override
    public List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectCommentsByEntity(entityType, entityId, offset, limit);
    }

    @Override
    public int findCountByEntity(int entityType, int entityId) {
        return commentMapper.selectCountByEntity(entityType, entityId);
    }

    @Override
    public int addComment(Comment comment) {
        if(comment == null){
            throw new IllegalArgumentException("参数不能为空");
        }

        //添加评论
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        int rows = commentMapper.insertComment(comment);

        //更新帖子评论数
        if (comment.getEntityType() == ENTITY_TYPE_POST){
            int count = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(), count);
        }

        return rows;
    }
}
