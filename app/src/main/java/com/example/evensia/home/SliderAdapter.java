package com.example.evensia.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evensia.R;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyViewHolder> {

    int list[];

    public SliderAdapter(int[] list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.view.setBackgroundColor(list[position]);
        holder.slider1.setImageResource(R.drawable.event1);
        holder.slider2.setImageResource(R.drawable.event2);
        holder.slider3.setImageResource(R.drawable.event3);
        holder.slider4.setImageResource(R.drawable.event1);
        holder.slider5.setImageResource(R.drawable.event1);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView slider1, slider2, slider3, slider4, slider5;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view);
            slider1 = itemView.findViewById(R.id.slider1);
            slider2 = itemView.findViewById(R.id.slider2);
            slider3 = itemView.findViewById(R.id.slider3);
            slider4 = itemView.findViewById(R.id.slider4);
            slider5 = itemView.findViewById(R.id.slider5);
        }
    }

}