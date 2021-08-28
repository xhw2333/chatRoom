package com.service;

import com.dao.UserDao;
import com.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于用户的业务
 */
public class UserService {

    private UserDao userDao = new UserDao();

    /**
     * @descripe 用户注册
     * @param username
     * @param password
     * @return
     */
    public boolean register(String username,String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if(userDao.check(username,password) != null){
            return false;
        }
        userDao.insert(user);
        return true;
    }

    /**
     * @descripe 用户登录
     * @param username
     * @param password
     * @return
     */
    public User login(String username,String password){
        User user = userDao.check(username,password);
        if(user != null){
            return user;
        }
        return null;
    }

    /**
     * @descripe 获得所有用户列表
     * @return
     */
    public List<User> getUserList(){
        return userDao.getAllUser();
    }

    /***
     * @Description: 查询好友
     * @param: [name]
     * @return: java.util.List<com.model.User>
     * @auther:xhw
     * @date: 2021/6/7 23:30
     */
    public List<User> searchFriend(String name){
        return userDao.getAllByName(name);
    }
}
