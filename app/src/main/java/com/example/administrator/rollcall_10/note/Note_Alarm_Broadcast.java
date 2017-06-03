package com.example.administrator.rollcall_10.note;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.main.MainActivity;
import com.example.administrator.rollcall_10.notifications.AlarmClock_Notification;
import com.example.administrator.rollcall_10.splashscreen.SplashScreen;

/**
 * Created by Administrator on 2017/5/22.
 */
public class Note_Alarm_Broadcast extends BroadcastReceiver {

     Note_RecyclerView Note_RecyclerView;
    Note_card_click_RecyclerviewAdapter note_card_click_recyclerviewAdapter;
    @Override
    public void onReceive(final Context context, Intent intent) {

        synchronized (this) {

            Intent intent_Notifcation = new Intent(context, Note_RecyclerView_Activity.class);

            String title= intent.getStringExtra("title");
            String content= intent.getStringExtra("content");



            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(intent_Notifcation);

            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            int NOTIFICATION_ID = 10;

            Notification.Builder notification = new Notification.Builder(context)
                    .setSmallIcon(R.mipmap.timer128)
                    .setContentTitle("鬧鐘時間到了!!")
                    .setContentText("便條紙內容："+content )
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            notification.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, notification.build());


            ContentValues values=new ContentValues();
            values.put(RollCall_Note_database.Note_FinshOrNotIcon,R.mipmap.tick64);
            values.put(RollCall_Note_database.Note_IsFinsh,1);
            RollCall_Note_database.getInstance(context).getWritableDatabase().update(RollCall_Note_database.Note_Database_Name, values, RollCall_Note_database.Note_Title + " = ?", new String[]{title});



            new Async_Update_Database(context.getApplicationContext()).execute();
            new Async_Update_FinshOrNotIcon_Database(context.getApplicationContext()).execute();
            new Async_Update_IsFinsh_Database(context.getApplicationContext()).execute();

        }
    }
}
