/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Connect;
import Java.Currency_Column;
import com.sun.glass.events.KeyEvent;
import java.awt.Frame;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

/**
 *
 * @author heruby
 */
public class Penjualan_Penjualan extends javax.swing.JFrame {

    public String totalclone;
    public int Tempharga;
    public int subtotalfix = 0;
    int kode_barang = 1;
    String kov;
    ResultSet rs, rs1, rs2, rsx = null;
    Connect connection, connection1;
    private PreparedStatement PS, PSx;
    public int subtotal1 = 0, hargajadi1 = 0, totalqty = 0, total = 0; //penjumlahan
    public int hargaRekom = 0, harga = 0, Jharga = 0, subtotal = 0, hargajadi = 0;//panggil colom tabel
    public int qty = 0;
    public String tmpLokasi, tmpKonv, tmpKodeBarang, tmpKel;
    public int tmpIdTOP, tmpKodeLokasi, tmpKodeKonv, jumlah_item;
    public double tmpHJ1, tmpHJ2, tmpKonvPcs, tmppcs, jmlKonv, jumlahTambah, jmlQty, jumlah_qty;
    private String nofaktur;
    private DefaultTableModel tabel;
    private String id, namabarang, kode_satuan;
    private static int item = 0;
    private boolean tampil = true;
    private int tempPrev = 0, jmlKolom = 0, kode_lokasi;
    private double tempJ, tempJQ, stok, jumlah = 0;
    private int tempL, tempK;
    private double tempAJ[], tempAL[];
    private ResultSet tempRs;
    ArrayList<String> kode_nama_arr = new ArrayList();

    public Penjualan_Penjualan() {
        initComponents();
        AturlebarKolom();
        this.setLocationRelativeTo(null);
        AutoCompleteDecorator.decorate(comCustomer);
        AutoCompleteDecorator.decorate(comSalesman);
        AutoCompleteDecorator.decorate(comStaff);
        AutoCompleteDecorator.decorate(comOrder);
//        //AutoCompleteDecorator.decorate(comTableBarang);
//        AutoCompleteDecorator.decorate(comTableSatuan);
//        AutoCompleteDecorator.decorate(comTableLokasi);
//        AutoCompleteDecorator.decorate(comTableHarga);
        loadCustomer();
        loadComOrder();
        loadSalesman();
        loadStaff();
        loadTOP();
        HitungSemua();
        tanggal_jam_sekarang();
//        loadComTableBarang();
//        loadComTableKode();
        //  loadComTableLokasi();
        loadNumberTable();
        //  AturlebarKolom();
        autonumber();

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
                            tbl_Penjualan.editCellAt(tbl_Penjualan.getSelectedRow(), 1);
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
//                ((JTextComponent) comTableKode.getEditor().getEditorComponent()).setText(key);
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

        //JCombobox nama barang
        ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboBarang(((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
                            tbl_Penjualan.editCellAt(tbl_Penjualan.getSelectedRow(), 2);
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
                //   ((JTextComponent) comTableKode.getEditor().getEditorComponent()).setText(key);
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

    public Penjualan_Penjualan(String tanggal, String transaksi) {
        initComponents();
        AturlebarKolom();
        this.setLocationRelativeTo(null);
        AutoCompleteDecorator.decorate(comCustomer);
        AutoCompleteDecorator.decorate(comSalesman);
        AutoCompleteDecorator.decorate(comStaff);
        AutoCompleteDecorator.decorate(comOrder);
//        //AutoCompleteDecorator.decorate(comTableBarang);
//        AutoCompleteDecorator.decorate(comTableSatuan);
//        AutoCompleteDecorator.decorate(comTableLokasi);
//        AutoCompleteDecorator.decorate(comTableHarga);
        loadCustomer();
        loadComOrder();
        loadSalesman();
        loadStaff();
        loadTOP();
        HitungSemua();
        tanggal_jam_sekarang();
//        loadComTableBarang();
//        loadComTableKode();
        //  loadComTableLokasi();
        loadNumberTable();
        //  AturlebarKolom();
        autonumber();

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
                            tbl_Penjualan.editCellAt(tbl_Penjualan.getSelectedRow(), 1);
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
//                ((JTextComponent) comTableKode.getEditor().getEditorComponent()).setText(key);
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

        //JCombobox nama barang
        ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboBarang(((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
                            tbl_Penjualan.editCellAt(tbl_Penjualan.getSelectedRow(), 2);
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
                //   ((JTextComponent) comTableKode.getEditor().getEditorComponent()).setText(key);
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

    public Penjualan_Penjualan(java.awt.Frame parent, boolean modal, Connect connection) {
        //   super(parent, modal, connection);
        initComponents();

    }

    void loadComboKode(final String key) {
        Runnable doHighlight = new Runnable() {
            @Override
            public void run() {
                System.out.println("ini load combo kode");
                try {
                    String sql = "select concat(kode_barang,\"-\",nama_barang) as gabung from barang where kode_barang ='" + key + "' "
                            + "OR nama_barang like '%" + key + "%'";
                    //String sql = "select concat(kode_barang,\"-\",nama_barang) as gabung from barang";
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
                    //comKodeBarang.setSelectedIndex(-1);
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

    void loadComboBarang(final String key) {
        Runnable doHighlight = new Runnable() {
            @Override
            public void run() {
                System.out.println("ini load combo nama");
                try {
                    String sql = "select concat(kode_barang,\"-\",nama_barang) as gabung from barang where kode_barang ='" + key + "' "
                            + "OR nama_barang like '%" + key + "%'";
                    //String sql = "select concat(kode_barang,\"-\",nama_barang) as gabung from barang";
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
                    //comKodeBarang.setSelectedIndex(-1);
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

    void load_dari_kode_barang() {

        int selectedRow = tbl_Penjualan.getSelectedRow();
        String nama_awal = String.valueOf(comTableKode.getSelectedItem().toString());
        String[] split = comTableKode.getSelectedItem().toString().split(" - ");
        System.out.println("nilai comTable barang adalah " + comTableKode.getSelectedItem());
        if (comTableKode.getSelectedItem() != null) {
            split = nama_awal.split("-");

        }
        for (int i = 0; i < split.length; i++) {
            System.out.println("ini split " + split[i]);
        }
        try {
//            String sql = "select kode_barang,nama_barang,harga_jual_2_barang from barang where kode_barang = '" + split[0] + "'";
            String sql = "select b.kode_barang, b.nama_barang, b.harga_jual_1_barang, "
                    + "b.harga_jual_2_barang, b.harga_jual_3_barang, b.harga_rata_rata_barang, "
                    + "bl.jumlah from barang b, barang_lokasi bl, lokasi l where b.kode_barang = bl.kode_barang and "
                    + "bl.kode_lokasi = l.kode_lokasi and l.nama_lokasi='toko' and b.kode_barang = '" + split[0] + "' "
                    + "and bl.kode_lokasi=4";
            System.out.println("jumlahhhh: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String id = res.getString(1);
                String namabarang = res.getString(2);
                harga = res.getInt(4);
                jumlah = res.getInt(7);
                this.kode_barang = Integer.parseInt(id);
                loadNumberTable();
//                loadHargaSatuanBarang();

                //String konv = comSatuan.getSelectedItem().toString();
                // String kode_strip = kode + "-" + nama;
                if (selectedRow != -1) {
                    System.out.println("idddddddddddddddddddddddddd");
                    loadComTableLokasi();
//                    comTableAsal.getItemAt(0);

                    // loadComSatuanBarang(String.valueOf(kode_barang));
                    //tbl_Penjualan.setValueAt(comSatuan.getItemAt(0), selectedRow, 3);
                    loadComTableSatuan();
                    tbl_Penjualan.setValueAt(comTableLokasi.getItemAt(0), selectedRow, 3);
                    tbl_Penjualan.setValueAt(comTableKonv.getItemAt(0), selectedRow, 4);
                    tbl_Penjualan.setValueAt(kode_barang, selectedRow, 1);
                    tbl_Penjualan.setValueAt(namabarang, selectedRow, 2);
                    tbl_Penjualan.setValueAt(0, selectedRow, 5);
                    tbl_Penjualan.setValueAt(harga, selectedRow, 6);
//                    tbl_Penjualan.setValueAt(harga, selectedRow, 8);
//                    System.out.println("harga: "+harga);
                    tbl_Penjualan.setValueAt(harga, selectedRow, 7);
//                    System.out.println(jumlah);

                }
//                loadComSatuanBarang(tbl_Penjualan.getValueAt(selectedRow, 1).toString());
//                tbl_Penjualan.setValueAt(comSatuan.getSelectedItem(), selectedRow, 3);
            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

    void load_dari_nama_barang() {
        int selectedRow = tbl_Penjualan.getSelectedRow();
        String nama_awal = String.valueOf(comTableBarang.getSelectedItem());
        String[] split = new String[2];
        System.out.println("nilai comTable barang adalah " + comTableBarang.getSelectedItem());
        if (comTableBarang.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }
        try {
            String sql = "select b.kode_barang, b.nama_barang, b.harga_jual_1_barang, "
                    + "b.harga_jual_2_barang, b.harga_jual_3_barang, b.harga_rata_rata_barang, "
                    + "bl.jumlah from barang b, barang_lokasi bl, lokasi l where b.kode_barang = bl.kode_barang and "
                    + "bl.kode_lokasi = l.kode_lokasi and l.nama_lokasi='toko' and b.kode_barang = '" + split[0] + "' "
                    + "and bl.kode_lokasi=4";
            System.out.println("jumlahhhh: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String id = res.getString(1);
                String namabarang = res.getString(2);
                harga = res.getInt(4);
                jumlah = res.getInt(7);
                this.kode_barang = Integer.parseInt(id);
                loadNumberTable();
                // loadComSatuanBarang(namabarang);
//                loadHargaSatuanBarang();
                loadComTableLokasi();
                //String konv = comSatuan.getSelectedItem().toString();

                // String kode_strip = kode + "-" + nama;
                if (selectedRow != -1) {
                    System.out.println("idddddddddddddddddddddddddd");
//                    comTableAsal.getItemAt(0);
                    //   loadComSatuanBarang(String.valueOf(kode_barang));
                    //tbl_Penjualan.setValueAt(comSatuan.getItemAt(0), selectedRow, 3);
                    loadComTableSatuan();
                    tbl_Penjualan.setValueAt(comTableLokasi.getItemAt(0), selectedRow, 3);
                    tbl_Penjualan.setValueAt(comTableKonv.getItemAt(0), selectedRow, 4);
                    tbl_Penjualan.setValueAt(kode_barang, selectedRow, 1);
                    tbl_Penjualan.setValueAt(namabarang, selectedRow, 2);
                    tbl_Penjualan.setValueAt(0, selectedRow, 5);
                    tbl_Penjualan.setValueAt(harga, selectedRow, 6);
//                    tbl_Penjualan.setValueAt(harga, selectedRow, 8);
//                    System.out.println("harga: "+harga);
                    tbl_Penjualan.setValueAt(harga, selectedRow, 7);
//                    System.out.println(jumlah);

                }
//                loadComSatuanBarang(tbl_Penjualan.getValueAt(selectedRow, 1).toString());
//                tbl_Penjualan.setValueAt(comSatuan.getSelectedItem(), selectedRow, 3);
            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

    void hapustabel() {
        DefaultTableModel model = (DefaultTableModel) tbl_Penjualan.getModel();
        for (int i = tbl_Penjualan.getRowCount() - 1; i > -1; i--) {
            model.removeRow(i);

        }
    }

    private void loadTable(String no_faktur) {
        DefaultTableModel model = (DefaultTableModel) tbl_Penjualan.getModel();
        TableModel tabelModel;
        tabelModel = tbl_Penjualan.getModel();
        try {
            hapustabel();
            String sql = "SELECT * FROM penjualan_detail pd, lokasi l, konversi k, penjualan p"
                    + "WHERE pd.kode_konversi = k.kode_konversi "
                    + "AND pd.kode_lokasi = l.kode_lokasi "
                    + "AND pd.no_faktur_penjualan = '" + no_faktur + "'"
                    + "AND pd.no_faktur_penjualan = p.no_faktur_penjualan";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            int x = 1;
            double qty = 0;
            while (res.next()) {
                System.out.println(x);
                model.addRow(new Object[]{
                    x++,
                    res.getString("kode_barang"),
                    res.getString("nama_barang_edit"),
                    res.getString("nama_lokasi"),
                    res.getString("nama_konversi"),
                    res.getString("jumlah_barang"),
                    res.getString("harga_penjualan"),
                    res.getString("harga_jual"),
                    res.getString("harga_revisi"),
                    res.getString("total_harga")
                });
                txt_tbl_total.setText(res.getString("totale"));
                qty += Double.parseDouble(res.getString("jumlah_barang"));
            }
            txt_item.setText("Jumlah Item : " + tbl_Penjualan.getRowCount());
            txt_jumQty.setText("Jumlah Qty : " + qty);
            res.close();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    private void loadForm(String no_faktur) {
//        TableModel tabelModel;
//        tabelModel = tbl_Pembelian.getModel();
        try {
            String sql = "SELECT * from penjualan p, customer c, salesman s, top t"
                    + "WHERE p.no_faktur_penjualan = '" + no_faktur + "' "
                    + "AND p.kode_customer = c.kode_customer "
                    + "AND p.kode_salesman = s.kode_salesman "
                    + "AND p.id_top = t.id_top ";
            System.out.println("sql loadform" + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                comCustomer.setSelectedItem(res.getString("nama_customer"));
                txt_Nama.setText(res.getString("nama_customer"));
                txt_Alamat.setText(res.getString("alamat_customer"));
                txt_tanggal.setText(res.getString("tgl_penjualan"));
                comOrder.setSelectedItem("no_faktur_order");
                txt_faktur.setText(res.getString("no_faktur_penjualan"));
                comSalesman.setSelectedItem(res.getString("nama_salesman"));
                comTOP.setSelectedItem(res.getString("nama_top"));
                comStaff.setSelectedItem(res.getString("staff"));
                txt_keterangan.setText(res.getString("keterangan_penjualan"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }

    void SaveData() {
        try {
            getId_penjualan();
            getVerif();
            int idCustomer = getIdCustomer(comCustomer.getSelectedItem().toString());
//        String idStaff=getIdCustomer(comStaff.getSelectedItem().toString());
//        String idSalesman=getIdCustomer(comSalesman.getSelectedItem().toString());
            int idPenjualan = getId_penjualan();

            Koneksi Koneksi = new Koneksi();
            Connection con = Koneksi.configDB();
            Statement st = con.createStatement();
//            
//                        String sql = "DELETE FROM penjualan "
//                    + "WHERE no_faktur_penjualan = '" + txt_faktur.getText() + "'";
//
//            st.executeUpdate(sql);
//
//            sql = "DELETE FROM penjualan_detail "
//                    + "WHERE no_faktur_penjualan = '" + txt_faktur.getText() + "'";

//            st.executeUpdate(sql);
            int totale = Integer.parseInt(txt_tbl_total.getText()) * -1;

            SimpleDateFormat format_tanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = format_tanggal.format(System.currentTimeMillis());

            String sql = "insert into penjualan(no_faktur_penjualan, tgl_penjualan, keterangan_penjualan, status_verifikasi, no_faktur_order , id_top, staff, kode_customer, "
                    + "kode_salesman, kode_pegawai, verifikasi_pegawai, tgl_validasi_penjualan, kode_pegawai_validasi, pembaran_udah_bayar, "
                    + "pembayaran_aktif, potongan, denda_salesman, jumlah_print, print_gudang, print_jalan,"
                    + "no_surat_jalan, tgl_surat_jalan, print_nota_jalan, tgl_nota_jalan, status_toko, tgl_bg_penjualan, "
                    + "faktur_tempo_bg_penjualan, faktur_bg_penjualan, kode_pegawai_bg_penjualan, no_seri_bg, totale, kembali, tgl_filter_tutup_buku)"
                    + "values('"
                    + txt_faktur.getText() + "','"
                    + date + "','"
                    + txt_keterangan.getText() + "','"
                    + 0 + "','" //nilai true false
                    + 0 + "','"
                    + 0 + "','" //id nya int comTOP.getSelectedItem()
                    + comStaff.getSelectedItem() + "','"
                    + idCustomer + "','" //id nya int idCustomer
                    + 0 + "','" //id nya int idSalesman
                    + 0 + "','" //id nya int idStaff
                    + 0 + "','"
                    + "0000-00-00" + "','"
                    + 0 + "','"
                    + totale + "','"
                    + txt_tbl_total.getText() + "','"
                    + 0 + "','"
                    + 0 + "','"
                    + 0 + "','"
                    + 0 + "','"
                    + 0 + "','"
                    + "0" + "','"
                    + date + "','"
                    + 0 + "','"
                    + date + "','"
                    + 0 + "','"
                    + date + "','"
                    + date + "','"
                    + "0" + "','"
                    + 0 + "','"
                    + "0" + "','"
                    + txt_tbl_total.getText() + "','"
                    + 0 + "','"
                    + date + "');";
            System.out.println("Mouse simpan pertama = " + sql);
            int dbq = st.executeUpdate(sql);

            int baris = tbl_Penjualan.getRowCount();
            System.out.println("baris = " + baris);
            TableModel tabelModel;
            tabelModel = tbl_Penjualan.getModel();

            for (int i = 0; i < baris; i++) {
                //      getHargaJual(i);
                sql = "insert into penjualan_detail( id_penjualan, no_faktur_penjualan, kode_barang, nama_barang_edit, "
                        + "kode_lokasi, jumlah_barang, jumlah_per_pcs, max_return, jenis_harga, "
                        + "harga_penjualan, harga_jual, harga_revisi, hpp, harga_jual1, harga_jual2, total_harga, "
                        + "id_top, komisi, pembayaran_penjualan, kode_barang_konversi, status_toko)"
                        + "value" + "('"
                        + idPenjualan + "','"
                        + txt_faktur.getText() + "','"
                        + tbl_Penjualan.getValueAt(i, 1).toString() + "','"
                        + tbl_Penjualan.getValueAt(i, 2).toString() + "','"
                        + getKodeLokasi(i) + "','"
                        + tbl_Penjualan.getValueAt(i, 5).toString() + "','"
                        + getKonvPcs(i) + "','"
                        + 0 + "','"
                        + "0" + "','"
                        + 0 + "','"
                        + 0 + "','"
                        + 0 + "','"
                        + 0 + "','"
                        + tmpHJ1 + "','"
                        + tmpHJ2 + "','"
                        + txt_tbl_total.getText() + "','"
                        + 0 + "','"
                        + 0 + "','"
                        + 0 + "','"
                        + 0 + "','"
                        + 0 + "')";

                int dbq2 = st.executeUpdate(sql);
                //     System.out.println("Mouse simpan kedua = "+sql);

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
                        + " kode_barang = " + tbl_Penjualan.getValueAt(i, 1)
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
//            sql = "insert into customer( kode_customer, nama_customer, alamat_customer)"
//                    + "value('" + idCustomer + "','" + txt_Nama.getText() + "','" + txt_Alamat.getText() + "');";
////            System.out.println(sql);
//            st.executeUpdate(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "data tidak dimasukkan ke database " + e.getMessage(), "informasi ", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    void loadComTableSatuan() {
        try {
            /// identitas konversi tidak urut
            String sql = "select * from konversi k, barang_konversi bk, barang b "
                    + "where b.kode_barang = bk.kode_barang "
                    + "and bk.kode_konversi = k.kode_konversi "
                    + "and b.kode_barang = '" + kode_barang + "' order by bk.kode_barang_konversi asc";
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
            TableColumnModel m = tbl_Penjualan.getColumnModel();
            m.getColumn(6).setCellRenderer(new Currency_Column());
            m.getColumn(7).setCellRenderer(new Currency_Column());
            m.getColumn(8).setCellRenderer(new Currency_Column());
            m.getColumn(9).setCellRenderer(new Currency_Column());
            //comTableKonv.setSelectedIndex(0);
            res.close();
            conn.close();
//            comKodeKonv.addItem(name);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

    String kodeSatuan(String nama_satuan, String kode_barang) {
        String kode = null;
        try {
            Koneksi Koneksi = new Koneksi();
            Connection con = Koneksi.configDB();
            String sql1a = "select kode_konversi from barang_konversi where kode_konversi = (select kode_konversi from konversi where nama_konversi='" + nama_satuan + "') and kode_barang = '" + kode_barang + "'";
            System.out.println(sql1a);
            Statement st1a = con.createStatement();
            java.sql.ResultSet res = st1a.executeQuery(sql1a);
            while (res.next()) {
                kode = res.getString("kode_konversi");
            }
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
        return kode;
    }

    void seleksiLokasi(String nama_lokasi) {
        try {
            Koneksi Koneksi = new Koneksi();
            Connection con = Koneksi.configDB();
            String sql1a = "select kode_lokasi from lokasi where nama_lokasi = '" + nama_lokasi + "'";
            System.out.println(sql1a);
            Statement st1a = con.createStatement();
            java.sql.ResultSet res = st1a.executeQuery(sql1a);
            while (res.next()) {
                kode_lokasi = Integer.parseInt(res.getString("kode_lokasi"));
            }
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
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

    double konversi_stok(String kode_barang, String kode_konversi, double stok) {
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
            hasil = stok / jumlah_konversi;
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
        return hasil;
    }

    private void loadStock(String kodeBarang) {
        try {
            String lokasi = tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 3).toString();
            seleksiLokasi(lokasi);
            String sql = "SELECT jumlah from barang_lokasi "
                    + "WHERE kode_barang = '" + kodeBarang + "'"
                    + "AND kode_lokasi = '" + kode_lokasi + "'";
            System.out.println("sts: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                stok = Double.parseDouble(res.getString("jumlah"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

    void HitungSubTotal() {

        TableModel tabelModel;
        tabelModel = tbl_Penjualan.getModel();
        int baris = tbl_Penjualan.getRowCount();
        int selectedRow = tbl_Penjualan.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tbl_Penjualan.getModel();
        try {
            String kode = tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 1).toString();
            String satuan = tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 4).toString();
            double qty1 = Double.parseDouble(String.valueOf(tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 5)));
            String kode_satuan1 = kodeSatuan(satuan, kode);
            double qty_cek = qty_konversi(kode, kode_satuan1, qty1);
            double stok_konversi = konversi_stok(kode, kode_satuan1, stok);
            System.out.println("qty_konversi : " + qty_cek);

            loadStock(kode);
            if (qty_cek > stok) {
                JOptionPane.showMessageDialog(null, "Qty tidak boleh lebih dari stok. Stock tersedia = " + stok_konversi + " " + satuan);
                tbl_Penjualan.setValueAt(stok_konversi, selectedRow, 5);

            } else if ((tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 5) != "")
                    && (tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 7) != "")) {

                int harga1, jenis_harga;
                int row = tbl_Penjualan.getSelectedRow();
                jenis_harga = Integer.parseInt(tbl_Penjualan.getValueAt(row, 6).toString());
                harga1 = (int) (qty_cek * jenis_harga);
                tbl_Penjualan.setValueAt(harga1, row, 7);
                //jumlah = Double.parseDouble(tabelModel.getValueAt(tbl_Penjualan.getSelectedRow(), 5).toString());
                //harga = Integer.parseInt(tabelModel.getValueAt(tbl_Penjualan.getSelectedRow(), 7).toString());
                //subtotal = (int) (jumlah * harga);
                tabelModel.setValueAt(harga1, tbl_Penjualan.getSelectedRow(), 9);
                HitungSemua();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data tidak boleh kosong" + e);
            e.printStackTrace();
        }

    }

    void loadCustomer() {

        try {
            String sql = "select * from customer";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString(1);
                String name = res.getString(2);
                comCustomer.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadStaff() {

        try {
            String sql = "select * from pegawai";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String nama = res.getString(5);
//                String kode = res.getString(1);
                comStaff.addItem(nama);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadSalesman() {

        try {
            String sql = "select * from salesman";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String nama = res.getString(2);
//                String kode = res.getString(1);
                comSalesman.addItem(nama);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadTOP() {

        try {
            String sql = "select * from top";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String nama = res.getString(2);
//                String kode = res.getString(1);
                comTOP.addItem(nama);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    private void removeSelectedRows(JTable table) {
        int Hapus = 1;
        Hapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin mau menghapus baris ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if (Hapus == 0) {
            DefaultTableModel model = (DefaultTableModel) this.tbl_Penjualan.getModel();
            int[] rows = table.getSelectedRows();
            for (int i = 0; i < rows.length; i++) {
                model.removeRow(rows[i] - i);
            }
        }
    }

    void loadComTableKode() {
        try {
            String sql = "select * from barang order by kode_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(4);
                comTableKode.addItem(name);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    public void loadComOrder() {
        try {
            String sql = "SELECT * FROM `order` ORDER BY order.no_faktur_order ASC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                Object[] order = new Object[2];
                order[0] = res.getString(2);
                comOrder.addItem((String) order[0]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void loadComTableLokasi() {
        try {
            String sql = "select * from lokasi";
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

    void ClearData() {
        DefaultTableModel dm = (DefaultTableModel) tbl_Penjualan.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        dm.addRow(new Object[]{"", "", "", "", "", "0", "0", "0", "0", "0"});
    }

    void loadNumberTable() {
        int baris = tbl_Penjualan.getRowCount();
        for (int a = 0; a < baris; a++) {
            String nomor = String.valueOf(a + 1);
            tbl_Penjualan.setValueAt(nomor + ".", a, 0);
        }

    }

    void AturlebarKolom() {
        TableColumn column;
        tbl_Penjualan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tbl_Penjualan.getColumnModel().getColumn(0);
        column.setPreferredWidth(30);
        column = tbl_Penjualan.getColumnModel().getColumn(1);
        column.setPreferredWidth(100);
        column = tbl_Penjualan.getColumnModel().getColumn(2);
        column.setPreferredWidth(150);
        column = tbl_Penjualan.getColumnModel().getColumn(3);
        column.setPreferredWidth(70);
        column = tbl_Penjualan.getColumnModel().getColumn(4);
        column.setPreferredWidth(70);
        column = tbl_Penjualan.getColumnModel().getColumn(5);
        column.setPreferredWidth(70);
        column = tbl_Penjualan.getColumnModel().getColumn(6);
        column.setPreferredWidth(90);
        column = tbl_Penjualan.getColumnModel().getColumn(7);
        column.setPreferredWidth(90);
        column = tbl_Penjualan.getColumnModel().getColumn(8);
        column.setPreferredWidth(95);
        column = tbl_Penjualan.getColumnModel().getColumn(9);
        column.setPreferredWidth(100);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tbl_Penjualan.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        tbl_Penjualan.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        tbl_Penjualan.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        tbl_Penjualan.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
        txt_tbl_total.setHorizontalAlignment(JTextField.RIGHT);

    }

    void BersihField() {
        txt_faktur.setText("");
        txt_keterangan.setText("");
    }

    public void autonumber() {
        try {
            String lastNo = "";
            String sql = "select max(no_faktur_penjualan) from penjualan ORDER BY no_faktur_penjualan DESC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                if (res.first() == false) {
                    txt_faktur.setText("PJ");

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
                    txt_faktur.setText(String.valueOf(huruf + "" + angkapad));
                    System.out.println(num);

                }
            }
            res.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static String rightPadZeros(String str, int num) {
        return String.format("%05d", Integer.parseInt(str));
    }

    public void tanggal_jam_sekarang() {
        Thread p = new Thread() {
            public void run() {
                for (;;) {
                    GregorianCalendar cal = new GregorianCalendar();
                    int hari = cal.get(Calendar.DAY_OF_MONTH);
                    int bulan = cal.get(Calendar.MONTH);
                    int tahun = cal.get(Calendar.YEAR);
                    int jam = cal.get(Calendar.HOUR_OF_DAY);
                    int menit = cal.get(Calendar.MINUTE);
                    int detik = cal.get(Calendar.SECOND);
                    txt_tanggal.setText(tahun + "-" + (bulan + 1) + "-" + hari + " " + jam + ":" + menit + ":" + detik);

                }
            }
        };
        p.start();
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

    private String currency_convert(int nilai) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(nilai);
    }

    public void HitungSemua() {
        if (tbl_Penjualan.getRowCount() >= 1) {
            subtotalfix = 0;
            for (int i = tbl_Penjualan.getRowCount() - 1; i > -1; i--) {
                int x = Integer.parseInt(tbl_Penjualan.getValueAt(i, 9).toString());
                subtotalfix += x;
            }
            txt_tbl_total.setText(String.valueOf(currency_convert(subtotalfix)));
        }
    }

    void showQTY() {
        try {
            jumlah_item = tbl_Penjualan.getRowCount();
            double qty, jumqty = 0;
            System.out.println("row count : " + jumlah_item);
            for (int j = 0; j < tbl_Penjualan.getRowCount();) {
                if (tbl_Penjualan.getValueAt(j, 5) != "") {
                    qty = Double.parseDouble(tbl_Penjualan.getValueAt(j, 5).toString());
                    jumqty += qty;
                }
                j++;
            }
            jumlah_qty = jumqty;
            txt_item.setText("Jumlah Item : " + jumlah_item);
            txt_jumQty.setText("Jumlah Qty : " + String.valueOf(jumlah_qty));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data tidak boleh kosong" + e);
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comTableKode = new javax.swing.JComboBox<>();
        comTableBarang = new javax.swing.JComboBox<>();
        comTableLokasi = new javax.swing.JComboBox<>();
        comTableKonv = new javax.swing.JComboBox<>();
        comTableHarga = new javax.swing.JComboBox<>();
        lbl_Save = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbl_prev = new javax.swing.JLabel();
        lbl_next = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txt_Nama = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_Alamat = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_tanggal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_faktur = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        comSalesman = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        comTOP = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        checkverif = new javax.swing.JCheckBox();
        jLabel17 = new javax.swing.JLabel();
        txt_keterangan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Penjualan = new javax.swing.JTable();
        jTextField9 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_tbl_total = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        txt_item = new javax.swing.JTextField();
        txt_jumQty = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        comCustomer = new javax.swing.JComboBox<>();
        comOrder = new javax.swing.JComboBox<>();
        comStaff = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        comTableKode.setEditable(true);
        comTableKode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        comTableKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableKodeActionPerformed(evt);
            }
        });

        comTableBarang.setEditable(true);
        comTableBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        comTableBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableBarangActionPerformed(evt);
            }
        });

        comTableLokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableLokasiActionPerformed(evt);
            }
        });

        comTableKonv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableKonvActionPerformed(evt);
            }
        });

        comTableHarga.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbl_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        lbl_Save.setText("F12 - Save");
        lbl_Save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_SaveMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_SaveMouseEntered(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Clear-icon.png"))); // NOI18N
        jLabel2.setText("F9 - Clear");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_yast_printer_30297.png"))); // NOI18N
        jLabel3.setText("Print");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_yast_printer_30297.png"))); // NOI18N
        jLabel4.setText("Print Surat Jalan");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        jLabel5.setText("Esc - Exit");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        lbl_prev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/back-icon.png"))); // NOI18N
        lbl_prev.setText("Prev");
        lbl_prev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_prevMouseClicked(evt);
            }
        });

        lbl_next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/forward-icon.png"))); // NOI18N
        lbl_next.setText("Next");
        lbl_next.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_nextMouseClicked(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(51, 51, 255));
        jLabel8.setText("Customer");

        jButton1.setText(">");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("Nama");

        txt_Nama.setEditable(false);
        txt_Nama.setBackground(new java.awt.Color(153, 255, 153));

        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText("Alamat");

        txt_Alamat.setEditable(false);
        txt_Alamat.setBackground(new java.awt.Color(153, 255, 153));

        jLabel11.setForeground(new java.awt.Color(51, 51, 255));
        jLabel11.setText("Tanggal");

        txt_tanggal.setEditable(false);

        jLabel12.setForeground(new java.awt.Color(51, 51, 255));
        jLabel12.setText("No. Order");

        jLabel13.setForeground(new java.awt.Color(51, 51, 255));
        jLabel13.setText("No. Faktur");

        txt_faktur.setEditable(false);

        jLabel14.setForeground(new java.awt.Color(51, 51, 255));
        jLabel14.setText("Salesman");

        jLabel15.setText("T.O.P");

        jLabel16.setText("Staff");

        checkverif.setText("Verifikasi Administrasi");
        checkverif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkverifActionPerformed(evt);
            }
        });

        jLabel17.setText("Keterangan Verifikasi");

        tbl_Penjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", "", "", "0", "0", "0", "0", "0"}
            },
            new String [] {
                "No", "Kode", "Barang", "Lokasi", "Satuan (1/2/3)", "Jumlah", "J.Harga(1/2/3)", "Harga", "Rekom Harga", "Sub Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, false, false, true, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Penjualan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_PenjualanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_PenjualanKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tbl_PenjualanKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Penjualan);
        if (tbl_Penjualan.getColumnModel().getColumnCount() > 0) {
            tbl_Penjualan.getColumnModel().getColumn(1).setCellEditor(new ComboBoxCellEditor(comTableKode));
            tbl_Penjualan.getColumnModel().getColumn(2).setCellEditor(new ComboBoxCellEditor(comTableBarang));
            tbl_Penjualan.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comTableLokasi));
            tbl_Penjualan.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comTableKonv));
            tbl_Penjualan.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comTableHarga));
        }

        jTextField9.setBackground(new java.awt.Color(204, 0, 0));

        jLabel18.setText("Total");

        txt_tbl_total.setBackground(new java.awt.Color(153, 255, 153));

        jLabel19.setText("Kasir");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Nama Kasir");

        jCheckBox2.setForeground(new java.awt.Color(255, 0, 0));
        jCheckBox2.setText("LGSG CETAK");

        jCheckBox3.setForeground(new java.awt.Color(255, 0, 0));
        jCheckBox3.setText("NON DENDA");

        txt_item.setBackground(new java.awt.Color(0, 0, 0));
        txt_item.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_item.setForeground(new java.awt.Color(255, 255, 0));
        txt_item.setText("Jumlah Item");

        txt_jumQty.setBackground(new java.awt.Color(0, 0, 0));
        txt_jumQty.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_jumQty.setForeground(new java.awt.Color(255, 255, 0));
        txt_jumQty.setText("Jumlah Qty");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        comCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=== Pilih Customer ===" }));
        comCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comCustomerActionPerformed(evt);
            }
        });

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(lbl_Save)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel5))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton1)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(lbl_prev))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel9))
                                        .addGap(17, 17, 17)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_Nama, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                            .addComponent(txt_Alamat))
                                        .addGap(12, 12, 12))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(checkverif)
                                        .addGap(157, 157, 157)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel14))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txt_faktur)
                                                    .addComponent(comSalesman, 0, 141, Short.MAX_VALUE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(5, 5, 5)
                                                .addComponent(comOrder, 0, 140, Short.MAX_VALUE)))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel16))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel15)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_next))
                            .addComponent(comTOP, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel20)
                                        .addGap(107, 107, 107)
                                        .addComponent(jCheckBox2))
                                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel18))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCheckBox3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_item, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(txt_jumQty, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_tbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator4)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_Save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_prev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_next, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator1)
                                .addComponent(jSeparator2)
                                .addComponent(jSeparator3)
                                .addComponent(jSeparator5))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator6)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jLabel11)
                            .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(comTOP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(comCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel9))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_Nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13)
                    .addComponent(txt_faktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkverif)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comSalesman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_tbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18))
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(jLabel20))
                    .addComponent(txt_item)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBox2)
                        .addComponent(jCheckBox3)
                        .addComponent(txt_jumQty, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Penjualan_KotakHistoriPenjualan pkhb = new Penjualan_KotakHistoriPenjualan();
        pkhb.setVisible(true);
        pkhb.setFocusable(true);
        this.setFocusable(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void checkverifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkverifActionPerformed

    }//GEN-LAST:event_checkverifActionPerformed
    private boolean getVerif() {
        boolean verif = true;
        int status;
        try {
            String sql = "SELECT MAX(status_verifikasi) FROM penjualan";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

//            System.out.println("test =" +sql);
//            System.out.println("k =" +kode_barang);
//            System.out.println("Select =" + comTableKonv.getSelectedItem());
            while (res.next()) {
                verif = res.getBoolean(1);
                if (checkverif.isSelected() == true) {
                    status = 1;
                } else {
                    status = 0;
                }
                System.out.println("verif =" + status);
//                System.out.println("echo : " + tbl_Penjualan.getValueAt(selectedRow, 2).toString());

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
            e.printStackTrace();
        }
        return verif;
    }

    private void comTableBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableBarangActionPerformed
        load_dari_nama_barang();
    }//GEN-LAST:event_comTableBarangActionPerformed


    private void comTableKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableKodeActionPerformed
        load_dari_kode_barang();
    }//GEN-LAST:event_comTableKodeActionPerformed

    private void comTableLokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableLokasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comTableLokasiActionPerformed

    private void comTableKonvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableKonvActionPerformed
        /*
         DefaultTableModel model = (DefaultTableModel) tbl_Penjualan.getModel();
         int baris = tbl_Penjualan.getRowCount();
         TableModel tabelModel;
         tabelModel = tbl_Penjualan.getModel();
         int kode_barang = 0;
         try {
         for (int i = 0; i < baris; i++) {
         kode_barang = Integer.parseInt(tabelModel.getValueAt(i, 1).toString());
         }
         String sql = "select nama_konversi, jumlah_konversi, identitas_konversi from barang_konversi bk, konversi k where bk.kode_konversi = k.kode_konversi and bk.kode_barang ='" + kode_barang + "'";
         System.out.println("sql: " + sql);
         java.sql.Connection conn = (Connection) Koneksi.configDB();
         java.sql.Statement stm = conn.createStatement();
         java.sql.ResultSet res = stm.executeQuery(sql);
         while (res.next()) {
         System.out.println("y1 " + comTableKonv.getSelectedIndex() + " a: " + res.getInt(3));
         if (comTableKonv.getSelectedIndex() == res.getInt(3) - 1) {
         System.out.println("y2 " + res.getInt(2) + " t: " + Tempharga);
         int temp = Tempharga * res.getInt(2);
         System.out.println("error: " + temp);
         model.setValueAt(String.valueOf(temp), tbl_Penjualan.getSelectedRow(), 7);

         }
         }
         } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "Pilih Barang dan Harga Terlebih Dahulu !");
         }
         */

    }//GEN-LAST:event_comTableKonvActionPerformed

    private void tbl_PenjualanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PenjualanKeyPressed
        // int kode_barang = 0;
        TableModel tabelModel;
        tabelModel = tbl_Penjualan.getModel();
        int baris = tbl_Penjualan.getRowCount();
        int selectedRow = tbl_Penjualan.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tbl_Penjualan.getModel();
        // InputMap im = tbl_Penjualan.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        //KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        //KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
        //  im.put(down, im.get(f2));

        if (comTableKode.requestFocus(true)) {
            tbl_Penjualan.setCellSelectionEnabled(true);
            tbl_Penjualan.changeSelection(selectedRow, 1, false, false);
        } else if (comTableBarang.requestFocus(true)) {
            tbl_Penjualan.setCellSelectionEnabled(true);
            tbl_Penjualan.changeSelection(selectedRow, 2, false, false);

            //    tbl_Penjualan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (tbl_Penjualan.getSelectedColumn() == 1 || tbl_Penjualan.getSelectedColumn() == 2)) {
            InputMap im = tbl_Penjualan.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
            KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
            im.put(down, im.get(f2));
            System.out.println("asd");

        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (tbl_Penjualan.getSelectedColumn() == 0
                || tbl_Penjualan.getSelectedColumn() == 3 || tbl_Penjualan.getSelectedColumn() == 4
                || tbl_Penjualan.getSelectedColumn() == 5 || tbl_Penjualan.getSelectedColumn() == 6
                || tbl_Penjualan.getSelectedColumn() == 7 || tbl_Penjualan.getSelectedColumn() == 8
                || tbl_Penjualan.getSelectedColumn() == 9)) {
            InputMap im = tbl_Penjualan.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
            KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
            im.put(f2, null);
            im.put(down, null);
            System.out.println("fgh");
        } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            if (tbl_Penjualan.getRowCount() - 1 == -1) {
                JOptionPane.showMessageDialog(null, "Data didalam tabel telah tiada.", "", 2);
                showQTY();
                HitungSemua();
            } else {
                removeSelectedRows(tbl_Penjualan);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_F12) {
            int simpan_data = 1;
            simpan_data = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menyimpan data ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
            if (simpan_data == 0) {
                SaveData();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_F9) {
            System.out.println("clear data");
            ClearData();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER && tbl_Penjualan.getSelectedColumn() == 9) {
            model.addRow(new Object[]{"", "", "", "", "", "", "", "", "", ""});
        } //                
        //SATUAN
        else if (evt.getKeyCode() == KeyEvent.VK_1 && tbl_Penjualan.getSelectedColumn() == 4) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 1));
            try {
                String sql = "select nama_konversi, bk.jumlah_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '1' and bk.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String sat = res.getString("nama_konversi");
                    String sat2 = sat;
                    tbl_Penjualan.setValueAt(sat2, tbl_Penjualan.getSelectedRow(), 4);
                    //harga
                    int jml = res.getInt("jumlah_konversi");
                    tbl_Penjualan.setValueAt(this.harga, tbl_Penjualan.getSelectedRow(), 7);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_2 && tbl_Penjualan.getSelectedColumn() == 4) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 1));
            try {
                String sql = "select nama_konversi, bk.jumlah_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '2' and bk.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String sat = res.getString("nama_konversi");
                    String sat2 = sat;
                    tbl_Penjualan.setValueAt(sat2, tbl_Penjualan.getSelectedRow(), 4);
                    //harga
                    int jml = res.getInt("jumlah_konversi");
                    tbl_Penjualan.setValueAt(this.harga, tbl_Penjualan.getSelectedRow(), 7);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_3 && tbl_Penjualan.getSelectedColumn() == 4) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 1));
            try {
                String sql = "select nama_konversi, bk.jumlah_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '3' and bk.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String sat = res.getString("nama_konversi");
                    String sat2 = sat;
                    tbl_Penjualan.setValueAt(sat2, tbl_Penjualan.getSelectedRow(), 4);
                    //harga
                    int jml = res.getInt("jumlah_konversi");
                    tbl_Penjualan.setValueAt(this.harga, tbl_Penjualan.getSelectedRow(), 7);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } //LOKASI
        else if (evt.getKeyCode() == KeyEvent.VK_1 && tbl_Penjualan.getSelectedColumn() == 3) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 1));
            try {
                //select l.kode_lokasi, l.nama_lokasi from lokasi l, barang_lokasi bl where bl.kode_barang = '" + kode_barang + "'
                String sql = "select nama_lokasi from lokasi l join barang_lokasi bl on bl.kode_lokasi = l.kode_lokasi where bl.kode_lokasi = '1' and bl.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String lok = res.getString("nama_lokasi");
                    String lok2 = lok;
                    tbl_Penjualan.setValueAt(lok2, tbl_Penjualan.getSelectedRow(), 3);
                    System.out.println(lok2);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_2 && tbl_Penjualan.getSelectedColumn() == 3) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 1));
            try {
                String sql = "select nama_lokasi from lokasi l join barang_lokasi bl on bl.kode_lokasi = l.kode_lokasi where bl.kode_lokasi = '2' and bl.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String lok = res.getString("nama_lokasi");
                    String lok2 = lok;
                    tbl_Penjualan.setValueAt(lok2, tbl_Penjualan.getSelectedRow(), 3);
                    System.out.println(lok2);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_3 && tbl_Penjualan.getSelectedColumn() == 3) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 1));
            try {
                String sql = "select nama_lokasi from lokasi l join barang_lokasi bl on bl.kode_lokasi = l.kode_lokasi where bl.kode_lokasi = '4' and bl.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String lok = res.getString("nama_lokasi");
                    String lok2 = lok;
                    tbl_Penjualan.setValueAt(lok2, tbl_Penjualan.getSelectedRow(), 3);
                    System.out.println(lok2);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }

        } else if (evt.getKeyCode() == KeyEvent.VK_4 && tbl_Penjualan.getSelectedColumn() == 3) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 1));
            try {
                String sql = "select nama_lokasi from lokasi l join barang_lokasi bl on bl.kode_lokasi = l.kode_lokasi where bl.kode_lokasi = '5' and bl.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String lok = res.getString("nama_lokasi");
                    String lok2 = lok;
                    tbl_Penjualan.setValueAt(lok2, tbl_Penjualan.getSelectedRow(), 3);
                    System.out.println(lok2);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_5 && tbl_Penjualan.getSelectedColumn() == 3) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 1));
            try {
                String sql = "select nama_lokasi from lokasi l join barang_lokasi bl on bl.kode_lokasi = l.kode_lokasi where bl.kode_lokasi = '6' and bl.kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String lok = res.getString("nama_lokasi");
                    String lok2 = lok;
                    tbl_Penjualan.setValueAt(lok2, tbl_Penjualan.getSelectedRow(), 3);
                    System.out.println(lok2);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } //HARGA
        else if (evt.getKeyCode() == KeyEvent.VK_1 && tbl_Penjualan.getSelectedColumn() == 6) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 1));
            try {

                //select l.kode_lokasi, l.nama_lokasi from lokasi l, barang_lokasi bl where bl.kode_barang = '" + kode_barang + "'
                String sql = "select harga_jual_1_barang"
                        + ", harga_jual_2_barang, harga_jual_3_barang from barang where kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String lok = res.getString("harga_jual_1_barang");
                    String lok2 = res.getString("harga_jual_2_barang");
                    String lok3 = res.getString("harga_jual_3_barang");
                    //  String []lok4 = {lok,lok2,lok3}; 
                    tbl_Penjualan.setValueAt(lok, tbl_Penjualan.getSelectedRow(), 6);
                    System.out.println(lok);
                    int harga1 = res.getInt("harga_jual_1_barang");
                    double qty = Double.parseDouble(tabelModel.getValueAt(tbl_Penjualan.getSelectedRow(), 5).toString());
                    double harga_konversi = harga1 * qty;
                    tbl_Penjualan.setValueAt(harga_konversi, tbl_Penjualan.getSelectedRow(), 7);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_2 && tbl_Penjualan.getSelectedColumn() == 6) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 1));
            try {

                //select l.kode_lokasi, l.nama_lokasi from lokasi l, barang_lokasi bl where bl.kode_barang = '" + kode_barang + "'
                String sql = "select harga_jual_1_barang, harga_jual_2_barang, harga_jual_3_barang from barang where kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String lok = res.getString("harga_jual_1_barang");
                    String lok2 = res.getString("harga_jual_2_barang");
                    String lok3 = res.getString("harga_jual_3_barang");
                    //  String []lok4 = {lok,lok2,lok3}; 
                    tbl_Penjualan.setValueAt(lok2, tbl_Penjualan.getSelectedRow(), 6);
                    System.out.println(lok2);
                    int harga1 = res.getInt("harga_jual_2_barang");
                    double qty = Double.parseDouble(tabelModel.getValueAt(tbl_Penjualan.getSelectedRow(), 5).toString());
                    double harga_konversi = harga1 * qty;
                    tbl_Penjualan.setValueAt(harga_konversi, tbl_Penjualan.getSelectedRow(), 7);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_3 && tbl_Penjualan.getSelectedColumn() == 6) {
            System.out.println("ini alt");
            String kode_barang = String.valueOf(tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 1));
            try {
                //select l.kode_lokasi, l.nama_lokasi from lokasi l, barang_lokasi bl where bl.kode_barang = '" + kode_barang + "'
                String sql = "select harga_jual_1_barang, harga_jual_2_barang, harga_jual_3_barang from barang where kode_barang = '" + kode_barang + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String lok = res.getString("harga_jual_1_barang");
                    String lok2 = res.getString("harga_jual_2_barang");
                    String lok3 = res.getString("harga_jual_3_barang");
                    //  String []lok4 = {lok,lok2,lok3}; 
                    tbl_Penjualan.setValueAt(lok3, tbl_Penjualan.getSelectedRow(), 6);
                    System.out.println(lok3);
                    int harga1 = res.getInt("harga_jual_3_barang");
                    double qty = Double.parseDouble(tabelModel.getValueAt(tbl_Penjualan.getSelectedRow(), 5).toString());
                    double harga_konversi = harga1 * qty;
                    tbl_Penjualan.setValueAt(harga_konversi, tbl_Penjualan.getSelectedRow(), 7);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        }

        loadNumberTable();


    }//GEN-LAST:event_tbl_PenjualanKeyPressed

    private int getIdCustomer(String Namacustomer) {
        int id = 0;
        try {
            String sql = "select * from customer where nama_customer = '" + Namacustomer + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                id = res.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
        return id;
    }

    private String getIdSalesman(String idsales) {
        String id = "";
        try {
            String sql = "select * from salesman where kode_salesman = '" + idsales + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                idsales = res.getString("kode_salesman");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
        return idsales;
    }

    private int getIdPegawai(int idpegawai) {
        String id = "";
        try {
            String sql = "select * from pegawai where kode_pegawai = '" + idpegawai + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                idpegawai = res.getInt("kode_pegawai");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
        return idpegawai;
    }

    private double getKodeLokasi(int baris) {
        TableModel tabelModel;
        tabelModel = tbl_Penjualan.getModel();
        tmpLokasi = tbl_Penjualan.getValueAt(baris, 3).toString();
        try {

            String sql = "SELECT * FROM `lokasi` WHERE nama_lokasi LIKE '" + tmpLokasi + "'";

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tmpKodeLokasi = res.getInt("kode_lokasi");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
        return tmpKodeLokasi;
    }

    private double getKonvPcs(int baris) {
        TableModel tabelModel;
        tabelModel = tbl_Penjualan.getModel();
        String tmpNamaKonv = tbl_Penjualan.getValueAt(baris, 4).toString();
        tmpKodeBarang = tbl_Penjualan.getValueAt(baris, 1).toString();
        try {

            String sql = "SELECT * FROM barang_konversi bk, konversi k"
                    + " WHERE k.kode_konversi = bk.kode_konversi"
                    + " and bk.kode_barang = " + tmpKodeBarang
                    + " and k.nama_konversi LIKE '" + tmpNamaKonv + "'";

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tmpKonvPcs = res.getDouble("bk.jumlah_konversi");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
        return tmpKonvPcs;
    }

    private void getHargaJual(int baris) {
        TableModel tabelModel;
        tabelModel = tbl_Penjualan.getModel();
        tmpKodeBarang = tbl_Penjualan.getValueAt(baris, 1).toString();
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
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    private void getQtyTambah(int baris) {
        TableModel tabelModel;
        tabelModel = tbl_Penjualan.getModel();
        int tmpJumlah = Integer.parseInt(tbl_Penjualan.getValueAt(baris, 5).toString());
        tmpKodeBarang = tbl_Penjualan.getValueAt(baris, 1).toString();
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
//            if (tmpKel.equalsIgnoreCase("2")) {
//                jmlKonv = tmpJmlKonv - 5;
//            } else {
            jmlKonv = tmpJmlKonv;
//            }
            jumlahTambah = tmpJumlah * jmlKonv;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    private void getQty(int baris) {
        TableModel tabelModel;
        tabelModel = tbl_Penjualan.getModel();
        tmpKodeBarang = tbl_Penjualan.getValueAt(baris, 1).toString();
        try {

            String sql = "SELECT * FROM barang_lokasi WHERE"
                    + " kode_barang = " + tmpKodeBarang
                    + " AND kode_lokasi = " + getKodeLokasi(baris);

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                jmlQty = res.getDouble("jumlah");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    private void lbl_SaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_SaveMouseClicked
        SaveData();

    }//GEN-LAST:event_lbl_SaveMouseClicked

    private void comCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comCustomerActionPerformed
        try {
            String sql = "select * from customer where nama_customer = '" + comCustomer.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString(1);
                String nama = res.getString(2);
                String alamat = res.getString(4);
//                String kode = res.getString(1);
                //    comCustomer.addItem(kode+" - "+nama);
                txt_Nama.setText(nama);
                txt_Alamat.setText(alamat);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }//GEN-LAST:event_comCustomerActionPerformed

    private void lbl_SaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_SaveMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_SaveMouseEntered

    private void editable() throws ParseException {

        String string = "2018-07-30 17:00:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        Date date2 = format.parse(string);

        if (date.after(date2)) {
            comCustomer.setEnabled(false);
            comOrder.setEnabled(false);
            comSalesman.setEnabled(false);
            comStaff.setEditable(false);
            comTOP.setEnabled(false);
            txt_faktur.setEditable(false);
            txt_tanggal.setEditable(false);
            checkverif.setEnabled(false);
            txt_item.setEditable(false);
            txt_jumQty.setEditable(false);
            txt_tbl_total.setEditable(false);
            txt_keterangan.setEditable(false);
            tbl_Penjualan.setEnabled(false);
        } else {
            comCustomer.setEnabled(true);
            comOrder.setEnabled(true);
            comSalesman.setEnabled(true);
            comStaff.setEditable(true);
            comTOP.setEnabled(true);
            txt_faktur.setEditable(true);
            txt_tanggal.setEditable(true);
            checkverif.setEnabled(true);
            txt_item.setEditable(true);
            txt_jumQty.setEditable(true);
            txt_tbl_total.setEditable(true);
            txt_keterangan.setEditable(true);
            tbl_Penjualan.setEnabled(true);
        }
    }


    private void lbl_nextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_nextMouseClicked
//        boolean isi = false;
//        do {
//            try {
//                String sql = "select no_faktur_pembelian from pembelian ORDER BY no_faktur_pembelian DESC";
//                java.sql.Connection conn = (Connection) Koneksi.configDB();
//                java.sql.Statement stm = conn.createStatement();
//                java.sql.ResultSet res = stm.executeQuery(sql);
//
//                editable();
//
//                boolean status = tempRs == null;
//                if (status) {
//                    tempRs = res;
//                }
//                boolean statusIsi = tempRs.previous();
//                System.out.println(statusIsi);
//                if (statusIsi) {
//                    String noNota = tempRs.getString("no_faktur_pembelian");
//                    txt_faktur.setText(noNota);
//                    System.out.println(noNota);
////                    System.out.println("prev");
//                    loadForm(noNota);
//                    loadTable(noNota);
//                    getQtyTambahTemp(txt_noNota.getText());
//                    break;
//
//                } else {
//                    tempRs.next();
//                    JOptionPane.showMessageDialog(this, "End of File");
//                }
//                res.close();
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.getMessage(),
//                        "Kesalahan", JOptionPane.WARNING_MESSAGE);
//            } catch (ParseException ex) {
//                Logger.getLogger(Pembelian_Transaksi.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } while (evt.equals(txt_noNota != null));
    }//GEN-LAST:event_lbl_nextMouseClicked

    private void lbl_prevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_prevMouseClicked
//        boolean isi = false;
//        do {
//            try {
//                String sql = "select no_faktur_pembelian from pembelian ORDER BY no_faktur_pembelian DESC";
//                java.sql.Connection conn = (Connection) Koneksi.configDB();
//                java.sql.Statement stm = conn.createStatement();
//                java.sql.ResultSet res = stm.executeQuery(sql);
//                boolean status = tempRs == null;
//
//                editable();
//
//                if (status) {
//                    tempRs = res;
//                }
//                if (tempRs.next()) {
//                    String noNota = tempRs.getString("no_faktur_pembelian");
//                    txt_noNota.setText(noNota);
//                    System.out.println(noNota);
//                    loadForm(noNota);
//                    loadTable(noNota);
//                    break;
//
//                } else {
//                    tempRs.previous();
//                    JOptionPane.showMessageDialog(this, "Start of File");
//                }
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
//                        "Kesalahan", JOptionPane.WARNING_MESSAGE);
//            }
//        } while (evt.equals(txt_noNota != null));
//        tempPrev++;
//    }                                      
//
//    private void txt_invKeyPressed(java.awt.event.KeyEvent evt) {                                   
//        int key = evt.getKeyCode();
//        if (key == 10) {
//            txt_diskon.requestFocus();
//        }
    }//GEN-LAST:event_lbl_prevMouseClicked

    private void tbl_PenjualanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PenjualanKeyReleased
        System.out.println("key released");
        if (tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 7) != ""
                && tbl_Penjualan.getValueAt(tbl_Penjualan.getSelectedRow(), 5) != ""
                && tbl_Penjualan.getRowCount() != -1
                && (tbl_Penjualan.getSelectedColumn() == 0
                || tbl_Penjualan.getSelectedColumn() == 1
                || tbl_Penjualan.getSelectedColumn() == 2
                || tbl_Penjualan.getSelectedColumn() == 3
                || tbl_Penjualan.getSelectedColumn() == 4
                || tbl_Penjualan.getSelectedColumn() == 5
                || tbl_Penjualan.getSelectedColumn() == 6
                || tbl_Penjualan.getSelectedColumn() == 7
                || tbl_Penjualan.getSelectedColumn() == 8
                || tbl_Penjualan.getSelectedColumn() == 9)) {
            HitungSubTotal();
            showQTY();
        }

    }//GEN-LAST:event_tbl_PenjualanKeyReleased

    private void tbl_PenjualanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PenjualanKeyTyped
        if (tbl_Penjualan.getSelectedColumn() == 8) {
            char vChar = evt.getKeyChar();
            if (!(Character.isDigit(vChar)
                    || (vChar == java.awt.event.KeyEvent.VK_BACK_SPACE)
                    || (vChar == java.awt.event.KeyEvent.VK_DELETE))) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_tbl_PenjualanKeyTyped

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        SaveData();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private int getId_penjualan() {
        int nilai_id = 0;
        try {
            String sql = "SELECT MAX(id_penjualan) FROM penjualan";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

//            System.out.println("test =" +sql);
//            System.out.println("k =" +kode_barang);
//            System.out.println("Select =" + comTableKonv.getSelectedItem());
            while (res.next()) {
                nilai_id = res.getInt(1);
                System.out.println("Nilai_id =" + ++nilai_id);
//                System.out.println("echo : " + tbl_Penjualan.getValueAt(selectedRow, 2).toString());

            }
            return nilai_id + 1;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
            e.printStackTrace();
        }

        return 0;
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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan_Penjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkverif;
    private javax.swing.JComboBox<String> comCustomer;
    private javax.swing.JComboBox<String> comOrder;
    private javax.swing.JComboBox<String> comSalesman;
    private javax.swing.JComboBox<String> comStaff;
    private javax.swing.JComboBox<String> comTOP;
    private javax.swing.JComboBox<String> comTableBarang;
    private javax.swing.JComboBox<String> comTableHarga;
    private javax.swing.JComboBox<String> comTableKode;
    private javax.swing.JComboBox<String> comTableKonv;
    private javax.swing.JComboBox<String> comTableLokasi;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel lbl_Save;
    private javax.swing.JLabel lbl_next;
    private javax.swing.JLabel lbl_prev;
    private javax.swing.JTable tbl_Penjualan;
    private javax.swing.JTextField txt_Alamat;
    private javax.swing.JTextField txt_Nama;
    private javax.swing.JTextField txt_faktur;
    private javax.swing.JTextField txt_item;
    private javax.swing.JTextField txt_jumQty;
    private javax.swing.JTextField txt_keterangan;
    private javax.swing.JTextField txt_tanggal;
    private javax.swing.JTextField txt_tbl_total;
    // End of variables declaration//GEN-END:variables
}
