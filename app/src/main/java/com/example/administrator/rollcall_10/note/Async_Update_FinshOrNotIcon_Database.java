package com.example.administrator.rollcall_10.note;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/24.
 */
public class Async_Update_FinshOrNotIcon_Database extends AsyncTask<ArrayList,Integer,Boolean>
{
    Context context;
    Note_RecyclerView_Activity.Note_recyclerViewAdapter note_recyclerViewAdapter;

    public Async_Update_FinshOrNotIcon_Database(Context context,  Note_RecyclerView_Activity.Note_recyclerViewAdapter note_recyclerViewAdapter){
        this.context=context;
        this.note_recyclerViewAdapter=note_recyclerViewAdapter;
    }

    public Async_Update_FinshOrNotIcon_Database(Context context){
        this.context=context;
    }


    @Override
    protected Boolean doInBackground(ArrayList... arrayLists) {

        //仔入前先清除
        Note_Object.FinshOrNotIcon.clear();


        Note_RecyclerView_Activity.RND = RollCall_Note_database.getInstance(context);
        Cursor c= Note_RecyclerView_Activity.RND.getReadableDatabase()
                .query(Note_RecyclerView_Activity.RND.Note_Database_Name,new String[]{Note_RecyclerView_Activity.RND.Note_FinshOrNotIcon},null,null,null,null,null);
        int s=0;
        c.moveToFirst();
        if(c.getCount() >= 1)
        {
            while (c.moveToNext())
            {
                Note_Object.FinshOrNotIcon.add(c.getInt(s));

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

        if( note_recyclerViewAdapter != null)
        {
         note_recyclerViewAdapter.notifyDataSetChanged();
        }

    }
}