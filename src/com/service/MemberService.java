package com.service;

import com.dao.MemberDao;
import com.dao.UserDao;
import com.model.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于群成员的业务
 */
public class MemberService {
    private MemberDao memberDao = new MemberDao();
    private UserDao userDao = new UserDao();

    public static void main(String[] args) {
        MemberService m = new MemberService();
        System.out.println(m.getGroupMember(1));
        m.addMember(7,1);
    }

    /**
     * 添加一个群成员
     * @param group_id
     * @param member_id
     * @return
     */
    public boolean addMember(int group_id,int member_id){
        if(memberDao.check(group_id,member_id)){
            return false;
        }
        memberDao.insert(group_id,member_id);
        return true;
    }

    /**
     * 删除群成员
     * @param group_id
     * @param member_id
     * @return
     */
    public boolean deleteMember(int group_id,int member_id){
        if(memberDao.check(group_id,member_id)){
            memberDao.delete(group_id,member_id);
            return true;
        }
        return false;
    }

    /**
     * 添加多个群成员
     * @param group_id
     * @param members_id
     * @return 无法添加的成员索引
     */
    public int addMoreMember(int group_id,int[] members_id){
        int index = 0;
        for(int i = 0; i < members_id.length; i++){
            if(addMember(group_id,members_id[i]) == false){
                index = i + 1;
            }
        }
        return index;
    }

    /**
     * 获取群成员消息
     * @param group_id
     * @return
     */
    public List<Member> getGroupMember(int group_id){
        List<Member> list = memberDao.getSomeByGid(group_id);

        for(int i = 0; i < list.size(); i++){
            int id = list.get(i).getMember_id();
            String name = UserDao.getOneById(id).getUsername();
            list.get(i).setMember_name(name);
        }
        return list;
    }
}
