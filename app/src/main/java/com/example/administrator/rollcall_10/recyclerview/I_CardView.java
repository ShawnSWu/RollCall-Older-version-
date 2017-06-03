package com.example.administrator.rollcall_10.recyclerview;

import com.example.administrator.rollcall_10.R;

/**
 * Created by Administrator on 2016/8/19.
 */
public interface I_CardView {
//    String.valueOf(R.string.NotYetEnteredFileName)

    ///***SetPeople CardView
    String ManualAdd_Title ="手動編號加入";
    String AutoAdd_Title="自動編號加入";
    String ManualAdd_detail = "您可以手動編輯自己要的" ;
    String AutoAdd_detail = "您只需要過濾自己不需要的" ;




    ///***Home CardView Title
    String RollCall ="點名";
//    String Watch_device_status ="查看裝置狀態";
    String Set_BLE_Device ="設定集合時間";
    String Memorandum ="待辦事項";
    String Calendar ="RollCall日曆";
    String googlemap ="RollCall地圖";



    ///***Home CardView Detail
    String RollCall_detail ="開始點名";
//    String Watch_device_status_detail ="目前藍芽追蹤器的狀態";
    String Set_BLE_Device_detail ="設定時間提醒遊客";
    String Memorandum_detail ="可以設置行程提醒";
    String Calendar_detail ="查詢日後行程！";
    String googlemap_detail ="導航至您要的所在地";
}
