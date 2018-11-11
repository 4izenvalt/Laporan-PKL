package Java;

import java.util.ArrayList;

public class TrBarang {

    private int kodebarang;
    private int kodekonversi;
    private int jmlkonversi;
    private int kelompok;
    private int top;
    private int status;
    private int qty;
    private double jumlah;
    private String namabarang;
    private double jual[];
    private double hargajual;
    private double hargabeli;
    private double hargarata;
    private double komisi[];
    private double subtotal;

    public TrBarang() {
        jual = new double[3];
        komisi = new double[2];
        qty = 0;
        hargajual = 0;
    }

//    Calculation
    public void subtot() {
        subtotal = qty * hargajual;
//        subtotal = qty * hargarata;
//        subtotal = qty * jual[1];
    }

//    Setter Getter    
    public double getSubtotal() {
        return subtotal;
    }

//    public double getHargajual() {
//        return hargajual;
//    }
    public int getKodekonversi() {
        return kodekonversi;
    }

    public void setKodekonversi(int kodekonversi) {
        this.kodekonversi = kodekonversi;
    }

    public int getJmlkonversi() {
        return jmlkonversi;
    }

    public void setJmlkonversi(int jmlkonversi) {
        this.jmlkonversi = jmlkonversi;
        hargajual = jual[1] * this.jmlkonversi;
        subtot();
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public void setHargajual(double hargajual) {
        this.hargajual = hargajual;
    }

//    public int getQty() {
//        return qty;
//    }
    public void setQty(int qty) {
        this.qty = qty;
        subtot();
    }

    public int getKodebarang() {
        return kodebarang;
    }

    public void setKodebarang(int kodebarang) {
        this.kodebarang = kodebarang;
    }

    public int getKelompok() {
        return kelompok;
    }

    public void setKelompok(int kelompok) {
        this.kelompok = kelompok;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public double[] getJual() {
        return jual;
    }

    public void setJual(double harga, int i) {
        this.jual[i] = harga;
    }

    public double getHargabeli() {
        return hargabeli;
    }

    public void setHargabeli(double hargabeli) {
        this.hargabeli = hargabeli;
    }

    public double getHargarata() {
        return hargarata;
    }

    public void setHargarata(double hargarata) {
        this.hargarata = hargarata;
    }

    public double[] getKomisi() {
        return komisi;
    }

    public void setKomisi(double harga, int i) {
        this.komisi[i] = harga;
    }

}
