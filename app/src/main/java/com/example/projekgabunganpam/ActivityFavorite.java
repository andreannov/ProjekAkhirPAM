// Nama package disesuaikan dengan struktur proyek Anda
package com.example.projekgabunganpam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityFavorite extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnTerbaru;
    private Button btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // Inisialisasi semua view dari layout
        btnBack = findViewById(R.id.btnBack);
        btnTerbaru = findViewById(R.id.btnTerbaru);
        btnFavorite = findViewById(R.id.btnFavorite);
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        // Listener untuk tombol kembali
        btnBack.setOnClickListener(v -> finish());

        // Listener untuk tombol "Terbaru"
        btnTerbaru.setOnClickListener(v -> {
            // Kode ini sudah benar karena ActivityTerbaru.java ada di proyek Anda
            Intent intent = new Intent(ActivityFavorite.this, ActivityTerbaru.class);
            startActivity(intent);
            finish(); // Tutup activity ini
        });

        // Listener untuk tombol "Favorite" (tidak melakukan apa-apa)
        btnFavorite.setOnClickListener(v -> {
            // Anda sudah di halaman ini, jadi tidak perlu ada aksi
        });
    }
}