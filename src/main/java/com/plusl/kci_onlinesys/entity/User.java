package com.plusl.kci_onlinesys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: kci_onlinesys
 * @description: 用户实例
 * @author: PlusL
 * @create: 2022-03-09 19:25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /** 用户ID */
    private Integer id;

    /** 用户邮箱 */
    private String email;

    /** 用户密码 */
    private String password;

    /** 加密盐 */
    private String salt;

    /** 头像 */
    private String headurl;

    /** 用户昵称 */
    private String nickname;

    /** 用户真实姓名 */
    private String realname;

    /** 用户类型  0-普通用户 1-工作室成员 2-管理员 */
    private int usertype;

    /** 用户电话 */
    private String phone;

    /** 用户性别  0-男  1-女 */
    private int sex;

    /** 用户状态  0-离线  1-在线 */
    private int status;

    /** 用户年级（如19届） */
    private int grade;

    /** 用户创建账号时间 */
    private Date createtime;

    /** 用户更新时间 */
    private Date updatetime;

    /** 用户专业 */
    private String major;

    /** 激活码 */
    private String activationCode;
}
