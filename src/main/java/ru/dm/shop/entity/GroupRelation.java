package ru.dm.shop.entity;

/**
 * Created by Denis on 12.05.16.
 */

public class GroupRelation {
    private String groupId;
    private String childGroupId;

    public GroupRelation(String groupId) {
        this.groupId = groupId;
    }

    public GroupRelation() {
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getChildGroupId() {
        return childGroupId;
    }

    public void setChildGroupId(String childGroupId) {
        this.childGroupId = childGroupId;
    }

}
