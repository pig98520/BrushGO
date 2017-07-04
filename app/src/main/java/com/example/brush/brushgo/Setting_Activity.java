package com.example.brush.brushgo;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//import android.icu.text.SimpleDateFormat;
//import android.icu.util.Calendar;

/**
 * Created by swlab on 2017/5/5.
 */

public class Setting_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button menu;
    private RadioButton twominutes;
    private RadioButton threeminutes;
    private RadioButton fourminutes;
    private RadioButton oneday;
    private RadioButton threedays;
    private RadioButton oneweek;
    private RadioGroup rg_time;
    private RadioGroup rg_reminder;
    private DrawerLayout drawer;
    private int time;
    private int reminder;
    private Button morning;
    private Button evening;
    private Switch alarm_switch;
    private Calendar now;
    private TextView m_alarm;
    private Calendar m_calendar;
    private String m_time;
    private String[] m_array;
    private TextView e_alarm;
    private Calendar e_calendar;
    private String e_time;
    private String [] e_array;
    private SimpleDateFormat formatter;
    private AlarmManager manager;
    private Intent intent;
    private PendingIntent pendingIntent;
    private FirebaseAuth auth;
    private Firebase myFirebaseRef;
    private Firebase morningRef;
    private Firebase eveningRef;
    private Firebase timeRef;
    private Firebase reminderRef;
    private Boolean isdoubleClick=false;

    @Override
    public void onBackPressed() {
        if(!isdoubleClick)
        {
            Toast.makeText(Setting_Activity.this,"雙擊以退出",Toast.LENGTH_LONG).show();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
        timeRef = new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/time");
        reminderRef = new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/reminder");

        morningRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot m_dataSnapshot) {
                m_time=m_dataSnapshot.getValue(String.class);
                if(m_time==null)
                    m_alarm.setText("AM 尚未設定");
                else
                m_alarm.setText("AM "+m_time.trim());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        eveningRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot e_dataSnapshot) {
                e_time=e_dataSnapshot.getValue(String.class);
                if(e_time==null)
                    e_alarm.setText("PM 尚未設定");
                else
                e_alarm.setText("PM "+e_time.trim());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        timeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot t_dataSnapshot) {
                time=t_dataSnapshot.getValue(int.class);
                if(time==240)
                    fourminutes.setChecked(true);
                else if(time==180)
                    threeminutes.setChecked(true);
                else if(time==120)
                    twominutes.setChecked(true);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        reminderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot r_dataSnapshot) {
            reminder=r_dataSnapshot.getValue(int.class);
                if(reminder==7)
                    oneweek.setChecked(true);
                else if(reminder==3)
                    threedays.setChecked(true);
                else if(reminder==1)
                    oneday.setChecked(true);
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
        menu=(Button) findViewById(R.id.btn_menu);
        alarm_switch=(Switch)findViewById(R.id.alarm_switch);
        morning=(Button) findViewById(R.id.btn_morning);
        evening=(Button) findViewById(R.id.btn_evening);
        m_alarm=(TextView) findViewById(R.id.txt_morning);
        e_alarm=(TextView) findViewById(R.id.txt_evening);
        now = Calendar.getInstance();
        e_calendar= Calendar.getInstance();
        m_calendar= Calendar.getInstance();
        formatter = new SimpleDateFormat(" HH:mm");
        formatter.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
        rg_time=(RadioGroup)findViewById(R.id.rg_time);
        rg_reminder=(RadioGroup)findViewById(R.id.rg_remider);
        twominutes =(RadioButton)findViewById(R.id.rdb_two);
        threeminutes =(RadioButton)findViewById(R.id.rdb_three);
        fourminutes =(RadioButton)findViewById(R.id.rdb_four);
        oneday=(RadioButton)findViewById(R.id.rdb_oneday);
        threedays=(RadioButton)findViewById(R.id.rdb_threedays);
        oneweek=(RadioButton)findViewById(R.id.rdb_week);
        drawer=(DrawerLayout)findViewById(R.id.drawerLayout);
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    }

    private void processControl() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
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
        alarm_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(alarm_switch.isChecked())
                {
                    morning.setVisibility(View.VISIBLE);
                    m_alarm.setVisibility(View.VISIBLE);
                    evening.setVisibility(View.VISIBLE);
                    e_alarm.setVisibility(View.VISIBLE);
                }
                else
                {
                    morning.setVisibility(View.INVISIBLE);
                    m_alarm.setVisibility(View.INVISIBLE);
                    evening.setVisibility(View.INVISIBLE);
                    e_alarm.setVisibility(View.INVISIBLE);
                    m_time=null;
                    e_time=null;
                    eveningRef.setValue(null);
                    morningRef.setValue(null);
                    /*if(pendingIntent!=null)
                    {
                        try {
                            manager.cancel(pendingIntent);
                        } catch (Exception e) {
                            Log.e("Fail", "AlarmManager update was not canceled. " + e.toString());
                        }
                    }*/
                }
            }
        });
        rg_reminder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if(oneday.isChecked())
                reminder =1;
            if(threedays.isChecked())
                reminder =3;
            if(oneweek.isChecked())
                reminder =7;
            reminderRef.setValue(reminder);
            }
        });
        rg_time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if(twominutes.isChecked())
                time=120;
            if(threeminutes.isChecked())
                time=180;
            if(fourminutes.isChecked())
                time=240;
            timeRef.setValue(time);
            }
        });
    }


    protected Dialog onCreateDialog(int id){
        if(id==1) {
            if(m_time!=null) {
                m_array = m_time.split(":");
                return new TimePickerDialog(Setting_Activity.this, morningTimePickerListener, Integer.parseInt(m_array[0]), Integer.parseInt(m_array[1]), true);
            }
            else
                return new TimePickerDialog(Setting_Activity.this, morningTimePickerListener,0,0, true);
        }
        if(id==2) {
            if(e_time!=null) {
                e_array = e_time.split(":");
                return new TimePickerDialog(Setting_Activity.this, eveningTimePickerListener, Integer.parseInt(e_array[0]), Integer.parseInt(e_array[1]), true);
            }
            else
                return new TimePickerDialog(Setting_Activity.this, eveningTimePickerListener, 0,0, true);
        }
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener morningTimePickerListener =new TimePickerDialog.OnTimeSetListener(){
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            m_calendar.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
            if(hourOfDay>12)
                hourOfDay-=12;
            m_calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            m_calendar.set(Calendar.MINUTE, minute);
            m_calendar.set(Calendar.SECOND, 0);
            m_calendar.set(Calendar.MILLISECOND, 0);
            m_time=formatter.format(m_calendar.getTime()).trim();
            m_alarm.setText("AM "+m_time);
            view.setCurrentHour(hourOfDay);
            view.setCurrentMinute(minute);
            morning_alarm(m_calendar);
            morningRef.setValue(m_time);
            Toast.makeText(Setting_Activity.this,m_calendar.getTime()+"",Toast.LENGTH_LONG).show();
        }
    };

    protected TimePickerDialog.OnTimeSetListener eveningTimePickerListener =new TimePickerDialog.OnTimeSetListener(){
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            e_calendar.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
            if(hourOfDay<12)
                hourOfDay+=12;
            e_calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            e_calendar.set(Calendar.MINUTE, minute);
            e_calendar.set(Calendar.SECOND, 0);
            e_calendar.set(Calendar.MILLISECOND, 0);
            e_time=formatter.format(e_calendar.getTime()).trim();
            e_alarm.setText("PM "+e_time);
            view.setCurrentHour(hourOfDay);
            view.setCurrentMinute(minute);
            evening_alarm(e_calendar);
            eveningRef.setValue(e_time);
            Toast.makeText(Setting_Activity.this,e_calendar.getTime()+"",Toast.LENGTH_LONG).show();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void morning_alarm(Calendar calendarTime) {
        intent = new Intent(Setting_Activity.this, AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if(calendarTime.before(now)) {
            calendarTime.add(Calendar.DATE, 1);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendarTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            calendarTime.add(Calendar.DATE,-1);
        }
        else
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendarTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        //alarmManager.set(AlarmManager.RTC_WAKEUP,calendarTime.getTimeInMillis(), pendingIntent);
    }

    private void evening_alarm(Calendar calendarTime) {
        intent = new Intent(Setting_Activity.this, AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if(calendarTime.before(now)) {
            calendarTime.add(Calendar.DATE, 1);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendarTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            calendarTime.add(Calendar.DATE,-1);
        }
        else
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendarTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        //alarmManager.set(AlarmManager.RTC_WAKEUP,calendarTime.getTimeInMillis(), pendingIntent);
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