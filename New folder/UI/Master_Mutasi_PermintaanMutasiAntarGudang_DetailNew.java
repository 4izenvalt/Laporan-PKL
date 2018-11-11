/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javax.swing.JOptionPane;
import Class.Koneksi;
import java.sql.Connection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import com.sun.glass.events.KeyEvent;
import java.awt.Window;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JComboBox;
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
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

/**
 *
 * @author USER
 */
public class Master_Mutasi_PermintaanMutasiAntarGudang_DetailNew extends javax.swing.JFrame {

    /**
     * Creates new form Penjualan_penjualan
     */
    int lokasipeminta = 0, lokasipenyedia = 0, jumlah_item = 0, kodebarang = 1, peminta, penyedia;
    double jumlah_qty = 0;
    String lokasi_deactived, lokasi_tmp = "PUSAT", kodekonversi;
    String[] barang;

    ArrayList<String> kode_nama_arr = new ArrayList();
    private static int item = 0;
    private boolean tampil = true;
    String status;

    Master_Mutasi_PermintaanMutasiAntarGudang awal;

    public Master_Mutasi_PermintaanMutasiAntarGudang_DetailNew() {

    }

    public Master_Mutasi_PermintaanMutasiAntarGudang_DetailNew(Master_Mutasi_PermintaanMutasiAntarGudang awal, String status) {
        initComponents();
        this.awal = awal;
        this.status = status;
        this.setLocationRelativeTo(null);

        lebarKolom();
        tanggal_jam_sekarang();
        loadNumberTable();
        autonumber();
        autonumberPermintaan();
        seleksilokasipeminta();
        seleksilokasipenyedia();
        System.out.println(lokasipeminta+" -------- "+lokasipenyedia);
        peminta = comTableLokasiPeminta.getSelectedIndex();
        penyedia = comTableLokasiPenyedia.getSelectedIndex();
        

        //JCombobox kode barang
        ((JTextComponent) comTableKode.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboKode(((JTextComponent) comTableKode.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
                            tbl_Permintaan_MutasiAG_new.editCellAt(tbl_Permintaan_MutasiAG_new.getSelectedRow(), 1);
                            comTableKode.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comTableKode.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comTableKode.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                //((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");

            }

        });
        ((JTextComponent) comTableKode.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
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
                            tbl_Permintaan_MutasiAG_new.editCellAt(tbl_Permintaan_MutasiAG_new.getSelectedRow(), 2);
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

    void cmbSelectInput(JComboBox combo, String input) {
        int cmb_count = combo.getItemCount() - 1;
        int inputlen = combo.getSelectedItem().toString().length() - 1;
        String combo_item = null;
        for (int i = 0; i < cmb_count; i++) {
            combo_item = combo.getItemAt(i).toString().substring(0, inputlen);
            if (input.equals(combo_item)) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }

    void showQTY() {
        try {
            jumlah_item = tbl_Permintaan_MutasiAG_new.getRowCount();
            int qty, jumqty = 0;
            System.out.println("row count : " + jumlah_item);
            for (int j = 0; j < tbl_Permintaan_MutasiAG_new.getRowCount();) {
                if (tbl_Permintaan_MutasiAG_new.getValueAt(j, 4) != "") {
                    qty = Integer.parseInt(tbl_Permintaan_MutasiAG_new.getValueAt(j, 4).toString());
                    jumqty += qty;
                }
                j++;
            }
            jumlah_qty = jumqty;
            txtJumItem.setText("Jumlah Item : " + jumlah_item);
            txtJumQty.setText("Jumlah Qty : " + String.valueOf(jumlah_qty));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data tidak boleh kosong" + e);
            e.printStackTrace();
        }

    }
    /*
     void showQTY() {
     try {
     jumlah_item = tbl_Permintaan_MutasiAG_new.getRowCount();
     int qty, jumqty = 0;
     System.out.println("row count : " + jumlah_item);
     for (int j = 0; j < jumlah_item;) {
     if (tbl_Permintaan_MutasiAG_new.getValueAt(j, 4) != "") {
     qty = Integer.parseInt(tbl_Permintaan_MutasiAG_new.getValueAt(j, 4).toString());
     System.out.println("j adalah : " + j);
     jumqty += qty;
     }
     j++;
     }
     jumlah_qty = jumqty;
     txtJumItem.setText("Jumlah Item : " + jumlah_item);
     txtJumQty.setText("Jumlah Qty : " + String.valueOf(jumqty));

     } catch (Exception e) {
     JOptionPane.showMessageDialog(null, "Eror" + e);
     e.printStackTrace();
     }

     }
     */

    public void lebarKolom() {
        TableColumn column;
        tbl_Permintaan_MutasiAG_new.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tbl_Permintaan_MutasiAG_new.getColumnModel().getColumn(0);
        column.setPreferredWidth(50);
        column = tbl_Permintaan_MutasiAG_new.getColumnModel().getColumn(1);
        column.setPreferredWidth(280);
        column = tbl_Permintaan_MutasiAG_new.getColumnModel().getColumn(2);
        column.setPreferredWidth(280);
        column = tbl_Permintaan_MutasiAG_new.getColumnModel().getColumn(3);
        column.setPreferredWidth(80);
        column = tbl_Permintaan_MutasiAG_new.getColumnModel().getColumn(4);
        column.setPreferredWidth(80);
        column = tbl_Permintaan_MutasiAG_new.getColumnModel().getColumn(5);
        column.setPreferredWidth(80);
    }

    void seleksilokasipeminta() {
        if (comTableLokasiPeminta.getSelectedItem() == "PUSAT") {
            lokasipeminta = 1;
        } else if (comTableLokasiPeminta.getSelectedItem() == "GUD63") {
            lokasipeminta = 2;
        } else if (comTableLokasiPeminta.getSelectedItem() == "TOKO") {
            lokasipeminta = 4;
        } else if (comTableLokasiPeminta.getSelectedItem() == "TENGAH") {
            lokasipeminta = 5;
        } else if (comTableLokasiPeminta.getSelectedItem() == "UTARA") {
            lokasipeminta = 6;
        }
    }

    void seleksilokasipenyedia() {
        if (comTableLokasiPenyedia.getSelectedItem() == "PUSAT") {
            lokasipenyedia = 1;
        } else if (comTableLokasiPenyedia.getSelectedItem() == "GUD63") {
            lokasipenyedia = 2;
        } else if (comTableLokasiPenyedia.getSelectedItem() == "TOKO") {
            lokasipenyedia = 4;
        } else if (comTableLokasiPenyedia.getSelectedItem() == "TENGAH") {
            lokasipenyedia = 5;
        } else if (comTableLokasiPenyedia.getSelectedItem() == "UTARA") {
            lokasipenyedia = 6;
        }
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

    public void tanggal_jam_sekarang() {
        Thread p = new Thread() {
            public void run() {
                for (;;) {
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
                    txt_tgl.setText(timeStamp);
                }
            }
        };
        System.out.println(txt_tgl.getText());
        p.start();
    }

    void loadNumberTable() {
        int baris = tbl_Permintaan_MutasiAG_new.getRowCount();
        for (int a = 0; a < baris; a++) {
            String nomor = String.valueOf(a + 1);
            tbl_Permintaan_MutasiAG_new.setValueAt(nomor + ".", a, 0);
        }

    }

    void loadComTableBarang() {
        try {
            String sql = "select b.kode_barang, b.nama_barang from barang b, barang_lokasi bl "
                    + "where b.kode_barang = bl.kode_barang "
                    + "and bl.kode_lokasi = '" + lokasipenyedia
                    + "' order by nama_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            String index = null;
            int i = 0;
            while (res.next()) {
                String kode = res.getString(1);
                String name = res.getString(2);
                comTableBarang.addItem(kode + " -- " + name);

            }
            String kalimat = comTableKode.getItemAt(0).toString();
            String[] kata = kalimat.split(" -- ");
            tbl_Permintaan_MutasiAG_new.setValueAt(kata[1], 0, 2);

            System.out.println("barang lokasi " + lokasipenyedia);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadComTableKode() {
        try {
            String sql = "select * from barang order by nama_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            String index1 = null;
            while (res.next()) {
                String kode = res.getString("kode_barang");
                String name = res.getString("nama_barang");
                comTableKode.addItem(kode + " -- " + name);

            }
            String kalimat = comTableKode.getItemAt(0).toString();
            String[] kata = kalimat.split(" -- ");
            System.out.println("index kode" + index1);
            tbl_Permintaan_MutasiAG_new.setValueAt(kata[0], 0, 1);
            System.out.println("kode barang" + lokasipenyedia);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadComTableSatuan() {
        try {
            /// identitas konversi tidak urut
            String sql = "select * from konversi k, barang_konversi bk, barang b "
                    + "where b.kode_barang = bk.kode_barang "
                    + "and bk.kode_konversi = k.kode_konversi "
                    + "and b.kode_barang = '" + kodebarang + "' order by bk.kode_barang_konversi asc";
            System.out.println("sts: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            comTableKonv.removeAllItems();
            int i = 1;
            while (res.next()) {
                String name = res.getString("nama_konversi");
//                if (res.getString("identitas_konversi").toString().equalsIgnoreCase("1")) {
//                    System.out.println("ya: " + name);
//                    comTableKonv.setSelectedItem(name);
//                }
                System.out.println(name);
                comTableKonv.addItem(name);
                i++;

            }
            //comTableKonv.setSelectedIndex(0);
            res.close();
            conn.close();
//            comKodeKonv.addItem(name);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

    void loadTxtTableQty() {
        try {
            String sql = "select sum(jumlah) from barang_lokasi bl "
                    + "where bl.kode_lokasi =  '" + lokasipenyedia
                    + "' and bl.kode_barang = '" + comTableKode.getSelectedItem() + "'";
            System.out.println("sts: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            txt_Qty.setText(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

    void load_dari_nama_barang() {
        int selectedRow = tbl_Permintaan_MutasiAG_new.getSelectedRow();
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
                loadNumberTable();
                if (selectedRow != -1) {
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
                    this.kodebarang = Integer.parseInt(kode);
                    loadComTableSatuan();
                    tbl_Permintaan_MutasiAG_new.setValueAt(comTableKonv.getItemAt(0), selectedRow, 3);
                    tbl_Permintaan_MutasiAG_new.setValueAt(kode, selectedRow, 1);
                    tbl_Permintaan_MutasiAG_new.setValueAt(nama, selectedRow, 2);
                    tbl_Permintaan_MutasiAG_new.setValueAt(0, selectedRow, 4);

                }
            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

    private void removeSelectedRows(JTable table) {
        int Hapus = 1;
        Hapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin mau menghapus baris ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if (Hapus == 0) {
            DefaultTableModel model = (DefaultTableModel) this.tbl_Permintaan_MutasiAG_new.getModel();
            int[] rows = table.getSelectedRows();
            for (int i = 0; i < rows.length; i++) {
                model.removeRow(rows[i] - i);
            }
        }
    }

    public void autonumber() {
        try {
            String lastNo = "";
            String sql = "select max(no_bukti) from mutasi_antar_gudang ORDER BY no_bukti DESC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next() && res != null) {
                if (res.first() == false) {
                    txt_noBukti.setText("MG");
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
                    txt_noBukti.setText(String.valueOf(huruf + "" + angkapad));
                }
            }
            res.close();
        } catch (NullPointerException ex) {
            txt_noBukti.setText("MG18-00001");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void autonumberPermintaan() {
        try {
            String lastNo = "";
            String sql = "select max(no_permintaan) from mutasi_antar_gudang where no_permintaan!='-'ORDER BY no_permintaan DESC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next() && res != null) {
                if (res.first() == false) {
                    txt_noPermintaan.setText("PM");
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
                    txt_noPermintaan.setText(String.valueOf(huruf + "" + angkapad));
                }
            }
            res.close();
        } catch (NullPointerException ex) {
            txt_noPermintaan.setText("PM18-00001");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }

    void ClearData() {
        DefaultTableModel dm = (DefaultTableModel) tbl_Permintaan_MutasiAG_new.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        dm.addRow(new Object[]{"", "", "", "", "", ""});
        autonumber();
        autonumberPermintaan();
    }

    public void SaveData() {
        try {
            Koneksi Koneksi = new Koneksi();
            Connection con = Koneksi.configDB();
            Statement st = con.createStatement();
            int row = 0;
            int jumlah_data = tbl_Permintaan_MutasiAG_new.getRowCount();
            System.out.println(jumlah_data);
            int jumlah_item = 0;
            double jumlah_qty = 0;
            boolean cukup = true;
            String kode_barang = "";
            String kode_konversi = "";
            String status = null;
            //Memasukkan data perbaris ke tabel mutasi detail
            for (int i = 0; i < jumlah_data; i++) {

                //Konversi satuan
                String satuan = String.valueOf(tbl_Permintaan_MutasiAG_new.getValueAt(i, 3));
                String sql1 = "select kode_konversi from barang_konversi where kode_konversi = (select kode_konversi from konversi where nama_konversi='" + satuan + "') and kode_barang = '" + tbl_Permintaan_MutasiAG_new.getValueAt(i, 1) + "'";
                System.out.println(sql1);
                Statement st1 = con.createStatement();
                java.sql.ResultSet res1 = st1.executeQuery(sql1);
                while (res1.next()) {
                    kode_konversi = res1.getString(1);
                }
                res1.close();
                System.out.println("satuan" + satuan);

                //Cek ketersediaan barang di gudang tertentu
                if (i == 0) {
                    for (int j = 0; j < jumlah_data; j++) {
                        //Konversi lokasi cek
                            String sql3a = "select kode_lokasi from lokasi where nama_lokasi = '" + comTableLokasiPenyedia.getSelectedItem().toString() + "'";
                            System.out.println(sql3a);
                            Statement st3a = con.createStatement();
                            java.sql.ResultSet res3a = st3a.executeQuery(sql3a);
                            String lokasi_cek = "";
                            while (res3a.next()) {
                                lokasi_cek = res3a.getString(1);
                            }
                            res3a.close();

                        //Konversi satuan cek
                            String satuan_cek = String.valueOf(tbl_Permintaan_MutasiAG_new.getValueAt(j, 3));
                            String sql1a = "select kode_konversi from barang_konversi where kode_konversi = (select kode_konversi from konversi where nama_konversi='" + satuan_cek + "') and kode_barang = '" + tbl_Permintaan_MutasiAG_new.getValueAt(i, 1) + "'";
                            System.out.println(sql1a);
                            Statement st1a = con.createStatement();
                            java.sql.ResultSet res1a = st1a.executeQuery(sql1a);
                            String kode_konversi_cek = "";
                            while (res1a.next()) {
                                kode_konversi_cek = res1a.getString(1);
                            }
                            res1a.close();

                        double qty = Double.parseDouble(String.valueOf(tbl_Permintaan_MutasiAG_new.getValueAt(j, 4)));

                        //qty dikali jumlah_konversi
                        double qty_sesudah = qty_konversi(String.valueOf(tbl_Permintaan_MutasiAG_new.getValueAt(j, 1)), kode_konversi_cek, qty);
                        System.out.println("qty_sesudah : " + qty_sesudah +" - "+ kode_konversi_cek+" - "+qty);
                        
                        String sql4 = "select * from barang_lokasi where kode_barang ='" + String.valueOf(tbl_Permintaan_MutasiAG_new.getValueAt(j, 1)) + "' and kode_lokasi = '" + lokasi_cek + "'";
                        System.out.println(sql4);
                        java.sql.Statement stm4 = con.createStatement();
                        java.sql.ResultSet res4 = stm4.executeQuery(sql4);
                        System.out.println("qty_sesduah " + qty_sesudah);

                        while (res4.next()) {
                            int stok = Integer.parseInt(res4.getString("jumlah"));
                            System.out.println("stok " + stok);
                            kode_barang = String.valueOf(tbl_Permintaan_MutasiAG_new.getValueAt(j, 1));
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
                if (!cukup) {
                    //keluar for untuk input semua baris
                    break;
                } else {
                    //Input kode_konversi
                    int kode = 0;
                    String sqlkode = "select kode_konversi from konversi WHERE nama_konversi = '" + tbl_Permintaan_MutasiAG_new.getValueAt(i, 3) + "'";
                    System.out.println(sqlkode);
                    Statement statkode = con.createStatement();
                    java.sql.ResultSet reskode = statkode.executeQuery(sqlkode);
                    while (reskode.next()) {
                        kode = Integer.parseInt(reskode.getString("kode_konversi"));
                    }
                    reskode.close();

                    //Memasukkan baris ke-i
                    String kalimat = tbl_Permintaan_MutasiAG_new.getValueAt(i, 1).toString();
                    String[] kata = kalimat.split(" -- ");
                    double jumlah = Double.parseDouble(String.valueOf(tbl_Permintaan_MutasiAG_new.getValueAt(i, 4)));
                    double qty_sesudah = qty_konversi(String.valueOf(tbl_Permintaan_MutasiAG_new.getValueAt(i, 1)), kode_konversi, jumlah);
                    String sql = "insert into mutasi_antar_gudang_detail( no_bukti, kode_barang, nama_barang, kode_lokasi_asal, satuan, qty, dikirim, kode_lokasi_tujuan)"
                            + "value('" + txt_noBukti.getText() + "','" + tbl_Permintaan_MutasiAG_new.getValueAt(i, 1) + "','" + tbl_Permintaan_MutasiAG_new.getValueAt(i, 2) + "','" + lokasipenyedia + "','" + kode + "','" + tbl_Permintaan_MutasiAG_new.getValueAt(i, 4) + "','" + tbl_Permintaan_MutasiAG_new.getValueAt(i, 5) + "','" + lokasipeminta + "');";
                    System.out.println("insert detail " + i);
                    System.out.println(sql);
                    row = st.executeUpdate(sql);
                    if (!tbl_Permintaan_MutasiAG_new.getValueAt(i, 4).toString().equals(tbl_Permintaan_MutasiAG_new.getValueAt(i, 5).toString())) {
                        status = "ON PROGRESS";
                    } else {
                        status = "OPEN";
                    }
                    System.out.println(status);

                    //mengupdate stok dimasing-masing gudang
                    String mengurangi = "update barang_lokasi set jumlah= jumlah - " + qty_sesudah + " where kode_barang = '" + tbl_Permintaan_MutasiAG_new.getValueAt(i, 1) + "' and kode_lokasi = '" + lokasipenyedia + "'";
                    System.out.println(mengurangi);
                    Statement st_kurang = con.createStatement();
                    row = st_kurang.executeUpdate(mengurangi);

                    String menambah = "update barang_lokasi set jumlah= jumlah + " + qty_sesudah + " where kode_barang = '" + tbl_Permintaan_MutasiAG_new.getValueAt(i, 1) + "' and kode_lokasi = '" + lokasipeminta + "'";
                    System.out.println(menambah);
                    Statement st_tambah = con.createStatement();
                    row = st_tambah.executeUpdate(menambah);

                    jumlah_item++;
                    jumlah_qty += jumlah; //jumlah qty pada form
                }
            }

            //Memasukkan data ke mutasi gudang umum
            if (cukup) {
                //String date = txt_tgl.getText();
                System.out.println("status = " + status);
                SimpleDateFormat format_tanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = format_tanggal.format(System.currentTimeMillis());
                String sql = "insert into mutasi_antar_gudang( no_bukti, no_permintaan, user, keterangan, jumlah_item, jumlah_qty, status, tanggal)"
                        + "value('" + txt_noBukti.getText() + "','" + txt_noPermintaan.getText() + "','" + 3 + "','" + txt_Keterangan.getText() + "','" + jumlah_item + "','" + jumlah_qty + "','" + status + "','" + date + "');";
                System.out.println(sql);
                row = st.executeUpdate(sql);
            } else {
                JOptionPane.showMessageDialog(null, "Stok barang " + kode_barang + " di gudang " + lokasipenyedia + " tidak cukup");
            }
            if (row >= 1) {
                JOptionPane.showMessageDialog(null, "data sudah ditambahkan ke database", "informasi", JOptionPane.INFORMATION_MESSAGE);
                con.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "data tidak dimasukkan ke database" + e, "informasi", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Tidak ada data yang dimasukkan");
        }
    }

    double qty_konversi(String kode_barang, String kode_konversi, double qty) {
        double hasil = 0;
        try {
            String sql = "select * from barang_konversi where kode_barang='" + kode_barang + "' and kode_konversi ='" + kode_konversi + "'";
            System.out.println("Kode konversi " + kode_konversi);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            double jumlah_konversi = 0;
            while (res.next()) {
                jumlah_konversi = Double.parseDouble(res.getString("jumlah_konversi"));
            }
            hasil = jumlah_konversi * qty;
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
        return hasil;
    }

    public static String rightPadZeros(String str, int num) {
        return String.format("%05d", Integer.parseInt(str));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comTableBarang = new javax.swing.JComboBox();
        txt_Qty = new javax.swing.JTextField();
        comTableKode = new javax.swing.JComboBox();
        txt_noPermintaan = new javax.swing.JTextField();
        comTableKonv = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JSeparator();
        LabelSave = new javax.swing.JLabel();
        label_Clear = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        label_Exit = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        txtJumItem = new javax.swing.JTextField();
        txtJumQty = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        txt_Keterangan = new javax.swing.JTextField();
        txt_tgl = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        comTableLokasiPeminta = new javax.swing.JComboBox<>();
        comTableLokasiPenyedia = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txt_noBukti = new javax.swing.JTextField();
        jScrollPane17 = new javax.swing.JScrollPane();
        tbl_Permintaan_MutasiAG_new = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        comTableBarang.setEditable(true);
        comTableBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableBarangActionPerformed(evt);
            }
        });
        comTableBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comTableBarangKeyPressed(evt);
            }
        });

        txt_Qty.setText("0");

        comTableKode.setEditable(true);
        comTableKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableKodeActionPerformed(evt);
            }
        });

        txt_noPermintaan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_noPermintaan.setEnabled(false);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));

        LabelSave.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        LabelSave.setText("F12 - Save");
        LabelSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelSaveMouseClicked(evt);
            }
        });

        label_Clear.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        label_Clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Clear-icon.png"))); // NOI18N
        label_Clear.setText("F9 - Clear");
        label_Clear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_ClearMouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_yast_printer_30297.png"))); // NOI18N
        jLabel21.setText("Print");
        jLabel21.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel21AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        label_Exit.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        label_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        label_Exit.setText("Esc - Exit");
        label_Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_ExitMouseClicked(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Kasir");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Nama Kasir");

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));

        txtJumItem.setBackground(new java.awt.Color(0, 0, 0));
        txtJumItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtJumItem.setForeground(new java.awt.Color(255, 204, 0));
        txtJumItem.setText("Jumlah Item");
        txtJumItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txtJumItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtJumItemMouseClicked(evt);
            }
        });
        txtJumItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumItemActionPerformed(evt);
            }
        });

        txtJumQty.setBackground(new java.awt.Color(0, 0, 0));
        txtJumQty.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtJumQty.setForeground(new java.awt.Color(255, 204, 0));
        txtJumQty.setText("Jumlah Qty");
        txtJumQty.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txtJumQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtJumQtyMouseClicked(evt);
            }
        });

        jSeparator4.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator7.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator9.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        txt_Keterangan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        txt_tgl.setEditable(false);
        txt_tgl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel23.setText("Lokasi Peminta");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel24.setText("Lokasi Penyedia");

        comTableLokasiPeminta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PUSAT", "GUD63", "TOKO", "TENGAH", "UTARA" }));
        comTableLokasiPeminta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableLokasiPemintaActionPerformed(evt);
            }
        });

        comTableLokasiPenyedia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PUSAT", "GUD63", "TOKO", "TENGAH", "UTARA" }));
        comTableLokasiPenyedia.setSelectedIndex(1);
        comTableLokasiPenyedia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableLokasiPenyediaActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel25.setText("Tanggal");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel27.setText("Keterangan");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel28.setText("No.Bukti");

        txt_noBukti.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_noBukti.setEnabled(false);

        tbl_Permintaan_MutasiAG_new.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode", "Nama", "Satuan", "Qty", "Dikirim"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Permintaan_MutasiAG_new.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_Permintaan_MutasiAG_newMouseClicked(evt);
            }
        });
        tbl_Permintaan_MutasiAG_new.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_Permintaan_MutasiAG_newKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_Permintaan_MutasiAG_newKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tbl_Permintaan_MutasiAG_newKeyTyped(evt);
            }
        });
        jScrollPane17.setViewportView(tbl_Permintaan_MutasiAG_new);
        if (tbl_Permintaan_MutasiAG_new.getColumnModel().getColumnCount() > 0) {
            tbl_Permintaan_MutasiAG_new.getColumnModel().getColumn(1).setCellEditor(new ComboBoxCellEditor(comTableKode));
            tbl_Permintaan_MutasiAG_new.getColumnModel().getColumn(2).setCellEditor(new ComboBoxCellEditor(comTableBarang));
            tbl_Permintaan_MutasiAG_new.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(txt_Qty));
        }

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addComponent(jSeparator2))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel32))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                        .addComponent(txtJumItem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtJumQty, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(161, 161, 161))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane17)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel27))
                                .addGap(68, 68, 68)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_tgl, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                            .addComponent(comTableLokasiPeminta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(comTableLokasiPenyedia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel28)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_noBukti, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_Keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label_Clear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label_Exit)))
                        .addContainerGap(317, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelSave)
                    .addComponent(label_Exit)
                    .addComponent(jLabel21)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_Clear)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(comTableLokasiPeminta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(comTableLokasiPenyedia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(txt_noBukti, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtJumItem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtJumQty, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtJumItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtJumItemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumItemMouseClicked

    private void txtJumQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtJumQtyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumQtyMouseClicked

    private void jLabel21AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel21AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel21AncestorAdded

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked

    }//GEN-LAST:event_jLabel21MouseClicked

    private void txtJumItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumItemActionPerformed

    private void comTableBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableBarangActionPerformed
        load_dari_nama_barang();
//        tbl_Permintaan_MutasiAG_new.setValueAt(kodekonversi, tbl_Permintaan_MutasiAG_new.getSelectedRow(), 3);

//        int kode_barang = 0;
//        int baris = tbl_Permintaan_MutasiAG_new.getRowCount();
//        TableModel tabelModel;
//        tabelModel = tbl_Permintaan_MutasiAG_new.getModel();
//        int selectedRow = tbl_Permintaan_MutasiAG_new.getSelectedRow();
//        
//        
//        
//        try {
//            /*
//            String kalimat = comTableKode.getSelectedItem().toString();
//            String[] kata = kalimat.split(" -- ");
//            for (int i = 0; i < kata.length; i++) {
//                System.out.println(kata[i]);
//            }
//            tbl_Permintaan_MutasiAG_new.setValueAt(kata[0], 0, 1);
//            tbl_Permintaan_MutasiAG_new.setValueAt(kata[1], 0, 2);
//            */
//            
//            String sql = "select * from barang where kode_barang = '" + comTableBarang.getSelectedItem().toString() + "'";
//            System.out.println(comTableBarang.getSelectedItem());
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
//                String kode = res.getString(1);
//                String brg = res.getString(4);
//                //      loadComTableSatuan();
//                //      String konv = comTableKonv.getSelectedItem().toString();
//
//                if (selectedRow != -1) {
//                    tbl_Permintaan_MutasiAG_new.setValueAt(brg, selectedRow, 2);
//                    tbl_Permintaan_MutasiAG_new.setValueAt(kode , selectedRow, 1);
//
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//            e.printStackTrace();
//        }
//
//        try {
//            String sql = "select k.nama_konversi, k.kode_konversi from konversi k, barang_konversi bk where k.kode_konversi = bk.kode_konversi and bk.kode_barang = '" + kode_barang + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            String Konv = "";
//            while (res.next()) {
//                Konv = res.getString(1);
////         comTableKonv.addItem(Konv);
//                System.out.println("Konv =" + Konv);
//                //                System.out.println("echo : " + tbl_Pembelian.getValueAt(selectedRow, 2).toString());
////         tbl_Permintaan_MutasiAG_new.setValueAt(comTableKonv.getSelectedItem(), selectedRow, 2);
//
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
//            e.printStackTrace();
//        }

    }//GEN-LAST:event_comTableBarangActionPerformed

    private void tbl_Permintaan_MutasiAG_newKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_Permintaan_MutasiAG_newKeyPressed

        DefaultTableModel model = (DefaultTableModel) tbl_Permintaan_MutasiAG_new.getModel();
        int selectedRow = tbl_Permintaan_MutasiAG_new.getSelectedRow();
        System.out.println(selectedRow);
        //int selectedRow = tbl_tambah_mutasi.getSelectedRow();
        //int baris = tbl_tambah_mutasi.getRowCount();

        TableModel tabelModel;
        tabelModel = tbl_Permintaan_MutasiAG_new.getModel();
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter
                if (tabelModel.getValueAt(tbl_Permintaan_MutasiAG_new.getSelectedRow(), 4).toString().equals("")
                        || tabelModel.getValueAt(tbl_Permintaan_MutasiAG_new.getSelectedRow(), 3).toString().equals("")
                        || tabelModel.getValueAt(tbl_Permintaan_MutasiAG_new.getSelectedRow(), 2).toString().equals("")
                        || tabelModel.getValueAt(tbl_Permintaan_MutasiAG_new.getSelectedRow(), 1).toString().equals("")) {
                    throw new NullPointerException();
                } else {
                    if (tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 5) {
                        /*
                         System.out.println("Jumlah item = " + jumlah_item);
                         jumlah_item++;
                         int qty = 0, jumqty = 0;
                         txtJumItem.setText("Jumlah Item : " + jumlah_item);
                         for (int j = 0; j < jumlah_item; j++) {
                         jumqty += Integer.parseInt(tbl_Permintaan_MutasiAG_new.getValueAt(j, 4).toString());
                         }
                         jumlah_qty = jumqty;
                         txtJumQty.setText("Jumlah Qty : " + String.valueOf(jumqty));
                         */

                        model.addRow(new Object[]{"", "", "", "", "", ""});

                    }

                }
            } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                this.dispose();
                awal.setVisible(true);
                awal.setFocusable(true);
                System.out.println("status = " + status);
                awal.loadTable(status, "*");
            } else if (evt.getKeyCode() == KeyEvent.VK_F5) {
                if (tbl_Permintaan_MutasiAG_new.getRowCount() - 1 == -1) {
                    JOptionPane.showMessageDialog(null, "Data didalam tabel telah tiada.", "", 2);
                } else {
                    removeSelectedRows(tbl_Permintaan_MutasiAG_new);
                }

            } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                if (tbl_Permintaan_MutasiAG_new.getRowCount() - 1 == -1) {
                    JOptionPane.showMessageDialog(null, "Data didalam tabel telah tiada.", "", 2);
                    showQTY();
                } else {
                    removeSelectedRows(tbl_Permintaan_MutasiAG_new);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_F12) {
                int simpan_data = 1;
                simpan_data = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menyimpan data ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
                if (simpan_data == 0) {
                    SaveData();
                }

            } else if (evt.getKeyCode() == KeyEvent.VK_F9) {
                ClearData();
            } else if (evt.getKeyCode() == KeyEvent.VK_INSERT) {
                if (tbl_Permintaan_MutasiAG_new.getRowCount() - 1 == -1) {
                    model.addRow(new Object[]{"", "", "", "", "", ""});
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_1 && tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 3) {
                System.out.println("ini alt");
                String kode_barang = String.valueOf(tbl_Permintaan_MutasiAG_new.getValueAt(tbl_Permintaan_MutasiAG_new.getSelectedRow(), 1));
                try {
                    String sql = "select k.kode_konversi, nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '1' and bk.kode_barang = '" + kode_barang + "'";
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    int i = 0;
                    while (res.next()) {
                        String kode = res.getString("kode_konversi");
                        System.out.println("konversi " + kode);
                        String sat = res.getString("nama_konversi");
                        String sat2 = sat;
                        tbl_Permintaan_MutasiAG_new.setValueAt(sat2, tbl_Permintaan_MutasiAG_new.getSelectedRow(), 3);
                        System.out.println(sat2);
                        i++;
                    }
                    res.close();
                    conn.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_2 && tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 3) {
                System.out.println("ini alt");
                String kode_barang = String.valueOf(tbl_Permintaan_MutasiAG_new.getValueAt(tbl_Permintaan_MutasiAG_new.getSelectedRow(), 1));
                try {
                    String sql = "select k.kode_konversi, nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '2' and bk.kode_barang = '" + kode_barang + "'";
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    while (res.next()) {
                        String kode = res.getString("kode_konversi");
                        System.out.println("konversi " + kode);
                        String sat = res.getString("nama_konversi");
                        String sat2 = sat;
                        tbl_Permintaan_MutasiAG_new.setValueAt(sat2, tbl_Permintaan_MutasiAG_new.getSelectedRow(), 3);
                        System.out.println(sat2);
                    }
                    res.close();
                    conn.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_3 && tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 3) {
                System.out.println("ini alt");
                String kode_barang = String.valueOf(tbl_Permintaan_MutasiAG_new.getValueAt(tbl_Permintaan_MutasiAG_new.getSelectedRow(), 1));
                try {
                    String sql = "select k.kode_konversi, nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '3' and bk.kode_barang = '" + kode_barang + "'";
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    while (res.next()) {
                        String kode = res.getString("kode_konversi");
                        System.out.println("konversi " + kode);
                        String sat = res.getString("nama_konversi");
                        String sat2 = "2. " + sat;
                        tbl_Permintaan_MutasiAG_new.setValueAt(sat2, tbl_Permintaan_MutasiAG_new.getSelectedRow(), 3);
                        System.out.println(sat2);
                    }
                    res.close();
                    conn.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 1 || tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 2 || tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 3 || tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 6)) {
                InputMap im = tbl_Permintaan_MutasiAG_new.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
                KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
                KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
                im.put(down, im.get(f2));
                System.out.println("asd");

            } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 0 || tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 4 || tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 5)) {
                InputMap im = tbl_Permintaan_MutasiAG_new.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
                KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
                KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
                im.put(f2, null);
                im.put(down, null);
                System.out.println("fgh");
            }

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Data harus lengkap");

        }

        loadNumberTable();
    }//GEN-LAST:event_tbl_Permintaan_MutasiAG_newKeyPressed

    private void tbl_Permintaan_MutasiAG_newKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_Permintaan_MutasiAG_newKeyReleased
        DefaultTableModel model = (DefaultTableModel) tbl_Permintaan_MutasiAG_new.getModel();
        int selectedRow = tbl_Permintaan_MutasiAG_new.getSelectedRow();
        int baris = tbl_Permintaan_MutasiAG_new.getRowCount();
        TableModel tabelModel;
        tabelModel = tbl_Permintaan_MutasiAG_new.getModel();
        loadNumberTable();
        if (    (tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 0
                || tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 3
                || tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 4
                || tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 5) 
                && tbl_Permintaan_MutasiAG_new.getValueAt(tbl_Permintaan_MutasiAG_new.getSelectedRow(), 4) != "") {
             showQTY();
        }
    }//GEN-LAST:event_tbl_Permintaan_MutasiAG_newKeyReleased

    private void LabelSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelSaveMouseClicked
        SaveData();
    }//GEN-LAST:event_LabelSaveMouseClicked

    private void comTableLokasiPemintaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableLokasiPemintaActionPerformed
        if (comTableLokasiPeminta.getSelectedItem() == comTableLokasiPenyedia.getSelectedItem()) {
            JOptionPane.showMessageDialog(null, "Lokasi tidak boleh sama");
            comTableLokasiPeminta.setSelectedIndex(peminta);
        }
        else{
            seleksilokasipeminta();
        System.out.println("seleksi peminta : " + lokasipeminta);

        }
        
        /*
         lokasi_deactived = comTableLokasiPeminta.getSelectedItem().toString();
         comTableLokasiPenyedia.removeItem(lokasi_deactived);
         if (!comTableLokasiPeminta.getSelectedItem().toString().equals(lokasi_tmp)) {
         comTableLokasiPenyedia.addItem(lokasi_deactived);
         }
         */
    }//GEN-LAST:event_comTableLokasiPemintaActionPerformed

    private void comTableLokasiPenyediaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableLokasiPenyediaActionPerformed
        if ( comTableLokasiPenyedia.getSelectedItem() == comTableLokasiPeminta.getSelectedItem()) {
            JOptionPane.showMessageDialog(null, "Lokasi tidak boleh sama");
            comTableLokasiPenyedia.setSelectedIndex(penyedia);
        }else{
            seleksilokasipenyedia();
        System.out.println("seleksi penyedia : " + lokasipenyedia);
        }
        
        
        //loadComTableKode();
        //loadComTableBarang();

    }//GEN-LAST:event_comTableLokasiPenyediaActionPerformed

    private void label_ExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_ExitMouseClicked

        awal.setVisible(true);
        awal.setFocusable(true);
        awal.loadTable(status, "*");
        this.dispose();
    }//GEN-LAST:event_label_ExitMouseClicked

    private void label_ClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_ClearMouseClicked
        ClearData();
    }//GEN-LAST:event_label_ClearMouseClicked
    void load_dari_kode_barang() {
        int selectedRow = tbl_Permintaan_MutasiAG_new.getSelectedRow();
        String nama_awal = String.valueOf(comTableKode.getSelectedItem());
        String[] split = new String[2];
        System.out.println("nilai comTable barang adalah " + comTableKode.getSelectedItem());
        if (comTableKode.getSelectedItem() != null) {
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
                loadNumberTable();
                if (selectedRow != -1) {
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
                    this.kodebarang = Integer.parseInt(kode);
                    loadComTableSatuan();
                    tbl_Permintaan_MutasiAG_new.setValueAt(comTableKonv.getItemAt(0), selectedRow, 3);
                    tbl_Permintaan_MutasiAG_new.setValueAt(kode, selectedRow, 1);
                    tbl_Permintaan_MutasiAG_new.setValueAt(nama, selectedRow, 2);
                    tbl_Permintaan_MutasiAG_new.setValueAt(0, selectedRow, 4);

                }
            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }
    private void comTableKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableKodeActionPerformed
        load_dari_kode_barang();

//
//        int kode_barang = 0;
//        int baris = tbl_Permintaan_MutasiAG_new.getRowCount();
//        TableModel tabelModel;
//        tabelModel = tbl_Permintaan_MutasiAG_new.getModel();
//        int selectedRow = tbl_Permintaan_MutasiAG_new.getSelectedRow();
//
//        try {
//            /*
//            String kalimat = comTableKode.getSelectedItem().toString();
//            String[] kata = kalimat.split(" -- ");
//            for (int i = 0; i < kata.length; i++) {
//                System.out.println(kata[i]);
//            }
//            tbl_Permintaan_MutasiAG_new.setValueAt(kata[0], 0, 1);
//            tbl_Permintaan_MutasiAG_new.setValueAt(kata[1], 0, 2);
//            */
//            
//            String sql = "select * from barang where kode_barang = '" + comTableKode.getSelectedItem().toString() + "'";
//            System.out.println(comTableKode.getSelectedItem());
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
//                String kode = res.getString(1);
//                String brg = res.getString(4);
//                //      loadComTableSatuan();
//                //      String konv = comTableKonv.getSelectedItem().toString();
//
//                if (selectedRow != -1) {
//                    tbl_Permintaan_MutasiAG_new.setValueAt(brg, selectedRow, 2);
//                    tbl_Permintaan_MutasiAG_new.setValueAt(kode , selectedRow, 1);
//
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//            e.printStackTrace();
//        }
//
//        try {
//            String sql = "select k.nama_konversi, k.kode_konversi from konversi k, barang_konversi bk where k.kode_konversi = bk.kode_konversi and bk.kode_barang = '" + kode_barang + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            String Konv = "";
//            while (res.next()) {
//                Konv = res.getString(1);
////         comTableKonv.addItem(Konv);
//                System.out.println("Konv =" + Konv);
//                //                System.out.println("echo : " + tbl_Pembelian.getValueAt(selectedRow, 2).toString());
////         tbl_Permintaan_MutasiAG_new.setValueAt(comTableKonv.getSelectedItem(), selectedRow, 2);
//
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
//            e.printStackTrace();
//        }
//

    }//GEN-LAST:event_comTableKodeActionPerformed

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
                    comTableKode.setModel(new DefaultComboBoxModel(kode_nama_arr.toArray()));
                    ((JTextComponent) comTableKode.getEditor().getEditorComponent()).setText(key);
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


    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            awal.setVisible(true);
            awal.setFocusable(true);
            awal.loadTable(status, "*");
            this.dispose();
        }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased

    }//GEN-LAST:event_formKeyReleased

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated

    private void comTableBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comTableBarangKeyPressed


    }//GEN-LAST:event_comTableBarangKeyPressed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        awal.setVisible(true);
        awal.setFocusable(true);
        awal.loadTable(status, "*");
    }//GEN-LAST:event_formWindowClosed

    private void tbl_Permintaan_MutasiAG_newKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_Permintaan_MutasiAG_newKeyTyped
        if (tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 4
                || tbl_Permintaan_MutasiAG_new.getSelectedColumn() == 5) {
            char vChar = evt.getKeyChar();
            if (!(Character.isDigit(vChar)
                    || (vChar == java.awt.event.KeyEvent.VK_BACK_SPACE)
                    || (vChar == java.awt.event.KeyEvent.VK_DELETE))) {
                evt.consume();
            }
            
        }
    }//GEN-LAST:event_tbl_Permintaan_MutasiAG_newKeyTyped

    private void tbl_Permintaan_MutasiAG_newMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Permintaan_MutasiAG_newMouseClicked
        
    }//GEN-LAST:event_tbl_Permintaan_MutasiAG_newMouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        SaveData();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        ClearData();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

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
            java.util.logging.Logger.getLogger(Master_Mutasi_PermintaanMutasiAntarGudang_DetailNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Mutasi_PermintaanMutasiAntarGudang_DetailNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Mutasi_PermintaanMutasiAntarGudang_DetailNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Mutasi_PermintaanMutasiAntarGudang_DetailNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master_Mutasi_PermintaanMutasiAntarGudang_DetailNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelSave;
    private javax.swing.JComboBox comTableBarang;
    private javax.swing.JComboBox comTableKode;
    private javax.swing.JComboBox comTableKonv;
    private javax.swing.JComboBox<String> comTableLokasiPeminta;
    private javax.swing.JComboBox<String> comTableLokasiPenyedia;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel label_Clear;
    private javax.swing.JLabel label_Exit;
    private javax.swing.JTable tbl_Permintaan_MutasiAG_new;
    private javax.swing.JTextField txtJumItem;
    private javax.swing.JTextField txtJumQty;
    private javax.swing.JTextField txt_Keterangan;
    private javax.swing.JTextField txt_Qty;
    private javax.swing.JTextField txt_noBukti;
    private javax.swing.JTextField txt_noPermintaan;
    private javax.swing.JTextField txt_tgl;
    // End of variables declaration//GEN-END:variables
}
