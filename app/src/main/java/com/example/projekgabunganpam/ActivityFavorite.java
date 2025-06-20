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

// [PERUBAHAN] Implementasikan listener dari adapter
public class ActivityFavorite extends AppCompatActivity implements BookmarkAdapter.OnBookmarkClickListener {

    private ImageButton btnBack;
    private Button btnTerbaru;
    private Button btnFavorite;

    // [PERUBAHAN] Deklarasi untuk RecyclerView
    private RecyclerView rvFavorites;
    private BookmarkAdapter adapter;
    private List<Bookmark> favoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        initializeViews();
        setupButtonListeners();
        setupRecyclerView();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        btnTerbaru = findViewById(R.id.btnTerbaru);
        btnFavorite = findViewById(R.id.btnFavorite);
        rvFavorites = findViewById(R.id.rvFavorites); // Inisialisasi RecyclerView
    }

    // [PERUBAHAN] Metode untuk mengatur RecyclerView
    private void setupRecyclerView() {
        favoriteList = new ArrayList<>();
        // Buat data dummy yang berbeda untuk halaman Favorite
        favoriteList.add(new Bookmark("Ruang G1.1", "Auditorium", "Favorit untuk seminar.", R.drawable.ic_gedung_g));
        favoriteList.add(new Bookmark("Ruang F4.5", "Laboratorium", "Lab paling sering dipakai.", R.drawable.ic_gedung_f));

        adapter = new BookmarkAdapter(favoriteList, this);
        rvFavorites.setLayoutManager(new LinearLayoutManager(this));
        rvFavorites.setAdapter(adapter);
    }

    private void setupButtonListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnTerbaru.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityFavorite.this, ActivityTerbaru.class);
            startActivity(intent);
            finish();
        });
        // Tombol Favorite tidak perlu aksi karena sudah di halaman ini
        btnFavorite.setOnClickListener(v -> {});
    }

    // [PERUBAHAN] Implementasi metode klik dari adapter
    @Override
    public void onBookmarkClick(Bookmark bookmark) {
        showRoomDetailPopup(bookmark);
    }

    // [PERUBAHAN] Metode untuk menampilkan popup detail ruangan
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
        popupWindow.showAtLocation(findViewById(R.id.rvFavorites), Gravity.CENTER, 0, 0);
    }
}