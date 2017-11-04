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
    private Firebase alarm_a_Ref;
    private Firebase alarm_b_Ref;
    private Firebase alarm_c_Ref;
    private Firebase alarm_d_Ref;
    private Firebase[] alarm_refs ={alarm_a_Ref,alarm_b_Ref,alarm_c_Ref,alarm_d_Ref};
    private Firebase reminderRef;
    private Firebase lastBrushtimeRef;
    private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
    private Calendar calendar;
    private Date a_time;
    private Date b_time;
    private Date c_time;
    private Date d_time;
    private Date[] times={a_time,b_time,c_time,d_time};
    private Long currentTimemillisecond;
    private int reminderTime;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Firebase.setAndroidContext(context);
        alarmManager= (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        auth= FirebaseAuth.getInstance();
        for(int i=0;i<4;i++)
            alarm_refs[i]=new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/alarm/"+i);


        reminderRef=new Firebase("https://brushgo-67813.firebaseio.com/setting/"+auth.getCurrentUser().getUid()+"/reminder");
        lastBrushtimeRef =new Firebase("https://brushgo-67813.firebaseio.com/profile/"+auth.getCurrentUser().getUid()+"/last_brush_time");
        alarmIntent =new Intent(context,AlarmNotificationReceiver.class);
        calendar=Calendar.getInstance();

        for(int i=0;i<4;i++)
        {
            final int finalI = i;
            alarm_refs[i].addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        try {
                            times[finalI] = sdf.parse(dataSnapshot.getValue().toString());
                            times[finalI].setYear(calendar.getTime().getYear());
                            times[finalI].setMonth(calendar.getTime().getMonth());
                            times[finalI].setDate(calendar.getTime().getDate());
                            pendingIntent = PendingIntent.getBroadcast(context, finalI, alarmIntent, pendingIntent.FLAG_UPDATE_CURRENT);
                            if(times[finalI].before(Calendar.getInstance().getTime())) {
                                times[finalI].setDate(times[finalI].getDate()+1);
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, times[finalI].getTime(), AlarmManager.INTERVAL_DAY,pendingIntent);
                                times[finalI].setDate(times[finalI].getDate()-1);
                            }
                            else
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, times[finalI].getTime(),AlarmManager.INTERVAL_DAY, pendingIntent);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }

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