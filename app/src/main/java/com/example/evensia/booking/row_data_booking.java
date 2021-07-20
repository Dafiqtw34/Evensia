package com.example.evensia.booking;

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
import com.example.evensia.booking.model.ModelBooking;

import java.util.List;

public class row_data_booking extends RecyclerView.Adapter<row_data_booking.ViewHolder> {
    private Context context;
    private List<ModelBooking> modelBookingList;

    public row_data_booking(Context context, List<ModelBooking> modelBookingList){
        this.context = context;
        this.modelBookingList = modelBookingList;

    }

    @NonNull
    @Override
    public row_data_booking.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_row_data_booking, parent, false);
        return new row_data_booking.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull row_data_booking.ViewHolder holder, int position) {
        ModelBooking modelBooking = modelBookingList.get(position);
        holder.judul.setText(modelBooking.getJudul());
        holder.tv_date.setText(modelBooking.getTv_date());
        holder.alamat.setText(modelBooking.getAlamat());
        holder.harga.setText(modelBooking.getHarga());

//         fungsi untuk melempar id
//        holder.parent.setOnClickListener(view -> {
//            Intent lemparId = new Intent(context, review.class);
//            lemparId.putExtra("sendID", modelGedung.getId());
//            context.startActivity(lemparId);
//        });

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(context)
                .load(modelBooking.getGambar1())
                .apply(options)
                .into(holder.gambar1);

        Glide.with(context)
                .load(modelBooking.getGambar2())
                .apply(options)
                .into(holder.gambar2);

        Glide.with(context)
                .load(modelBooking.getGambar3())
                .apply(options)
                .into(holder.gambar3);

        Glide.with(context)
                .load(modelBooking.getGambar4())
                .apply(options)
                .into(holder.gambar4);
    }

    @Override
    public int getItemCount() {
        return modelBookingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView judul, alamat, harga, tv_date;
        private ImageView gambar1, gambar2, gambar3, gambar4;
        private RelativeLayout parent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.judul);
            tv_date = itemView.findViewById(R.id.tv_date);
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