package com.example.evensia.home;

public class ModelEvent {
    private String name, gambar, judul, isi;

    public ModelEvent() {

    }

    public ModelEvent( String name,String gambar,String judul, String isi) {
        this.name = name;
        this.judul = judul;
        this.gambar = gambar;
        this.isi = isi;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGambar() { return gambar; }

    public void setGambar(String gambar) { this.gambar = gambar; }

    public String getIsi() { return isi; }

    public void setIsi(String isi) { this.isi = isi; }
}
