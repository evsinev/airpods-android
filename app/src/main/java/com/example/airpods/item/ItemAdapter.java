package com.example.airpods.item;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airpods.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private static final String TAG = "MainActivity.ItemAdapter";

    private final List<ItemState> states;
    private final LayoutInflater inflater;

    public ItemAdapter(List<ItemState> aStates, LayoutInflater inflater) {
        this.inflater = inflater;
        states = aStates;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder()");
        View view = inflater.inflate(R.layout.status_big, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder()");
        ItemState state = states.get(position);
        holder.updateState(state);
        Log.d(TAG, "Updating at position " + position);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount() " + states.size());
        return states.size();
    }

    public void removeOldItems() {
        for(int i = states.size() - 1; i>=0; i--) {
            ItemState state = states.get(i);
            if(System.currentTimeMillis() - state.getCreateTime() > 10_000) {
                Log.d(TAG, "Removed old item " + i);
                states.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public void fireItem(ItemState aItemState) {

        Log.d(TAG, "fireItemChanged()");
        for (int i = 0; i < states.size(); i++) {
            ItemState item = states.get(i);
            if(item.getAddress().equals(aItemState.getAddress())) {
                Log.d(TAG, "  found item " + i);
                states.set(i, aItemState);
                this.notifyItemChanged(i, aItemState);
                return;
            }
        }

        // if not found
        Log.d(TAG, "  adding new item ");
        states.add(aItemState);
        this.notifyItemInserted(states.size() - 1);


    }
}
