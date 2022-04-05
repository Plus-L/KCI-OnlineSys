package com.plusl.kci_onlinesys.service;

import com.plusl.kci_onlinesys.entity.DiscussPost;

import java.util.List;

/**
 * @program: kci_onlinesys
 * @description: 帖子业务接口
 * @author: PlusL
 * @create: 2022-03-14 16:32
 **/
public interface DiscussPostService {
    /**
     * 依据用户ID查询帖子，并分页  -->用于用户个人界面
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<DiscussPost> findDiscussPosts(int userId,int offset,int limit);

    /**
     * 查询当前userID发起帖子条数
     * @param userId
     * @return
     */
    public int findDiscussPostRows(int userId);

    /**
     * 通过id查询帖子
     * @param id
     * @return
     */
    public DiscussPost findeDidcussPostById(int id);

    /**
     * 插入一条帖子
     * @param discussPost
     * @return
     */
    public int addDiscussPost(DiscussPost discussPost);

    /**
     * 更新帖子评论数
     * @param id
     * @param commentCount
     * @return
     */
    public int updateCommentCount(int id, int commentCount);
}
