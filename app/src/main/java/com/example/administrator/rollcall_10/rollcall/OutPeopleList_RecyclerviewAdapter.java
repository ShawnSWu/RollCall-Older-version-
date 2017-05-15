package com.example.administrator.rollcall_10.rollcall;

/**
 * Created by Administrator on 2017/5/4.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.rollcall_10.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OutPeopleList_RecyclerviewAdapter extends RecyclerView.Adapter<OutPeopleList_RecyclerviewAdapter.ViewHolder> {




    ArrayList<String> name;
    String[] data;

    public OutPeopleList_RecyclerviewAdapter(HashMap<String,String> txt_hashmap) {


        Iterator it=txt_hashmap.entrySet().iterator();

        data=new String[txt_hashmap.size()];


        int i=0;
        while (it.hasNext()){
            Map.Entry entry=(Map.Entry)it.next();

            data[i] = entry.getValue().toString();
            i++;
        }

    }


    @Override
    public OutPeopleList_RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_outpeople_item_layout, parent, false);
        ViewHolder viewholder = new ViewHolder(view);


        return viewholder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txtItem.setText(" "+data[position]);
//      holder.txtItem_name.setText(address[position]);
        holder.image.setImageResource(R.mipmap.person64);


    }




    @Override
    public int getItemCount() {
        return data.length;
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtItem,txtItem_name;
        public ImageView image;


        public ViewHolder(View v)
        {
            super(v);
            txtItem = (TextView) v.findViewById(R.id.txtItem);
//          txtItem_name=(TextView)v.findViewById(R.id.txtItem_name);
            image =(ImageView)v.findViewById(R.id.item_image);


        }


    }
}

