package com.example.administrator.rollcall_10.manual_add;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.administrator.rollcall_10.R;

import java.util.ArrayList;

/**
 * Created by Kelvin on 5/7/16.
 */
public class ManualAdd_ListAdapter_BTLE_Devices extends ArrayAdapter<ManualAdd_BTLE_Device> {

    Context context;
    int layoutResourceID;
    ArrayList<ManualAdd_BTLE_Device> devices;

    public ManualAdd_ListAdapter_BTLE_Devices(Context context, int resource, ArrayList<ManualAdd_BTLE_Device> objects) {
        super(context.getApplicationContext(), resource, objects);

        this.context = context;
        this.layoutResourceID = resource;
        this.devices = objects;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceID, parent, false);
        }

        ManualAdd_BTLE_Device device = devices.get(position);
        String name = device.getName();
        String address = device.getAddress();
        int rssi = device.getRSSI();

        TextView tv = null;

        tv = (TextView) convertView.findViewById(R.id.tv_name);
        if (name != null && name.length() > 0) {
            tv.setText(device.getName());
        }
        else {
            tv.setText("No Name");
        }

//        tv = (TextView) convertView.findViewById(R.id.tv_rssi);
//        tv.setText("RSSI: " + Integer.toString(rssi));

        tv = (TextView) convertView.findViewById(R.id.tv_macaddr);
        if (address != null && address.length() > 0) {
            tv.setText(device.getAddress());
        }
        else {
            tv.setText("No Address");
        }

        return convertView;
    }



    //**0826
//    public void remove(int i) {
//        devices.remove(devices.get(i));
//}

}
