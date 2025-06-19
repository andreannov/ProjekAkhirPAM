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
import java.util.List;

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
        // Logika ini diasumsikan sudah memiliki drawable yang sesuai
        // Contoh: R.drawable.denah_lantaif1, R.drawable.denah_lantaif2, dst.
        String resourceName = "denah_lantai" + gedung.toLowerCase() + lantai;
        int resourceId = getResources().getIdentifier(resourceName, "drawable", getPackageName());
        return resourceId;
    }

    private void addPin(PinData pin) {
        ImageView pinView = new ImageView(this);
        pinView.setImageResource(R.drawable.ic_pin);
        pinView.setContentDescription(pin.getRoomName());

        // SET UKURAN PIN (misal 32dp x 32dp)
        int sizeInDp = 32;
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

        pinContainer.post(this::updatePinPositions);
    }

    private void updatePinPositions() {
        if (photoView.getDrawable() == null) return;

        RectF displayRect = photoView.getDisplayRect();
        if (displayRect == null) return;

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

        boolean isBookmarked = bookmarkedRooms.contains(pinData.getRoomName());
        btnBookmark.setImageResource(isBookmarked ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark);

        btnBookmark.setOnClickListener(v -> {
            if (bookmarkedRooms.contains(pinData.getRoomName())) {
                bookmarkedRooms.remove(pinData.getRoomName());
                btnBookmark.setImageResource(R.drawable.ic_bookmark);
                Toast.makeText(this, pinData.getRoomName() + " dihapus dari bookmark", Toast.LENGTH_SHORT).show();
            } else {
                bookmarkedRooms.add(pinData.getRoomName());
                btnBookmark.setImageResource(R.drawable.ic_bookmark_filled);
                Toast.makeText(this, pinData.getRoomName() + " ditambahkan ke bookmark", Toast.LENGTH_SHORT).show();
            }
            saveBookmarks();
        });

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
        // Data pin yang sudah ada... (tidak diubah)
        pinList.add(new PinData(0.1990, 0.8672, "Game Center", "F", 1, "Rekreasi", false, "15:00"));
        pinList.add(new PinData(0.5634, 0.8672, "F2.1", "F", 2, "Ruang Kelas", true, "09:30"));
        // ...dan seterusnya
        return pinList;
    }

    private Set<String> loadBookmarks() {
        SharedPreferences prefs = getSharedPreferences("BOOKMARKS", MODE_PRIVATE);
        return prefs.getStringSet("bookmarked_rooms", new HashSet<>());
    }

    private void saveBookmarks() {
        SharedPreferences prefs = getSharedPreferences("BOOKMARKS", MODE_PRIVATE);
        // == PERBAIKAN BUG ==
        // Kunci disamakan menjadi "bookmarked_rooms"
        prefs.edit().putStringSet("bookmarked_rooms", bookmarkedRooms).apply();
    }
}