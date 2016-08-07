package com.example.administrator.rollcall_10;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Watch_List extends ListActivity {



    TextView mtxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.watchlist_activity);



        Bundle bundle = getIntent().getExtras();
        final String[] name = bundle.getStringArray("devicename");

        final ListView listView = getListView();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1, name);
        listView.setAdapter(adapter);




        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);





    }



}



