package com.example.administrator.rollcall_10.note;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/5/26.
 */
public class Note_RecyclerView extends RecyclerView {

    View emptyView;

    public Note_RecyclerView(Context context) {
        super(context);
    }

    public Note_RecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Note_RecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        observer.onChanged();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    final AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();

            Log.e("adapter.getItemCount()","數量是"+adapter.getItemCount());
            Log.e("Note_Object.Title.size()","數量是"+Note_Object.Title.size());


            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    Note_RecyclerView.this.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    Note_RecyclerView.this.setVisibility(View.VISIBLE);
                }
            }
        }
    };


}

