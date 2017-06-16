package com.example.brush.brushgo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by pig98520 on 2017/5/28.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("刷牙時間到了~")
                .setContentText("快點打開BrushGo來刷牙吧~")
                //.setContentInfo("推播資訊")
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND);

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }
}
