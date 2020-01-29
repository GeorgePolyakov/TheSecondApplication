package com.example.secondproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Music> music;

    public MyAdapter(List<Music> music, Context context) {
        this.music = music;
        this.context = context;
    }

    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //когда листаем
        Music music = this.music.get(position);
      //  holder.textViewHead.setText(music.getHead());
       // holder.textViewDesc.setText(music.getDesc());
    }

    @Override
    public int getItemCount() {
        return music.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHead;
        public TextView textViewDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // textViewHead = itemView.findViewById(R.id.TextVIewHeader);
           // textViewDesc = itemView.findViewById(R.id.TextVIewDescription);
        }
    }

}
