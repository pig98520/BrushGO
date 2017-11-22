package com.example.brush.brushgo;

import com.firebase.client.Firebase;

/**
 * Created by pig98520 on 2017/11/22.
 */

public class DB_recordTouched {
   private Firebase dbRef;
   private String value;

    public DB_recordTouched(Firebase dbRef, String value) {
        this.dbRef = dbRef;
        this.value = value;
    }

    public Firebase getDbRef() {
        return dbRef;
    }

    public void setDbRef(Firebase dbRef) {
        this.dbRef = dbRef;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void pushValue(){
        dbRef.push().setValue(value);
    }
    public void unpushValue(){
        dbRef.setValue(value);
    }
}
