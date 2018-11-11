/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Currency_Column;
import java.sql.Connection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import com.sun.glass.events.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

/**
 * @author User
 */
public final class Pembelian_Transaksi extends javax.swing.JFrame {

    public String totalclone, tempKodeSupply, tmpNamaSupply, tmpLokasi, tmpKonv, tmpKodeBarang, tmpKel;
    public double Tempharga, tmpHJ1, tmpHJ2, tmpHB, tmpKonvPcs, tmppcs, jmlKonv, jumlahTambah;
    public double totalBiaya = 0, totalnow = 0;
    public int tmpIdTOP, tmpKodeLokasi, tmpKodeKonv, tmpKodeUang;
    public double jmlQty;
    public String txtnofaktur;
    private String nofaktur;
    private int stok, tempPrev = 0, jmlKolom = 0;
    private double tempJ, tempJQ;
    private int tempL, tempK;
    private ResultSet tempRs;
    private DefaultTableModel tabel;
    private SimpleDateFormat formatTanggal;
    private String kode_barang;
    private String no_nota;

    ArrayList<String> kode_nama_arr = new ArrayList();
    ArrayList<String> kode_nama_arrk = new ArrayList();
    ArrayList<String> kode_nama_arr1 = new ArrayList();
    private static int item = 0, item1 = 0, itemk = 0;
    private boolean tampil = true, tampil1 = true, tampilk = true;
    private boolean ini_baru = false, akhir = true;

    public Pembelian_Transaksi() {
        initComponents();
        this.setVisible(true);
//        AutoCompleteDecorator.decorate(comSupplier);
        //AutoCompleteDecorator.decorate(comTableBarang);
        AutoCompleteDecorator.decorate(comTableKonv);
        AutoCompleteDecorator.decorate(comTableLokasi);
        formatTanggal = new SimpleDateFormat("yyyy-MM-dd");
        TableColumnModel m = tbl_Pembelian.getColumnModel();
        m.getColumn(6).setCellRenderer(new Currency_Column());
        m.getColumn(7).setCellRenderer(new Currency_Column());
        m.getColumn(9).setCellRenderer(new Currency_Column());
        m.getColumn(11).setCellRenderer(new Currency_Column());
        m.getColumn(12).setCellRenderer(new Currency_Column());
        loadSupplier();
        loadTop();
        loadJenisKeuangan();
        loadComTableBarang();
        loadComKodeBarang();
//        loadComTableLokasi();
//        loadNumberTable();
        loadComTableSatuan();
        loadComTableLokasi();
        tanggal_jam_sekarang();
        autonumber();
        AturlebarKolom();
        //        HitungSemua();
        uangtotal();
        uangdp();
        comSupplier.requestFocus();

        //enable diskon
        txt_diskonRp.setEditable(false);
        txt_diskon.setEditable(false);
        if (Integer.parseInt(txt_tbl_total.getText()) > 0) {
            txt_diskon.setEditable(true);
        }
        //JCombobox Nama barang
        ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboNama(((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
                            //tbl_Pembelian.editCellAt(tbl_Pembelian.getSelectedRow(), 2);
                            comTableBarang.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                //((JTextComponent) comTableBarang.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");

            }

        });
        ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                tampil = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tampil = false;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tampil = true;
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

            }
        });
        //JCombobox Kode barang
        ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (itemk == 0) {
                    loadComboKode(((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText());
                } else {
                    itemk = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampilk) {
                            //tbl_Pembelian.editCellAt(tbl_Pembelian.getSelectedRow(), 2);
                            comKodeBarang.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                //((JTextComponent) comTableBarang.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");

            }

        });
        ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                tampilk = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tampilk = false;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tampilk = true;
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

            }
        });
        //JCombobox supplier
        ((JTextComponent) comSupplier.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item1 == 0) {
                    loadSupplier(((JTextComponent) comSupplier.getEditor().getEditorComponent()).getText());
                } else {
                    item1 = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil1) {
                            //tbl_Pembelian.editCellAt(tbl_Pembelian.getSelectedRow(), 2);
                            comSupplier.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comSupplier.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comSupplier.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                //((JTextComponent) comTableBarang.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");

            }

        });
        ((JTextComponent) comSupplier.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                tampil1 = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tampil1 = false;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tampil1 = true;
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

            }
        });
    }

    public Pembelian_Transaksi(String data, boolean cb) {
        initComponents();
        this.setVisible(true);
        AutoCompleteDecorator.decorate(comSupplier);
        AutoCompleteDecorator.decorate(comTableBarang);
        AutoCompleteDecorator.decorate(comTableKonv);
        AutoCompleteDecorator.decorate(comTableLokasi);
        loadSupplier();
        loadComTableBarang();
        loadComTableLokasi();
        loadComTableSatuan();
//        loadNumberTable();
//        tanggal_jam_sekarang();
        AturlebarKolom();
        this.nofaktur = data;
        setData1();
        setData2();
        autonumber();
        HitungSemua();
        uangtotal();
        uangdp();
        if (!cb) {
            txt_inv.setEditable(false);
            tgl_inv.setEnabled(false);
            txt_diskon.setEditable(false);
            txt_diskonRp.setEditable(false);
            comSupplier.setEnabled(false);
            txt_ket.setEditable(false);
            tbl_Pembelian.setEnabled(false);
            txt_dp.setEnabled(false);
//             HitungSemua();
        }
    }

    public Pembelian_Transaksi(String data, String no_faktur) {
        initComponents();
        this.setVisible(true);
        AutoCompleteDecorator.decorate(comSupplier);
        AutoCompleteDecorator.decorate(comTableBarang);
        AutoCompleteDecorator.decorate(comTableKonv);
        AutoCompleteDecorator.decorate(comTableLokasi);
        loadSupplier();
        loadComTableBarang();
        loadComTableLokasi();
        loadComTableSatuan();
//        loadNumberTable();
//        tanggal_jam_sekarang();
        AturlebarKolom();
        this.nofaktur = data;
        setData1();
        setData2();
        autonumber();
        HitungSemua();
        uangtotal();
        uangdp();
        if (no_faktur == "") {
            txt_inv.setEditable(false);
            tgl_inv.setEnabled(false);
            txt_diskon.setEditable(false);
            txt_diskonRp.setEditable(false);
            comSupplier.setEnabled(false);
            txt_ket.setEditable(false);
            tbl_Pembelian.setEnabled(false);
            txt_dp.setEnabled(false);
//             HitungSemua();
        }
    }

    private String currency_convert(int nilai) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(nilai);
    }

    private void tanggalsekarang() {
        int i = 0;
        Thread p = new Thread() {
            public void run() {
                for (;;) {
                    GregorianCalendar cal = new GregorianCalendar();
                    int hari = cal.get(Calendar.DAY_OF_MONTH);
                    int bulan = cal.get(Calendar.MONTH);
                    int tahun = cal.get(Calendar.YEAR);
                    txt_tgl.setText(tahun + "-" + (bulan + 1) + "-" + hari);
                    if (i > 0) {
                        txt_tgl.setText(tahun + "-" + (bulan + 1) + "-" + hari);

                    } else {

                    }
                }
            }
        };
        p.start();
    }

    private void getKodeSupply() {
        tmpNamaSupply = txt_nmSupply.getText();
        try {

            String sql = "SELECT * FROM `supplier` WHERE nama_supplier LIKE '" + tmpNamaSupply + "'";

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tempKodeSupply = res.getString("kode_supplier");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror getKodeSupply \n" + e);
        }
    }

    private int getKodeTOP() {
        String tmpTOP = com_top.getSelectedItem().toString();
        try {

            String sql = "SELECT * FROM `top` WHERE nama_top LIKE '" + tmpTOP + "'";

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tmpIdTOP = res.getInt("id_top");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror getKodeTOP \n" + e);
        }
        return tmpIdTOP;
    }

    private int getKodeLokasi(int baris) {
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        tmpLokasi = tbl_Pembelian.getValueAt(baris, 3).toString();
        try {

            String sql = "SELECT * FROM `lokasi` WHERE nama_lokasi LIKE '" + tmpLokasi + "'";

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tmpKodeLokasi = res.getInt("kode_lokasi");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror getKodeLokasi \n" + e);
        }
        return tmpKodeLokasi;
    }

    private int getKodeKonv(int baris) {
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        tmpKonv = tbl_Pembelian.getValueAt(baris, 4).toString();
        try {

            String sql = "SELECT * FROM `konversi` WHERE nama_konversi LIKE '" + tmpKonv + "'";

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tmpKodeKonv = res.getInt("kode_konversi");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror getKodeKonv \n" + e);
        }
        return tmpKodeKonv;
    }

    private void getKodeUang() {
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        try {

            String sql = "SELECT * FROM `transaksi_nama_keuangan` WHERE nama_keuangan LIKE '" + com_jenisKeuangan.getSelectedItem().toString() + "'";

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tmpKodeUang = res.getInt("kode_nama_keuangan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror getKodeUang \n" + e);
        }
    }

    private void getHargaJual(int baris) {
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        tmpKodeBarang = tbl_Pembelian.getValueAt(baris, 1).toString();
        try {

            String sql = "SELECT * FROM `barang` WHERE kode_barang LIKE '" + tmpKodeBarang + "'";

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tmpHJ1 = res.getInt("harga_jual_1_barang");
                tmpHJ2 = res.getInt("harga_jual_2_barang");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror getHargaJual \n" + e);
        }
    }

    private double getHargaBeli(int baris) {
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        tmpKodeBarang = tbl_Pembelian.getValueAt(baris, 1).toString();
        try {

            String sql = "SELECT * FROM `barang` WHERE kode_barang LIKE '" + tmpKodeBarang + "'";

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tmpHB = res.getDouble("harga_beli");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror getHargaBeli \n" + e);
        }
        return tmpHB;
    }

    private double getKonvPcs(int baris) {
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        String tmpNamaKonv = tbl_Pembelian.getValueAt(baris, 4).toString();
        tmpKodeBarang = tbl_Pembelian.getValueAt(baris, 1).toString();
        try {

            String sql = "SELECT * FROM barang_konversi bk, konversi k"
                    + " WHERE k.kode_konversi = bk.kode_konversi"
                    + " and bk.kode_barang = '" + tmpKodeBarang
                    + "' and k.nama_konversi LIKE '" + tmpNamaKonv + "'";

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tmpKonvPcs = res.getDouble("bk.jumlah_konversi");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror getKonvPcs \n" + e);
        }
        return tmpKonvPcs;
    }

    private void getQtyTambah(int baris) {
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        int tmpJumlah = Integer.parseInt(tbl_Pembelian.getValueAt(baris, 5).toString());
        tmpKodeBarang = tbl_Pembelian.getValueAt(baris, 1).toString();
        double tmpJmlKonv = getKonvPcs(baris);
        try {

            String sql = "SELECT * FROM barang"
                    + " WHERE kode_barang = " + tmpKodeBarang;

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tmpKel = res.getString("id_kelompok");
            }
            jmlKonv = tmpJmlKonv;
            jumlahTambah = tmpJumlah * jmlKonv;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror getQtyTambah \n" + e);
        }
    }

    private void getQty(int baris) {
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        tmpKodeBarang = tbl_Pembelian.getValueAt(baris, 1).toString();
        try {

            String sql = "SELECT * FROM barang_lokasi WHERE"
                    + " kode_barang = '" + tmpKodeBarang + "'"
                    + " AND kode_lokasi = " + getKodeLokasi(baris);

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                jmlQty = res.getDouble("jumlah");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror getQty \n" + e);
        }
    }

    private void getQtyTambahTemp(String noNota) {

        try {

            String sql = "SELECT * FROM detail_pembelian dp, barang_lokasi bl WHERE"
                    + " dp.kode_barang = bl.kode_barang AND"
                    + " dp.kode_lokasi = bl.kode_lokasi AND"
                    + " no_faktur_pembelian = '" + noNota + "'";

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                tempK = res.getInt("kode_barang");
                tempL = res.getInt("kode_lokasi");
                tempJ = res.getDouble("jumlah_barang");
                tempJQ = res.getDouble("jumlah");
                System.out.println("Kode barang = " + tempK + ", Kode Lokasi = " + tempL + ", Jumlah = " + tempJ);

                sql = "UPDATE barang_lokasi SET"
                        + " jumlah = " + (tempJQ - tempJ)
                        + " WHERE"
                        + " kode_barang = " + tempK
                        + " AND kode_lokasi = " + tempL;
                java.sql.Statement st = conn.createStatement();
                st.executeUpdate(sql);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror getQtyTambahTemp \n" + e);
        }
    }

    private void setData1() {
        txt_noNota.setText(nofaktur);

        try {
//             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String sql = "select * "
                    + "from pembelian p, supplier s, top t "
                    + "where t.id_top = p.id_top and p.kode_supplier = s.kode_supplier and "
                    + "p.no_faktur_pembelian = '" + nofaktur + "'";
//            System.out.println("aaaa: " + sql);
            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                txt_nmSupply.setText(res.getString("nama_supplier"));
                txt_almtSupply.setText(res.getString("alamat_supplier"));
                txt_rekSupply.setText(res.getString("rekening_supplier"));
                txt_ket.setText(res.getString("keterangan_supplier"));
                txt_tgl.setText(res.getString("tgl_pembelian"));
                com_top.setSelectedItem("id_top");
                txt_inv.setText(res.getString("no_faktur_supplier_pembelian"));

//                tempKodeSupply = res.getString("kode_supplier");
//                String tgl = res.getString("tgl_nota_supplier_pembelian");
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                sdf.setLenient(false);
//                tgl_inv.setDate(sdf.parse(tgl));
                txt_diskon.setText(res.getString("discon_persen"));
                txt_diskonRp.setText(res.getString("discon_rp"));
            }
//           
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror setData1 \n" + e);
//        } catch (ParseException ex) {
//            Logger.getLogger(Pembelian_Transaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setData2() {
        int row = 0;
        txt_noNota.setText(nofaktur);
        tabel = new DefaultTableModel(new String[]{
            "No.", "Kode", "Barang", "Lokasi", "Satuan", "Jumlah", "Harga", "Sub Total", "Diskon %", "Diskon Rp", "Diskon-2 %", "Diskon-2 Rp", "Harga Jadi"
        }, 0);
        try {
            String sql = "select * "
                    + "from detail_pembelian d, pembelian p, barang b, lokasi l, barang_konversi bs, konversi k "
                    + "where "
                    + "d.kode_barang = b.kode_barang and d.no_faktur_pembelian = p.no_faktur_pembelian and "
                    + "d.kode_lokasi = l.kode_lokasi and k.kode_konversi = bs.kode_konversi and d.kode_konversi = bs.kode_konversi "
                    + "order by d.id_detail_pembelian desc";
//            System.out.println(sql);
            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                double a = res.getInt("jumlah_barang");
                double b = res.getInt("harga_pembelian");
                int c = res.getInt("jumlah_konversi");
                double abc = a * b * c;
                double dis = res.getInt("discon_persen");
                double disrp = res.getInt("discon_rp");
                double dis1 = res.getInt("discon2_persen");
                double disrp1 = res.getInt("discon2_rp");
                double diskon = (dis + dis1) * abc / 100;
                double harga_jadi = abc - diskon - disrp - disrp1;

                tabel.addRow(new Object[]{
                    res.getInt("kode_pembelian"),
                    res.getString("kode_barang"),
                    res.getString("nama_barang"),
                    res.getString("nama_lokasi"),
                    res.getString("nama_konversi"),
                    res.getInt("jumlah_barang"),
                    res.getInt("harga_pembelian"),
                    abc,
                    res.getInt("discon_persen"),
                    res.getInt("discon_rp"),
                    res.getInt("discon2_persen"),
                    res.getInt("discon2_rp"),
                    harga_jadi
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            tbl_Pembelian.setModel(tabel);
        }
    }

    private void editable() throws ParseException {

        String string = "2018-07-30 17:00:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        Date date2 = format.parse(string);

        if (date.after(date2)) {
            comSupplier.setEnabled(false);
            com_faktur.setEnabled(false);
            com_top.setEnabled(false);
            txt_inv.setEditable(false);
            tgl_inv.setEnabled(false);
            txt_diskon.setEditable(false);
            txt_diskonRp.setEditable(false);
            com_jenisKeuangan.setEnabled(false);
            txt_dp.setEditable(false);
            txt_ket.setEditable(false);
            tbl_Pembelian.setEnabled(false);
        } else {
            comSupplier.setEnabled(true);
            com_faktur.setEnabled(true);
            com_top.setEnabled(true);
            txt_inv.setEditable(true);
            tgl_inv.setEnabled(true);
            txt_diskon.setEditable(true);
            txt_diskonRp.setEditable(true);
            com_jenisKeuangan.setEnabled(true);
            txt_dp.setEditable(true);
            txt_ket.setEditable(true);
            tbl_Pembelian.setEnabled(true);
        }
    }

    void loadSupplier(String param) {
        if (param.equals("*")) {
            param = "";
        }
        if (param.substring(0, 1).equals(" ")) {
            param = param.substring(1);
        }
        try {
//            String sql = "select * from supplier where nama_supplier like '%"+param+"%' or kode_supplier like '%"+param+"%'";
            String sql = "select concat(kode_supplier,\" - \",nama_supplier) as gabung from supplier "
                    + "where kode_supplier ='" + param + "' OR nama_supplier like '%" + param + "%'";
//            System.out.println("sql: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
//            System.out.println("ini sql com kode nama " + sql);
            kode_nama_arr.clear();
            kode_nama_arr.add("");
            while (res.next()) {
                String gabung = res.getString("gabung");
                kode_nama_arr.add(gabung);
                item++;
            }
            if (item == 0) {
                item = 1;
            }
//            System.out.println("kdenamarr: " + kode_nama_arr);
            comSupplier.setModel(new DefaultComboBoxModel(kode_nama_arr.toArray()));
            //((JTextComponent) comSupplier.getEditor().getEditorComponent()).setText(param);
            conn.close();
            res.close();
//            }
        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Eror -- 190" + e);
        }

    }

    void loadSupplier() {

        try {
            String sql = "select * from supplier";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(1) + " - " + res.getString(2);
                comSupplier.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadTop() {

        try {
            String sql = "select * from top";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(2);
                com_top.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadJenisKeuangan() {

        try {
            String sql = "select * from transaksi_nama_keuangan";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(2);
                com_jenisKeuangan.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadComTableBarang() {
//        TableModel tabelModel;
//        tabelModel = tbl_Pembelian.getModel();
//        int baris = tbl_Pembelian.getRowCount();
        try {
            String sql = "select * from barang order by nama_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
//            for (int i = 0; i < baris; i++) {
                String name = res.getString(1) + "- " + res.getString(4);
                comTableBarang.addItem(name);
            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadComKodeBarang() {
        //        TableModel tabelModel;
//        tabelModel = tbl_Pembelian.getModel();
//        int baris = tbl_Pembelian.getRowCount();
        try {
            String sql = "select * from barang order by nama_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
//            for (int i = 0; i < baris; i++) {
                String name = res.getString(1) + "- " + res.getString(4);
                comKodeBarang.addItem(name);
            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadComTableLokasi() {
        try {
            String sql = "select * from lokasi";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            System.out.println("lks: " + sql);
            comTableLokasi.removeAllItems();
            while (res.next()) {
                String name = res.getString("nama_lokasi");
                comTableLokasi.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadComTableSatuan() {
        try {
            String sql = "select * from konversi k, barang_konversi bk, barang b "
                    + "where b.kode_barang = bk.kode_barang "
                    + "and bk.kode_konversi = k.kode_konversi "
                    + "and b.kode_barang = '" + this.kode_barang + "' order by bk.kode_barang_konversi asc";
            System.out.println("sts: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            comTableKonv.removeAllItems();
            while (res.next()) {
                String name = res.getString("nama_konversi");
                if (res.getString("identitas_konversi").toString().equalsIgnoreCase("1")) {
                    System.out.println("ya: " + name);
                    comTableKonv.setSelectedItem(name);
                }
                comTableKonv.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

//    void loadNumberTable() {
//        int baris = tbl_Pembelian.getRowCount();
//        for (int a = 0; a < baris; a++) {
//            String nomor = String.valueOf(a + 1);
//            tbl_Pembelian.setValueAt(nomor + ".", a, 0);
//        }
//
//    }
    void BersihField() {
        txt_inv.setText("");
        tgl_inv.setCalendar(null);
        txt_diskon.setText("");
        txt_diskonRp.setText("");
    }

    void AturlebarKolom() {
        TableColumn column;
        tbl_Pembelian.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tbl_Pembelian.getColumnModel().getColumn(0);
        column.setPreferredWidth(30);
        column = tbl_Pembelian.getColumnModel().getColumn(1);
        column.setPreferredWidth(80);
        column = tbl_Pembelian.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        column = tbl_Pembelian.getColumnModel().getColumn(3);
        column.setPreferredWidth(70);
        column = tbl_Pembelian.getColumnModel().getColumn(4);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(5);
        column.setPreferredWidth(50);
        column = tbl_Pembelian.getColumnModel().getColumn(6);
        column.setPreferredWidth(120);
        column = tbl_Pembelian.getColumnModel().getColumn(7);
        column.setPreferredWidth(140);
        column = tbl_Pembelian.getColumnModel().getColumn(8);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(9);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(10);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(11);
        column.setPreferredWidth(100);

    }

    public void getTanggal() {
        Thread p = new Thread() {
            public void run() {
                for (;;) {
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                }
            }
        };
        p.start();
    }

    public void tanggal_jam_sekarang() {
        Thread p = new Thread() {
            public void run() {
                for (;;) {
//                    GregorianCalendar cal = new GregorianCalendar();
//                    int hari = cal.get(Calendar.DAY_OF_MONTH);
//                    int bulan = cal.get(Calendar.MONTH);
//                    int tahun = cal.get(Calendar.YEAR);
//                    int jam = cal.get(Calendar.HOUR_OF_DAY);
//                    int menit = cal.get(Calendar.MINUTE)//                    int detik = cal.get(Calendar.SECOND);
//                    txt_tgl.setText(tahun + "-" + (bulan + 1) + "-" + hari + " " + jam + ":" + menit + ":" + detik);
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    txt_tgl.setText(timeStamp);
                }
            }
        };
        System.out.println(txt_tgl.getText());
        p.start();
    }

    public void autonumber() {
        try {
            String lastNo = "";
            String sql = "select max(no_faktur_pembelian) from pembelian ORDER BY no_faktur_pembelian DESC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                if (res.first() == false) {
                    txt_noNota.setText("PB");

                } else {
                    res.last();
                    String auto_num = res.getString(1);

                    String[] noLama = auto_num.split("-");
//                    ++noLama;
                    String no = String.valueOf(noLama[1]);
//                    no = Integer.toString(noLama);

                    if (no.length() == 1) {
                        lastNo = "0000" + no;
                    } else if (no.length() == 2) {
                        lastNo = "000" + no;
                    } else if (no.length() == 3) {
                        lastNo = "00" + no;
                    } else if (no.length() == 4) {
                        lastNo = "0" + no;
                    } else {
                        lastNo = "00001";
                    }

                    int num = Integer.parseInt(lastNo);
                    String huruf = String.valueOf(auto_num.substring(0, 5));
                    num = Integer.valueOf(auto_num.substring(5)) + 0;
                    String angkapad = rightPadZeros(String.valueOf(++num), 5);
                    txt_noNota.setText(String.valueOf(huruf + "" + angkapad));
                    this.no_nota = txt_noNota.getText();

                }
            }
            res.close();

        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
//                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static String rightPadZeros(String str, int num) {
        return String.format("%05d", Integer.parseInt(str));
    }

    private void loadForm(String noNota) {
//        TableModel tabelModel;
//        tabelModel = tbl_Pembelian.getModel();
        try {
            String sql = "Select * from pembelian p, supplier s, top t, transaksi_nama_keuangan tk WHERE "
                    + "p.kode_supplier = s.kode_supplier AND "
                    + "p.id_top = t.id_top AND "
                    + "p.kode_nama_keuangan = tk.kode_nama_keuangan AND "
                    + "p.no_faktur_pembelian = '" + noNota + "'";
            System.out.println("sql loadform" + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                double tempTotal = (Double.parseDouble(res.getString("biaya_pembelian").toString()) * -1);
                comSupplier.setSelectedItem(res.getString("nama_supplier"));
                txt_nmSupply.setText(res.getString("nama_supplier"));
                txt_almtSupply.setText(res.getString("alamat_supplier"));
                txt_rekSupply.setText(res.getString("rekening_supplier"));
                com_top.setSelectedItem(res.getString("nama_top"));
                txt_inv.setText(res.getString("no_faktur_supplier_pembelian"));
                tgl_inv.setDate(res.getDate("tgl_nota_supplier_pembelian"));
//                tgl_inv.setDateFormatString(res.getString("tgl_nota_supplier_pembelian"));
                txt_diskon.setText(res.getString("discon_persen"));
                txt_diskonRp.setText(res.getString("discon_rp"));
                txt_ket.setText(res.getString("keterangan_pembelian"));
                com_jenisKeuangan.setSelectedItem(res.getString("nama_keuangan"));
                txt_dp.setText(res.getString("total_dp"));
                txt_tbl_total.setText(String.valueOf(tempTotal));
                System.out.println("LoadForm : " + sql);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
//                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void loadTable(String noNota) {
        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        try {
            hapustabel();
            String sql = "SELECT * FROM detail_pembelian dp, konversi k, lokasi l, pembelian p "
                    + "WHERE dp.kode_konversi = k.kode_konversi "
                    + "AND dp.kode_lokasi = l.kode_lokasi "
                    + "AND dp.no_faktur_pembelian = '" + noNota + "'"
                    + "AND dp.no_faktur_pembelian = p.no_faktur_pembelian";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                int tmpnomor = tbl_Pembelian.getRowCount() + 1;
                int kode = res.getInt("kode_barang");
                String nama = res.getString("nama_barang_edit");
                String lokasi = res.getString("nama_lokasi");
                String konv = res.getString("nama_konversi");
                int jml = res.getInt("jumlah_barang");
                int harga = res.getInt("harga_pembelian");
                int subtotal = res.getInt("harga_asli");
                int disc1persen = res.getInt("dp.discon_persen");
                int disc1 = res.getInt("dp.discon_rp");
                int disc2persen = res.getInt("dp.discon2_persen");
                int disc2 = res.getInt("dp.discon2_rp");
                int hargajadi = res.getInt("harga_asli_discon");
//                txt_diskonPersen.setText((String.valueOf(discpersen)));
//                txt_diskonRp.setText((String.valueOf(disc)));
                model.insertRow(tbl_Pembelian.getRowCount(), new Object[]{tmpnomor, kode, nama, lokasi, konv, jml, harga, subtotal, disc1persen, disc1, disc2persen, disc2, hargajadi});

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
//        setTotal();
//        uangtotal();

    }

    void totalhargajadi() {
        int jumlahBaris = tbl_Pembelian.getRowCount();
        int totalBiaya = 0;
        int jumlahBarang, hargaBarang;
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        for (int i = 0; i < jumlahBaris; i++) {
            jumlahBarang = Integer.parseInt(tabelModel.getValueAt(i, 0).toString());
            hargaBarang = Integer.parseInt(tabelModel.getValueAt(i, 1).toString());
            totalBiaya = totalBiaya + (jumlahBarang * hargaBarang);
        }
        txt_tbl_total.setText(String.valueOf(totalBiaya));
    }

    void setTmpTotal() {
        int jumlahBaris = tbl_Pembelian.getRowCount();
        totalBiaya = 0;
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        for (int i = 0; i < jumlahBaris; i++) {
            System.out.println("i: " + i);
            double subtot = 0;
            if (!tabelModel.getValueAt(i, 12).toString().isEmpty()) {
                subtot = Double.parseDouble(tabelModel.getValueAt(i, 12).toString());
            }
//            System.out.println("subtot: "+subtot);
            double hargaJadi = subtot;
            totalBiaya = totalBiaya + hargaJadi;
        }
    }

    void cekdiskondp() {
        int jumlahBaris = tbl_Pembelian.getRowCount();
        double total = 0;
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        for (int i = 0; i < jumlahBaris; i++) {
            System.out.println("i: " + i);
            double subtot = 0;
            if (!tabelModel.getValueAt(i, 12).toString().isEmpty()) {
                subtot = Double.parseDouble(tabelModel.getValueAt(i, 12).toString());
            }
//            System.out.println("subtot: "+subtot);
            double hargaJadi = subtot;
            total = total + hargaJadi;
        }

//        double total = totalBiaya;
        double diskon;
        if (txt_diskon.getText().toString().equals("")) {
            diskon = 0;
        } else {
            diskon = Double.parseDouble(txt_diskon.getText().toString());
        }
        double dp;
        if (txt_dp.getText().toString().equals("")) {
            dp = 0;
        } else {
            dp = Double.parseDouble(txt_dp.getText().toString());
        }
//            setTmpTotal();
//            double total = totalBiaya;
//            dp = total * dp / 100;
//            txt_dp.setText(String.valueOf(dp));
//            txt_tbl_total.setText(String.valueOf(totalBiaya));

        diskon = total * diskon / 100;
        txt_diskonRp.setText(String.valueOf(diskon));
        total -= diskon;
        total -= dp;
        txt_tbl_total.setText(String.valueOf(total));
        uangtotal();

    }

    void hapussemuatabel() {
        int Hapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin mau menghapus semua data di tabel", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if (Hapus == 0) {
            DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
            for (int i = tbl_Pembelian.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);

            }
            model.addRow(new Object[]{"", "", "", "", "", "", "", "", "", "", "", "", ""});
        }
    }

    void hapustabel() {
        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        for (int i = tbl_Pembelian.getRowCount() - 1; i > -1; i--) {
            model.removeRow(i);

        }
    }

    static String rptabel(String b) {
        b = b.replace(",", "");
        b = NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.parseDouble(b));
        return b;
    }

    static String rptabelkembali(String b) {
        b = b.replace(",", "");

        return b;
    }

    void dpnya() {
        float t = Float.parseFloat(totalclone);
        float dp = Float.parseFloat(txt_dp.getText());
        String h = String.valueOf(t - dp);
        System.out.println(t + " : " + dp + " : " + h);
        txt_tbl_total.setText(h);
    }

    private void HitungSemua() {
        double subtotalfix = 0, grandtotal = 0, discount = 0, jumlahItem = 0, jumlahQty = 0, dp = 0;
        if (!txt_diskon.getText().equals("")) {
            discount = Integer.parseInt(txt_diskon.getText().toString());
        }
        if (!txt_dp.getText().equals("")) {
            dp = Integer.parseInt(txt_dp.getText().toString());
        }
        if (tbl_Pembelian.getRowCount() >= 1) {
            for (int i = tbl_Pembelian.getRowCount() - 1; i > -1; i--) {
                double x = Integer.parseInt(tbl_Pembelian.getValueAt(i, 12).toString().replace(".0", ""));
                int y = Integer.parseInt(tbl_Pembelian.getValueAt(i, 5).toString().replace(".0", ""));
                double b = tbl_Pembelian.getRowCount();
//                System.out.println(x);
                subtotalfix += x;
                jumlahQty += y;
                txt_jumItem.setText("" + b);

            }
        }
//        txt_jumQty.setText(String.valueOf(jumlahQty));
        txt_tbl_total.setText(String.valueOf(subtotalfix));
        grandtotal = subtotalfix;

//
//        jumlahItem = tbl_Pembelian.getRowCount() - 1;
//        txt_jumItem.setText(String.valueOf(jumlahItem));
        int i = tbl_Pembelian.getRowCount();
        jumlahQty = 0;
        for (int j = 0; j < i; j++) {
            jumlahQty += Integer.parseInt(tbl_Pembelian.getValueAt(j, 5).toString());
        }
        txt_jumQty.setText(String.valueOf(jumlahQty));
        //diskonnnnnnnn
        if (discount > subtotalfix) {
            JOptionPane.showMessageDialog(null, "Discount tidak boleh lebih besar daripada Grand Total.");
            discount = 0;
            txt_diskon.setText(String.valueOf(discount));
        } else {
            grandtotal = grandtotal - discount;
        }
        //dppppppppppppp
        if (dp > subtotalfix) {
            JOptionPane.showMessageDialog(null, "DP tidak boleh lebih besar daripada Grand Total.");
            dp = 0;
            txt_dp.setText(String.valueOf(discount));
        } else {
            grandtotal = grandtotal - dp;
//            txt_tbl_total.setText(String.valueOf(grandtotal));
        }
        txt_tbl_total.setText(String.valueOf(grandtotal));
        uangdp();
        uangtotal();
        if (Double.parseDouble(txt_tbl_total.getText()) > 0) {
            txt_diskon.setEditable(true);
        }

    }

    private void Hitung() {
        int subtotalfix = 0, grandtotal = 0, discount = 0, jumlahItem = 0, jumlahQty = 0;
        if (!txt_dp.getText().equals("")) {
            discount = Integer.parseInt(txt_dp.getText().toString());
        }
        if (tbl_Pembelian.getRowCount() >= 1) {
            for (int i = tbl_Pembelian.getRowCount() - 1; i > -1; i--) {
                int x = Integer.parseInt(tbl_Pembelian.getValueAt(i, 12).toString());
                subtotalfix += x;
            }
        }
        txt_tbl_total.setText(String.valueOf(subtotalfix));
//
//        jumlahItem = tbl_Pembelian.getRowCount() - 1;
//        txt_jumItem.setText(String.valueOf(jumlahItem));

        int i = tbl_Pembelian.getRowCount();

        for (int j = 0; j < i; j++) {
            jumlahQty += Integer.parseInt(tbl_Pembelian.getValueAt(j, 5).toString());
        }
        txt_jumQty.setText(String.valueOf(jumlahQty));

        if (discount > subtotalfix) {
            JOptionPane.showMessageDialog(null, "Discount tidak boleh lebih besar daripada subtotal.");
            discount = 0;
            txt_diskon.setText(String.valueOf(discount));
        } else {
            grandtotal = subtotalfix - discount;
            txt_tbl_total.setText(String.valueOf(grandtotal));

        }
    }

    private void removeSelectedRows(JTable table) {
        int Hapus = 1;
        Hapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin mau menghapus baris ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if (Hapus == 0) {
            DefaultTableModel model = (DefaultTableModel) this.tbl_Pembelian.getModel();
            int[] rows = table.getSelectedRows();

            for (int i = 0; i < rows.length; i++) {
                model.removeRow(rows[i] - i);
            }
        }
    }

    public void uangtotal() {
        String b;
        b = txt_tbl_total.getText();
        if (b.isEmpty()) {
            b = "0";
        } else {
            b = b.replace(",", "");
            b = NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.parseDouble(b));
            b = b.replace(",", ".");
        }
        txt_tbl_total.setText(b);
    }

    public void uangdp() {
        String b;
        b = txt_dp.getText();
        if (b.isEmpty()) {
            b = "0";
        } else {
            b = b.replace(",", "");
            b = NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.parseDouble(b));
            b = b.replace(",", ".");
        }
        txt_dp.setText(b);
    }

//        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comTableBarang = new javax.swing.JComboBox();
        tblActionTabelBarang = new javax.swing.JButton();
        comTableKonv = new javax.swing.JComboBox<>();
        comTableLokasi = new javax.swing.JComboBox<>();
        comKodeBarang = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txt_rekSupply = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txt_noNota = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        com_faktur = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Pembelian = new javax.swing.JTable();
        txt_jumQty = new javax.swing.JTextField();
        txt_jumItem = new javax.swing.JTextField();
        chk_cetakSlg = new javax.swing.JCheckBox();
        lbl_nmKasir = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_tbl_total = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txt_nmSupply = new javax.swing.JTextField();
        txt_almtSupply = new javax.swing.JTextField();
        txt_ket = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        com_top = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        txt_tgl = new javax.swing.JTextField();
        LabelSave = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        LabelClear = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        LabelPrint = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        LabelExit = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        LabelPrev = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        LabelNext = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        txt_inv = new javax.swing.JTextField();
        txt_diskon = new javax.swing.JTextField();
        txt_diskonRp = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        chk_fakturSupply = new javax.swing.JCheckBox();
        txt_dp = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        com_jenisKeuangan = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        comSupplier = new javax.swing.JComboBox();
        tgl_inv = new com.toedter.calendar.JDateChooser();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        comTableBarang.setEditable(true);
        comTableBarang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comTableBarangItemStateChanged(evt);
            }
        });
        comTableBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableBarangActionPerformed(evt);
            }
        });

        tblActionTabelBarang.setText("jButton1");

        comTableKonv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableKonvActionPerformed(evt);
            }
        });

        comTableLokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableLokasiActionPerformed(evt);
            }
        });

        comKodeBarang.setEditable(true);
        comKodeBarang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comKodeBarangItemStateChanged(evt);
            }
        });
        comKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comKodeBarangActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 255));
        jLabel22.setText("Faktur Beli");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 74, -1, -1));

        txt_rekSupply.setEditable(false);
        txt_rekSupply.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_rekSupplyMouseClicked(evt);
            }
        });
        txt_rekSupply.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rekSupplyKeyPressed(evt);
            }
        });
        jPanel1.add(txt_rekSupply, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 145, 174, -1));

        jLabel24.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 255));
        jLabel24.setText("Tanggal");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 119, -1, -1));

        txt_noNota.setEditable(false);
        txt_noNota.setEnabled(false);
        txt_noNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_noNotaActionPerformed(evt);
            }
        });
        jPanel1.add(txt_noNota, new org.netbeans.lib.awtextra.AbsoluteConstraints(499, 92, 174, -1));

        jLabel21.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel21.setText("Keterangan");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 169, -1, -1));

        com_faktur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FAKTUR BELI", "BY ORDER BELI" }));
        com_faktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_fakturActionPerformed(evt);
            }
        });
        jPanel1.add(com_faktur, new org.netbeans.lib.awtextra.AbsoluteConstraints(499, 72, 174, -1));

        jLabel19.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel19.setText("Supplier");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 72, -1, -1));

        jLabel23.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 255));
        jLabel23.setText("Inv #");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(778, 66, -1, -1));

        jLabel27.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel27.setText("Diskon Rp");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(778, 145, -1, -1));

        jLabel26.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel26.setText("Diskon %");
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(778, 120, -1, -1));

        tbl_Pembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No.", "Kode", "Barang", "Lokasi", "Satuan", "Jumlah", "Harga", "Sub Total", "Diskon %", "Diskon Rp", "Diskon-2 %", "Diskon-2 Rp", "Harga Jadi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, false, true, false, false, true, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Pembelian.setToolTipText("");
        tbl_Pembelian.setRequestFocusEnabled(false);
        tbl_Pembelian.setRowSelectionAllowed(false);
        tbl_Pembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_PembelianMouseClicked(evt);
            }
        });
        tbl_Pembelian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_PembelianKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_PembelianKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Pembelian);
        if (tbl_Pembelian.getColumnModel().getColumnCount() > 0) {
            tbl_Pembelian.getColumnModel().getColumn(1).setCellEditor(new ComboBoxCellEditor(comTableBarang));
            tbl_Pembelian.getColumnModel().getColumn(2).setCellEditor(new ComboBoxCellEditor(comTableBarang));
            tbl_Pembelian.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comTableLokasi));
            tbl_Pembelian.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comTableKonv));
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 214, 1120, 300));

        txt_jumQty.setEditable(false);
        txt_jumQty.setBackground(new java.awt.Color(0, 0, 0));
        txt_jumQty.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_jumQty.setForeground(new java.awt.Color(255, 204, 0));
        txt_jumQty.setText("Jumlah Qty");
        txt_jumQty.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_jumQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_jumQtyMouseClicked(evt);
            }
        });
        jPanel1.add(txt_jumQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(689, 585, 150, 27));

        txt_jumItem.setEditable(false);
        txt_jumItem.setBackground(new java.awt.Color(0, 0, 0));
        txt_jumItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_jumItem.setForeground(new java.awt.Color(255, 204, 0));
        txt_jumItem.setText("Jumlah Item");
        txt_jumItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_jumItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_jumItemMouseClicked(evt);
            }
        });
        txt_jumItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jumItemKeyReleased(evt);
            }
        });
        jPanel1.add(txt_jumItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(533, 585, 150, 27));

        chk_cetakSlg.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chk_cetakSlg.setForeground(new java.awt.Color(153, 0, 0));
        chk_cetakSlg.setText("Cetak LSG");
        jPanel1.add(chk_cetakSlg, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 587, -1, -1));

        lbl_nmKasir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbl_nmKasir.setText("Nama Kasir");
        jPanel1.add(lbl_nmKasir, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 590, -1, -1));

        jLabel29.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel29.setText("Kasir");
        jPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 590, -1, -1));

        txt_tbl_total.setBackground(new java.awt.Color(0, 0, 0));
        txt_tbl_total.setForeground(new java.awt.Color(255, 255, 255));
        txt_tbl_total.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_tbl_total.setEnabled(false);
        txt_tbl_total.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_tbl_totalMouseClicked(evt);
            }
        });
        jPanel1.add(txt_tbl_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 545, 160, 25));

        jLabel30.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel30.setText("Total");
        jPanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(923, 549, -1, -1));

        txt_nmSupply.setEditable(false);
        txt_nmSupply.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_nmSupplyMouseClicked(evt);
            }
        });
        txt_nmSupply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nmSupplyActionPerformed(evt);
            }
        });
        txt_nmSupply.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nmSupplyKeyPressed(evt);
            }
        });
        jPanel1.add(txt_nmSupply, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 92, 174, -1));

        txt_almtSupply.setEditable(false);
        txt_almtSupply.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_almtSupplyMouseClicked(evt);
            }
        });
        txt_almtSupply.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_almtSupplyKeyPressed(evt);
            }
        });
        jPanel1.add(txt_almtSupply, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 119, 174, -1));

        txt_ket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_ketMouseClicked(evt);
            }
        });
        txt_ket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ketKeyPressed(evt);
            }
        });
        jPanel1.add(txt_ket, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 169, 1033, 35));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(204, 0, 0));
        jLabel31.setText("Nama");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 94, -1, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(204, 0, 0));
        jLabel33.setText("Alamat");
        jPanel1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 121, -1, -1));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(204, 0, 0));
        jLabel34.setText("Rekening");
        jPanel1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 147, -1, -1));

        jLabel28.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 255));
        jLabel28.setText("Tgl Inv");
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(778, 94, -1, -1));

        jLabel25.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 255));
        jLabel25.setText("TOP");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 148, -1, -1));

        com_top.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_topActionPerformed(evt);
            }
        });
        jPanel1.add(com_top, new org.netbeans.lib.awtextra.AbsoluteConstraints(499, 146, 174, -1));

        jLabel35.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 255));
        jLabel35.setText("No.Nota");
        jPanel1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 94, -1, -1));

        txt_tgl.setEditable(false);
        txt_tgl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tglActionPerformed(evt);
            }
        });
        jPanel1.add(txt_tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(499, 117, 174, -1));

        LabelSave.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LabelSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        LabelSave.setText("F12-Save");
        LabelSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelSaveMouseClicked(evt);
            }
        });
        jPanel1.add(LabelSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, 23));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 11, 5, 23));

        LabelClear.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LabelClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Clear-icon.png"))); // NOI18N
        LabelClear.setText("F9-Clear");
        LabelClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelClearMouseClicked(evt);
            }
        });
        LabelClear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LabelClearKeyPressed(evt);
            }
        });
        jPanel1.add(LabelClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(101, 11, -1, 23));

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 11, 5, 23));

        LabelPrint.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LabelPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_yast_printer_30297.png"))); // NOI18N
        LabelPrint.setText("Print");
        jPanel1.add(LabelPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 11, -1, 23));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(237, 11, -1, 23));

        LabelExit.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LabelExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        LabelExit.setText("Esc-Exit");
        LabelExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelExitMouseClicked(evt);
            }
        });
        jPanel1.add(LabelExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(249, 11, -1, 21));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 48, 1140, 10));

        LabelPrev.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LabelPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/back-icon.png"))); // NOI18N
        LabelPrev.setText("Prev");
        LabelPrev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelPrevMouseClicked(evt);
            }
        });
        jPanel1.add(LabelPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 11, -1, 21));

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(323, 11, -1, 23));

        LabelNext.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LabelNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/forward-icon.png"))); // NOI18N
        LabelNext.setText("Next");
        LabelNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelNextMouseClicked(evt);
            }
        });
        LabelNext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LabelNextKeyPressed(evt);
            }
        });
        jPanel1.add(LabelNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(401, 13, -1, -1));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 576, 1130, 3));

        txt_inv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_invMouseClicked(evt);
            }
        });
        txt_inv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_invActionPerformed(evt);
            }
        });
        txt_inv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_invKeyPressed(evt);
            }
        });
        jPanel1.add(txt_inv, new org.netbeans.lib.awtextra.AbsoluteConstraints(854, 64, 174, -1));

        txt_diskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_diskonActionPerformed(evt);
            }
        });
        txt_diskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_diskonKeyPressed(evt);
            }
        });
        jPanel1.add(txt_diskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(854, 117, 174, -1));

        txt_diskonRp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_diskonRpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_diskonRpKeyTyped(evt);
            }
        });
        jPanel1.add(txt_diskonRp, new org.netbeans.lib.awtextra.AbsoluteConstraints(855, 143, 173, -1));

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 11, -1, 23));

        chk_fakturSupply.setText("Faktur Supplier Telah Diterima");
        jPanel1.add(chk_fakturSupply, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 551, -1, -1));

        txt_dp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dpKeyPressed(evt);
            }
        });
        jPanel1.add(txt_dp, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 550, 173, -1));

        jLabel36.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel36.setText("DP");
        jPanel1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(705, 547, -1, -1));

        com_jenisKeuangan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        com_jenisKeuangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_jenisKeuanganActionPerformed(evt);
            }
        });
        jPanel1.add(com_jenisKeuangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(583, 545, 112, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Jenis Keuangan");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(472, 548, -1, -1));

        comSupplier.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comSupplier.setEditable(true);
        comSupplier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        comSupplier.setRequestFocusEnabled(true);
        comSupplier.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comSupplierItemStateChanged(evt);
            }
        });
        comSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comSupplierActionPerformed(evt);
            }
        });
        jPanel1.add(comSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 70, 174, -1));

        tgl_inv.setDateFormatString(" yyyy- MM-dd");
        tgl_inv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_invKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tgl_invKeyTyped(evt);
            }
        });
        jPanel1.add(tgl_inv, new org.netbeans.lib.awtextra.AbsoluteConstraints(854, 91, 174, -1));

        jMenuBar1.setPreferredSize(new java.awt.Dimension(0, 0));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        jMenuItem1.setText("Save");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        jMenuItem2.setText("Clear");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem3.setText("Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(1156, 682));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_rekSupplyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_rekSupplyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rekSupplyMouseClicked

    private void txt_rekSupplyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rekSupplyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rekSupplyKeyPressed

    private void txt_jumQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_jumQtyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumQtyMouseClicked

    private void txt_jumItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_jumItemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumItemMouseClicked

    private void txt_tbl_totalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_tbl_totalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tbl_totalMouseClicked

    private void txt_nmSupplyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nmSupplyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nmSupplyMouseClicked

    private void txt_nmSupplyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nmSupplyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nmSupplyKeyPressed

    private void txt_almtSupplyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_almtSupplyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_almtSupplyMouseClicked

    private void txt_almtSupplyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_almtSupplyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_almtSupplyKeyPressed

    private void txt_ketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_ketMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ketMouseClicked

    private void txt_ketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ketKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tbl_Pembelian.requestFocus();
        }
    }//GEN-LAST:event_txt_ketKeyPressed

    private void com_topActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_topActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_com_topActionPerformed

    private void txt_invMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_invMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_invMouseClicked

    private void txt_diskonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            txt_diskonRp.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_txt_diskonKeyPressed

    private void txt_diskonRpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonRpKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            txt_ket.requestFocus();
        }
    }//GEN-LAST:event_txt_diskonRpKeyPressed

    private void com_fakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_fakturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_com_fakturActionPerformed

    private void comSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comSupplierActionPerformed
        try {
            String sql = "select * from supplier where kode_supplier = '" + comSupplier.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String nama = res.getString(2);
                String alamat = res.getString(3);
                String rek = res.getString(9);
                comSupplier.setSelectedItem(res.getString(1));
                txt_nmSupply.setText(nama);
                txt_almtSupply.setText(alamat);
                txt_rekSupply.setText(rek);
            }
            txt_inv.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }//GEN-LAST:event_comSupplierActionPerformed
    void load_dari_nama_barang() {
        int kode_barang = 0;
        int baris = tbl_Pembelian.getRowCount();
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        int selectedRow = tbl_Pembelian.getSelectedRow();
        String nama_awal = String.valueOf(comTableBarang.getSelectedItem());
        String[] split = new String[2];
        System.out.println("nilai comTable barang adalah " + comTableBarang.getSelectedItem());
        if (comTableBarang.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }
        try {
            String sql = "select * from barang where kode_barang = '" + split[0] + "'";
            this.kode_barang = split[0];
            System.out.println(sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {

                String kode = res.getString(1);
                int harga = Math.round(res.getFloat(11));
                int jumlah = 1;
                int diskon = 0;
                loadComTableSatuan();
                String konv = comTableKonv.getSelectedItem().toString();
                int diskonp, diskonp2, totaldiskon;
                int jumlah1, harga1, subtotal, totaljadi;

                if (selectedRow != -1) {
                    tbl_Pembelian.setValueAt(kode, selectedRow, 1);
                    tbl_Pembelian.setValueAt(res.getString("nama_barang"), selectedRow, 2);
                    tbl_Pembelian.setValueAt(konv, selectedRow, 4);
                    tbl_Pembelian.setValueAt(jumlah, selectedRow, 5);
                    tbl_Pembelian.setValueAt(harga, selectedRow, 6);
                    tbl_Pembelian.setValueAt(diskon, selectedRow, 8);
                    tbl_Pembelian.setValueAt(diskon, selectedRow, 9);
                    tbl_Pembelian.setValueAt(diskon, selectedRow, 10);
                    tbl_Pembelian.setValueAt(diskon, selectedRow, 11);
                    tbl_Pembelian.setValueAt(comTableLokasi.getItemAt(0), selectedRow, 3);
//                    int i = selectedRow;
//                    konv = comTableKonv.getSelectedItem().toString();

                    jumlah1 = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 5).toString());
                    harga1 = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 6).toString());
                    diskonp = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 8).toString());
                    diskonp2 = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 10).toString());
                    tmppcs = getKonvPcs(tbl_Pembelian.getSelectedRow());
                    subtotal = (int) (jumlah1 * harga1 * tmppcs);
                    totaldiskon = ((diskonp + diskonp2) * subtotal / 100);
                    totaljadi = subtotal - totaldiskon;
                    tabelModel.setValueAt(subtotal, tbl_Pembelian.getSelectedRow(), 7);
                    tabelModel.setValueAt(totaljadi, tbl_Pembelian.getSelectedRow(), 12);
                    Tempharga = res.getInt(11);
                    System.out.println("temharga = " + Tempharga);
//                    loadComTableSatuan();

                }
                TableColumnModel m = tbl_Pembelian.getColumnModel();
                m.getColumn(6).setCellRenderer(new Currency_Column());
                m.getColumn(7).setCellRenderer(new Currency_Column());
                m.getColumn(9).setCellRenderer(new Currency_Column());
                m.getColumn(11).setCellRenderer(new Currency_Column());
                m.getColumn(12).setCellRenderer(new Currency_Column());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            //System.out.println("asdasdasdasdasdasd");
            //e.printStackTrace();
        }

        try {
            String sql = "select k.nama_konversi, k.kode_konversi from konversi k, barang_konversi bk where k.kode_konversi = bk.kode_konversi and bk.kode_barang = '" + kode_barang + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            String Konv = "";
//            System.out.println("test =" +sql);
//            System.out.println("k =" +kode_barang);
//            System.out.println("Select =" + comTableKonv.getSelectedItem());
            while (res.next()) {
                Konv = res.getString(1);
                comTableKonv.addItem(Konv);
                System.out.println("Konv =" + Konv);
//                System.out.println("echo : " + tbl_Pembelian.getValueAt(selectedRow, 2).toString());
                tbl_Pembelian.setValueAt(comTableKonv.getSelectedItem(), selectedRow, 4);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
            e.printStackTrace();
        }
//        txt_inv.requestFocus();
    }

    void load_dari_kode_barang() {
        int kode_barang = 0;
        int baris = tbl_Pembelian.getRowCount();
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        int selectedRow = tbl_Pembelian.getSelectedRow();
        String nama_awal = String.valueOf(comKodeBarang.getSelectedItem());
        String[] split = new String[2];
        System.out.println("nilai comTable barang adalah " + comKodeBarang.getSelectedItem());
        if (comKodeBarang.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }
        try {
            String sql = "select * from barang where kode_barang = '" + split[0] + "'";
            this.kode_barang = split[0];
            System.out.println(sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {

                String kode = res.getString(1);
                int harga = Math.round(res.getFloat(11));
                String jumlah = "1";
                String diskon = "0";
                loadComTableSatuan();
                String konv = comTableKonv.getSelectedItem().toString();
                int diskonp, diskonp2, totaldiskon;
                int jumlah1, harga1, subtotal, totaljadi;

                if (selectedRow != -1) {
                    tbl_Pembelian.setValueAt(kode, selectedRow, 1);
                    tbl_Pembelian.setValueAt(res.getString("nama_barang"), selectedRow, 2);
                    tbl_Pembelian.setValueAt(konv, selectedRow, 4);
                    tbl_Pembelian.setValueAt(jumlah, selectedRow, 5);
                    tbl_Pembelian.setValueAt(harga, selectedRow, 6);
                    tbl_Pembelian.setValueAt(diskon, selectedRow, 8);
                    tbl_Pembelian.setValueAt(diskon, selectedRow, 9);
                    tbl_Pembelian.setValueAt(diskon, selectedRow, 10);
                    tbl_Pembelian.setValueAt(diskon, selectedRow, 11);
                    tbl_Pembelian.setValueAt(comTableLokasi.getItemAt(0), selectedRow, 3);
//                    int i = selectedRow;
//                    konv = comTableKonv.getSelectedItem().toString();

                    jumlah1 = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 5).toString());
                    harga1 = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 6).toString());
                    diskonp = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 8).toString());
                    diskonp2 = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 10).toString());
                    tmppcs = getKonvPcs(tbl_Pembelian.getSelectedRow());
                    subtotal = (int) (jumlah1 * harga1 * tmppcs);
                    totaldiskon = ((diskonp + diskonp2) * subtotal / 100);
                    totaljadi = subtotal - totaldiskon;
                    tabelModel.setValueAt(subtotal, tbl_Pembelian.getSelectedRow(), 7);
                    tabelModel.setValueAt(totaljadi, tbl_Pembelian.getSelectedRow(), 12);
                    Tempharga = res.getInt(11);
                    System.out.println("temharga = " + Tempharga);
//                    loadComTableSatuan();

                }
                TableColumnModel m = tbl_Pembelian.getColumnModel();
                m.getColumn(6).setCellRenderer(new Currency_Column());
                m.getColumn(7).setCellRenderer(new Currency_Column());
                m.getColumn(9).setCellRenderer(new Currency_Column());
                m.getColumn(11).setCellRenderer(new Currency_Column());
                m.getColumn(12).setCellRenderer(new Currency_Column());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            //System.out.println("asdasdasdasdasdasd");
            //e.printStackTrace();
        }

        try {
            String sql = "select k.nama_konversi, k.kode_konversi from konversi k, barang_konversi bk where k.kode_konversi = bk.kode_konversi and bk.kode_barang = '" + kode_barang + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            String Konv = "";
//            System.out.println("test =" +sql);
//            System.out.println("k =" +kode_barang);
//            System.out.println("Select =" + comTableKonv.getSelectedItem());
            while (res.next()) {
                Konv = res.getString(1);
                comTableKonv.addItem(Konv);
                System.out.println("Konv =" + Konv);
//                System.out.println("echo : " + tbl_Pembelian.getValueAt(selectedRow, 2).toString());
                tbl_Pembelian.setValueAt(comTableKonv.getSelectedItem(), selectedRow, 4);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
            e.printStackTrace();
        }
    }

    void loadComboNama(String key) {
        Runnable doHighlight = new Runnable() {
            @Override
            public void run() {
                System.out.println("ini load combo nama");
                try {
                    String sql = "select concat(kode_barang,\"-\",nama_barang) as gabung from barang where kode_barang ='" + key + "' OR nama_barang like '%" + key + "%'";
                    System.out.println(sql);
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    System.out.println("ini sql com kode nama " + sql);
                    kode_nama_arr.clear();
                    kode_nama_arr.add("");
                    while (res.next()) {
                        String gabung = res.getString("gabung");
                        kode_nama_arr.add(gabung);
                        item++;
                    }
                    if (item == 0) {
                        item = 1;
                    }
                    comTableBarang.setModel(new DefaultComboBoxModel(kode_nama_arr.toArray()));
                    ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).setText(key);
                    conn.close();
                    res.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                    e.printStackTrace();
                }

            }
        };
        SwingUtilities.invokeLater(doHighlight);
    }

    void loadComboKode(String key) {
        Runnable doHighlight = new Runnable() {
            @Override
            public void run() {
                System.out.println("ini load combo kode");
                try {
                    String sql = "select concat(kode_barang,\"-\",nama_barang) as gabung from barang where kode_barang ='" + key + "' OR nama_barang like '%" + key + "%'";
                    System.out.println(sql);
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    System.out.println("ini sql com kode " + sql);
                    kode_nama_arrk.clear();
                    kode_nama_arrk.add("");
                    while (res.next()) {
                        String gabung = res.getString("gabung");
                        kode_nama_arrk.add(gabung);
                        itemk++;
                    }
                    if (itemk == 0) {
                        itemk = 1;
                    }
                    comKodeBarang.setModel(new DefaultComboBoxModel(kode_nama_arrk.toArray()));
                    ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).setText(key);
                    conn.close();
                    res.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                    e.printStackTrace();
                }

            }
        };
        SwingUtilities.invokeLater(doHighlight);
    }

    private void comTableBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableBarangActionPerformed
        load_dari_nama_barang();

    }//GEN-LAST:event_comTableBarangActionPerformed

    private void txt_diskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_diskonActionPerformed
        // TODO add your handling code here:
//        double diskon;
//        if (txt_diskon.getText().toString().equals("")) {
//            diskon = 0;
//        } else {
//            diskon = Double.parseDouble(txt_diskon.getText().toString());
//        }
//        setTmpTotal();
//        double total = totalBiaya;
//        diskon = total * diskon / 100;
//        txt_diskonRp.setText(String.valueOf(diskon));
//        totalBiaya -= diskon;
//        txt_tbl_total.setText(String.valueOf(totalBiaya));
//        uangtotal();
        cekdiskondp();
//        txt_diskonRp.setText("0");
    }//GEN-LAST:event_txt_diskonActionPerformed

    private void tbl_PembelianKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PembelianKeyReleased
        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        int selectedRow = tbl_Pembelian.getSelectedRow();
        int baris = tbl_Pembelian.getRowCount();
        double jumlah = 0, harga = 0, harga_jadi = 0, diskon = 0, diskon1 = 0, diskonp = 0, diskonp1 = 0;
        int qty = 0;
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        if (tbl_Pembelian.getSelectedRow() > 0) {
            if (tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 5) != null && !tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 5).equals("")) {
                jumlah = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 5).toString());
                harga = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 6).toString());
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter

                jumlah = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 5).toString());
                harga = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 6).toString());
                diskon = Double.parseDouble(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 8).toString());
                diskon1 = Double.parseDouble(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 10).toString());
                diskonp = Double.parseDouble(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 9).toString());
                diskonp1 = Double.parseDouble(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 11).toString());
                tmppcs = getKonvPcs(tbl_Pembelian.getSelectedRow());
                int subtotal = (int) (jumlah * harga * tmppcs);
                double diskonrp = subtotal * diskon / 100;
                double diskonrp1 = subtotal * diskon1 / 100;
                double hargajadii = subtotal - diskonrp - diskonrp1;
                tabelModel.setValueAt(subtotal, tbl_Pembelian.getSelectedRow(), 7);
                tabelModel.setValueAt(diskonrp, tbl_Pembelian.getSelectedRow(), 9);
                tabelModel.setValueAt(diskonrp1, tbl_Pembelian.getSelectedRow(), 11);
                tabelModel.setValueAt(hargajadii, tbl_Pembelian.getSelectedRow(), 12);

//        loadNumberTable();
            }
            HitungSemua();
        }

    }//GEN-LAST:event_tbl_PembelianKeyReleased

    private void txt_invActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_invActionPerformed


    }//GEN-LAST:event_txt_invActionPerformed

    private void tbl_PembelianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PembelianKeyPressed
        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        int selectedRow = tbl_Pembelian.getSelectedRow();
        int baris = tbl_Pembelian.getRowCount();
        int jumlah = 0, harga = 0, harga_jadi = 0;
        double diskon = 0, diskon1 = 0, diskonrp = 0, diskonrp1 = 0;
        int qty = 0;

        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter
            jumlah = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 5).toString());
            harga = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 6).toString());
            jumlah = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 5).toString());
            harga = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 6).toString());
            diskon = Double.parseDouble(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 8).toString());
            diskon1 = Double.parseDouble(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 10).toString());
            diskonrp = Double.parseDouble(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 9).toString());
            diskonrp1 = Double.parseDouble(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 11).toString());
            tmppcs = getKonvPcs(tbl_Pembelian.getSelectedRow());
            int subtotal = (int) (harga * jumlah * tmppcs);
            double diskonn = ((diskon + diskon1) * subtotal / 100);
            double hargajadii = subtotal - diskonn;
            tabelModel.setValueAt(subtotal, tbl_Pembelian.getSelectedRow(), 7);
            tabelModel.setValueAt(hargajadii, tbl_Pembelian.getSelectedRow(), 12);

            HitungSemua();
            if (tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 12).toString().equals("0")) {
                JOptionPane.showMessageDialog(null, "Data Terakhir Tidak Boleh kosong", "", 2);
            } else {
                if (Double.parseDouble(tabelModel.getValueAt(tbl_Pembelian.getRowCount() - 1, 12).toString()) != 0) {
                    model.addRow(new Object[]{"", "", "", "", "", "0", "0", "0", "0", "0", "0", "0", "0"});
                }

            }
        } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            if (tbl_Pembelian.getRowCount() - 1 == -1) {
                JOptionPane.showMessageDialog(null, "Data didalam tabel telah tiada.", "", 2);
            } else {
                removeSelectedRows(tbl_Pembelian);
                HitungSemua();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_INSERT) {
            if (tbl_Pembelian.getRowCount() - 1 == -1) {
                model.addRow(new Object[]{"", "", "", "", "0", "0", "0", "0", "0"});
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_F12) {
            int simpan_data = 1;
            simpan_data = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menyimpan data ini ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
            if (simpan_data == 0) {
                simpan_data();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_F9) {
            clear();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (tbl_Pembelian.getSelectedColumn() == 2 || tbl_Pembelian.getSelectedColumn() == 3 || tbl_Pembelian.getSelectedColumn() == 1)) {
            InputMap im = tbl_Pembelian.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
            KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
            im.put(down, im.get(f2));
            System.out.println("asd");
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (tbl_Pembelian.getSelectedColumn() != 2 || tbl_Pembelian.getSelectedColumn() != 1)) {
            InputMap im = tbl_Pembelian.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
            KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
            im.put(f2, null);
            im.put(down, null);
            System.out.println("fgh");
        } else if ((evt.getKeyCode() == KeyEvent.VK_1 || evt.getKeyCode() == KeyEvent.VK_NUMPAD2) && tbl_Pembelian.getSelectedColumn() == 4) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_Pembelian.getValueAt(tbl_Pembelian.getSelectedRow(), 1));
            try {
                String sql = "select nama_konversi from konversi k join barang_konversi bk on "
                        + "bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '1' and bk.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String sat = res.getString("nama_konversi");
                    String sat2 = sat;
                    tbl_Pembelian.setValueAt(sat2, tbl_Pembelian.getSelectedRow(), 4);
                    System.out.println(sat2);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if ((evt.getKeyCode() == KeyEvent.VK_2 || evt.getKeyCode() == KeyEvent.VK_NUMPAD2) && tbl_Pembelian.getSelectedColumn() == 4) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_Pembelian.getValueAt(tbl_Pembelian.getSelectedRow(), 1));
            try {
                String sql = "select nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi "
                        + "where bk.identitas_konversi = '2' and bk.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String sat = res.getString("nama_konversi");
                    String sat2 = sat;
                    tbl_Pembelian.setValueAt(sat2, tbl_Pembelian.getSelectedRow(), 4);
                    System.out.println(sat2);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if ((evt.getKeyCode() == KeyEvent.VK_3 || evt.getKeyCode() == KeyEvent.VK_NUMPAD3) && tbl_Pembelian.getSelectedColumn() == 4) {
            String kode_barang = String.valueOf(tbl_Pembelian.getValueAt(tbl_Pembelian.getSelectedRow(), 1));
            try {
                String sql = "select nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi "
                        + "where bk.identitas_konversi = '3' and bk.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String sat = res.getString("nama_konversi");
                    String sat2 = "2. " + sat;
                    tbl_Pembelian.setValueAt(sat2, tbl_Pembelian.getSelectedRow(), 4);
                    System.out.println(sat2);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        }

//        loadNumberTable();

    }//GEN-LAST:event_tbl_PembelianKeyPressed
    void clear() {
        DefaultTableModel t = (DefaultTableModel) tbl_Pembelian.getModel();
        t.setRowCount(0);
        t.addRow(new Object[]{"", "", "", "", "", "", ""});
    }
    private void txt_diskonRpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonRpKeyTyped
        char enter = evt.getKeyChar();
        if (!(Character.isDigit(enter))) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_diskonRpKeyTyped

    private void comTableKonvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableKonvActionPerformed

        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        int baris = tbl_Pembelian.getRowCount();
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        int kode_barang = 0;
        try {
//            for (int i = 0; i < baris; i++) {
//                kode_barang = Integer.parseInt(tabelModel.getValueAt(i, 1).toString());
//            }
            kode_barang = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 1).toString());
            String sql = "select nama_konversi, jumlah_konversi, identitas_konversi from barang_konversi bk, konversi k where bk.kode_konversi = k.kode_konversi and bk.kode_barang ='" + kode_barang + "'";
            System.out.println("sql: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                System.out.println("y1 " + comTableKonv.getSelectedIndex() + " a: " + res.getInt(3));
                if (comTableKonv.getSelectedIndex() == res.getInt(3) - 1) {
                    System.out.println("y2 " + res.getInt(2) + " t: " + Tempharga);
                    double temp = Tempharga * res.getInt(2);
                    System.out.println("error: " + temp);
                    for (int i = 0; i < baris; i++) {
                        int tmpJumlah = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 5).toString());
                        model.setValueAt(String.valueOf(temp * tmpJumlah), tbl_Pembelian.getSelectedRow(), 7);
                        model.setValueAt(String.valueOf(temp * tmpJumlah), tbl_Pembelian.getSelectedRow(), 12);
                    }
                }
            }

        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Pilih Barang dan Harga Terlebih Dahulu !");
        }

//        System.out.println("eaaaa");
//        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
//        int baris = tbl_Pembelian.getRowCount();
//        TableModel tabelModel;
//        tabelModel = tbl_Pembelian.getModel();
//        int kode_barang = 0;
//        try {
//            for (int i = 0; i < baris; i++) {
//                kode_barang = Integer.parseInt(tabelModel.getValueAt(i, 1).toString());
//            }
//            String sql = "select * from barang_konversi where kode_barang ='" + kode_barang + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//           
//            while (res.next()) {
//                String konvTemp = res.getString(2);
//                int selectedRow = tbl_Pembelian.getSelectedRow();
//                if ((comTableKonv.getSelectedIndex() == 1) & (comTableKonv.getSelectedIndex() + 1) == res.getInt(5)) {
//                    int temporary = (int) Float.parseFloat(Tempharga);
//                    float temp = temporary * res.getInt(4);
//                    model.setValueAt(String.valueOf(temp), 0, 6);
//                 
//                } else{
//                     int temporary = (int) Float.parseFloat(Tempharga);
//                    model.setValueAt(String.valueOf(temporary), 0, 6);
//             
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//        }
//
    }//GEN-LAST:event_comTableKonvActionPerformed

    private void comTableLokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableLokasiActionPerformed
        try {
            String barang = comTableBarang.getSelectedIndex() != -1 ? comTableBarang.getSelectedItem().toString() : comKodeBarang.getSelectedItem().toString();
            String sql = "select * from lokasi l, barang_lokasi bl, barang b "
                    + "where l.kode_lokasi = bl.kode_lokasi "
                    + "and b.kode_barang = bl.kode_barang "
                    + "and l.nama_lokasi = '" + comTableLokasi.getSelectedItem().toString() + "' "
                    + "and b.nama_barang = '" + barang + "'";
//            System.out.println("stl: "+sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String id = res.getString(2);
                int selectedRow = tbl_Pembelian.getSelectedRow();
                if (selectedRow != -1) {
                    int stok_now = res.getInt("jumlah");
                    stok = stok_now;
                    System.out.println("st_n: " + stok);
                    tbl_Pembelian.setValueAt(id, selectedRow, 3);
                }
            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }//GEN-LAST:event_comTableLokasiActionPerformed

    private void LabelClearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LabelClearKeyPressed

    }//GEN-LAST:event_LabelClearKeyPressed

    private void LabelClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelClearMouseClicked
        BersihField();
    }//GEN-LAST:event_LabelClearMouseClicked

    private void LabelNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelNextMouseClicked
        //nextOrder();
        boolean isi = false;

        try {
            String sql = "select no_faktur_pembelian from pembelian ORDER BY no_faktur_pembelian DESC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            do {
                editable();

                boolean status = tempRs == null;
                if (status) {
                    tempRs = res;
                }
                boolean statusIsi = tempRs.previous();
                System.out.println(statusIsi);
                if (statusIsi) {
                    ini_baru = false;
                    String noNota = tempRs.getString("no_faktur_pembelian");
                    txt_noNota.setText(noNota);
                    System.out.println(noNota);
//                    System.out.println("prev");
                    loadForm(noNota);
                    loadTable(noNota);
//                    getQtyTambahTemp(txt_noNota.getText());
                    break;
                } else {
                    //tempRs.next(); 
//                    baru();
                    enable_true();
                    if (akhir) {
                        ini_baru = true;
                        akhir = false;
                    } else {
                        baru();
                    }
                    JOptionPane.showMessageDialog(this, "End of File");
                    break;
                }
            } while (evt.equals(txt_noNota != null));
            //res.close();
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.getMessage(),
//                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(Pembelian_Transaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
//        enable_true();

    }//GEN-LAST:event_LabelNextMouseClicked
    void baru() {

        //initComponents();
        txt_diskonRp.setEditable(false);
        loadSupplier();
        loadTop();
        loadJenisKeuangan();
        loadComTableBarang();
        loadComKodeBarang();
//        loadNumberTable();
        loadComTableSatuan();
        loadComTableLokasi();
        autonumber();
        AturlebarKolom();
        uangtotal();
        uangdp();

        if (!ini_baru) {
            DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
            TableModel tabelModel;
            tabelModel = tbl_Pembelian.getModel();
            model.addRow(new Object[]{"", "", "", "", "0", "", "0", "0", "0"});
            ini_baru = true;
        }
        enable_true();
    }

    void enable_true() {
        comSupplier.setEnabled(true);
        com_faktur.setEnabled(true);
        com_top.setEnabled(true);
        txt_inv.setEditable(true);
        tgl_inv.setEnabled(true);
        txt_diskon.setEditable(true);
        txt_diskonRp.setEditable(true);
        com_jenisKeuangan.setEnabled(true);
        txt_dp.setEditable(true);
        txt_ket.setEditable(true);
        tbl_Pembelian.setEnabled(true);
    }
    private void LabelPrevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelPrevMouseClicked
//        prevOrder();
        boolean isi = false;
        do {
            try {
                String sql = "select no_faktur_pembelian from pembelian ORDER BY no_faktur_pembelian DESC";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                boolean status = tempRs == null;
                akhir = false;
                editable();
                String noNota = "";
                if (status) {
                    tempRs = res;
                }
                if (tempRs.next()) {
                    noNota = tempRs.getString("no_faktur_pembelian");
                    txt_noNota.setText(noNota);
                    System.out.println(noNota);
                    loadForm(noNota);
                    loadTable(noNota);
                    ini_baru = false;
                    break;

                } else {
                    tempRs.previous();
                    JOptionPane.showMessageDialog(this, "Start of File");
                }
            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
//                        "Kesalahan", JOptionPane.WARNING_MESSAGE);
            }
        } while (evt.equals(txt_noNota != null));
        tempPrev++;
    }//GEN-LAST:event_LabelPrevMouseClicked

    private void txt_invKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_invKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            tgl_inv.getDateEditor().getUiComponent().requestFocusInWindow();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_txt_invKeyPressed

    private void tgl_invKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_invKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            txt_diskon.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_tgl_invKeyPressed

    private void LabelSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelSaveMouseClicked
        simpan_data();

    }//GEN-LAST:event_LabelSaveMouseClicked
    void simpan_data() {
        if (comSupplier.getSelectedItem().toString()==null) {
            JOptionPane.showMessageDialog(null, "Isi data supplier terlebih dahulu", "informasi", JOptionPane.INFORMATION_MESSAGE);
            comSupplier.requestFocus();
        } else {
            try {
                SimpleDateFormat format_tanggal = new SimpleDateFormat("yyyy-MM-dd");
                String date = format_tanggal.format(tgl_inv.getDate());
                getKodeSupply();
                getKodeUang();

                tempK = 0;
                tempL = 0;
                tempJ = 0;
                tempJQ = 0;

                getQtyTambahTemp(txt_noNota.getText());

                Koneksi Koneksi = new Koneksi();
                Connection con = Koneksi.configDB();
                Statement st = con.createStatement();

//            String sql = "SELECT * FROM barang_lokasi WHERE"
//                    + " kode_barang = ' " + tmpKodeBarang + "'"
//                    + " AND kode_lokasi = " + tempL;
//            java.sql.ResultSet res = st.executeQuery(sql);
//            while (res.next()) {
//                tempJQ = res.getDouble("jumlah");
//            }
//            String sql = "UPDATE barang_lokasi SET"
//                    + " jumlah = " + (tempJQ - tempJ)
//                    + " WHERE"
//                    + " kode_barang = " + tempK
//                    + " AND kode_lokasi = " + tempL;
//            st.executeUpdate(sql);
                String sql = "DELETE FROM pembelian "
                        + "WHERE no_faktur_pembelian = '" + txt_noNota.getText() + "'";

                st.executeUpdate(sql);
                sql = "DELETE FROM detail_pembelian "
                        + "WHERE no_faktur_pembelian = '" + txt_noNota.getText() + "'";

                st.executeUpdate(sql);

                sql = "insert into pembelian( no_faktur_pembelian, no_faktur_supplier_pembelian, tgl_pembelian, tgl_nota_supplier_pembelian, biaya_pembelian, biaya_pembayaran, discon_persen, discon_rp, potongan, kode_supplier, keterangan_pembelian, faktur_bg, kode_nama_keuangan, total_dp)"
                        + "value('" + txt_noNota.getText() + "','"
                        + txt_inv.getText() + "','"
                        + txt_tgl.getText() + "','"
                        + date + "','-"
                        + txt_tbl_total.getText().replace(".", "") + "','-"
                        + txt_tbl_total.getText().replace(".", "") + "','"
                        + txt_diskon.getText() + "','"
                        + txt_diskonRp.getText() + "','"
                        + txt_diskonRp.getText() + "','"
                        + tempKodeSupply.toString() + "','"
                        + txt_ket.getText() + "','"
                        + "" + "','"
                        + tmpKodeUang + "','"
                        + txt_dp.getText() + "');";

                int dbq = st.executeUpdate(sql);

                int baris = tbl_Pembelian.getRowCount();
                System.out.println("baris = " + baris);
                TableModel tabelModel;
                tabelModel = tbl_Pembelian.getModel();

                for (int i = 0; i < baris; i++) {
                    getHargaJual(i);
                    sql = "insert into detail_pembelian( no_faktur_pembelian, id_top, kode_barang, nama_barang_edit, kode_lokasi, kode_konversi, jumlah_barang, jumlah_per_pcs, harga_pembelian, harga_asli_pakai, harga_asli, harga_asli_discon, hpp, harga_awal, hj1, hj2, discon_persen, discon_rp, discon2_persen, discon2_rp, max_return)"
                            + "value" + "('" + txt_noNota.getText() + "','"
                            + getKodeTOP() + "','"
                            + tbl_Pembelian.getValueAt(i, 1).toString() + "','"
                            + tbl_Pembelian.getValueAt(i, 2).toString() + "','"
                            + getKodeLokasi(i) + "','"
                            + getKodeKonv(i) + "','"
                            + tbl_Pembelian.getValueAt(i, 5).toString() + "','"
                            + getKonvPcs(i) + "','"
                            + getHargaBeli(i) + "','"
                            + tbl_Pembelian.getValueAt(i, 6).toString() + "','"
                            + tbl_Pembelian.getValueAt(i, 7).toString() + "','"
                            + tbl_Pembelian.getValueAt(i, 12).toString() + "','"
                            + getHargaBeli(i) + "','"
                            + tbl_Pembelian.getValueAt(i, 6).toString() + "','"
                            + tmpHJ1 + "','"
                            + tmpHJ2 + "','"
                            + tbl_Pembelian.getValueAt(i, 8).toString() + "','"
                            + tbl_Pembelian.getValueAt(i, 9).toString() + "','"
                            + tbl_Pembelian.getValueAt(i, 10).toString() + "','"
                            + tbl_Pembelian.getValueAt(i, 11).toString() + "','"
                            + 0 + "')";

                    int dbq2 = st.executeUpdate(sql);

                    if (dbq2 == 1) {
                        JOptionPane.showMessageDialog(null, "data barang ke " + (i + 1) + " sudah ditambahkan ke database", "informasi", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

                for (int i = 0; i < baris; i++) {
                    getQty(i);
                    System.out.println("Qty = " + jmlQty);
                    getQtyTambah(i);
                    System.out.println("Qty Tambah = " + jumlahTambah);
                    double kodelokasi = getKodeLokasi(i);
                    sql = "UPDATE barang_lokasi SET"
                            + " jumlah = " + (jmlQty + jumlahTambah)
                            + " WHERE"
                            + " kode_barang = " + tbl_Pembelian.getValueAt(i, 1)
                            + " AND kode_lokasi = " + kodelokasi;

                    int dbq3 = st.executeUpdate(sql);

                    if (dbq3 == 1) {
                        JOptionPane.showMessageDialog(null, "data barang ke " + (i + 1) + " jumlah qty ditambah " + jumlahTambah, "informasi", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                if (dbq == 1) {
                    JOptionPane.showMessageDialog(null, "data sudah ditambahkan ke database", "informasi", JOptionPane.INFORMATION_MESSAGE);
                    con.close();
                }

                sql = "insert into supplier( nama_supplier, alamat_supplier, rekening_supplier)"
                        + "value('" + txt_nmSupply.getText() + "','" + txt_almtSupply.getText() + "','" + txt_rekSupply.getText() + "');";
//            System.out.println(sql);
                st.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "data tidak dimasukkan ke database" + e, "informasi", JOptionPane.INFORMATION_MESSAGE);
            }
        }
//        finally {
//
//        }
    }
    private void LabelExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelExitMouseClicked
        dispose();
    }//GEN-LAST:event_LabelExitMouseClicked

    private void tbl_PembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_PembelianMouseClicked

//               
        int baris = tbl_Pembelian.getSelectedRow();
        int kolom = tbl_Pembelian.getSelectedColumn();

        TableModel model = tbl_Pembelian.getModel();

        int tabel = tbl_Pembelian.getRowCount();

//        model.setValueAt(rptabelkembali(String.valueOf(harga)), i - 1, 6);
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "baris : " + baris + " kolom : " + kolom);

//                System.out.println("Double Click");
            Pembelian_LaporanFaktur a = new Pembelian_LaporanFaktur();
            a.setVisible(true);

        }


    }//GEN-LAST:event_tbl_PembelianMouseClicked

    private void txt_nmSupplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nmSupplyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nmSupplyActionPerformed

    private void txt_jumItemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumItemKeyReleased

    }//GEN-LAST:event_txt_jumItemKeyReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        try {
            SimpleDateFormat format_tanggal = new SimpleDateFormat("yyyy-MM-dd");
            String date = format_tanggal.format(tgl_inv.getDate());
            Koneksi Koneksi = new Koneksi();
            Connection con = Koneksi.configDB();

            Statement st = con.createStatement();
            String sql = "insert into pembelian( no_faktur_pembelian, no_faktur_supplier_pembelian, tgl_pembelian,  tgl_nota_supplier_pembelian,  discon_persen, discon_rp, keterangan_pembelian)"
                    + "value('" + txt_noNota.getText() + "','" + txt_inv.getText() + "','" + txt_tgl.getText() + "','" + date + "','" + txt_diskon.getText() + "','" + txt_diskonRp.getText() + "','" + txt_ket.getText() + "');";
//            System.out.println(sql);
            int row = st.executeUpdate(sql);

            if (row == 1) {
                JOptionPane.showMessageDialog(null, "data sudah ditambahkan ke database", "informasi", JOptionPane.INFORMATION_MESSAGE);
                con.close();
            }

            sql = "insert into supplier( nama_supplier, alamat_supplier, rekening_supplier)"
                    + "value('" + txt_nmSupply.getText() + "','" + txt_almtSupply.getText() + "','" + txt_rekSupply.getText() + "');";
//            System.out.println(sql);
            st.executeUpdate(sql);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "data tidak dimasukkan ke database" + e, "informasi", JOptionPane.INFORMATION_MESSAGE);
        } finally {

        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        BersihField();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void txt_noNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_noNotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_noNotaActionPerformed

    private void txt_tglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tglActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tglActionPerformed

    private void com_jenisKeuanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_jenisKeuanganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_com_jenisKeuanganActionPerformed

    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_jPanel1KeyPressed

    private void LabelNextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LabelNextKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LabelNextKeyPressed

    private void comTableBarangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comTableBarangItemStateChanged
        tampil = false;
    }//GEN-LAST:event_comTableBarangItemStateChanged

    private void comSupplierItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comSupplierItemStateChanged
        tampil1 = false;
    }//GEN-LAST:event_comSupplierItemStateChanged

    private void comKodeBarangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comKodeBarangItemStateChanged
        tampilk = false;
    }//GEN-LAST:event_comKodeBarangItemStateChanged

    private void comKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comKodeBarangActionPerformed
        load_dari_kode_barang();
    }//GEN-LAST:event_comKodeBarangActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void tgl_invKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_invKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txt_ket.requestFocus();
        }
    }//GEN-LAST:event_tgl_invKeyTyped

    private void txt_dpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dpKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            double dp;
//            if (txt_dp.getText().toString().equals("")) {
//                dp = 0;
//            } else {
//                dp = Double.parseDouble(txt_dp.getText().toString());
//            }
//            setTmpTotal();
//            double total = totalBiaya;
////            dp = total * dp / 100;
//            txt_dp.setText(String.valueOf(dp));
//            totalBiaya -= dp;
//            txt_tbl_total.setText(String.valueOf(totalBiaya));
//            uangtotal();
            cekdiskondp();
        }// TODO add your handling code here:
    }//GEN-LAST:event_txt_dpKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pembelian_Transaksi().setVisible(true);

            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelClear;
    private javax.swing.JLabel LabelExit;
    private javax.swing.JLabel LabelNext;
    private javax.swing.JLabel LabelPrev;
    private javax.swing.JLabel LabelPrint;
    private javax.swing.JLabel LabelSave;
    private javax.swing.JCheckBox chk_cetakSlg;
    private javax.swing.JCheckBox chk_fakturSupply;
    private javax.swing.JComboBox<String> comKodeBarang;
    private javax.swing.JComboBox comSupplier;
    private javax.swing.JComboBox comTableBarang;
    private javax.swing.JComboBox<String> comTableKonv;
    private javax.swing.JComboBox<String> comTableLokasi;
    private javax.swing.JComboBox<String> com_faktur;
    private javax.swing.JComboBox<String> com_jenisKeuangan;
    private javax.swing.JComboBox<String> com_top;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JLabel lbl_nmKasir;
    private javax.swing.JButton tblActionTabelBarang;
    private javax.swing.JTable tbl_Pembelian;
    private com.toedter.calendar.JDateChooser tgl_inv;
    private javax.swing.JTextField txt_almtSupply;
    private javax.swing.JTextField txt_diskon;
    private javax.swing.JTextField txt_diskonRp;
    private javax.swing.JTextField txt_dp;
    public javax.swing.JTextField txt_inv;
    private javax.swing.JTextField txt_jumItem;
    private javax.swing.JTextField txt_jumQty;
    private javax.swing.JTextField txt_ket;
    private javax.swing.JTextField txt_nmSupply;
    private javax.swing.JTextField txt_noNota;
    private javax.swing.JTextField txt_rekSupply;
    private javax.swing.JTextField txt_tbl_total;
    private javax.swing.JTextField txt_tgl;
    // End of variables declaration//GEN-END:variables

    private String totalclone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
