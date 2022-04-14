package com.plusl.kci_onlinesys.mapper;

import com.plusl.kci_onlinesys.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: kci_onlinesys
 * @description: 帖子数据库管理层
 * @author: PlusL
 * @create: 2022-03-13 21:04
 **/
@Mapper
public interface DiscussPostMapper {

    /**
     * 分页查询
     * @param userId
     * @param offset 当前行数
     * @param limit 每页限制行数
     * @return
     */
    List<DiscussPost> selectDiscussPost(@Param("userId") int userId,@Param("offset") int offset,@Param("limit") int limit);

    /**
     * 返回帖子条数
     * @param userId
     * @return
     */
    int selectDiscussPostRows(@Param("userId") int userId);

    /**
     * 通过id查询帖子
     * @param id
     * @return
     */
    DiscussPost selectDiscussPostById(int id);

    /**
     * 插入一条帖子
     * @param discussPost
     * @return
     */
    int insertDiscussPost(DiscussPost discussPost);

    /**
     * 更新当前帖子评论数
     * @param id
     * @param commentCount
     * @return
     */
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

}
