package com.example.administrator.rollcall_10.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.rollcall_10.R;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/8/14.
 */


public class EditList_RecyclerviewAdapter extends RecyclerView.Adapter<EditList_RecyclerviewAdapter.ViewHolder> {

    String [] name;

    private ArrayList<Recyclerview_ItemData> arrayList =new ArrayList<>();

    public EditList_RecyclerviewAdapter(ArrayList<Recyclerview_ItemData> arrayList){

        this.arrayList =arrayList;
    }

    @Override
    public EditList_RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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
