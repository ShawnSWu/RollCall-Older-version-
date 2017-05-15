package com.example.administrator.rollcall_10.navigationdrawer;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.administrator.rollcall_10.auto_add.AutoAdd_BLE_MainActivity;
import com.example.administrator.rollcall_10.device_io.I_File_Path;
import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.recyclerview.Setpeople_RecyclerviewAdapter;
import com.example.administrator.rollcall_10.device_io.Device_IO;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/6.
 */
public class mainview_fragmentlayout_SetPeople extends Fragment {


  AutoAdd_BLE_MainActivity autoAdd_ble_mainActivity;


    private Context Context;
    private ArrayList<File> files;

    private File PeopleList;


    private static mainview_fragmentlayout_SetPeople mainview_fragmentlayout_setPeople;


   public static mainview_fragmentlayout_SetPeople mainview_fragmentlayout_SetPeople_getIntance(){

       if(mainview_fragmentlayout_setPeople==null){
           mainview_fragmentlayout_setPeople=new mainview_fragmentlayout_SetPeople();
       }

       return mainview_fragmentlayout_setPeople;
   }

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;



    private void Initialsettings(){

        recyclerView =
                (RecyclerView)getActivity().findViewById(R.id.recyclerView_setpeople);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        adapter = new Setpeople_RecyclerviewAdapter(Context);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //換標題
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.RollCall_Fragment_Title_Setpeople);



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
        Initialsettings();

        Context = getActivity().getApplicationContext();

        PeopleList =new File(I_File_Path.path_People_list);
        PeopleList.mkdirs();


    }

    @Override
    public void onResume() {
        super.onResume();


    }


}

