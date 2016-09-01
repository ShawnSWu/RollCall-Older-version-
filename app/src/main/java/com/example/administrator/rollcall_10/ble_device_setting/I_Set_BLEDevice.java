package com.example.administrator.rollcall_10.ble_device_setting;

/**
 * Created by Administrator on 2016/8/15.
 */
public interface I_Set_BLEDevice {

    //倒數計時器時間
    String Remaining_time ="剩下的時間：";


    //**一秒跳一次 倒數計時用
    int Onesecond= 1000;

    //**倒數時間到時 震動的秒數
    int shocktime =1500;


    //**NumberPicker最小值
    int numberPicker_min=0;

    //**NumberPicker最大值
    int numberPicker_max=59;

}
