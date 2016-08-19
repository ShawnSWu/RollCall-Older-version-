package com.example.administrator.rollcall_10;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    Device_IO device_io =new Device_IO();

    private Context context;

    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RollCall_1.0_file/People_List";//新增檔案






    //Toolbar
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //**內鍵創建文字檔 strat--->
        File peoplefile = new File(path + "/" + R.string.RollCall_local_folder + ".txt");

        try {
            FileWriter fw = new FileWriter(peoplefile, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //**自行創建文字檔 End--->




        //**Toolbar"三"線的變化
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //**Toolbar"三"線的變化


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.main_fragment, new mainview_fragmentlayout_Home())
                .commitAllowingStateLoss();
    }


    //**Toolbar箭頭的點擊事件
    public boolean onOptionsItemSelected(MenuItem item) {

        //home
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //**Toolbar箭頭的點擊事件




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            Log.d("shawn", "返回鍵不會關掉整個Activity");
            Intent setIntent = new Intent(Intent.ACTION_MAIN);
            setIntent.addCategory(Intent.CATEGORY_HOME);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(setIntent);

        }

    }






    //**Toolbar其他元件點擊事件
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }
    //**Toolbar其他元件點擊事件






    //**左選單項目點擊事件
    @TargetApi(Build.VERSION_CODES.M)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment mainview = null;

        switch (id){
            case R.id.Home:
                mainview=new mainview_fragmentlayout_Home();
                break;

            case R.id.Set_People:
                mainview=new mainview_fragmentlayout_SetPeople();
                break;

            case R.id.Edit_List:
                mainview=new mainview_fragmentlayout_EditList();
                break;

            case R.id.Question:
                mainview=new mainview_fragmentlayout_Question();
                break;

            case R.id.Contact_Us:
                mainview=new mainview_fragmentlayout_ContactUs();
                break;
        }


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, mainview)
                .commitAllowingStateLoss();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
    //**左選單項目點擊事件








}
