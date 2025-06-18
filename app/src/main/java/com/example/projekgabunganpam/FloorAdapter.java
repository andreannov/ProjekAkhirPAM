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

    public FloorAdapter(List<Integer> floorList) {
        this.floorList = floorList;
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
        holder.btnFloor.setText("Lantai " + floorNumber);
        // Tambahkan onclick jika ingin aksi saat tombol diklik
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
