package com.example.administrator.rollcall_10.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.auto_add.AutoAdd_BLE_MainActivity;
import com.example.administrator.rollcall_10.main.MainActivity;

/**
 * Created by Administrator on 2016/9/2.
 */
public class Successful_NotificationDisplayService extends Service {
    NotificationManager nManager;

    final int NOTIFICATION_ID = 16;
    public Successful_NotificationDisplayService() {
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {



        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Bundle bundle = intent.getExtras();
        int List_size=  bundle.getInt("List_size");
        String List_name=  bundle.getString("List_name");



        displayNotification("成功加入"+List_size+"筆資料", "已成功人數將加入到"+List_name+"中");
        stopSelf();


        return super.onStartCommand(intent, flags, startId);
    }

    private void displayNotification(String title, String text){

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.mipmap.tick512_2)
                //.setLargeIcon(BitmapFactory.decodeResource(R.drawable.xyz))
                .setColor(getResources().getColor(R.color.color_fff))
                .setVibrate(new long[]{0, 200, 200, 200})
                //.setSound()
//                .setLights(Color.WHITE, 1000, 5000)
                //.setWhen(System.currentTimeMillis())

                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text));

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification.build());
    }





}