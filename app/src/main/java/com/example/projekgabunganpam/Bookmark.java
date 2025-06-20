package com.example.projekgabunganpam;

// [FILE BARU] Model untuk data bookmark
public class Bookmark {
    private String roomName;
    private String roomType;
    private String notes;
    private int imageResId; // Untuk gambar ruangan

    public Bookmark(String roomName, String roomType, String notes, int imageResId) {
        this.roomName = roomName;
        this.roomType = roomType;
        this.notes = notes;
        this.imageResId = imageResId;
    }

    // Getters
    public String getRoomName() {
        return roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getNotes() {
        return notes;
    }

    public int getImageResId() {
        return imageResId;
    }
}