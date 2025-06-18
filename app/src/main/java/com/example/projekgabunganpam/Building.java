package com.example.projekgabunganpam;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private String name;
    private int floors;
    private int imageResId;
    private List<String> facilities;

    public Building(String name, int floors, int imageResId, List<String> facilities) {
        this.name = name;
        this.floors = floors;
        this.imageResId = imageResId;
        this.facilities = facilities;
    }

    // Constructor tambahan tanpa fasilitas
    public Building(String name, int floors, int imageResId) {
        this.name = name;
        this.floors = floors;
        this.imageResId = imageResId;
        this.facilities = new ArrayList<>(); // fasilitas kosong
    }

    public String getName() {
        return name;
    }

    public int getFloors() {
        return floors;
    }

    public int getImageResId() {
        return imageResId;
    }

    public List<String> getFacilities() {
        return facilities;
    }
}
