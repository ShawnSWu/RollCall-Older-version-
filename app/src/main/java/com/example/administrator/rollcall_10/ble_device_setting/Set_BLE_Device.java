package com.example.administrator.rollcall_10.ble_device_setting;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.device_io.I_File_Path;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Set_BLE_Device extends AppCompatActivity {


    public final static String SELECTED_FILE_PATH="Selected_File_Path";
    public final static String CHOOSE_SECOND="Choose_second";
    public final static String SELECTED_FILE_NAME="Selected_File_Name";
    public final static String CHOOSE_TIME_SPINNER_ITEM="Choose_Time_spinner_Item";

    public Spinner Listspinner,Secondspinner;



    private Button button;
    private  String[] spinner_ListName;
    private File selected;
    private ArrayList<File> files;
    private File PeopleList;

    public TextView setlist,settime;
    public ArrayAdapter<String> ListAdapter;
    public ArrayAdapter<CharSequence> TimeAdapter;

   private static StringBuffer choose_sencond;


    private void Spinner(){

        Listspinner=(Spinner)findViewById(R.id.SetDevice_Listspinner);
        ListAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,filestring());
        Listspinner.setAdapter(ListAdapter);

        Secondspinner=(Spinner)findViewById(R.id.SetDevice_Secondspinner);
        TimeAdapter= ArrayAdapter.createFromResource(this, R.array.spinner_time, android.R.layout.simple_spinner_dropdown_item);
        Secondspinner.setAdapter(TimeAdapter);

    }



    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set__ble__device_layout);

        //****Scan返回鍵監聽事件 Start****\\
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("設置提醒功能");

        Spinner();

        RecyclerView SetDeivce_statusCard = (RecyclerView) findViewById(R.id.SetDevice_CardView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SetDeivce_statusCard.setLayoutManager(layoutManager);
        SetDeivce_statusCard.setAdapter(new SetDeviceCardViewAdapter());

        button= (Button)findViewById(R.id.send);
        button.setOnClickListener(btn);

    }



    private String[] filestring()
    {
        PeopleList =new File(I_File_Path.path_People_list);

        files = filter(PeopleList.listFiles());

        spinner_ListName=new String[files.size()];

        for(int i=0;i<files.size();i++){
            spinner_ListName[i]=files.get(i).getName().substring(0,files.get(i).getName().length()-4);
        }

            return spinner_ListName;
    }





    View.OnClickListener btn =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selected = new File(String.valueOf(files.get(Listspinner.getSelectedItemPosition())));

            if(selected.length() ==0)
            {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.ListIsNull), Toast.LENGTH_LONG).show();
            }else
            {

                switch (Secondspinner.getSelectedItemPosition()){

                    case 0:
                        choose_sencond=new StringBuffer("$10000#");
                        break;
                    case 1:
                        choose_sencond=new StringBuffer("$20000#");
                        break;
                    case 2:
                        choose_sencond=new StringBuffer("$1800000#");
                        break;
                    case 3:
                        choose_sencond=new StringBuffer("$2700000#");
                        break;
                    case 4:
                        choose_sencond=new StringBuffer("$3600000#");
                        break;
                    case 5:
                        choose_sencond=new StringBuffer("$5400000#");
                        break;
                    case 6:
                        choose_sencond=new StringBuffer("$7200000#");
                        break;

                }

                Intent it = new Intent(Intent.ACTION_VIEW);
                it.setClass(getApplicationContext(), setBLEDevice_connect.class);

                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putString(SELECTED_FILE_PATH, selected.getPath());
                bundle.putString(CHOOSE_SECOND,choose_sencond.toString());
                bundle.putString(SELECTED_FILE_NAME,selected.getName());
                bundle.putString(CHOOSE_TIME_SPINNER_ITEM,Secondspinner.getSelectedItem().toString());
                it.putExtras(bundle);
               getApplicationContext().startActivity(it);

            }





        }
    };

    public ArrayList<File> filter(File[] fileList)
    {
        ArrayList<File> files = new ArrayList<File>();
        if(fileList == null)
        {
            return files;
        }
        for(File file: fileList)
        {
            if(!file.isDirectory() && file.isHidden())
            {
                continue;
            }
            files.add(file);
        }
        Collections.sort(files);
        return files;
    }

    @Override
    public Intent getSupportParentActivityIntent()
    {
        finish();
        return null;
    }

    public class SetDeviceCardViewAdapter extends RecyclerView.Adapter<SetDeviceCardViewAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder
        {

            public ViewHolder(View v)
            {
                super(v);
                setlist = (TextView)v.findViewById(R.id.setlist);
                settime = (TextView)v.findViewById(R.id.settime);
                setlist.setText(ListAdapter.getItem(0));
                settime.setText(TimeAdapter.getItem(0));

            }
        }

        @Override
        public SetDeviceCardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.setdevice_status_cardview, parent, false);
            final ViewHolder viewHolder = new ViewHolder(v);

            Listspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    setlist.setText(Listspinner.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Secondspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    settime.setText(Secondspinner.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });

            return viewHolder;
        }



        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


        }

        @Override
        public int getItemCount() {
            return 1;
        }
    }
}



