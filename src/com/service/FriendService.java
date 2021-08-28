package com.service;

import com.dao.UserDao;
import com.model.Friend;
import com.model.User;
import com.dao.FriendDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 关于好友业务
 */
public class FriendService {
    private FriendDao friendDao = new FriendDao();
    private UserDao userDao = new UserDao();

    public static void main(String[] args) {
        FriendService f = new FriendService();
        List<User> g = f.getMyFriend(1);
        System.out.println(g);
    }

    /**
     * 添加好友
     * @param my_id
     * @param friend_id
     * @return
     */
    public boolean addFriend(int my_id,int friend_id){
//        不能添加自己
        if(my_id == friend_id){
            return false;
        }
//        已存在好友关系
        if(FriendDao.check(my_id,friend_id)){
            return false;
        }
        FriendDao.add(my_id,friend_id);
        return true;
    }

    /**
     * 删除好友
     * @param my_id
     * @param friend_id
     * @return
     */
    public boolean deleteFriend(int my_id,int friend_id){
//        判断此好友是否存在
        if(FriendDao.check(my_id,friend_id)){
            FriendDao.delete(my_id,friend_id);
            return true;
        }
        return false;

    }

    /**
     * 获取好友列表
     * @param uid
     * @return
     */
    public List<User> getMyFriend(int uid){
        List<Friend> list = friendDao.getAll(uid);
        List<User> friends = new ArrayList<User>();
        for(int i = 0; i < list.size();i++){
//            补充好友信息
            User user = userDao.getOneById(list.get(i).getFriend_id());
            friends.add(user);
        }
        return friends;
    }
}
