package com.example.brush.brushgo;

/**
 * Created by pig98520 on 2017/8/25.
 */

public class DB_Image {
    private String tutorial;

    public DB_Image(){

    }

    public DB_Image(String tutorial) {
        this.tutorial = tutorial;
    }

    public String getTutorial() {
        return tutorial;
    }

    public void setTutorial(String tutorial) {
        this.tutorial = tutorial;
    }
}
