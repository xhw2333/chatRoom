package com.dao;

import com.model.Friend;
import com.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * friend表
 */
public class FriendDao {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/chatroom?characterEncoding=utf-8&serverTimezone=GMT%2B8";
    private static final String UserName = "root";
    private static final String Password = "root";

    public static void main(String[] args) {
        Friend friend = new Friend(1,3);
        System.out.println(friend.getFriend_id() + ",,,," + friend.getMy_id());
//        add(friend.getMy_id(),friend.getFriend_id());
        delete(friend.getMy_id(),friend.getFriend_id());
//        List<Friend> list= getAll(1);
//        for(int i = 0; i < list.size(); i++){
//            System.out.println(list.get(i));
//        }
    }

    /**
     * @descripe 插入
     * @param my_id
     * @param friend_id
     */
    public static void add(int my_id,int friend_id){

        String sql = "insert into friend VALUES(null,?,?)";

        Connection connection = null;
        PreparedStatement statement = null;
        try{
            //加载驱动
            Class.forName(driver);
            //获得数据库的链接
            connection = DriverManager.getConnection(url, UserName,Password);
            //创建预编译的Statement
            statement = connection.prepareStatement(sql);
            //设置参数
            statement.setInt(1,my_id);
            statement.setInt(2,friend_id);
            //执行
            statement.executeUpdate();

            // 再次添加数据，对方也要添加好友嘛
            //设置参数
            statement.setInt(1,friend_id);
            statement.setInt(2,my_id);
            //执行
            statement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @descripe 删除
     * @param my_id
     * @param friend_id
     */
    public static void delete(int my_id,int friend_id){

        String sql = "DELETE FROM friend WHERE my_id = ? AND friend_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        try{
            //加载驱动
            Class.forName(driver);
            //获得数据库的链接
            connection = DriverManager.getConnection(url, UserName,Password);
            //创建预编译的Statement
            statement = connection.prepareStatement(sql);
            //设置参数
            statement.setInt(1,my_id);
            statement.setInt(2,friend_id);
            //执行
            statement.executeUpdate();

            //设置参数
            statement.setInt(1,friend_id);
            statement.setInt(2,my_id);
            //执行
            statement.executeUpdate();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查是否存在此关系
     * @param my_id
     * @param friend_id
     * @return
     */
    public static boolean check(int my_id,int friend_id){

        String sql = "SELECT * from friend WHERE `my_id` = ? AND `friend_id` = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try{
            //加载驱动
            Class.forName(driver);
            //获得数据库的链接
            connection = DriverManager.getConnection(url, UserName,Password);
            //创建预编译的Statement
            statement = connection.prepareStatement(sql);
            //设置参数
            statement.setInt(1,my_id);
            statement.setInt(2,friend_id);
            //执行
            statement.executeQuery();

            // 再次添加数据，对方也要添加好友嘛
            //设置参数
            statement.setInt(1,friend_id);
            statement.setInt(2,my_id);
            //执行
            rs = statement.executeQuery();

            if(rs.next()){
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取好友列表
     * @param id
     * @return
     */
    public static List<Friend> getAll(int id){

        String sql = "SELECT * from friend WHERE my_id = ?";
        List<Friend> list = new ArrayList<Friend>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            //加载驱动
            Class.forName(driver);
            //获得数据库的链接
            connection = DriverManager.getConnection(url, UserName,Password);
            //预编译
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            //执行sql语句
            rs = statement.executeQuery();
            while(rs.next()){
                Friend friend = new Friend();

                friend.setMy_id(rs.getInt("my_id"));
                friend.setFriend_id(rs.getInt("friend_id"));

                list.add(friend);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
