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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.rollcall_10.recyclerview.Home_RecyclerviewAdapter;
import com.example.administrator.rollcall_10.R;

/**
 * Created by Administrator on 2016/8/6.
 */
public class mainview_fragmentlayout_Home extends Fragment {

    Context context;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    private static mainview_fragmentlayout_Home mainview_fragmentlayout_home=new mainview_fragmentlayout_Home();

    private mainview_fragmentlayout_Home(){}




    private void Initialsettings(){

        recyclerView =
                (RecyclerView)getActivity().findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        adapter = new Home_RecyclerviewAdapter(context);
        recyclerView.setAdapter(adapter);

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //換標題
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.RollCall_Fragment_Title_Home);


    }




    public static mainview_fragmentlayout_Home getMainview_fragmentlayout_Home_Intance(){

        if(mainview_fragmentlayout_home==null) {
            mainview_fragmentlayout_home=new mainview_fragmentlayout_Home();
        }

        return mainview_fragmentlayout_home;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View frist_view = inflater.inflate(R.layout.mainview_fragmentlayout_home, null);
        return frist_view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Initialsettings();

}


}