package com.example.brush.brushgo;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.brush.brushgo.R.id.Home;

/**
 * Created by swlab on 2017/5/5.
 */

public class Tutorial_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FirebaseAuth auth;
    private Firebase firebaseRef;
    private Firebase profileRef;
    private Firebase settingRef;
    private Firebase toothRef;
    private Firebase interdentalRef;
    private Firebase touchedRef;
    private StorageReference storageReference;
    private String nowTime;
    private String nowDate;
    private Bundle bundle;
    private boolean isNew=false;
    private Boolean isdoubleClick=false;
    private Tutorial_Adapter customAdapter;
    private ViewPager viewPager;
    private int[] sliderView=new int[]{R.id.slider_1,R.id.slider_2,R.id.slider_3,R.id.slider_4,R.id.slider_5,R.id.slider_6,R.id.slider_7};
    private ImageView[] slider=new ImageView[sliderView.length];
    private String [] imageUrl=new String[sliderView.length];
    private Dialog customDialog;
    private Button dialog_confirm;
    private Button dialog_cancel;
    private TextView dialog_title;
    private TextView dialog_message;
    private TextView dialog_link;

    @Override
    public void onBackPressed() {
        if(!isdoubleClick)
        {
            Toast.makeText(Tutorial_Activity.this,"雙擊以退出",Toast.LENGTH_LONG).show();
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);
        setImageUrl();
        processView();
        processControl();
    }

    private void recordTouched() {
        touchedRef =firebaseRef.child("touched").child(auth.getCurrentUser().getUid()).child("tutorial");
        DB_recordTouched touched=new DB_recordTouched(touchedRef,nowTime);
        touched.pushValue();
    }

    private void setImageUrl() {
        storageReference= FirebaseStorage.getInstance().getReference();
        customAdapter=new Tutorial_Adapter(Tutorial_Activity.this);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(customAdapter);
        for(int i=0;i<imageUrl.length;i++) {
            final int finalI = i;
            storageReference.child("tutorial").child("tutorial_" +i+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    imageUrl[finalI]=uri.toString();
                    customAdapter.setImageUrl(uri.toString(),finalI);
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void processView() {
        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_tutorial);
        navigationView.setNavigationItemSelectedListener(Tutorial_Activity.this);
        firebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/");
        auth= FirebaseAuth.getInstance();
        nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        profileRef=firebaseRef.child("profile").child(auth.getCurrentUser().getUid());
        settingRef = firebaseRef.child("setting").child(auth.getCurrentUser().getUid());
        toothRef = firebaseRef.child("tooth").child(auth.getCurrentUser().getUid());
        interdentalRef=firebaseRef.child("interdental").child(auth.getCurrentUser().getUid());
        nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        bundle = this.getIntent().getExtras();
        if(bundle!=null)
            isNew = bundle.getBoolean("isNew");
        if(isNew)
            signupDialog_google();
        else {
            recordTouched();
        }

        drawer=(DrawerLayout)findViewById(R.id.drawerLayout);
        for(int i=0;i<slider.length;i++)
            slider[i]=(ImageView)findViewById(sliderView[i]);
    }

    private void processControl() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == customAdapter.getCount() - 1) {
                    finisnDialog();
                }
                for (int i = 0; i < customAdapter.getCount(); i++)
                {
                    if(position==i)
                        slider[i].setImageResource(R.drawable.slider_black);
                    else
                        slider[i].setImageResource(R.drawable.slider_gray);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for(int i=0;i<customAdapter.getCount();i++)
        {
            final int finalI = i;
            slider[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(finalI, true);
                }
            });
        }
    }

    private void signupDialog_google() {
        customDialog =new Dialog(this,R.style.DialogCustom);
        customDialog.setContentView(R.layout.custom_dialog_sign_up_google);
        customDialog.setCancelable(false);
        dialog_title = (TextView) customDialog.findViewById(R.id.title);
        dialog_title.setText("BrushGo研究同意書");
        dialog_message=(TextView)customDialog.findViewById(R.id.message);
        dialog_message.setText("親愛的受訪者您好:\n\n"+
                                "我們誠摯地邀請您參與此次\n" +
                                "『口腔衛生促進』研究，若\n"+
                                "您同意參與此次計畫，且允\n" +
                                "許我們取用您使用BrushGo\n" +
                                "期間的相關紀錄請填寫問卷\n" +
                                "後按下同意按鈕，而您所提\n"+
                                "供的一切資料僅供學術研究\n" +
                                "使用。\n\n"+
                                "BrushGo開發團隊 敬上");

        dialog_confirm = (Button) customDialog.findViewById(R.id.confirm);
        dialog_confirm.setText("同意");
        dialog_cancel=(Button)customDialog.findViewById(R.id.cancel);
        dialog_cancel.setText("不同意");
        customDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);

        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                DB_Setting setting = new DB_Setting(auth.getCurrentUser().getEmail(),180,1,null,null,null,null,null);
                settingRef.setValue(setting);

                DB_Profile profile=new DB_Profile(auth.getCurrentUser().getDisplayName(),nowDate,null,null,null);
                profileRef.setValue(profile);

                for(int i=0;i<32;i++) {
                    toothRef.child(i + 1 + "").child("in").setValue("g");
                    toothRef.child(i + 1 + "").child("out").setValue("g");
                }
                for(int i=0;i<30;i++){
                    interdentalRef.child(i+1+"").setValue("g");
                }
            }
        });
        customDialog.show();
    }

    private void finisnDialog() {
        customDialog =new Dialog(this,R.style.DialogCustom);
        customDialog.setContentView(R.layout.custom_dialog_two);
        customDialog.setCancelable(false);
        dialog_title = (TextView) customDialog.findViewById(R.id.title);
        dialog_title.setText("教學結束");
        dialog_link = (TextView) customDialog.findViewById(R.id.message);
        dialog_link.setText("馬上使用BrushGo刷牙吧！");
        dialog_confirm = (Button) customDialog.findViewById(R.id.confirm);
        dialog_confirm.setText("開始刷牙");
        dialog_cancel=(Button) customDialog.findViewById(R.id.cancel);
        dialog_cancel.setText("再看一次");
        customDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);

        customDialog.show();

        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                startActivity(new Intent(Tutorial_Activity.this,Home_Activity.class));
            }
        });
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                startActivity(new Intent(Tutorial_Activity.this,Tutorial_Activity.class));
            }
        });
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
            dialog_link = (TextView) customDialog.findViewById(R.id.message);
            dialog_link.setText("登出後將無法準確紀錄您刷牙的狀況，但您仍會收到BrushGo的提醒。");
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
                    startActivity(new Intent(Tutorial_Activity.this,MainActivity.class));

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