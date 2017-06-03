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
import android.util.Log;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.auto_add.AutoAdd_BLE_MainActivity;
import com.example.administrator.rollcall_10.main.MainActivity;
import com.example.administrator.rollcall_10.recyclerview.Recyclerview_WatchList;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/9/2.
 */
public class Successful_NotificationDisplayService extends Service {

    int List_size;
    String List_name,List_path;
    String Extra_meassage1,Extra_meassage2;

    File file;

    HashMap<String,String> listmap=new HashMap<>();

    final int NOTIFICATION_ID = 16;


    public Successful_NotificationDisplayService(){
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Bundle bundle = intent.getExtras();
        List_size=  bundle.getInt("List_size");
        List_name=  bundle.getString("List_name");
        List_path=  bundle.getString("List_Path");


        Extra_meassage1=bundle.getString("Extra_meassage1");
        Extra_meassage2=bundle.getString("Extra_meassage2");

        listmap= (HashMap<String, String>) bundle.getSerializable("Txt_path");

        Log.e("QWEQWE!!!",""+List_name+"   path : "+List_path);



        if(Extra_meassage1 != null) {
            displayNotification(Extra_meassage1 + List_size + getResources().getString(R.string.Notification_Meassage2),
                    Extra_meassage2 + List_name.substring(0, List_name.length() - 4) + getResources().getString(R.string.Notification_Meassage4));
            stopSelf();

        }else {
            displayNotification(getResources().getString(R.string.Notification_Meassage1) + List_size + getResources().getString(R.string.Notification_Meassage2),
                    getResources().getString(R.string.Notification_Meassage3) + List_name.substring(0, List_name.length() - 4) + getResources().getString(R.string.Notification_Meassage4));
            stopSelf();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void displayNotification(String title, String text){

        Intent notificationIntent = new Intent(this, Recyclerview_WatchList.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        Bundle Notification=new Bundle();
        Notification.putString("List_Name",List_name);
        Notification.putString("List_Path",List_path);
        Notification.putSerializable("Txt_path",listmap);


        notificationIntent.putExtras(Notification);

        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.mipmap.inputdata512)
                //.setLargeIcon(BitmapFactory.decodeResource(R.drawable.xyz))
//                .setColor(getResources().getColor(R.color.color_fff))
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