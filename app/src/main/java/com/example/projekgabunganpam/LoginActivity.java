package com.example.projekgabunganpam;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmailLogin, etPasswordLogin;
    private Button btnLogin;
    private TextView tvGoToRegister;
    private ImageButton btnBack;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inisialisasi komponen
        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);
        String text = "New to Found U? <u><font color='#2196F3'>Sign Up</font></u>";
        tvGoToRegister.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        btnBack = findViewById(R.id.btnBack);

        mAuth = FirebaseAuth.getInstance();

        // Aksi klik tombol Login
        btnLogin.setOnClickListener(v -> {
            String email = etEmailLogin.getText().toString().trim();
            String password = etPasswordLogin.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show();
                            // == PERBAIKAN LOGIKA ==
                            // Navigasi ke DashboardActivity setelah login berhasil
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Login gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // == PERBAIKAN LOGIKA ==
        // Aksi klik "Sign Up" untuk pindah ke halaman pendaftaran
        tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ActivitySignUp.class);
            startActivity(intent);
        });

        // Aksi tombol back
        btnBack.setOnClickListener(v -> {
            finish(); // kembali ke activity sebelumnya
        });
    }
}