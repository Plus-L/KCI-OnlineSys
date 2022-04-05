package com.plusl.kci_onlinesys.service;


import com.plusl.kci_onlinesys.entity.Comment;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: kci_onlinesys
 * @description: 评论业务接口
 * @author: PlusL
 * @create: 2022-04-02 11:03
 **/

public interface CommentService {
    /**
     * 通过实体搜寻评论（找到当前实体下的评论）
     * @param entityType
     * @param entityId
     * @param offset
     * @param limit
     * @return
     */
    public List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int limit);

    /**
     * 根据实体查询评论条数，方便分页
     * @param entityType
     * @param entityId
     * @return
     */
    public int findCountByEntity(int entityType, int entityId);

    /**
     * 增加评论
     * 事务隔离级别（READ_COMMITTED读取已提交，可防止脏读）
     * @param comment
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addComment(Comment comment);
}
