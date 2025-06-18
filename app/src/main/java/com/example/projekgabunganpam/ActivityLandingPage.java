package com.example.projekgabunganpam; // Sesuaikan dengan package proyek Anda

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityLandingPage extends AppCompatActivity {

    // Deklarasi variabel untuk elemen UI
    private Button buttonLogin;
    private Button buttonSignUp;
    private TextView textViewMoreInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pastikan nama file XML Anda adalah activity_landing_page.xml
        setContentView(R.layout.activity_landing_page);

        // Panggil metode untuk inisialisasi view
        initializeViews();

        // Panggil metode untuk setup listeners
        setupListeners();
    }

    // Metode untuk menginisialisasi semua view dari layout
    private void initializeViews() {
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewMoreInfo = findViewById(R.id.textViewMoreInfo);
    }

    // Metode untuk mengatur semua listener tombol dan teks
    private void setupListeners() {
        // 1. Listener untuk tombol Login
        buttonLogin.setOnClickListener(v -> {
            // Membuat Intent untuk pindah ke MainActivity (Halaman Login)
            // Asumsi: MainActivity adalah halaman login Anda
            Intent intent = new Intent(ActivityLandingPage.this, LoginActivity.class);
            startActivity(intent);
        });

        // 2. Listener untuk tombol Sign Up
        buttonSignUp.setOnClickListener(v -> {
            // Membuat Intent untuk pindah ke ActivitySignUp
            Intent intent = new Intent(ActivityLandingPage.this, ActivitySignUp.class);
            startActivity(intent);
        });

        // 3. Listener untuk teks "More Information"
        textViewMoreInfo.setOnClickListener(v -> {
            // Menampilkan dialog informasi sederhana
            showMoreInfoDialog();
        });
    }

    // Metode untuk menampilkan dialog informasi
    private void showMoreInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About Found U App");
        builder.setMessage("Found U adalah aplikasi inovatif yang dirancang untuk membantu Anda menemukan informasi ruangan dengan cepat dan mudah. Selamat menggunakan aplikasi kami!");

        // Menambahkan tombol "OK" untuk menutup dialog
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss(); // Menutup dialog
        });

        // Membuat dan menampilkan dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}