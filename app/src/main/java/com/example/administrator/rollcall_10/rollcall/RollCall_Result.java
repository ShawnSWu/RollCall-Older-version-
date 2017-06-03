package com.example.administrator.rollcall_10.rollcall;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;
import com.shinelw.library.ColorArcProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017/2/22.
 */

public class RollCall_Result extends AppCompatActivity {

    private HashMap<String,String> MainHashMapList=new HashMap<>();

    String should_inpeople,real_inpeople, outpeople;

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

        should_inpeople=String.valueOf(MainHashMapList.size());


        real_inpeople=String.valueOf(RollCall_successful_key.size());

        outpeople=String.valueOf(MainHashMapList.size()-RollCall_successful_key.size());


        //**總共人數
        TextView inpeople_tv=(TextView)findViewById(R.id.total_people);
        if (inpeople_tv != null) {
            inpeople_tv.setText(should_inpeople);
        }

        //**未到人數
        TextView real_inpeople_tv=(TextView)findViewById(R.id.Not_Arrival_People);
        if (real_inpeople_tv != null)
        {
            real_inpeople_tv.setText(outpeople);
        }

    }

    private void leavel_dailog(){

        RollCall_Dialog.Builder rd=new RollCall_Dialog.Builder(this);


        rd.setTitle(getResources().getString(R.string.LeaveThiePage)).
                setMessage(getResources().getString(R.string.LeaveThiePage_btnYes)).
                setPositiveButton(getResources().getString(R.string.RollCall_List_Delete_Button_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).
                setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
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


        for(int i=0;i<RollCall_successful_key.size();i++)
        {
            MainHashMapList.remove(RollCall_successful_key.get(i));
        }

        Result_RecyclerView result_recyclerView=(Result_RecyclerView)findViewById(R.id.outpeople_list_layout);
        result_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        result_recyclerView.setEmptyView(findViewById(R.id.All_People_Arrivals));

        OutPeopleList_RecyclerviewAdapter qutPeopleList_RecyclerviewAdapter =new OutPeopleList_RecyclerviewAdapter(MainHashMapList);

        result_recyclerView.setAdapter(qutPeopleList_RecyclerviewAdapter);
        result_recyclerView.setItemAnimator(new DefaultItemAnimator());



        ColorArcProgressBar progressBar_outpeople = (ColorArcProgressBar)findViewById(R.id.outpeople_progressBar);
        progressBar_outpeople.setMaxValues(Integer.valueOf(should_inpeople));
        progressBar_outpeople.setCurrentValues(Integer.valueOf(real_inpeople));


    }

    //****Scan返回鍵(左上角鍵頭)監聽事件 Start***\\\
    @Override
    public Intent getSupportParentActivityIntent() {
        leavel_dailog();
        return null;
    }
    //****Scan返回鍵(左上角鍵頭)監聽事件 End***\\\



}
