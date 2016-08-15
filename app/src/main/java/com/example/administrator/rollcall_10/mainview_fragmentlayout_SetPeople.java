package com.example.administrator.rollcall_10;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/8/6.
 */
public class mainview_fragmentlayout_SetPeople extends Fragment {


    ImageButton MenaulAdd;

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


        MenaulAdd =(ImageButton)getActivity().findViewById(R.id.menualadd_img);
        MenaulAdd.setOnClickListener(menauladd);
    }


    public View.OnClickListener menauladd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

//            LayoutInflater inflater = (LayoutInflater) getActivity()
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.dialog_listview_seleted, null);








            RollCall_Dialog rollCall_dialog = new RollCall_Dialog(v.getContext());
            rollCall_dialog.setTitle("想要加入人數的清單");
//          rollCall_dialog.setContentView(layout);
            rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
            rollCall_dialog.setCancelable(false);
            rollCall_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, v.getContext().getString(R.string.RollCall_Dialog__Button_close), close);
            rollCall_dialog.show();






            //**成功將每個資料夾寫入
//                Intent it = new Intent(Intent.ACTION_VIEW);
//                it.setClass(v.getContext(), ManualAdd_BLE_MainActivity.class);
//
//                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//
//                Bundle bundle = new Bundle();
//                bundle.putString("Selected_File_Path",selected.getPath());
//                it.putExtras(bundle);
//
//                v.getContext(). startActivity(it);
        }
    };


    DialogInterface.OnClickListener close = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {         }

    };
}

