package com.example.administrator.rollcall_10.recyclerview;

import android.support.v7.widget.RecyclerView;
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


    public WatchList_RecyclerviewAdapter(String[] name) {
        this.name=name;
    }

    @Override
    public WatchList_RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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
