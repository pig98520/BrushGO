package com.example.brush.brushgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.firebase.client.Firebase;

/**
 * Created by pig98520 on 2017/11/5.
 */

public class Interdental_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.interdental);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Interdental_Activity.this,Home_Activity.class));
    }
}
