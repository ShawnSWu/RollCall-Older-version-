package com.example.administrator.rollcall_10.device_io;

import android.os.Environment;

/**
 * Created by Administrator on 2016/8/15.
 */
public interface I_File_Path {


    //RollCall總檔案
    String main_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RollCall_1.0_file";//新增檔案


    //人數清單檔案用
    String path_People_list =main_path+"/People_List";


    //文字檔用
    String TextFile=".txt";


    //路徑用的斜線
    String Slash ="/";

    String Slash2 ="\\";

    //內建文字檔
    String Built_TextFile ="/第一個團.txt";
}
