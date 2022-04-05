package com.plusl.kci_onlinesys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: kci_onlinesys
 * @description: 评论实体
 * @author: PlusL
 * @create: 2022-04-01 20:37
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private int id;
    private int userId;
    /**
     * 评论的类型，是对资源的评论还是对评论的评论
     */
    private int entityType;
    /**
     * 评论对象的ID
     */
    private int entityId;
    /**
     * 指向发起被评论文章/资源/评论的人
     */
    private int targetId;
    private String content;
    private int status;
    private Date createTime;

}
