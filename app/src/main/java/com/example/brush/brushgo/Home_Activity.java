package com.example.brush.brushgo;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
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
    private TextView timer;
    private int defaultTime;
    private int timersec;
    private int currentTime;
    private int i;
    private ImageView clockArray[]=new ImageView[12];
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
            defaultTime=bundle.getInt("time")*60;
        else
            defaultTime=180;
        timer.setText(defaultTime+"");
    }

    private void processView() {
        navigateionView=(NavigationView) findViewById(R.id.nav_home);
        navigateionView.setNavigationItemSelectedListener(Home_Activity.this);
        menu=(Button) findViewById(R.id.btn_menu);
        play=(Button) findViewById(R.id.btn_play);
        stop=(Button) findViewById(R.id.btn_stop);
        timer=(TextView)findViewById(R.id.txt_timer);
        music= MediaPlayer.create(Home_Activity.this,R.raw.eine_kleine_nachtmusik);
        drawer=(DrawerLayout)findViewById(R.id.drawerLayout);
        clockArray[0]=(ImageView)findViewById(R.id.imageView_1);
        clockArray[1]=(ImageView)findViewById(R.id.imageView_2);
        clockArray[2]=(ImageView)findViewById(R.id.imageView_3);
        clockArray[3]=(ImageView)findViewById(R.id.imageView_4);
        clockArray[4]=(ImageView)findViewById(R.id.imageView_5);
        clockArray[5]=(ImageView)findViewById(R.id.imageView_6);
        clockArray[6]=(ImageView)findViewById(R.id.imageView_7);
        clockArray[7]=(ImageView)findViewById(R.id.imageView_8);
        clockArray[8]=(ImageView)findViewById(R.id.imageView_9);
        clockArray[9]=(ImageView)findViewById(R.id.imageView_10);
        clockArray[10]=(ImageView)findViewById(R.id.imageView_11);
        clockArray[11]=(ImageView)findViewById(R.id.imageView_12);
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
                music= MediaPlayer.create(Home_Activity.this,R.raw.eine_kleine_nachtmusik);
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
                clockShow();
            }
            @Override
            public void onFinish() {
                play.setBackgroundResource(R.mipmap.ic_play_circle_outline_black_24dp);
                music.stop();
                music= MediaPlayer.create(Home_Activity.this,R.raw.eine_kleine_nachtmusik);
                timerStop();
                clockReset();
                timerFinish();
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
        clockReset();
    }

    private void timerFinish() {
        AlertDialog.Builder finishDialog=new AlertDialog.Builder(this);
        finishDialog.setTitle("時間到了~");
        finishDialog.setMessage("恭喜你刷好牙了~");
        DialogInterface.OnClickListener confirmClick =new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };
        finishDialog.setNeutralButton("確定",confirmClick);
        finishDialog.show();
    }


    private void clockShow() {
        currentTime = (Integer.parseInt(timer.getText().toString().trim())) % 12;
        if(clockArray[currentTime].getVisibility()==View.INVISIBLE){
            clockArray[currentTime].setVisibility(View.VISIBLE);
        }
        else if(clockArray[currentTime].getVisibility()==View.VISIBLE){
            clockArray[currentTime].setVisibility(View.INVISIBLE);
        }
    }

    private void clockReset() {
        for(i=0;i<12;i++)
        {
            clockArray[i].setVisibility(View.INVISIBLE);
        }
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