package com.example.administrator.rollcall_10.optionmenu_editist__view;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.device_io.Device_IO;
import com.example.administrator.rollcall_10.device_io.I_File_Path;
import com.example.administrator.rollcall_10.navigationdrawer.mainview_fragmentlayout_EditList;
import com.example.administrator.rollcall_10.recyclerview.List_Long_click_RecyclerviewAdapter;
import com.example.administrator.rollcall_10.recyclerview.OptionEdit_List_Long_click_RecyclerviewAdapter;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import java.io.File;

public class optionmenu_view_edit extends AppCompatActivity {

    String Seletor_List_Path,Seletor_List_File;
    String[] ListData_Array;
    File selected;
    ListView Listview_optionmenu_edit_view;


    Device_IO device_io=new Device_IO();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optionmenu_view_edit);


        Bundle bundle = getIntent().getExtras();

        //**陣列資料
        ListData_Array = bundle.getStringArray("ListData_Array");

        //**清單名稱
        Seletor_List_File= bundle.getString("Seletor_List_File");

        //**清單路徑
        Seletor_List_Path= bundle.getString("Seletor_List_Path");

        //換標題
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Seletor_List_File.substring(0,Seletor_List_File.length()-4));



        selected=new File(Seletor_List_Path);




        Log.e("shawn","清單的路徑:"+selected.getPath());













        Listview_optionmenu_edit_view =(ListView)findViewById(R.id.optionmenu_edit_view);
        ListAdapter adapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 ,ListData_Array);
        Listview_optionmenu_edit_view.setAdapter(adapter);






        Listview_optionmenu_edit_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {


                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                final View layout = inflater.inflate(R.layout.dialog_list_longclick_layout, null);

                TextView title_txt=(TextView)layout.findViewById(R.id.Dialog_Title);

                title_txt.setText(ListData_Array[position]);


                RecyclerView recyclerView=(RecyclerView)layout.findViewById(R.id.recyclerView_list_long_click);
                recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

                String item_txt[]={"修改","刪除"};


                int item_image[]={R.mipmap.pen_rename,R.mipmap.recyclerbin64};



                final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(layout.getContext());
                rollCall_dialog.setView(layout);
                rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
                rollCall_dialog.setCancelable(false);
                rollCall_dialog.setCancelable(true);

                ///**關閉dialog
                Button btn_close =(Button)layout.findViewById(R.id.btn_close);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rollCall_dialog.dismiss();
                    }
                });

                OptionEdit_List_Long_click_RecyclerviewAdapter optionEdit_List_long_click_recyclerviewAdapter =new OptionEdit_List_Long_click_RecyclerviewAdapter(item_txt,item_image,selected,v.getContext(),rollCall_dialog);
                recyclerView.setAdapter(optionEdit_List_long_click_recyclerviewAdapter);

                recyclerView.setItemAnimator(new DefaultItemAnimator());


                rollCall_dialog.show();





                return true;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finsh, menu);

                return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }



}
