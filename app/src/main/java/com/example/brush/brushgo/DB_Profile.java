package com.example.brush.brushgo;

/**
 * Created by pig98520 on 2017/7/21.
 */

public class DB_Profile {
    private String name;
    private String install_date;
    private String back_date;
    private String first_pcr;
    private String second_pcr;

    public DB_Profile(){

    }
    public DB_Profile(String name, String install_date, String back_date, String first_pcr, String second_pcr) {
        this.name = name;
        this.install_date = install_date;
        this.back_date = back_date;
        this.first_pcr = first_pcr;
        this.second_pcr = second_pcr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstall_date() {
        return install_date;
    }

    public void setInstall_date(String install_date) {
        this.install_date = install_date;
    }

    public String getBack_date() {
        return back_date;
    }

    public void setBack_date(String back_date) {
        this.back_date = back_date;
    }

    public String getFirst_pcr() {
        return first_pcr;
    }

    public void setFirst_pcr(String first_pcr) {
        this.first_pcr = first_pcr;
    }

    public String getSecond_pcr() {
        return second_pcr;
    }

    public void setSecond_pcr(String second_pcr) {
        this.second_pcr = second_pcr;
    }
}
