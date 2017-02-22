package com.example.administrator.rollcall_10.rollcall;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.rollcall_10.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017/2/22.
 */

public class RollCall_Result extends Activity {

    private HashMap<String,String> MainHashMapList=new HashMap<>();

    private ArrayList<String> RollCall_successful_key=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rollcall_result_layout);




        Bundle bundle = this.getIntent().getExtras();
        MainHashMapList = (HashMap) bundle.getSerializable("MainHashMapList");
        RollCall_successful_key=bundle.getStringArrayList("RollCall_successful_key");





        String should_inpeople=String.valueOf(MainHashMapList.size());


        String real_inpeople=String.valueOf(RollCall_successful_key.size());

        String outpeople=String.valueOf(MainHashMapList.size()-RollCall_successful_key.size());


        //**應到人數
        TextView inpeople_tv=(TextView)findViewById(R.id.should_inpeople);
        inpeople_tv.setText(should_inpeople);

        //**實到人數
        TextView real_inpeople_tv=(TextView)findViewById(R.id.real_inpeople);
        real_inpeople_tv.setText(real_inpeople);

        //**未到人數
        TextView outpeople_tv=(TextView)findViewById(R.id.outpeople);
        outpeople_tv.setText(outpeople);

    }




}
