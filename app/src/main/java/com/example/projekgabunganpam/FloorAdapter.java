package com.example.projekgabunganpam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FloorAdapter extends RecyclerView.Adapter<FloorAdapter.FloorViewHolder> {

    private List<Integer> floorList;
    private OnFloorClickListener listener; // <-- [PERUBAHAN] Tambahkan listener

    // [PERUBAHAN] Buat interface untuk click listener
    public interface OnFloorClickListener {
        void onFloorClick(int floorNumber);
    }

    // [PERUBAHAN] Modifikasi constructor untuk menerima listener
    public FloorAdapter(List<Integer> floorList, OnFloorClickListener listener) {
        this.floorList = floorList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FloorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_floor, parent, false);
        return new FloorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FloorViewHolder holder, int position) {
        int floorNumber = floorList.get(position);
        holder.btnFloor.setText("LANTAI " + floorNumber);

        // [PERUBAHAN] Tambahkan OnClickListener ke tombol
        holder.btnFloor.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFloorClick(floorNumber);
            }
        });
    }

    @Override
    public int getItemCount() {
        return floorList.size();
    }

    public static class FloorViewHolder extends RecyclerView.ViewHolder {
        Button btnFloor;

        public FloorViewHolder(@NonNull View itemView) {
            super(itemView);
            btnFloor = itemView.findViewById(R.id.btnFloor);
        }
    }
}