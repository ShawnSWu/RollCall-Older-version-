package com.example.administrator.rollcall_10.rollcall;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.rollcall_10.note.Note_Object;

/**
 * Created by Administrator on 2017/5/30.
 */
public class Result_RecyclerView extends RecyclerView {


    View emptyView;

    public Result_RecyclerView(Context context) {
        super(context);
    }

    public Result_RecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Result_RecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
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



            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    Result_RecyclerView.this.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    Result_RecyclerView.this.setVisibility(View.VISIBLE);
                }
            }
        }
    };
}
