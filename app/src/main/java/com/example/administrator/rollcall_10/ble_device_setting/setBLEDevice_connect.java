package com.example.administrator.rollcall_10.ble_device_setting;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.main.MainActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class setBLEDevice_connect extends AppCompatActivity {

    static String cmd_3000="$3000#";
    static String stop="$stop#";

    private String senddata;


    private BluetoothGatt bluetoothGatt;

    private BluetoothGattCharacteristic characteristic;
    private  BluetoothGattService service;

    private List<BluetoothGattCharacteristic> listGattCharacteristic;
    private List<BluetoothGattService> supportedGattServices;


    private BluetoothDevice device;


    private Menu mymenu;
    private MenuItem scan,countdown;
    static String countdown_time;

    private CountDownTimer mCountDown;


    //*scan
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private static final int REQUEST_ENABLE_BT=1;
    private static final int SCAN_TIME=10000;
    private ArrayList<BluetoothDevice> Device_ArrayList=new ArrayList<>();
    private Handler handler;
    private Boolean scanning;
    private ListAdapter listAdapter;
    private ArrayList<String> deviceName;
    private String path;

    //**傳進來的清單,要放的ArrayList
    ArrayList<String> ReadyScanList =new ArrayList<>();

    //**有KEY有值得HashMap 拿來判斷
    private HashMap<String,String> MainHashMapList=new HashMap<>();

    ArrayList<String> RollCall_successful_key =new ArrayList<>();

    //**拿來保存要給MainHashMapList的暫時ArrayList
    ArrayList<String> ScanList_Key =new ArrayList<>();
    ArrayList<String> ScanList_Name =new ArrayList<>();
    //**拿來保存要給MainHashMapList的暫時ArrayList




    private void LoadingTxtData(){

        Bundle bundle = getIntent().getExtras();
        String Seletor_File = bundle.getString("Selected_File_Path");

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(Seletor_File);
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
                ScanList_Name.add(array_data_split[0]);

                MainHashMapList.put(array_data_split[0],array_data_split[1]);

            }

            for(int i=0;i<MainHashMapList.size();i++){
                Log.e("0508",""+MainHashMapList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }




//        do {
//
//
//            try {
//                ListWith_Address = bufferedReader.readLine();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//
//            //**防止空值
//            if(ListWith_Address!= null) {
//                //**把要掃描的清單內的資料取出來
//                ReadyScanList.add(ListWith_Address);
//
//                Log.e("-++++++++++///","::::::::::::::"+ListWith_Address);
//
//            }
//
//        } while (ListWith_Address != null);

//        for(int i=0;i<ReadyScanList.size();i+=2) {
//
//            Log.e("ScanList_Name的for",ReadyScanList.get(i));
//            ScanList_Name.add(ReadyScanList.get(i));
//        }
//
//
//        for(int i=1;i<ReadyScanList.size();i+=2) {
//
//            Log.e("ScanList_Key的for",ReadyScanList.get(i));
//            ScanList_Key.add(ReadyScanList.get(i));
//        }
//
//
//
//        for(int i=0;i<ScanList_Key.size();i++) {
//
//            MainHashMapList.put(ScanList_Key.get(i),ScanList_Name.get(i));
//
//        }




//        for(int i=0;i<ReadyScanList.size()/2;i++) {
//
//            Log.e("shawn2017-02-22","清單內容----:"+ReadyScanList.toString());
//            Log.e("shawn2017-02-22","清單長度:"+ReadyScanList.size());
//
//            Log.e("shawn2017-02-22", "第"+i+"個的字為:" + ReadyScanList.get(i));
//
//        }

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
            Toast.makeText(getBaseContext(),"不支援BLE",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


    }

    @Override
    public Intent getSupportParentActivityIntent() {
        finish();
        bluetoothGatt.close();
        return null;
    }


   private void Shawn_Test_Log_List(String path){
        String ListWith_Address = null;

        FileReader fr = null;

        try {
            fr = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(fr);


        do {
            try {
                ListWith_Address = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }



            //**防止空值
            if(ListWith_Address!= null) {
                //**把要掃描的清單內的資料取出來
                ReadyScanList.add(ListWith_Address);

                Log.e("-++++++++++///","::::::::::::::"+ListWith_Address);

            }

        } while (ListWith_Address != null);




        for(int i=0;i<ReadyScanList.size();i+=2) {

            Log.e("ScanList_Name的for",ReadyScanList.get(i));
            ScanList_Name.add(ReadyScanList.get(i));
        }


        for(int i=1;i<ReadyScanList.size();i+=2) {

            Log.e("ScanList_Key的for",ReadyScanList.get(i));
            ScanList_Key.add(ReadyScanList.get(i));
        }



        for(int i=0;i<ScanList_Key.size();i++) {

            MainHashMapList.put(ScanList_Key.get(i),ScanList_Name.get(i));

        }




//        for(int i=0;i<ReadyScanList.size()/2;i++) {
//
//            Log.e("shawn2017-02-22","清單內容----:"+ReadyScanList.toString());
//            Log.e("shawn2017-02-22","清單長度:"+ReadyScanList.size());
//
//            Log.e("shawn2017-02-22", "第"+i+"個的字為:" + ReadyScanList.get(i));
//
//        }


        Log.e("shawn2017-02-22","MainHashMapList內容----:"+MainHashMapList);

    }


    private void init_UI(){

        Bundle bundle = this.getIntent().getExtras();
        path =  bundle.getString("Selected_File_Path");
        int int_senddata=bundle.getInt("Choose_second");

        //把要傳的資料轉成String
        senddata=""+int_senddata;



        Shawn_Test_Log_List(path);
        deviceName=new ArrayList<String>();
        ListView listView = (ListView) findViewById(R.id.SetDevice_listview);
        listAdapter=new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_expandable_list_item_1,deviceName);//ListView使用的Adapter，
        listView.setAdapter(listAdapter);//將listView綁上Adapter



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               device = Device_ArrayList.get(position);

                bluetoothGatt = device.connectGatt(setBLEDevice_connect.this, false, mGattCallback);


            }
        });


        Button btn_connect=(Button)findViewById(R.id.connect);
        btn_connect.setOnClickListener(new Btn_Connect());
    }


    private class Btn_Connect implements View.OnClickListener {

        @Override
        public void onClick(View view) {

//
           write_Into();


//                device = Device_ArrayList.get(0);
//                bluetoothGatt = device.connectGatt(setBLEDevice_connect.this, false, mGattCallback);




        }
    }




    private void write_Into(){

        service = bluetoothGatt.getService(UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e"));

        characteristic = service.getCharacteristic(UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e"));


        try {
            characteristic.setValue(cmd_3000.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        bluetoothGatt.writeCharacteristic(characteristic);

        Log.e("測試測試測試測試測試測試測試","write_Into_into:");
        enableTXNotification();

    }



    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                //連接成功後啟動服務發現


                Log.e("onConnectionStateChange", "啟動服務發現:");


                    bluetoothGatt.discoverServices();

            }
        }



        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            if (status == BluetoothGatt.GATT_SUCCESS)
            {
                Log.e("20170424", "寫入成功" +characteristic);
                Log.e("20170424", "onCharacteristicWrite:就是"+ new String(characteristic.getValue()));


            }


        }



        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);

            if (status == BluetoothGatt.GATT_SUCCESS) {

                supportedGattServices =bluetoothGatt.getServices();

                for(int i=0;i<supportedGattServices.size();i++){

                    Log.e("20170424","Service 的 UUID : BluetoothGattService UUID=:"+supportedGattServices.get(i).getUuid());
                    listGattCharacteristic=supportedGattServices.get(i).getCharacteristics();

                    for(int j=0;j<listGattCharacteristic.size();j++)
                    {
                        Log.e("20170424","Characteristic 的 UUID : BluetoothGattCharacteristic UUID=:"+listGattCharacteristic.get(j).getUuid());
                    }

                }

                write_Into();
                enableTXNotification();
            }
        }
    };


    public void enableTXNotification()
    {

        BluetoothGattService RxService = bluetoothGatt.getService(UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e"));
        if (RxService == null) {
            Log.e("FuckyouBLE","UART service not found!");

            return;
        }
        BluetoothGattCharacteristic TxChar = RxService.getCharacteristic(UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e"));
        if (TxChar == null) {
            Log.e("FuckyouBLE","Tx charateristic not found!");
            return;
        }
        bluetoothGatt.setCharacteristicNotification(TxChar, true);

        BluetoothGattDescriptor descriptor = TxChar.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        bluetoothGatt.writeDescriptor(descriptor);

    }









    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_set_bledevice_connect);

        CheckBluetooth_support();

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
        bluetoothGatt.close();
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

//                        if (ReadyScanList.contains(bluetoothDevice.getAddress())) {

                            Device_ArrayList.add(bluetoothDevice);         //如沒重複則添加到bluetoothdevices中

                            deviceName.add(bluetoothDevice.getName() + "\r\n" + bluetoothDevice.getAddress()); //將device的Name、rssi、address裝到此ArrayList<Strin>中

                            ((BaseAdapter) listAdapter).notifyDataSetChanged();//使用notifyDataSetChanger()更新listAdapter的內容


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


    protected void onPause() {
        super.onPause();

        bluetoothAdapter.stopLeScan(LeScanCallback);
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
        mCountDown = new CountDownTimer(SCAN_TIME, 1000) {

            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;



                countdown_time = "剩下時間："+
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


    //**Toolbar元鍵控制
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
    //**Toolbar元鍵控制
}
