package com.example.administrator.rollcall_10.note;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/19.
 */
public class Async_Update_Database extends AsyncTask<ArrayList,Integer,Boolean>
{

    Context context;
    Note_card_click_RecyclerviewAdapter note_card_click_recyclerviewAdapter;

    public Async_Update_Database(Context context){
        this.context=context;

    }



    @Override
    protected Boolean doInBackground(ArrayList... arrayLists) {
        //仔入前先清除
        Note_Object.Title.clear();
        Note_Object.Content.clear();
        Note_Object.Time.clear();
        Note_Object.IsFinsh.clear();
        Note_Object.FinshOrNotIcon.clear();

        Note_RecyclerView_Activity.RND = RollCall_Note_database.getInstance(context);
        Cursor c= Note_RecyclerView_Activity.RND.getReadableDatabase()
                .query(Note_RecyclerView_Activity.RND.Note_Database_Name,new String[]{Note_RecyclerView_Activity.RND.Note_Title},null,null,null,null,null);

        int i=0;
        c.moveToFirst();
        if(c.getCount() >= 1)
        {
            while (c.moveToNext())
            {
                Note_Object.Title.add( c.getString(i));

            }
        }

        c= Note_RecyclerView_Activity.RND.getReadableDatabase()
                .query(Note_RecyclerView_Activity.RND.Note_Database_Name,new String[]{Note_RecyclerView_Activity.RND.Note_Content},null,null,null,null,null);
        int a=0;
        c.moveToFirst();
        if(c.getCount() >= 1)
        {
            while (c.moveToNext())
            {
                Note_Object.Content.add( c.getString(a));

            }
        }



        c= Note_RecyclerView_Activity.RND.getReadableDatabase()
                .query(Note_RecyclerView_Activity.RND.Note_Database_Name,new String[]{Note_RecyclerView_Activity.RND.Note_Create_Note_Time},null,null,null,null,null);
        int j=0;
        c.moveToFirst();
        if(c.getCount() >= 1)
        {
            while (c.moveToNext())
            {
                Note_Object.Time.add( c.getString(j));

            }
        }

        c= Note_RecyclerView_Activity.RND.getReadableDatabase()
                .query(Note_RecyclerView_Activity.RND.Note_Database_Name,new String[]{Note_RecyclerView_Activity.RND.Note_IsFinsh},null,null,null,null,null);
        int s=0;
        c.moveToFirst();
        if(c.getCount() >= 1)
        {
            while (c.moveToNext())
            {
                Note_Object.IsFinsh.add(c.getInt(s));

            }
        }

        c= Note_RecyclerView_Activity.RND.getReadableDatabase()
                .query(Note_RecyclerView_Activity.RND.Note_Database_Name,new String[]{Note_RecyclerView_Activity.RND.Note_FinshOrNotIcon},null,null,null,null,null);
        int s1=0;
        c.moveToFirst();
        if(c.getCount() >= 1)
        {
            while (c.moveToNext())
            {
                Note_Object.FinshOrNotIcon.add(c.getInt(s1));

            }
        }






        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if(note_card_click_recyclerviewAdapter != null){
            note_card_click_recyclerviewAdapter.notifyDataSetChanged();
        }


    }
}
