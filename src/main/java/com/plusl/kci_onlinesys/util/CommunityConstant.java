package com.plusl.kci_onlinesys.util;

/**
 * @program: kci_onlinesys
 * @description: 社区常量接口
 * @author: PlusL
 * @create: 2022-03-17 16:28
 **/
public interface CommunityConstant {
    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * 默认登录凭证超时时间, 默认12小时
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * 勾选记住我状态下的登录凭证超时时间, 默认30天
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24;

    /**
     * 实体类型：帖子
     */
    int ENTITY_TYPE_POST = 1;

    /**
     * 实体类型：评论
     */
    int ENTITY_TYPE_COMMENT = 2;
}
