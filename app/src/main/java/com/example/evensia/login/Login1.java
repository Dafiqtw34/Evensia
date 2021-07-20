package com.example.evensia.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evensia.R;
import com.example.evensia.gedung.gedung;
import com.example.evensia.home.Home;
import com.example.evensia.register.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login1 extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    EditText mEmail, mPassword;
    Button pindahactivity;
    FirebaseAuth fAuth;
    TextView pindahactivity2;
//    ProgressBar progressBar;
    private static int RC_SIGN_IN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        mEmail =findViewById(R.id.email);
        mPassword = findViewById(R.id.pass);
        pindahactivity = findViewById(R.id.login);
        pindahactivity2 = findViewById(R.id.bl);
//        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();

        checkAuth();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(view -> signIn());

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
            }
//            progressBar.setVisibility(View.VISIBLE);
            else {
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(Login1.this, "Berhasil", Toast.LENGTH_SHORT).show();

                        Intent moveGedung = new Intent(Login1.this, Home.class);
                        startActivity(moveGedung);
                        finish();
                    }else {
                        Toast.makeText(Login1.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        
        pindahactivity2.setOnClickListener(view -> {
            Intent moveMain = new Intent(Login1.this, MainActivity.class);
            startActivity(moveMain);
        });
    }

    private void checkAuth() {
        FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                Intent moveGedung = new Intent(Login1.this, Home.class);
                startActivity(moveGedung);
                finish();
            }
        };

        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null){
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                Toast.makeText(this, "User email : "+personEmail, Toast.LENGTH_SHORT).show();
            }

            startActivity(new Intent(Login1.this , Home.class));
        }catch (ApiException e){
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

            Log.d("Message", e.toString());
        }
    }
}