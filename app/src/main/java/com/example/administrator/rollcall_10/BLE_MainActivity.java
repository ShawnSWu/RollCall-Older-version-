package com.example.administrator.rollcall_10;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BLE_MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    public static final int REQUEST_ENABLE_BT = 1;
    public static final int BTLE_SERVICES = 2;

    private HashMap<String, BTLE_Device> mBTDevicesHashMap;
    private ArrayList<BTLE_Device> mBTDevicesArrayList;
    private ListAdapter_BTLE_Devices adapter;
    private ListView listView;

MainActivity mainActivity=new MainActivity();
    private ActionBar actionBar;

    private BroadcastReceiver_BTState mBTStateUpdateReceiver;
    private Scanner_BTLE mBTLeScanner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ble_activity_main);

        //****Scan返回鍵監聽事件 Start****\\
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //****Scan返回鍵監聽事件 End****\\









        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Utils.toast(getApplicationContext(), "BLE not supported");
            finish();




        }




        mBTStateUpdateReceiver = new BroadcastReceiver_BTState(getApplicationContext());
        mBTLeScanner = new Scanner_BTLE(this,10000, -75);

        mBTDevicesHashMap = new HashMap<>();
        mBTDevicesArrayList = new ArrayList<>();

        adapter = new ListAdapter_BTLE_Devices(this, R.layout.btle_device_list_item, mBTDevicesArrayList);


        listView = new ListView(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        ((ScrollView) findViewById(R.id.scrollView)).addView(listView);




        //*****原本scan按鍵  Start***\\\
//        btn_Scan = (Button) findViewById(R.id.btn_scan);
//        findViewById(R.id.btn_scan).setOnClickListener(this);
        //*****原本scan按鍵  End***\\\
        startScan();

    }




    //****Scan返回鍵(左上角鍵頭)監聽事件 Start***\\\
    @Override
    public Intent getSupportParentActivityIntent() {
        finish();
        return null;
    }
    //****Scan返回鍵(左上角鍵頭)監聽事件 End***\\\










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

        stopScan();
        //********************* 裝置名稱
        String name = mBTDevicesArrayList.get(position).getName();

        String address = mBTDevicesArrayList.get(position).getAddress();

        Intent intent = new Intent(this, Activity_BTLE_Services.class);
        intent.putExtra(Activity_BTLE_Services.EXTRA_NAME, name);
        intent.putExtra(Activity_BTLE_Services.EXTRA_ADDRESS, address);
        startActivityForResult(intent, BTLE_SERVICES);

    }

    public void getnamedata(String name){


    }








    //******************************原本scan按鍵，暫時不用
   @Override
   public void onClick(View v) {
//
//        switch (v.getId()) {
//
//            case R.id.btn_scan:
//                Utils.toast(getApplicationContext(), "Scan Button Pressed");
//
//                if (!mBTLeScanner.isScanning()) {
//                    startScan();
//                }
//                else {
//                    stopScan();
//                }
//
//                break;
//            default:
//                break;
//        }

//
    }
    //******************************原本scan按鍵，暫時不用







    public void addDevice(BluetoothDevice device, int rssi) {

        String address = device.getAddress();
        String[] list = new String[15];

        if (!mBTDevicesHashMap.containsKey(address)) {


            String s = "";

            int abc = 0;

            FileReader fr = null;

            try {
                fr = new FileReader(mainActivity.file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            BufferedReader br = new BufferedReader(fr);


            do {
                try {
                    s = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                list[abc] = s;
                abc++;

            } while (s != null);




            if (Arrays.asList(list).contains(address)) {


                if (device.getName().startsWith("KSU")) {//////////////////////////////開頭限制


                    BTLE_Device btleDevice = new BTLE_Device(device);
                    btleDevice.setRSSI(rssi);

                    mBTDevicesHashMap.put(address, btleDevice);


                    mBTDevicesArrayList.add(btleDevice);
                }

            }






        }
        else {
            mBTDevicesHashMap.get(address).setRSSI(rssi);

        }

        adapter.notifyDataSetChanged();
    }


















    public void startScan(){

        //*****原本scan按鍵
//        btn_Scan.setText("Scanning...");
        //*****原本scan按鍵


        mBTDevicesArrayList.clear();
        mBTDevicesHashMap.clear();

        mBTLeScanner.start();
    }

    public void stopScan() {
        //*****原本scan按鍵
//        btn_Scan.setText("Scan Again");
        //*****原本scan按鍵


        mBTLeScanner.stop();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.scan_set, menu);
        return true;

    }

    

    //**Toolbar元鍵控制
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_scan:
                if (!mBTLeScanner.isScanning()) {
                    startScan();

                }
                else {
                    stopScan();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //**Toolbar元鍵控制



}
