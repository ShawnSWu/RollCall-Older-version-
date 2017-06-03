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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.demo.Utils;
import com.example.administrator.rollcall_10.device_io.Device_IO;
import com.example.administrator.rollcall_10.notifications.Successful_NotificationDisplayService;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class ManualAdd_BLE_MainActivity extends AppCompatActivity  {

    public static final int REQUEST_ENABLE_BT = 1;
    public static final int BTLE_SERVICES = 2;

    private HashMap<String, ManualAdd_BTLE_Device> mBTDevicesHashMap;

    private ArrayList<ManualAdd_BTLE_Device> mBTDevicesArrayList;

    private ManualAdd_ListAdapter_BTLE_Devices adapter;

    private ListView listView;


    private ManualAdd_BroadcastReceiver_BTState mBTStateUpdateReceiver;
    private ManualAdd_BLE_Scanner_BTLE manualAdd_ble_scanner_btle;


    private Device_IO device_io =new Device_IO();

    private ManualAdd_BTLE_Device btleDevice;


    private Menu mymenu;
    private MenuItem scan,countdown;


    private CountDownTimer mCountDown;

    private HashMap<String,String> txt_Hashmap=new HashMap<>();



    //**清單最後名單*******
    private HashMap<String,String> final_Savepeople_HashMap =new HashMap<>();

    private  ArrayList<String> Get_HashMap_Key =new ArrayList<>();

    private RollCall_Dialog rollCall_dialog;
    private String address;
    private EditText edit_device;
    private String Seletor_File;

    private String Seletor_File_Name;




    private void leavel_dailog(){
        onPause();
        RollCall_Dialog.Builder rd=new RollCall_Dialog.Builder(this);
        rd.setTitle("離開此頁面?").
                setMessage("離開後記錄將不會保存,是否離開?").
                setPositiveButton("確定離開", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopScan();
                        finish();
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        onRestart();
                    }
                });

        rd.show();


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setProgressBarIndeterminateVisibility(true);
        setContentView(R.layout.manualadd_ble_activity_main);


        //**Actionbar跟標題資料
        Acttionbar_TitleData();



        Button btn_manualadd =(Button)findViewById(R.id.manual_add);
        if (btn_manualadd != null) {
            btn_manualadd.setOnClickListener(new dialog_button(this));
        }


        //用以檢查,是否用在設備上
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Utils.toast(getApplicationContext(), "BLE not supported / 您的藍芽不支援");
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


                onPause();
                //先載入dialog畫面
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.dialog_edit_manualadd, null);
                rollCall_dialog = new RollCall_Dialog(ManualAdd_BLE_MainActivity.this);

                TextView txt_device_address =(TextView)layout.findViewById(R.id.device_address);
                txt_device_address.setText(address);

                edit_device =(EditText)layout.findViewById(R.id.edit_device);
                edit_device.setHint(mBTDevicesArrayList.get(position).getName());


                //測到有裝置　先停止掃描　等待輸入




                ///**關閉dialog
                Button btn_close =(Button)layout.findViewById(R.id.btn_close);
                btn_close.setOnClickListener(new dialog_button(ManualAdd_BLE_MainActivity.this));



                //**編輯的dialog的確認鍵

                //**確定的button in dialog
                Button btn_ok =(Button)layout.findViewById(R.id.btn_ok);
                btn_ok.setOnClickListener(new dialog_button(ManualAdd_BLE_MainActivity.this));


                rollCall_dialog.setView(layout);
                rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
                rollCall_dialog.setCancelable(false);
                rollCall_dialog.show();
            }
        });


        startScan();


    }






    //****Scan返回鍵(左上角鍵頭)監聽事件 Start***\\\
    @Override
    public Intent getSupportParentActivityIntent() {

        leavel_dailog();
        return null;
    }
    //****Scan返回鍵(左上角鍵頭)監聽事件 End***\\\




    //**Actionbar跟標題
    private void Acttionbar_TitleData(){

        //****Scan返回鍵監聽事件
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //**掃描的清單名稱
        Bundle bundle = getIntent().getExtras();
        Seletor_File_Name = bundle.getString("Selected_File_Name");

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
    protected void onRestart() {
        super.onRestart();
        registerReceiver(mBTStateUpdateReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        manualAdd_ble_scanner_btle.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

//      unregisterReceiver(mBTStateUpdateReceiver);
        manualAdd_ble_scanner_btle.stop();

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
        leavel_dailog();
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

        address = device.getAddress();
        final String device_name =device.getName();



        //先載入dialog畫面
        LayoutInflater inflater = (LayoutInflater)this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_edit_manualadd, null);
       rollCall_dialog = new RollCall_Dialog(this);

        TextView txt_device_address =(TextView)layout.findViewById(R.id.device_address);
        txt_device_address.setText(address);

        edit_device =(EditText)layout.findViewById(R.id.edit_device);
        edit_device.setHint(device_name);


        //測到有裝置　先停止掃描　等待輸入
        onPause();



        ///**關閉dialog
        Button btn_close =(Button)layout.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new dialog_button(this));



        //**編輯的dialog的確認鍵

        //**確定的button in dialog
        Button btn_ok =(Button)layout.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new dialog_button(this));


        rollCall_dialog.setView(layout);
        rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
        rollCall_dialog.setCancelable(false);
        rollCall_dialog.show();

    }



    public synchronized void addDevice(final BluetoothDevice device, final int rssi) {
        //**name

        final String address = device.getAddress();
          final String device_name =device.getName();

        if (!mBTDevicesHashMap.containsKey(address)) {

            if(device_name !=null) {

//                if (device.getName().startsWith(I_Set_BLEDevice.device_startwith)) {//////////////////////////////開頭限制

                    btleDevice = new ManualAdd_BTLE_Device(device);
                    btleDevice.setRSSI(rssi);


                    mBTDevicesHashMap.put(address, btleDevice);

                    mBTDevicesArrayList.add(btleDevice);


                    //*產生手動加入dialog
                    produce_manual_dialog(device);

//                }
            }
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



    //**計時器
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
                    mCountDown.start();
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











    class dialog_button implements View.OnClickListener{
        Context context;
        dialog_button(Context context){
            this.context=context;
        }
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                //dialog取消按鍵
                case R.id.btn_close:
                    onRestart();
                    rollCall_dialog.dismiss();
                    break;


                //dialog確定按鍵
                case R.id.btn_ok:


                    Bundle bundle = getIntent().getExtras();
                    Seletor_File=  bundle.getString("Selected_File_Path");

                    String edit_device_name =edit_device.getText().toString();

                    final_Savepeople_HashMap.put(address,edit_device_name);

                    Log.e("shawn",edit_device_name+"and"+address);

                    btleDevice.setName(edit_device_name);

                    adapter.notifyDataSetChanged();

                    rollCall_dialog.dismiss();
                    onRestart();

                    break;





                //加入清單按鍵
                case R.id.manual_add:

                    if(ListHasNoData()) {
                        List_hasno_data_warning();
                    }else{
                        AddDataToList();
                    }
                    break;



            }

        }

        //判斷最終清單內有無資料
        boolean ListHasNoData(){

            if(final_Savepeople_HashMap.size() == 0){
                return true;
            }
            return false;
        }


        private void AddDataToList(){
            onPause();
            RollCall_Dialog.Builder rd=new RollCall_Dialog.Builder(context);
            rd.setTitle(getResources().getString(R.string.BeGoingToAddList)).
                    setMessage(getResources().getString(R.string.AreYouSureAddToList_Message1) + final_Savepeople_HashMap.size() + getResources().getString(R.string.AreYouSureAddToList_Message2)).
                    setPositiveButton(getResources().getString(R.string.AreYouSureAddToList_Btn), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            SureToJoin();
                            finish();

                            Intent startNotificationServiceIntent = new Intent(ManualAdd_BLE_MainActivity.this, Successful_NotificationDisplayService.class);

                            Bundle Notification=new Bundle();
                            Notification.putString("List_name",Seletor_File_Name);
                            Notification.putString("List_Path",Seletor_File);
                            Notification.putInt("List_size", final_Savepeople_HashMap.size());
                            Notification.putSerializable("Txt_path",final_Savepeople_HashMap);

                            startNotificationServiceIntent.putExtras(Notification);

                            startService(startNotificationServiceIntent);

                        }
                    }).
                    setNegativeButton(getResources().getString(R.string.ContinueEdit), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            onRestart();
                        }
                    });

            rd.show();
        }

      private   void List_hasno_data_warning(){
            onPause();
            RollCall_Dialog.Builder rd=new RollCall_Dialog.Builder(context);
            rd.setTitle(getResources().getString(R.string.YouNoAddAnyData)).
                    setMessage(getResources().getString(R.string.DoNothing)).
                    setPositiveButton(getResources().getString(R.string.LeaveThiePage_btnYes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).
                    setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            onRestart();
                        }
                    });

            rd.show();
        }
    }


    private  void SureToJoin(){
        device_io.Manual__WriteDataToTxt(final_Savepeople_HashMap,true,Seletor_File);
        rollCall_dialog.dismiss();
        stopScan();
        finish();
    }
}




