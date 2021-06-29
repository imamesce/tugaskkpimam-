package id.imam.cobakkp.model;

import com.google.android.gms.common.data.SingleRefDataBufferIterator;

public class ModelWisatabaru {
    String id_order;
    String kode_transaksi;
    String keterangan;
    String alamat_email;
    String gambar_wisata;
    String tempat_wisata;
    String dekripsi_wisata;
    String harga_wisata;
    String keterangan_wisata;
    String status;
    String nama_wisata;

    public String getGambar_wisata() {
        return gambar_wisata;
    }

    public void setGambar_wisata(String gambar_wisata) {
        this.gambar_wisata = gambar_wisata;
    }

    public String getTempat_wisata() {
        return tempat_wisata;
    }

    public void setTempat_wisata(String tempat_wisata) {
        this.tempat_wisata = tempat_wisata;
    }

    public String getDekripsi_wisata() {
        return dekripsi_wisata;
    }

    public void setDekripsi_wisata(String dekripsi_wisata) {
        this.dekripsi_wisata = dekripsi_wisata;
    }

    public String getHarga_wisata() {
        return harga_wisata;
    }

    public void setHarga_wisata(String harga_wisata) {
        this.harga_wisata = harga_wisata;
    }

    public String getKeterangan_wisata() {
        return keterangan_wisata;
    }

    public void setKeterangan_wisata(String keterangan_wisata) {
        this.keterangan_wisata = keterangan_wisata;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public ModelWisatabaru(String gambar_wisata, String tempat_wisata, String dekripsi_wisata, String harga_wisata, String keterangan_wisata, String status, String nama_wisata) {
        this.gambar_wisata = gambar_wisata;
        this.tempat_wisata = tempat_wisata;
        this.dekripsi_wisata = dekripsi_wisata;
        this.harga_wisata = harga_wisata;
        this.keterangan_wisata = keterangan_wisata;
        this.status = status;
        this.nama_wisata = nama_wisata;
    }

    public ModelWisatabaru() {
    }
}