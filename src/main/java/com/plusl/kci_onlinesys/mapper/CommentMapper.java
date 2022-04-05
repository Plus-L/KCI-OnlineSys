package com.plusl.kci_onlinesys.mapper;

import com.plusl.kci_onlinesys.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: kci_onlinesys
 * @description: 评论-数据库管理层
 * @author: PlusL
 * @create: 2022-04-02 10:50
 **/
@Repository
@Mapper
public interface CommentMapper {
    /**
     * 通过实体搜寻评论（找到当前实体下的评论）
     * @param entityType
     * @param entityId
     * @param offset
     * @param limit
     * @return
     */
    List<Comment> selectCommentsByEntity(@Param("entityType") int entityType,@Param("entityId") int entityId, @Param("offset") int offset,@Param("limit") int limit);

    /**
     * 根据实体查询评论条数，方便分页
     * @param entityType
     * @param entityId
     * @return
     */
    int selectCountByEntity(@Param("entityType") int entityType, @Param("entityId") int entityId);

    /**
     * 插入评论
     * @param comment
     * @return
     */
    int insertComment(Comment comment);
}
