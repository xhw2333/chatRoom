package com.model;

/**
 * 群模型
 */
public class Group {
    private int group_id;
    private String group_name;
    private int owner_id;

    public Group(int group_id, String group_name, int owner_id) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.owner_id = owner_id;
    }

    public Group() {
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    @Override
    public String toString() {
        return "{" +
                "group_id:" + group_id +
                ", group_name:'" + group_name + '\'' +
                ", owner_id:" + owner_id +
                '}';
    }
}
