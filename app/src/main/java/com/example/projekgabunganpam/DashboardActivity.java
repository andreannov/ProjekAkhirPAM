package com.example.projekgabunganpam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
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

    private TextView tvStudentName, tvStudentId, tvStudentEmail;
    private Button btnLogout;
    private ImageView ivBookmark;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tvStudentName = findViewById(R.id.tvStudentName);
        tvStudentId = findViewById(R.id.tvStudentId);
        tvStudentEmail = findViewById(R.id.tvStudentEmail);
        btnLogout = findViewById(R.id.btnLogout);
        ivBookmark = findViewById(R.id.ivBookmark);

        // Listener untuk Tombol Logout (INI SUDAH BENAR)
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(DashboardActivity.this, "Logout berhasil", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        });

        // [PERIKSA BAGIAN INI] Listener untuk Ikon Bookmark
        // Pastikan kodenya persis seperti ini:
        ivBookmark.setOnClickListener(v -> {
            // Kode ini HANYA membuka ActivityTerbaru, tidak melakukan logout.
            Intent intent = new Intent(DashboardActivity.this, ActivityTerbaru.class);
            startActivity(intent);
        });

        loadUserProfile();

        // Kode untuk RecyclerView gedung
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
                        String nim = document.getString("nim");

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