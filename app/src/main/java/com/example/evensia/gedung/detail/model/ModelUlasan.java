package com.example.evensia.detail.model;

public class ModelUlasan {
    private String profile, nama, ulasan;

    public ModelUlasan(){

    }
    public ModelUlasan(String profile,String nama, String ulasan) {
        this.nama = nama;
        this.profile = profile;
        this.ulasan = ulasan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProfile() { return profile; }

    public void setProfile(String profile) { this.profile = profile; }

    public String getUlasan() { return ulasan; }

    public void setUlasan(String ulasan) { this.ulasan = ulasan; }
}
