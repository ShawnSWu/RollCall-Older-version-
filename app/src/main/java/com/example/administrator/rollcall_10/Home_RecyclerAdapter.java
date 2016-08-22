package com.example.administrator.rollcall_10;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Administrator on 2016/8/9.
 */
public class Home_RecyclerAdapter extends RecyclerView.Adapter<Home_RecyclerAdapter.ViewHolder> {
    private Context context;

    File selected;

    private ListView FileList;


    private Context mContext;
    private ArrayList<File> files;

    private File PeopleList;



    public Home_RecyclerAdapter(Context context) {

     this.context=context;
    }



    private String[] titles = {I_CardView.RollCall,
            I_CardView.Watch_list,
            I_CardView.Set_BLE_Device,
            I_CardView.Memorandum,
            I_CardView.Not_Open,
            I_CardView.Not_Open,
            I_CardView.Not_Open,
        };



    private String[] details = {
            I_CardView.RollCall_detail,
            I_CardView.Watch_list_detail,
            I_CardView.Set_BLE_Device_detail,
            I_CardView.Memorandum_detail,
            I_CardView.Not_Open_detail,
            I_CardView.Not_Open_detail,
            I_CardView.Not_Open_detail,
           };



    private int[] images = {

            R.mipmap.rollcallicon256,
            R.mipmap.setlist256,
            R.mipmap.bluetoothdevice128,
            R.mipmap.memorandum256,
            R.mipmap.worker256,
            R.mipmap.worker256,
            R.mipmap.worker256,

       };



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_recyclerview_cardview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        mContext = v.getContext().getApplicationContext();

        PeopleList =new File(I_File_Path.path_People_list);
        PeopleList.mkdirs();

        files = filter(PeopleList.listFiles());
        return viewHolder;

    }
    DialogInterface.OnClickListener close = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {         }

    };




    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemDetail.setText(details[i]);
        viewHolder.itemImage.setImageResource(images[i]);
        FragmentManager manager;
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }






    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =
                    (TextView)itemView.findViewById(R.id.item_detail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();



                    switch (getAdapterPosition()){
                        case 0:
                            startscan(v);
                            break;


                        case 1:
                            WatchList(v);
                            break;

                        case 2:
                            Set_BLEDevice(v);
                            break;
                        case 3:
                            Snackbar.make(v, "尚未開放" ,
                                    Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;

                        case 4:
                            Snackbar.make(v, "尚未開放" ,
                                    Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            break;
                    }

                }
            });
        }



























        //**開始掃描
        public void startscan(View v){

            LayoutInflater inflater = (LayoutInflater) v.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.dialog_listview_rollcall_seleted, null);

            final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(v.getContext());
//            rollCall_dialog.setTitle(R.string.RollCall_List_Dialog_Title_WantToScan);
            rollCall_dialog.setView(layout);
            rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
            rollCall_dialog.setCancelable(false);
            rollCall_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, v.getContext().getString(R.string.RollCall_Dialog__Button_close), close);
            rollCall_dialog.setCancelable(true);
            rollCall_dialog.show();


            FileList = (ListView)layout.findViewById(R.id.dialog_list_seletor);
            FileList.setAdapter(new File_RollCAll_Adapter());


            FileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {




                    selected = new File(String.valueOf(files.get(position)));



                    if(selected.length() ==0) {

                        Toast.makeText(v.getContext(), "清單是空的!!", Toast.LENGTH_LONG).show();



                    }else {

                        Log.e("1", ":AA" + selected.getName());


                        Intent it = new Intent(Intent.ACTION_VIEW);
                        it.setClass(v.getContext(), BLE_MainActivity.class);

                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        Bundle bundle = new Bundle();
                        bundle.putString("Selected_File_Path", selected.getPath());
                        bundle.putString("Selected_File_Name", selected.getName());
                        it.putExtras(bundle);

                        v.getContext().startActivity(it);

                        rollCall_dialog.dismiss();

                    }






                }
            });


        }







        //**查看清單
        public void WatchList(View v){


            Snackbar.make(v, "暫不開放" ,
                    Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();


//            if(device_io.file.length()==0){
//                Snackbar.make(v, "當前清單是空的" ,
//                        Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//            else {
//                Intent it = new Intent(Intent.ACTION_VIEW);
//                it.setClass(v.getContext(), Recyclerview_WatchList.class);
//
//                String x = "";
//
//                Bundle bundle = new Bundle();
//                bundle.putStringArray("devicename",   device_io.readData(device_io.file, x));
//                it.putExtras(bundle);
//
//                v.getContext(). startActivity(it);
//            }

        }


        DialogInterface.OnClickListener close = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {         }

        };





        //**設置藍芽裝置
        public void Set_BLEDevice(View v){
            Intent it = new Intent(Intent.ACTION_VIEW);
            it.setClass(v.getContext(), Set_BLE_Device.class);
            v.getContext(). startActivity(it);
        }



        public void Note(View v){}



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



    private class File_RollCAll_Adapter extends BaseAdapter {

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


                v = LayoutInflater.from(mContext).inflate(R.layout.editlist_file, null);

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