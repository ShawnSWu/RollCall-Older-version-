package com.example.administrator.rollcall_10;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/11.
 */
public class Recyclerview_WatchList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_watchlist);


        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerView_watchlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        String list[];



        Bundle bundle = getIntent().getExtras();
        final String[] name = bundle.getStringArray("devicename");



        RecyclerviewAdapter_WatchList recyclerviewAdapter_watchList =new RecyclerviewAdapter_WatchList(name);
        recyclerView.setAdapter(recyclerviewAdapter_watchList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());




    }
}
