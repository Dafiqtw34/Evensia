package com.example.evensia.gedung.pesanan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.evensia.R;
import com.example.evensia.booking.Booking;
import com.example.evensia.gedung.model.ModelGedung;
import com.example.evensia.gedung.pesanan.model.ModelPesanan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class pesan_gedung extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private List<ModelGedung> modelGedungList;
    private ImageView buttonBack, calender;
    private TextView judul, isi, alamat, nilai, tvDate, email, nomor, harga;
    private EditText nama, nomoruser, emailuser;
    private RadioGroup radio;
    private RadioButton jam1;
    private ImageView gambar1, gambar2, gambar3, gambar4;
    private Button bayar;
    private int mDate, mMonth, mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_gedung);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        modelGedungList = new ArrayList<>();

        setID();
        onClickMethod();
        getDataGedung();
    }

    private void setID() {
        recyclerView = findViewById(R.id.recyclerView);
        buttonBack = findViewById(R.id.back);
        judul = findViewById(R.id.judul);
        isi = findViewById(R.id.isi);
        alamat = findViewById(R.id.alamat);
        harga = findViewById(R.id.harga);
        email = findViewById(R.id.email);
        nomor = findViewById(R.id.nomor);
        nomoruser = findViewById(R.id.nomoruser);
        nama = findViewById(R.id.name);
        emailuser = findViewById(R.id.email_user);
        nilai = findViewById(R.id.nilai);
        gambar1 = findViewById(R.id.gambar1);
        gambar2 = findViewById(R.id.gambar2);
        gambar3 = findViewById(R.id.gambar3);
        gambar4 = findViewById(R.id.gambar4);
        tvDate = findViewById(R.id.tv_date);
        calender = findViewById(R.id.calender);
        radio = findViewById(R.id.radio);
        bayar = findViewById(R.id.bayar);
    }

    private void onClickMethod() {
        buttonBack.setOnClickListener(view -> {
            onBackPressed();
        });

        calender.setOnClickListener(view -> {

            final Calendar calendar = Calendar.getInstance();
            mDate = calendar.get(Calendar.DATE);
            mMonth = calendar.get(Calendar.MONTH);
            mYear = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(pesan_gedung.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                    SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                    Date dates = new Date(year, month, date - 1);
                    String dayOfWeek = simpledateformat.format(dates);
                    String[] bulan = {"Jauari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus",
                            "September", "Oktober", "November", "Desember"};
                    tvDate.setText("Tanggal pesanan " + dayOfWeek + "," + date + " " + bulan[month] + " " + year);

                }
            }, mYear, mMonth, mDate);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });
    }

    private void getDataGedung() {
        String getId = getIntent().getStringExtra("sendPesananID");

        if (getId != null && !getId.equals("")) {
            firebaseFirestore.collection("review").document(getId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String getJudul = document.getString("judul");
                        judul.setText(getJudul);

                        String getAlamat = document.getString("alamat");
                        alamat.setText(getAlamat);

                        String getHarga = document.getString("harga");
                        harga.setText(getHarga);

                        String getIsi = document.getString("isi");
                        isi.setText(getIsi);

                        String getEmail = document.getString("email");
                        email.setText(getEmail);

                        String getNohp = document.getString("nomor");
                        nomor.setText(getNohp);

                        String getNilai = document.getString("nilai");
                        nilai.setText(getNilai);

                        RequestOptions options = new RequestOptions()
                                .placeholder(R.mipmap.ic_launcher_round)
                                .error(R.mipmap.ic_launcher_round);

                        String getGambar1 = document.getString("gambar1");
                        Glide.with(pesan_gedung.this)
                                .load(getGambar1)
                                .apply(options)
                                .into(gambar1);

                        String getGambar2 = document.getString("gambar2");
                        Glide.with(pesan_gedung.this)
                                .load(getGambar2)
                                .apply(options)
                                .into(gambar2);

                        String getGambar3 = document.getString("gambar3");
                        Glide.with(pesan_gedung.this)
                                .load(getGambar3)
                                .apply(options)
                                .into(gambar3);

                        String getGambar4 = document.getString("gambar4");
                        Glide.with(pesan_gedung.this)
                                .load(getGambar4)
                                .apply(options)
                                .into(gambar4);

                        bayar.setOnClickListener(view -> {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(pesan_gedung.this);
                            builder1.setMessage("Apa data anda sudah sesuai? ");
                            builder1.setCancelable(false);

                            builder1.setPositiveButton("Yes", (dialog, id) -> {
                                        int getRadioButtonID = radio.getCheckedRadioButtonId();
                                        jam1 = findViewById(getRadioButtonID);
                                        String getName = nama.getText().toString();
                                        String getEmailuser = emailuser.getText().toString();
                                        String getNomor = nomoruser.getText().toString();
                                        String getJam = jam1.getText().toString();
                                        String getTv_date = tvDate.getText().toString();

                                        insertToDatabase(getAlamat, getHarga, getIsi, getJudul, getNilai, getGambar1, getGambar2, getGambar3, getGambar4, getJam, getName, getEmail, getEmailuser, getNomor, getTv_date, firebaseAuth, firebaseFirestore);
                                        dialog.dismiss();
                                    });

                            builder1.setNegativeButton("No", (dialog, id) -> dialog.dismiss());

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        });

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            });
        }
    }

    private void insertToDatabase(String getAlamat, String getHarga, String getIsi, String getJudul, String getNilai, String getGambar1, String getGambar2, String getGambar3, String getGambar4, String getJam, String getName, String getEmail, String getEmailuser, String getNomor, String getTv_date, FirebaseAuth fAuth, FirebaseFirestore firebaseFirestore) {
        String getUserID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        String getRandomID = firebaseFirestore.collection("pesan_gedung").document().getId();

        ModelPesanan modelPesanan = new ModelPesanan();
        modelPesanan.setAlamat(getAlamat);
        modelPesanan.setHarga(getHarga);
        modelPesanan.setIsi(getIsi);
        modelPesanan.setJudul(getJudul);
        modelPesanan.setNilai(getNilai);
        modelPesanan.setGambar1(getGambar1);
        modelPesanan.setGambar2(getGambar2);
        modelPesanan.setGambar3(getGambar3);
        modelPesanan.setGambar4(getGambar4);
        modelPesanan.setJam(getJam);
        modelPesanan.setName(getName);
        modelPesanan.setJam(getJam);
        modelPesanan.setEmail(getEmail);
        modelPesanan.setEmailuser(getEmailuser);
        modelPesanan.setNomor(getNomor);
        modelPesanan.setTv_date(getTv_date);
        firebaseFirestore.collection("Users").document(getUserID).collection("pesan_gedung").document(getRandomID).set(modelPesanan).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(pesan_gedung.this, "Berhasil", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(pesan_gedung.this, Booking.class);
                startActivity(intent);
            } else {
                Toast.makeText(pesan_gedung.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}