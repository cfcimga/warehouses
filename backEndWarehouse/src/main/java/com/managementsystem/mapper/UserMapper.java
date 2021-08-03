package com.managementsystem.mapper;

import com.managementsystem.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {

    List<User> findAll();

    User findUserById(@Param("uId") Long uId);

    User findUserByIdAndPassword(@Param("uId")Integer uId,@Param("uPassword") String uPassword );

    int addUser(User user);

    int editUser(User user);

    int deleteUser(@Param("uId")Integer uId);

}
