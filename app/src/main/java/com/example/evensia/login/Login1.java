package com.example.evensia.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evensia.R;
import com.example.evensia.gedung.gedung;
import com.example.evensia.gedung.review;
import com.example.evensia.home.Home;
import com.example.evensia.register.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login1 extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button pindahactivity;
    FirebaseAuth fAuth;
    TextView pindahactivity2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        mEmail =findViewById(R.id.email);
        mPassword = findViewById(R.id.pass);
        pindahactivity = findViewById(R.id.login);
        pindahactivity2 = findViewById(R.id.bl);

        fAuth = FirebaseAuth.getInstance();

        checkAuth();

        pindahactivity.setOnClickListener(view -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)){
                mEmail.setError("Email Harus diisi");
            }

            else if (TextUtils.isEmpty(password)){
                mPassword.setError("Password harus diisi");
            }

            else if (password.length() < 6) {
                mPassword.setError("Password harus diisi dari 6 karakter");
            } else {
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(Login1.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), gedung.class));
                        String getID = fAuth.getUid();
                        SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefLogin", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Id_user", getID);
                        editor.apply();
                    }else {
                        Toast.makeText(Login1.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        
        pindahactivity2.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
    }

    private void checkAuth() {
        FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                startActivity(new Intent(getApplicationContext(), gedung.class));
                finish();
            }
        };

        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }
}