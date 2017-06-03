package com.example.administrator.rollcall_10.notifications;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.ble_device_setting.BLE_CallBack;
import com.example.administrator.rollcall_10.ble_device_setting.RollCallTimeUp_BroadcastReceiver;
import com.example.administrator.rollcall_10.ble_device_setting.setBLEDevice_connect;
import com.example.administrator.rollcall_10.main.MainActivity;
import com.example.administrator.rollcall_10.splashscreen.SplashScreen;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/5/15.
 */
public class AlarmClock_Notification extends Service {

    public final static String SELECTED_FILE_NAME="Selected_File_Name";
    public final static String CHOOSE_TIME="Choose_Time";



    private BLE_CallBack[] ble_callBack;
    private BluetoothDevice device;
    private String Choose_Time;

    ArrayList<BluetoothDevice> arrayList;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        Bundle bundle =  intent.getExtras();

        String choose_second= bundle.getString(setBLEDevice_connect.CHOOSE_SECOND);
        String Selected_File_Name=bundle.getString(setBLEDevice_connect.SELECTED_FILE_NAME);
        Choose_Time=bundle.getString(setBLEDevice_connect.CHOOSE_TIME);
        arrayList= (ArrayList<BluetoothDevice>) bundle.getSerializable(setBLEDevice_connect.DEVICE_ARRAYLIST);



        ble_callBack = new BLE_CallBack[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++)
        {
            ble_callBack[i] = new BLE_CallBack(choose_second);
            device = arrayList.get(i);
            ble_callBack[i].bluetoothGatt = device.connectGatt(this, false, ble_callBack[i].mGattCallback);
        }

        Broadcast_Alarm_Notification(Selected_File_Name,choose_second);





        return START_NOT_STICKY;
    }


    private void Broadcast_Alarm_Notification(String Selected_File_Name, String choose_second ){



        Calendar cal = Calendar.getInstance();
        // 設定於幾秒後執行
        int time=Integer.parseInt(choose_second.substring(1,choose_second.length()-1))/1000;

        if(time < 60)
        {
            cal.add(Calendar.SECOND,time);
        }
        else if(time > 60 || time<3600)
        {
            time=time/60;
            cal.add(Calendar.MINUTE, time);
        }
        else if(time >3600){

            time=time/3600;
            cal.add(Calendar.HOUR, time);
        }

        Intent intent = new Intent(this, RollCallTimeUp_BroadcastReceiver.class);
        Bundle bundle=new Bundle();
        bundle.putString(SELECTED_FILE_NAME, Selected_File_Name);

        bundle.putString(CHOOSE_TIME,Choose_Time);
        intent.putExtras(bundle);


        PendingIntent pi = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);

    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
