/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;

/**
 *
 * @author deadser
 */
public class ListDetailTargetPembelian {
    private String NoFakturPembelian, NamaBarang, TahunBeli, NamaKonversi;
    private int No, jumlah, BulanBeli;

    public ListDetailTargetPembelian() {
    }

    public void setNoFakturPembelian(String NoFakturPembelian) {
        this.NoFakturPembelian = NoFakturPembelian;
    }

    public void setNamaBarang(String NamaBarang) {
        this.NamaBarang = NamaBarang;
    }
    public void setBulanBeli(int BulanBeli) {
        this.BulanBeli = BulanBeli;
    }

    public String getTahunBeli() {
        return TahunBeli;
    }

    public void setNo(int No) {
        this.No = No;
    }

    public void setNamaKonversi(String NamaKonversi) {
        this.NamaKonversi = NamaKonversi;
    }



    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public void setTahunBeli(String TahunBeli) {
        this.TahunBeli = TahunBeli;
    }

    public int getBulanBeli() {
        return BulanBeli;
    }

    public String getNamaKonversi() {
        return NamaKonversi;
    }


    public String getNamaBarang() {
        return NamaBarang;
    }

    public String getNoFakturPembelian() {
        return NoFakturPembelian;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getNo() {
        return No;
    }      
}
