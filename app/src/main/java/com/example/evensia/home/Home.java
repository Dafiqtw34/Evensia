package com.example.evensia.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.evensia.booking.Booking;
import com.example.evensia.chat.Chat;
import com.example.evensia.profile.Profile;
import com.example.evensia.R;
import com.example.evensia.gedung.gedung;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;
//    private Button logoutButton;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private List<ModelEvent> modelEventList;
    private TextView namaUser;
    ViewPager2 imageContainer;
    SliderAdapter adapter;
    CardView gedung;
    int list[];
    TextView[] dots;
    LinearLayout layout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageContainer = findViewById(R.id.image_container);
        layout = findViewById(R.id.grid);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        return true;

                    case R.id.nav_booking:
                        startActivity(new Intent(getApplicationContext(), Booking.class));
                        overridePendingTransition(0,0);
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

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        modelEventList = new ArrayList<>();

        setID();
        onClickMethod();
        getName();
        getDataEvent();
//        getOneData();

        dots = new TextView[5];

        list = new int[5];
        list[0] = getResources().getColor(R.color.black);
        list[1] = getResources().getColor(R.color.red);
        list[2] = getResources().getColor(R.color.green);
        list[3] = getResources().getColor(R.color.yellow);
        list[4] = getResources().getColor(R.color.orange);

        adapter = new SliderAdapter(list);
        imageContainer.setAdapter(adapter);

//        setIndicators();
//
//        imageContainer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                selectedDots(position);
//                super.onPageSelected(position);
//            }
//        });
//
//    }
//
//    private void selectedDots(int position) {
//        for (int i = 0; i < dots.length; i++) {
//            if (i == position) {
//                dots[i].setTextColor(list[position]);
//            } else {
//                dots[i].setTextColor(getResources().getColor(R.color.grey));
//            }
//        }
//    }
//
//    private void setIndicators() {
//        for (int i = 0; i < dots.length; i++) {
//            dots[i] = new TextView(this);
//            dots[i].setText(Html.fromHtml("&#9679;"));
//            dots[i].setTextSize(18);
//            layout.addView(dots[i]);
//        }
    }

    private void setID() {
        recyclerView = findViewById(R.id.recyclerView);
//        logoutButton = findViewById(R.id.logoutButton);
        namaUser = findViewById(R.id.Hallo);
        gedung = findViewById(R.id.moveGedung);
    }

    private void onClickMethod() {
//        logoutButton.setOnClickListener(view -> {
//            FirebaseAuth.getInstance().signOut();
//
//            SharedPreferences preferences = getSharedPreferences("AuthLogin", Context.MODE_PRIVATE);
//            preferences.edit().clear().apply();
//
//            Intent moveLogin = new Intent(Home.this, Login1.class);
//            startActivity(moveLogin);
//            finish();
//        });

        gedung.setOnClickListener(view -> {
            Intent moveGedung = new Intent(Home.this, gedung.class);
            startActivity(moveGedung);
        });
//
//
    }


//    private void getDataHome() {
//        firebaseFirestore.collection("Users").get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                    ModelHome modelHome = documentSnapshot.toObject(ModelHome.class);
//                    modelHomeList.add(modelHome);
//                }
//            }
//        });
//    }
    // Mengambil 1 data dari database
    private void getName() {
        firebaseAuth = FirebaseAuth.getInstance();
        String getToken = firebaseAuth.getCurrentUser().getUid();
        // mengambil nama tabel pada database dan penamaan id perdokumen
        firebaseFirestore.collection("Users").document(getToken).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // mengambil data kolom dengan id
                String getName = task.getResult().get("name").toString();
                namaUser.setText("Hello, " + getName);
            } else {
                Toast.makeText(this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataEvent() {
        firebaseFirestore.collection("artikel").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        ModelEvent modelEvent = documentSnapshot.toObject(ModelEvent.class);
                        modelEventList.add(modelEvent);
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Home.this);
                    row_data_event recyclerAdapter = new row_data_event(Home.this, modelEventList);

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(recyclerAdapter);
                }
            });
//                for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                    ModelEvent modelEvent = documentSnapshot.toObject(ModelEvent.class);
//                    modelEventList.add(modelEvent);
//                }
//
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Home.this);
//                row_data_event recyclerAdapter = new row_data_event(Home.this, modelEventList);
//
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(linearLayoutManager);
//                recyclerView.setAdapter(recyclerAdapter);
//            }
//        });
    }
}