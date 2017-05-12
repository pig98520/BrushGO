package com.example.brush.brushgo;

/**
 * Created by swlab on 2017/5/11.
 */
public class DB_Setting {
    private String user;
    private int time;
    private int remider;

    public DB_Setting(String user, int time, int remider) {
        this.user = user;
        this.time = time;
        this.remider = remider;
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

    public int getRemider() {
        return remider;
    }

    public void setRemider(int remider) {
        this.remider = remider;
    }
}
