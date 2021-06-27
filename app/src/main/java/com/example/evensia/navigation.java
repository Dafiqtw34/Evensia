package com.example.evensia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.evensia.home.Home;
import com.example.evensia.register.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class navigation extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.nav_home:
//                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.nav_booking:
//                    Toast.makeText(MainActivity.this, "Booking", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.nav_chat:
//                    Toast.makeText(MainActivity.this, "Chat", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.nav_profile:
//                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
//                    break;
//
//            }
//            return true;
//        });
    }
}