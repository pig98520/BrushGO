package com.example.brush.brushgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pig98520 on 2017/11/5.
 */

public class Interdental_Activity extends AppCompatActivity {
    private Button[] btn_interdental=new Button[30];
    private boolean[] condition_interdental=new boolean[30];
    private int[] btn_interdental_id={R.id.interdental_1,R.id.interdental_2,R.id.interdental_3,R.id.interdental_4,R.id.interdental_5,
            R.id.interdental_6,R.id.interdental_7,R.id.interdental_8,R.id.interdental_9,R.id.interdental_10,
            R.id.interdental_11,R.id.interdental_12,R.id.interdental_13,R.id.interdental_14,R.id.interdental_15,
            R.id.interdental_16,R.id.interdental_17,R.id.interdental_18,R.id.interdental_19,R.id.interdental_20,
            R.id.interdental_21,R.id.interdental_22,R.id.interdental_23,R.id.interdental_24,R.id.interdental_25,
            R.id.interdental_26,R.id.interdental_27,R.id.interdental_28,R.id.interdental_29,R.id.interdental_30};
    private boolean isValue=false;
    private FirebaseAuth auth;
    private Firebase firebaseRef;
    private Firebase touchedRef;
    private String nowTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.interdental);
        recordTouched();
        processView();
        processControl();
        checkValue();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Interdental_Activity.this,Home_Activity.class));
    }

    private void recordTouched() {
        firebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/");
        auth= FirebaseAuth.getInstance();
        touchedRef =firebaseRef.child("touched").child(auth.getCurrentUser().getUid()).child("home");
        nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        DB_recordTouched touched=new DB_recordTouched(touchedRef,nowTime);
        touched.pushValue();
    }

    private void checkValue() {
        firebaseRef.child("interdental").child(auth.getUid()).child("1").addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    isValue = true;
                    setInterdental();
                }
                else {
                    isValue = false;
                    setInterdental();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void setInterdental() {
        if(isValue){
            for(int i=0;i<btn_interdental.length;i++){
                final int finalI = i;
                firebaseRef.child("interdental").child(auth.getUid()).child(i+1+"").addValueEventListener(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue().equals("g")){
                            btn_interdental[finalI].setBackgroundResource(R.drawable.transparent_mark);
                            condition_interdental[finalI]=true;
                        }
                        else{
                            btn_interdental[finalI].setBackgroundResource(R.drawable.red_mark);
                            condition_interdental[finalI]=false;
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        }
        else{
            for(int i=0;i<btn_interdental.length;i++){
                firebaseRef.child("interdental").child(auth.getUid()).child(i+1+"").setValue("g");
            }
            isValue=true;
            setInterdental();
        }
    }

    private void processView() {
        firebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/");
        auth=FirebaseAuth.getInstance();
        for(int i=0;i<btn_interdental.length;i++)
            btn_interdental[i]=(Button)findViewById(btn_interdental_id[i]);
    }


    private void processControl() {

    }
}
