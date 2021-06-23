package com.example.evensia.gedung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.evensia.R;
import com.example.evensia.gedung.model.ModelGedung;

import java.util.List;


public class row_data_recycler extends RecyclerView.Adapter<row_data_recycler.ViewHolder> {
    private Context context;
    private List<ModelGedung> modelGedungList;

    public row_data_recycler(Context context, List<ModelGedung> modelGedungList){
        this.context = context;
        this.modelGedungList = modelGedungList;

    }

    @NonNull
    @Override
    public row_data_recycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_row_data_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull row_data_recycler.ViewHolder holder, int position) {
        ModelGedung modelGedung = modelGedungList.get(position);
        holder.judul.setText(modelGedung.getJudul());
        holder.isi.setText(modelGedung.getIsi());
        holder.alamat.setText(modelGedung.getAlamat());
        holder.nilai.setText(modelGedung.getNilai());

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(context)
                .load(modelGedung.getGambar1())
                .apply(options)
                .into(holder.gambar1);

        Glide.with(context)
                .load(modelGedung.getGambar2())
                .apply(options)
                .into(holder.gambar2);

        Glide.with(context)
                .load(modelGedung.getGambar3())
                .apply(options)
                .into(holder.gambar3);

        Glide.with(context)
                .load(modelGedung.getGambar4())
                .apply(options)
                .into(holder.gambar4);
    }

    @Override
    public int getItemCount() {
        return modelGedungList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView judul, isi, nilai, alamat;
        private ImageView gambar1, gambar2, gambar3, gambar4;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.judul);
            isi = itemView.findViewById(R.id.isi);
            nilai = itemView.findViewById(R.id.nilai);
            alamat = itemView.findViewById(R.id.alamat);
            gambar1 = itemView.findViewById(R.id.gambar1);
            gambar2 = itemView.findViewById(R.id.gambar2);
            gambar3 = itemView.findViewById(R.id.gambar3);
            gambar4= itemView.findViewById(R.id.gambar4);
        }
    }
}