package com.example.secondproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MusicRecycleAdapter extends RecyclerView.Adapter< MusicRecycleAdapter. MusicViewHolder> {
    private static int viewHolderCount;
    private static int viewSlideCount;
    private int numberItems;
    List<String> song;
    private Context parent;

    public void EmptyMethode(){

    }

    public  MusicRecycleAdapter(int numbersOfItems, Context parent, List<String> song) {
        numberItems = numbersOfItems;
        viewHolderCount = 0;
        viewSlideCount = 0;
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
        MusicRecycleAdapter. MusicViewHolder viewHolder = new  MusicRecycleAdapter. MusicViewHolder(view);
        Log.d("myLog1", viewHolderCount + " ");
        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  MusicRecycleAdapter. MusicViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class  MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView listItemNumberView;
        TextView viewHolderIndex;

        public  MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemNumberView = itemView.findViewById(R.id.tv_number_item);  //конвертируем TextView в объкты java
            viewHolderIndex = itemView.findViewById(R.id.tv_view_holder_number);
            int positionX = getAdapterPosition();
            itemView.setOnClickListener(this);
        }

        void bind(int ListIndex) {
            //viewHolderIndex.setText(song.get(ListIndex));
            listItemNumberView.setText(song.get(ListIndex));
        }

        @Override
        public void onClick(View v) {
            //нажатие на кнопку
        }
    }
}
