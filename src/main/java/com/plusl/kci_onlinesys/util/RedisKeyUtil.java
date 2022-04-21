package com.plusl.kci_onlinesys.util;

/**
 * @program: kci_onlinesys
 * @description: 生成RedisKey的工具类
 * @author: PlusL
 * @create: 2022-04-07 20:13
 **/
public class RedisKeyUtil {
    /**
     * 分隔符
     */
    private static final String SPLIT = ":";
    /**
     * 实体的赞：前缀
     */
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    /**
     * 用户赞
     */
    private static final String PREFIX_USER_LIKE = "like:user";
    /**
     * 某个用户关注的实体
     */
    private static final String PREFIX_FOLLOWEE = "follwee";
    /**
     * 某个用户的粉丝
     */
    private static final String PREFIX_FOLLOWER = "follwer";
    /**
     * 验证码
     */
    private static final String PREFIX_KAPTCHA = "kaptcha";
    /**
     * 登录凭证
     */
    private static final String PREFIX_TICKET = "ticket";
    /**
     * 用户
     */
    private static final String PREFIX_USER = "user";


    //某个实体的赞

    /**
     * like:entity:entityType:entityId -> set(userid)
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @return
     */
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * like:user:userId -> int  此处opsForValue的get方法;简单来说调用incr后得到 值不会出错是没有经过redistemplate的deserialize, 而get必须经过
     * @param userId
     * @return
     */
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    /**
     * 某个用户关注的实体 followee:userId:entityType -> zset(entityId, now)
     * now指当前时间，用作排序
     * @param userId
     * @param entityType
     * @return
     */
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }

    /**
     * 某个用户的粉丝 follwer:entityType:entityId -> zset(userId, now)
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getFollowerKey(int entityType, int entityId){
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * 获取验证码key
     * @param owner
     * @return
     */
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    /**
     * 获取用户登录凭证key
     * @param ticket
     * @return
     */
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    /**
     * 获取用户key
     * @param userId
     * @return
     */
    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }

}
