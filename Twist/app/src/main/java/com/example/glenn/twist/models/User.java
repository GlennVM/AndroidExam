package com.example.glenn.twist.models;

/**
 * Created by Glenn on 24/08/2016.
 */
public class User {
    String name;
    int ID;

    public User(String name, int ID) {
        setName(name);
        setID(ID);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}
