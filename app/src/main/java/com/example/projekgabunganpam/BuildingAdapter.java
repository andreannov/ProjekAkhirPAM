package com.example.projekgabunganpam;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder> {

    private List<Building> buildingList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Building building);
    }

    public BuildingAdapter(List<Building> buildingList, OnItemClickListener listener) {
        this.buildingList = buildingList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BuildingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_building_item, parent, false);
        return new BuildingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingViewHolder holder, int position) {
        Building building = buildingList.get(position);
        holder.bind(building, listener);
    }

    @Override
    public int getItemCount() {
        return buildingList.size();
    }

    public static class BuildingViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBuildingImage;
        TextView tvBuildingName;
        LinearLayout llFacilities;
        Button btnLearnMore;

        public BuildingViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBuildingImage = itemView.findViewById(R.id.ivBuildingImage);
            tvBuildingName = itemView.findViewById(R.id.tvBuildingName);
            llFacilities = itemView.findViewById(R.id.llFacilities);
            btnLearnMore = itemView.findViewById(R.id.btnLearnMore);
        }

        public void bind(final Building building, final OnItemClickListener listener) {
            tvBuildingName.setText(building.getName());

            // Pakai setImageResource karena imageResId adalah int drawable resource id
            ivBuildingImage.setImageResource(building.getImageResId());

            // Bersihkan dulu fasilitas supaya tidak menumpuk saat recycle
            llFacilities.removeAllViews();

            for (String facility : building.getFacilities()) {
                TextView tvFacility = new TextView(itemView.getContext());
                tvFacility.setText("- " + facility);
                tvFacility.setTextSize(14f);
                tvFacility.setTextColor(Color.DKGRAY);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 4, 0, 4);
                tvFacility.setLayoutParams(params);

                llFacilities.addView(tvFacility);
            }

            btnLearnMore.setOnClickListener(v -> {
                listener.onItemClick(building);
            });
        }

    }
}
