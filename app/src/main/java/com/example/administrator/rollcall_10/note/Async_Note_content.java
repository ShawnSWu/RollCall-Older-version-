package com.example.administrator.rollcall_10.note;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/5/19.
 */
public class Async_Note_content extends AsyncTask<Boolean,Integer,Void> {

    Activity activity;
    Context context;

    public Async_Note_content(Activity activity){
        this.activity=activity;
    }

    public Async_Note_content(Context context){
        this.context=context;
    }

    @Override
    protected Void doInBackground(Boolean... booleen) {
        Note_Object.Title.clear();
        Note_Object.Content.clear();
        Note_Object.Time.clear();
        Note_Object.IsFinsh.clear();

        Cursor c= Note_EditView.RND.getReadableDatabase()
                .query(Note_EditView.RND.Note_Database_Name,new String[]{RollCall_Note_database.Note_Title},null,null,null,null,null);

        int i=0;
        c.moveToFirst();
        if(c.getCount() >= 1)
        {
            while (c.moveToNext())
            {
                Note_Object.Title.add( c.getString(i));
            }
        }

        c= Note_EditView.RND.getReadableDatabase()
                .query(Note_EditView.RND.Note_Database_Name,new String[]{RollCall_Note_database.Note_Content},null,null,null,null,null);

        int i1=0;
        c.moveToFirst();
        if(c.getCount() >= 1)
        {
            while (c.moveToNext())
            {
                Note_Object.Content.add( c.getString(i1));
            }
        }

        c= Note_EditView.RND.getReadableDatabase()
                .query(Note_EditView.RND.Note_Database_Name,new String[]{RollCall_Note_database.Note_Create_Note_Time},null,null,null,null,null);

        int i2=0;
        c.moveToFirst();
        if(c.getCount() >= 1)
        {
            while (c.moveToNext())
            {
                Note_Object.Time.add( c.getString(i2));
            }
        }


        c= Note_EditView.RND.getReadableDatabase()
                .query(Note_EditView.RND.Note_Database_Name,new String[]{RollCall_Note_database.Note_IsFinsh},null,null,null,null,null);

        int i3=0;
        c.moveToFirst();
        if(c.getCount() >= 1)
        {
            while (c.moveToNext())
            {
                Note_Object.IsFinsh.add( c.getInt(i3));
            }
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        if(activity != null){
            Intent intent = activity.getIntent();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(Note_EditView.TITLE, Note_Object.Title);
            bundle.putStringArrayList(Note_EditView.CONTENT, Note_Object.Content);
            bundle.putStringArrayList(Note_EditView.TIME,  Note_Object.Time);
            bundle.putIntegerArrayList(Note_EditView.isFinsh,Note_Object.IsFinsh);

            intent.putExtras(bundle);

            activity.setResult(activity.RESULT_OK, intent);

            Note_EditView.RND. getWritableDatabase().close();

            activity.finish();
        }


    }
}
