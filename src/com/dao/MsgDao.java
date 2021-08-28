package com.dao;

import com.model.Member;
import com.model.Msg;
import com.model.User;

import java.sql.*;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * message表
 */
public class MsgDao {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/chatroom?characterEncoding=utf-8&serverTimezone=GMT%2B8";
    private static final String UserName = "root";
    private static final String Password = "root";

    public static void main(String[] args) {
//        insert(1,2,"你好",0);
//        delete(1);
        System.out.println(getAllById(1,2,0));
//        Date time = new Date();
//        System.out.println(time);
        System.out.println(getMaxId());
    }

    /**
     * 插入数据
     * @param send_id
     * @param accept_id
     * @param content
     * @param target
     */
    public static void insert(int send_id, int accept_id, String content,int target){

        String sql = "insert into `message` VALUES(null,?,?,?,?,?)";
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

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
            statement.setInt(1,send_id);
            statement.setInt(2,accept_id);
            statement.setString(3,content);
            statement.setString(4,sdf.format(date));
            statement.setInt(5,target);

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
     * 删除数据
     * @param id
     */
    public static void delete(int id){

        String sql = "DELETE FROM `message` WHERE id = ?";

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
            statement.setInt(1,id);
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
     * 检车是否存在此消息
     * @param s_id
     * @return
     */
    public static boolean check(int s_id){
        String sql = "SELECT * from message WHERE `send_id` = ?";
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
            statement.setInt(1,s_id);
            //执行sql语句
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
     * 获取私聊记录
     * @param send_id
     * @param accept_id
     * @param target
     * @return
     */
    public static List<Msg> getAllById(int send_id,int accept_id,int target){
        String sql = "SELECT * from `message` WHERE (send_id = ? AND accept_id = ? AND target = ?) OR (send_id = ? AND accept_id = ? AND target = ?)";
        List<Msg> list = new ArrayList<Msg>();

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
            statement.setInt(1,send_id);
            statement.setInt(2,accept_id);
            statement.setInt(3,target);
            statement.setInt(4,accept_id);
            statement.setInt(5,send_id);
            statement.setInt(6,target);
            //执行
            rs = statement.executeQuery();;

            while(rs.next()){
                Msg msg = new Msg();
                msg.setId(rs.getInt("id"));
                msg.setSend_id(rs.getInt("send_id"));
                msg.setAccept_id(rs.getInt("accept_id"));
                msg.setContent(rs.getString("content"));
                msg.setTime(rs.getDate("date"));
                msg.setTarget(rs.getInt("target"));
                list.add(msg);
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
     * 获取群消息记录
     * @param accept_id
     * @param target
     * @return
     */
    public static List<Msg> getGroupMsgById(int accept_id,int target){
        String sql = "SELECT * from `message` WHERE accept_id = ? AND target = ?";
        List<Msg> list = new ArrayList<Msg>();

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
            statement.setInt(1,accept_id);
            statement.setInt(2,target);
            //执行
            rs = statement.executeQuery();;

            while(rs.next()){
                Msg msg = new Msg();
                msg.setId(rs.getInt("id"));
                msg.setSend_id(rs.getInt("send_id"));
                msg.setAccept_id(rs.getInt("accept_id"));
                msg.setContent(rs.getString("content"));
                msg.setTime(rs.getDate("date"));
                msg.setTarget(rs.getInt("target"));
                list.add(msg);
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
     * 获取表内id最大项
     * @return
     */
    public static int getMaxId(){
        String sql = "SELECT MAX(id) id from message";
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

            if(rs.next()){
                return rs.getInt("id");
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
        return 0;
    }
}
