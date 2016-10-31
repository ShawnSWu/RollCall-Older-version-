package com.example.administrator.rollcall_10.manual_add;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.auto_add.AutoAdd_ListAdapter_BTLE_Devices;
import com.example.administrator.rollcall_10.demo.BTLE_Device;
import com.example.administrator.rollcall_10.demo.Utils;
import com.example.administrator.rollcall_10.device_io.Device_IO;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ManualAdd_BLE_MainActivity extends AppCompatActivity  {

    public static final int REQUEST_ENABLE_BT = 1;
    public static final int BTLE_SERVICES = 2;

    private HashMap<String, ManualAdd_BTLE_Device> mBTDevicesHashMap;

    private ArrayList<ManualAdd_BTLE_Device> mBTDevicesArrayList;

    private ManualAdd_ListAdapter_BTLE_Devices adapter;

    ListView listView;


    private ArrayList<String> string_shawn;



    private ManualAdd_BroadcastReceiver_BTState mBTStateUpdateReceiver;
    private ManualAdd_BLE_Scanner_BTLE manualAdd_ble_scanner_btle;


    Device_IO device_io =new Device_IO();

    ManualAdd_BTLE_Device btleDevice;


    Menu mymenu;
    MenuItem scan,countdown;


    private CountDownTimer mCountDown;


    public  ArrayList<String> savepeople_address =new ArrayList<>();

    public  ArrayList<String> savepeople_name =new ArrayList<>();
    



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setProgressBarIndeterminateVisibility(true);
        setContentView(R.layout.manualadd_ble_activity_main);







        //**Actionbar跟標題資料
        Acttionbar_TitleData();



        //用以檢查,是否用在設備上
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Utils.toast(getApplicationContext(), "BLE not supported");
            finish();
        }



        //**掃描時間 先給10分鐘
        mBTStateUpdateReceiver = new ManualAdd_BroadcastReceiver_BTState(getApplicationContext());
        manualAdd_ble_scanner_btle = new ManualAdd_BLE_Scanner_BTLE(this,600000, -75);

        mBTDevicesHashMap = new HashMap<>();
        mBTDevicesArrayList = new ArrayList<>();

        adapter = new ManualAdd_ListAdapter_BTLE_Devices(this, R.layout.manualadd_btle_device_list_item, mBTDevicesArrayList);


        listView=(ListView)findViewById(R.id.listView_manualadd);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Log.e("1","shawn"+savepeople_name.get(position));
                Log.e("1","shawn"+savepeople_address.get(position));


///**********未完成
                LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.dialog_edit_manualadd, null);

                final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(ManualAdd_BLE_MainActivity.this);

                TextView txt_device_address =(TextView)layout.findViewById(R.id.device_address);
                txt_device_address.setText(savepeople_address.get(position));


                ///**關閉dialog
                Button btn_close =(Button)layout.findViewById(R.id.btn_close);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rollCall_dialog.dismiss();
                    }
                });




                final EditText edit_device =(EditText)layout.findViewById(R.id.edit_device);
                edit_device.setHint(savepeople_name.get(position));



                //**編輯的dialog的確認鍵
                Button btn_ok =(Button)layout.findViewById(R.id.btn_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle bundle = getIntent().getExtras();
                        String Seletor_File=  bundle.getString("Selected_File_Path");

                        String edit_device_name =edit_device.getText().toString();
                        Toast.makeText(v.getContext(),edit_device_name , Toast.LENGTH_LONG).show();

                        //**修改ＮＡＭＥ
                        TextView tv=(TextView)findViewById(R.id.tv_name);
                        tv.setText(edit_device_name);






                        device_io.Temporary_Manual_WriteData(edit_device_name,savepeople_address.get(position),true,Seletor_File);



                        rollCall_dialog.dismiss();

                    }
                });




                rollCall_dialog.setView(layout);
                rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
                rollCall_dialog.setCancelable(false);
                rollCall_dialog.setCancelable(true);
                rollCall_dialog.show();
///**********未完成


            }
        });


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




    public void produce_manual_dialog(final BluetoothDevice device){

        final String address = device.getAddress();
        final String device_name =device.getName();




        LayoutInflater inflater = (LayoutInflater)this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_edit_manualadd, null);
        final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(this);

        TextView txt_device_address =(TextView)layout.findViewById(R.id.device_address);
        txt_device_address.setText(address);





        ///**關閉dialog
        Button btn_close =(Button)layout.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollCall_dialog.dismiss();
            }
        });





        final EditText edit_device =(EditText)layout.findViewById(R.id.edit_device);
        edit_device.setHint(device_name);


        //**編輯的dialog的確認鍵
        Button btn_ok =(Button)layout.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = getIntent().getExtras();
                String Seletor_File=  bundle.getString("Selected_File_Path");

                String edit_device_name =edit_device.getText().toString();
                Toast.makeText(v.getContext(),edit_device_name , Toast.LENGTH_LONG).show();



                //**修改ＮＡＭＥ
//                TextView tv=(TextView)findViewById(R.id.tv_name);
//                tv.setText(edit_device_name);



                device_io.Temporary_Manual_WriteData(edit_device_name,address,true,Seletor_File);

                btleDevice.setName(edit_device_name);




                adapter.notifyDataSetChanged();


                rollCall_dialog.dismiss();

            }
        });
        rollCall_dialog.setView(layout);
        rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
        rollCall_dialog.setCancelable(false);
        rollCall_dialog.setCancelable(true);
        rollCall_dialog.show();

    }



    public void addDevice(final BluetoothDevice device, final int rssi) {
        //**name

        final String address = device.getAddress();
          final String device_name =device.getName();

        if (!mBTDevicesHashMap.containsKey(address)) {


           btleDevice = new ManualAdd_BTLE_Device(device);
            btleDevice.setRSSI(rssi);



            mBTDevicesHashMap.put(address, btleDevice);

            mBTDevicesArrayList.add(btleDevice);

            savepeople_address.add(address);
            savepeople_name.add(device_name);

            //*產生手動加入dialog
            produce_manual_dialog(device);

        }



        else {
            mBTDevicesHashMap.get(address).setRSSI(rssi);

        }

        adapter.notifyDataSetChanged();
    }




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


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.scan_set, menu);
        mymenu = menu;

        countdown= mymenu.findItem(R.id.conutdown);
        scan = mymenu.findItem(R.id.action_scan).setIcon(R.drawable.stopscanbtn);
        countdown();

        return true;

    }


    public void countdown()

    {
        mCountDown = new CountDownTimer(600000, 1000) {

            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;


                String countdown_time =   //(TimeUnit.MILLISECONDS.toHours(millis))+  -------小時
                        "剩下時間："+
                        (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)))+":"+
                        (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdown.setTitle(countdown_time);

                countdown.setTitle(countdown_time);
                scan.setIcon(R.drawable.stopscanbtn);

                if(countdown_time== "1"){
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

