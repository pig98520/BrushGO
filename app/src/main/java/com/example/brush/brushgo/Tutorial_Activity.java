package com.example.brush.brushgo;

import android.app.Dialog;
import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by swlab on 2017/5/5.
 */

public class Tutorial_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FirebaseAuth auth;
    private Boolean isdoubleClick=false;
    private Tutorial_Adapter customAdapter;
    private ViewPager viewPager;
    private int[] sliderView=new int[]{R.id.slider_1,R.id.slider_2,R.id.slider_3,R.id.slider_4};
    private ImageView[] slider=new ImageView[sliderView.length];
    private Dialog customDialog;
    private Button dialog_confirm;
    private Button dialog_cancel;
    private TextView dialog_title;
    private TextView dialog_message;

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
        processView();
        processControl();
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void processView() {
        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_tutorial);
        navigationView.setNavigationItemSelectedListener(Tutorial_Activity.this);
        drawer=(DrawerLayout)findViewById(R.id.drawerLayout);
        auth= FirebaseAuth.getInstance();
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        customAdapter=new Tutorial_Adapter(this);
        viewPager.setAdapter(customAdapter);
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

    private void finisnDialog() {
        customDialog =new Dialog(this,R.style.DialogCustom);
        customDialog.setContentView(R.layout.custom_dialog_two);
        customDialog.setCancelable(false);
        dialog_title = (TextView) customDialog.findViewById(R.id.title);
        dialog_title.setText("教學結束");
        dialog_message = (TextView) customDialog.findViewById(R.id.message);
        dialog_message.setText("馬上使用BrushGo刷牙吧！");
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

        if(id==R.id.Home)
        {
            Intent intent=new Intent();
            intent.setClass(this,Home_Activity.class);
            startActivity(intent);
            finish();
        }
        else if(id==R.id.Video)
        {
            Intent intent=new Intent();
            intent.setClass(this,Video_Activity.class);
            startActivity(intent);
            finish();
        }
        else if(id==R.id.Information)
        {
            Intent intent=new Intent();
            intent.setClass(this,Information_Activity.class);
            startActivity(intent);
            finish();
        }
        else if(id==R.id.Tutorial)
        {
            Intent intent=new Intent();
            intent.setClass(this,Tutorial_Activity.class);
            startActivity(intent);
            finish();
        }
        else if(id==R.id.Setting)
        {
            Intent intent=new Intent();
            intent.setClass(this,Setting_Activity.class);
            startActivity(intent);
            finish();
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
                    startActivity(new Intent(Tutorial_Activity.this,MainActivity.class));
                    finish();
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