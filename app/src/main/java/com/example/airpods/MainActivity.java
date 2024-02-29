package com.example.airpods;

import static com.dosse.airpods.pods.PodsStatusScanCallback.getScanFilters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airpods.item.ItemAdapter;
import com.example.airpods.item.ItemState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private BluetoothLeScanner bluetoothLeScanner;
    private ItemAdapter itemAdapter;
    private AirPodsScanCallback scanCallback;
    private static final List<ItemState> states = new ArrayList<>();

    ScanSettings scanSettings = new ScanSettings.Builder()
            .setScanMode(true ? ScanSettings.SCAN_MODE_LOW_POWER : ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setReportDelay(1) // DON'T USE 0
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        itemAdapter = new ItemAdapter(states, LayoutInflater.from(this));
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

        scanCallback = new AirPodsScanCallback(itemAdapter);

        BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = manager.getAdapter();
        bluetoothLeScanner = adapter.getBluetoothLeScanner();
    }

    @Override
    protected void onResume() {
        super.onResume();

        itemAdapter.removeOldItems();

        if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "No permission");

            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        bluetoothLeScanner.startScan(getScanFilters(), scanSettings, scanCallback);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onPause() {
        super.onPause();
        bluetoothLeScanner.stopScan(scanCallback);
    }
}