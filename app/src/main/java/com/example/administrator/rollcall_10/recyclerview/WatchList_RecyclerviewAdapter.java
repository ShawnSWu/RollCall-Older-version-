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

/**
 * Created by Administrator on 2016/8/11.
 */
public class WatchList_RecyclerviewAdapter extends RecyclerView.Adapter<WatchList_RecyclerviewAdapter.ViewHolder> {

    String [] name;
    String [] address;



    public WatchList_RecyclerviewAdapter(String[] address) {
        this.address=address;
    }

    public WatchList_RecyclerviewAdapter(String[] name,String[] address) {
        this.name=name;
        this.address=address;
    }



    @Override
    public WatchList_RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_watchlist_item_layout, parent, false);
        ViewHolder viewholder = new ViewHolder(view);


        return viewholder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtItem.setText(address[position]);
//        holder.txtItem_name.setText(address[position]);
        holder.image.setImageResource(R.mipmap.info64);
        Log.e("Shawn","---"+position);
    }




    @Override
    public int getItemCount() {
        return address.length;
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtItem,txtItem_name;
        public ImageView image;


        public ViewHolder(View v)
        {
            super(v);
            txtItem = (TextView) v.findViewById(R.id.txtItem);
//            txtItem_name=(TextView)v.findViewById(R.id.txtItem_name);
            image =(ImageView)v.findViewById(R.id.item_image);

        }


    }
}
