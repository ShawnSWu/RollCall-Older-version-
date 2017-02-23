package com.example.administrator.rollcall_10.rollcall;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

;
import com.example.administrator.rollcall_10.demo.BroadcastReceiver_BTState;
import com.example.administrator.rollcall_10.demo.ListAdapter_BTLE_Devices;
import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.demo.Utils;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RollCall_BLE_MainActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener {
//    private final static String TAG = MainActivity.class.getSimpleName();
//    int Scan_Btn_Count=1;


    public static final int REQUEST_ENABLE_BT = 1;
    public static final int BTLE_SERVICES = 2;

    private HashMap<String, RollCall_BTLE_Device> mBTDevicesHashMap;
    private ArrayList<RollCall_BTLE_Device> mBTDevicesArrayList;
    private RollCall_ListAdapter_BTLE_Devices adapter;
    private ListView listView;

    RollCall_BTLE_Device btleDevice;

    static String countdown_time;

    private BroadcastReceiver_BTState mBTStateUpdateReceiver;
    private RollCall_Scanner_BTLE mBTLeScanner;


    Menu mymenu;
    MenuItem scan,countdown;
    private CountDownTimer mCountDown;


    private String Seletor_File_Name;

    //**傳進來的清單,要放的ArrayList
    ArrayList<String> ReadyScanList =new ArrayList<>();


    //**有KEY有值得HashMap 拿來判斷
   private HashMap<String,String> MainHashMapList=new HashMap<>();

    ArrayList<String> RollCall_successful_key =new ArrayList<>();


    //**拿來保存要給RSL的暫時ArrayList
    ArrayList<String> ScanList_Key =new ArrayList<>();
    ArrayList<String> ScanList_Name =new ArrayList<>();
    //**拿來保存要給RSL的暫時ArrayList



    void Shawn_Test_Log_List(){
        String ListWith_Address = null;

        Bundle bundle = getIntent().getExtras();
        String Seletor_File = bundle.getString("Selected_File_Path");

        FileReader fr = null;

        try {
            fr = new FileReader(Seletor_File);
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


        Log.e("shawn2017-02-22","RSL內容----:"+MainHashMapList);

    }


    private void leavel_dailog(final Context context){
        onPause();
        RollCall_Dialog.Builder rd=new RollCall_Dialog.Builder(this);


        if(countdown_time!="DONE") {
            rd.setTitle("離開此頁面?").
                    setMessage("點名尚未結束,是否離開?").
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
                            Toast.makeText(context, "繼續點名", Toast.LENGTH_LONG).show();
                        }
                    });

            rd.show();
        }else{

            rd.setTitle("離開此頁面?").
                    setMessage("確定離開?").
                    setPositiveButton("確定", new DialogInterface.OnClickListener() {
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


    }


    //**Actionbar跟標題
    public void Acttionbar_TitleData(){

        //****Scan返回鍵監聽事件
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //**掃描的清單名稱
        Bundle bundle = getIntent().getExtras();
       Seletor_File_Name = bundle.getString("Selected_File_Name");

        //清單名稱當標題
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle(Seletor_File_Name.substring(0,Seletor_File_Name.length()-4));



    }





    void ShowRollCallResults(){

        Intent it=new Intent();
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("RollCall_successful_key",RollCall_successful_key);
        bundle.putSerializable("MainHashMapList",MainHashMapList);
        bundle.putString("Selected_File_Name",Seletor_File_Name);
        it.putExtras(bundle);

        it.setClass(this,RollCall_Result.class);

        startActivity(it);

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.ble_activity_main);


        Shawn_Test_Log_List();



        Acttionbar_TitleData();


        Button finsh_rollcall=(Button)findViewById(R.id.finsh_rollcall);
        assert finsh_rollcall != null;
        finsh_rollcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                RollCall_Dialog.Builder rd=new RollCall_Dialog.Builder(v.getContext());

                if(mBTLeScanner.isScanning()) {
                    onPause();
                    rd.setTitle("點名尚未結束?").
                            setMessage("是否結束點名,前往看結果?").
                            setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    stopScan();
                                    ShowRollCallResults();
                                    finish();
                                }
                            }).
                            setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    onRestart();
                                    Toast.makeText(v.getContext(), "繼續點名", Toast.LENGTH_LONG).show();
                                }
                            });

                    rd.show();
                }else{

                    ShowRollCallResults();
                }


            }
        });




        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Utils.toast(getApplicationContext(), "BLE not supported");
            finish();

        }


        mBTStateUpdateReceiver = new BroadcastReceiver_BTState(getApplicationContext());
        mBTLeScanner = new RollCall_Scanner_BTLE(this,300000, -75);

        mBTDevicesHashMap = new HashMap<>();
        mBTDevicesArrayList = new ArrayList<>();

        adapter = new RollCall_ListAdapter_BTLE_Devices(this, R.layout.btle_device_list_item, mBTDevicesArrayList);


        listView=(ListView)findViewById(R.id.listView_rollcall);
        listView.setAdapter(adapter);


        //*****原本scan按鍵  End***\\\
        startScan();

    }







    //****Scan返回鍵(左上角鍵頭)監聽事件 Start***\\\
    @Override
    public Intent getSupportParentActivityIntent() {
        leavel_dailog(this);
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
    protected void onRestart() {
        super.onRestart();
        registerReceiver(mBTStateUpdateReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        mBTLeScanner.start();
    }




    ///**暫時停時 停止掃描
    @Override
    protected void onPause() {
        super.onPause();



//        unregisterReceiver(mBTStateUpdateReceiver);
        Log.e("1","shawn-pause停止掃描");
        stopScan();
    }







    ///***停止掃描
    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(mBTStateUpdateReceiver);
        stopScan();
    }
    ///***停止掃描






    @Override
    public void onDestroy() {
        super.onDestroy();
        stopScan();
    }


    @Override
    public void onBackPressed() {
        leavel_dailog(this);
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
                Utils.toast(getApplicationContext(), "請打開藍芽");
            }
        }
        else if (requestCode == BTLE_SERVICES) {
            // Do something
        }

    }


    ///**掃描到的每個裝置
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Context context = view.getContext();
//
////        Utils.toast(context, "List Item clicked");
//
//        // do something with the text views and start the next activity.
//
//        stopScan();
//        //********************* 裝置名稱
//        String name = mBTDevicesArrayList.get(position).getName();
//
//        String address = mBTDevicesArrayList.get(position).getAddress();
//
//        Intent intent = new Intent(this, Activity_BTLE_Services.class);
//        intent.putExtra(Activity_BTLE_Services.EXTRA_NAME, name);
//        intent.putExtra(Activity_BTLE_Services.EXTRA_ADDRESS, address);
//        startActivityForResult(intent, BTLE_SERVICES);

    }
















    ///***由Bundle過來的路徑去掃描要的檔案
    public void addDevice(BluetoothDevice device, int rssi) {

        String address = device.getAddress();


        //**傳進來的清單,要放的ArrayList
//        ArrayList<String> ReadyScanList =new ArrayList<>();



        //**從首頁 點名Bundle過來的資料
        Bundle bundle = getIntent().getExtras();
        String Seletor_File = bundle.getString("Selected_File_Path");






        if (!mBTDevicesHashMap.containsKey(address)) {


            //**從被掃描的清單內查這個address是否在清單內
            if (ReadyScanList.contains(address)) {



                if (device.getName().startsWith("KSU")) {//////////////////////////////開頭限制


                    btleDevice = new RollCall_BTLE_Device(device);


                    //**如果hashmap的key有的話 在顯示
                    for(int i=0;i<ScanList_Key.size();i++) {
                        if (Objects.equals(btleDevice.getAddress(), ScanList_Key.get(i))) {


                            btleDevice.setName(ScanList_Name.get(i));

                            Log.e("1","-*-*----"+ScanList_Key.get(i));
                            Log.e("1","-*-*----"+ScanList_Name.get(i));



                            RollCall_successful_key.add(ScanList_Key.get(i));








                            adapter.notifyDataSetChanged();

                          mBTDevicesHashMap.put(address, btleDevice);

                          mBTDevicesArrayList.add(btleDevice);


                        }
                    }
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

    public void countdown()

    {
        mCountDown = new CountDownTimer(300000, 1000) {

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.scan_set, menu);
        mymenu = menu;

        countdown= mymenu.findItem(R.id.conutdown);
        scan = mymenu.findItem(R.id.action_scan).setIcon(R.drawable.stopscanbtn);
        countdown();
        return true;

    }

    

    //**Toolbar元鍵控制
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_scan:
                if (!mBTLeScanner.isScanning()) {
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







}
