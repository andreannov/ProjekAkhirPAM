package com.example.projekgabunganpam; // Sesuai dengan struktur proyek Anda

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ActivitySignUp extends AppCompatActivity {

    // Deklarasi variabel untuk setiap elemen UI
    private ImageButton btnBack;
    private TextInputEditText etName, etEmail, etPassword;
    private MaterialButton btnSignUp;
    private TextView tvLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Inisialisasi Views
        initializeViews();

        // Setup listener untuk tombol dan link
        setupListeners();
    }

    // Metode untuk inisialisasi semua view agar onCreate lebih rapi
    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        // Penting: Ambil TextInputEditText, bukan TextInputLayout
        // Anda perlu menambahkan ID pada TextInputEditText di XML
        // Contoh: android:id="@+id/etName"
        etName = findViewById(R.id.etName); // Asumsikan ID ini ditambahkan di XML
        etEmail = findViewById(R.id.etEmailLogin); // ID ini sudah ada di XML
        etPassword = findViewById(R.id.etPasswordLogin); // ID ini sudah ada di XML
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLoginLink = findViewById(R.id.tvLoginLink);
    }

    // Metode untuk mengatur semua listener
    private void setupListeners() {
        // 1. Listener untuk tombol kembali
        btnBack.setOnClickListener(v -> finish());

        // 2. Listener untuk link "Login"
        tvLoginLink.setOnClickListener(v -> {
            // Pindah ke MainActivity (halaman login)
            Intent intent = new Intent(ActivitySignUp.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Tutup halaman sign up
        });

        // 3. Listener untuk tombol Sign Up
        btnSignUp.setOnClickListener(v -> {
            // Panggil metode untuk memvalidasi dan memproses sign up
            validateAndSignUp();
        });
    }

    // Metode untuk validasi input dan proses sign up
    private void validateAndSignUp() {
        // Ambil input dari EditText dan hilangkan spasi di awal/akhir
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validasi input
        if (TextUtils.isEmpty(name)) {
            etName.setError("Nama tidak boleh kosong");
            etName.requestFocus();
            return; // Hentikan proses
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email tidak boleh kosong");
            etEmail.requestFocus();
            return;
        }

        // Cek format email valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Masukkan format email yang valid");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password tidak boleh kosong");
            etPassword.requestFocus();
            return;
        }

        // Cek panjang password (misalnya minimal 6 karakter)
        if (password.length() < 6) {
            etPassword.setError("Password minimal 6 karakter");
            etPassword.requestFocus();
            return;
        }

        // Jika semua validasi lolos
        // TODO: Tambahkan logika pendaftaran di sini
        // Misalnya, menyimpan data ke database (Firebase, SQLite, dll.)
        // atau memanggil API.

        // Untuk sekarang, kita hanya tampilkan pesan Toast sebagai penanda
        String message = "Sign Up Berhasil!\nNama: " + name + "\nEmail: " + email;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        // Setelah berhasil, bisa langsung arahkan ke halaman login atau utama
        Intent intent = new Intent(ActivitySignUp.this, LoginActivity.class);
        startActivity(intent);
        finishAffinity(); // Menutup semua activity sebelumnya di stack ini
    }
}