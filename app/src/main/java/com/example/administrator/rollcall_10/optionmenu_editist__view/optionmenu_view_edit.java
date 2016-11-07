package com.example.administrator.rollcall_10.optionmenu_editist__view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.administrator.rollcall_10.R;

public class optionmenu_view_edit extends AppCompatActivity {

    ListView optionmenu_edit_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optionmenu_view_edit);

        Bundle bundle = getIntent().getExtras();
        String[] ListData_Array = bundle.getStringArray("ListData_Array");
        String Seletor_List_File= bundle.getString("Seletor_List_File");

        Log.e("1","----:"+ListData_Array.length);




        //換標題
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Seletor_List_File);



        optionmenu_edit_view =(ListView)findViewById(R.id.optionmenu_edit_view);
        ListAdapter adapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 ,ListData_Array);
        optionmenu_edit_view.setAdapter(adapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finsh, menu);

                return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }


}
