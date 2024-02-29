package com.example.airpods.item;

import com.dosse.airpods.pods.PodsStatus;

import java.util.Date;

public class ItemState {

    private final String address;
    private final int rssi;
    private final PodsStatus podsStatus;
    private final long createTime;

    public ItemState(String address, int rssi, PodsStatus podsStatus, long createTime) {
        this.address = address;
        this.rssi = rssi;
        this.podsStatus = podsStatus;
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public int getRssi() {
        return rssi;
    }

    public PodsStatus getPodsStatus() {
        return podsStatus;
    }

    public long getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "ItemState{" +
                "address='" + address + '\'' +
                ", rssi=" + rssi +
                ", podsStatus=" + podsStatus +
                ", createTime=" + new Date(createTime) +
                '}';
    }
}
