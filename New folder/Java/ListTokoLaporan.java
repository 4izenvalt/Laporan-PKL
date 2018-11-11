package Java;

import java.util.Date;

/**
 *
 * @author WINZ
 */
public class ListTokoLaporan {

    private String no_faktur, tanggal, namabarang, namakonversi;
    private int tanggalChanged;
    private int jumlah, hargajual, hargabarang;
    private Date tgl;
    private boolean isTr;

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

    public int getHargabarang() {
        return hargabarang;
    }

    public void setHargabarang(int hargabarang) {
        this.hargabarang = hargabarang;
    }

    public int getTanggalChanged() {
        return tanggalChanged;
    }

    public void setTanggalChanged(String tanggalChanged) {
        String tahun = tanggalChanged.substring(0,3);
        String bulan = tanggalChanged.substring(5,6);
        String tanggal = tanggalChanged.substring(8,9);
        String jam = tanggalChanged.substring(11,12);
        String menit = tanggalChanged.substring(14,15);
        String detik = tanggalChanged.substring(17,18);
        
        this.tanggalChanged = Integer.parseInt(tahun+""+bulan+""+tanggal+""+jam+""+menit+""+detik);
    }
}
