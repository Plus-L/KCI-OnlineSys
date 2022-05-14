package com.plusl.kci_onlinesys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: kci_onlinesys
 * @description: 私信信息实体
 * @author: PlusL
 * @create: 2022-04-30 14:32
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String conversationId;
    private String content;
    private int status;
    private Date createTime;

}
