package com.example.evensia.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evensia.R;
import com.example.evensia.gedung.model.ModelGedung;
import com.example.evensia.login.Login1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Home extends AppCompatActivity {
    private Button logoutButton;
//    private FirebaseFirestore firebaseFirestore;
//    private FirebaseAuth firebaseAuth;
    private List<ModelHome> modelHomeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        firebaseFirestore = FirebaseFirestore.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();

        setID();
        onClickMethod();
//        getDataHome();
    }

    private void setID() {
        logoutButton = findViewById(R.id.logoutButton);
    }

    private void onClickMethod() {
        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();

            SharedPreferences preferences = getSharedPreferences("SharedPrefLogin", Context.MODE_PRIVATE);
            preferences.edit().clear().apply();

            Intent moveLogin = new Intent(Home.this, Login1.class);
            startActivity(moveLogin);
            finish();
        });
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
}