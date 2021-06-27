package com.example.evensia.home;

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

import java.util.List;

public class row_data_event extends RecyclerView.Adapter<row_data_event.ViewHolder> {
    private Context context;
    private List<ModelEvent> modelEventList;

    public row_data_event(Context context, List<ModelEvent> modelEventList){
        this.context = context;
        this.modelEventList = modelEventList;

    }

    @NonNull
    @Override
    public row_data_event.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_row_data_event, parent, false);
        return new row_data_event.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull row_data_event.ViewHolder holder, int position) {
        ModelEvent modelEvent = modelEventList.get(position);
        holder.judul.setText(modelEvent.getJudul());
        holder.isi.setText(modelEvent.getIsi());

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
                .load(modelEvent.getGambar())
                .apply(options)
                .into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        return modelEventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView judul, isi;
        private ImageView gambar;
        private RelativeLayout parent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gambar = itemView.findViewById(R.id.gambar);
            judul = itemView.findViewById(R.id.judul);
            isi = itemView.findViewById(R.id.isi);
            parent= itemView.findViewById(R.id.parent);
        }
    }
}