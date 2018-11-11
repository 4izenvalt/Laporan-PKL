
package Java;

public class ListBarang {
     private int kode_barang, Pusat, GUD63, TOKO, TENGAH, UTARA, No;
     private String nama_barang, satuan, Status, id_kelompok, id_top, id_konversi;

    public String getId_konversi() {
        return id_konversi;
    }

    public void setId_konversi(String id_konversi) {
        this.id_konversi = id_konversi;
    }
     private double harga_jual_1, harga_jual_2, harga_jual_3, harga_beli, harga_rata_rata_barang;

    public double getHarga_beli() {
        return harga_beli;
    }

    public void setHarga_beli(double harga_beli) {
        this.harga_beli = harga_beli;
    }

    public double getHarga_rata_rata_barang() {
        return harga_rata_rata_barang;
    }

    public void setHarga_rata_rata_barang(double harga_rata_rata_barang) {
        this.harga_rata_rata_barang = harga_rata_rata_barang;
    }
  
    public String getId_kelompok() {
        return id_kelompok;
    }

    public void setId_kelompok(String id_kelompok) {
        this.id_kelompok = id_kelompok;
    }

    public String getId_top() {
        return id_top;
    }

    public void setId_top(String id_top) {
        this.id_top = id_top;
    }
   

    public int getNo() {
        return No;
    }

    public void setNo(int No) {
        this.No = No;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
 
    public int getPusat() {
        return Pusat;
    }

    public void setPusat(int Pusat) {
        this.Pusat = Pusat;
    }

    public int getGUD63() {
        return GUD63;
    }

    public void setGUD63(int GUD63) {
        this.GUD63 = GUD63;
    }

    public int getTOKO() {
        return TOKO;
    }

    public void setTOKO(int TOKO) {
        this.TOKO = TOKO;
    }

    public int getTENGAH() {
        return TENGAH;
    }

    public void setTENGAH(int TENGAH) {
        this.TENGAH = TENGAH;
    }

    public int getUTARA() {
        return UTARA;
    }

    public void setUTARA(int UTARA) {
        this.UTARA = UTARA;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getKode_barang() {
        return kode_barang;
    }

    public void setKode_barang(int kode_barang) {
        this.kode_barang = kode_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public double getHarga_jual_1() {
        return harga_jual_1;
    }

    public void setHarga_jual_1(double harga_jual_1) {
        this.harga_jual_1 = harga_jual_1;
    }

    public double getHarga_jual_2() {
        return harga_jual_2;
    }

    public void setHarga_jual_2(double harga_jual_2) {
        this.harga_jual_2 = harga_jual_2;
    }

    public double getHarga_jual_3() {
        return harga_jual_3;
    }

    public void setHarga_jual_3(double harga_jual_3) {
        this.harga_jual_3 = harga_jual_3;
    }

  
}