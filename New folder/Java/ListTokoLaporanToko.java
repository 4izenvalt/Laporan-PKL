package Java;

import java.sql.Date;

public class ListTokoLaporanToko {

    private String no_faktur, tanggal, namabarang, namakonversi, kode;
    private int jumlah, hargajual, totaljual;
    private Date tgl;
    private boolean isTr;
    private int tglsort;

    public Date getTgl() {
        return tgl;
    }

    public void setTgl(Date tgl) {
        this.tgl = tgl;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public boolean isIsTr() {
        return isTr;
    }

    public void setIsTr(boolean isTr) {
        this.isTr = isTr;
    }

    public String getNo_faktur() {
        return no_faktur;
    }

    public void setNo_faktur(String no_faktur) {
        this.no_faktur = no_faktur;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
        setTanggalChanged();
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public String getNamakonversi() {
        return namakonversi;
    }

    public void setNamakonversi(String namakonversi) {
        this.namakonversi = namakonversi;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHargajual() {
        return hargajual;
    }

    public void setHargajual(int hargajual) {
        this.hargajual = hargajual;
    }

    public int getTotaljual() {
        return totaljual;
    }

    public void setTotaljual(int hargabarang) {
        this.totaljual = hargabarang;
    }

    public void setTanggalChanged() {
        String tahun = getTanggal().substring(1, 4);
        String bulan = getTanggal().substring(5, 7);
        String tanggal = getTanggal().substring(8, 10);
        String jam = getTanggal().substring(11, 13);
        String menit = getTanggal().substring(14, 16);
//        String detik = getTanggal().substring(17);
        
        this.tglsort = Integer.parseInt(tahun + "" + bulan + "" + tanggal + "" + jam + "" + menit);
    }

    public int getTglsort() {
        return tglsort;
    }

}
