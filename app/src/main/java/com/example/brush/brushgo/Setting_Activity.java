package com.example.brush.brushgo;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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

/**
 * Created by swlab on 2017/5/5.
 */

public class Setting_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button menu;
    private Button save;
    private RadioButton threeminutes;
    private RadioButton fourminutes;
    private RadioButton fiveminutes;
    private RadioButton oneday;
    private RadioButton threedays;
    private RadioButton oneweek;
    private DrawerLayout drawer;
    private int time;
    private int remider;
    private Button morning;
    private Button evening;
    private TextView m_alarm;
    private TextView e_alarm;
    private String m_time;
    private String e_time;
    private int m_hour;
    private int m_min;
    private int e_hour;
    private int e_min;
    private FirebaseAuth auth;
    private Firebase myFirebaseRef;
    private Firebase morningRef;
    private Firebase eveningRef;
    private Firebase userRef;
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
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                m_time=dataSnapshot.getValue(String.class);
                    m_alarm.setText(m_time);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        eveningRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                e_time=dataSnapshot.getValue(String.class);
                e_alarm.setText(e_time);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
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
        threeminutes=(RadioButton)findViewById(R.id.rdb_three);
        fourminutes=(RadioButton)findViewById(R.id.rdb_four);
        fiveminutes=(RadioButton)findViewById(R.id.rdb_five);
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
            return new TimePickerDialog(Setting_Activity.this,morningTimePickerListner, m_hour, m_min,false);
        if(id==2)
            return new TimePickerDialog(Setting_Activity.this,eveningTimePickerListner, e_hour, e_min,false);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener morningTimePickerListner=new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            m_hour =hourOfDay;
            m_min =minute;
            m_time=m_hour+":"+m_min;
            m_alarm.setText(m_time);
        }
    };
    protected TimePickerDialog.OnTimeSetListener eveningTimePickerListner=new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            e_hour =hourOfDay;
            e_min =minute;
            e_time=e_hour+":"+e_min;
            e_alarm.setText(e_time);
        }
    };

    private int checkTime() {
        if(threeminutes.isChecked())
            time=3;
        else if(fourminutes.isChecked())
            time=4;
        else if(fiveminutes.isChecked())
            time=5;
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

    private void updateUser() {
        DB_Setting data = new DB_Setting(auth.getCurrentUser().getEmail(),time*60,remider, m_time,e_time);
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
            auth.signOut();
            Intent intent=new Intent();
            intent.setClass(this,MainActivity.class);
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}