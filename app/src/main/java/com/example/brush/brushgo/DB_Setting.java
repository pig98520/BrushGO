package com.example.brush.brushgo;

/**
 * Created by swlab on 2017/5/11.
 */
public class DB_Setting {
    private String user;
    private int time;
    private int remider;
    private String morning;
    private String evening;

    public DB_Setting(){

    }
    public DB_Setting(String user, int time, int remider, String morning, String evening) {
        this.user = user;
        this.time = time;
        this.remider = remider;
        this.morning = morning;
        this.evening = evening;
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

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getEvening() {
        return evening;
    }

    public void setEvening(String evening) {
        this.evening = evening;
    }
}
