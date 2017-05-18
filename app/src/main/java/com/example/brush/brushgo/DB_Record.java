package com.example.brush.brushgo;

/**
 * Created by pig98520 on 2017/5/18.
 */

public class DB_Record {
    private String user;
    private String time;

    public DB_Record(String user, String time) {
        this.user = user;
        this.time = time;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
