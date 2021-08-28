package com.dao;

import com.model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * member表
 */
public class MemberDao {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/chatroom?characterEncoding=utf-8&serverTimezone=GMT%2B8";
    private static final String UserName = "root";
    private static final String Password = "root";

    /**
     * 插入
     * @param group_id
     * @param member_id
     */
    public static void insert(int group_id,int member_id){

        String sql = "insert into `member` VALUES(null,?,?)";

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
            statement.setInt(2,member_id);
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
     * 删除
     * @param group_id
     * @param member_id
     */
    public static void delete(int group_id,int member_id){

        String sql = "DELETE FROM `member` WHERE group_id = ? AND member_id = ?";

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
            statement.setInt(2,member_id);
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
     * 检查
     * @param group_id
     * @param member_id
     * @return
     */
    public static boolean check(int group_id,int member_id){
        String sql = "select * from `member` WHERE group_id = ? AND member_id = ?";

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
            statement.setInt(1,group_id);
            statement.setInt(2,member_id);
            //执行
            rs = statement.executeQuery();

            if(rs.next()) return true;

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
     * 获取用户加入的群的群id
     * @param member_id
     * @return
     */
    public static List<Member> getAllById(int member_id){

        String sql = "SELECT * from `member` WHERE member_id = ?";
        List<Member> list = new ArrayList<Member>();

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
            statement.setInt(1,member_id);
            //执行
            rs = statement.executeQuery();;

            while(rs.next()){
                Member member = new Member();
                member.setGroup_id(rs.getInt("group_id"));
                member.setMember_id(rs.getInt("member_id"));
                list.add(member);
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
     * 获取群对应的成员
     * @param group_id
     * @return
     */
    public static List<Member> getSomeByGid(int group_id){

        String sql = "SELECT * from `member` WHERE group_id = ?";

        List<Member> list = new ArrayList<Member>();

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
            statement.setInt(1,group_id);
            //执行
            rs = statement.executeQuery();;

            while(rs.next()){
                Member member = new Member();
                member.setGroup_id(rs.getInt("group_id"));
                member.setMember_id(rs.getInt("member_id"));
                list.add(member);
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
