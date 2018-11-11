
package Java;


public class ListSaldoKeuangan {
     private String nama_keuangan, nomor_keuangan, atas_nama;
     private int kode_nama_keuangan, no;
     private double saldo_keuangan;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getNomor_keuangan() {
        return nomor_keuangan;
    }

    public void setNomor_keuangan(String nomor_keuangan) {
        this.nomor_keuangan = nomor_keuangan;
    }

    public String getNama_keuangan() {
        return nama_keuangan;
    }

    public void setNama_keuangan(String nama_keuangan) {
        this.nama_keuangan = nama_keuangan;
    }

    public String getAtas_nama() {
        return atas_nama;
    }

    public void setAtas_nama(String atas_nama) {
        this.atas_nama = atas_nama;
    }

    public int getKode_nama_keuangan() {
        return kode_nama_keuangan;
    }

    public void setKode_nama_keuangan(int kode_nama_keuangan) {
        this.kode_nama_keuangan = kode_nama_keuangan;
    }

    public double getSaldo_keuangan() {
        return saldo_keuangan;
    }

    public void setSaldo_keuangan(double saldo_keuangan) {
        this.saldo_keuangan = saldo_keuangan;
    }
}
