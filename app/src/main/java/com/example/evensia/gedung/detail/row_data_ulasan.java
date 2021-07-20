package com.example.evensia.detail;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.evensia.R;
import com.example.evensia.detail.model.ModelUlasan;

import java.util.List;

public class row_data_ulasan extends RecyclerView.Adapter<row_data_ulasan.ViewHolder> {
    private Context context;
    private List<ModelUlasan> modelUlasanList;

    public row_data_ulasan(Context context, List<ModelUlasan> modelUlasanList){
        this.context = context;
        this.modelUlasanList = modelUlasanList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_row_data_ulasan, parent, false);
        return new row_data_ulasan.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull row_data_ulasan.ViewHolder holder, int position) {
        ModelUlasan modelUlasan = modelUlasanList.get(position);
        holder.nama.setText(modelUlasan.getNama());
        holder.ulasan.setText(modelUlasan.getUlasan());

        // fungsi untuk melempar id
//        holder.parent.setOnClickListener(view -> {
//            Intent lemparJudul = new Intent(context, Home.class);
//            lemparJudul.putExtra("sendJudul", modelGedung.getJudul());
//            context.startActivity(lemparJudul);
//        });

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(context)
                .load(modelUlasan.getProfile())
                .apply(options)
                .into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return modelUlasanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nama, ulasan;
        private ImageView profile;
        private RelativeLayout parent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.profile);
            nama = itemView.findViewById(R.id.nama);
            ulasan = itemView.findViewById(R.id.ulasan);
            parent= itemView.findViewById(R.id.parent);
        }
    }
}