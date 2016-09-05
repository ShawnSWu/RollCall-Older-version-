package com.example.administrator.rollcall_10.manual_add;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.demo.Utils;
import com.example.administrator.rollcall_10.device_io.Device_IO;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ManualAdd_BLE_MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final int REQUEST_ENABLE_BT = 1;
    public static final int BTLE_SERVICES = 2;

    private HashMap<String, ManualAdd_BTLE_Device> mBTDevicesHashMap;
    private ArrayList<ManualAdd_BTLE_Device> mBTDevicesArrayList;
    private ManualAdd_ListAdapter_BTLE_Devices adapter;

    ListView listView;


    private ManualAdd_BroadcastReceiver_BTState mBTStateUpdateReceiver;
    private ManualAdd_BLE_Scanner_BTLE manualAdd_ble_scanner_btle;


    public int DeviceAmount = 0;

    Device_IO device_io =new Device_IO();

    ManualAdd_BTLE_Device btleDevice;


    Menu mymenu;
    MenuItem scan,countdown;


    private CountDownTimer mCountDown;

    private void dialog_used(){




    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setProgressBarIndeterminateVisibility(true);
        setContentView(R.layout.ble_activity_main);

        //**Actionbar跟標題資料
        Acttionbar_TitleData();

        dialog_used();

        //用以檢查,是否用在設備上
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Utils.toast(getApplicationContext(), "BLE not supported");
            finish();
        }



        //**掃描時間 先給1分鐘
        mBTStateUpdateReceiver = new ManualAdd_BroadcastReceiver_BTState(getApplicationContext());
        manualAdd_ble_scanner_btle = new ManualAdd_BLE_Scanner_BTLE(this,10000, -75);

        mBTDevicesHashMap = new HashMap<>();
        mBTDevicesArrayList = new ArrayList<>();

        adapter = new ManualAdd_ListAdapter_BTLE_Devices(this, R.layout.btle_device_list_item, mBTDevicesArrayList);


        listView = new ListView(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        ((ScrollView) findViewById(R.id.scrollView)).addView(listView);



        startScan();

    }




    //****Scan返回鍵(左上角鍵頭)監聽事件 Start***\\\
    @Override
    public Intent getSupportParentActivityIntent() {
        finish();
        return null;
    }
    //****Scan返回鍵(左上角鍵頭)監聽事件 End***\\\




    //**Actionbar跟標題
    public void Acttionbar_TitleData(){

        //****Scan返回鍵監聽事件
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //**掃描的清單名稱
        Bundle bundle = getIntent().getExtras();
        String Seletor_File_Name = bundle.getString("Selected_File_Name");

        //清單名稱當標題
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle(Seletor_File_Name);
    }




    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(mBTStateUpdateReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    @Override
    protected void onResume() {
        super.onResume();

//        registerReceiver(mBTStateUpdateReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    @Override
    protected void onPause() {
        super.onPause();

//        unregisterReceiver(mBTStateUpdateReceiver);
        stopScan();
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(mBTStateUpdateReceiver);
        stopScan();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopScan();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
//                Utils.toast(getApplicationContext(), "Thank you for turning on Bluetooth");
            }
            else if (resultCode == RESULT_CANCELED) {
                Utils.toast(getApplicationContext(), "Please turn on Bluetooth");
            }
        }
        else if (requestCode == BTLE_SERVICES) {
            // Do something
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




        Context context = view.getContext();

        //        Utils.toast(context, "List Item clicked");

        // do something with the text views and start the next activity.

//        stopScan();
        //********************* 裝置名稱
        String name = mBTDevicesArrayList.get(position).getName();

        String address = mBTDevicesArrayList.get(position).getAddress();


        LayoutInflater inflater = (LayoutInflater)this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_edit_manualadd, null);
        final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(this);
        TextView txt_device_address =(TextView)layout.findViewById(R.id.device_address);
        txt_device_address.setText(address);


        Button btn_close =(Button)layout.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollCall_dialog.dismiss();
            }
        });




        Button btn_ok =(Button)layout.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });




        rollCall_dialog.setView(layout);
        rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
        rollCall_dialog.setCancelable(false);
        rollCall_dialog.setCancelable(true);
        rollCall_dialog.show();



    }










    public void addDevice(BluetoothDevice device, int rssi) {

        String address = device.getAddress();
        if (!mBTDevicesHashMap.containsKey(address)) {


           btleDevice = new ManualAdd_BTLE_Device(device);
            btleDevice.setRSSI(rssi);

            mBTDevicesHashMap.put(address, btleDevice);
            mBTDevicesArrayList.add(btleDevice);



            //***Dialog１１１
//            RollCall_Dialog rollCall_dialog = new RollCall_Dialog(this);
//            rollCall_dialog.setTitle(R.string.RollCall_Dialog_Title_FindDevice);
//            rollCall_dialog.setMessage(ManualAdd_BLE_MainActivity.this.getString(R.string.RollCall_Dialog__Message_AddYesOrNo));
//            rollCall_dialog.setIcon(android.R.drawable.ic_dialog_info);
//            rollCall_dialog.setCancelable(false);
//            rollCall_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, ManualAdd_BLE_MainActivity.this.getString(R.string.RollCall_Dialog__Button_No), no);
//            rollCall_dialog.setButton(DialogInterface.BUTTON_POSITIVE, ManualAdd_BLE_MainActivity.this.getString(R.string.RollCall_Dialog__Button_Yes), yes);
//            rollCall_dialog.show();
            //***Dialog




            LayoutInflater inflater = (LayoutInflater)this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.dialog_edit_manualadd, null);
            final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(this);
            TextView txt_device_address =(TextView)layout.findViewById(R.id.device_address);
            txt_device_address.setText(address);


            Button btn_close =(Button)layout.findViewById(R.id.btn_close);
            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rollCall_dialog.dismiss();
                }
            });




            Button btn_ok =(Button)layout.findViewById(R.id.btn_ok);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });




            rollCall_dialog.setView(layout);
            rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
            rollCall_dialog.setCancelable(false);
            rollCall_dialog.setCancelable(true);
            rollCall_dialog.show();


        }



        else {
            mBTDevicesHashMap.get(address).setRSSI(rssi);

        }

        adapter.notifyDataSetChanged();
    }

    public DialogInterface.OnClickListener yes = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

                    DeviceAmount++;

                     String address = mBTDevicesArrayList.get(DeviceAmount-1).getAddress();


                     Bundle bundle = getIntent().getExtras();
                     String Seletor_File=  bundle.getString("Selected_File_Path");



              device_io.writeData(address,true,Seletor_File);
        }

    };





//    public DialogInterface.OnClickListener no = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//
//
//
//
//
//        }
//
//    };





    public ManualAdd_BTLE_Device getDevice(int position) {

        return mBTDevicesArrayList.get(position);

    }











    public void startScan(){


        Bundle bundle = getIntent().getExtras();
        String Seletor_File=  bundle.getString("Selected_File_Path");
        File peoplefile = new File(Seletor_File);

        peoplefile.delete();////////////////////////////////////////////下次加入時 刪除之前的資料

        //****剛剛把資料刪掉 在建一個一樣的
        try {
            peoplefile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mBTDevicesArrayList.clear();
        mBTDevicesHashMap.clear();
        manualAdd_ble_scanner_btle.start();




    }

    public void stopScan() {

        manualAdd_ble_scanner_btle.stop();
        mCountDown.cancel();


    }

    public void countdown()

    {
        mCountDown = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;



                String countdown_time = "" + (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

                countdown.setTitle(countdown_time);
                scan.setIcon(R.drawable.stopscanbtn);

                if(countdown_time =="1"){
                    mCountDown.onFinish();
                }



            }

            public void onFinish() {

                countdown.setTitle("done");
                scan.setIcon(R.drawable.startscanbtn);
                Log.e("1","倒數結束");

            }
        }.start();

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.scan_set, menu);
        mymenu = menu;
//        progress_menu_item = mymenu.findItem(R.id.action_progress_show);
        //**一開始就掃描progress
        countdown= mymenu.findItem(R.id.conutdown);
        countdown();
        scan = mymenu.findItem(R.id.action_scan).setIcon(R.drawable.stopscanbtn);


        return true;

    }


    //**Toolbar元鍵控制
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_scan:
                if (!manualAdd_ble_scanner_btle.isScanning()) {
                    startScan();
                    countdown();

                }
                else {
                    stopScan();
                //**記時結束
                     mCountDown.cancel();
                    mCountDown.onFinish();


                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //**Toolbar元鍵控制



}
