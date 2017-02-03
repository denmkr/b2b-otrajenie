package ru.dm.shop.entity;

/**
 * Created by Denis on 08.02.16.
 */
public class GroupChild {

    String name;
    String id;
    int childsCount;

    public GroupChild(String name, String id) {
        this.name = name;
        this.id = id;
        childsCount = 0;
    }

    public void addChildsCount() {
        childsCount++;
    }

    public void setChildsCount(int childsCount) {
        this.childsCount = childsCount;
    }

    public int getChildsCount() {
        return childsCount;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "\n" + name + " " + childsCount;
    }
}