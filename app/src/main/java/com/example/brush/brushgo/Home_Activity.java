package com.example.brush.brushgo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.brush.brushgo.R.id.Home;
import static com.example.brush.brushgo.R.id.drawerLayout;

/**
 * Created by swlab on 2017/5/5.
 */

public class Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ConstraintLayout layout;
    private Button menu;
    private Button play;
    private Button stop;
    private Button change_music;
    private Button change_color;
    private TextView timer;
    private TextView countdown;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private int defaultTime;
    private int timersec;
    private int currentTime;
    private int backgroundColor;
    private SimpleDateFormat dtFormat;
    private String nowTime;
    private Date date;
    private FirebaseAuth auth;
    private Firebase readFirebaseRef;
    private Firebase musicFirebaseRef;
    private Firebase recordFirebaseRef;
    private Firebase userFirebaseRef;
    private ImageView clockArray[]=new ImageView[60];
    private int colorArray[]=new int[4];
    private CountDownTimer countdownTimer;
    private int timeArray[]=new int[]{120,180,240};
    private MediaPlayer music;
    private String musicUrl=" ";
    private int musicIndex=(int) (Math.random()*10+1);
    private DrawerLayout drawer;
    private NavigationView navigateionView;
    private boolean isdoubleClick=false;

    @Override
    public void onBackPressed() {
        if(!isdoubleClick)
        {
            Toast.makeText(Home_Activity.this,"雙擊以退出",Toast.LENGTH_LONG).show();
            isdoubleClick=true;
        }
        else
        {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                isdoubleClick=false;
            }
        }.start();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Firebase.setAndroidContext(this);
        processView();
        setValue();
        setMusic();
        processControl();
    }

    private void setMusic() {
        if(musicIndex<10)
            musicIndex+=1;
        else
            musicIndex=1;
        music=new MediaPlayer(); //建立一個media player
        musicFirebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/music/"+musicIndex); //取得firebase網址 用亂數取得節點網址

        progressDialog.setTitle("Loading");
        progressDialog.setMessage("載入音樂中,請稍後");
        progressDialog.setIcon(R.drawable.loading_24);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();

        musicFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                musicUrl=dataSnapshot.getValue(String.class); //取得節點內的資料
                try {
                    music.setDataSource(musicUrl); //設定media的路徑
                    music.prepare();
                    progressDialog.dismiss();
                } catch (IOException e) {
                    Toast.makeText(Home_Activity.this,"讀取不到音樂",Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                    progressDialog.dismiss();
            }
        });
    }

    private void setValue() {
        readFirebaseRef = new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/time");
        readFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                defaultTime=dataSnapshot.getValue(int.class);
                if(defaultTime==0)
                {
                    defaultTime=timeArray[(int) (Math.random()*3)];
                    updateUser();
                }
                timer.setText(defaultTime+"");
                if(defaultTime%60<10)
                    countdown.setText("0"+defaultTime/60+"：0"+defaultTime%60);
                else
                    countdown.setText("0"+defaultTime/60+"："+defaultTime%60);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void updateUser() {
        userFirebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid());
        DB_CreateUser data = new DB_CreateUser(auth.getCurrentUser().getEmail(),defaultTime,3);
        userFirebaseRef.setValue(data);
        Toast.makeText(Home_Activity.this,  "資料已儲存", Toast.LENGTH_SHORT).show();
    }


    private void processView() {
        layout=(ConstraintLayout)findViewById(R.id.constraintLayout);
        navigateionView=(NavigationView) findViewById(R.id.nav_home);
        navigateionView.setNavigationItemSelectedListener(Home_Activity.this);
        drawer=(DrawerLayout)findViewById(drawerLayout);
        menu=(Button) findViewById(R.id.btn_menu);
        play=(Button) findViewById(R.id.btn_play);
        stop=(Button) findViewById(R.id.btn_stop);
        change_music=(Button)findViewById(R.id.btn_changecmusic);
        change_color=(Button)findViewById(R.id.btn_changecolor);
        timer=(TextView)findViewById(R.id.txt_timer);
        countdown=(TextView)findViewById(R.id.txt_countdown);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressDialog = new ProgressDialog(this,R.style.DialogCustom);
        auth= FirebaseAuth.getInstance();
        clockArray[0]=(ImageView)findViewById(R.id.imageView_12);
        clockArray[5]=(ImageView)findViewById(R.id.imageView_1);
        clockArray[10]=(ImageView)findViewById(R.id.imageView_2);
        clockArray[15]=(ImageView)findViewById(R.id.imageView_3);
        clockArray[20]=(ImageView)findViewById(R.id.imageView_4);
        clockArray[25]=(ImageView)findViewById(R.id.imageView_5);
        clockArray[30]=(ImageView)findViewById(R.id.imageView_6);
        clockArray[35]=(ImageView)findViewById(R.id.imageView_7);
        clockArray[40]=(ImageView)findViewById(R.id.imageView_8);
        clockArray[45]=(ImageView)findViewById(R.id.imageView_9);
        clockArray[50]=(ImageView)findViewById(R.id.imageView_10);
        clockArray[55]=(ImageView)findViewById(R.id.imageView_11);
        colorArray[0]=R.color.background;
        colorArray[1]=R.color.pink;
        colorArray[2]=R.color.yellow;
        colorArray[3]=R.color.purple;
    }

    private void processControl() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
                if(music.isPlaying()){
                    play.setBackgroundResource(R.drawable.speaker_512);
                    music.pause();
                    timerPause();
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(music.isPlaying()){
                    play.setBackgroundResource(R.drawable.speaker_512);
                    music.pause();
                    timerPause();
                }
                else {
                    play.setBackgroundResource(R.drawable.non_speaker_512);
                    music.start();
                    timerStart();
                }
            }

        });
        stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                play.setBackgroundResource(R.drawable.speaker_512);
                timerStop();
            }
        });
        change_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music.stop();
                music.release();
                timerPause();
                play.setBackgroundResource(R.drawable.speaker_512);
                setMusic();
            }
        });
        change_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundColor = ((ColorDrawable)layout.getBackground()).getColor();
                for(int n=0;n<(colorArray.length-1);n++) {
                    if (backgroundColor == getResources().getColor(colorArray[n]))
                        layout.setBackgroundResource(colorArray[n+1]);
                }
                if(backgroundColor == getResources().getColor(colorArray[colorArray.length-1]))
                    layout.setBackgroundResource(colorArray[0]);
            }
        });
    }
    private void timerStart() {
        timersec= Integer.parseInt(timer.getText().toString().trim());
        countdownTimer = new CountDownTimer(timersec * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(millisUntilFinished/1000+"");
                if(millisUntilFinished/1000%60<10)
                    countdown.setText("0"+millisUntilFinished/1000/60+"：0"+millisUntilFinished/1000%60);
                else
                    countdown.setText("0"+millisUntilFinished/1000/60+"："+millisUntilFinished/1000%60);
                clcokStart();
                setProgressbar();
            }

            @Override
            public void onFinish() {
                play.setBackgroundResource(R.drawable.speaker_512);
                timerStop();
                finishDialog();
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
        music.stop();
        setMusic();
        clockStop();
        setProgressbar();
    }

    private void setProgressbar() {
        currentTime = (Integer.parseInt(timer.getText().toString().trim()));
        progressBar.setMax(defaultTime);
        progressBar.setProgress(defaultTime-currentTime);
    }

    private void finishDialog() {
        AlertDialog.Builder finishDialog=new AlertDialog.Builder(this,R.style.DialogCustom);
        finishDialog.setTitle("時間到了~");
        finishDialog.setMessage("恭喜你刷好牙了，請按確認以紀錄。");
        DialogInterface.OnClickListener confirmClick =new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recordData();
            }
        };
        finishDialog.setNeutralButton("確定",confirmClick);
        finishDialog.show();
    }

    private void recordData() {
        dtFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        date = new Date();
        nowTime = dtFormat.format(date);
        recordFirebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/record").child(auth.getCurrentUser().getUid());
        DB_Record data = new DB_Record(auth.getCurrentUser().getEmail(),nowTime);
        recordFirebaseRef.push().setValue(data);
        Toast.makeText(Home_Activity.this,  "使用紀錄已更新", Toast.LENGTH_SHORT).show();
    }

    private void clcokStart() {
        currentTime = (Integer.parseInt(timer.getText().toString().trim()));
        if(currentTime%60%5==0) {
            if (clockArray[currentTime % 60].getVisibility() == View.INVISIBLE) {
                clockArray[currentTime % 60].setVisibility(View.VISIBLE);
            } else if (clockArray[currentTime % 60].getVisibility() == View.VISIBLE) {
                clockArray[currentTime % 60].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void clockStop() {
        for(int i=0;i<60;i=i+5)
        {
            clockArray[i].setVisibility(View.INVISIBLE);
        }
        if(defaultTime%60<10)
            countdown.setText("0"+defaultTime/60+"：0"+defaultTime%60);
        else
            countdown.setText("0"+defaultTime/60+"："+defaultTime%60);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id== Home)
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
        else if(id==R.id.Logout)
        {
            AlertDialog.Builder logoutDialog=new AlertDialog.Builder(this);
            logoutDialog.setTitle("確定要登出?");
            logoutDialog.setMessage("登出後即無法使用部分提醒功能。");
            DialogInterface.OnClickListener confirmClick =new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    auth.signOut();
                    Intent intent=new Intent();
                    intent.setClass(Home_Activity.this,MainActivity.class);
                    startActivity(intent);
                }
            };
            DialogInterface.OnClickListener cancelClick =new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            };
            logoutDialog.setNeutralButton("確定",confirmClick);
            logoutDialog.setNegativeButton("取消",cancelClick);
            logoutDialog.show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        music.release();
    }
}