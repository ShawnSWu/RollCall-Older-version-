package com.example.administrator.rollcall_10;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Administrator on 2016/8/6.
 */
public class mainview_fragmentlayout_Home extends Fragment {

    Context context;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    ImageButton scanbtn,listbtn;
    MainActivity mainActivity=new MainActivity();
    ManualAdd_BLE_MainActivity manualAdd_ble_mainActivity =new ManualAdd_BLE_MainActivity();



    private void Initialsettings(){

//        scanbtn=(ImageButton)getActivity().findViewById(R.id.Home_RollCall_img);
//        scanbtn.setOnClickListener(scan);
//
//        listbtn=(ImageButton)getActivity().findViewById(R.id.Home_SeeList_img);
//        listbtn.setOnClickListener(Listbtn);


        recyclerView =
                (RecyclerView)getActivity().findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        adapter = new Home_RecyclerAdapter(context);
        recyclerView.setAdapter(adapter);





    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //換標題
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.RollCall_Fragment_Title_Home);
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









//    public View.OnClickListener scan = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent();
//            intent.setClass(getActivity(), BLE_MainActivity.class);
//            startActivity(intent);
//        }
//    };
//






//    public View.OnClickListener Listbtn = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            if(mainActivity.file.length()==0){
//
//
//                RollCall_Dialog rollCall_dialog = new RollCall_Dialog(getActivity());
//                rollCall_dialog.setTitle(R.string.RollCall_Dialog_Title_ListEmpty);
//                rollCall_dialog.setMessage(getResources().getString(R.string.RollCall_Dialog__Message_GoToScan));
//                rollCall_dialog.setIcon(R.mipmap.exclamation128);
//                rollCall_dialog.setCancelable(false);
////                rollCall_dialog.setButton(DialogInterface.BUTTON_POSITIVE,getResources().getString(R.string.RollCall_Dialog__Button_GoToAddDevice),null);
//                rollCall_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.RollCall_Dialog__Button_close), close);
//
//                rollCall_dialog.show();
//
//
//
//                TextView messageText = (TextView)rollCall_dialog.findViewById( android.R.id.message );
//                messageText.setGravity( Gravity.CENTER_HORIZONTAL );
//
//
//
//            }
//            else {
//
//                Intent it = new Intent(Intent.ACTION_VIEW);
//                it.setClass(getActivity(), Watch_List.class);
//
//                String x = "";
//
//                Bundle bundle = new Bundle();
//                bundle.putStringArray("devicename", manualAdd_ble_mainActivity.readData(mainActivity.file, x));
//                it.putExtras(bundle);
//
//                startActivity(it);
//
//            }
//
//        }
//    };

//    public DialogInterface.OnClickListener GoToSetPeople = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//
//               getFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.main_fragment, new mainview_fragmentlayout_SetPeople())
//                    .commit();
//
//
//        }
//
//    };




//    public DialogInterface.OnClickListener close = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//
//
//
//
//        }
//
//    };

}