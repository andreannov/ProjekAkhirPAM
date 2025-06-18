package com.example.projekgabunganpam;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FloorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloorAdapter adapter;
    List<Integer> floorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lantai);

        recyclerView = findViewById(R.id.rvFloors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));  // Ganti ke LinearLayoutManager vertikal

        // Tombol back
        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Tutup activity ini, kembali ke activity sebelumnya
            }
        });

        // Ambil info dari dashboard
        String buildingName = getIntent().getStringExtra("buildingName");
        int floorCount = getIntent().getIntExtra("floorCount", 0);

        // Buat daftar lantai sesuai jumlahnya
        for (int i = 1; i <= floorCount; i++) {
            floorList.add(i);
        }

        adapter = new FloorAdapter(floorList);
        recyclerView.setAdapter(adapter);
    }
}
