package com.example.evensia.booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.evensia.chat.Chat;
import com.example.evensia.profile.Profile;
import com.example.evensia.R;
import com.example.evensia.booking.model.ModelBooking;
import com.example.evensia.home.Home;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Booking extends AppCompatActivity {
    private RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private List<ModelBooking> modelBookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        modelBookingList = new ArrayList<>();

        getDataPesanan(firebaseAuth);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_booking);
        recyclerView = findViewById(R.id.recyclerView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_booking:
                        return true;
                    case R.id.nav_chat:
                        startActivity(new Intent(getApplicationContext(), Chat.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

    }

    private void getDataPesanan(FirebaseAuth fAuth){
        String getUserID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
//        String getRandomID = firebaseFirestore.collection("pesan_gedung").document().getId();

        firebaseFirestore.collection("Users").document(getUserID).collection("pesan_gedung").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    ModelBooking modelBooking = documentSnapshot.toObject(ModelBooking.class);
                    modelBookingList.add(modelBooking);
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Booking.this);
                row_data_booking recyclerAdapter = new row_data_booking(Booking.this, modelBookingList);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });
    }
}