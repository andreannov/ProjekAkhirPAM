package com.example.projekgabunganpam;

import android.content.SharedPreferences;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.TypedValue;
import android.util.Pair;
import java.util.HashSet;
import java.util.Set;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DenahActivity extends AppCompatActivity {

    private PhotoView photoView;
    private FrameLayout pinContainer;

    // Menyimpan referensi semua pin dan posisinya
    private final List<ImageView> pinViews = new ArrayList<>();
    private final HashMap<ImageView, Pair<Double, Double>> pinPositions = new HashMap<>();

    private Set<String> bookmarkedRooms;

    // Uji coba
    private String gedung = "F";
    private int lantai = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denah);

        photoView = findViewById(R.id.ivDenah);
        pinContainer = findViewById(R.id.pinContainer);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Load bookmark dari SharedPreferences
        bookmarkedRooms = loadBookmarks();

        // Set gambar denah
        int denahResId = getDenahResource(gedung, lantai);
        if (denahResId != 0) {
            photoView.setImageResource(denahResId);
            photoView.setMaximumScale(5.0f);
            photoView.setOnMatrixChangeListener(rect -> updatePinPositions());
        } else {
            Toast.makeText(this, "Denah tidak ditemukan", Toast.LENGTH_SHORT).show();
        }

        // Tambahkan semua pin
        List<PinData> pinList = getAllPins();
        for (PinData pin : pinList) {
            if (pin.gedung.equals(gedung) && pin.lantai == lantai) {
                addPin(pin);
            }
        }
    }

    private int getDenahResource(String gedung, int lantai) {
        if (gedung.equals("F")) {
            switch (lantai) {
                case 1: return R.drawable.denah_lantaif1;
                case 2: return R.drawable.denah_lantaif2;
                case 3: return R.drawable.denah_lantaif3;
                case 4: return R.drawable.denah_lantaif4;
                case 5: return R.drawable.denah_lantaif5;
            }
        } else if (gedung.equals("G")) {
            switch (lantai) {
                case 1: return R.drawable.denah_lantaig1;
                case 2: return R.drawable.denah_lantaig2;
            }
        }
        return 0;
    }

    private void addPin(PinData pin) {
        ImageView pinView = new ImageView(this);
        pinView.setImageResource(R.drawable.ic_pin);
        pinView.setContentDescription(pin.getRoomName());

        // SET UKURAN PIN (misal 64dp x 64dp)
        int sizeInDp = 32; // contoh ukuran 32dp (lebih proporsional)
        int sizeInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                sizeInDp,
                getResources().getDisplayMetrics()
        );
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(sizeInPx, sizeInPx);
        pinView.setLayoutParams(params);

        // Klik pin
        pinView.setOnClickListener(v -> showPopup(pin));

        pinContainer.addView(pinView);
        pinViews.add(pinView);
        pinPositions.put(pinView, new Pair<>(pin.xRatio, pin.yRatio));

        // Posisi awal
        pinContainer.post(this::updatePinPositions);
    }

    private void updatePinPositions() {
        if (photoView.getDrawable() == null) return;

        RectF displayRect = photoView.getDisplayRect();
        if (displayRect == null) return;

        int imgWidth = photoView.getDrawable().getIntrinsicWidth();
        int imgHeight = photoView.getDrawable().getIntrinsicHeight();

        for (ImageView pinView : pinViews) {
            Pair<Double, Double> ratio = pinPositions.get(pinView);
            if (ratio == null) continue;

            float x = (float) (displayRect.left + ratio.first * displayRect.width());
            float y = (float) (displayRect.top + ratio.second * displayRect.height());

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) pinView.getLayoutParams();
            if (params == null) {
                params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                );
            }

            // Sesuaikan agar pin berada di tengah titiknya
            params.leftMargin = (int) (x - pinView.getWidth() / 2f);
            params.topMargin = (int) (y - pinView.getHeight() / 2f);
            pinView.setLayoutParams(params);
        }
    }

    private void showPopup(PinData pinData) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_detail_ruangan, null);

        TextView tvRoomName = popupView.findViewById(R.id.tvRoomName);
        TextView tvRoomType = popupView.findViewById(R.id.tvRoomType);
        TextView tvStatus = popupView.findViewById(R.id.tvStatus);
        TextView tvNextActivity = popupView.findViewById(R.id.tvNextActivity);
        ImageButton btnBookmark = popupView.findViewById(R.id.btnBookmark);

        tvRoomName.setText(pinData.getRoomName());
        tvRoomType.setText(pinData.getRoomType());
        tvStatus.setText(pinData.isOccupied() ? "Terpakai" : "Kosong");
        tvNextActivity.setText(pinData.getNextActivityTime().isEmpty() ? "-" : pinData.getNextActivityTime());

        // Ganti ikon bookmark tergantung status
        boolean isBookmarked = bookmarkedRooms.contains(pinData.getRoomName());
        btnBookmark.setImageResource(isBookmarked ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark);

        btnBookmark.setOnClickListener(v -> {
            if (bookmarkedRooms.contains(pinData.getRoomName())) {
                bookmarkedRooms.remove(pinData.getRoomName());
                btnBookmark.setImageResource(R.drawable.ic_bookmark);
            } else {
                bookmarkedRooms.add(pinData.getRoomName());
                btnBookmark.setImageResource(R.drawable.ic_bookmark_filled);
            }
            saveBookmarks();
        });

        // Atur ukuran & animasi
        // int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setElevation(20);
        popupWindow.showAtLocation(pinContainer, Gravity.CENTER, 0, 0);
    }

    private List<PinData> getAllPins() {
        List<PinData> pinList = new ArrayList<>();
        // Gedung F - Lantai 1
        pinList.add(new PinData(0.1990, 0.8672, "Game Center", "F", 1, "Rekreasi", false, "15:00"));

        // Gedung F - Lantai 2
        pinList.add(new PinData(0.5634, 0.8672, "F2.1", "F", 2, "Ruang Kelas", true, "09:30"));
        pinList.add(new PinData(0.7842, 0.8655, "F2.2", "F", 2, "Ruang Kelas", false, "13:00"));
        pinList.add(new PinData(0.9378, 0.8087, "F2.4", "F", 2, "Ruang Kelas", true, "11:00"));
        pinList.add(new PinData(0.9361, 0.5014, "F2.5", "F", 2, "Ruang Kelas", false, "14:15"));
        pinList.add(new PinData(0.9462, 0.2248, "F2.6", "F", 2, "Ruang Kelas", false, "16:00"));
        pinList.add(new PinData(0.7926, 0.1539, "F2.8", "F", 2, "Ruang Kelas", false, "08:00"));
        pinList.add(new PinData(0.5622, 0.1492, "F2.9", "F", 2, "Ruang Kelas", true, "10:45"));
        pinList.add(new PinData(0.2784, 0.6645, "F2.10", "F", 2, "Ruang Kerja Bersama", false, "-"));
        pinList.add(new PinData(0.2116, 0.3454, "F2.11", "F", 2, "Ruang Baca", true, "-"));
        pinList.add(new PinData(0.0714, 0.6693, "F2.12", "F", 2, "Ruang Kerja Bersama", false, "-"));

        // Gedung F - Lantai 3
        pinList.add(new PinData(0.4720, 0.8962, "F3.1", "F", 3, "Ruang Kelas", false, "08:00"));
        pinList.add(new PinData(0.5806, 0.5676, "F3.2", "F", 3, "Ruang Kelas", true, "09:45"));
        pinList.add(new PinData(0.6724, 0.8962, "F3.3", "F", 3, "Ruang Kelas", false, "11:00"));
        pinList.add(new PinData(0.6757, 0.5629, "F3.4", "F", 3, "Ruang Kelas", true, "13:00"));
        pinList.add(new PinData(0.7876, 0.5652, "F3.5", "F", 3, "Ruang Kelas", false, "14:30"));
        pinList.add(new PinData(0.8310, 0.9104, "F3.6A", "F", 3, "Ruang Kelas", true, "15:30"));
        pinList.add(new PinData(0.9495, 0.9080, "F3.6B", "F", 3, "Ruang Kelas", false, "10:00"));
        pinList.add(new PinData(0.8193, 0.0806, "F3.7A", "F", 3, "Ruang Kelas", false, "12:15"));
        pinList.add(new PinData(0.9545, 0.0853, "F3.7B", "F", 3, "Ruang Kelas", true, "13:30"));
        pinList.add(new PinData(0.7859, 0.4210, "F3.8", "F", 3, "Ruang Kelas", false, "09:00"));
        pinList.add(new PinData(0.6824, 0.0924, "F3.9", "F", 3, "Ruang Kelas", true, "10:30"));
        pinList.add(new PinData(0.6807, 0.4305, "F3.10", "F", 3, "Ruang Kelas", false, "16:00"));
        pinList.add(new PinData(0.4787, 0.0995, "F3.11", "F", 3, "Ruang Kelas", true, "11:30"));
        pinList.add(new PinData(0.5605, 0.4258, "F3.12", "F", 3, "Ruang Kelas", false, "13:15"));
        pinList.add(new PinData(0.2667, 0.6267, "F3.13", "F", 3, "Ruang Kelas", true, "15:00"));
        pinList.add(new PinData(0.2600, 0.3596, "F3.14", "F", 3, "Ruang Kelas", false, "08:30"));
        pinList.add(new PinData(0.1765, 0.6243, "F3.15", "F", 3, "Ruang Kelas", false, "10:45"));
        pinList.add(new PinData(0.1682, 0.3619, "F3.16", "F", 3, "Ruang Kelas", true, "12:00"));
        pinList.add(new PinData(0.0547, 0.6338, "F3.17", "F", 3, "Ruang Kelas", false, "13:45"));
        pinList.add(new PinData(0.0530, 0.3619, "F3.18", "F", 3, "Ruang Kelas", true, "15:15"));

        // Gedung F - Lantai 4
        pinList.add(new PinData(0.6790, 0.7922, "F4.1", "F", 4, "Ruang Kelas", false, "08:00"));
        pinList.add(new PinData(0.5806, 0.5629, "F4.2", "F", 4, "Ruang Kelas", true, "09:00"));
        pinList.add(new PinData(0.6841, 0.5652, "F4.3", "F", 4, "Ruang Kelas", false, "10:00"));
        pinList.add(new PinData(0.7742, 0.5723, "F4.4", "F", 4, "Ruang Kelas", true, "11:00"));
        pinList.add(new PinData(0.7742, 0.4258, "F4.5", "F", 4, "Ruang Kelas", false, "12:00"));
        pinList.add(new PinData(0.6707, 0.4328, "F4.6", "F", 4, "Ruang Kelas", true, "13:00"));
        pinList.add(new PinData(0.5772, 0.4399, "F4.7", "F", 4, "Ruang Kelas", false, "14:00"));
        pinList.add(new PinData(0.6607, 0.2130, "F4.8", "F", 4, "Ruang Kelas", true, "15:00"));
        pinList.add(new PinData(0.2700, 0.3643, "F4.9", "F", 4, "Ruang Kelas", false, "16:00"));
        pinList.add(new PinData(0.2600, 0.6385, "F4.10", "F", 4, "Ruang Kelas", true, "17:00"));
        pinList.add(new PinData(0.1649, 0.3714, "F4.11", "F", 4, "Ruang Kelas", false, "08:30"));
        pinList.add(new PinData(0.1598, 0.6385, "F4.12", "F", 4, "Ruang Kelas", true, "09:30"));
        pinList.add(new PinData(0.0580, 0.3714, "F4.13", "F", 4, "Ruang Kelas", false, "10:30"));
        pinList.add(new PinData(0.0497, 0.6314, "F4.14", "F", 4, "Ruang Kelas", true, "11:30"));

        // Gedung F - Lantai 5
        pinList.add(new PinData(0.6123, 0.8493, "F5.1", "F", 5));
        pinList.add(new PinData(0.7492, 0.8426, "F5.2", "F", 5));
        pinList.add(new PinData(0.5772, 0.5708, "F5.3", "F", 5));
        pinList.add(new PinData(0.6390, 0.5674, "F5.4", "F", 5));
        pinList.add(new PinData(0.7091, 0.5741, "F5.5", "F", 5));
        pinList.add(new PinData(0.7675, 0.5674, "F5.6", "F", 5));
        pinList.add(new PinData(0.9011, 0.4970, "F5.7 - F5.8", "F", 5));
        pinList.add(new PinData(0.7625, 0.4366, "F5.10", "F", 5));
        pinList.add(new PinData(0.7008, 0.4298, "F5.11", "F", 5));
        pinList.add(new PinData(0.6306, 0.4366, "F5.12", "F", 5));
        pinList.add(new PinData(0.5755, 0.4433, "F5.13", "F", 5));
        pinList.add(new PinData(0.7308, 0.1413, "F5.14", "F", 5));
        pinList.add(new PinData(0.2567, 0.4768, "F5.15", "F", 5));

        // Gedung G - Lantai 1
        pinList.add(new PinData(0.8427, 0.1468, "G1.1", "G", 1, "Lab", false, "12.30-15.20"));
        pinList.add(new PinData(0.8360, 0.8867, "G1.2", "G", 1, "Lab", true, "12.30-15.20"));
        pinList.add(new PinData(0.6273, 0.1468, "G1.3", "G", 1, "Lab", false, "10.20-14.15"));
        pinList.add(new PinData(0.6206, 0.8773, "G1.4", "G", 1, "Lab", true, "10.20-14.15"));
        pinList.add(new PinData(0.4203, 0.1421, "G1.5", "G", 1, "Lab", false, "12.30-15.20"));
        pinList.add(new PinData(0.4069, 0.8631, "G1.6", "G", 1, "Lab", true, "12.30-15.20"));

        // Gedung G - Lantai 2
        pinList.add(new PinData(0.1932, 0.4967, "Auditorium Algoritma", "G", 2, "Auditorium", false, "09.00-12.00"));
        return pinList;
    }

    private Set<String> loadBookmarks() {
        SharedPreferences prefs = getSharedPreferences("BOOKMARKS", MODE_PRIVATE);
        return prefs.getStringSet("bookmarked_rooms", new HashSet<>());
    }

    private void saveBookmarks() {
        SharedPreferences prefs = getSharedPreferences("BOOKMARKS", MODE_PRIVATE);
        prefs.edit().putStringSet("rooms", bookmarkedRooms).apply();
    }
}