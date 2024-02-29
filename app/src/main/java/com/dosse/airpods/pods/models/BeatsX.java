package com.dosse.airpods.pods.models;

import com.dosse.airpods.pods.Pod;
import com.example.airpods.R;

public class BeatsX extends SinglePods {

    public BeatsX (Pod singlePod) {
        super(singlePod);
    }

    @Override
    public int getDrawable () {
        return getPod().isConnected() ? R.drawable.beatsx : R.drawable.beatsx_disconnected;
    }

    @Override
    public String getModel () {
        return Constants.MODEL_BEATS_X;
    }

}