package com.example.projekgabunganpam; // Sesuaikan dengan package proyek Anda

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityTerbaru extends AppCompatActivity {

    // Deklarasi variabel untuk view
    private ImageButton btnBack;
    private Button btnTerbaru;
    private Button btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Menghubungkan layout activity_terbaru.xml dengan activity ini
        // Pastikan nama file XML Anda adalah 'activity_terbaru.xml'
        setContentView(R.layout.activity_terbaru);

        // Inisialisasi view dari layout
        initializeViews();

        // Setup listener untuk setiap tombol
        setupButtonListeners();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        btnTerbaru = findViewById(R.id.btnTerbaru);
        btnFavorite = findViewById(R.id.btnFavorite);
    }

    private void setupButtonListeners() {
        // 1. Listener untuk tombol kembali (btnBack)
        btnBack.setOnClickListener(v -> finish());

        // 2. Listener untuk tombol "Terbaru" (btnTerbaru)
        // Karena kita sudah berada di halaman Terbaru, tombol ini tidak perlu
        // melakukan aksi apa pun saat diklik.
        btnTerbaru.setOnClickListener(v -> {
            // Tidak ada aksi yang diperlukan
        });

        // 3. Listener untuk tombol "Favorite" (btnFavorite)
        btnFavorite.setOnClickListener(v -> {
            // Membuat Intent untuk pindah ke ActivityFavorite
            Intent intent = new Intent(ActivityTerbaru.this, ActivityFavorite.class);
            startActivity(intent);
            // Menutup activity terbaru agar tidak menumpuk saat berganti tab
            finish();
        });
    }
}