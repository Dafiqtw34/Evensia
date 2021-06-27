package com.example.evensia.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.example.evensia.R;
import com.example.evensia.detail.model.ModelUlasan;
import com.example.evensia.gedung.gedung;
import com.example.evensia.gedung.model.ModelGedung;
import com.example.evensia.gedung.row_data_recycler;
import com.example.evensia.login.Login1;
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
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_review_gedung);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        modelUlasanList = new ArrayList<>();

        setID();
        onClickMethod();
        getDataUlasan();
    }

    private void setID() {
        recyclerView = findViewById(R.id.recyclerView);
        logoutButton = findViewById(R.id.logoutButton);
    }

    private void onClickMethod() {
        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();

            SharedPreferences preferences = getSharedPreferences("AuthLogin", Context.MODE_PRIVATE);
            preferences.edit().clear().apply();

            Intent moveLogin = new Intent(review.this, Login1.class);
            startActivity(moveLogin);
            finish();

        });
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
}