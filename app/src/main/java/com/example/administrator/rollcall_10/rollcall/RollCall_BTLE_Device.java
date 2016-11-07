package com.example.administrator.rollcall_10.rollcall;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Kelvin on 5/8/16.
 */
public class RollCall_BTLE_Device {

    private BluetoothDevice bluetoothDevice;
    private int rssi;
    String devicename;

    public RollCall_BTLE_Device(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public String getAddress() {
        return bluetoothDevice.getAddress();
    }

    public String getName() {
        return bluetoothDevice.getName();
    }

    public void setRSSI(int rssi) {
        this.rssi = rssi;
    }

    public int getRSSI() {
        return rssi;
    }

    public void setName(String edit_device_name) {
        this.devicename=edit_device_name;
    }

    public String get_Manual_Name() {
        return devicename;
    }
}
