package com.example.administrator.rollcall_10.recyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.device_io.Device_IO;
import com.example.administrator.rollcall_10.optionmenu_editist__view.optionmenu_view_edit;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/11.
 */
public class OptionEdit_List_Long_click_RecyclerviewAdapter extends RecyclerView.Adapter<OptionEdit_List_Long_click_RecyclerviewAdapter.ViewHolder> {


    private String[] longclick_item;

    private int[] image;

    private String pathstring;

    private ArrayList<String> ListData_Array=new ArrayList<>();

    private HashMap<String,String> ReadFileList=new HashMap<>();
    private int position;

    private Device_IO device_io=new Device_IO();


    RollCall_Dialog option_rollCall_dialog;

    ListView Listview_optionmenu_edit_view;

    ArrayAdapter<String> arrayAdapter;
    Context context;

    public OptionEdit_List_Long_click_RecyclerviewAdapter(String[] item, int[] image, ArrayList<String> ListData_Array, HashMap<String,String> ReadFileList,
                                                          String pathstring, int position,RollCall_Dialog option_rollCall_dialog,ListView Listview_optionmenu_edit_view,ArrayAdapter arrayAdapter,Context context) {
        this.longclick_item=item;
        this.image=image;
        this.ListData_Array=ListData_Array;
        this.ReadFileList=ReadFileList;
        this.position=position;
        this.pathstring=pathstring;
        this.option_rollCall_dialog=option_rollCall_dialog;
        this.Listview_optionmenu_edit_view=Listview_optionmenu_edit_view;
        this.arrayAdapter=arrayAdapter;
        this.context=context;
    }





    @Override
    public OptionEdit_List_Long_click_RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_listlongclick_item_layout, parent, false);
        ViewHolder viewholder = new ViewHolder(view);


        return viewholder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.Longclick_Item.setText(longclick_item[position]);
        holder.image.setImageResource(image[position]);

    }




    @Override
    public int getItemCount() {
        return longclick_item.length;
    }





    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView Longclick_Item;
        public ImageView image;


        public ViewHolder(View v)
        {
            super(v);
            Longclick_Item = (TextView) v.findViewById(R.id.longclick_item);
            image =(ImageView)v.findViewById(R.id.item_image);



            v.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    switch (getAdapterPosition()){
                        case 0:
                            Edit_item_Name(v);

                            break;

                        case 1:
                             delete_item(v);

                            break;




                    }

                }
            });

        }

    }

    public void Edit_item_Name(View v){

        LayoutInflater inflater = LayoutInflater.from(v.getContext());
        final View layout = inflater.inflate(R.layout.dialog_edit_item_layout, null);



        final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(layout.getContext());
        rollCall_dialog.setView(layout);
        rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
        rollCall_dialog.setCancelable(false);
        rollCall_dialog.setCancelable(true);

        final EditText edit_name =(EditText)layout.findViewById(R.id.original_name_edit);
        edit_name.setHint(ListData_Array.get(position).split(",")[0]);



        Button btn_close =(Button)layout.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollCall_dialog.dismiss();
            }
        });

        //**編輯的dialog的確認鍵
        Button btn_ok =(Button)layout.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                device_io.EditDataFromTxt(ListData_Array.get(position).split(",")[1],edit_name.getText().toString(),ReadFileList,pathstring);

                updateData();
                arrayAdapter.notifyDataSetChanged();

                rollCall_dialog.dismiss();
                option_rollCall_dialog.dismiss();


            }



        });

        rollCall_dialog.show();

    }


    public void delete_item(View v){

        RollCall_Dialog.Builder rd=new RollCall_Dialog.Builder(v.getContext());
        rd.setTitle(ListData_Array.get(position).split(",")[0]).
                setMessage( "刪除" + ListData_Array.get(position).split(",")[0] + "這個項目嗎?").
                setPositiveButton("確定刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        device_io.DeleteDataFromTxt(ListData_Array.get(position).split(",")[1],ReadFileList,pathstring);

                        updateData();
                        arrayAdapter.notifyDataSetChanged();
                        option_rollCall_dialog.dismiss();


                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });

        rd.show();



    }


    private void updateData(){

        Device_IO device_io=new Device_IO();

        ListData_Array=device_io.ArrayList_ReadDataFromTxt(pathstring);

        arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, ListData_Array);
        Listview_optionmenu_edit_view.setAdapter(arrayAdapter);

    }
}
