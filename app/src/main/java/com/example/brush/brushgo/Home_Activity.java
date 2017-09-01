package com.example.brush.brushgo;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.brush.brushgo.R.id.Home;
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
import static com.example.brush.brushgo.R.id.lower_left;
import static com.example.brush.brushgo.R.id.lower_right;
import static com.example.brush.brushgo.R.id.upper_left;
import static com.example.brush.brushgo.R.id.upper_right;

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
    private ProgressDialog progressDialog;
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
    private Firebase timeRef;
    private Firebase reminderRef;
    private Firebase musicRef;
    private Firebase recordRef;
    private Firebase toothRef;
    private Firebase profileRef;
    private Firebase settingRef;
    private Firebase imageRef;
    private int[] timeArray=new int[]{120,180,240};
    private ImageView tooth[]=new ImageView[32];
    private int tooth_id[]=new int[]{
            imageView_1,imageView_2,imageView_3,imageView_4,imageView_5,imageView_6,imageView_7, imageView_8,
            imageView_9,imageView_10,imageView_11,imageView_12,imageView_13,imageView_14, imageView_15,imageView_16,
            imageView_17,imageView_18,imageView_19,imageView_20,imageView_21, imageView_22,imageView_23,imageView_24,
            imageView_25,imageView_26,imageView_27,imageView_28,imageView_29,imageView_30,imageView_31,imageView_32};
    private ImageView arrow_array[]=new ImageView[8];
    private int colorArray[]=new int[4];
    private CountDownTimer countdownTimer;
    private MediaPlayer music;
    private MediaPlayer finish_music;
    private Boolean finish=false;
    private String musicUrl=" ";
    private int musicIndex=(int) (Math.random()*10+1);
    private DrawerLayout drawer;
    private NavigationView navigateionView;
    private boolean isdoubleClick=false;
    private AlarmManager alarmManager;
    private Intent intent;
    private PendingIntent pendingIntent;
    private Vibrator vibrator;

    private Dialog customDialog;
    private TextView dialog_title;
    private TextView dialog_message;
    private Button dialog_confirm;
    private ImageView[] fireworkArray =new ImageView[8];
    private int[] fireworkView =new int[]{R.id.imageView1,R.id.imageView2,R.id.imageView3,R.id.imageView4,
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
        checkGoogleuser();
        setValue();
        setTooth();
        startDialog();
        processControl();
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
        progressDialog = new ProgressDialog(this,R.style.DialogCustom);
        auth= FirebaseAuth.getInstance();
        firebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/");
        profileRef=firebaseRef.child("profile").child(auth.getCurrentUser().getUid());
        settingRef = firebaseRef.child("setting").child(auth.getCurrentUser().getUid());
        toothRef=firebaseRef.child("tooth").child(auth.getCurrentUser().getUid());
        timeRef = firebaseRef.child("setting").child(auth.getCurrentUser().getUid()).child("time");
        reminderRef=firebaseRef.child("setting").child(auth.getCurrentUser().getUid()).child("reminder");
        recordRef =firebaseRef.child("record").child(auth.getCurrentUser().getUid());
        imageRef=firebaseRef.child("image");

        decimalFormat= new DecimalFormat("00");
        nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for(int i=0;i<tooth.length;i++)
            tooth[i]=(ImageView)findViewById(tooth_id[i]);

        colorArray[0]=R.color.background;
        colorArray[1]=R.color.pink;
        colorArray[2]=R.color.yellow;
        colorArray[3]=R.color.purple;

        arrow_array[0]=(ImageView)findViewById(upper_left);
        arrow_array[1]=(ImageView)findViewById(upper_right);
        arrow_array[2]=(ImageView)findViewById(lower_right);
        arrow_array[3]=(ImageView)findViewById(lower_left);
        arrow_array[4]=(ImageView)findViewById(R.id.lower_left_out);
        arrow_array[5]=(ImageView)findViewById(R.id.lower_right_out);
        arrow_array[6]=(ImageView)findViewById(R.id.upper_right_out);
        arrow_array[7]=(ImageView)findViewById(R.id.upper_left_out);

        alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        intent =new Intent(Home_Activity.this,AlarmNotificationReceiver.class);
        vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
    }


    private void checkGoogleuser() {
        profileRef.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null) {
                    Intent intent=new Intent();
                    intent.setClass(Home_Activity.this,Tutorial_Activity.class);
                    startActivity(intent);

                    DB_Setting setting = new DB_Setting(auth.getCurrentUser().getEmail(),timeArray[(int) (Math.random()*3)],3,null,null);
                    settingRef.setValue(setting);

                    DB_Profile profile=new DB_Profile(auth.getCurrentUser().getDisplayName(),nowDate,null,null,null);
                    profileRef.setValue(profile);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void setValue() {
        timeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                defaultTime=dataSnapshot.getValue(int.class);
                currentTime=defaultTime;
                timer.setText(defaultTime+"");
                countdown.setText(decimalFormat.format(defaultTime/60)+" : "+decimalFormat.format(defaultTime%60));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void setTooth() {
        for(int j=0;j<tooth.length;j++) {
            final int finalJ = j;
            toothRef.child(j+1+"").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        if(dataSnapshot.getValue().toString().trim().equals("b"))
                        {
                            imageRef.child("tooth_dirty").child(finalJ%16+1+"").addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(final DataSnapshot dataSnapshot) {
                                    Glide.with(Home_Activity.this)
                                            .load(Uri.parse(dataSnapshot.getValue().toString()))
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(tooth[finalJ]);
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                        else
                        {
                            imageRef.child("tooth_clean").child(finalJ%16+1+"").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Glide.with(Home_Activity.this)
                                            .load(Uri.parse(dataSnapshot.getValue().toString()))
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(tooth[finalJ]);
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                    }
                    else
                    {
                        for(int i=0;i<tooth.length;i++)
                            toothRef.child(i+1+"").setValue("g");
                        setTooth();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }


    private void setMusic() {
        music=new MediaPlayer();
        musicIndex=(int)(Math.random()*10+1);
        musicRef =new Firebase("https://brushgo-67813.firebaseio.com/music/"+musicIndex); //取得firebase網址 用亂數取得節點網址
        if(!finish)
            loadingDialog();
        musicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                musicUrl=dataSnapshot.getValue(String.class); //取得節點內的資料
                try {
                    music.setDataSource(musicUrl); //設定media的路徑
                    music.prepare();
                    progressDialog.dismiss();
                    finish=false;

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

    private void loadingDialog() {
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("載入音樂中，請稍後");
        progressDialog.setIcon(R.drawable.loading_24);
        progressDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);
        progressDialog.show();
    }

    private void processControl() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
                if(music.isPlaying()){
                    play.setBackgroundResource(R.drawable.play_button_512);
                    music.pause();
                    timerPause();
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(music.isPlaying()){
                    play.setBackgroundResource(R.drawable.play_button_512);
                    music.pause();
                    timerPause();
                }
                else {
                    play.setBackgroundResource(R.drawable.pause_button_512);
                    music.start();
                    timerStart();
                }
            }

        });
        change_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTimer)
                {
                    music.stop();
                    music.release();
                    timerPause();
                    play.setBackgroundResource(R.drawable.play_button_512);
                    setMusic();
                }
                else
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

    private void startDialog() {
        customDialog =new Dialog(this);
        customDialog.setContentView(R.layout.custom_dialog);
        customDialog.setCancelable(false);

        dialog_title = (TextView) customDialog.findViewById(R.id.title);
        dialog_title.setText("貼心提醒");
        dialog_message = (TextView) customDialog.findViewById(R.id.message);
        dialog_message.setText("刷牙前請先使用牙線及牙間刷做簡單清潔。");
        dialog_confirm = (Button) customDialog.findViewById(R.id.confirm);
        dialog_confirm.setText("已清潔，開始刷牙。");
        customDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);

        customDialog.show();

        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                setMusic();
            }
        });
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
                finish=true;
                play.setBackgroundResource(R.drawable.play_button_512);
                timerStop();
                finishDialog();
                finish_music = MediaPlayer.create(Home_Activity.this, R.raw.woo_hoo);
                finish_music.start();
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
        music.stop();
        setMusic();
        tooth_stop();
    }

    private void setProgressbar() {
        currentTime = (Integer.parseInt(timer.getText().toString().trim()));
        progressBar.setMax(defaultTime);
        progressBar.setProgress(defaultTime-currentTime);
    }

    private void finishDialog() {
        customDialog =new Dialog(this);
        customDialog.setContentView(R.layout.firework_dilog);
        customDialog.setCancelable(false);

        dialog_title = (TextView) customDialog.findViewById(R.id.title);
        dialog_title.setText("刷牙時間到囉");
        dialog_message = (TextView) customDialog.findViewById(R.id.message);
        dialog_message.setText("恭喜您刷完牙了，按下確認以紀錄。");
        dialog_confirm = (Button) customDialog.findViewById(R.id.confirm);
        customDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);
        for(int i = 0; i< fireworkView.length; i++) {
            fireworkArray[i] = (ImageView) customDialog.findViewById(fireworkView[i]);
            Glide.with(Home_Activity.this)
                    .load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/image%2Ffirework.gif?alt=media&token=c3d69e19-6be0-415e-86ff-7aeed07f3c51"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(fireworkArray[i]);
        }

        customDialog.show();

        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordData();
                setReminder();
                music.stop();
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
        DB_Record data = new DB_Record(auth.getCurrentUser().getEmail(),nowTime);
        recordRef.push().setValue(data);
    }


    private void tooth_start() {
        currentTime = (Integer.parseInt(timer.getText().toString().trim()));
        aveTime=defaultTime/8;
        if(currentTime%aveTime==0&&currentTime!=defaultTime&&currentTime!=0)
            vibrator.vibrate(1000);

        if(currentTime>defaultTime-aveTime*1&&currentTime<defaultTime-aveTime*0-1) {
            arrow_array[0].setVisibility(View.VISIBLE);
        }
        else  if(currentTime>defaultTime-aveTime*2&&currentTime<defaultTime-aveTime*1) {
            arrow_array[0].setVisibility(View.INVISIBLE);
            arrow_array[1].setVisibility(View.VISIBLE);
        }
        else  if(currentTime>defaultTime-aveTime*3&&currentTime<defaultTime-aveTime*2) {
            arrow_array[1].setVisibility(View.INVISIBLE);
            arrow_array[2].setVisibility(View.VISIBLE);
        }
        else  if(currentTime>defaultTime-aveTime*4&&currentTime<defaultTime-aveTime*3) {
            arrow_array[2].setVisibility(View.INVISIBLE);
            arrow_array[3].setVisibility(View.VISIBLE);
        }

        else  if(currentTime>defaultTime-aveTime*5&&currentTime<defaultTime-aveTime*4) {
            arrow_array[3].setVisibility(View.INVISIBLE);
            arrow_array[4].setVisibility(View.VISIBLE);
        }
        else  if(currentTime>defaultTime-aveTime*6&&currentTime<defaultTime-aveTime*5) {
            arrow_array[4].setVisibility(View.INVISIBLE);
            arrow_array[5].setVisibility(View.VISIBLE);
        }
        else  if(currentTime>defaultTime-aveTime*7&&currentTime<defaultTime-aveTime*6) {
            arrow_array[5].setVisibility(View.INVISIBLE);
            arrow_array[6].setVisibility(View.VISIBLE);
        }
        else  if(currentTime>defaultTime-aveTime*8&&currentTime<defaultTime-aveTime*7) {
            arrow_array[6].setVisibility(View.INVISIBLE);
            arrow_array[7].setVisibility(View.VISIBLE);
        }
    }

    private void tooth_stop() {
        arrow_array[7].setVisibility(View.INVISIBLE);
        vibrator.vibrate(1500);

        for(int i=0;i<arrow_array.length-1;i++)
            arrow_array[i].setVisibility(View.INVISIBLE);
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
        else if(id==R.id.Tutorial)
        {
            Intent intent=new Intent();
            intent.setClass(this,Tutorial_Activity.class);
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
/*                    deleteAccount();*/
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
    /*private void deleteAccount() {
        FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {

                }
            }
        });
    }*/
    @Override
    protected void onStop() {
        super.onStop();
        if(music.isPlaying()){
            play.setBackgroundResource(R.drawable.play_button_512);
            music.pause();
            timerPause();
        }
    }
}