package com.example.evensia.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evensia.R;
import com.example.evensia.home.Home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText mName, mEmail, mPhone, mPassword;
    private Button pindahactivity;
    private CheckBox mCeklis;
    private FirebaseAuth fAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = findViewById(R.id.email);
        mName = findViewById(R.id.name);
        mPhone = findViewById(R.id.phone);
        mPassword = findViewById(R.id.pass);
        mCeklis = findViewById(R.id.syarat);
        pindahactivity = findViewById(R.id.reg);



        pindahactivity.setOnClickListener(view -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String nama = mName.getText().toString().trim();
            String phone = mPhone.getText().toString().trim();

            fAuth = FirebaseAuth.getInstance();
            firebaseFirestore = FirebaseFirestore.getInstance();

            if (TextUtils.isEmpty(email)){
                mEmail.setError("Email Harus diisi");
            }

            else if (TextUtils.isEmpty(password)){
                mPassword.setError("Password harus diisi");
            }

            else if (password.length() < 6) {
                mPassword.setError("Password harus diisi dari 6 karakter");
            }

            else if (!mCeklis.isChecked()) {
                mCeklis.setError("harus diceklis");
            }

            else {
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        insertToDatabase(nama, phone, fAuth, firebaseFirestore);
                    }else {
                        Toast.makeText(MainActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void insertToDatabase(String name, String phoneNumber, FirebaseAuth fAuth, FirebaseFirestore firebaseFirestore) {
        String getUserID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        ModelRegister modelRegister = new ModelRegister(name, phoneNumber);
        firebaseFirestore.collection("Users").document(getUserID).set(modelRegister).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(MainActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(), Home.class));
            } else {
                Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}