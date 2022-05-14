package com.plusl.kci_onlinesys.mapper;

import com.plusl.kci_onlinesys.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: kci_onlinesys
 * @description: 私信Mapper
 * @author: PlusL
 * @create: 2022-04-30 14:37
 **/
@Mapper
public interface MessageMapper {

    /**
     * 查询当前用户的会话列表，针对每个会话只显示一条信息
     * @param userId 用户ID
     * @param offset 当前页的起始消息ID
     * @param limit 每页消息数量
     * @return 当前用户的会话列表
     */
    List<Message> selectConversations(int userId, int offset, int limit);

    /**
     * 查询当前用户的会话数
     * @param userId 用户ID
     * @return 当前用户的会话数量
     */
    int selectConversationCount(int userId);

    /**
     * 查询某个会话的私信列表
     * @param conversationId 会话ID
     * @param offset 当前页的起始消息ID
     * @param limit 每页消息数量
     * @return 某会话的私信列表
     */
    List<Message> selectLetters(String conversationId, int offset, int limit);

    /**
     * 查询私信数
     * @param conversationId 会话ID
     * @return 私信数量
     */
    int selectLetterCount(String conversationId);

    /**
     * 查询未读私信数
     * @param userId 用户ID
     * @param conversationId 会话ID
     * @return 用户未读私信数
     */
    int selectUnReadLetterCount(int userId, String conversationId);


}
