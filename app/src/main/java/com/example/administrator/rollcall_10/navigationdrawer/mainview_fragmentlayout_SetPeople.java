package com.example.administrator.rollcall_10.navigationdrawer;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.rollcall_10.device_io.Device_IO;
import com.example.administrator.rollcall_10.device_io.I_File_Path;
import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.manual_add.Extra_ManualAdd_BLE_MainActivity;
import com.example.administrator.rollcall_10.manual_add.ManualAdd_BLE_MainActivity;
import com.example.administrator.rollcall_10.recyclerview.List_Long_click_RecyclerviewAdapter;
import com.example.administrator.rollcall_10.recyclerview.Setpeople_RecyclerviewAdapter;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/6.
 */
public class mainview_fragmentlayout_SetPeople extends Fragment {
    

    View frist_view;
    File selected;
    private ListView FileList;

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
        setHasOptionsMenu(true);

        PeopleList =new File(I_File_Path.path_People_list);
        PeopleList.mkdirs();

        files = Setpeople_RecyclerviewAdapter.filter(PeopleList.listFiles());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        frist_view=inflater.inflate(R.layout.mainview_fragmentlayout_setpeople,null);
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



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.setting_in_setpeople, menu);

        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id) {
            case R.id.Extra_add_person:

                show_extraAdd_dialog();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    void show_extraAdd_dialog(){

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View layout = inflater.inflate(R.layout.dialog_extra_addperson_layout, null);

        final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(layout.getContext());
        rollCall_dialog.setView(layout);
        rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
        rollCall_dialog.setCancelable(false);
        rollCall_dialog.setCancelable(true);

        TextView title=(TextView)layout.findViewById(R.id.Extra_Dialog_Title);
        TextView message=(TextView)layout.findViewById(R.id.ExtraAdd_Dialog_Message);
        title.setGravity( Gravity.CENTER_HORIZONTAL );
        message.setGravity( Gravity.CENTER_HORIZONTAL );

        Button okAndCountinue=(Button)layout.findViewById(R.id.ExtraAdd_btn_ok);
        okAndCountinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!files.isEmpty()) {
                    Extra_Add(frist_view);
                }else {
                    Toast.makeText(Context,Context.getResources().getString(R.string.RollCall_PleaseAddListFirst),Toast.LENGTH_SHORT).show();
                    rollCall_dialog.dismiss();
                }
            }
        });

        Button cancel=(Button)layout.findViewById(R.id.ExtraAdd_btn_close);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollCall_dialog.dismiss();
            }
        });

        rollCall_dialog.show();
    }



    ///***額外加入
    public void Extra_Add(final View v)
    {

        LayoutInflater inflater = (LayoutInflater) v.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_listview_addpeople_seleted, null);

        final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(v.getContext());



        Button btn_close =(Button)layout.findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rollCall_dialog.dismiss();
            }
        });
        rollCall_dialog.setView(layout);
        rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
        rollCall_dialog.setCancelable(true);
        rollCall_dialog.show();




        FileList = (ListView)layout.findViewById(R.id.dialog_list_seletor);
        FileList.setAdapter(new File_AddList_Adapter());


        FileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //**成功
                selected = new File(String.valueOf(files.get(position)));

                    Log.e("shawn","選擇的資料路徑："+ selected.getPath());

                Log.e("長度",""+new Device_IO().HashMap_ReadDataFromTxt(String.valueOf(files.get(position))).size());

                if(new Device_IO().HashMap_ReadDataFromTxt(String.valueOf(files.get(position))).size() != 0) {
                    Intent it = new Intent(Intent.ACTION_VIEW);
                    it.setClass(v.getContext(), Extra_ManualAdd_BLE_MainActivity.class);

                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    Bundle bundle = new Bundle();
                    bundle.putString("Selected_File_Path", selected.getPath());
                    bundle.putString("Selected_File_Name", selected.getName());
                    it.putExtras(bundle);

                    v.getContext().startActivity(it);
                    rollCall_dialog.dismiss();
                }
                else
                {
                    rollCall_dialog.dismiss();

                    Snackbar.make(getView(),getResources().getString(R.string.ListIsNull),
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


            }


        });


    }

    
    
    
    public class File_AddList_Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return files.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            Holder holder;
            if(v == null){


                v = LayoutInflater.from(Context).inflate(R.layout.editlist_file, null);

                holder = new Holder();
                holder.textView = (TextView) v.findViewById(R.id.text);
                holder.imageView =(ImageView)v.findViewById(R.id.imageView);
                v.setTag(holder);


            } else{

                holder = (Holder) v.getTag();

            }

            String filePath = files.get(position).getPath();
            String fileName = FilenameUtils.getName(filePath);
            holder.textView.setText(fileName.substring(0,fileName.length()-4));
            holder.imageView.setImageResource(R.mipmap.txt_list64);




            return v;
        }

        private class Holder{
            TextView textView;
            ImageView imageView;

        }


    }
    
}

