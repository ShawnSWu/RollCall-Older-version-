package com.example.administrator.rollcall_10.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.administrator.rollcall_10.device_io.I_File_Path;
import com.example.administrator.rollcall_10.main.MainActivity;
import com.example.administrator.rollcall_10.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Administrator on 2016/8/18.
 */
public class SplashScreen extends Activity {

    public static final int SplashScreen_Time=3000;
    ImageView LoGo_text;
    File peoplefile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_layout);


        File  PeopleList =new File(I_File_Path.path_People_list);
        PeopleList.mkdirs();

        //**內鍵創建文字檔 strat--->
        peoplefile = new File(I_File_Path.path_People_list + I_File_Path.Built_TextFile);

        if(!peoplefile.exists()) {
            try {
                peoplefile.createNewFile();
                Log.e("1", "內建文字檔建立成功");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("1", "內建文字檔建立失敗");
            }
        }

        //**內鍵創建文字檔 End--->


        LoGo_text=(ImageView)findViewById(R.id.Logo_text);







        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                ///******************Splash Screen畫面     主畫面
                Intent i = new Intent(SplashScreen.this, MainActivity.class); //MainActivity為主要檔案名稱
                startActivity(i);


            }
        }, SplashScreen_Time);


    }

}
