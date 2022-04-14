package com.plusl.kci_onlinesys.util;

/**
 * @program: kci_onlinesys
 * @description: 生成RedisKey的工具类
 * @author: PlusL
 * @create: 2022-04-07 20:13
 **/
public class RedisKeyUtil {
    //分隔符
    private static final String SPLIT = ":";
    //实体的赞：前缀
    private static final String PREFIX_ENTITY_LICK = "like:entity";

    //某个实体的赞

    /**
     * lick:entity:entityType:entityId -> set(userid)
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @return
     */
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LICK + SPLIT + entityType + SPLIT + entityId;
    }
}
