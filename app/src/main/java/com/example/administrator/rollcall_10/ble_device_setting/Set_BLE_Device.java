package com.example.administrator.rollcall_10.ble_device_setting;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.device_io.I_File_Path;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Set_BLE_Device extends AppCompatActivity {

    private NumberPicker numberPicker_Seconds,numberPicker_minute;
    private TextView txt_second,spenner_name,txt_minute;
    private Button button;
    private Spinner spinner;
    private  String[] spinner_ListName;
    private File selected;
    private ArrayList<File> files;
    private File PeopleList;



   private static int choose_sencond, choose_minute;



    private void NumberPicker(){

            //**秒數NumberPiker
            numberPicker_Seconds=(NumberPicker)findViewById(R.id.numberPicker_min);
            numberPicker_Seconds.setMinValue(I_Set_BLEDevice.numberPicker_min);
            numberPicker_Seconds.setMaxValue(I_Set_BLEDevice.numberPicker_max);

            numberPicker_Seconds.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    if(newVal<10) {
                        txt_second.setText("0" + newVal);
                    }else{
                        txt_second.setText("" + newVal);
                    }
                    choose_sencond =newVal;
                }
            });


            //**分鐘NumberPiker
            numberPicker_minute=(NumberPicker)findViewById(R.id.numberPicker_minute);
            numberPicker_minute.setMinValue(I_Set_BLEDevice.numberPicker_min);
            numberPicker_minute.setMaxValue(I_Set_BLEDevice.numberPicker_max);

            numberPicker_minute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    txt_minute.setText(""+newVal);

                    choose_minute =newVal;
                }
            });

        }


    private void Spinner(){

        spinner=(Spinner)findViewById(R.id.spinner);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,filestring());

        spinner.setAdapter(arrayAdapter);
        spenner_name=(TextView)findViewById(R.id.spenner_name);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spenner_name.setText(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set__ble__device_layout);

        //****Scan返回鍵監聽事件 Start****\\
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NumberPicker();

        Spinner();



        txt_second =(TextView)findViewById(R.id.txt_sencond);
        txt_minute=(TextView)findViewById(R.id.txt_minute);


        button= (Button)findViewById(R.id.send);
        button.setOnClickListener(btn);

    }






    private String[] filestring(){
        PeopleList =new File(I_File_Path.path_People_list);

        files = filter(PeopleList.listFiles());

        spinner_ListName=new String[files.size()];

        for(int i=0;i<files.size();i++){
            spinner_ListName[i]=files.get(i).getName().substring(0,files.get(i).getName().length()-4);
        }

            return spinner_ListName;
    }





    View.OnClickListener btn =new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            //**05/06**使用者選擇的秒數總和
            int total_sencond = choose_sencond +(choose_minute *60);



            selected = new File(String.valueOf(files.get(spinner.getSelectedItemPosition())));



            if(selected.length() ==0) {

                Toast.makeText(getApplicationContext(), "清單是空的!!", Toast.LENGTH_LONG).show();



            }else {

                Log.e("1", ":AA" + selected.getName());


                Intent it = new Intent(Intent.ACTION_VIEW);
                it.setClass(getApplicationContext(), setBLEDevice_connect.class);


                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putString("Selected_File_Path", selected.getPath());
                bundle.putInt("Choose_second", total_sencond);
                it.putExtras(bundle);

               getApplicationContext().startActivity(it);



            }





        }
    };

//
//    public void Countdown(int Countdown_time){
//
//        //***第一個參數是總共計時幾秒,第二個參數是每隔幾秒跳一次
//        new CountDownTimer(Countdown_time,I_Set_BLEDevice.Onesecond){
//
//
//            @Override
//            public void onFinish() {
//                mTextView.setText("Done!");
//                setVibrate(I_Set_BLEDevice.shocktime);
//            }
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//
//                mTextView.setText(I_Set_BLEDevice.Remaining_time + millisUntilFinished/1000);
//
//            }
//
//        }.start();
//
//    }





    public void setVibrate(int time){
        Vibrator myVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        myVibrator.vibrate(time);
    }






    public ArrayList<File> filter(File[] fileList) {
        ArrayList<File> files = new ArrayList<File>();
        if(fileList == null){
            return files;
        }
        for(File file: fileList) {
            if(!file.isDirectory() && file.isHidden()) {
                continue;
            }
            files.add(file);
        }
        Collections.sort(files);
        return files;
    }



    //****Scan返回鍵(左上角鍵頭)監聽事件 Start***\\\
    @Override
    public Intent getSupportParentActivityIntent() {
        finish();
        return null;
    }
    //****Scan返回鍵(左上角鍵頭)監聽事件 End***\\\


}
