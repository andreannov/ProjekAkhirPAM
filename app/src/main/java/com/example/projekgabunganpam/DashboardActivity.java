package com.example.projekgabunganpam;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Building> buildingList;
    BuildingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.rvBuildings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Buat daftar gedung
        buildingList = new ArrayList<>();
        List<String> facilitiesF = new ArrayList<>();
        facilitiesF.add("WiFi");
        facilitiesF.add("Lift");

        buildingList.add(new Building("Gedung F", 12, R.drawable.ic_gedung_f, facilitiesF));

        buildingList.add(new Building("Gedung G", 2, R.drawable.ic_gedung_g));  // Gunakan gambar berbeda

        // Pasang adapter
        adapter = new BuildingAdapter(buildingList, building -> {
            // Ketika gedung diklik, pindah ke halaman lantai
            Intent intent = new Intent(this, FloorActivity.class);
            intent.putExtra("buildingName", building.getName());
            intent.putExtra("floorCount", building.getFloors());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}
