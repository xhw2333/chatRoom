package com.service;

import com.dao.GroupDao;
import com.dao.MemberDao;
import com.dao.MsgDao;
import com.dao.UserDao;
import com.model.Group;
import com.model.Member;
import com.model.Msg;
import com.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天主要业务
 */
public class ChatService {
    private MemberDao memberDao = new MemberDao();
    private UserDao userDao = new UserDao();
    private GroupDao groupDao = new GroupDao();
    private MsgDao msgDao = new MsgDao();

    public static void main(String[] args) {
        ChatService c = new ChatService();
        System.out.println(c.getGroupMember(1));
    }

    /**
     * 转换为需要发送的正确格式
     * @param msg
     * @return
     */
    public Msg getMsg(Msg msg){
        User send_man = userDao.getOneById(msg.getSend_id());
        msg.setSend_name(send_man.getUsername());
//            用户名
        if(msg.getTarget() == 0){
            User accept_man = userDao.getOneById(msg.getAccept_id());
            if(accept_man != null) msg.setAccept_name(accept_man.getUsername());
        }
//        群名
        else {
            Group group = groupDao.getOneById(msg.getAccept_id());
            if(group != null) msg.setAccept_name(group.getGroup_name());
        }
        int id = msgDao.getMaxId()+1;
        msg.setId(id);
        return msg;
    }

    /**
     * 获取成员信息，用于群发
     * @param group_id
     * @return
     */
    public List<User> getGroupMember(int group_id){
        List<Member> list = memberDao.getSomeByGid(group_id);
        List<User> member = new ArrayList<User>();
        for(int i = 0; i < list.size(); i++){
            int m_id = list.get(i).getMember_id();
            member.add(UserDao.getOneById(m_id));
        }
        return member;
    }
}
