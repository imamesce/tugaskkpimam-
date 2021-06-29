package id.imam.cobakkp.model;

public class ModelBerhasil {

    String id_order;
    String alamat_email;
    String kode_transaksi;

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getAlamat_email() {
        return alamat_email;
    }

    public void setAlamat_email(String alamat_email) {
        this.alamat_email = alamat_email;
    }

    public String getKode_transaksi() {
        return kode_transaksi;
    }

    public void setKode_transaksi(String kode_transaksi) {
        this.kode_transaksi = kode_transaksi;
    }

    public ModelBerhasil(String id_order, String alamat_email, String kode_transaksi) {
        this.id_order = id_order;
        this.alamat_email = alamat_email;
        this.kode_transaksi = kode_transaksi;
    }

    public ModelBerhasil() {
    }
}
