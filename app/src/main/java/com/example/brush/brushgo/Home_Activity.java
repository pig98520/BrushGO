package com.example.brush.brushgo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by swlab on 2017/5/5.
 */

public class Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button menu;
    private Button play;
    private Button stop;
    private Button next;
    private Button previous;
    private TextView timer;
    private int defaultTime;
    private int timersec;
    private ImageView image_1;
    private ImageView image_2;
    private ImageView image_3;
    private ImageView image_4;
    private ImageView image_5;
    private CountDownTimer countdownTimer;
    private MediaPlayer music;
    private DrawerLayout drawer;
    private NavigationView navigateionView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        processView();
        setValue();
        processControl();
    }

    private void setValue() {
        Bundle bundle=this.getIntent().getExtras();
        if(bundle!=null)
            defaultTime=bundle.getInt("time")*60+1;
        else
            defaultTime=180+1;
        timer.setText(defaultTime+"");
    }

    private void processView() {
        navigateionView=(NavigationView) findViewById(R.id.nav_home);
        navigateionView.setNavigationItemSelectedListener(Home_Activity.this);
        menu=(Button) findViewById(R.id.btn_menu);
        play=(Button) findViewById(R.id.btn_play);
        stop=(Button) findViewById(R.id.btn_stop);
        next=(Button) findViewById(R.id.btn_next);
        previous=(Button) findViewById(R.id.btn_previous);
        timer=(TextView)findViewById(R.id.txt_timer);
        image_1=(ImageView)findViewById(R.id.image_1);
        image_2=(ImageView)findViewById(R.id.image_2);
        image_3=(ImageView)findViewById(R.id.image_3);
        image_4=(ImageView)findViewById(R.id.image_4);
        image_5=(ImageView)findViewById(R.id.image_5);
        music= MediaPlayer.create(Home_Activity.this,R.raw.eine_kleine_nachtmusik);
        drawer=(DrawerLayout)findViewById(R.id.drawerLayout);
    }
    private void processControl() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(music.isPlaying()){
                    play.setBackgroundResource(R.mipmap.ic_play_circle_outline_black_24dp);
                    music.pause();
                    timerPause();
                }
                else {
                    play.setBackgroundResource(R.mipmap.ic_pause_circle_filled_black_24dp);
                    music.start();
                    timerStart();
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                play.setBackgroundResource(R.mipmap.ic_play_circle_outline_black_24dp);
                music.stop();
                music= MediaPlayer.create(Home_Activity.this,R.raw.the_place_inside);
                timerStop();
            }
        });

    }
    private void timerStart() {
        timersec= Integer.parseInt(timer.getText().toString().trim());
        countdownTimer = new CountDownTimer(timersec * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(millisUntilFinished/1000+"");
               if(Integer.parseInt(timer.getText().toString())==defaultTime-60)
                    image_1.setVisibility(View.VISIBLE);
                else if (Integer.parseInt(timer.getText().toString())==defaultTime-120)
                    image_2.setVisibility(View.VISIBLE);
                else if (Integer.parseInt(timer.getText().toString())==defaultTime-180)
                    image_3.setVisibility(View.VISIBLE);
                else if (Integer.parseInt(timer.getText().toString())==defaultTime-240)
                    image_4.setVisibility(View.VISIBLE);
                else if (Integer.parseInt(timer.getText().toString())==defaultTime-300)
                    image_5.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                timer.setText("時間到了唷^^");
                timer.setVisibility(View.VISIBLE);
                music.stop();
            }
        };
        countdownTimer.start();
    }
    private void timerPause() {
        countdownTimer.cancel();
    }
    private void timerStop() {
        countdownTimer.cancel();
        timer.setText(defaultTime+"");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.Home)
        {
            Intent intent=new Intent();
            intent.setClass(this,Home_Activity.class);
            startActivity(intent);
        }
        else if(id==R.id.Video)
        {
            Intent intent=new Intent();
            intent.setClass(this,Video_Activity.class);
            startActivity(intent);
        }
        else if(id==R.id.Information)
        {
            Intent intent=new Intent();
            intent.setClass(this,Information_Activity.class);
            startActivity(intent);
        }
        else if(id==R.id.Question)
        {
            Intent intent=new Intent();
            intent.setClass(this,Question_Activity.class);
            startActivity(intent);
        }
        else if(id==R.id.Setting)
        {
            Intent intent=new Intent();
            intent.setClass(this,Setting_Activity.class);
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}