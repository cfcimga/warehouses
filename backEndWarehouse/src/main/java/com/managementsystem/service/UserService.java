package com.managementsystem.service;

import com.managementsystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserService extends IService<User> {

    List<User> list();

    User findUserById(Long uId);

    User findUserByIdAndPassword(Integer uId,String uPassword);

    int addUser(User user);

    int editUser(User user);

    int deleteUser(@Param("uId")Integer uId);

}
