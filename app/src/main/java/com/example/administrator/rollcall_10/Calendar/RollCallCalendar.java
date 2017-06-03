package com.example.administrator.rollcall_10.Calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.example.administrator.rollcall_10.R;

import java.util.Calendar;

public class RollCallCalendar extends AppCompatActivity implements com.andexert.calendarlistview.library.DatePickerController{

    private DayPickerView dayPickerView;
    private int year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("日曆");
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR)+3;

        dayPickerView = (DayPickerView) findViewById(R.id.pickerView);
        dayPickerView.setController(this);


    }

    @Override
    public int getMaxYear()
    {
        return year;
    }

    //****Scan返回鍵(左上角鍵頭)監聽事件 Start***\\\
    @Override
    public Intent getSupportParentActivityIntent()
    {
        setResult(RESULT_CANCELED);
        finish();
        return null;
    }
    //****Scan返回鍵(左上角鍵頭)監聽事件 End***\\\

    @Override
    public void onDayOfMonthSelected(int year, int month, int day)
    {
        Log.e("Day Selected", day + " / " + month + " / " + year);
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays)
    {

        Log.e("Date range selected", selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString());
    }
}
