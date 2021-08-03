package com.managementsystem.service.impl;

import com.managementsystem.entity.User;
import com.managementsystem.mapper.UserMapper;
import com.managementsystem.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public List<User> list(){
        return userMapper.findAll();
    }
    @Override
    public User findUserById(Long uId){
        return userMapper.findUserById(uId);
    }

    @Override
    public User findUserByIdAndPassword(Integer uId,String uPassword){
        return userMapper.findUserByIdAndPassword(uId,uPassword);
    }

    @Override
    public int addUser(User user){
        return userMapper.addUser(user);
    }

    @Override
    public int editUser(User user){
        return userMapper.editUser(user);
    }

    @Override
    public int deleteUser(Integer uId){
        return userMapper.deleteUser(uId);
    }

}
