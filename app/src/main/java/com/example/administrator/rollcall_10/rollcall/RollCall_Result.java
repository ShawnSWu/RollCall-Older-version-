package com.example.administrator.rollcall_10.rollcall;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 2017/2/22.
 */

public class RollCall_Result extends AppCompatActivity {

    private HashMap<String,String> MainHashMapList=new HashMap<>();



    private ArrayList<String> RollCall_successful_key=new ArrayList<>();




    private String Selected_File_Name ;


    //**Actionbar跟標題
    public void Acttionbar_TitleData_And_init(){

        //****Scan返回鍵監聽事件
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = this.getIntent().getExtras();
        MainHashMapList = (HashMap<String,String>) bundle.getSerializable("MainHashMapList");
        RollCall_successful_key=bundle.getStringArrayList("RollCall_successful_key");
        Selected_File_Name=bundle.getString("Selected_File_Name");


        //清單名稱當標題
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle(Selected_File_Name.substring(0,Selected_File_Name.length()-4));

        String should_inpeople=String.valueOf(MainHashMapList.size());


        String real_inpeople=String.valueOf(RollCall_successful_key.size());

        String outpeople=String.valueOf(MainHashMapList.size()-RollCall_successful_key.size());


        //**應到人數
        TextView inpeople_tv=(TextView)findViewById(R.id.should_inpeople);
        if (inpeople_tv != null) {
            inpeople_tv.setText(should_inpeople+" ； ");
        }

        //**實到人數
        TextView real_inpeople_tv=(TextView)findViewById(R.id.real_inpeople);
        if (real_inpeople_tv != null) {
            real_inpeople_tv.setText(real_inpeople);
        }

        //**未到人數
        TextView outpeople_tv=(TextView)findViewById(R.id.outpeople);
        if (outpeople_tv != null) {
            outpeople_tv.setText(outpeople);
        }

    }

    private void leavel_dailog(){

        RollCall_Dialog.Builder rd=new RollCall_Dialog.Builder(this);


        rd.setTitle("離開此頁面?").
                setMessage("確定離開?").
                setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        rd.show();



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rollcall_result_layout);

        Acttionbar_TitleData_And_init();


        for(int i=0;i<RollCall_successful_key.size();i++){
            MainHashMapList.remove(RollCall_successful_key.get(i));
        }

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.outpeople_list_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        OutPeopleList_RecyclerviewAdapter qutPeopleList_RecyclerviewAdapter =new OutPeopleList_RecyclerviewAdapter(MainHashMapList);

        recyclerView.setAdapter(qutPeopleList_RecyclerviewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }














    //****Scan返回鍵(左上角鍵頭)監聽事件 Start***\\\
    @Override
    public Intent getSupportParentActivityIntent() {
        leavel_dailog();
        return null;
    }
    //****Scan返回鍵(左上角鍵頭)監聽事件 End***\\\



}
