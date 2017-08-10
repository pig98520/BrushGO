package com.example.brush.brushgo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.text.DecimalFormat;

/**
 * Created by pig98520 on 2017/5/28.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver{
    private DecimalFormat decimalFormat = new DecimalFormat("00");
    private String contentTitle="BrushGo";
    private String contentText ="刷牙時間到了喔,快點打開BrushGo來刷牙吧";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getStringExtra("contentTitle")!=null)
            contentTitle=intent.getStringExtra("contentTitle");

        if(intent.getStringExtra("contentText")!=null)
            contentText =intent.getStringExtra("contentText");

        Intent notifiIntent =new Intent(context,Home_Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifiIntent, PendingIntent.FLAG_UPDATE_CURRENT); //點擊後回到APP

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.tooth_icon)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                /* .setContentInfo("推播資訊")*/
                .setContentIntent(pendingIntent) //點擊後回到APP
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND);

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }
}
