package com.example.administrator.rollcall_10.note;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.rollcall_10.R;


public class Note_EditView extends AppCompatActivity {

    public static String Action_AddNote="AddNote";

    public static String TITLE="title";
    public static String CONTENT="content";
    public static String TIME="time";
    public static String isFinsh="isfinsh";

    private String PRIMITIVE_EDIT_CONTENT;
    private String edit_time;
    private String content;

    private int choose_hour=0,choose_minute=0;

    private RelativeLayout rl;
    private EditText editText;

    private TextView Create_time,SetRemindTime;
    private FloatingActionButton AddAlarm_fab;

    public static RollCall_Note_database RND;

    private int position=-1;

    //****Scan返回鍵(左上角鍵頭)監聽事件 Start***\\\
    @Override
    public Intent getSupportParentActivityIntent()
    {
        setResult(RESULT_CANCELED);
        finish();
        return null;
    }
    //****Scan返回鍵(左上角鍵頭)監聽事件 End***\\\

    private void findview_init()
    {
        SetRemindTime= (TextView) findViewById(R.id.SetRemindTime);
        SetRemindTime.setVisibility(View.INVISIBLE);

        editText=(EditText)findViewById(R.id.edit_note);
        Create_time = (TextView) findViewById(R.id.createtime);
        rl=(RelativeLayout)findViewById(R.id.note_edit_relativelayout);

        AddAlarm_fab=(FloatingActionButton)findViewById(R.id.fab_addAlarm);






    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__note__edit_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RND = RollCall_Note_database.getInstance(this);



        findview_init();

        rl.setOnClickListener(new EditText_onclick());
        AddAlarm_fab.setOnClickListener(new FabButton_onclick());


        if(getIntent().getAction()==Action_AddNote)
        {

            getSupportActionBar().setTitle("新增便條紙");
            edit_time = getIntent().getStringExtra("CreateTime");
            Create_time.setText("新增時間：" + edit_time);

            TranslateAnimation Fab_setTime = new TranslateAnimation(10f, 10f, -10, 85);
            Fab_setTime.setDuration(500);
            Fab_setTime.setRepeatCount(Animation.INFINITE);
            Fab_setTime.setRepeatMode(Animation.REVERSE);
            AddAlarm_fab.setAnimation(Fab_setTime);
            Fab_setTime.start();
        }
        else
        {
            AddAlarm_fab.setVisibility(View.INVISIBLE);

            Bundle bundle=getIntent().getExtras();
            PRIMITIVE_EDIT_CONTENT=bundle.getString(Note_EditView.CONTENT);
            edit_time=bundle.getString(Note_EditView.TIME);
            position=bundle.getInt("position");
            editText.setText(PRIMITIVE_EDIT_CONTENT);
            Create_time.setText("編輯時間：" + edit_time);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finsh, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId() ==R.id.action_finsh)
        {
            if(!editText.getText().toString().isEmpty())
            {
                if (getIntent().getAction() == Action_AddNote)
                {
                    if(choose_hour != 0 )
                    {
                        AddDataToDataBase();
                        SetAlarm();

                    }
                    else
                    {
                        Toast.makeText(this,"時間尚未設定",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    EditDataToDataBase();
                }

            }
            else{
                Toast.makeText(this,"內容不可為空",Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


   private void AddDataToDataBase()
    {
        Note_Object.Title.clear();
        Note_Object.Content.clear();
        Note_Object.Time.clear();
        Note_Object.IsFinsh.clear();
        Note_Object.FinshOrNotIcon.clear();

        content=editText.getText().toString();

        String title;

        if(content.length() > 3)
        {
            title =content.substring(0,3)+"...";
        }
        else{
            title =content;
        }

        ContentValues values=new ContentValues();
        values.put(RND.Note_Title,title);
        values.put(RND.Note_Content,content);
        values.put(RND.Note_Create_Note_Time,edit_time);
        values.put(RND.Note_IsFinsh,0);
        values.put(RND.Note_FinshOrNotIcon,R.mipmap.timer128);
        long id= RND.getWritableDatabase().insert(RND.Note_Database_Name,null,values);

        Log.e("ADD",""+id);

        //從資料庫下載資料完後執行intent(在Async裡面)
        new Async_Note_content(this).execute();

    }

    private void EditDataToDataBase()
    {
        Note_Object.Title.clear();
        Note_Object.Content.clear();
        Note_Object.Time.clear();
        Note_Object.IsFinsh.clear();
        Note_Object.FinshOrNotIcon.clear();


        //先獲取Edit上的字
        String content=editText.getText().toString();
        String title;
        if(content.length() > 3)
        {
            //title就是content的前3個字加上...
            title =content.substring(0,3)+"...";
        }
        else{
            title =content;
        }
        //放入ContentValues
        ContentValues values=new ContentValues();
        values.put(RND.Note_Title,title);
        values.put(RND.Note_Content,content);
        values.put(RND.Note_Create_Note_Time,edit_time);


        //如果原始內容大於3個字
        if(PRIMITIVE_EDIT_CONTENT.length()>3)
        {
            RND.getWritableDatabase().update(RND.Note_Database_Name, values, RollCall_Note_database.Note_Title + " = ?", new String[]{PRIMITIVE_EDIT_CONTENT.substring(0, 3) + "..."});
        }else
        {
            RND.getWritableDatabase().update(RND.Note_Database_Name, values, RollCall_Note_database.Note_Title + " = ?", new String[]{PRIMITIVE_EDIT_CONTENT});
        }

        //從資料庫下載資料完後執行intent(在Async裡面)
        new Async_Note_content(this).execute();

    }




    class EditText_onclick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(editText, 0);
        }
    }



    class FabButton_onclick implements View.OnClickListener
    {

        @Override
        public void onClick(View view) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

                new TimePickerDialog(Note_EditView.this, R.style.Theme_Dialog, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                    SetRemindTime.setVisibility(View.VISIBLE);
                    SetRemindTime.setText("鬧鐘 "+hourOfDay + "：" + minute);

                    choose_hour=hourOfDay;
                    choose_minute=minute;
                }
            },hour,minute,false).show();



        }
    }


    public void SetAlarm()
    {

        Alarm_List.calendar=Calendar.getInstance();
        Alarm_List.calendar.set(Calendar.HOUR_OF_DAY,choose_hour);
        Alarm_List.calendar.set(Calendar.MINUTE,choose_minute);
        Alarm_List.alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);

        String title;
        String content=editText.getText().toString();
        if(editText.getText().toString().length() > 3)
        {
            title =editText.getText().toString().substring(0,3)+"...";
        }
        else
        {
            title =editText.getText().toString();
        }


        Intent intent=new Intent(this,Note_Alarm_Broadcast.class);
        intent.addCategory(editText.getText().toString());
        intent.putExtra("title",title);
        intent.putExtra("content",content);

        Alarm_List.pi=PendingIntent.getBroadcast(this,1,intent,PendingIntent.FLAG_ONE_SHOT);


        Alarm_List.alarmManager.set(AlarmManager.RTC_WAKEUP,Alarm_List.calendar.getTimeInMillis(),Alarm_List.pi);



        new Async_Update_Database(this).execute();

    }




}
