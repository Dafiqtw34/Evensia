package com.example.evensia.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.evensia.R;
import com.example.evensia.booking.Booking;
import com.example.evensia.chat.Chat;
import com.example.evensia.home.Home;
import com.example.evensia.login.Login1;
import com.example.evensia.register.ModelRegister;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class Profile extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private CircularImageView image_profile;
    private Button logoutButton;
    private final static int CAMERA_REQUEST_CODE = 200;
    private final static int STORAGE_REQUEST_CODE = 400;
    private final static int IMAGE_PICK_GALLERY_CODE = 1000;
    private final static int IMAGE_PICK_CAMERA_CODE = 2001;
    private Uri imageUri;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        logoutButton = findViewById(R.id.logoutButton);
        image_profile = findViewById(R.id.image_profile);

        image_profile.setOnClickListener(v -> selectImage());

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    return true;

                case R.id.nav_booking:
                    startActivity(new Intent(getApplicationContext(), Booking.class));
                    return true;

                case R.id.nav_chat:
                    startActivity(new Intent(getApplicationContext(), Chat.class));
                    return true;

                case R.id.nav_profile:
                    return true;
            }

            return false;
        });

        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();

            Intent moveLogin = new Intent(Profile.this, Login1.class);
            startActivity(moveLogin);
            finish();

        });
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                if (!checkCameraPermission()) {
                    requestCameraPermission();
                } else {
                    pickCamera();
                }
            } else if (options[item].equals("Choose from Gallery")) {
                if (!checkStoragePermission()) {
                    requestStoragePermission();
                } else {
                    pickFromGallery();
                }
            } else {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void requestCameraPermission() {
        String[] cameraPermission = {android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(Profile.this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private void requestStoragePermission() {
        String[] storagePermission = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(Profile.this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean results = ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return result & results;
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void pickFromGallery() {
        Intent moveGallery = new Intent(Intent.ACTION_PICK);
        moveGallery.setType("image/*");
        startActivityForResult(moveGallery, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "NewPick");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Image To Text");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent moveCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        moveCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(moveCamera, IMAGE_PICK_CAMERA_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    } else {
                        Toast.makeText(this, "Permisiion denied", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case STORAGE_REQUEST_CODE:
                boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (writeStorageAccepted) {
                    pickFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                try {
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    image_profile.setImageBitmap(selectedImage);

                    insertImages(imageUri);
                } catch (FileNotFoundException e) {
                    e.getLocalizedMessage();
                }
            } else {
                try {
                    Uri getImageUri = data.getData();
                    InputStream imageStream = getContentResolver().openInputStream(getImageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    image_profile.setImageBitmap(selectedImage);

                    insertImages(getImageUri);
                } catch (FileNotFoundException e) {
                    e.getLocalizedMessage();
                }
            }
        }
    }

    private void insertImages(Uri getImageUri) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (getImageUri != null) {
            StorageReference finalStorage = storageReference.child("uploads/" + UUID.randomUUID().toString());
            finalStorage.putFile(getImageUri).continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    task.getException();
                }

                return finalStorage.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri getImageURL = task.getResult();
                    String getCurrentUserID = firebaseAuth.getCurrentUser().getUid();
                    String getURL = getImageURL.toString();

                    firebaseFirestore.collection("Users").document(getCurrentUserID).update("profile", getURL).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(this, "Bberhasil update", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, task1.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}