package com.plusl.kci_onlinesys.service;

import com.plusl.kci_onlinesys.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @program: kci_onlinesys
 * @description: 点赞业务
 * @author: PlusL
 * @create: 2022-04-07 20:22
 **/

@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 点赞行为
     * @param userId 用户ID
     * @param entityType 实体类型
     * @param entityId 实体ID
     */
    public void like(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
        if (isMember) {
            // Redis中已存在该用户的点赞信息，即用户点了两次，移除赞
            redisTemplate.opsForSet().remove(entityLikeKey, entityId);
        } else {
            // Redis中不存在点赞信息，则加入redis
            redisTemplate.opsForSet().add(entityLikeKey, entityId);
        }
    }

    /**
     * 获得某一实体获得的点赞数
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @return 操作行数
     */
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    /**
     * 查询某人对某实体的点赞状态
     * @param userId 用户ID
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @return 点赞状态
     */
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }


}
