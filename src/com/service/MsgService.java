package com.service;

import com.dao.GroupDao;
import com.dao.MsgDao;
import com.dao.UserDao;
import com.model.Msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 关于消息的业务
 */
public class MsgService {
    private MsgDao msgDao = new MsgDao();
    private UserDao userDao = new UserDao();
    private GroupDao groupDao = new GroupDao();

    public static void main(String[] args) {
        MsgService s = new MsgService();
        System.out.println(s.getMyMsg(1,2,0));
        System.out.println(s.getMyMsg(1,7,1));
    }

    /**
     * 添加消息
     * @param send_id
     * @param accept_id
     * @param content
     * @param target 标识 0 -私聊 | 1 - 群聊
     */
    public void addMsg(int send_id,int accept_id,String content,int target){
        if(send_id >= 0 && accept_id >= 0) msgDao.insert(send_id,accept_id,content,target);
    }

    /**
     * 删除消息
     * @param id
     */
    public void deleteMsg(int id){
        msgDao.delete(id);
    }

    /**
     * 获取关于我的消息
     * @param send_id
     * @param accept_id
     * @param target
     * @return
     */
    public List<Msg> getMyMsg(int send_id,int accept_id,int target){
//        私聊消息
        List<Msg> list = new ArrayList<Msg>();
        if(target == 0){
            list = msgDao.getAllById(send_id,accept_id,target);

            for(int i = 0; i < list.size(); i++){
                int s_id = list.get(i).getSend_id();
                int a_id = list.get(i).getAccept_id();
                list.get(i).setSend_name(userDao.getOneById(s_id).getUsername());
                list.get(i).setAccept_name(userDao.getOneById(a_id).getUsername());
                list.get(i).setTarget(target);
            }
        }
//        群聊消息
        else {
            list = msgDao.getGroupMsgById(accept_id,target);

            for(int i = 0; i < list.size(); i++){
                int s_id = list.get(i).getSend_id();
                int a_id = list.get(i).getAccept_id();
                list.get(i).setSend_name(userDao.getOneById(s_id).getUsername());
                list.get(i).setAccept_name(groupDao.getOneById(a_id).getGroup_name());
                list.get(i).setTarget(target);
            }
        }
       return list;
    }
}
