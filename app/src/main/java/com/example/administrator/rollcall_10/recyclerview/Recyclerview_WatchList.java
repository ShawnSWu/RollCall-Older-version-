package com.example.administrator.rollcall_10.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.device_io.Device_IO;
import com.example.administrator.rollcall_10.optionmenu_editist__view.optionmenu_view_edit;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/11.
 */
public class Recyclerview_WatchList extends AppCompatActivity  {
    String Seletor_List_File,Seletor_List_Path;

    WatchList_RecyclerviewAdapter recyclerviewAdapter_watchList;
    HashMap<String,String> txt_hashmap;
    RecyclerView recyclerView;
    private void Edit_Activity(){
        Intent option_edit= new Intent(Recyclerview_WatchList.this, optionmenu_view_edit.class); //MainActivity為主要檔案名稱
        Bundle dataMap = new Bundle();
        dataMap.putString("Seletor_List_File",Seletor_List_File);
        dataMap.putString("Seletor_List_Path",Seletor_List_Path);
        option_edit.putExtras(dataMap);

        startActivity(option_edit);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_watchlist);

        Log.e("adasd4as6d4a6sd5","onCreate");
            //****Scan返回鍵監聽事件 Start****\\
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //****Scan返回鍵監聽事件 End****\\



        Bundle bundle = getIntent().getExtras();
        Seletor_List_File=  bundle.getString("List_Name");
        Seletor_List_Path=bundle.getString("List_Path");


        //換標題
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Seletor_List_File.substring(0,Seletor_List_File.length()-4));





        recyclerView=(RecyclerView)findViewById(R.id.recyclerView_watchlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        //**清單內的資料,用陣列顯示

        txt_hashmap=(HashMap<String, String>) bundle.getSerializable("Txt_path");

        recyclerviewAdapter_watchList =new WatchList_RecyclerviewAdapter(txt_hashmap);
        recyclerView.setAdapter(recyclerviewAdapter_watchList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_setting_edit:
                Edit_Activity();

                return true;


        }

        return false;

    }

    @Override
    protected void onResume() {
        super.onResume();

        updateData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateData();
    }

    private void updateData()
    {
        Device_IO device_io=new Device_IO();
        HashMap<String,String> ListData_Array;

        ListData_Array=device_io.HashMap_ReadDataFromTxt(Seletor_List_Path);

        if(ListData_Array.size()!=0) {
            recyclerviewAdapter_watchList = new WatchList_RecyclerviewAdapter(ListData_Array);
            recyclerView.setAdapter(recyclerviewAdapter_watchList);
        }else{

        }


    }
}
