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

//        MenaulAdd =(ImageButton)frist_view.findViewById(R.id.menualadd_img);
//
//        MenaulAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                LayoutInflater inflater = (LayoutInflater) getActivity()
//                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                final View layout = inflater.inflate(R.layout.dialog_listview_seleted, null);
//
//                final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(v.getContext());
//                rollCall_dialog.setTitle(R.string.RollCall_List_Dialog_Title_WantToAdd);
//                rollCall_dialog.setView(layout);
//                rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
//                rollCall_dialog.setCancelable(false);
//                rollCall_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, v.getContext().getString(R.string.RollCall_Dialog__Button_close), close);
//                rollCall_dialog.setCancelable(true);
//                rollCall_dialog.show();
//
//
//
//
//                FileList = (ListView)layout.findViewById(R.id.dialog_list_seletor);
//                FileList.setAdapter(new File_AddList_Adapter());
//
//
//                FileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//
//
//
//                        //**成功將每個資料夾寫入
//                selected = new File(String.valueOf(files.get(position)));
//
//                        Log.e("1",":"+selected.getPath());
//                Intent it = new Intent(Intent.ACTION_VIEW);
//                it.setClass(v.getContext(), ManualAdd_BLE_MainActivity.class);
//
//                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//
//                Bundle bundle = new Bundle();
//                bundle.putString("Selected_File_Path",selected.getPath());
//                bundle.putString("Selected_File_Name",selected.getName());
//                it.putExtras(bundle);
//
//                v.getContext(). startActivity(it);
//                        rollCall_dialog.dismiss();
//
//
//
//                    }
//                });

//            }
//        });
//
        return frist_view;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Initialsettings();




        Context = getActivity().getApplicationContext();

        PeopleList =new File(I_File_Path.path_People_list);
        PeopleList.mkdirs();

//        files = filter(PeopleList.listFiles());


    }

    @Override
    public void onResume() {
        super.onResume();


    }


}

