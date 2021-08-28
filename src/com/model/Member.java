package com.model;

/**
 * 成员模型
 */
public class Member {
    private int member_id;
    private int group_id;
    private String member_name;

    public Member(int member_id, int group_id, String member_name) {
        this.member_id = member_id;
        this.group_id = group_id;
        this.member_name = member_name;
    }

    public Member() {
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    @Override
    public String toString() {
        return "{" +
                "member_id:" + member_id +
                ", group_id:" + group_id +
                ", member_name:'" + member_name + '\'' +
                '}';
    }
}
