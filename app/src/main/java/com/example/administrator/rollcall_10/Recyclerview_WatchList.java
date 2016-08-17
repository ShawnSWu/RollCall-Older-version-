package com.example.administrator.rollcall_10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/11.
 */
public class Recyclerview_WatchList extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_watchlist);

        //****Scan返回鍵監聽事件 Start****\\
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //****Scan返回鍵監聽事件 End****\\



        Bundle bundle = getIntent().getExtras();
        String Seletor_List_File=  bundle.getString("List_Name");



        //換標題
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Seletor_List_File);





        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerView_watchlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        String list[];




        final String[] name = bundle.getStringArray("devicename");



        RecyclerviewAdapter_WatchList recyclerviewAdapter_watchList =new RecyclerviewAdapter_WatchList(name);
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









}
