package com.plusl.kci_onlinesys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: kci_onlinesys
 * @description: 登录凭证
 * @author: PlusL
 * @create: 2022-03-19 15:46
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginTicket{

    private int id;
    private int userId;
    private String ticket;
    private int status;
    private Date expired;
}
