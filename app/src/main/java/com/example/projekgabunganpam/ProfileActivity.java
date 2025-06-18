package com.example.projekgabunganpam; // Sesuaikan dengan package proyek Anda

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    // Deklarasi variabel untuk elemen UI
    private ImageButton backArrow;
    private TextView tvStudentName;
    private TextView tvStudentId;
    private TextView tvStudentEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pastikan nama file XML Anda adalah activity_profile.xml
        setContentView(R.layout.activity_profile);

        // Inisialisasi semua view dari layout
        initializeViews();

        // Setup listener untuk tombol kembali
        setupListeners();

        // Memulai proses pengambilan data profil (simulasi)
        fetchUserProfile();
    }

    private void initializeViews() {
        backArrow = findViewById(R.id.back_arrow);
        tvStudentName = findViewById(R.id.tvStudentName);
        tvStudentId = findViewById(R.id.tvStudentId);
        tvStudentEmail = findViewById(R.id.tvStudentEmail);
    }

    private void setupListeners() {
        // Listener untuk tombol kembali
        backArrow.setOnClickListener(v -> finish());
    }

    /**
     * Metode ini mensimulasikan pengambilan data pengguna dari server.
     * Di aplikasi nyata, di sinilah Anda akan memanggil Firebase atau API backend Anda.
     */
    private void fetchUserProfile() {
        // Teks "Loading..." sudah diatur di XML, jadi kita tidak perlu mengaturnya lagi.
        // Kita gunakan Handler untuk menunda eksekusi selama 2 detik (2000 milidetik)
        // untuk mensimulasikan waktu yang dibutuhkan untuk mengambil data dari internet.
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Data dummy sebagai contoh
            String name = "Budi Santoso";
            String nim = "215150700111001";
            String email = "budi.s@student.ub.ac.id";

            // Setelah data "didapatkan", perbarui TextViews
            tvStudentName.setText(name);
            tvStudentId.setText(nim);
            tvStudentEmail.setText(email);

        }, 2000); // Penundaan selama 2 detik
    }
}