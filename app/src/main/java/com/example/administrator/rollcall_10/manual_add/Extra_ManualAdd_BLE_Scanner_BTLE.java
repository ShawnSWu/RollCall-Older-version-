package com.example.administrator.rollcall_10.manual_add;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;

import com.example.administrator.rollcall_10.demo.Utils;

/**
 * Created by Kelvin on 4/20/16.
 */
public class Extra_ManualAdd_BLE_Scanner_BTLE {

    private Extra_ManualAdd_BLE_MainActivity extra_manualAdd_ble_mainActivity;


    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;

    private long scanPeriod;
    private int signalStrength;


    public Extra_ManualAdd_BLE_Scanner_BTLE(Extra_ManualAdd_BLE_MainActivity extra_manualAdd_ble_mainActivity, int scanPeriod, int signalStrength) {

        this.extra_manualAdd_ble_mainActivity = extra_manualAdd_ble_mainActivity;

        mHandler = new Handler();

        this.scanPeriod = scanPeriod;
        this.signalStrength = signalStrength;

        final BluetoothManager bluetoothManager =
                (BluetoothManager) extra_manualAdd_ble_mainActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    public boolean isScanning() {
        return mScanning;
    }

    public void start() {

        if (!Utils.checkBluetooth(mBluetoothAdapter)) {
            Utils.requestUserBluetooth(extra_manualAdd_ble_mainActivity);
            extra_manualAdd_ble_mainActivity.stopScan();
            extra_manualAdd_ble_mainActivity.finish();

        }
        else {
            scanLeDevice(true);
        }
    }

    public void stop() {
        scanLeDevice(false);
    }

    // If you want to scan for only specific types of peripherals,
    // you can instead call startLeScan(UUID[], BluetoothAdapter.LeScanCallback),
    // providing an array of UUID objects that specify the GATT services your app supports.
    private  synchronized void  scanLeDevice(final boolean enable) {
        if (enable && !mScanning) {
//                Utils.toast(manualAdd_ble_mainActivity.getApplicationContext(), "請開始加入人數");

            // Stops scanning after a pre-defined scan period.
            //**還不知道功能 暫留
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    Utils.toast(autoAdd_ble_mainActivity.getApplicationContext(), "Stopping BLE scan...");

                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);

                    extra_manualAdd_ble_mainActivity.stopScan();
                }
            }, scanPeriod);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
//            mBluetoothAdapter.startLeScan(uuids, mLeScanCallback);
        }
        else {
//            Utils.toast(manualAdd_ble_mainActivity.getApplicationContext(), "停止加入人數");
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {

                    final int new_rssi = rssi;
                    if (rssi > signalStrength) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                extra_manualAdd_ble_mainActivity.addDevice(device, new_rssi);
                            }
                        });
                    }
                }
            };
}