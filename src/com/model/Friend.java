package com.model;

/**
 * 好友模型
 */
public class Friend {
    private int my_id;
    private int friend_id;

    public Friend(int my_id, int friend_id) {
        this.my_id = my_id;
        this.friend_id = friend_id;
    }

    public Friend(){

    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public int getMy_id() {
        return my_id;
    }

    public void setMy_id(int my_id) {
        this.my_id = my_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    @Override
    public String toString() {
        return "{" +
                "my_id:" + my_id +
                ", friend_id:" + friend_id +
                '}';
    }
}
