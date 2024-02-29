package com.example.airpods;

import static com.dosse.airpods.pods.PodsStatusScanCallback.decodeResult;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.util.Log;

import com.dosse.airpods.pods.PodsStatus;
import com.example.airpods.item.ItemAdapter;
import com.example.airpods.item.ItemState;

import java.util.List;

public class AirPodsScanCallback extends ScanCallback  {

    private static final String TAG = "AirPodsScanCallback";

    private final ItemAdapter itemAdapter;

    public AirPodsScanCallback(ItemAdapter itemAdapter) {
        this.itemAdapter = itemAdapter;
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        String decoded = decodeResult(result);
        PodsStatus status = new PodsStatus(decoded);
        Log.e(TAG, result.getDevice().getAddress()
                + " : "
                + result.getRssi()
                + " : "
                + status.getAirpods().parseStatusForLogger()
        );
        fireItemChanged(new ItemState(
                result.getDevice().getAddress(), result.getRssi(), status, System.currentTimeMillis()
        ));
    }

    @Override
    public void onBatchScanResults(List<ScanResult> results) {
        Log.e(TAG, "onBatchScanResults() " + results);
        for (ScanResult result : results) {
            onScanResult(-1, result);
        }
    }

    @Override
    public void onScanFailed(int errorCode) {
        Log.e(TAG, "onScanFailed() " + errorCode);
    }


    private void fireItemChanged(ItemState aItemState) {
        itemAdapter.removeOldItems();
        itemAdapter.fireItem(aItemState);
    }
}
