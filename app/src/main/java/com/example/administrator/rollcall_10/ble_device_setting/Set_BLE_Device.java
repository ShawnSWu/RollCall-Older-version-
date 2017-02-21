package com.example.administrator.rollcall_10.ble_device_setting;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.ble_device_setting.I_Set_BLEDevice;

public class Set_BLE_Device extends AppCompatActivity {

    NumberPicker numberPicker_Seconds,numberPicker_minute,numberPicker_Hour;

    TextView mTextView;

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set__ble__device_layout);

        //****Scan返回鍵監聽事件 Start****\\
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        Log.e("UART","fucking guy");

        //**秒數NumberPiker
        numberPicker_Seconds=(NumberPicker)findViewById(R.id.numberPicker_Seconds);
        numberPicker_Seconds.setMinValue(I_Set_BLEDevice.numberPicker_min);
        numberPicker_Seconds.setMaxValue(I_Set_BLEDevice.numberPicker_max);





        //**分鐘NumberPiker
        numberPicker_minute=(NumberPicker)findViewById(R.id.numberPicker_minute);
        numberPicker_minute.setMinValue(I_Set_BLEDevice.numberPicker_min);
        numberPicker_minute.setMaxValue(I_Set_BLEDevice.numberPicker_max);




        //**小時NumberPiker
        numberPicker_Hour=(NumberPicker)findViewById(R.id.numberPicker_Hour);
        numberPicker_Hour.setMinValue(I_Set_BLEDevice.numberPicker_min);
        numberPicker_Hour.setMaxValue(I_Set_BLEDevice.numberPicker_max);





        mTextView=(TextView)findViewById(R.id.text_view);

        button= (Button)findViewById(R.id.button);
        button.setOnClickListener(btn);
    }


    View.OnClickListener btn =new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            Log.e("UART","fucking guy");


            int Countdown_time = numberPicker_Hour.getValue()*1000*60*60   +  numberPicker_minute.getValue()*1000*60 + numberPicker_Seconds.getValue()*1000;



            Countdown(Countdown_time);


        }
    };


    public void Countdown(int Countdown_time){

        //***第一個參數是總共計時幾秒,第二個參數是每隔幾秒跳一次
        new CountDownTimer(Countdown_time,I_Set_BLEDevice.Onesecond){


            @Override
            public void onFinish() {
                mTextView.setText("Done!");
                setVibrate(I_Set_BLEDevice.shocktime);
            }

            @Override
            public void onTick(long millisUntilFinished) {


                mTextView.setText(I_Set_BLEDevice.Remaining_time + millisUntilFinished/1000);

            }

        }.start();

    }





    public void setVibrate(int time){
        Vibrator myVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        myVibrator.vibrate(time);
    }















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.setting, menu);
        return true;

    }


    //****Scan返回鍵(左上角鍵頭)監聽事件 Start***\\\
    @Override
    public Intent getSupportParentActivityIntent() {
        finish();
        return null;
    }
    //****Scan返回鍵(左上角鍵頭)監聽事件 End***\\\


}
