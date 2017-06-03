package com.example.administrator.rollcall_10.ble_device_setting;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.administrator.rollcall_10.R;

import com.example.administrator.rollcall_10.main.MainActivity;
import com.example.administrator.rollcall_10.notifications.AlarmClock_Notification;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;

public class setBLEDevice_connect extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT=1;
    private static final int SCAN_TIME=120000;

    public  static final String CHOOSE_SECOND="choose_second";
    public  static final String DEVICE_ARRAYLIST="Device_ArrayList";
    public  static final String SELECTED_FILE_NAME="Selected_File_Name";
    public  static final String CHOOSE_TIME="Choose_Time";



    private Menu mymenu;
    private MenuItem scan,countdown;
    static String countdown_time;

    private CountDownTimer mCountDown;

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<BluetoothDevice> Device_ArrayList=new ArrayList<>();
    private Handler handler;
    private Boolean scanning;
    private ListAdapter listAdapter;
    private ArrayList<String> deviceName;
    private String Seletor_File_path;

    private String choose_second;

    private String Selected_File_Name;
    private String Choose_Time;
    private BLE_CallBack[] ble_callBack;

    private HashMap<String,String> MainHashMapList=new HashMap<>();

    private ArrayList<String> ScanList_Key =new ArrayList<>();

    private void LoadingTxtData()
    {

        Bundle bundle = this.getIntent().getExtras();
        Seletor_File_path =  bundle.getString(Set_BLE_Device.SELECTED_FILE_PATH);
        choose_second=bundle.getString(Set_BLE_Device.CHOOSE_SECOND);
        Selected_File_Name=bundle.getString(Set_BLE_Device.SELECTED_FILE_NAME);
        Choose_Time=bundle.getString(Set_BLE_Device.CHOOSE_TIME_SPINNER_ITEM);

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {

            fileReader = new FileReader(Seletor_File_path);

            bufferedReader= new BufferedReader(fileReader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            while(bufferedReader.ready()){

                String data_split=bufferedReader.readLine();


                Log.e("readLine","readLine的data"+data_split);

                String[] array_data_split=data_split.split(",");

                ScanList_Key.add(array_data_split[1]);


                

                MainHashMapList.put(array_data_split[1],array_data_split[0]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }



    public void CheckBluetooth_support(){

        if(!getPackageManager().hasSystemFeature(getPackageManager().FEATURE_BLUETOOTH_LE))
        {
            Toast.makeText(getBaseContext(),"不支援BLE",Toast.LENGTH_SHORT).show();
            finish();
        }


        bluetoothManager=(BluetoothManager)this.getSystemService(BLUETOOTH_SERVICE);
        bluetoothAdapter=bluetoothManager.getAdapter();

        if(bluetoothAdapter==null)
        {
            Toast.makeText(getBaseContext(),getResources().getString(R.string.NotSupportBLE),Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


    }

    @Override
    public Intent getSupportParentActivityIntent() {
        finish();
        return null;
    }


    private void init_UI(){

        deviceName=new ArrayList<String>();
        ListView listView = (ListView) findViewById(R.id.SetDevice_listview);
        listAdapter=new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_expandable_list_item_1,deviceName);//ListView使用的Adapter，
        listView.setAdapter(listAdapter);//將listView綁上Adapter

        Button btn_connect=(Button)findViewById(R.id.connect);
        btn_connect.setText(getResources().getString(R.string.RollCall_List_Delete_Button_yes));
        btn_connect.setOnClickListener(new Btn_Connect());
    }

    private class Btn_Connect implements View.OnClickListener
    {

        @Override
        public void onClick(View view)
        {
            if(Device_ArrayList.size() != 0)
            {
                onPause();

                RollCall_Dialog.Builder rd=new RollCall_Dialog.Builder(setBLEDevice_connect.this);
                        rd.setTitle("設定時間到裝置裡").
                        setMessage("確定設定這 " +Device_ArrayList.size() + " 個裝置嗎?").
                        setPositiveButton(getResources().getString(R.string.SetDevice_btnYes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NotificationService();

                            }
                        }).
                        setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                onResume();
                            }
                        });

                rd.show();

            }else
            {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.NotYetScanAnyDevice),Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void NotificationService()
    {

        final SpotsDialog spotsDialog= new SpotsDialog(setBLEDevice_connect.this,R.style.connect_loding);
            spotsDialog.show();

        Intent intent=new Intent(setBLEDevice_connect.this, AlarmClock_Notification.class);

        Bundle bundle=new Bundle();

        bundle.putString(CHOOSE_SECOND ,choose_second);
        bundle.putSerializable(DEVICE_ARRAYLIST,Device_ArrayList);
        bundle.putString(SELECTED_FILE_NAME,Selected_File_Name);
        bundle.putString(CHOOSE_TIME,Choose_Time);
        intent.putExtras(bundle);

        startService(intent);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                spotsDialog.dismiss();
            }
        }, 4000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_set_bledevice_connect);

        CheckBluetooth_support();

        LoadingTxtData();

        //****Scan返回鍵監聽事件
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handler=new Handler();

        init_UI();
        ScanDevice(true);
    }




    public void ScanDevice(Boolean enable)
    {
        if(enable){
            handler.postDelayed(new Runnable() { //啟動一個Handler，並使用postDelayed在10秒後自動執行此Runnable()
                @Override
                public void run() {
                    bluetoothAdapter.stopLeScan(LeScanCallback);//停止搜尋
                    scanning=false; //搜尋旗標設為false

                }
            },SCAN_TIME); //SCAN_TIME為幾秒後要執行此Runnable，此範例中為10秒
            scanning=true; //搜尋旗標設為true
            bluetoothAdapter.startLeScan(LeScanCallback);//開始搜尋BLE設備

        }
        else{
            scanning=false;
            bluetoothAdapter.stopLeScan(LeScanCallback);
        }




    }

    public void StopScan(){
        scanning=false;
        if(ble_callBack != null) {
            for (int i = 0; i < ble_callBack.length; i++) {
                ble_callBack[i].bluetoothGatt.close();
            }
        }
        bluetoothAdapter.stopLeScan(LeScanCallback);
    }


    @Override
    protected void onPause() {
        super.onPause();
        scanning=false;
        bluetoothAdapter.stopLeScan(LeScanCallback);
    }

    private BluetoothAdapter.LeScanCallback LeScanCallback=new BluetoothAdapter.LeScanCallback(){

        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice,final int rssi, byte[] bytes) {



            runOnUiThread(new Runnable() {
                @Override
                public void run()
                {

                    if (!Device_ArrayList.contains(bluetoothDevice)) //利用contains判斷是否有搜尋到重複的device
                    {

                        if(bluetoothDevice.getName()!=null)
                        {

                            if (ScanList_Key.contains(bluetoothDevice.getAddress()))
                            {

                            Device_ArrayList.add(bluetoothDevice);         //如沒重複則添加到bluetoothdevices中

                            deviceName.add(MainHashMapList.get(bluetoothDevice.getAddress()) + "\r\n" + bluetoothDevice.getAddress()); //將device的Name、rssi、address裝到此ArrayList<String>中

                            ((BaseAdapter) listAdapter).notifyDataSetChanged();//使用notifyDataSetChanger()更新listAdapter的內容


                            }
                        }
                    }
                }
            });

        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        //一般來說，只要使用到mBluetoothAdapter.isEnabled()就可以將BL開啟了，但此部分添加一個Result Intent
        //跳出詢問視窗是否開啟BL，因此該Intenr為BluetoothAdapter.ACTION.REQUEST_ENABLE
        if(!bluetoothAdapter.isEnabled()){
            Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,REQUEST_ENABLE_BT); //再利用startActivityForResult啟動該Intent
        }
        ScanDevice(true); //使用ScanFunction(true) 開啟BLE搜尋功能，該Function在下面部分

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(REQUEST_ENABLE_BT==1 && resultCode== Activity.RESULT_CANCELED){
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        mCountDown = new CountDownTimer(SCAN_TIME, 1000)
        {
            long millis;

            public void onTick(long millisUntilFinished)
            {
                millis = millisUntilFinished;


                countdown_time = getResources().getString(R.string.Remaining_time)+
                        (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)))+":"+
                        (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

                countdown.setTitle(countdown_time);
                scan.setIcon(R.drawable.stopscanbtn);

                if(countdown_time.equals("1")){
                    mCountDown.onFinish();
                }

            }

            public void onFinish() {
                countdown_time="DONE";
                countdown.setTitle("done");

                scan.setIcon(R.drawable.startscanbtn);
                Log.e("1","倒數結束");

            }

        }.start();

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_scan:
                if (!scanning) {
                    ScanDevice(true);
                    mCountDown.start();

                }
                else {
                    StopScan();
                    //**記時結束
                    mCountDown.cancel();
                    mCountDown.onFinish();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
