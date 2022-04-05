package com.plusl.kci_onlinesys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: kci_onlinesys
 * @description: 帖子实体类
 * @author: PlusL
 * @create: 2022-03-13 20:39
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussPost {
    private int id;
    private int userId;
    private int groupId;
    private String title;
    private String content;//帖子内容
    private int type;//类型
    private int status;//状态
    private Date createTime;
    private int commentCount;//评论条数
    private double score;//热度评级
    private String img;//封面图片

}
