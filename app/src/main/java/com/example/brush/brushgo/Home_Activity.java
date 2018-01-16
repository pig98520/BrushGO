package com.example.brush.brushgo;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.brush.brushgo.R.id.arrow_1;
import static com.example.brush.brushgo.R.id.arrow_10;
import static com.example.brush.brushgo.R.id.arrow_11;
import static com.example.brush.brushgo.R.id.arrow_12;
import static com.example.brush.brushgo.R.id.arrow_13;
import static com.example.brush.brushgo.R.id.arrow_14;
import static com.example.brush.brushgo.R.id.arrow_15;
import static com.example.brush.brushgo.R.id.arrow_16;
import static com.example.brush.brushgo.R.id.arrow_17;
import static com.example.brush.brushgo.R.id.arrow_18;
import static com.example.brush.brushgo.R.id.arrow_19;
import static com.example.brush.brushgo.R.id.arrow_2;
import static com.example.brush.brushgo.R.id.arrow_20;
import static com.example.brush.brushgo.R.id.arrow_21;
import static com.example.brush.brushgo.R.id.arrow_22;
import static com.example.brush.brushgo.R.id.arrow_23;
import static com.example.brush.brushgo.R.id.arrow_24;
import static com.example.brush.brushgo.R.id.arrow_3;
import static com.example.brush.brushgo.R.id.arrow_4;
import static com.example.brush.brushgo.R.id.arrow_5;
import static com.example.brush.brushgo.R.id.arrow_6;
import static com.example.brush.brushgo.R.id.arrow_7;
import static com.example.brush.brushgo.R.id.arrow_8;
import static com.example.brush.brushgo.R.id.arrow_9;
import static com.example.brush.brushgo.R.id.drawerLayout;
import static com.example.brush.brushgo.R.id.imageView_1;
import static com.example.brush.brushgo.R.id.imageView_10;
import static com.example.brush.brushgo.R.id.imageView_11;
import static com.example.brush.brushgo.R.id.imageView_12;
import static com.example.brush.brushgo.R.id.imageView_13;
import static com.example.brush.brushgo.R.id.imageView_14;
import static com.example.brush.brushgo.R.id.imageView_15;
import static com.example.brush.brushgo.R.id.imageView_16;
import static com.example.brush.brushgo.R.id.imageView_17;
import static com.example.brush.brushgo.R.id.imageView_18;
import static com.example.brush.brushgo.R.id.imageView_19;
import static com.example.brush.brushgo.R.id.imageView_2;
import static com.example.brush.brushgo.R.id.imageView_20;
import static com.example.brush.brushgo.R.id.imageView_21;
import static com.example.brush.brushgo.R.id.imageView_22;
import static com.example.brush.brushgo.R.id.imageView_23;
import static com.example.brush.brushgo.R.id.imageView_24;
import static com.example.brush.brushgo.R.id.imageView_25;
import static com.example.brush.brushgo.R.id.imageView_26;
import static com.example.brush.brushgo.R.id.imageView_27;
import static com.example.brush.brushgo.R.id.imageView_28;
import static com.example.brush.brushgo.R.id.imageView_29;
import static com.example.brush.brushgo.R.id.imageView_3;
import static com.example.brush.brushgo.R.id.imageView_30;
import static com.example.brush.brushgo.R.id.imageView_31;
import static com.example.brush.brushgo.R.id.imageView_32;
import static com.example.brush.brushgo.R.id.imageView_4;
import static com.example.brush.brushgo.R.id.imageView_5;
import static com.example.brush.brushgo.R.id.imageView_6;
import static com.example.brush.brushgo.R.id.imageView_7;
import static com.example.brush.brushgo.R.id.imageView_8;
import static com.example.brush.brushgo.R.id.imageView_9;
import static com.example.brush.brushgo.R.id.tongue;

/**
 * Created by swlab on 2017/5/5.
 */

public class Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ConstraintLayout layout;
    private Button menu;
    private Button play;
    private Button change_music;
    private Button change_color;
    private TextView timer;
    private TextView countdown;
    private ProgressBar progressBar;
    private Dialog progressDialog;
    private int defaultTime;
    private int aveTime;
    private int timersec;
    private int currentTime;
    private int reminderTime;
    private long currentTimemillisecond;
    private boolean isTimer=false;
    private int backgroundColor;
    private String nowTime;
    private String nowDate;
    private DecimalFormat decimalFormat;
    private FirebaseAuth auth;
    private Firebase firebaseRef;
    private Firebase touchedRef;
    private Firebase timeRef;
    private Firebase reminderRef;
    private Firebase recordRef;
    private Firebase profileRef;
    private Firebase settingRef;
    private StorageReference storageRef;
    private Firebase toothRef;
    private String[] tooth_image=new String[]{
            "tooth_1","tooth_2","tooth_3","tooth_4",
            "tooth_5","tooth_6","tooth_7","tooth_8",
            "tooth_9","tooth_10","tooth_11","tooth_12",
            "tooth_13","tooth_14","tooth_15","tooth_16"};
    private ImageView tooth[]=new ImageView[32];
    private int tooth_id[]=new int[]{
            imageView_1,imageView_2,imageView_3,imageView_4,imageView_5,imageView_6,imageView_7, imageView_8,
            imageView_9,imageView_10,imageView_11,imageView_12,imageView_13,imageView_14, imageView_15,imageView_16,
            imageView_17,imageView_18,imageView_19,imageView_20,imageView_21, imageView_22,imageView_23,imageView_24,
            imageView_25,imageView_26,imageView_27,imageView_28,imageView_29,imageView_30,imageView_31,imageView_32};
    private ImageView arrow[]=new ImageView[25];
    private int arrow_id[]=new int[]{
            arrow_1,arrow_2,arrow_3,arrow_4,arrow_5,
            arrow_6,arrow_7,arrow_8,arrow_9,arrow_10,
            arrow_11,arrow_12,arrow_13,arrow_14,arrow_15,
            arrow_16,arrow_17,arrow_18,arrow_19,arrow_20,
            arrow_21,arrow_22,arrow_23,arrow_24,tongue};
    private int colorArray[]=new int[4];
    private CountDownTimer countdownTimer;
    private CountDownTimer rebrushTimer;
    private MediaPlayer background_music;
    private MediaPlayer finish_music;
    private Boolean isStart =false;
    private Boolean isFinish =false;
    private Boolean isRebrush=false;
    private Boolean isClean=false;
    private Boolean isLogout=false;
    private Boolean click_confirm=false;
    private String push_key;
    private int musicIndex=(int) (Math.random()*10+1);
    private DrawerLayout drawer;
    private NavigationView navigateionView;
    private boolean isdoubleClick=false;
    private AlarmManager alarmManager;
    private Intent intent;
    private PendingIntent pendingIntent;
    private Vibrator vibrator;

    private RequestOptions options;
    private Dialog customDialog;
    private TextView dialog_title;
    private TextView dialog_message;
    private Button dialog_confirm;
    private Button dialog_cancel;
    private Button dialog_middle;
    private ImageView[] fireworkArray =new ImageView[8];
    private int[] fireworkView =new int[]{
            R.id.imageView1,R.id.imageView2,R.id.imageView3,R.id.imageView4,
            R.id.imageView5,R.id.imageView6,R.id.imageView7,R.id.imageView8};


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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Firebase.setAndroidContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        processView();
        recordTouched();
        checkGoogleuser(); //檢查是否首次登入
        processControl();
    }

    private void recordTouched() {
        firebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/");
        auth= FirebaseAuth.getInstance();
        touchedRef =firebaseRef.child("touched").child(auth.getCurrentUser().getUid()).child("home");
        nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        DB_recordTouched touched=new DB_recordTouched(touchedRef,nowTime);
        touched.pushValue();
    }

    private void checkGoogleuser() {
        touchedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==1){
                    Intent intent = new Intent();
                    intent.setClass(Home_Activity.this, Tutorial_Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isNew",true);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
                    setValue();
                    setMusic();
                    setTooth();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void processView() {
        layout=(ConstraintLayout)findViewById(R.id.constraintLayout);
        navigateionView=(NavigationView) findViewById(R.id.nav_home);
        navigateionView.setNavigationItemSelectedListener(Home_Activity.this);
        drawer=(DrawerLayout)findViewById(drawerLayout);
        menu=(Button) findViewById(R.id.btn_menu);
        play=(Button) findViewById(R.id.btn_play);
        change_music=(Button)findViewById(R.id.btn_changecmusic);
        change_color=(Button)findViewById(R.id.btn_changecolor);
        timer=(TextView)findViewById(R.id.txt_timer);
        countdown=(TextView)findViewById(R.id.txt_countdown);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        background_music=new MediaPlayer();
        finish_music = MediaPlayer.create(Home_Activity.this, R.raw.woo_hoo);
        auth= FirebaseAuth.getInstance();
        storageRef=FirebaseStorage.getInstance().getReference();
        firebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/");
        profileRef=firebaseRef.child("profile").child(auth.getCurrentUser().getUid());
        settingRef = firebaseRef.child("setting").child(auth.getCurrentUser().getUid());
        toothRef=firebaseRef.child("tooth").child(auth.getCurrentUser().getUid());
        timeRef = firebaseRef.child("setting").child(auth.getCurrentUser().getUid()).child("time");
        reminderRef=firebaseRef.child("setting").child(auth.getCurrentUser().getUid()).child("reminder");
        recordRef =firebaseRef.child("record").child(auth.getCurrentUser().getUid());
        storageRef=FirebaseStorage.getInstance().getReference();

        decimalFormat= new DecimalFormat("00");
        nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for(int i=0;i<tooth.length;i++)
            tooth[i]=(ImageView)findViewById(tooth_id[i]);

        for(int i = 0; i< arrow.length; i++)
            arrow[i]=(ImageView)findViewById(arrow_id[i]);

        colorArray[0]=R.color.background;
        colorArray[1]=R.color.pink;
        colorArray[2]=R.color.yellow;
        colorArray[3]=R.color.purple;


        alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        intent =new Intent(Home_Activity.this,AlarmNotificationReceiver.class);
        vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
    }

    private void setValue() {
        timeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    defaultTime = dataSnapshot.getValue(int.class);
                    currentTime = defaultTime;
                    timer.setText(defaultTime + "");
                    countdown.setText(decimalFormat.format(defaultTime / 60) + " : " + decimalFormat.format(defaultTime % 60));
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void setTooth() {
        if(isClean)
        {
            for(int i=0;i<tooth.length;i++){
                final int finalI = i;
                storageRef.child("tooth").child(tooth_image[i%16]+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        options = new RequestOptions()
                                .dontAnimate();
                        Glide.with(Home_Activity.this)
                                .setDefaultRequestOptions(options)
                                .load(uri)
                                .into(tooth[finalI]);
                    }
                });
            }
        }
        else{
            for(int j=0;j<tooth.length;j++) {
                final int finalJ = j;
                toothRef.child(j+1+"").child("in").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            if(dataSnapshot.getValue().toString().equals("b")){
                                toothRef.child(finalJ +1+"").child("out").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //內B外G
                                        if(dataSnapshot.getValue().toString().equals("g")){
                                            storageRef.child("tooth").child(tooth_image[finalJ%16]+"_i.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Glide.with(Home_Activity.this)
                                                            .load(uri)
                                                            .into(tooth[finalJ]);
                                                }
                                            });
                                        }
                                        else if(dataSnapshot.getValue().toString().equals("b")){
                                            //內外皆B
                                            storageRef.child("tooth").child(tooth_image[finalJ%16]+"_.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Glide.with(Home_Activity.this)
                                                            .load(uri)
                                                            .into(tooth[finalJ]);
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                            }

                            else if(dataSnapshot.getValue().toString().equals("g")){
                                toothRef.child(finalJ +1+"").child("out").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getValue().toString().equals("g")){
                                            //內外都G
                                            storageRef.child("tooth").child(tooth_image[finalJ%16]+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Glide.with(Home_Activity.this)
                                                            .load(uri)
                                                            .into(tooth[finalJ]);
                                                }
                                            });
                                        }
                                        else if(dataSnapshot.getValue().toString().equals("b")){
                                            //內G外B
                                            storageRef.child("tooth").child(tooth_image[finalJ%16]+"_o.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Glide.with(Home_Activity.this)
                                                            .load(uri)
                                                            .into(tooth[finalJ]);
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                            }
                        }
                        else{
                            for(int i=0;i<tooth.length;i++) {
                                toothRef.child(i + 1 + "").child("in").setValue("g");
                                toothRef.child(i + 1 + "").child("out").setValue("g");
                            }
                            setTooth();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        }
/*        Set_Tooth_Thread tooth_thr=new Set_Tooth_Thread(this,isClean);
        tooth_thr.start();
        try {
            tooth_thr.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    private void setMusic() {
        musicIndex=(int)(Math.random()*10+1);
        background_music=new MediaPlayer();

        if(!isFinish)
            loadingDialog();
        storageRef.child("music").child(musicIndex+".mp3").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    background_music.setDataSource(uri.toString());
                    background_music.prepare();
                    progressDialog.dismiss();
                    if(!isTimer)
                        startDialog();
                    isFinish =false;
                    if(!isStart&&isTimer)
                        rebrush(10);
                } catch (IOException e) {
                    Toast.makeText(Home_Activity.this,"讀取不到音樂",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadingDialog() {
        progressDialog =new Dialog(this,R.style.DialogCustom);
        progressDialog.setContentView(R.layout.custom_progress_dialog);
        progressDialog.setCancelable(false);
        dialog_title = (TextView) progressDialog.findViewById(R.id.title);
        dialog_title.setText("Loading");
        dialog_message = (TextView) progressDialog.findViewById(R.id.message);
        dialog_message.setText("載入音樂請稍候...");
        progressDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);
        progressDialog.show();
    }

    private void processControl() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
                if(background_music.isPlaying()){
                    play.setBackgroundResource(R.drawable.play_button_512);
                    background_music.pause();
                    timerPause();
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(background_music.isPlaying()){
                    rebrush(10);
                    play.setBackgroundResource(R.drawable.play_button_512);
                    background_music.pause();
                    timerPause();
                }
                else {
                    isStart=true;
                    rebrushTimer.cancel();
                    play.setBackgroundResource(R.drawable.pause_button_512);
                    background_music.start();
                    timerStart();
                }
            }

        });
        change_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTimer)
                {
                    background_music.stop();
                    background_music.release();
                    timerPause();
                    play.setBackgroundResource(R.drawable.play_button_512);
                    setMusic();
                    rebrush(10);
                }
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
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if(isRebrush)
                    rebrushTimer.cancel();
                if(background_music.isPlaying()){
                    play.setBackgroundResource(R.drawable.play_button_512);
                    background_music.pause();
                    timerPause();
                    isRebrush=true;
                }

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if(!background_music.isPlaying()) {
                    if (isRebrush && !isTimer&&!isLogout)
                        rebrush(5);
                    else if (isRebrush && isTimer&&!isLogout)
                        rebrush(10);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void startDialog() {
        customDialog =new Dialog(this,R.style.DialogCustom);
        customDialog.setContentView(R.layout.custom_dialog_three);
        customDialog.setCancelable(false);
        dialog_title = (TextView) customDialog.findViewById(R.id.title);
        dialog_title.setText("貼心提醒");
        dialog_message = (TextView) customDialog.findViewById(R.id.message);
        String message = "<font color=#000000>刷牙前請先使用牙線及牙間刷進行牙縫清潔，</font> <font color=#ff0000>並於牙縫清潔後按下開始刷牙。</font>";
        dialog_message.setText(Html.fromHtml(message));
        dialog_confirm = (Button) customDialog.findViewById(R.id.confirm);
        dialog_confirm.setText("開始刷牙");
        dialog_middle=(Button)customDialog.findViewById(R.id.middle);
        dialog_middle.setText("牙縫狀況");
        dialog_cancel=(Button) customDialog.findViewById(R.id.cancel);
        dialog_cancel.setText("衛教及設定");
        customDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);

        customDialog.show();

        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                /*rebrush(5);*/
                isStart=true;
                play.setBackgroundResource(R.drawable.pause_button_512);
                background_music.start();
                timerStart();
                if(isRebrush)
                    rebrushTimer.cancel();
            }
        });
        dialog_middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                startActivity(new Intent(Home_Activity.this,Tooth_Condition_Activity.class));
            }
        });
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    private void rebrush(final long time) {
        isRebrush=true;

        rebrushTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                vibrator.vibrate(1000);
                customDialog = new Dialog(Home_Activity.this, R.style.DialogCustom);
                customDialog.setContentView(R.layout.custom_dialog_one);
                customDialog.setCancelable(false);
                dialog_title = (TextView) customDialog.findViewById(R.id.title);
                dialog_title.setText("提醒");
                dialog_message = (TextView) customDialog.findViewById(R.id.message);
                if(time==5)
                    dialog_message.setText("尚未開始BrushGo，請按下確認鍵開始刷牙唷");
                else
                    dialog_message.setText("準備好繼續BrushGo了嗎?");
                dialog_confirm = (Button) customDialog.findViewById(R.id.confirm);
                dialog_confirm.setText("確認");
                dialog_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!background_music.isPlaying()) {
                            customDialog.dismiss();
                            isStart = true;
                            isRebrush = false;
                            play.setBackgroundResource(R.drawable.pause_button_512);
                            background_music.start();
                            timerStart();
                        }
                        else
                            customDialog.dismiss();
                    }
                });
                customDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);
                customDialog.show();
            }
        };
        rebrushTimer.start();
    }

    private void timerStart() {
        isTimer=true;
        timersec= Integer.parseInt(timer.getText().toString().trim());
        countdownTimer = new CountDownTimer(timersec * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(millisUntilFinished/1000+"");
                countdown.setText(decimalFormat.format(millisUntilFinished/1000/60)+" : "+decimalFormat.format(millisUntilFinished/1000%60));
                tooth_start();
                setProgressbar();
            }

            @Override
            public void onFinish() {
                isFinish =true;
                isClean=true;
                play.setBackgroundResource(R.drawable.play_button_512);
                timerStop();
                finishDialog();
                finish_music.start();
                recordData();
                setReminder();
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
        setProgressbar();
        background_music.stop();
        setMusic();
        setTooth();
        reset_arrow();
    }

    private void setProgressbar() {
        currentTime = (Integer.parseInt(timer.getText().toString().trim()));
        progressBar.setMax(defaultTime);
        progressBar.setProgress(defaultTime-currentTime);
    }

    private void finishDialog() {
        customDialog =new Dialog(this,R.style.DialogCustom);
        customDialog.setContentView(R.layout.firework_dilog);
        customDialog.setCancelable(false);

        dialog_title = (TextView) customDialog.findViewById(R.id.title);
        dialog_title.setText("恭喜完成");
        dialog_message = (TextView) customDialog.findViewById(R.id.message);
        dialog_message.setText("您已經使用BrushGo  次囉,繼續加油~");
        dialog_confirm=(Button) customDialog.findViewById(R.id.confirm);
        dialog_confirm.setText("確認");
        recordRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog_message.setText("您已經使用BrushGo "+(dataSnapshot.getChildrenCount())+" 次囉,繼續加油~");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        dialog_confirm = (Button) customDialog.findViewById(R.id.confirm);
        customDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);
        for(int i = 0; i< fireworkView.length; i++) {
            fireworkArray[i] = (ImageView) customDialog.findViewById(fireworkView[i]);
            options=new RequestOptions();
            Glide.with(Home_Activity.this)
                    .setDefaultRequestOptions(options)
                    .asGif()
                    .load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/image%2Ffirework.gif?alt=media&token=c3d69e19-6be0-415e-86ff-7aeed07f3c51"))
                    .into(fireworkArray[i]);
        }

        customDialog.show();

        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_confirm=true;
                recordData();
                setReminder();
                if(finish_music.isPlaying())
                    finish_music.stop();
                customDialog.dismiss();
                Toast.makeText(Home_Activity.this,"資料已儲存",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setReminder() {
        reminderRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reminderTime=Integer.parseInt(dataSnapshot.getValue().toString());
                intent.putExtra("contentTitle","打開BrushGo吧");
                intent.putExtra("contentText","已經"+reminderTime+"天沒使用BrushGo刷牙囉:(");
                currentTimemillisecond =System.currentTimeMillis();
                pendingIntent=PendingIntent.getBroadcast(Home_Activity.this, (int) currentTimemillisecond, intent,pendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTimemillisecond +reminderTime*AlarmManager.INTERVAL_DAY ,pendingIntent);
                profileRef.child("last_brush_time").setValue(currentTimemillisecond);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void recordData() {
        if(!click_confirm) {
            DB_Record data = new DB_Record(auth.getCurrentUser().getEmail(), nowTime, "F");
            recordRef.push().setValue(data, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    push_key=firebase.getKey();
                }
            });
        }
        else {
            recordRef.child(push_key).child("click_confirm").setValue("T");
            click_confirm=false;
        }
    }

    private void tooth_start() {
        currentTime = (Integer.parseInt(timer.getText().toString().trim()));
        aveTime=defaultTime/25;
        if(currentTime%aveTime==0&&currentTime!=defaultTime&&currentTime!=0) {
            vibrator.vibrate(500);
        }

        for(int i = 0; i< arrow.length; i++){
            if(i==0)
            {
                if(currentTime>defaultTime-aveTime*(i+1)&&currentTime<defaultTime-aveTime*(i-1))
                    arrow[i].setVisibility(View.VISIBLE);
            }
            else if(i==arrow.length-1)
            {
                if(currentTime>defaultTime-aveTime*(i+1)&&currentTime<defaultTime-aveTime*i){
                    arrow[i].setImageResource(R.drawable.tongue_brush);
                    arrow[i-1].setVisibility(View.INVISIBLE);
                }
            }
            else
                if(currentTime>defaultTime-aveTime*(i+1)&&currentTime<defaultTime-aveTime*i){
                arrow[i].setVisibility(View.VISIBLE);
                arrow[i-1].setVisibility(View.INVISIBLE);
            }
        }
        int[][] part={
                {1,2,3,4,5},
                {7,8,9,10,11},
                {13,14,15,16,17},
                {19,20,21,22,23}};


        ImageView[][][] tooth_group={
                {{tooth[0],tooth[1],tooth[2]},
                {tooth[3],tooth[4],tooth[5]},
                {tooth[6],tooth[7],tooth[8],tooth[9]},
                {tooth[10],tooth[11],tooth[12]},
                {tooth[13],tooth[14],tooth[15]}},

                {{tooth[13],tooth[14],tooth[15]},
                {tooth[10],tooth[11],tooth[12]},
                {tooth[6],tooth[7],tooth[8],tooth[9]},
                {tooth[3],tooth[4],tooth[5]},
                {tooth[0],tooth[1],tooth[2]}},

                {{tooth[29],tooth[30],tooth[31]},
                {tooth[26],tooth[27],tooth[28]},
                {tooth[22],tooth[23],tooth[24],tooth[25]},
                {tooth[19],tooth[20],tooth[21]},
                {tooth[16],tooth[17],tooth[18]}},

                {{tooth[16],tooth[17],tooth[18]},
                {tooth[19],tooth[20],tooth[21]},
                {tooth[22],tooth[23],tooth[24],tooth[25]},
                {tooth[26],tooth[27],tooth[28]},
                {tooth[29],tooth[30],tooth[31]}}
        };

        for(int j=0;j<part.length;j++){
            for(int k=0;k<part[j].length;k++){
                if(currentTime==defaultTime-aveTime*part[j][k]){
                    for(int l=0;l<tooth_group[j][k].length;l++)
                        for(int m=0;m<tooth.length;m++){
                            if(tooth_group[j][k][l]==tooth[m]) {
                                final int finalM = m;
                                final int finalJ = j;
                                toothRef.child(m + 1 + "").child("in").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getValue().equals("b")) {
                                            toothRef.child(finalM + 1 + "").child("out").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.getValue().equals("b")) {
                                                        //內外皆B
                                                        if (finalJ % 2 == 0) { //跑外圈
                                                            storageRef.child("tooth").child(tooth_image[finalM % 16] + "_i.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    Glide.with(Home_Activity.this)
                                                                            .load(uri)
                                                                            .into(tooth[finalM]);
                                                                }
                                                            });
                                                        } else { //跑內圈
                                                            storageRef.child("tooth").child(tooth_image[finalM % 16] + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    Glide.with(Home_Activity.this)
                                                                            .load(uri)
                                                                            .into(tooth[finalM]);
                                                                }
                                                            });
                                                        }
                                                    } else {
                                                        //內B外G
                                                        if (finalJ % 2 == 0) { //跑外圈

                                                        } else { //跑內圈
                                                            storageRef.child("tooth").child(tooth_image[finalM % 16] + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    Glide.with(Home_Activity.this)
                                                                            .load(uri)
                                                                            .into(tooth[finalM]);
                                                                }
                                                            });
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {

                                                }
                                            });
                                        } else {
                                            toothRef.child(finalM + 1 + "").child("out").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.getValue().equals("b")) {
                                                        //內G外B
                                                        if (finalJ % 2 == 0) { //跑外圈
                                                            storageRef.child("tooth").child(tooth_image[finalM % 16] + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    Glide.with(Home_Activity.this)
                                                                            .load(uri)
                                                                            .into(tooth[finalM]);
                                                                }
                                                            });
                                                        } else { //跑內圈

                                                        }
                                                    } else {
                                                        //內外皆G
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                        }
                    }
                }
            }
        }
    }

    private void reset_arrow() {
        arrow[arrow.length-1].setImageResource(R.drawable.tongue);
        vibrator.vibrate(1000);

        for(int i = 0; i< arrow.length-1; i++)
            arrow[i].setVisibility(View.INVISIBLE);
        if(defaultTime%60<10)
            countdown.setText("0"+defaultTime/60+"：0"+defaultTime%60);
        else
            countdown.setText("0"+defaultTime/60+"："+defaultTime%60);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id== R.id.Home)
        {
            startActivity(new Intent(this,Home_Activity.class));
        }
        else if(id==R.id.Video)
        {
            startActivity(new Intent(this,Video_Activity.class));
        }
        else if(id==R.id.Information)
        {
            startActivity(new Intent(this,Information_Activity.class));
        }
        else if(id==R.id.Tutorial)
        {
            startActivity(new Intent(this,Tutorial_Activity.class));
        }
        else if(id==R.id.Tooth_Condition){
            startActivity(new Intent(this,Tooth_Condition_Activity.class));
        }
        else if(id==R.id.Setting)
        {
            startActivity(new Intent(this,Setting_Activity.class));
        }
        else if(id==R.id.Logout)
        {
            isLogout=true;
            customDialog =new Dialog(this,R.style.DialogCustom);
            customDialog.setContentView(R.layout.custom_dialog_two);
            customDialog.setCancelable(false);
            dialog_title = (TextView) customDialog.findViewById(R.id.title);
            dialog_title.setText("確定要登出?");
            dialog_message = (TextView) customDialog.findViewById(R.id.message);
            dialog_message.setText("登出後將無法準確紀錄您刷牙的狀況，但您仍會收到BrushGo的提醒。");
            dialog_confirm = (Button) customDialog.findViewById(R.id.confirm);
            dialog_confirm.setText("登出");
            dialog_cancel=(Button) customDialog.findViewById(R.id.cancel);
            dialog_cancel.setText("取消");
            customDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);
            customDialog.show();

            dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auth.signOut();
                    customDialog.dismiss();
                    startActivity(new Intent(Home_Activity.this,MainActivity.class));
                }
            });
            dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();
                    isLogout=false;
                    if(!background_music.isPlaying()) {
                        if (isRebrush && !isTimer&&!isLogout)
                            rebrush(5);
                        else if (isRebrush && isTimer&&!isLogout)
                            rebrush(10);
                    }
                }
            });
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /*    private void deleteAccount() {
            FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                    }
                }
            });
        }*/
    @Override
    protected void onStop() {
        super.onStop();
        if(background_music.isPlaying()){
            play.setBackgroundResource(R.drawable.play_button_512);
            background_music.pause();
            timerPause();
        }
        if(finish_music.isPlaying())
            finish_music.pause();
        if(isRebrush){
            rebrushTimer.cancel();
            customDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!isRebrush&&isTimer) //關閉前未通知,且音樂進行到一半
            rebrush(10);
        else if(isRebrush&&!isTimer) //關閉前正在倒數,且音樂尚未開始
            rebrush(5);
        else if(isRebrush&&isTimer)
            rebrush(10);
    }
}