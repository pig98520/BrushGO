package com.example.brush.brushgo;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.brush.brushgo.R.id.Home;
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

/**
 * Created by pig98520 on 2017/11/20.
 */

public class Tooth_Condition_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Button menu;
    private Button brush;
    private Switch switcher;
    private boolean isTooth=true;
    private DrawerLayout drawer;
    private Boolean isdoubleClick=false;
    private FirebaseAuth auth;
    private StorageReference storageRef;
    private Firebase firebaseRef;
    private Firebase toothRef;
    private Firebase interdentalRef;
    private Firebase touchedRef;
    private String nowTime;

    private Dialog customDialog;
    private Button dialog_confirm;
    private Button dialog_cancel;
    private TextView dialog_title;
    private TextView dialog_message;

    private RequestOptions options;
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

    private Button[] btn_interdental=new Button[30];
    private int[] btn_interdental_id={R.id.interdental_1,R.id.interdental_2,R.id.interdental_3,R.id.interdental_4,R.id.interdental_5,
            R.id.interdental_6,R.id.interdental_7,R.id.interdental_8,R.id.interdental_9,R.id.interdental_10,
            R.id.interdental_11,R.id.interdental_12,R.id.interdental_13,R.id.interdental_14,R.id.interdental_15,
            R.id.interdental_16,R.id.interdental_17,R.id.interdental_18,R.id.interdental_19,R.id.interdental_20,
            R.id.interdental_21,R.id.interdental_22,R.id.interdental_23,R.id.interdental_24,R.id.interdental_25,
            R.id.interdental_26,R.id.interdental_27,R.id.interdental_28,R.id.interdental_29,R.id.interdental_30};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.tooth_condition);
        processView();
        processControl();
        recordTouched();
        setTooth();
        setInterdental();
    }

    @Override
    public void onBackPressed() {
        if(!isdoubleClick)
        {
            Toast.makeText(Tooth_Condition_Activity.this,"雙擊以退出",Toast.LENGTH_LONG).show();
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

    private void recordTouched() {
        firebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/");
        auth= FirebaseAuth.getInstance();
        touchedRef =firebaseRef.child("touched").child(auth.getCurrentUser().getUid()).child("condition");
        nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        DB_recordTouched touched=new DB_recordTouched(touchedRef,nowTime);
        touched.pushValue();
    }

    private void processView() {
        NavigationView navigateionView=(NavigationView) findViewById(R.id.nav_conditon);
        navigateionView.setNavigationItemSelectedListener(Tooth_Condition_Activity.this);
        auth= FirebaseAuth.getInstance();
        menu=(Button)findViewById(R.id.btn_menu);
        brush=(Button)findViewById(R.id.btn_brush);
        switcher=(Switch)findViewById(R.id.switcher);
        drawer=(DrawerLayout)findViewById(R.id.drawerLayout);
        for(int i=0;i<tooth.length;i++)
            tooth[i]=(ImageView)findViewById(tooth_id[i]);
        for(int i=0;i<btn_interdental.length;i++)
            btn_interdental[i]=(Button)findViewById(btn_interdental_id[i]);
        storageRef= FirebaseStorage.getInstance().getReference();
        firebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/");
        toothRef=firebaseRef.child("tooth").child(auth.getCurrentUser().getUid());
        interdentalRef=firebaseRef.child("interdental").child(auth.getUid());
        nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private void processControl() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tooth_Condition_Activity.this,Home_Activity.class));
            }
        });
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isTooth=false;
                    setTooth();
                }
                else{
                    isTooth=true;
                    setTooth();
                }
            }
        });
    }

    private void setTooth() {
        if(isTooth){
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
                                                    Glide.with(Tooth_Condition_Activity.this)
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
                                                    Glide.with(Tooth_Condition_Activity.this)
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

                            if(dataSnapshot.getValue().toString().equals("g")){
                                toothRef.child(finalJ +1+"").child("out").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getValue().toString().equals("g")){
                                            //內外都G
                                            storageRef.child("tooth").child(tooth_image[finalJ%16]+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Glide.with(Tooth_Condition_Activity.this)
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
                                                    Glide.with(Tooth_Condition_Activity.this)
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
        else{
            for(int i=0;i<tooth.length;i++)
            {
                final int finalI = i;
                storageRef.child("tooth").child(tooth_image[i%16]+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        options = new RequestOptions()
                                .dontAnimate();
                        Glide.with(Tooth_Condition_Activity.this)
                                .setDefaultRequestOptions(options)
                                .load(uri)
                                .into(tooth[finalI]);
                    }
                });
            }
        }
    }

    private void setInterdental() {
        for(int i=0;i<btn_interdental.length;i++){
            final int finalI = i;
            interdentalRef.child(i+1+"").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue().equals("g")){
                        btn_interdental[finalI].setBackgroundResource(R.drawable.transparent_mark);
                    }
                    else{
                        btn_interdental[finalI].setBackgroundResource(R.drawable.red_mark);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id== Home)
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
                    startActivity(new Intent(Tooth_Condition_Activity.this,MainActivity.class));
                }
            });
            dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();
                }
            });
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
