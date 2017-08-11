package com.example.brush.brushgo;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**

 * Created by pig98520 on 2017/7/14.

 */

public class AlarmRebootReceiver extends BroadcastReceiver {
    private AlarmManager alarmManager;
    private Intent alarmIntent;
    private PendingIntent pendingIntent;
    private FirebaseAuth auth;
    private Firebase morningRef;
    private Firebase eveningRef;
    private Firebase reminderRef;
    private Firebase lastBrushtimeRef;
    private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
    private Calendar calendar;
    private Date m_time;
    private Date e_time;
    private Long currentTimemillisecond;
    private int reminderTime;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Firebase.setAndroidContext(context);
        alarmManager= (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        auth= FirebaseAuth.getInstance();
        morningRef =new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/morning");
        eveningRef =new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/evening");
        reminderRef=new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/reminder");
        lastBrushtimeRef =new Firebase("https://brushgo-67813.firebaseio.com/profile/"+auth.getCurrentUser().getUid()+"/last_brush_time");
        alarmIntent =new Intent(context,AlarmNotificationReceiver.class);
        calendar=Calendar.getInstance();

        morningRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    try {
                        m_time= sdf.parse(dataSnapshot.getValue().toString());
                        m_time.setYear(calendar.getTime().getYear());
                        m_time.setMonth(calendar.getTime().getMonth());
                        m_time.setDate(calendar.getTime().getDate());
                        pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, pendingIntent.FLAG_UPDATE_CURRENT);
                        if(m_time.before(Calendar.getInstance().getTime())) {
                            m_time.setDate(m_time.getDate()+1);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, m_time.getTime(), AlarmManager.INTERVAL_DAY,pendingIntent);
                            m_time.setDate(m_time.getDate()-1);
                        }
                        else
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, m_time.getTime(),AlarmManager.INTERVAL_DAY, pendingIntent);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        eveningRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    try {
                        e_time= sdf.parse(dataSnapshot.getValue().toString());
                        e_time.setYear(calendar.getTime().getYear());
                        e_time.setMonth(calendar.getTime().getMonth());
                        e_time.setDate(calendar.getTime().getDate());
                        pendingIntent = PendingIntent.getBroadcast(context, 1, alarmIntent, pendingIntent.FLAG_UPDATE_CURRENT);
                        if(e_time.before(Calendar.getInstance().getTime())) {
                            e_time.setDate(e_time.getDate()+1);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, e_time.getTime(),AlarmManager.INTERVAL_DAY, pendingIntent);
                            e_time.setDate(e_time.getDate()-1);
                        }
                        else
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, e_time.getTime(),AlarmManager.INTERVAL_DAY, pendingIntent);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        lastBrushtimeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    currentTimemillisecond = Long.parseLong(dataSnapshot.getValue().toString());
                    reminderRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                reminderTime=Integer.parseInt(dataSnapshot.getValue().toString());
                                alarmIntent.putExtra("contentTitle", "打開BrushGo吧");
                                alarmIntent.putExtra("contentText", "已經" + reminderTime + "天沒使用BrushGo刷牙囉:(");
                                pendingIntent = PendingIntent.getBroadcast(context, (int) (long) currentTimemillisecond, alarmIntent, pendingIntent.FLAG_UPDATE_CURRENT);
                                alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTimemillisecond + AlarmManager.INTERVAL_DAY * reminderTime, pendingIntent);
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