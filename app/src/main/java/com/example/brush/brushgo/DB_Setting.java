package com.example.brush.brushgo;

/**
 * Created by swlab on 2017/5/11.
 */
public class DB_Setting {
    private String user;
    private int time;
    private int remider;
    private long morning;
    private long evening;
    public DB_Setting(String user, int time, int remider, long morning, long evening) {
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

    public long getMorning() {
        return morning;
    }

    public void setMorning(long morning) {
        this.morning = morning;
    }

    public long getEvening() {
        return evening;
    }

    public void setEvening(long evening) {
        this.evening = evening;
    }
}
