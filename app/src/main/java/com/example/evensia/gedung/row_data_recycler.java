package com.example.evensia.gedung;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.evensia.R;
import com.example.evensia.detail.model.ModelUlasan;
import com.example.evensia.detail.review;
import com.example.evensia.gedung.model.ModelGedung;
import com.example.evensia.home.Home;

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
        holder.harga.setText(modelGedung.getHarga());
        holder.nilai.setText(modelGedung.getNilai());

        // fungsi untuk melempar id
//        holder.parent.setOnClickListener(view -> {
//            Intent lemparJudul = new Intent(context, review.class);
//            lemparJudul.putExtra("sendJudul", modelGedung.getJudul());
//            context.startActivity(lemparJudul);
//        });
//        holder.parent.setOnClickListener(view -> {
//            Intent lemparAlamat = new Intent(context, review.class);
//            lemparAlamat.putExtra("sendAlamat", modelGedung.getAlamat());
//            context.startActivity(lemparAlamat);
//        });
//        holder.parent.setOnClickListener(view -> {
//            Intent lemparHarga = new Intent(context, review.class);
//            lemparHarga.putExtra("sendHarga", modelGedung.getHarga());
//            context.startActivity(lemparHarga);
//        });
//        holder.parent.setOnClickListener(view -> {
//            Intent lemparIsi = new Intent(context, review.class);
//            lemparIsi.putExtra("sendIsi", modelGedung.getIsi());
//            context.startActivity(lemparIsi);
//        });
//        holder.parent.setOnClickListener(view -> {
//            Intent lemparNilai = new Intent(context, review.class);
//            lemparNilai.putExtra("sendNilai", modelGedung.getNilai());
//            context.startActivity(lemparNilai);
//        });
//        holder.parent.setOnClickListener(view -> {
//            Intent lemparGambar1 = new Intent(context, review.class);
//            lemparGambar1.putExtra("sendGambar1", modelGedung.getGambar1());
//            context.startActivity(lemparGambar1);
//        });
//        holder.parent.setOnClickListener(view -> {
//            Intent lemparGambar2 = new Intent(context, review.class);
//            lemparGambar2.putExtra("sendGambar2", modelGedung.getGambar2());
//            context.startActivity(lemparGambar2);
//        });
//        holder.parent.setOnClickListener(view -> {
//            Intent lemparGambar3 = new Intent(context, review.class);
//            lemparGambar3.putExtra("sendGambar3", modelGedung.getGambar3());
//            context.startActivity(lemparGambar3);
//        });
//        holder.parent.setOnClickListener(view -> {
//            Intent lemparGambar4 = new Intent(context, review.class);
//            lemparGambar4.putExtra("sendGambar4", modelGedung.getGambar4());
//            context.startActivity(lemparGambar4);
//        });

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
        private TextView judul, isi, nilai, alamat, harga;
        private ImageView gambar1, gambar2, gambar3, gambar4;
        private RelativeLayout parent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.judul);
            isi = itemView.findViewById(R.id.isi);
            nilai = itemView.findViewById(R.id.nilai);
            alamat = itemView.findViewById(R.id.alamat);
            harga = itemView.findViewById(R.id.harga);
            gambar1 = itemView.findViewById(R.id.gambar1);
            gambar2 = itemView.findViewById(R.id.gambar2);
            gambar3 = itemView.findViewById(R.id.gambar3);
            gambar4= itemView.findViewById(R.id.gambar4);
            parent= itemView.findViewById(R.id.parent);
        }
    }
}