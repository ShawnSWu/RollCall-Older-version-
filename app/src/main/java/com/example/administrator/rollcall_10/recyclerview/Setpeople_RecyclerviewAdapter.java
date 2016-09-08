package com.example.administrator.rollcall_10.recyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.rollcall_10.manual_add.ManualAdd_BLE_MainActivity;
import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.auto_add.AutoAdd_BLE_MainActivity;
import com.example.administrator.rollcall_10.device_io.I_File_Path;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by Administrator on 2016/8/14.
 */


public class Setpeople_RecyclerviewAdapter extends RecyclerView.Adapter<Setpeople_RecyclerviewAdapter.ViewHolder> {

    File selected;

    private ListView FileList;


    private Context Context;
    private ArrayList<File> files;

    private File PeopleList;


    public Setpeople_RecyclerviewAdapter(Context context) {

        this.Context=context;
    }


    private String[] titles = {
            I_CardView.ManualAdd_Title,
            I_CardView.AutoAdd_Title,
    };



    private String[] details = {
            I_CardView.ManualAdd_detail,
            I_CardView.AutoAdd_detail,
    };



    private int[] images = {

            R.mipmap.menualadd256,
            R.mipmap.autoadd_group256,

    };




    @Override
    public Setpeople_RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.setpeople_recyclerview_cardview, parent, false);
        ViewHolder viewholder = new ViewHolder(v);


        Context = v.getContext().getApplicationContext();

        PeopleList =new File(I_File_Path.path_People_list);
        PeopleList.mkdirs();

        files = filter(PeopleList.listFiles());

        return viewholder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemTitle.setText(titles[position]);
        holder.itemDetail.setText(details[position]);
        holder.itemImage.setImageResource(images[position]);
    }




    @Override
    public int getItemCount() {
        return titles.length;
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;


        public ViewHolder(View v)
        {
            super(v);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =
                    (TextView)itemView.findViewById(R.id.item_detail);



            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (getAdapterPosition()){
                        case 0:
                            ManualAdd(v);
                            break;
                        case 1:
                            AutoAdd(v);
                            break;
                    }


                }
            });




        }



        ///***手動加入監聽事件
        public void ManualAdd(final View v){


            LayoutInflater inflater = (LayoutInflater) v.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.dialog_listview_addpeople_seleted, null);

             final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(v.getContext());

            Button btn_close =(Button)layout.findViewById(R.id.btn_close);

            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rollCall_dialog.dismiss();

                }
            });
            rollCall_dialog.setView(layout);
            rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
            rollCall_dialog.setCancelable(false);
            rollCall_dialog.setCancelable(true);
            rollCall_dialog.show();




            FileList = (ListView)layout.findViewById(R.id.dialog_list_seletor);
            FileList.setAdapter(new File_AddList_Adapter());


            FileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {



                    //**成功
                    selected = new File(String.valueOf(files.get(position)));

                    if(selected.length() !=0){

                        Log.e("shawn","此清單不是空的,跳覆寫警告");

//                        getActivity().getResources().getString(R.string.RollCall_List_Delete_Message)
                        RollCall_Dialog rollCall_dialog = new RollCall_Dialog(parent.getContext());
                        rollCall_dialog.setMessage(v.getContext().getResources().getString(R.string.RollCall_Delete_Warning_Message));
                        rollCall_dialog.setCancelable(false);
                        rollCall_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, v.getContext().getString(R.string.RollCall_Dialog__Button_No), no);
                        rollCall_dialog.setButton(DialogInterface.BUTTON_POSITIVE,v.getContext().getString(R.string.RollCall_Dialog__Button_Yes), yes);
                        rollCall_dialog.show();



                    }else {
                        Log.e("shawn","選擇的資料路徑："+ selected.getPath());


                        Intent it = new Intent(Intent.ACTION_VIEW);
                        it.setClass(v.getContext(), ManualAdd_BLE_MainActivity.class);

                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        Bundle bundle = new Bundle();
                        bundle.putString("Selected_File_Path", selected.getPath());
                        bundle.putString("Selected_File_Name", selected.getName());
                        it.putExtras(bundle);

                        v.getContext().startActivity(it);
                        rollCall_dialog.dismiss();

                    }


                }
                public DialogInterface.OnClickListener yes = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("shawn","確定覆寫! 進入加入人數畫面");
                        Intent it = new Intent(Intent.ACTION_VIEW);
                        it.setClass(v.getContext(), AutoAdd_BLE_MainActivity.class);

                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                        Bundle bundle = new Bundle();
                        bundle.putString("Selected_File_Path", selected.getPath());
                        bundle.putString("Selected_File_Name", selected.getName());
                        it.putExtras(bundle);

                        v.getContext().startActivity(it);
                        rollCall_dialog.dismiss();
                    }

                };

                public DialogInterface.OnClickListener no = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("shawn","關閉");
                    }

                };

            });


        }

        ///***自動加入監聽事件
        public void AutoAdd(final View v){

            LayoutInflater inflater = (LayoutInflater) v.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.dialog_listview_addpeople_seleted, null);

            final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(v.getContext());

            Button btn_close =(Button)layout.findViewById(R.id.btn_close);

            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rollCall_dialog.dismiss();

                }
            });
            rollCall_dialog.setView(layout);
            rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
            rollCall_dialog.setCancelable(false);
            rollCall_dialog.setCancelable(true);
            rollCall_dialog.show();


            FileList = (ListView)layout.findViewById(R.id.dialog_list_seletor);
            FileList.setAdapter(new File_AddList_Adapter());


            FileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {


                    //**成功將每個資料夾寫入
                    selected = new File(String.valueOf(files.get(position)));

                    if(selected.length() !=0){

                        Log.e("shawn","此清單不是空的,跳覆寫警告");


            RollCall_Dialog rollCall_dialog = new RollCall_Dialog(parent.getContext());
            rollCall_dialog.setMessage(v.getContext().getResources().getString(R.string.RollCall_Delete_Warning_Message));
            rollCall_dialog.setCancelable(false);
            rollCall_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, v.getContext().getString(R.string.RollCall_Dialog__Button_No), no);
            rollCall_dialog.setButton(DialogInterface.BUTTON_POSITIVE,v.getContext().getString(R.string.RollCall_Dialog__Button_Yes), yes);
            rollCall_dialog.show();



                    }
                    else {
                        Log.e("shawn","選擇的資料路徑："+ selected.getPath());


                        Intent it = new Intent(Intent.ACTION_VIEW);
                        it.setClass(v.getContext(), AutoAdd_BLE_MainActivity.class);

                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                        Bundle bundle = new Bundle();
                        bundle.putString("Selected_File_Path", selected.getPath());
                        bundle.putString("Selected_File_Name", selected.getName());
                        it.putExtras(bundle);

                        v.getContext().startActivity(it);
                        rollCall_dialog.dismiss();

                    }



                }
                public DialogInterface.OnClickListener yes = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.e("shawn","確定覆寫! 進入加入人數畫面");

                        Intent it = new Intent(Intent.ACTION_VIEW);
                        it.setClass(v.getContext(), AutoAdd_BLE_MainActivity.class);

                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                        Bundle bundle = new Bundle();
                        bundle.putString("Selected_File_Path", selected.getPath());
                        bundle.putString("Selected_File_Name", selected.getName());
                        it.putExtras(bundle);

                        v.getContext().startActivity(it);
                        rollCall_dialog.dismiss();
                    }

                };

                public DialogInterface.OnClickListener no = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("shawn","關閉dialog");
                    }

                };
            });



        }



    }



































    public ArrayList<File> filter(File[] fileList) {
        ArrayList<File> files = new ArrayList<File>();
        if(fileList == null){
            return files;
        }
        for(File file: fileList) {
            if(!file.isDirectory() && file.isHidden()) {
                continue;
            }
            files.add(file);
        }
        Collections.sort(files);
        return files;
    }



    private class File_AddList_Adapter extends BaseAdapter {

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
            holder.textView.setText(fileName);
            holder.imageView.setImageResource(R.mipmap.txt128);




            return v;
        }

        private class Holder{
            TextView textView;
            ImageView imageView;

        }


    }


}



