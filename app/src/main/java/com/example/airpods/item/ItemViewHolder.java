package com.example.airpods.item;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dosse.airpods.pods.PodsStatus;
import com.dosse.airpods.pods.models.IPods;
import com.dosse.airpods.pods.models.RegularPods;
import com.dosse.airpods.pods.models.SinglePods;
import com.example.airpods.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public static final long TIMEOUT_CONNECTED = 30000;

    private static final String TAG = "MainActivity.ItemViewHolder";

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void updateState(ItemState aState) {
        Log.d(TAG, "updateState() " + aState);
        PodsStatus status = aState.getPodsStatus();
        IPods airpods = aState.getPodsStatus().getAirpods();
        boolean single = airpods.isSingle();

        if (!single) {
            setImageViewResource(R.id.leftPodImg, ((RegularPods)airpods).getLeftDrawable());
            setImageViewResource(R.id.rightPodImg, ((RegularPods)airpods).getRightDrawable());
            setImageViewResource(R.id.podCaseImg, ((RegularPods)airpods).getCaseDrawable());
        } else {
            setImageViewResource(R.id.podCaseImg, ((SinglePods)airpods).getDrawable());
        }

        setViewVisibility(R.id.leftPod, single ? View.GONE : View.VISIBLE);
        setViewVisibility(R.id.rightPod, single ? View.GONE : View.VISIBLE);


        if (isFreshStatus(status)) {
            setViewVisibility(R.id.leftPodText, View.VISIBLE);
            setViewVisibility(R.id.rightPodText, View.VISIBLE);
            setViewVisibility(R.id.podCaseText, View.VISIBLE);
            setViewVisibility(R.id.leftPodUpdating, View.INVISIBLE);
            setViewVisibility(R.id.rightPodUpdating, View.INVISIBLE);
            setViewVisibility(R.id.podCaseUpdating, View.INVISIBLE);

            if (!single) {
                RegularPods regularPods = (RegularPods)airpods;

                setTextViewText(R.id.leftPodText, regularPods.getParsedStatus(RegularPods.LEFT));
                setTextViewText(R.id.rightPodText, regularPods.getParsedStatus(RegularPods.RIGHT));
                setTextViewText(R.id.podCaseText, regularPods.getParsedStatus(RegularPods.CASE));

                setImageViewResource(R.id.leftBatImg, regularPods.getBatImgSrcId(RegularPods.LEFT));
                setImageViewResource(R.id.rightBatImg, regularPods.getBatImgSrcId(RegularPods.RIGHT));
                setImageViewResource(R.id.caseBatImg, regularPods.getBatImgSrcId(RegularPods.CASE));

                setViewVisibility(R.id.leftBatImg, regularPods.getBatImgVisibility(RegularPods.LEFT));
                setViewVisibility(R.id.rightBatImg, regularPods.getBatImgVisibility(RegularPods.RIGHT));
                setViewVisibility(R.id.caseBatImg, regularPods.getBatImgVisibility(RegularPods.CASE));

                setViewVisibility(R.id.leftInEarImg, regularPods.getInEarVisibility(RegularPods.LEFT));
                setViewVisibility(R.id.rightInEarImg, regularPods.getInEarVisibility(RegularPods.RIGHT));
            } else {
                SinglePods singlePods = (SinglePods)airpods;

                setTextViewText(R.id.podCaseText, singlePods.getParsedStatus());
                setImageViewResource(R.id.caseBatImg, singlePods.getBatImgSrcId());
                setViewVisibility(R.id.caseBatImg, singlePods.getBatImgVisibility());
            }
        } else {
            setViewVisibility(R.id.leftPodText, View.INVISIBLE);
            setViewVisibility(R.id.rightPodText, View.INVISIBLE);
            setViewVisibility(R.id.podCaseText, View.INVISIBLE);
            setViewVisibility(R.id.leftBatImg, View.GONE);
            setViewVisibility(R.id.rightBatImg, View.GONE);
            setViewVisibility(R.id.caseBatImg, View.GONE);
            setViewVisibility(R.id.leftPodUpdating, View.VISIBLE);
            setViewVisibility(R.id.rightPodUpdating, View.VISIBLE);
            setViewVisibility(R.id.podCaseUpdating, View.VISIBLE);
            setViewVisibility(R.id.leftInEarImg, View.INVISIBLE);
            setViewVisibility(R.id.rightInEarImg, View.INVISIBLE);        }

    }

    private void setTextViewText(int aId, String aText) {
        TextView textView = itemView.findViewById(aId);
        textView.setText(aText);
    }

    private void setImageViewResource(int aId, int aDrawableId) {
        ImageView imageView = itemView.findViewById(aId);
        if(imageView != null) {
            imageView.setImageResource(aDrawableId);
        }
    }

    private void setViewVisibility(int aId, int aVisible) {
        View view = itemView.findViewById(aId);
        if(view != null) {
            view.setVisibility(aVisible);
        }
    }

    private boolean isFreshStatus (PodsStatus status) {
        return System.currentTimeMillis() - status.getTimestamp() < TIMEOUT_CONNECTED;
    }
    
}
