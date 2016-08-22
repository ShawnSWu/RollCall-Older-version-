package com.example.administrator.rollcall_10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/8/18.
 */
public class SplashScreen extends Activity {

    public static final int SplashScreen_Time=5000;
    ImageView LoGo_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_layout);




        LoGo_text=(ImageView)findViewById(R.id.Logo_text);
        LoGo_text.setAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));






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

                // close this activity
                finish();
            }
        }, SplashScreen_Time);


    }

}
