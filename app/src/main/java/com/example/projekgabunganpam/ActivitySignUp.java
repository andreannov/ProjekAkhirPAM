package com.example.projekgabunganpam;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore; // <-- [PERUBAHAN] Import Firestore

import java.util.HashMap; // <-- [PERUBAHAN] Import HashMap
import java.util.Map; // <-- [PERUBAHAN] Import Map

public class ActivitySignUp extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etPassword;
    private MaterialButton btnSignUp;
    private TextView tvLoginLink;
    private ImageButton btnBack;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
        btnSignUp.setOnClickListener(v -> validateAndSignUp());
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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ActivitySignUp.this, "Pendaftaran akun berhasil!", Toast.LENGTH_SHORT).show();

                        // [PERUBAHAN] Menyimpan data tambahan (nama & email) ke Firestore
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String userId = mAuth.getCurrentUser().getUid();

                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name);
                        user.put("email", email);
                        // Tambahkan field lain jika perlu, misal: user.put("nim", "12345");

                        db.collection("users").document(userId).set(user)
                                .addOnSuccessListener(aVoid -> Log.d("Firestore", "User profile created for " + userId))
                                .addOnFailureListener(e -> Log.w("Firestore", "Error creating user profile", e));

                        // Arahkan ke halaman Login
                        Intent intent = new Intent(ActivitySignUp.this, LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    } else {
                        Toast.makeText(ActivitySignUp.this, "Pendaftaran Gagal: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}