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

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.main.MainActivity;
import com.example.administrator.rollcall_10.optionmenu_editist__view.optionmenu_view_edit;
import com.example.administrator.rollcall_10.recyclerview.WatchList_RecyclerviewAdapter;

/**
 * Created by Administrator on 2016/8/11.
 */
public class Recyclerview_WatchList extends AppCompatActivity  {


    String Seletor_List_File;
    String[] ListData_Array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_watchlist);

            //****Scan返回鍵監聽事件 Start****\\
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //****Scan返回鍵監聽事件 End****\\



        Bundle bundle = getIntent().getExtras();
        Seletor_List_File=  bundle.getString("List_Name");



        //換標題
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Seletor_List_File);





        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerView_watchlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        //**清單內的資料,用陣列顯示
        ListData_Array = bundle.getStringArray("device_Imperfect");

        for(int i=0;i<ListData_Array.length;i++) {
            Log.e("1", "::---" + ListData_Array[i]);
        }

//      final String[] address = bundle.getStringArray("device_address");

        WatchList_RecyclerviewAdapter recyclerviewAdapter_watchList =new WatchList_RecyclerviewAdapter(ListData_Array);
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
        switch (item.getItemId()) {
            case R.id.action_setting_add:
               //**新增


                return true;


            case R.id.action_setting_edit:
                //**修改

                Intent option_edit= new Intent(Recyclerview_WatchList.this, optionmenu_view_edit.class); //MainActivity為主要檔案名稱
                Bundle dataMap = new Bundle();
                dataMap.putStringArray("ListData_Array", ListData_Array);
                dataMap.putString("Seletor_List_File",Seletor_List_File);

                option_edit.putExtras(dataMap);

                startActivity(option_edit);

                return true;


            default:
                return super.onOptionsItemSelected(item);


        }

    }



}
