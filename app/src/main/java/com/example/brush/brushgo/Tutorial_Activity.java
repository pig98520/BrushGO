package com.example.brush.brushgo;

import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
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
        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_information);
        navigationView.setNavigationItemSelectedListener(Tutorial_Activity.this);
        drawer=(DrawerLayout)findViewById(R.id.drawerLayout);
        auth= FirebaseAuth.getInstance();
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        customAdapter=new Tutorial_Adapter(this);
        viewPager.setAdapter(customAdapter);
    }

    private void processControl() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(position==customAdapter.getCount()-1) {
                    finisnDialog();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void finisnDialog() {
        AlertDialog.Builder finishDialog=new AlertDialog.Builder(this);
        finishDialog.setCancelable(false);
        finishDialog.setTitle("教學結束");
        DialogInterface.OnClickListener confirmClick =new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent();
                intent.setClass(Tutorial_Activity.this,Home_Activity.class);
                startActivity(intent);
            }
        };
        DialogInterface.OnClickListener cancelClick =new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        };
        finishDialog.setNeutralButton("開始刷牙",confirmClick);
        finishDialog.setNegativeButton("重看一次",cancelClick);
        finishDialog.show();
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
                    intent.setClass(Tutorial_Activity.this,MainActivity.class);
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
}