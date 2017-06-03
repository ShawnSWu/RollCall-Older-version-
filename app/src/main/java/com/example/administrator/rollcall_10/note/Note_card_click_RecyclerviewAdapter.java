package com.example.administrator.rollcall_10.note;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/8/11.
 */
public class Note_card_click_RecyclerviewAdapter extends RecyclerView.Adapter<Note_card_click_RecyclerviewAdapter.ViewHolder>{



    private Context mContext;

    private RollCall_Dialog rollCall_dialog;

    private String[] longclick_item;
    private int[] image;
    private int position;
    private Note_RecyclerView_Activity.Note_recyclerViewAdapter note_recyclerViewAdapter;

    public Note_card_click_RecyclerviewAdapter(String[] item, int[] image, Context mContext, RollCall_Dialog rollCall_dialog, int position, Note_RecyclerView_Activity.Note_recyclerViewAdapter note_recyclerViewAdapter)
    {
        this.longclick_item=item;
        this.image=image;
        this.mContext=mContext;
        this.rollCall_dialog=rollCall_dialog;
        this.position=position;
        this.note_recyclerViewAdapter=note_recyclerViewAdapter;

    }


    @Override
    public Note_card_click_RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_listlongclick_item_layout, parent, false);
        ViewHolder viewholder = new ViewHolder(view);


        return viewholder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.Longclick_Item.setText(longclick_item[position]);
        holder.image.setImageResource(image[position]);

    }




    @Override
    public int getItemCount() {
        return longclick_item.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView Longclick_Item;
        public ImageView image;

        private int choose_hour=0,choose_minute=0;


        public ViewHolder(View v)
        {
            super(v);
            Longclick_Item = (TextView) v.findViewById(R.id.longclick_item);
            image =(ImageView)v.findViewById(R.id.item_image);



            v.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    switch (getAdapterPosition()) {

                        case 0:
                            AlreadyFinsh_And_ResetTime(position);
                            break;
                        case 1:
                            Edit_Note(position);
                            break;

                        case 2:
                            delate_note(v, position);
                            break;
                    }


                }
            });

        }



        private void AlreadyFinsh_And_ResetTime(final int i)
        {

            if(Note_Object.IsFinsh.get(i) == 0) {
                //若是未完成(標記為已完成)

                ContentValues values = new ContentValues();
                values.put(RollCall_Note_database.Note_FinshOrNotIcon, R.mipmap.tick64);
                values.put(RollCall_Note_database.Note_IsFinsh, 1);
                RollCall_Note_database.getInstance(mContext).getWritableDatabase().update(RollCall_Note_database.Note_Database_Name, values, RollCall_Note_database.Note_Title + " = ?", new String[]{Note_Object.Title.get(i)});

                new Async_Update_IsFinsh_Database(mContext).execute();
                new Async_Update_FinshOrNotIcon_Database(mContext, note_recyclerViewAdapter).execute();


                Intent intent=new Intent(mContext,Note_Alarm_Broadcast.class);
                intent.putExtra("title",Note_Object.Title.get(i));
                intent.putExtra("content",Note_Object.Content.get(i));
                intent.addCategory(Note_Object.Content.get(i));


                PendingIntent pi=PendingIntent.getBroadcast(mContext,1,intent,PendingIntent.FLAG_ONE_SHOT);



                Alarm_List.alarmManager.cancel(pi);


                note_recyclerViewAdapter.notifyDataSetChanged();



            }

            else{
                //若是已完成(重設提醒)
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                new TimePickerDialog(mContext, R.style.Theme_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                        choose_hour=hourOfDay;
                        choose_minute=minute;

                        ReSetAlarm(Note_Object.Content.get(i));

                        if(choose_hour>=13)
                        {
                            choose_hour-=12;
                            Toast.makeText(mContext,"已重設提醒時間:下午"+choose_hour+"點"+choose_minute+"分",Toast.LENGTH_LONG).show();
                        }else
                        {
                            Toast.makeText(mContext,"已重設提醒時間:上午"+choose_hour+"點"+choose_minute+"分",Toast.LENGTH_LONG).show();
                        }

                        note_recyclerViewAdapter.notifyDataSetChanged();
                    }
                },hour,minute,false).show();


                new Async_Update_IsFinsh_Database(mContext).execute();
                new Async_Update_FinshOrNotIcon_Database(mContext, note_recyclerViewAdapter).execute();

            }



            rollCall_dialog.dismiss();

        }

        private void ReSetAlarm(String EditContent)
        {

            Alarm_List.calendar=Calendar.getInstance();
            Alarm_List.calendar.set(Calendar.HOUR_OF_DAY,choose_hour);
            Alarm_List.calendar.set(Calendar.MINUTE,choose_minute);
            Alarm_List.alarmManager=(AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);

            String title;
            String content=EditContent;
            if(EditContent.length() > 3)
            {
                title =EditContent.substring(0,3)+"...";
            }
            else{
                title =EditContent;
            }


            Intent intent=new Intent(mContext,Note_Alarm_Broadcast.class);
            intent.putExtra("title",title);
            intent.putExtra("content",content);
            intent.addCategory(EditContent);

            Alarm_List.pi=PendingIntent.getBroadcast(mContext,1,intent,PendingIntent.FLAG_ONE_SHOT);
            Alarm_List.alarmManager.set(AlarmManager.RTC_WAKEUP,Alarm_List.calendar.getTimeInMillis(),Alarm_List.pi);



            ContentValues values=new ContentValues();
            values.put(RollCall_Note_database.Note_FinshOrNotIcon,R.mipmap.timer128);
            values.put(RollCall_Note_database.Note_IsFinsh,0);
            RollCall_Note_database.getInstance(mContext).getWritableDatabase().update(RollCall_Note_database.Note_Database_Name, values, RollCall_Note_database.Note_Title + " = ?", new String[]{title});


            new Async_Update_FinshOrNotIcon_Database(mContext.getApplicationContext()).execute();
            new Async_Update_IsFinsh_Database(mContext.getApplicationContext()).execute();



        }


       private void Edit_Note(int position){


            String edit_time = Note_RecyclerView_Activity.formatter.format(Note_RecyclerView_Activity.curDate);

            Intent intent=new Intent(mContext,Note_EditView.class);
            intent.setAction(Note_RecyclerView_Activity.Edit_Note);
            Bundle bundle=new Bundle();
            bundle.putInt("position",position);
            bundle.putString(Note_EditView.CONTENT,Note_Object.Content.get(position));
            bundle.putString(Note_EditView.TIME,edit_time);

            intent.putExtras(bundle);
            ((Activity) mContext).startActivityForResult(intent,Note_RecyclerView_Activity.requstcode);
            rollCall_dialog.dismiss();
        }


        private void delate_note(View v, final int position)
        {

            RollCall_Dialog.Builder rd=new RollCall_Dialog.Builder(v.getContext());
            rd.setTitle("確定刪除便條紙  "+Note_Object.Title.get(position)+" 嗎?").
                    setMessage("鬧鐘提醒也會移並移除哦!! 確定刪除?").
                    setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent=new Intent(mContext,Note_Alarm_Broadcast.class);
                            intent.putExtra("title",Note_Object.Title.get(position));
                            intent.putExtra("content",Note_Object.Content.get(position));
                            intent.addCategory(Note_Object.Content.get(position));

                            PendingIntent pi=PendingIntent.getBroadcast(mContext,1,intent,PendingIntent.FLAG_ONE_SHOT);

                            Alarm_List.alarmManager=(AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
                            Alarm_List.alarmManager.cancel(pi);


                            SQLiteDatabase db = Note_RecyclerView_Activity.RND.getWritableDatabase();

                            db.delete(RollCall_Note_database.Note_Database_Name, RollCall_Note_database.Note_Title + " = ?", new String[]{Note_Object.Title.get(position)});
                            Log.e("A_Note_Object.Title.size()","數量是"+Note_Object.Title.size());
                            LoadingData();


                            new Async_Update_FinshOrNotIcon_Database(mContext.getApplicationContext()).execute();
                            new Async_Update_IsFinsh_Database(mContext.getApplicationContext()).execute();

                            note_recyclerViewAdapter.notifyDataSetChanged();

                            rollCall_dialog.dismiss();

                        }
                    }).
                    setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            rd.show();





        }




    }




    public void LoadingData()
    {
//        new Async_Update_Database(mContext).execute();
        Note_Object.Title.clear();

        Note_RecyclerView_Activity.RND = RollCall_Note_database.getInstance(mContext);
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
    }
}
