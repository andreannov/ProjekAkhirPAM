package com.example.projekgabunganpam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Building> buildingList;
    BuildingAdapter adapter;

    // [PERUBAHAN] Tambahkan variabel untuk data profil dan Firebase
    private TextView tvStudentName, tvStudentId, tvStudentEmail;
    private Button btnLogout;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // [PERUBAHAN] Inisialisasi Firebase Auth dan Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // [PERUBAHAN] Inisialisasi TextViews dan tombol Logout
        tvStudentName = findViewById(R.id.tvStudentName);
        tvStudentId = findViewById(R.id.tvStudentId);
        tvStudentEmail = findViewById(R.id.tvStudentEmail);
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(DashboardActivity.this, "Logout berhasil", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        });

        // [PERUBAHAN] Panggil metode untuk memuat data profil pengguna
        loadUserProfile();

        // Kode untuk RecyclerView gedung tetap sama
        recyclerView = findViewById(R.id.rvBuildings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buildingList = new ArrayList<>();
        List<String> facilitiesF = new ArrayList<>();
        facilitiesF.add("WiFi");
        facilitiesF.add("Lift");
        buildingList.add(new Building("Gedung F", 12, R.drawable.ic_gedung_f, facilitiesF));
        buildingList.add(new Building("Gedung G", 2, R.drawable.ic_gedung_g));

        adapter = new BuildingAdapter(buildingList, building -> {
            Intent intent = new Intent(this, FloorActivity.class);
            intent.putExtra("buildingName", building.getName());
            intent.putExtra("floorCount", building.getFloors());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    // [PERUBAHAN] Metode baru untuk mengambil data dari Firestore
    private void loadUserProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DocumentReference docRef = db.collection("users").document(uid);

            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Dashboard", "DocumentSnapshot data: " + document.getData());
                        String name = document.getString("name");
                        String email = document.getString("email");
                        // Asumsikan NIM belum ada, jadi kita set manual
                        String nim = document.getString("nim"); // akan null jika belum ada

                        tvStudentName.setText(name);
                        tvStudentEmail.setText(email);
                        tvStudentId.setText(nim != null ? nim : "NIM belum diatur");

                    } else {
                        Log.d("Dashboard", "No such document");
                        tvStudentName.setText("Data tidak ditemukan");
                    }
                } else {
                    Log.d("Dashboard", "get failed with ", task.getException());
                    Toast.makeText(this, "Gagal memuat data profil.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}