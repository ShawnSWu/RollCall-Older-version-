package com.example.administrator.rollcall_10;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Administrator on 2016/8/6.
 */
public class mainview_fragmentlayout_SetPeople extends Fragment {


    ImageButton MenaulAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View frist_view=inflater.inflate(R.layout.mainview_fragmentlayout_setpeople,null);
        return frist_view;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        MenaulAdd =(ImageButton)getActivity().findViewById(R.id.menualadd_img);
        MenaulAdd.setOnClickListener(menauladd);
    }


    public View.OnClickListener menauladd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ManualAdd_BLE_MainActivity.class);
            startActivity(intent);
        }
    };
}

