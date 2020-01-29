package com.example.secondproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.secondproject.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NumbersRecycleAdapter extends RecyclerView.Adapter<NumbersRecycleAdapter.NumberViewHolder> {

    private static int viewHolderCount;
    private static int viewSlideCount;
    private int numberItems;
    List<Music> song;
    private Context parent;

    public void EmptyMethode(){

    }

    public NumbersRecycleAdapter(int numbersOfItems, Context parent, List<Music> song) {
        numberItems = numbersOfItems;
        viewHolderCount = 0;
        viewSlideCount = 0;
        this.song = song;
        this.parent = parent;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recycle_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        NumberViewHolder viewHolder = new NumberViewHolder(view);
        Log.d("myLog1", viewHolderCount + " ");
        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView listItemNumberView;
        TextView viewHolderIndex;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemNumberView = itemView.findViewById(R.id.tv_number_item);  //конвертируем TextView в объкты java
            viewHolderIndex = itemView.findViewById(R.id.tv_view_holder_number);
            int positionX = getAdapterPosition();
            itemView.setOnClickListener(this);
        }

        void bind(int ListIndex) {
            viewHolderIndex.setText(song.get(ListIndex).getSongName());
            listItemNumberView.setText(song.get(ListIndex).getAuthorName());
        }

        @Override
        public void onClick(View v) {
            //нажатие на кнопку
        }
    }

}