package com.example.projekgabunganpam;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    // Deklarasi variabel untuk elemen UI
    private ImageButton backArrow;
    private TextView tvStudentName;
    private TextView tvStudentId;
    private TextView tvStudentEmail;

    // [PERBAIKAN] Deklarasi untuk Firebase Auth dan Firestore
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // [PERBAIKAN] Inisialisasi Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Inisialisasi semua view dari layout
        initializeViews();

        // Setup listener untuk tombol kembali
        setupListeners();

        // Memulai proses pengambilan data profil dari Firestore
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
     * [PERBAIKAN] Metode ini sekarang mengambil data pengguna dari Firestore
     * bukan lagi menggunakan data dummy.
     */
    private void fetchUserProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Tampilkan "Loading..." selagi mengambil data
            tvStudentName.setText("Loading...");
            tvStudentId.setText("Loading...");
            tvStudentEmail.setText("Loading...");

            String uid = user.getUid();
            DocumentReference docRef = db.collection("users").document(uid);

            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Ambil data dari dokumen Firestore
                        String name = document.getString("name");
                        String email = document.getString("email");
                        String nim = document.getString("nim");

                        // Setelah data didapatkan, perbarui TextViews
                        tvStudentName.setText(name);
                        tvStudentEmail.setText(email);
                        tvStudentId.setText(nim != null ? nim : "NIM belum diatur");

                    } else {
                        Log.d("ProfileActivity", "No such document");
                        Toast.makeText(ProfileActivity.this, "Data profil tidak ditemukan.", Toast.LENGTH_SHORT).show();
                        tvStudentName.setText("Data tidak ada");
                    }
                } else {
                    Log.w("ProfileActivity", "get failed with ", task.getException());
                    Toast.makeText(ProfileActivity.this, "Gagal memuat data profil.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Handle kasus jika tidak ada pengguna yang login
            Toast.makeText(this, "Tidak ada pengguna yang login.", Toast.LENGTH_LONG).show();
            finish(); // Kembali ke halaman sebelumnya
        }
    }
}