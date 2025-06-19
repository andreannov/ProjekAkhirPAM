package com.example.projekgabunganpam;

import android.content.Intent; // <-- [PERUBAHAN] Import Intent
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// [PERUBAHAN] Implementasikan OnFloorClickListener dari adapter
public class FloorActivity extends AppCompatActivity implements FloorAdapter.OnFloorClickListener {

    RecyclerView recyclerView;
    FloorAdapter adapter;
    List<Integer> floorList = new ArrayList<>();
    private String buildingName; // <-- [PERUBAHAN] Jadikan variabel member

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lantai);

        recyclerView = findViewById(R.id.rvFloors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> finish());

        // Ambil info dari dashboard
        buildingName = getIntent().getStringExtra("buildingName");
        int floorCount = getIntent().getIntExtra("floorCount", 0);

        for (int i = 1; i <= floorCount; i++) {
            floorList.add(i);
        }

        // [PERUBAHAN] Kirim 'this' sebagai listener ke adapter
        adapter = new FloorAdapter(floorList, this);
        recyclerView.setAdapter(adapter);
    }

    // [PERUBAHAN] Implementasikan metode dari interface OnFloorClickListener
    @Override
    public void onFloorClick(int floorNumber) {
        // Ketika sebuah lantai diklik, buka DenahActivity
        Intent intent = new Intent(this, DenahActivity.class);
        // Kirim nama gedung dan nomor lantai yang dipilih ke DenahActivity
        intent.putExtra("GEDUNG", buildingName);
        intent.putExtra("LANTAI", floorNumber);
        startActivity(intent);
    }
}