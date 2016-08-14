package com.example.administrator.rollcall_10;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.io.FilenameUtils;


import java.util.ArrayList;


/**
 * Created by Administrator on 2016/8/14.
 */


public class RecyclerviewAdapter_EditList extends RecyclerView.Adapter<RecyclerviewAdapter_EditList.ViewHolder> {

    String [] name;

    private ArrayList<Recyclerview_ItemData> arrayList =new ArrayList<>();

    public RecyclerviewAdapter_EditList(ArrayList<Recyclerview_ItemData> arrayList){

        this.arrayList =arrayList;
    }

    @Override
    public RecyclerviewAdapter_EditList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_watchlist_item_layout, parent, false);
        ViewHolder viewholder = new ViewHolder(view);


        return viewholder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtItem.setText(name[position]);
        holder.image.setImageResource(R.mipmap.info64);
    }




    @Override
    public int getItemCount() {
        return name.length;
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtItem;
        public ImageView image;


        public ViewHolder(View v)
        {
            super(v);
            txtItem = (TextView) v.findViewById(R.id.txtItem);
            image =(ImageView)v.findViewById(R.id.item_image);

        }


    }



}
