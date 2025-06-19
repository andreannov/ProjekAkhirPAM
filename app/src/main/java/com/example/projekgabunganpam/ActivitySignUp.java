package com.example.projekgabunganpam;

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
import com.google.firebase.auth.FirebaseAuth;

public class ActivitySignUp extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etPassword;
    private MaterialButton btnSignUp;
    private TextView tvLoginLink;
    private ImageButton btnBack;

    // Tambahkan instance FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Inisialisasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLoginLink = findViewById(R.id.tvLoginLink);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        tvLoginLink.setOnClickListener(v -> {
            Intent intent = new Intent(ActivitySignUp.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        btnSignUp.setOnClickListener(v -> {
            validateAndSignUp();
        });
    }

    private void validateAndSignUp() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Nama tidak boleh kosong");
            etName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Masukkan email yang valid");
            etEmail.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password minimal 6 karakter");
            etPassword.requestFocus();
            return;
        }

        // == PERBAIKAN LOGIKA ==
        // Proses pendaftaran pengguna baru ke Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Pendaftaran ke Firebase berhasil
                        Toast.makeText(ActivitySignUp.this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show();

                        // Arahkan ke halaman Login agar pengguna bisa login dengan akun baru
                        Intent intent = new Intent(ActivitySignUp.this, LoginActivity.class);
                        startActivity(intent);
                        finishAffinity(); // Tutup semua activity sebelumnya
                    } else {
                        // Jika pendaftaran gagal, tampilkan pesan error dari Firebase
                        Toast.makeText(ActivitySignUp.this, "Pendaftaran Gagal: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}