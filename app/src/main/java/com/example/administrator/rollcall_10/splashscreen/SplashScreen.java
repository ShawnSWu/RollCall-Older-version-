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
import com.example.administrator.rollcall_10.note.Async_Note_content;
import com.example.administrator.rollcall_10.note.Async_Update_Database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Administrator on 2016/8/18.
 */
public class SplashScreen extends Activity {

    public static final int SplashScreen_Time=3000;
    ImageView LoGo_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_layout);

        new Async_Update_Database(this).execute();


        File  PeopleList =new File(I_File_Path.path_People_list);

        if(!PeopleList.exists())
        {
            PeopleList.mkdirs();
        }



        LoGo_text=(ImageView)findViewById(R.id.Logo_text);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);


            }
        }, SplashScreen_Time);


    }

}
