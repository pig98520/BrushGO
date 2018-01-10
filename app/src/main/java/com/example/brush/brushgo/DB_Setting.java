package com.example.brush.brushgo;

/**
 * Created by swlab on 2017/5/11.
 */
public class DB_Setting {
    private String user;
    private int time;
    private int reminder;
    private String alarm_a;
    private String alarm_b;
    private String alarm_c;
    private String alarm_d;
    private String alarm_e;

    public DB_Setting() {

    }

    public DB_Setting(String user, int time, int reminder, String alarm_a, String alarm_b, String alarm_c, String alarm_d,String alarm_e) {
        this.user = user;
        this.time = time;
        this.reminder = reminder;
        this.alarm_a = alarm_a;
        this.alarm_b = alarm_b;
        this.alarm_c = alarm_c;
        this.alarm_d = alarm_d;
        this.alarm_e=alarm_e;
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

    public String getAlarm_a() {
        return alarm_a;
    }

    public void setAlarm_a(String alarm_a) {
        this.alarm_a = alarm_a;
    }

    public String getAlarm_b() {
        return alarm_b;
    }

    public void setAlarm_b(String alarm_b) {
        this.alarm_b = alarm_b;
    }

    public String getAlarm_c() {
        return alarm_c;
    }

    public void setAlarm_c(String alarm_c) {
        this.alarm_c = alarm_c;
    }

    public String getAlarm_d() {
        return alarm_d;
    }

    public void setAlarm_d(String alarm_d) {
        this.alarm_d = alarm_d;
    }

    public String getAlarm_e() {
        return alarm_e;
    }

    public void setAlarm_e(String alarm_e) {
        this.alarm_e = alarm_e;
    }
}