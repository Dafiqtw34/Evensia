package com.example.evensia.gedung;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evensia.R;
import com.example.evensia.gedung.model.ModelGedung;
import com.example.evensia.home.Home;
import com.example.evensia.login.Login1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class gedung extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private List<ModelGedung> modelGedungList;
    private Button logoutButton;
    private ImageView buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gedung);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        modelGedungList = new ArrayList<>();

        setID();
        onClickMethod();
        getDataGedung();
    }

    private void setID() {
        recyclerView = findViewById(R.id.recyclerView);
        logoutButton = findViewById(R.id.logoutButton);
        buttonBack = findViewById(R.id.back);
    }

    private void onClickMethod() {
        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();

            SharedPreferences preferences = getSharedPreferences("SharedPrefLogin", Context.MODE_PRIVATE);
            preferences.edit().clear().apply();

            Intent moveLogin = new Intent(gedung.this, Login1.class);
            startActivity(moveLogin);
            finish();

        });

        buttonBack.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();

            SharedPreferences preferences = getSharedPreferences("SharedPrefLogin", Context.MODE_PRIVATE);
            preferences.edit().clear().apply();

            Intent moveHome = new Intent(gedung.this, Home.class);
            startActivity(moveHome);
            finish();
        });
    }


    private void getDataGedung() {
        firebaseFirestore.collection("review").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    ModelGedung modelGedung = documentSnapshot.toObject(ModelGedung.class);
                    modelGedungList.add(modelGedung);
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(gedung.this);
                row_data_recycler recyclerAdapter = new row_data_recycler(gedung.this, modelGedungList);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });
    }
}