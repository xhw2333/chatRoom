package com.service;

import com.dao.GroupDao;
import com.dao.MemberDao;
import com.model.Group;
import com.model.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于群的业务
 */
public class GroupService {
    private GroupDao groupDao = new GroupDao();

    public static void main(String[] args) {
        GroupService g = new GroupService();
//        System.out.println(g.getMyGroup(1));
        int arr[] = {1,2,4};
        g.setGroup("test4",1,arr);
    }

    /**
     * 建群
     * @param name
     * @param oid
     * @return
     */
    public boolean addGroup(String name, int oid){
//        判断群是否存在
        if(GroupDao.getIDByNameAndOid(name,oid) != -1){
            return false;
        }
        GroupDao.insert(name,oid);
//        获取群id，用于添加群主本身
        int g_id = GroupDao.getIDByNameAndOid(name,oid);
//        添加成员（群主）
        MemberDao memberDao = new MemberDao();
        memberDao.insert(g_id,oid);
        return true;
    }

    /***
     * @Description: 建群伴随添加成员
     * @param: [name, oid, members_id]
     * @return: boolean
     * @auther:xhw
     * @date: 2021/6/25 2:08
     */
    public boolean setGroup(String name,int oid,int[] members_id){
        try{
//            判断群是否存在
            if(addGroup(name,oid) == false) return false;
            int g_id = GroupDao.getIDByNameAndOid(name,oid);
            System.out.println(g_id);
//            添加群成员
            MemberService memberService = new MemberService();
            memberService.addMoreMember(g_id,members_id);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 删群
     * @param group_id
     * @param oid
     * @return
     */
    public boolean deleteGroup(int group_id, int oid){
        if(GroupDao.getOneById(group_id) != null){
            GroupDao.delete(group_id,oid);
            return true;
        }
        return false;
    }

    /**
     * 获得该用户的群列表
     * @param uid
     * @return
     */
    public List<Group> getMyGroup(int uid){
        List<Group> group = new ArrayList<Group>();
        MemberDao memberDao = new MemberDao();
        List<Member> list = memberDao.getAllById(uid);
        for(int i = 0; i < list.size(); i++){
            group.add(groupDao.getOneById(list.get(i).getGroup_id()));
        }
        return group;
    }
}
