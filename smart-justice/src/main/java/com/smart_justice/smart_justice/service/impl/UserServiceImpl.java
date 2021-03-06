package com.smart_justice.smart_justice.service.impl;

import com.smart_justice.smart_justice.mapper.UserMapper;
import com.smart_justice.smart_justice.model.User;
import com.smart_justice.smart_justice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 总用户服务实现类
 *
 * @author tudou
 * @version 1.0
 * @date 2020/7/11 21:38
 **/

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean registerUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public boolean updateUserInfo(User user) {
        User reUser=userMapper.getUserById(user.getId());
        if(reUser==null){
            return false;
        }
        if(user.getPhone()!=null&& !user.getPhone().equals(reUser.getPhone())){
            reUser.setPhone(user.getPhone());
        }
        if(user.getEmail()!=null&& !user.getEmail().equals(reUser.getEmail())){
            reUser.setEmail(user.getEmail());
            reUser.setIsValid(0);
        }
        if(user.getRealName()!=null&& !user.getRealName().equals(reUser.getRealName())){
            reUser.setRealName(user.getRealName());
        }
        return userMapper.updateUser(reUser);
    }

    @Override
    public boolean loginUser(String username, String password) {
        User user=userMapper.getUserByUsername(username);
        if (user==null){
            return false;
        }
        return user.getPassword().equals(password);
    }

    @Override
    public User getUserInfo(Integer id) {
       return userMapper.getUserById(id);
    }

    @Override
    public User getUserInfo(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public boolean isUsernameExist(String username) {
        User user=userMapper.getUserByUsername(username);
        return user != null;
    }

    @Override
    public boolean isUserEmailExist(String email) {
        User user=userMapper.getUserByEmail(email);
        return user != null;
    }
}
