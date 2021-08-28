package com.dao;

import com.model.*;
import net.sf.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/chatroom?characterEncoding=utf-8&serverTimezone=GMT%2B8";
    private static final String UserName = "root";
    private static final String Password = "root";

    //
    public static void main(String[] args) {
//        boolean num;
//        User user = new User();
//        user.setUsername("admin");
//        user.setPassword("123456");
//        insert(user);
//        num = check("xhw1","123456");
//        List<User> list = new ArrayList<User>();
//        list = getAllUser();
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
//        System.out.println(num);
        System.out.println(getAllByName("xhw"));

    }

    /**
     * @describe 插入数据
     * @param user
     */
    public static void insert(User user){

        String sql = "insert into account VALUES(null,?,?)";

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
            statement.setString(1,user.getUsername());
            statement.setString(2,user.getPassword());
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
     * * @describe 查找用户数据
     * @param username
     * @param password
     * @return
     */
    public static User check(String username, String password){

        String sql = "SELECT * from account WHERE `name` = ? AND `password` = ?";
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
            //给？设值
            statement.setString(1,username);
            statement.setString(2,password);
            //执行sql语句
            rs = statement.executeQuery();

            if(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("name"));
                return user;
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
        return null;
    }

    /**
     * @descripe 取得用户列表
     * @return null/list
     */
    public static List<User> getAllUser(){

        String sql = "SELECT * from account";
        List<User> list = new ArrayList<User>();
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
            //执行sql语句
            rs = statement.executeQuery();
            while(rs.next()){
                User user = new User();

                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("name"));

                list.add(user);
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

    /**
     * 根据id获取用户
     * @param uid
     * @return
     */
    public static User getOneById(int uid){

        String sql = "SELECT * from account WHERE `id` = ?";
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
            //给？设值
            statement.setInt(1,uid);
            //执行sql语句
            rs = statement.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("name"));
                return user;
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
        return null;
    }

    /**
     * 模糊搜索
     * @param some_name
     * @return
     */
    public static List<User> getAllByName(String some_name){

        String sql = "SELECT * from account where `name` like '%" + some_name + "%' ";

        List<User> list = new ArrayList<User>();
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
            //执行sql语句
            rs = statement.executeQuery();

            System.out.println(sql);
            while(rs.next()){
                User user = new User();

                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("name"));

                list.add(user);
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
