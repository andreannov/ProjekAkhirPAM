package com.example.projekgabunganpam;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ActivityTerbaru extends AppCompatActivity implements BookmarkAdapter.OnBookmarkClickListener {

    private ImageButton btnBack;
    private Button btnTerbaru;
    private Button btnFavorite;

    private RecyclerView rvBookmarks;
    private BookmarkAdapter adapter;
    private List<Bookmark> bookmarkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terbaru);

        initializeViews();
        setupButtonListeners();
        setupRecyclerView();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        btnTerbaru = findViewById(R.id.btnTerbaru);
        btnFavorite = findViewById(R.id.btnFavorite);
        rvBookmarks = findViewById(R.id.rvBookmarks);
    }

    private void setupRecyclerView() {
        bookmarkList = new ArrayList<>();

        // [PERBAIKAN] Pastikan parameter ke-4 selalu dari R.drawable
        // Jangan pernah menggunakan R.layout di sini
        bookmarkList.add(new Bookmark("Ruang F3.1", "Ruang Kelas", "Kelas untuk mata kuliah Dasar Pemrograman.", R.drawable.ic_gedung_f));
        bookmarkList.add(new Bookmark("Ruang G2.2", "Laboratorium", "Lab Jaringan Komputer.", R.drawable.ic_gedung_g));
        bookmarkList.add(new Bookmark("Ruang F3.2", "Ruang Kelas", "Kelas untuk mata kuliah Algoritma.", R.drawable.ic_gedung_f));
        bookmarkList.add(new Bookmark("Ruang F2.8", "Ruang Dosen", "Ruang istirahat dosen.", R.drawable.ic_icon));
        bookmarkList.add(new Bookmark("Ruang G1.1", "Auditorium", "Untuk seminar dan acara besar.", R.drawable.ic_gedung_g));

        adapter = new BookmarkAdapter(bookmarkList, this);
        rvBookmarks.setLayoutManager(new LinearLayoutManager(this));
        rvBookmarks.setAdapter(adapter);
    }

    private void setupButtonListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnFavorite.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityTerbaru.this, ActivityFavorite.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBookmarkClick(Bookmark bookmark) {
        showRoomDetailPopup(bookmark);
    }

    private void showRoomDetailPopup(Bookmark bookmark) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_detail_ruangan, null);

        TextView tvRoomName = popupView.findViewById(R.id.tvRoomName);
        TextView tvRoomType = popupView.findViewById(R.id.tvRoomType);
        TextView tvStatus = popupView.findViewById(R.id.tvStatus);
        TextView tvNextActivity = popupView.findViewById(R.id.tvNextActivity);

        tvRoomName.setText(bookmark.getRoomName());
        tvRoomType.setText(bookmark.getRoomType());
        tvStatus.setText("Kosong");
        tvNextActivity.setText("-");

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.setElevation(20);
        popupWindow.showAtLocation(findViewById(R.id.rvBookmarks), Gravity.CENTER, 0, 0);
    }
}