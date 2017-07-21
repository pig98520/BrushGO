package com.example.brush.brushgo;

/**
 * Created by pig98520 on 2017/7/21.
 */

public class DB_Profile {
    private String name;
    private String date;

    public DB_Profile(){

    }
    public DB_Profile(String name, String date) {
        this.name = name;
        this.date = date;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
