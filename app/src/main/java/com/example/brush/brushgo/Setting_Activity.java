package com.example.brush.brushgo;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.brush.brushgo.R.id.Home;

//import android.icu.text.SimpleDateFormat;
//import android.icu.util.Calendar;

/**
 * Created by swlab on 2017/5/5.
 */

public class Setting_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button menu;
    private Button brush;
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
    private Button alarm_a;
    private Button alarm_b;
    private Button alarm_c;
    private Button alarm_d;
    private Button alarm_e;
    private Button[] alarms=new Button[5];
    private Switch switch_a;
    private Switch switch_b;
    private Switch switch_c;
    private Switch switch_d;
    private Switch switch_e;
    private Switch[] switches=new Switch[5];
    private Calendar now;
    private Calendar calendar_a;
    private Calendar calendar_b;
    private Calendar calendar_c;
    private Calendar calendar_d;
    private Calendar calendar_e;
    private Calendar[] calendars=new Calendar[5];
    private String time_a;
    private String time_b;
    private String time_c;
    private String time_d;
    private String time_e;
    private String [] times=new String[]{time_a,time_b,time_c,time_d,time_e};
    private String[] time_a_array;
    private String [] time_b_array;
    private String[] time_c_array;
    private String [] time_d_array;
    private SimpleDateFormat simpleDateFormat;
    private DecimalFormat decimalFormat;
    private AlarmManager manager;
    private Intent intent;
    private PendingIntent pendingIntent;
    private FirebaseAuth auth;
    private Firebase firebaseRef;
    private Firebase touchedRef;
    private Firebase alarm_Ref_a;
    private Firebase alarm_Ref_b;
    private Firebase alarm_Ref_c;
    private Firebase alarm_Ref_d;
    private Firebase alarm_Ref_e;
    private Firebase[] alarm_refs={alarm_Ref_a,alarm_Ref_b,alarm_Ref_c,alarm_Ref_d,alarm_Ref_e};
    private Firebase timeRef;
    private Firebase reminderRef;
    private String nowTime;
    private Boolean isdoubleClick=false;

    private Dialog customDialog;
    private Button dialog_confirm;
    private Button dialog_cancel;
    private TextView dialog_title;
    private TextView dialog_message;

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
        recordTouched();
        processView();
        setValue();
        processControl();
    }

    private void recordTouched() {
        firebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/");
        auth= FirebaseAuth.getInstance();
        touchedRef =firebaseRef.child("touched").child(auth.getCurrentUser().getUid()).child("setting");
        nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        DB_recordTouched touched=new DB_recordTouched(touchedRef,nowTime);
        touched.pushValue();
    }

    private void setValue() {
        for(int i=0;i<alarm_refs.length;i++)
            alarm_refs[i]=new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/alarm/"+i);

        timeRef = new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/time");
        reminderRef = new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/reminder");
        for(int i=0;i<alarm_refs.length;i++) {
            final int finalI = i;
            alarm_refs[i].addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    times[finalI] = dataSnapshot.getValue(String.class);
                    if (times[finalI] == null)
                        alarms[finalI].setText("    尚未設定");
                    else
                        alarms[finalI].setText("     " + times[finalI].trim());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
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
        NavigationView navigateionView=(NavigationView) findViewById(R.id.nav_setting);
        navigateionView.setNavigationItemSelectedListener(Setting_Activity.this);
        auth= FirebaseAuth.getInstance();
        firebaseRef = new Firebase("https://brushgo-67813.firebaseio.com");
        menu=(Button) findViewById(R.id.btn_menu);
        brush=(Button)findViewById(R.id.btn_brush);
        switch_a =(Switch)findViewById(R.id.switch_1);
        switch_b =(Switch)findViewById(R.id.switch_2);
        switch_c =(Switch)findViewById(R.id.switch_3);
        switch_d =(Switch)findViewById(R.id.switch_4);
        switch_e =(Switch)findViewById(R.id.switch_5);
        switches= new Switch[]{switch_a, switch_b, switch_c, switch_d,switch_e};
        alarm_a =(Button) findViewById(R.id.btn_a);
        alarm_b =(Button) findViewById(R.id.btn_b);
        alarm_c =(Button) findViewById(R.id.btn_c);
        alarm_d =(Button) findViewById(R.id.btn_d);
        alarm_e =(Button) findViewById(R.id.btn_e);
        alarms=new Button[]{alarm_a,alarm_b,alarm_c,alarm_d,alarm_e};
        now = Calendar.getInstance();
        nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        calendar_a=Calendar.getInstance();
        calendar_b=Calendar.getInstance();
        calendar_c=Calendar.getInstance();
        calendar_d=Calendar.getInstance();
        calendar_e=Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
        decimalFormat=new DecimalFormat("00");
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
        intent = new Intent(Setting_Activity.this, AlarmNotificationReceiver.class);
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
                startActivity(new Intent(Setting_Activity.this,Home_Activity.class));
            }
        });
        for(int i=0;i<alarms.length;i++)
        {
            final int finalI = i;
            alarms[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(finalI);
                }
            });
        }

        for(int i=0;i<switches.length;i++)
        {
            final int finalI = i;
            switches[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(switches[finalI].isChecked())
                        alarms[finalI].setClickable(true);
                    else{
                        alarms[finalI].setClickable(false);
                        times[finalI]=null;
                        alarm_refs[finalI].setValue(null);
                        pendingIntent = PendingIntent.getBroadcast(Setting_Activity.this, finalI, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        manager.cancel(pendingIntent);
                    }
                }
            });
        }
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
        if(id==0) {
            if(time_a !=null) {
                time_a_array = time_a.split(":");
                return new TimePickerDialog(Setting_Activity.this, timePicker_a, Integer.parseInt(time_a_array[0]), Integer.parseInt(time_a_array[1]), true);
            }
            else
                return new TimePickerDialog(Setting_Activity.this, timePicker_a,0,0, true);
        }
        if(id==1) {
            if(time_b !=null) {
                time_b_array = time_b.split(":");
                return new TimePickerDialog(Setting_Activity.this, timePicker_b, Integer.parseInt(time_b_array[0]), Integer.parseInt(time_b_array[1]), true);
            }
            else
                return new TimePickerDialog(Setting_Activity.this, timePicker_b, 0,0, true);
        }
        if(id==2){
            if(time_c !=null) {
                time_c_array = time_c.split(":");
                return new TimePickerDialog(Setting_Activity.this, timePicker_c, Integer.parseInt(time_c_array[0]), Integer.parseInt(time_c_array[1]), true);
            }
            else
                return new TimePickerDialog(Setting_Activity.this, timePicker_c, 0,0, true);

        }
        if(id==3){
            if(time_d !=null) {
                time_d_array = time_d.split(":");
                return new TimePickerDialog(Setting_Activity.this, timePicker_d, Integer.parseInt(time_d_array[0]), Integer.parseInt(time_d_array[1]), true);
            }
            else
                return new TimePickerDialog(Setting_Activity.this, timePicker_d, 0,0, true);

        }
        if(id==4){
            if(time_d !=null) {
                time_d_array = time_d.split(":");
                return new TimePickerDialog(Setting_Activity.this, timePicker_e, Integer.parseInt(time_d_array[0]), Integer.parseInt(time_d_array[1]), true);
            }
            else
                return new TimePickerDialog(Setting_Activity.this, timePicker_e, 0,0, true);

        }
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener timePicker_a =new TimePickerDialog.OnTimeSetListener(){
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar_a.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
            calendar_a.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar_a.set(Calendar.MINUTE, minute);
            calendar_a.set(Calendar.SECOND, 0);
            calendar_a.set(Calendar.MILLISECOND, 0);
            time_a = simpleDateFormat.format(calendar_a.getTime()).trim();
            alarm_a.setText("      "+ time_a);
            view.setCurrentHour(hourOfDay);
            alarmManager(calendar_a,0);
            alarm_refs[0].setValue(time_a);
            Toast.makeText(Setting_Activity.this, calendar_a.getTime()+"",Toast.LENGTH_LONG).show();
        }
    };

    protected TimePickerDialog.OnTimeSetListener timePicker_b =new TimePickerDialog.OnTimeSetListener(){
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar_b.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
            calendar_b.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar_b.set(Calendar.MINUTE, minute);
            calendar_b.set(Calendar.SECOND, 0);
            calendar_b.set(Calendar.MILLISECOND, 0);
            time_b = simpleDateFormat.format(calendar_b.getTime()).trim();
            alarm_b.setText("     "+ time_b);
            view.setCurrentHour(hourOfDay);
            view.setCurrentMinute(minute);
            alarmManager(calendar_b,1);
            alarm_refs[1].setValue(time_b);
            Toast.makeText(Setting_Activity.this, calendar_b.getTime()+"",Toast.LENGTH_LONG).show();
        }
    };

    protected TimePickerDialog.OnTimeSetListener timePicker_c =new TimePickerDialog.OnTimeSetListener(){
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar_c.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
            calendar_c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar_c.set(Calendar.MINUTE, minute);
            calendar_c.set(Calendar.SECOND, 0);
            calendar_c.set(Calendar.MILLISECOND, 0);
            time_c = simpleDateFormat.format(calendar_c.getTime()).trim();
            alarm_c.setText("     "+ time_c);
            view.setCurrentHour(hourOfDay);
            view.setCurrentMinute(minute);
            alarmManager(calendar_c,0);
            alarm_refs[2].setValue(time_c);
            Toast.makeText(Setting_Activity.this, calendar_c.getTime()+"",Toast.LENGTH_LONG).show();
        }
    };

    protected TimePickerDialog.OnTimeSetListener timePicker_d =new TimePickerDialog.OnTimeSetListener(){
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar_d.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
            calendar_d.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar_d.set(Calendar.MINUTE, minute);
            calendar_d.set(Calendar.SECOND, 0);
            calendar_d.set(Calendar.MILLISECOND, 0);
            time_d = simpleDateFormat.format(calendar_d.getTime()).trim();
            alarm_d.setText("      "+ time_d);
            view.setCurrentHour(hourOfDay);
            view.setCurrentMinute(minute);
            alarmManager(calendar_d,0);
            alarm_refs[3].setValue(time_d);
            Toast.makeText(Setting_Activity.this, calendar_d.getTime()+"",Toast.LENGTH_LONG).show();
        }
    };

    protected TimePickerDialog.OnTimeSetListener timePicker_e =new TimePickerDialog.OnTimeSetListener(){
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar_e.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
            calendar_e.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar_e.set(Calendar.MINUTE, minute);
            calendar_e.set(Calendar.SECOND, 0);
            calendar_e.set(Calendar.MILLISECOND, 0);
            time_e = simpleDateFormat.format(calendar_e.getTime()).trim();
            alarm_e.setText("      "+ time_e);
            view.setCurrentHour(hourOfDay);
            view.setCurrentMinute(minute);
            alarmManager(calendar_e,0);
            alarm_refs[4].setValue(time_e);
            Toast.makeText(Setting_Activity.this, calendar_e.getTime()+"",Toast.LENGTH_LONG).show();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void alarmManager(Calendar calendarTime,int id) {
        if(id==0)
            intent.putExtra("contentText","早安~起床後別忘記打開BrushGo刷刷牙喔~");
        else if(id==1)
            intent.putExtra("contentText","早餐時間到囉~祝您用餐愉快,同時別忘了用完餐後要記得刷刷牙維持口腔清潔唷:)");
        else if(id==2)
            intent.putExtra("contentText","午餐時間到囉~祝您用餐愉快,同時別忘了用完餐後要記得刷刷牙維持口腔清潔唷:)");
        else if(id==3)
            intent.putExtra("contentText","晚餐時間到囉~祝您用餐愉快,同時別忘了用完餐後要記得刷刷牙維持口腔清潔唷:)");
        else if(id==4)
            intent.putExtra("contentText","睡前小叮嚀,睡覺之前別忘記打開BrushGo刷刷牙喔~");
        pendingIntent = PendingIntent.getBroadcast(this, id, intent, pendingIntent.FLAG_UPDATE_CURRENT);
        if(calendarTime.before(now)) {
            calendarTime.add(Calendar.DATE,1); //如果時間早於現在就加一天
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendarTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            calendarTime.add(Calendar.DATE,-1); //把加上去的日期扣回來
        }
        else
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendarTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
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
                    startActivity(new Intent(Setting_Activity.this,MainActivity.class));
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