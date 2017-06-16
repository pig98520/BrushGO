package com.example.brush.brushgo;

/**
 * Created by swlab on 2017/5/11.
 */
public class DB_Setting {
    private String user;
    private int time;
    private int reminder;
    private String morning;
    private String evening;

    public DB_Setting() {

    }

    public DB_Setting(String user, int time, int reminder, String morning, String evening) {
        this.user = user;
        this.time = time;
        this.reminder = reminder;
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

    public int getReminder() {
        return reminder;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
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