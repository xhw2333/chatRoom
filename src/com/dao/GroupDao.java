package com.dao;

import com.model.Group;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * group表
 */
public class GroupDao {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/chatroom?characterEncoding=utf-8&serverTimezone=GMT%2B8";
    private static final String UserName = "root";
    private static final String Password = "root";

    public static void main(String[] args) {
//        insert("test",1);
        Group group = getOneById(1);
        System.out.println(group + " " + getIDByNameAndOid("test",1));
    }

    /**
     * 插入群
     * @param name
     * @param owner_id
     */
    public static void insert(String name,int owner_id){

        String sql = "insert into `group` VALUES(null,?,?)";

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
            statement.setString(1,name);
            statement.setInt(2,owner_id);
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
     * 删除群
     * @param group_id
     * @param owner_id
     */
    public static void delete(int group_id,int owner_id){

        String sql = "DELETE FROM `group` WHERE group_id = ? AND owner_id = ?";

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
            statement.setInt(1,group_id);
            statement.setInt(2,owner_id);
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
     * 通过群名和群主id获取群id
     * @param name
     * @param oid
     * @return
     */
    public static int getIDByNameAndOid(String name,int oid){
        String sql = "SELECT * from `group` WHERE group_name = ? AND owner_id = ?";
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
            statement.setString(1,name);
            statement.setInt(2,oid);
            //执行sql语句
            rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt("group_id");
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
        return -1;
    }

    /**
     * 通过id找群的基本信息
     * @param group_id
     * @return
     */
    public static Group getOneById(int group_id){
        String sql = "SELECT * from `group` WHERE group_id = ?";
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
            statement.setInt(1,group_id);
            //执行sql语句
            rs = statement.executeQuery();
            if(rs.next()){
                Group group = new Group(rs.getInt("group_id"),rs.getString("group_name"),rs.getInt("owner_id"));
                return group;
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

}
