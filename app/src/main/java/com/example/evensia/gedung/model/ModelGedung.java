package com.example.evensia.gedung.model;

public class ModelGedung {
    private String alamat, harga, isi, judul, nilai, gambar1, gambar2, gambar3, gambar4;

    public ModelGedung() {

    }

    public ModelGedung(String alamat,String harga, String isi, String judul, String nilai, String gambar1, String gambar2, String gambar3, String gambar4) {
        this.alamat = alamat;
        this.harga = harga;
        this.isi = isi;
        this.judul = judul;
        this.nilai = nilai;
        this.gambar1 = gambar1;
        this.gambar2 = gambar2;
        this.gambar3 = gambar3;
        this.gambar4 = gambar4;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getHarga() { return harga; }

    public void setHarga(String harga) { this.harga = harga; }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getGambar1() {
        return gambar1;
    }

    public void setGambar1(String gambar1) {
        this.gambar1 = gambar1;
    }

    public String getGambar2() {
        return gambar2;
    }

    public void setGambar2(String gambar2) {
        this.gambar2 = gambar2;
    }

    public String getGambar3() {
        return gambar3;
    }

    public void setGambar3(String gambar3) {
        this.gambar3 = gambar3;
    }

    public String getGambar4() {
        return gambar4;
    }

    public void setGambar4(String gambar4) {
        this.gambar4 = gambar4;
    }
}
