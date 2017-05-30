package com.example.brush.brushgo;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

/**
 * Created by swlab on 2017/5/5.
 */

public class Setting_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button menu;
    private Button save;
    private RadioButton twominutes;
    private RadioButton threeminutes;
    private RadioButton fourminutes;
    private RadioButton oneday;
    private RadioButton threedays;
    private RadioButton oneweek;
    private DrawerLayout drawer;
    private int time;
    private int remider;
    private Button morning;
    private Button evening;
    private TextView m_alarm;
    private Calendar m_calendar;
    private String m_time;
    private TextView e_alarm;
    private Calendar e_calendar;
    private String e_time;
    private SimpleDateFormat formatter;
    private FirebaseAuth auth;
    private Firebase myFirebaseRef;
    private Firebase morningRef;
    private Firebase eveningRef;
    private Firebase userRef;
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        processView();
        setValue();
        processControl();
    }

    private void setValue() {
        morningRef = new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/morning");
        eveningRef = new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/evening");
        morningRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                m_time=dataSnapshot.getValue(String.class);
                if(m_time==null)
                    m_alarm.setText("AM 尚未設定");
                else
                m_alarm.setText("AM"+m_time);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        eveningRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                e_time=dataSnapshot.getValue(String.class);
                if(e_time==null)
                    e_alarm.setText("PM 尚未設定");
                else
                e_alarm.setText("PM"+e_time);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void processView() {
        NavigationView navigateionView=(NavigationView) findViewById(R.id.nav_information);
        navigateionView.setNavigationItemSelectedListener(Setting_Activity.this);
        auth= FirebaseAuth.getInstance();
        myFirebaseRef = new Firebase("https://brushgo-67813.firebaseio.com");
        userRef = myFirebaseRef.child("setting").child(auth.getCurrentUser().getUid().trim());
        menu=(Button) findViewById(R.id.btn_menu);
        save=(Button) findViewById(R.id.btn_save);
        morning=(Button) findViewById(R.id.btn_morning);
        evening=(Button) findViewById(R.id.btn_evening);
        m_alarm=(TextView) findViewById(R.id.txt_morning);
        e_alarm=(TextView) findViewById(R.id.txt_evening);
        e_calendar= Calendar.getInstance();
        m_calendar= Calendar.getInstance();
        formatter = new SimpleDateFormat(" HH:mm", Locale.TAIWAN);
        twominutes =(RadioButton)findViewById(R.id.rdb_two);
        threeminutes =(RadioButton)findViewById(R.id.rdb_three);
        fourminutes =(RadioButton)findViewById(R.id.rdb_four);
        oneday=(RadioButton)findViewById(R.id.rdb_oneday);
        threedays=(RadioButton)findViewById(R.id.rdb_threedays);
        oneweek=(RadioButton)findViewById(R.id.rdb_week);
        drawer=(DrawerLayout)findViewById(R.id.drawerLayout);
    }

    private void processControl() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                checkTime();
                checkRemider();
                updateUser();
            }
        });
        morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });
        evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(2);
            }
        });
    }


    protected Dialog onCreateDialog(int id){
        if(id==1)
            return new TimePickerDialog(Setting_Activity.this,morningTimePickerListner, 0, 0,false);
        if(id==2)
            return new TimePickerDialog(Setting_Activity.this,eveningTimePickerListner, 0,0,false);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener morningTimePickerListner=new TimePickerDialog.OnTimeSetListener(){
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            m_calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            m_calendar.set(Calendar.MINUTE, minute);
            m_calendar.set(Calendar.SECOND, 0);
            m_calendar.set(Calendar.MILLISECOND, 0);
            m_alarm.setText("AM"+formatter.format(m_calendar.getTime()));
            startAlarm(m_calendar);
            Toast.makeText(Setting_Activity.this,m_calendar.getTime()+"",Toast.LENGTH_LONG).show();
        }
    };
    protected TimePickerDialog.OnTimeSetListener eveningTimePickerListner=new TimePickerDialog.OnTimeSetListener(){
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            e_calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            e_calendar.set(Calendar.MINUTE, minute);
            e_calendar.set(Calendar.SECOND, 0);
            e_alarm.setText("PM"+formatter.format(e_calendar.getTime()));
            startAlarm(e_calendar);

            Toast.makeText(Setting_Activity.this,e_calendar.getTime()+"",Toast.LENGTH_LONG).show();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startAlarm(Calendar calendarTime) {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;
        myIntent = new Intent(Setting_Activity.this, AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendarTime.getTimeInMillis(), pendingIntent);
    }
    private int checkTime() {
        if(twominutes.isChecked())
            time=2;
        else if(threeminutes.isChecked())
            time=3;
        else if(fourminutes.isChecked())
            time=4;
        return time;
    }

    private int checkRemider() {
        if(oneday.isChecked())
            remider=1;
        else if(threedays.isChecked())
            remider=3;
        else if(oneweek.isChecked())
            remider=7;
        return remider;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateUser() {
        DB_Setting data = new DB_Setting(auth.getCurrentUser().getEmail(),time*60,remider, formatter.format(m_calendar.getTime()),formatter.format(e_calendar.getTime()));
        userRef.setValue(data);
        Toast.makeText(Setting_Activity.this,  "資料已儲存", Toast.LENGTH_SHORT).show();
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
            intent.setClass(this,Setting_Activity.class);
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
                    intent.setClass(Setting_Activity.this,MainActivity.class);
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