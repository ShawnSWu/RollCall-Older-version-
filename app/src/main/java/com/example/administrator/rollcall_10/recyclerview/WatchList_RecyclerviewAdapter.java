package com.example.administrator.rollcall_10.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.rollcall_10.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/11.
 */
public class WatchList_RecyclerviewAdapter extends RecyclerView.Adapter<WatchList_RecyclerviewAdapter.ViewHolder> {


    HashMap<String,String> txt_hashmap;

    String[] data;


    public WatchList_RecyclerviewAdapter(HashMap<String,String> txt_hashmap) {
        this.txt_hashmap=txt_hashmap;

        Iterator it=txt_hashmap.entrySet().iterator();

        data=new String[txt_hashmap.size()];

        int i=0;
        while (it.hasNext()){
            Map.Entry entry=(Map.Entry)it.next();
            data[i]=entry.getValue() + " ," + entry.getKey();
            Log.e("~~~~","---*-*-"+data[i]);
            i++;

        }



    }


    @Override
    public WatchList_RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_watchlist_item_layout, parent, false);
        ViewHolder viewholder = new ViewHolder(view);


        return viewholder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txtItem.setText(data[position]);

        holder.image.setImageResource(R.mipmap.info64);

    }




    @Override
    public int getItemCount() {
        return txt_hashmap.size();
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
