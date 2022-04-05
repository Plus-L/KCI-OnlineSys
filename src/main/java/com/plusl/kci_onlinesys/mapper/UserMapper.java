package com.plusl.kci_onlinesys.mapper;

import com.plusl.kci_onlinesys.entity.User;
import com.plusl.kci_onlinesys.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: kci_onlinesys
 * @description: 用户数据库操作
 * @author: PlusL
 * @create: 2022-03-09 20:47
 **/

@Repository
@Mapper
public interface UserMapper {
    /**
     * 添加用户/注册
     */
    Long addUser(User user);

    /**
     * 列出所有用户
     */
    List<User> findAllUser();

    /**
     * 通过ID查询
     */
    User findById(@Param("id") int id);

    /**
     * 通过邮箱查询
     */
    User findByEmail(@Param("email") String email);

    /**
     * 通过realname查询
     */
    User findByRealname(@Param("realname") String realname);

    /**
     * 通过nickname查询
     */
    User findByNickname(@Param("nickname") String nickname);

    /**
     * updateUser
     */
    Long updateUser(User user);

    /**
     * 更新激活状态
     * @param id
     * @return
     */
    int updateStatus(@Param("id") int id,@Param("status") int status);

    /**
     * 更新头像
     * @param userId
     * @param headurl
     * @return
     */
    int updateHeadUrl(@Param("id") int userId, @Param("headurl") String headurl);

    /**
     * deleteUser
     */
    Long deleteUserById(@Param("id") int id);

}
