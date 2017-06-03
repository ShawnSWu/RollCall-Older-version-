package com.example.administrator.rollcall_10.ble_device_setting;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.main.MainActivity;
import com.example.administrator.rollcall_10.notifications.AlarmClock_Notification;

/**
 * Created by Administrator on 2017/5/5.
 */
public class RollCallTimeUp_BroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent_Notifcation = new Intent(context, MainActivity.class);

        String file_name= intent.getStringExtra(AlarmClock_Notification.SELECTED_FILE_NAME);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent_Notifcation);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        int NOTIFICATION_ID = 10;

        Notification.Builder notification = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.timer128)
                .setContentTitle("集合時間到了!!")
                .setContentText("清單：  "+ file_name.substring(0,file_name.length()-4) +"  的集合時間到了" )
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE);

        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification.build());


    }
}
