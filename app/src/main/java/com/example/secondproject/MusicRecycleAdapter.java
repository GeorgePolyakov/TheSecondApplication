package com.example.secondproject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MusicRecycleAdapter extends RecyclerView.Adapter<MusicRecycleAdapter.MusicViewHolder> {
    List<String> song;
    OnRecycleViewMusicListener onRecycleViewMusicListener;
    private int numberItems;
    private Context parent;

    public MusicRecycleAdapter(int numbersOfItems, Context parent, List<String> song, OnRecycleViewMusicListener onRecycleViewMusicListener) {
        numberItems = numbersOfItems;
        this.onRecycleViewMusicListener = onRecycleViewMusicListener;
        this.song = song;
        this.parent = parent;
    }

    @NonNull
    @Override
    public MusicRecycleAdapter.MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recycle_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        MusicRecycleAdapter.MusicViewHolder viewHolder = new MusicRecycleAdapter.MusicViewHolder(view, onRecycleViewMusicListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicRecycleAdapter.MusicViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView listItemNumberView;
        TextView viewHolderIndex;
        OnRecycleViewMusicListener onRecycleViewMusicListener;

        public MusicViewHolder(@NonNull View itemView, OnRecycleViewMusicListener onRecycleViewMusicListener) {
            super(itemView);
            listItemNumberView = itemView.findViewById(R.id.tv_number_item);
            viewHolderIndex = itemView.findViewById(R.id.tv_view_holder_number);
            this.onRecycleViewMusicListener = onRecycleViewMusicListener;
            itemView.setOnClickListener(this);
        }

        void bind(int ListIndex) {
            listItemNumberView.setText(song.get(ListIndex));
        }

        @Override
        public void onClick(View v) {
            onRecycleViewMusicListener.onMusicRecycleClick(listItemNumberView.getText().toString());
        }
    }

    public interface OnRecycleViewMusicListener {
        void onMusicRecycleClick(String key);
    }
}
