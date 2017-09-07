package com.example.brush.brushgo;

/**
 * Created by pig98520 on 2017/5/18.
 */

public class DB_Record {
    private String user;
    private String time;
    private String click_confirm;

    public DB_Record(String user, String time,String click_confirm) {
        this.user = user;
        this.time = time;
        this.click_confirm=click_confirm;
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

    public String getClick_confirm() {
        return click_confirm;
    }

    public void setClick_confirm(String click_confirm) {
        this.click_confirm = click_confirm;
    }
}
