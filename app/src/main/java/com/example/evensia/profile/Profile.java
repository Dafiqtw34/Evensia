package com.example.evensia.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.evensia.R;
import com.example.evensia.booking.Booking;
import com.example.evensia.chat.Chat;
import com.example.evensia.home.Home;
import com.example.evensia.login.Login1;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    CircleImageView image_profile;
    private Button logoutButton;
//    private static final int GALLER_ACTION_PICK_CODE = 100 ;
//    Uri imageUri ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        logoutButton = findViewById(R.id.logoutButton);

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
                        startActivity(new Intent(getApplicationContext(), Booking.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_chat:
                        startActivity(new Intent(getApplicationContext(), Chat.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_profile:
                        return true;
                }

                return false;
            }
        });

        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();

            Intent moveLogin = new Intent(Profile.this, Login1.class);
            startActivity(moveLogin);
            finish();

        });

//        image_profile = findViewById(R.id.image_profile);
//
//        image_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                runTimePermission();
//            }
//
//            private void runTimePermission() {
//
//                Dexter.withContext(this)
//                        .withPermission(
//                                Manifest.permission.READ_EXTERNAL_STORAGE
//                        ).withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                        gelleryIntent();
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();
//            }
//        });
//
//
//    }
//
//    private void gelleryIntent() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent , GALLER_ACTION_PICK_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if ();
    }
}