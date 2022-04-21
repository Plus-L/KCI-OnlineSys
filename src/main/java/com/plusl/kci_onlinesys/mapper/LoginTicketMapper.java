package com.plusl.kci_onlinesys.mapper;

import com.plusl.kci_onlinesys.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @program: kci_onlinesys
 * @description: 登录凭证Mapper  update:@Deprecated 2022.4.18 不再推荐使用
 * @author: PlusL
 * @create: 2022-03-19 15:48
 **/

@Mapper
@Deprecated
public interface LoginTicketMapper {

    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    @Update({
            "update login_ticket set status=#{status} where ticket=#{ticket}"
    })
    int updateStatus(@Param("ticket") String ticket,@Param("status") int status);
}
