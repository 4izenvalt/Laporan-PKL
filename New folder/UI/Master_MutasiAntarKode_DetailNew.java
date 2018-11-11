/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import com.sun.glass.events.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

/**
 *
 * @author USER
 */
public class Master_MutasiAntarKode_DetailNew extends javax.swing.JFrame {

    private String no_bukti, lokasi_mutasi;
    int kode_barang;
    Master_MutasiAntarKode awal;

    ArrayList<String> kode_nama_arr = new ArrayList();
    private static int item = 0;
    private boolean tampil = true;

    /**
     * Creates new form Penjualan_penjualan
     */
    public Master_MutasiAntarKode_DetailNew() {
        initComponents();
        this.awal = awal;
        this.setLocationRelativeTo(null);

        loadComLokasi();
        //loadComTableKode();
        loadComTableSatuan();
        //loadComTableBarang();
        loadNumberTable();
        autonumber();
        tanggalsekarang();
    }

    public Master_MutasiAntarKode_DetailNew(Master_MutasiAntarKode awal) {
        initComponents();
        this.awal = awal;
        this.setLocationRelativeTo(null);
        loadComLokasi();
        loadComTableSatuan();
        loadNumberTable();
        autonumber();
        tanggalsekarang();

        //JCombobox kode barang
        ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboKode(((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
                            tbl_mutasi_kode.editCellAt(tbl_mutasi_kode.getSelectedRow(), 1);
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
                ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).setText(key);
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
        //kode JCombobox sampai sini

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
                            tbl_mutasi_kode.editCellAt(tbl_mutasi_kode.getSelectedRow(), 2);
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

    }

    public Master_MutasiAntarKode_DetailNew(String no_bukti, Master_MutasiAntarKode awal) {
        //this.update = update;
        this.awal = awal;
        initComponents();
        loadComTableKode();
        this.no_bukti = no_bukti;
        loadLihatDetail();

        //JCombobox kode barang
        ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboKode(((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
                            tbl_mutasi_kode.editCellAt(tbl_mutasi_kode.getSelectedRow(), 1);
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
                ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).setText(key);
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
        //kode JCombobox sampai sini

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
                            tbl_mutasi_kode.editCellAt(tbl_mutasi_kode.getSelectedRow(), 2);
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

        this.setLocationRelativeTo(null);
    }

    void loadLihatDetail() {
        loadComLokasi();
        comTableLokasi.setEnabled(false);

        try {
            String sql = "select mk.no_bukti, mk.tgl_mutasi,mk.kode_barang, mk.nama_barang, lokasi.nama_lokasi as lokasi,k.nama_konversi,mk.jumlah , mku.keterangan from mutasi_kode_detail mk join mutasi_kode_detail_utama mku on mk.no_bukti = mku.no_bukti join lokasi on lokasi.kode_lokasi=mk.kode_lokasi join konversi k on k.kode_konversi=mk.kode_konversi where mk.no_bukti ='" + no_bukti + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) tbl_mutasi_kode.getModel();
            int i = 0;
            String sql2 = "select tanggal from mutasi_kode_detail where no_bukti='" + no_bukti + "'";

            while (res.next()) {
                model.setValueAt(i + 1, i, 0);
                comTableLokasi.setSelectedItem(res.getString("lokasi"));
                txt_no_bukti.setText(res.getString("no_bukti"));
                tgl_now.setText(res.getString("tgl_mutasi"));
                txt_ket.setText(res.getString("keterangan"));
                model.setValueAt(res.getString("kode_barang"), i, 1);
                model.setValueAt(res.getString("nama_barang"), i, 2);

                String satuan = res.getString("nama_konversi");
                model.setValueAt(satuan, i, 3);
                model.setValueAt(res.getString("jumlah"), i, 4);
                model.addRow(new Object[]{"", "", "", ""});
                i++;
            }
            model.removeRow(i);
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadNumberTable() {
        int baris = tbl_mutasi_kode.getRowCount();
        for (int a = 0; a < baris; a++) {
            String nomor = String.valueOf(a + 1);
            tbl_mutasi_kode.setValueAt(nomor + ".", a, 0);
        }

    }

    void loadComLokasi() {
        try {
            String sql = "select * from lokasi order by kode_lokasi asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(2);
                comTableLokasi.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadComTableKode() {
        try {
            String sql = "select * from barang order by kode_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(1);
                String barang = res.getString(4);
                comKodeBarang.addItem(name + "-" + barang);
                // comTableKode.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    private void tanggalsekarang() {
        Thread p = new Thread() {
            public void run() {
                for (;;) {
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
                    tgl_now.setText(timeStamp);
                }
            }
        };
        System.out.println(tgl_now.getText());
        p.start();
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
                comTableKonv.addItem(name);
            }
            res.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

    void loadComTableBarang() {
        try {
            String sql = "select * from barang order by kode_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString(1);
                String barang = res.getString(4);
//                comTableBarang.addItem(barang);
                comTableBarang.addItem(kode + "-" + barang);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    private void removeSelectedRows(JTable table) {
        int Hapus = 1;
        Hapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin mau menghapus baris ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if (Hapus == 0) {
            DefaultTableModel model = (DefaultTableModel) this.tbl_mutasi_kode.getModel();
            int[] rows = table.getSelectedRows();

            for (int i = 0; i < rows.length; i++) {
                model.removeRow(rows[i] - i);
            }
        }
    }

    void hapusMutasi() {
        int hapus = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin Menghapus data ini ?", "Konfimasi Tolak Hapus Data", JOptionPane.OK_CANCEL_OPTION);
        if (hapus == JOptionPane.OK_OPTION) {
            try {
                Connection conn = (Connection) Koneksi.configDB();
                String sql = "delete from mutasi_kode_detail where no_bukti='" + txt_no_bukti.getText() + "'";
                PreparedStatement st = conn.prepareStatement(sql);
                st.executeUpdate();
                System.out.println(sql);
                String sql2 = "delete from mutasi_kode_detail_utama where no_bukti='" + txt_no_bukti.getText() + "'";
                PreparedStatement st2 = conn.prepareStatement(sql2);
                st2.executeUpdate();
                System.out.println(sql2);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Delete Data Gagal");
            }
            JOptionPane.showMessageDialog(null, "Data sudah dihapus.");
//            Master_MutasiAntarKode mak = new Master_MutasiAntarKode();           
//            mak.loadTable("*","*","*");
        }
    }

    public void autonumber() {
        try {
            String lastNo = "";
            String sql = "select max(no_bukti) from mutasi_kode_detail ORDER BY no_bukti DESC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next() && res != null) {
                if (res.first() == false) {
                    txt_no_bukti.setText("MK");

                } else {
                    res.last();
                    String auto_num = res.getString(1);

                    int noLama = Integer.parseInt(auto_num.substring(auto_num.length() - 4));
                    ++noLama;
                    String no = String.valueOf(noLama);
                    no = Integer.toString(noLama);

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
                    txt_no_bukti.setText(String.valueOf(huruf + "" + angkapad));

                }
            }
            res.close();

        } catch (NullPointerException ex) {
            txt_no_bukti.setText("MK18-00001");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static String rightPadZeros(String str, int num) {
        return String.format("%05d", Integer.parseInt(str));
    }

    double qty_konversi(String kode_barang, String kode_konversi, double qty) {
        double hasil = 0;

        try {

            String sql = "select * from barang_konversi where kode_barang='" + kode_barang + "' and (kode_konversi ='" + kode_konversi + "' || identitas_konversi='1') order by identitas_konversi asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            double[] jumlah_konversi = new double[2];
            int indeks = 0; //0 dg identitas 1, 1 dengan identitas yg dipilih
            int[] identitas_konversi = new int[2];
            while (res.next()) {
                jumlah_konversi[indeks] = Double.parseDouble(res.getString("jumlah_konversi"));
                identitas_konversi[indeks] = Integer.parseInt(res.getString("identitas_konversi"));
                indeks++;
            }

            System.out.println("identitas 1 =" + identitas_konversi[0]);
            System.out.println("identitas 2 =" + identitas_konversi[1]);
            if (identitas_konversi[1] == 3) {
                hasil = qty * jumlah_konversi[0];
            } else if (identitas_konversi[1] == 2) {
                hasil = qty * jumlah_konversi[1];
            } else {
                hasil = qty;
            }
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
        return hasil;
    }

    void bersihField() {
        DefaultTableModel model = (DefaultTableModel) tbl_mutasi_kode.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        model.addRow(new Object[]{"", "", "", "", ""});
        txt_ket.setText("");
    }

    void simpanData() {
        if (tbl_mutasi_kode.getRowCount() == 2) {
            try {
                Koneksi Koneksi = new Koneksi();
                Connection con = Koneksi.configDB();
                Statement st = con.createStatement();
                int row = 0;
                int jumlah_data = tbl_mutasi_kode.getRowCount();
                String kode_barang = "", satuan_asal = "", satuan_tujuan = "", lokasi = "";
                boolean cukup = true;
                double qty_kurangi = 0, qty_tambah = 0;

                for (int i = 0; i < jumlah_data; i++) {
                    //ubah ke kode lokasi

                    String sql2 = "select kode_lokasi from lokasi where nama_lokasi= '" + lokasi_mutasi + "'";
                    Statement st2 = con.createStatement();
                    java.sql.ResultSet res = st2.executeQuery(sql2);
                    while (res.next()) {
                        lokasi = res.getString(1);
                    }
                    res.close();

                    //ubah ke kode satuan (konversi)
                    String satuan = "";
                    String sql3 = "select kode_konversi from konversi where nama_konversi = '" + tbl_mutasi_kode.getValueAt(i, 3) + "'";
                    Statement st3 = con.createStatement();
                    java.sql.ResultSet res2 = st3.executeQuery(sql3);
                    while (res2.next()) {
                        satuan = res2.getString(1);
                    }
                    res.close();
                    //cek ketersediaan barang kode tertentu
                    if (i == 0) {
                        for (int j = 0; j < jumlah_data; j++) {
                            //Konversi satuan cek
                            String satuan_cek = "";
                            String sql4 = "select kode_konversi from konversi where nama_konversi = '" + tbl_mutasi_kode.getValueAt(i, 3) + "'";
                            Statement st4 = con.createStatement();
                            java.sql.ResultSet res3 = st4.executeQuery(sql4);
                            while (res3.next()) {
                                satuan_cek = res3.getString(1);
                            }
                            res.close();
                            System.out.println("qty : " + tbl_mutasi_kode.getValueAt(j, 4));

                            double qty = Double.parseDouble(String.valueOf(tbl_mutasi_kode.getValueAt(j, 4)));

                            //qty dikali jumlah_konversi
                            double qty_sesudah = qty_konversi(String.valueOf(tbl_mutasi_kode.getValueAt(j, 1)), satuan_cek, qty);

                            String sql5 = "select * from barang_lokasi where kode_barang ='" + String.valueOf(tbl_mutasi_kode.getValueAt(j, 1)) + "' and kode_lokasi = '" + lokasi + "'";
                            System.out.println(sql5);
                            java.sql.Statement st5 = con.createStatement();
                            java.sql.ResultSet res4 = st5.executeQuery(sql5);
                            System.out.println("qty_sesudah " + qty_sesudah);
                            while (res4.next()) {
                                int stok = Integer.parseInt(res4.getString("jumlah"));
                                System.out.println("stok " + stok);
                                kode_barang = String.valueOf(tbl_mutasi_kode.getValueAt(j, 1));
                                //nama_gudang = String.valueOf(tbl_tambah_mutasi.getValueAt(j, 3));
                                if (qty_sesudah > stok) {
                                    cukup = false;
                                    //keluar while
                                    break;
                                }
                            }
                            if (!cukup) {
                                //keluar pengecekan stok
                                break;
                            }
                            res4.close();

                        }
                    }
                    //Konversi satuan asal
                    String sql4 = "select kode_konversi from konversi where nama_konversi = '" + tbl_mutasi_kode.getValueAt(i, 3) + "'";
                    Statement st4 = con.createStatement();
                    java.sql.ResultSet res3 = st4.executeQuery(sql4);
                    while (res3.next()) {
                        satuan_asal = res3.getString(1);
                    }
                    res.close();
                    //Konversi satuan tujuan
                    String sql4b = "select kode_konversi from konversi where nama_konversi = '" + tbl_mutasi_kode.getValueAt(1, 3) + "'";
                    Statement st4b = con.createStatement();
                    java.sql.ResultSet res3a = st4b.executeQuery(sql4);
                    while (res3a.next()) {
                        satuan_tujuan = res3a.getString(1);
                    }
                    res.close();

                    if (!cukup) {
                        //keluar for untuk input semua baris
                        break;
                    } else {
                        //memasukkan baris ke-i
                        System.out.println("jml kurangi =" + tbl_mutasi_kode.getValueAt(0, 4));
                        System.out.println("jml tambah= " + tbl_mutasi_kode.getValueAt(1, 4));
                        double jumlah_kurangi = Double.parseDouble(String.valueOf(tbl_mutasi_kode.getValueAt(0, 4)));
                        double jumlah_tambah = Double.parseDouble(String.valueOf(tbl_mutasi_kode.getValueAt(1, 4)));
                        qty_kurangi = qty_konversi(String.valueOf(tbl_mutasi_kode.getValueAt(0, 1)), satuan_asal, jumlah_kurangi);
                        qty_tambah = qty_konversi(String.valueOf(tbl_mutasi_kode.getValueAt(1, 1)), satuan_tujuan, jumlah_tambah);

                        //nanti keterangannya dimasukin tabel yg utama
                        String sql = "insert into mutasi_kode_detail( no_bukti, kode_lokasi, tgl_mutasi, kode_barang, nama_barang, kode_konversi, jumlah)"
                                + "value('" + txt_no_bukti.getText() + "','" + lokasi + "','" + tgl_now.getText() + "','"
                                + tbl_mutasi_kode.getValueAt(i, 1) + "','" + tbl_mutasi_kode.getValueAt(i, 2) + "','" + satuan_asal
                                + "','" + txt_qty.getText() + "');";
                        row = st.executeUpdate(sql);
                    }
                }
                if (cukup) {
                    SimpleDateFormat format_tanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = format_tanggal.format(System.currentTimeMillis());
                    //memasukkan ke tabel mutasi utama
                    String sql = "insert into mutasi_kode_detail_utama(no_bukti, tgl_mutasi, keterangan)"
                            + "value('" + txt_no_bukti.getText() + "','" + date + "','" + txt_ket.getText() + "');";
                    st.executeUpdate(sql);
                    //mengupdate stok dimasing-masing kode di database barang_lokasi, parameternya kode barang sm kode lokasi
                    String mengurangi = "update barang_lokasi set jumlah= jumlah - " + qty_kurangi + " where kode_barang = '" + tbl_mutasi_kode.getValueAt(0, 1) + "' and kode_lokasi = '" + lokasi + "'";
                    Statement st_kurang = con.createStatement();
                    row = st_kurang.executeUpdate(mengurangi);

                    String menambah = "update barang_lokasi set jumlah= jumlah + " + qty_tambah + " where kode_barang = '" + tbl_mutasi_kode.getValueAt(1, 1) + "' and kode_lokasi = '" + lokasi + "'";
                    Statement st_tambah = con.createStatement();
                    row = st_tambah.executeUpdate(menambah);

                }

                if (row == 1) {
                    JOptionPane.showMessageDialog(null, "data sudah ditambahkan ke database", "informasi", JOptionPane.INFORMATION_MESSAGE);
                    con.close();
                } else{
                    JOptionPane.showMessageDialog(null, "Stok barang " + kode_barang + "tidak cukup");
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "data tidak dimasukkan ke database" + e, "informasi", JOptionPane.INFORMATION_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "isi 2 baris data");
        }

    }

    void exitForm() {
        //Master_MutasiAntarKode mak = new Master_MutasiAntarKode();
        //mak.setVisible(true);
        awal.setFocusable(true);
        this.dispose();
        awal.loadTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comKodeBarang = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        comTableKonv = new javax.swing.JComboBox<>();
        comTableBarang = new javax.swing.JComboBox<>();
        txt_qty = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_mutasi_kode = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        LabelSave = new javax.swing.JLabel();
        tabel_clear = new javax.swing.JLabel();
        labelDelete = new javax.swing.JLabel();
        labelExit = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        txt_ket = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        txt_no_bukti = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        comTableLokasi = new javax.swing.JComboBox<>();
        tgl_now = new javax.swing.JTextField();

        comKodeBarang.setEditable(true);
        comKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comKodeBarangActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comTableKonv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comTableKonv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableKonvActionPerformed(evt);
            }
        });

        comTableBarang.setEditable(true);
        comTableBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableBarangActionPerformed(evt);
            }
        });

        txt_qty.setText("jTextField1");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 204));
        jLabel14.setText("Tanggal");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 204));
        jLabel17.setText("No. Bukti");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.white, java.awt.Color.white));

        tbl_mutasi_kode.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "No", "Kode", "Nama", "Satuan", "Qty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_mutasi_kode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_mutasi_kodeKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_mutasi_kode);
        if (tbl_mutasi_kode.getColumnModel().getColumnCount() > 0) {
            tbl_mutasi_kode.getColumnModel().getColumn(0).setResizable(false);
            tbl_mutasi_kode.getColumnModel().getColumn(1).setCellEditor(new ComboBoxCellEditor(comKodeBarang));
            tbl_mutasi_kode.getColumnModel().getColumn(2).setCellEditor(new ComboBoxCellEditor(comTableBarang));
            tbl_mutasi_kode.getColumnModel().getColumn(3).setResizable(false);
            tbl_mutasi_kode.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comTableKonv));
            tbl_mutasi_kode.getColumnModel().getColumn(4).setResizable(false);
            tbl_mutasi_kode.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(txt_qty));
        }

        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));

        LabelSave.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        LabelSave.setText("F12 - Save");
        LabelSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelSaveMouseClicked(evt);
            }
        });

        tabel_clear.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tabel_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Clear-icon.png"))); // NOI18N
        tabel_clear.setText("F9 - Clear");
        tabel_clear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_clearMouseClicked(evt);
            }
        });

        labelDelete.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        labelDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N
        labelDelete.setText("F5-Delete");
        labelDelete.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                labelDeleteAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        labelDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelDeleteMouseClicked(evt);
            }
        });

        labelExit.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        labelExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        labelExit.setText("Esc - Exit");
        labelExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelExitMouseClicked(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Kasir");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Nama Kasir");

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));

        txt_ket.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_ket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_ketMouseClicked(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setText("Keterangan");

        jSeparator4.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator7.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator9.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        txt_no_bukti.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_no_bukti.setEnabled(false);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 204));
        jLabel18.setText("Lokasi");

        comTableLokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableLokasiActionPerformed(evt);
            }
        });

        tgl_now.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 788, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 65, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel32))
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel34)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_ket, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(18, 18, 18)
                                        .addComponent(tgl_now, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(40, 40, 40)
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_no_bukti, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31)
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comTableLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(LabelSave)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tabel_clear)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelDelete)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelExit)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelSave)
                    .addComponent(labelExit)
                    .addComponent(labelDelete)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tabel_clear)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel17)
                    .addComponent(txt_no_bukti, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(comTableLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tgl_now, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ket, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(8, 8, 8)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_ketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_ketMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ketMouseClicked

    private void labelDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelDeleteMouseClicked
        hapusMutasi();
    }//GEN-LAST:event_labelDeleteMouseClicked

    private void labelDeleteAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_labelDeleteAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_labelDeleteAncestorAdded

    private void comTableLokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableLokasiActionPerformed
        lokasi_mutasi = comTableLokasi.getSelectedItem().toString();
        System.out.println("lokasi " + comTableLokasi.getSelectedItem().toString());
    }//GEN-LAST:event_comTableLokasiActionPerformed
    void load_dari_kode_barang() {
        int selectedRow = tbl_mutasi_kode.getSelectedRow();
        String nama_awal = String.valueOf(comKodeBarang.getSelectedItem());
        String[] split = new String[2];
        System.out.println("nilai comTable barang adalah " + comKodeBarang.getSelectedItem());
        if (comKodeBarang.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }
        try {
            String sql = "select kode_barang,nama_barang from barang where kode_barang = '" + split[0] + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString("kode_barang");
                String nama = res.getString("nama_barang");
                this.kode_barang = Integer.parseInt(kode);
                loadNumberTable();
                loadComTableSatuan();
                if (selectedRow != -1) {
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
                    tbl_mutasi_kode.setValueAt(kode, selectedRow, 1);
                    tbl_mutasi_kode.setValueAt(nama, selectedRow, 2);
                }
            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }
    private void comKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comKodeBarangActionPerformed

        System.out.println("action");
        load_dari_kode_barang();

//        int baris = tbl_mutasi_kode.getRowCount();
//        TableModel tabelModel;
//        tabelModel = tbl_mutasi_kode.getModel();
//        int selectedRow = tbl_mutasi_kode.getSelectedRow();
//
//        try {
//            String sql = "select * from barang where kode_barang = '" + comKodeBarang.getSelectedItem() + "'";
//            
////            String sql = "select * from barang where kode_barang = '" + kata[0] + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
//                this.kode_barang=Integer.parseInt(res.getString("kode_barang"));
//                String kode = res.getString(1);
//                String brg = res.getString(4);
//                loadComTableSatuan();
//                String konv = comTableKonv.getItemAt(0).toString();
//
//                if (selectedRow != -1) {
//                    tbl_mutasi_kode.setValueAt(konv, selectedRow, 3);
////                    tbl_mutasi_kode.setValueAt((kode+"-"+brg), selectedRow, 2);
//                    tbl_mutasi_kode.setValueAt(brg, selectedRow, 2);
//                    tbl_mutasi_kode.setValueAt(kode, selectedRow, 1);
//
//                }
//            }
//            res.close();
//            conn.close();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//            e.printStackTrace();
//        }
//        try {
//            String sql = "select k.nama_konversi, k.kode_konversi from konversi k, barang_konversi bk where k.kode_konversi = bk.kode_konversi and bk.kode_barang = '" + kode_barang + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            String Konv = "";
//            while (res.next()) {
//                Konv = res.getString(1);
//                comTableKonv.addItem(Konv);
//                System.out.println("Konv =" + Konv);
////                System.out.println("echo : " + tbl_Pembelian.getValueAt(selectedRow, 2).toString());
//                tbl_mutasi_kode.setValueAt(comTableKonv.getSelectedItem(), selectedRow, 3);
//
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
//            e.printStackTrace();
//        }
    }//GEN-LAST:event_comKodeBarangActionPerformed
    void load_dari_nama_barang() {
        int selectedRow = tbl_mutasi_kode.getSelectedRow();
        String nama_awal = String.valueOf(comTableBarang.getSelectedItem());
        String[] split = new String[2];
        System.out.println("nilai comTable barang adalah " + comTableBarang.getSelectedItem());
        if (comTableBarang.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }
        try {
            String sql = "select kode_barang,nama_barang from barang where kode_barang = '" + split[0] + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString("kode_barang");
                String nama = res.getString("nama_barang");
                this.kode_barang = Integer.parseInt(kode);
                loadNumberTable();
                loadComTableSatuan();
                String konv = comTableKonv.getSelectedItem().toString();
                String kode_strip = kode + "-" + nama;
                if (selectedRow != -1) {
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
                    tbl_mutasi_kode.setValueAt(kode, selectedRow, 1);
                    tbl_mutasi_kode.setValueAt(nama, selectedRow, 2);
                }
            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }
    private void comTableBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableBarangActionPerformed
        load_dari_nama_barang();

//        int baris = tbl_mutasi_kode.getRowCount();
//        TableModel tabelModel;
//        tabelModel = tbl_mutasi_kode.getModel();
//        int selectedRow = tbl_mutasi_kode.getSelectedRow();
//
//        try {
//            String sql = "select * from barang where kode_barang = '" + comTableBarang.getSelectedItem() + "'";
//
////            String sql = "select * from barang where kode_barang = '" + kata[0] + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
//                this.kode_barang = Integer.parseInt(res.getString("kode_barang"));
//                String kode = res.getString(1);
//                String brg = res.getString(4);
//                loadComTableSatuan();
//                String konv = comTableKonv.getItemAt(0).toString();
//
//                if (selectedRow != -1) {
//                    tbl_mutasi_kode.setValueAt(konv, selectedRow, 3);
////                    tbl_mutasi_kode.setValueAt((kode+"-"+brg), selectedRow, 2);
//                    tbl_mutasi_kode.setValueAt(brg, selectedRow, 2);
//                    tbl_mutasi_kode.setValueAt(kode, selectedRow, 1);
//
//                }
//            }
//            res.close();
//            conn.close();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//            e.printStackTrace();
//        }

    }//GEN-LAST:event_comTableBarangActionPerformed

    private void tbl_mutasi_kodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_mutasi_kodeKeyPressed
        DefaultTableModel model = (DefaultTableModel) tbl_mutasi_kode.getModel();
        int selectedRow = tbl_mutasi_kode.getSelectedRow();
        int baris = tbl_mutasi_kode.getRowCount();

        TableModel tabelModel;
        tabelModel = tbl_mutasi_kode.getModel();
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter
                if (tbl_mutasi_kode.getRowCount() < 2) {
                    if (tabelModel.getValueAt(tbl_mutasi_kode.getSelectedRow(), 1).toString().equals("")
                            || tabelModel.getValueAt(tbl_mutasi_kode.getSelectedRow(), 2).toString().equals("")
                            || tabelModel.getValueAt(tbl_mutasi_kode.getSelectedRow(), 3).toString().equals("")
                            || tabelModel.getValueAt(tbl_mutasi_kode.getSelectedRow(), 4).toString().equals("")) {
                        throw new NullPointerException();
                    } else {
                        if(tbl_mutasi_kode.getSelectedColumn() == 4){
                            model.addRow(new Object[]{"", "", "", "", ""});
                        }
                    }
                }
                loadNumberTable();
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Seluruh data harus diisi");
        }
        if (evt.getKeyCode() == KeyEvent.VK_1 && tbl_mutasi_kode.getSelectedColumn() == 3) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_mutasi_kode.getValueAt(tbl_mutasi_kode.getSelectedRow(), 1));
            try {
                String sql = "select nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '1' and bk.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String sat = res.getString("nama_konversi");
                    String sat2 = sat;
                    tbl_mutasi_kode.setValueAt(sat2, selectedRow, 3);
                    System.out.println(sat2);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_2 && tbl_mutasi_kode.getSelectedColumn() == 3) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_mutasi_kode.getValueAt(tbl_mutasi_kode.getSelectedRow(), 1));
            try {
                String sql = "select nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '2' and bk.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String sat = res.getString("nama_konversi");
                    String sat2 = sat;
                    tbl_mutasi_kode.setValueAt(sat2, selectedRow, 3);
                    System.out.println(sat2);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_3 && tbl_mutasi_kode.getSelectedColumn() == 3) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_mutasi_kode.getValueAt(tbl_mutasi_kode.getSelectedRow(), 1));
            try {
                String sql = "select nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '3' and bk.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String sat = res.getString("nama_konversi");
                    String sat2 = sat;
                    tbl_mutasi_kode.setValueAt(sat2, selectedRow, 3);
                    System.out.println(sat2);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (tbl_mutasi_kode.getSelectedColumn() == 1 || tbl_mutasi_kode.getSelectedColumn() == 2)) {
            InputMap im = tbl_mutasi_kode.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
            KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
            im.put(down, im.get(f2));
            System.out.println("asd");

        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (tbl_mutasi_kode.getSelectedColumn() == 0 || tbl_mutasi_kode.getSelectedColumn() == 3 || tbl_mutasi_kode.getSelectedColumn() == 4)) {
            InputMap im = tbl_mutasi_kode.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
            KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
            im.put(f2, null);
            im.put(down, null);
            System.out.println("fgh");
        }

    }//GEN-LAST:event_tbl_mutasi_kodeKeyPressed

    private void comTableKonvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableKonvActionPerformed
        DefaultTableModel model = (DefaultTableModel) tbl_mutasi_kode.getModel();
        int baris = tbl_mutasi_kode.getRowCount();
        TableModel tabelModel;
        tabelModel = tbl_mutasi_kode.getModel();
        int selectedRow = tbl_mutasi_kode.getSelectedRow();
        try {
            String sql = "select nama_konversi, jumlah_konversi, identitas_konversi from barang_konversi bk, konversi k where bk.kode_konversi = k.kode_konversi and bk.kode_barang ='" + kode_barang + "'";
            System.out.println("sql: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            String Konv = "";
            comTableKonv.removeAllItems();
            while (res.next()) {
                Konv = res.getString(1);
                comTableKonv.addItem(Konv);
                System.out.println("Konv =" + Konv);
                System.out.println("y1 " + comTableKonv.getSelectedIndex() + " a: " + res.getInt(3));
                if (selectedRow != -1) {
                    tbl_mutasi_kode.setValueAt(Konv, selectedRow, 3);
                }
            }
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Pilih Barang dan Harga Terlebih Dahulu !");
        }

    }//GEN-LAST:event_comTableKonvActionPerformed

    private void tabel_clearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_clearMouseClicked
        bersihField();
    }//GEN-LAST:event_tabel_clearMouseClicked

    private void LabelSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelSaveMouseClicked
        simpanData();
    }//GEN-LAST:event_LabelSaveMouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F2) { //saat menekan f2   
            simpanData();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F9) { //saat menekan f2   
            bersihField();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F5) { //saat menekan f5
            hapusMutasi();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) { //saat menekan f5
            exitForm();
        }
    }//GEN-LAST:event_formKeyPressed

    private void labelExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelExitMouseClicked
        exitForm();
    }//GEN-LAST:event_labelExitMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        exitForm();
    }//GEN-LAST:event_formWindowClosed

    void loadComboKode(String key) {
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
                    comKodeBarang.setModel(new DefaultComboBoxModel(kode_nama_arr.toArray()));
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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiAntarKode_DetailNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiAntarKode_DetailNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiAntarKode_DetailNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiAntarKode_DetailNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Master_MutasiAntarKode_DetailNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelSave;
    private javax.swing.JComboBox<String> comKodeBarang;
    private javax.swing.JComboBox<String> comTableBarang;
    private javax.swing.JComboBox<String> comTableKonv;
    private javax.swing.JComboBox<String> comTableLokasi;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel labelDelete;
    private javax.swing.JLabel labelExit;
    private javax.swing.JLabel tabel_clear;
    private javax.swing.JTable tbl_mutasi_kode;
    private javax.swing.JTextField tgl_now;
    private javax.swing.JTextField txt_ket;
    private javax.swing.JTextField txt_no_bukti;
    private javax.swing.JTextField txt_qty;
    // End of variables declaration//GEN-END:variables
}
