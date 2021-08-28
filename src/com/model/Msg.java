package com.model;

import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 消息模型
 */
public class Msg {
    private int id;
    private int send_id;
    private int accept_id;
    private String content;
    private String send_name;
    private String accept_name;
    private int target; // 对象是群还是用户, 0 - 用户 | 1 - 群组
//    private Date time;
    private long time;


    public Msg(int id, int send_id, int accept_id, String content, String send_name, String accept_name,int target) {
        this.id = id;
        this.send_id = send_id;
        this.accept_id = accept_id;
        this.content = content;
        this.send_name = send_name;
        this.accept_name = accept_name;
        this.target = target;
        long time = new Date().getTime();
        this.time = time;
    }

    public Msg(int send_id,int accept_id, String content,int target){
        this.send_id = send_id;
        this.accept_id = accept_id;
        this.content = content;
        this.target = target;
        long time = new Date().getTime();
        this.time = time;
    }

    public Msg() {
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getAccept_name() {
        return accept_name;
    }

    public void setAccept_name(String accept_name) {
        this.accept_name = accept_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSend_id() {
        return send_id;
    }

    public void setSend_id(int send_id) {
        this.send_id = send_id;
    }

    public int getAccept_id() {
        return accept_id;
    }

    public void setAccept_id(int accept_id) {
        this.accept_id = accept_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime() {
        long time = new Date().getTime();
        this.time = time;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getTarget() {
        return target;
    }

    public void setTime(Date time) {
        this.time = time.getTime();
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");

        return "{" +
                "id:" + id +
                ", send_id:" + send_id +
                ", accept_id:" + accept_id +
                ", content:\"" + content + "\"" +
                ", send_name:\"" + send_name + '\"' +
                ", accept_name:\"" + accept_name + '\"' +
                ", target:" + target +
                ", time:" + sdf.format(time) +
                '}';
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");

        Date date=new Date();
        long time = date.getTime();

        String dateStr=sdf.format(time);

        System.out.println(dateStr);
        Msg msg = new Msg(1,2,"nihao",1);
//        String message = "{\"send_id\":1,\"accept_id\":1,\"content\":\"\",\"target\":0}";
//        JSONObject json = JSONObject.fromObject(message);
//        System.out.println(json);
//        Msg msg = (Msg) JSONObject.toBean(json,Msg.class);
//        msg.setTime();
//        System.out.println(msg);
    }
}
