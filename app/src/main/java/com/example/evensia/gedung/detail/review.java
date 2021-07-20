package com.example.evensia.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.evensia.R;
import com.example.evensia.detail.model.ModelUlasan;
import com.example.evensia.gedung.gedung;
import com.example.evensia.gedung.model.ModelGedung;
import com.example.evensia.gedung.row_data_recycler;
import com.example.evensia.home.Home;
import com.example.evensia.login.Login1;
import com.example.evensia.pesan_gedung;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.example.evensia.R.layout.activity_review_gedung;

public class review extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private List<ModelUlasan> modelUlasanList;
    private List<ModelGedung> modelGedungList;
    private Button logoutButton;
    private ImageView buttonBack;
    private TextView judul, harga, isi, alamat, nilai;
    private ImageView gambar1, gambar2, gambar3, gambar4;
    private RelativeLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_review_gedung);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        modelUlasanList = new ArrayList<>();
        modelGedungList = new ArrayList<>();

        setID();
        onClickMethod();
        getDataUlasan();
        getDataGedung();
    }

    private void setID() {
        recyclerView = findViewById(R.id.recyclerView);
        logoutButton = findViewById(R.id.logoutButton);
        buttonBack = findViewById(R.id.back);
        judul = findViewById(R.id.judul);
        harga = findViewById(R.id.harga);
        isi = findViewById(R.id.isi);
        alamat = findViewById(R.id.alamat);
        nilai = findViewById(R.id.nilai);
        gambar1 = findViewById(R.id.gambar1);
        gambar2 = findViewById(R.id.gambar2);
        gambar3 = findViewById(R.id.gambar3);
        gambar4 = findViewById(R.id.gambar4);
        parent = findViewById(R.id.review);
    }

    private void onClickMethod() {
        logoutButton.setOnClickListener(view -> {
            String getId = getIntent().getStringExtra("sendID");
            Intent movePesan = new Intent(review.this, pesan_gedung.class);
            movePesan.putExtra("sendPesananID", getId);
            startActivity(movePesan);
        });

        buttonBack.setOnClickListener(view -> {
            onBackPressed();
//            Intent moveHome = new Intent(review.this, gedung.class);
//            startActivity(moveHome);
        });

//        parent.setOnClickListener(view -> {
//            FirebaseAuth.getInstance().signOut();
//
//            SharedPreferences preferences = getSharedPreferences("SharedPrefLogin", Context.MODE_PRIVATE);
//            preferences.edit().clear().apply();
//
//            Intent moveHome = new Intent(review.this, pesan_gedung.class);
////            lemparId.putExtra("sendID", modelGedung.getId());
////            context.startActivity(lemparId);
////            startActivity(moveHome);
////            finish();
//        });
    }

    private void getDataUlasan() {
        firebaseFirestore.collection("ulasan").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    ModelUlasan modelUlasan = documentSnapshot.toObject(ModelUlasan.class);
                    modelUlasanList.add(modelUlasan);
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(review.this);
                row_data_ulasan recyclerAdapter = new row_data_ulasan(review.this, modelUlasanList);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });
    }

    private void getDataGedung() {
        String getId = getIntent().getStringExtra("sendID");

        if (getId != null && !getId.equals("")) {
            firebaseFirestore.collection("review").document(getId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String getJudul = document.getString("judul");
                        judul.setText(getJudul);

                        String getHarga = document.getString("harga");
                        harga.setText(getHarga);

                        String getIsi = document.getString("isi");
                        isi.setText(getIsi);

                        String getAlamat = document.getString("alamat");
                        alamat.setText(getAlamat);

                        String getNilai = document.getString("nilai");
                        nilai.setText(getNilai);

                        RequestOptions options = new RequestOptions()
                                .placeholder(R.mipmap.ic_launcher_round)
                                .error(R.mipmap.ic_launcher_round);

                        String getGambar1 = document.getString("gambar1");
                        Glide.with(review.this)
                                .load(getGambar1)
                                .apply(options)
                                .into(gambar1);

                        String getGambar2 = document.getString("gambar2");
                        Glide.with(review.this)
                                .load(getGambar2)
                                .apply(options)
                                .into(gambar2);

                        String getGambar3 = document.getString("gambar3");
                        Glide.with(review.this)
                                .load(getGambar3)
                                .apply(options)
                                .into(gambar3);

                        String getGambar4 = document.getString("gambar4");
                        Glide.with(review.this)
                                .load(getGambar4)
                                .apply(options)
                                .into(gambar4);

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            });
        }


//                Toast.makeText(this, getGambar4, Toast.LENGTH_SHORT).show();
    }
}