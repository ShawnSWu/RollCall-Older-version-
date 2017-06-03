package com.example.administrator.rollcall_10.main;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.Toast;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.device_io.Device_IO;
import com.example.administrator.rollcall_10.navigationdrawer.mainview_fragmentlayout_ContactUs;
import com.example.administrator.rollcall_10.navigationdrawer.mainview_fragmentlayout_EditList;
import com.example.administrator.rollcall_10.navigationdrawer.mainview_fragmentlayout_Home;
import com.example.administrator.rollcall_10.navigationdrawer.mainview_fragmentlayout_Question;
import com.example.administrator.rollcall_10.navigationdrawer.mainview_fragmentlayout_SetPeople;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    private final String PERMISSION_WRITE_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    private final String PERMISSION_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";

    //Toolbar
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;





    //MarshMallow(API-23)之後要在 Runtime 詢問權限
    private boolean needCheckPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {PERMISSION_WRITE_STORAGE,PERMISSION_COARSE_LOCATION};
            int permsRequestCode = 200;
            requestPermissions(perms, permsRequestCode);
            return true;
        }
        return false;
    }

    //開啟權限
    private boolean hasPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return(ActivityCompat.checkSelfPermission(this, PERMISSION_WRITE_STORAGE) == PackageManager.PERMISSION_GRANTED) &&(ActivityCompat.checkSelfPermission(this, PERMISSION_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 200){
            if (grantResults.length > 0) {

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(">>>", "取得授權，可以執行動作了1"+grantResults[0]);

                    UI();
                }else
                {
                    Toast.makeText(this,getResources().getString(R.string.PleaseTurnOnPermissions),Toast.LENGTH_LONG).show();
                    finish();
                }

                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(">>>", "取得授權，可以執行動作了2");
                    UI();
                }else
                {
                    Toast.makeText(this,getResources().getString(R.string.PleaseTurnOnPermissions),Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }


    //UI元件載入
    private void UI(){
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
            .replace(R.id.main_fragment, mainview_fragmentlayout_Home.getMainview_fragmentlayout_Home_Intance())
            .commitAllowingStateLoss();
}


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UI();
        if (!hasPermission()) {
            if (needCheckPermission()) {
                UI();

            }
        }

    }


    //**Toolbar箭頭的點擊事件
    public boolean onOptionsItemSelected(MenuItem item)
    {

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





//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.setting, menu);
//        return true;
//    }
//






    //**左選單項目點擊事件
    @TargetApi(Build.VERSION_CODES.M)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment mainview = null;




        switch (id){
            case R.id.Home:
                mainview=mainview_fragmentlayout_Home.getMainview_fragmentlayout_Home_Intance();

                Log.e("Home",""+mainview.hashCode());

                break;

            case R.id.Set_People:
                mainview=mainview_fragmentlayout_SetPeople.mainview_fragmentlayout_SetPeople_getIntance();
                Log.e("setpeople",""+mainview.hashCode());
                break;

            case R.id.Edit_List:
                mainview=mainview_fragmentlayout_EditList.mainview_fragmentlayout_editList_getIntace();
                Log.e("Edit_List",""+mainview.hashCode());
                break;


            case R.id.Contact_Us:
                mainview=mainview_fragmentlayout_ContactUs.mainview_fragmentlayout_ContactUs_getIntace();
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
