package com.example.projekgabunganpam;

public class PinData {
    public double xRatio;
    public double yRatio;
    public String roomName;
    public String gedung; // F atau G
    public int lantai;    // Lantai berapa
    public String roomType;         // Tambahan: tipe ruangan
    public boolean isOccupied;      // Tambahan: apakah sedang digunakan
    public String nextActivityTime; // Tambahan: waktu aktivitas berikutnya

    public PinData(double xRatio, double yRatio, String roomName, String gedung, int lantai) {
        this(xRatio, yRatio, roomName, gedung, lantai, "-", false, "-");
    }
    public PinData(double xRatio, double yRatio, String roomName, String gedung, int lantai,
                   String roomType, boolean isOccupied, String nextActivityTime) {
        this.xRatio = xRatio;
        this.yRatio = yRatio;
        this.roomName = roomName;
        this.gedung = gedung;
        this.lantai = lantai;
        this.roomType = roomType;
        this.isOccupied = isOccupied;
        this.nextActivityTime = nextActivityTime;
    }

    public double getXRatio() {
        return xRatio;
    }

    public void setXRatio(double xRatio) {
        this.xRatio = xRatio;
    }

    public double getYRatio() {
        return yRatio;
    }

    public void setYRatio(double yRatio) {
        this.yRatio = yRatio;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getGedung() {
        return gedung;
    }

    public void setGedung(String gedung) {
        this.gedung = gedung;
    }

    public int getLantai() {
        return lantai;
    }

    public void setLantai(int lantai) {
        this.lantai = lantai;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String getNextActivityTime() {
        return nextActivityTime;
    }

    public void setNextActivityTime(String nextActivityTime) {
        this.nextActivityTime = nextActivityTime;
    }
}