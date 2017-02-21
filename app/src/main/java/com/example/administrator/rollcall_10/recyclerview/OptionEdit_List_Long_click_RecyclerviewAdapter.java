package com.example.administrator.rollcall_10.recyclerview;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.device_io.Device_IO;
import com.example.administrator.rollcall_10.device_io.I_File_Path;
import com.example.administrator.rollcall_10.navigationdrawer.mainview_fragmentlayout_EditList;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Administrator on 2016/8/11.
 */
public class OptionEdit_List_Long_click_RecyclerviewAdapter extends RecyclerView.Adapter<OptionEdit_List_Long_click_RecyclerviewAdapter.ViewHolder> {

    File selected;

    private Context mContext;

    RollCall_Dialog rollCall_dialog;



    String[] longclick_item;
    int[] image;

    Device_IO device_io=new Device_IO();


    public OptionEdit_List_Long_click_RecyclerviewAdapter(String[] item, int[] image, File file, Context mContext, RollCall_Dialog rollCall_dialog) {
        this.longclick_item=item;
        this.image=image;
        this.selected=file;
        this.mContext=mContext;
        this.rollCall_dialog=rollCall_dialog;
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

        EditText edit_name =(EditText)layout.findViewById(R.id.original_name_edit);
        edit_name.setHint("original name");

        Log.e("1","aqq-*:"+device_io.Edit_List_ReadData(selected).get(0));

        ///**關閉dialog
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

                Toast.makeText(v.getContext(), "Coming Soon！！", Toast.LENGTH_SHORT).show();

//                Log.e("1","aqq:"+device_io.Edit_List_ReadData(selected));
//
//
//                //***重新載入一次 suck code
//                FragmentManager fragmentManager = ((Activity) mContext).getFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.main_fragment, new mainview_fragmentlayout_EditList())
//                        .commitAllowingStateLoss();
//                //***重新載入一次 suck code

                rollCall_dialog.dismiss();


            }



        });

        rollCall_dialog.show();

    }

}
