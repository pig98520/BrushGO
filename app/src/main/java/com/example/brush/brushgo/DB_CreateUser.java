package com.example.brush.brushgo;

/**
 * Created by pig98520 on 2017/6/17.
 */

public class DB_CreateUser {
    private String user;
    private int time;
    private int reminder;

    public  DB_CreateUser(){

    }
    public DB_CreateUser(String user, int time, int reminder) {
        this.user = user;
        this.time = time;
        this.reminder = reminder;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getReminder() {
        return reminder;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }


}

