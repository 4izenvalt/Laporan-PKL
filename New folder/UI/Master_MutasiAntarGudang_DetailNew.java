/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Java.*;
import Class.Koneksi;
import com.sun.glass.events.KeyEvent;
import java.awt.event.KeyAdapter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author USER
 */
public class Master_MutasiAntarGudang_DetailNew extends javax.swing.JFrame {

    /**
     * Creates new form Penjualan_penjualan
     */
    private Connect connection;
    private int nomorTable = 1;
    private int kode_barang = 1;
    private int jumlah_item = 0;
    private double jumlah_qty = 0;
    private String no_bukti = "";
    private double[] qty_sesudah_lama;
    private String[] lokasi_asal_lama;
    private String[] lokasi_tujuan_lama;
    private String[] kode_barang_lama;
    private String[] id_detail_mutasi;
    private String[] identitas_konversi;
    private int row_update;
    private int update_insert_jumlah_data = 0;
    private boolean update = false;
    private boolean update_insert = false;
//    private List<Integer> kode_lokasi_asal = new ArrayList<Integer>();
//    private List<Integer> kode_lokasi_tujuan= new ArrayList<Integer>();
    private List<Integer> kode_konversi = new ArrayList<Integer>();

    public Master_MutasiAntarGudang_DetailNew() {
        initComponents();
        loadKodeBarang();
        this.setLocationRelativeTo(null);

    }

    public Master_MutasiAntarGudang_DetailNew(String no_bukti, boolean update) {
        this.update = update;
        initComponents();
        loadComTableBarang();
        loadKodeBarang();
        this.no_bukti = no_bukti;
        loadLihatDetail();
        isi_qty_lama();
        isi_lokasi_lama();
        AutoCompleteDecorator.decorate(comTableBarang);
        AutoCompleteDecorator.decorate(comKodeBarang);
        this.setLocationRelativeTo(null);
        isi_jumlah_item_qty();
    }

    public Master_MutasiAntarGudang_DetailNew(Connect connection) {
        initComponents();
        this.connection = connection;
        loadComTableBarang();
        tanggal_jam_sekarang();
        loadKodeBarang();
        autonumber();
        AutoCompleteDecorator.decorate(comTableBarang);
        AutoCompleteDecorator.decorate(comKodeBarang);

        this.setLocationRelativeTo(null);

    }

    void isi_jumlah_item_qty() {
        try {
            String sql = "select qty from mutasi_antar_gudang_detail where no_bukti ='" + no_bukti + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                jumlah_qty += Double.parseDouble(res.getString("qty"));
                jumlah_item++;
            }
            txt_jumlah_item.setText("Jumlah Item = " + jumlah_item);
            txt_jumlah_qty.setText("Jumlah Item = " + jumlah_qty);
            res.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadLihatDetail() {

        try {
            String sql = "select bk.identitas_konversi, mdt.no_bukti, md.tanggal,mdt.kode_barang, mdt.nama_barang, asal.nama_lokasi as asal,k.nama_konversi,mdt.qty,tujuan.nama_lokasi as tujuan from mutasi_antar_gudang_detail mdt join mutasi_antar_gudang md \n"
                    + "on md.no_bukti = mdt.no_bukti \n"
                    + "join lokasi asal on asal.kode_lokasi=mdt.kode_lokasi_asal\n"
                    + "join lokasi tujuan on tujuan.kode_lokasi = mdt.kode_lokasi_tujuan\n"
                    + "join konversi k on k.kode_konversi=mdt.satuan join barang_konversi bk on bk.kode_barang = mdt.kode_barang and bk.kode_konversi = mdt.satuan\n"
                    + "where mdt.no_bukti ='" + no_bukti + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) tbl_tambah_mutasi.getModel();
            int i = 0;

            while (res.next()) {
                model.setValueAt(i + 1, i, 0);
                txt_noBukti.setText(res.getString("no_bukti"));
                txt_tgl.setText(res.getString("tanggal"));
                model.setValueAt(res.getString("kode_barang"), i, 1);
                model.setValueAt(res.getString("nama_barang"), i, 2);
                model.setValueAt(res.getString("asal"), i, 3);

                String satuan = res.getString("nama_konversi");
                model.setValueAt(satuan, i, 4);
                model.setValueAt(res.getString("qty"), i, 5);
                model.setValueAt(res.getString("tujuan"), i, 6);
                model.addRow(new Object[]{"", "", "", "", "", "", ""});
                i++;
            }

            row_update = tbl_tambah_mutasi.getRowCount();
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
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
        p.start();
    }

    void loadComTableBarang() {
        try {
            String sql = "select * from barang order by nama_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(4);
                comTableBarang.addItem(name);
            }
            res.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadComTableLokasi() {
        try {
            String sql = "select * from barang b join barang_lokasi bl on b.kode_barang = bl.kode_barang join lokasi l on l.kode_lokasi = bl.kode_lokasi where b.kode_barang = '" + this.kode_barang + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            comTableAsal.removeAllItems();
            while (res.next()) {
                String name = res.getString("nama_lokasi");
                comTableAsal.addItem(name);
                //comTableTujuan.addItem(name);
            }
            res.close();

            String sql1 = "select * from lokasi";
            java.sql.ResultSet res1 = stm.executeQuery(sql1);
            comTableTujuan.removeAllItems();

            while (res1.next()) {
                String name = res1.getString("nama_lokasi");
                //comTableAsal.addItem(name);
                comTableTujuan.addItem(name);
            }
            res1.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadKodeBarang() {
        try {
            String sql = "select * from barang order by kode_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(1);

                comKodeBarang.addItem(name);
            }
            res.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadNumberTable() {
        int baris = tbl_tambah_mutasi.getRowCount();
        for (int a = 0; a < baris; a++) {
            String nomor = String.valueOf(a + 1);
            tbl_tambah_mutasi.setValueAt(nomor + ".", a, 0);
        }

    }

    void loadComTableSatuan() {
        try {
            /// identitas konversi tidak urut
            String sql = "select * from konversi k, barang_konversi bk, barang b "
                    + "where b.kode_barang = bk.kode_barang "
                    + "and bk.kode_konversi = k.kode_konversi "
                    + "and b.kode_barang = '" + this.kode_barang + "' order by bk.kode_barang_konversi asc";
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
            comTableKonv.setSelectedIndex(0);
            res.close();
            conn.close();
//            comKodeKonv.addItem(name);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comTableBarang = new javax.swing.JComboBox<>();
        comTableKonv = new javax.swing.JComboBox<>();
        comTableAsal = new javax.swing.JComboBox<>();
        comTableTujuan = new javax.swing.JComboBox<>();
        comKodeKonv = new javax.swing.JComboBox<>();
        comKodeBarang = new javax.swing.JComboBox<>();
        txt_qty = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_tambah_mutasi = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        txt_jumlah_item = new javax.swing.JTextField();
        txt_jumlah_qty = new javax.swing.JTextField();
        txt_keterangan = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        txt_noBukti = new javax.swing.JTextField();
        txt_tgl = new javax.swing.JTextField();

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

        comKodeKonv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comKodeKonv.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                comKodeKonvAncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        comKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comKodeBarangActionPerformed(evt);
            }
        });
        comKodeBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comKodeBarangKeyPressed(evt);
            }
        });

        txt_qty.setText("jTextField1");
        txt_qty.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_qtyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_qtyFocusLost(evt);
            }
        });
        txt_qty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_qtyMouseExited(evt);
            }
        });
        txt_qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_qtyActionPerformed(evt);
            }
        });
        txt_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_qtyKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

        tbl_tambah_mutasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode", "Barang", "Asal", "Satuan (1/2/3)", "Jumlah", "Tujuan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_tambah_mutasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_tambah_mutasiMouseClicked(evt);
            }
        });
        tbl_tambah_mutasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_tambah_mutasiKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_tambah_mutasi);
        if (tbl_tambah_mutasi.getColumnModel().getColumnCount() > 0) {
            tbl_tambah_mutasi.getColumnModel().getColumn(0).setResizable(false);
            tbl_tambah_mutasi.getColumnModel().getColumn(1).setResizable(false);
            tbl_tambah_mutasi.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comKodeBarang));
            tbl_tambah_mutasi.getColumnModel().getColumn(2).setResizable(false);
            tbl_tambah_mutasi.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comTableBarang));
            tbl_tambah_mutasi.getColumnModel().getColumn(3).setResizable(false);
            tbl_tambah_mutasi.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comTableAsal));
            tbl_tambah_mutasi.getColumnModel().getColumn(4).setResizable(false);
            tbl_tambah_mutasi.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comTableKonv));
            tbl_tambah_mutasi.getColumnModel().getColumn(5).setResizable(false);
            tbl_tambah_mutasi.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(txt_qty));
            tbl_tambah_mutasi.getColumnModel().getColumn(6).setResizable(false);
            tbl_tambah_mutasi.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comTableTujuan));
        }

        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        jLabel19.setText("F12 - Save");
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Clear-icon.png"))); // NOI18N
        jLabel20.setText("F9 - Clear");
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
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

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        jLabel26.setText("Esc - Exit");
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Kasir");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Nama Kasir");

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));

        txt_jumlah_item.setBackground(new java.awt.Color(0, 0, 0));
        txt_jumlah_item.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_jumlah_item.setForeground(new java.awt.Color(255, 204, 0));
        txt_jumlah_item.setText("Jumlah Item = 0");
        txt_jumlah_item.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_jumlah_item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_jumlah_itemMouseClicked(evt);
            }
        });
        txt_jumlah_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlah_itemActionPerformed(evt);
            }
        });

        txt_jumlah_qty.setBackground(new java.awt.Color(0, 0, 0));
        txt_jumlah_qty.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_jumlah_qty.setForeground(new java.awt.Color(255, 204, 0));
        txt_jumlah_qty.setText("Jumlah Qty = 0");
        txt_jumlah_qty.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_jumlah_qty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_jumlah_qtyMouseClicked(evt);
            }
        });
        txt_jumlah_qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlah_qtyActionPerformed(evt);
            }
        });

        txt_keterangan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_keterangan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_keteranganMouseClicked(evt);
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

        txt_noBukti.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_noBukti.setEnabled(false);

        txt_tgl.setEditable(false);
        txt_tgl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tglActionPerformed(evt);
            }
        });

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
                            .addComponent(jSeparator2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel17))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_noBukti, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                            .addComponent(txt_tgl)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel21)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel26))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel34)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 459, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel32))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                        .addComponent(txt_jumlah_item, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_jumlah_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(161, 161, 161))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel26)
                    .addComponent(jLabel21)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(txt_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txt_noBukti, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(8, 8, 8)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_jumlah_item, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_jumlah_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_jumlah_itemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_jumlah_itemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlah_itemMouseClicked

    private void txt_jumlah_qtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_jumlah_qtyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlah_qtyMouseClicked

    private void jLabel21AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel21AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel21AncestorAdded

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked

    }//GEN-LAST:event_jLabel21MouseClicked

    private void txt_keteranganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_keteranganMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_keteranganMouseClicked

    private void txt_jumlah_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlah_itemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlah_itemActionPerformed

    private void tbl_tambah_mutasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_tambah_mutasiMouseClicked
        int baris = tbl_tambah_mutasi.getSelectedRow();
        int kolom = tbl_tambah_mutasi.getSelectedColumn();

        TableModel model = tbl_tambah_mutasi.getModel();

        int tabel = tbl_tambah_mutasi.getRowCount();
        //        model.setValueAt(rptabelkembali(String.valueOf(harga)), i - 1, 6);
        if (evt.getClickCount() == 2) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "baris : " + baris + " kolom : " + kolom);

            System.out.println("Double Click");

        }
    }//GEN-LAST:event_tbl_tambah_mutasiMouseClicked

    private void comTableBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableBarangActionPerformed
        int kode_barang = 0;
        int baris = tbl_tambah_mutasi.getRowCount();
        TableModel tabelModel;
        tabelModel = tbl_tambah_mutasi.getModel();
        int selectedRow = tbl_tambah_mutasi.getSelectedRow();

        try {
            String sql = "select * from barang where nama_barang = '" + comTableBarang.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString(1);
                this.kode_barang = Integer.parseInt(kode);
                loadNumberTable();
                loadComTableSatuan();
                loadComTableLokasi();
                String konv = comTableKonv.getSelectedItem().toString();
                if (selectedRow != -1) {
                    comTableAsal.getItemAt(0);
                    tbl_tambah_mutasi.setValueAt(comTableAsal.getItemAt(0), selectedRow, 3);
                    tbl_tambah_mutasi.setValueAt(comTableKonv.getItemAt(0), selectedRow, 4);
                    tbl_tambah_mutasi.setValueAt(kode, selectedRow, 1);

//                    nomorTable++;
                }
            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_comTableBarangActionPerformed
//    void edit_sisa(){
//        tbl_tambah_mutas
//    }

    void simpan_data() {
        if (update && !update_insert) {
            System.out.println("====================================================================");
            update_data();
        } else {
            try {
                //String date = txt_tgl.getText();
                Koneksi Koneksi = new Koneksi();
                Connection con = Koneksi.configDB();
                Statement st = con.createStatement();
                int row = 0;
                int jumlah_data = 0;

                int indeks_awal = 0;
                if (update_insert) {
                    jumlah_data = update_insert_jumlah_data + row_update - 1;
                    indeks_awal = row_update - 1;
                } else {
                    jumlah_data = tbl_tambah_mutasi.getRowCount();
                }
                System.out.println(jumlah_data);
                int jumlah_item = 0;
                double jumlah_qty = 0;
                boolean cukup = true;
                String kode_barang = "", nama_gudang = "";
                String kode_konversi = "", lokasi_asal = "", lokasi_tujuan = "";
                //Memasukkan data perbaris ke tabel mutasi detail
                for (int i = indeks_awal; i < jumlah_data; i++) {
                    //Konversi satuan
                    String satuan = String.valueOf(tbl_tambah_mutasi.getValueAt(i, 4));
                    String sql1 = "select kode_konversi from barang_konversi where kode_konversi = (select kode_konversi from konversi where nama_konversi='" + satuan + "') and kode_barang = '" + tbl_tambah_mutasi.getValueAt(i, 1) + "'";
                    Statement st1 = con.createStatement();
                    java.sql.ResultSet res1 = st1.executeQuery(sql1);
                    while (res1.next()) {
                        kode_konversi = res1.getString(1);
                    }
                    res1.close();

                    //Cek ketersediaan barang di gudang tertentu
                    if (i == indeks_awal) {
                        for (int j = indeks_awal; j < jumlah_data; j++) {
                            //Konversi lokasi cek
                            String sql3a = "select kode_lokasi from lokasi where nama_lokasi = '" + tbl_tambah_mutasi.getValueAt(j, 3) + "'";
                            Statement st3a = con.createStatement();
                            java.sql.ResultSet res3a = st3a.executeQuery(sql3a);
                            String lokasi_cek = "";
                            while (res3a.next()) {
                                lokasi_cek = res3a.getString(1);
                            }
                            res3a.close();

                            //Konversi satuan cek
                            String satuan_cek = String.valueOf(tbl_tambah_mutasi.getValueAt(j, 4));
                            String sql1a = "select kode_konversi from barang_konversi where kode_konversi = (select kode_konversi from konversi where nama_konversi='" + satuan_cek + "') and kode_barang = '" + tbl_tambah_mutasi.getValueAt(i, 1) + "'";
                            Statement st1a = con.createStatement();
                            java.sql.ResultSet res1a = st1a.executeQuery(sql1a);
                            String kode_konversi_cek = "";
                            while (res1a.next()) {
                                kode_konversi_cek = res1a.getString(1);
                            }
                            res1a.close();

                            double qty = Double.parseDouble(String.valueOf(tbl_tambah_mutasi.getValueAt(j, 5)));

                            //qty dikali jumlah_konversi
                            double qty_sesudah = qty_konversi(String.valueOf(tbl_tambah_mutasi.getValueAt(j, 1)), kode_konversi_cek, qty);

                            String sql4 = "select * from barang_lokasi where kode_barang ='" + String.valueOf(tbl_tambah_mutasi.getValueAt(j, 1)) + "' and kode_lokasi = '" + lokasi_cek + "'";
                            System.out.println(sql4);
                            java.sql.Statement stm4 = con.createStatement();
                            java.sql.ResultSet res4 = stm4.executeQuery(sql4);
                            System.out.println("qty_sesduah " + qty_sesudah);

                            while (res4.next()) {
                                int stok = Integer.parseInt(res4.getString("jumlah"));
                                System.out.println("stok " + stok);
                                kode_barang = String.valueOf(tbl_tambah_mutasi.getValueAt(j, 1));
                                nama_gudang = String.valueOf(tbl_tambah_mutasi.getValueAt(j, 3));
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

                    //Konversi lokasi asal
                    String sql2 = "select kode_lokasi from lokasi where nama_lokasi = '" + tbl_tambah_mutasi.getValueAt(i, 3) + "'";
                    Statement st2 = con.createStatement();
                    java.sql.ResultSet res2 = st2.executeQuery(sql2);
                    while (res2.next()) {
                        lokasi_asal = res2.getString(1);
                    }
                    res2.close();

                    //Konversi lokasi tujuan
                    String sql3 = "select kode_lokasi from lokasi where nama_lokasi = '" + tbl_tambah_mutasi.getValueAt(i, 6) + "'";
                    Statement st3 = con.createStatement();
                    java.sql.ResultSet res3 = st3.executeQuery(sql3);
                    while (res3.next()) {
                        lokasi_tujuan = res3.getString(1);
                    }
                    res3.close();

                    if (!cukup) {
                        //keluar for untuk input semua baris
                        break;
                    } else {
                        //Memasukkan baris ke-i
                        double jumlah = Double.parseDouble(String.valueOf(tbl_tambah_mutasi.getValueAt(i, 5)));
                        double qty_sesudah = qty_konversi(String.valueOf(tbl_tambah_mutasi.getValueAt(i, 1)), kode_konversi, jumlah);
                        String sql = "insert into mutasi_antar_gudang_detail( no_bukti, kode_barang, nama_barang, kode_lokasi_asal, satuan, qty, kode_lokasi_tujuan)"
                                + "value('" + txt_noBukti.getText() + "','" + tbl_tambah_mutasi.getValueAt(i, 1) + "','" + tbl_tambah_mutasi.getValueAt(i, 2) + "','"
                                + lokasi_asal + "','" + kode_konversi + "','" + jumlah
                                + "','" + lokasi_tujuan + "')";
                        System.out.println(sql);
                        row = st.executeUpdate(sql);

                        //mengupdate stok dimasing-masing gudang
                        String mengurangi = "update barang_lokasi set jumlah= jumlah - " + qty_sesudah + " where kode_barang = '" + tbl_tambah_mutasi.getValueAt(i, 1) + "' and kode_lokasi = '" + lokasi_asal + "'";
                        Statement st_kurang = con.createStatement();
                        row = st_kurang.executeUpdate(mengurangi);

                        String menambah = "update barang_lokasi set jumlah= jumlah + " + qty_sesudah + " where kode_barang = '" + tbl_tambah_mutasi.getValueAt(i, 1) + "' and kode_lokasi = '" + lokasi_tujuan + "'";
                        Statement st_tambah = con.createStatement();
                        row = st_tambah.executeUpdate(menambah);

                        jumlah_item++;
                        jumlah_qty += jumlah; //jumlah qty pada form
                    }

                }

                //Memasukkan data ke mutasi gudang umum
                if (cukup && !update_insert) {
                    SimpleDateFormat format_tanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = format_tanggal.format(System.currentTimeMillis());
                    System.out.println("ini tanggalnya" + date);

                    String sql = "insert into mutasi_antar_gudang( no_bukti, no_permintaan, user, keterangan, jumlah_item, jumlah_qty, status, tanggal)"
                            + "value('" + txt_noBukti.getText() + "','-','"
                            + 3 + "','" + txt_keterangan.getText() + "','" + jumlah_item
                            + "','" + jumlah_qty
                            + "','" + "-" + "','" + date + "')";
                    System.out.println(sql);
                    row = st.executeUpdate(sql);

                } else if (cukup && update_insert) {
                    //JUMLAH ITEM MASIH SALAH
                    this.jumlah_item ++;
                    this.jumlah_qty += Double.parseDouble(String.valueOf(tbl_tambah_mutasi.getValueAt(tbl_tambah_mutasi.getRowCount()-1, 5)));
                    String sql = "update mutasi_antar_gudang set jumlah_item ='"+this.jumlah_item+"', jumlah_qty='"+this.jumlah_qty+"' where no_bukti='"+no_bukti+"'";
                    System.out.println(sql);
                    row = st.executeUpdate(sql);
                } else if (!cukup) {
                    JOptionPane.showMessageDialog(null, "Stok barang " + kode_barang + " di gudang " + nama_gudang + " tidak cukup");
                }
                if (row >= 1) {
                    JOptionPane.showMessageDialog(null, "data sudah ditambahkan ke database", "informasi", JOptionPane.INFORMATION_MESSAGE);
                    con.close();
                } else {

                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "data tidak dimasukkan ke database" + e, "informasi", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Tidak ada data yang dimasukkan");
            }
        }
    }
    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        int simpan = 1;
        simpan = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menyimpan data ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if (simpan == 0) {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
            simpan_data();
        }

    }//GEN-LAST:event_jLabel19MouseClicked
    double qty_konversi(String kode_barang, String kode_konversi, double qty) {
        double hasil = 0;

        try {

            String sql = "select * from barang_konversi where kode_barang='" + kode_barang + "' and (kode_konversi ='" + kode_konversi + "' || identitas_konversi='1') order by identitas_konversi asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            System.out.println(sql);
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

    void isi_lokasi_lama() {
        try {
            Koneksi Koneksi = new Koneksi();
            Connection con = Koneksi.configDB();
            Statement st = con.createStatement();
            int jumlah_data = tbl_tambah_mutasi.getRowCount();
            lokasi_asal_lama = new String[jumlah_data];
            lokasi_tujuan_lama = new String[jumlah_data];
            for (int i = 0; i < jumlah_data - 1; i++) {
                String sql1a = "select kode_lokasi_asal, kode_lokasi_tujuan from mutasi_antar_gudang_detail where no_bukti = '" + this.no_bukti + "'";
                Statement st1a = con.createStatement();
                java.sql.ResultSet res1a = st1a.executeQuery(sql1a);
                String asal = "";
                String tujuan = "";
                while (res1a.next()) {
                    asal = res1a.getString("kode_lokasi_asal");
                    tujuan = res1a.getString("kode_lokasi_tujuan");
                }
                res1a.close();
                lokasi_asal_lama[i] = asal;
                lokasi_tujuan_lama[i] = tujuan;
            }
            con.close();
        } catch (SQLException e) {

        }
    }

    void isi_qty_lama() {
        try {
            Koneksi Koneksi = new Koneksi();
            Connection con = Koneksi.configDB();
            Statement st = con.createStatement();
            int jumlah_data = tbl_tambah_mutasi.getRowCount();
            qty_sesudah_lama = new double[jumlah_data];
            kode_barang_lama = new String[jumlah_data];
            for (int i = 0; i < jumlah_data - 1; i++) {
                //Konversi satuan cek
                String satuan_cek = String.valueOf(tbl_tambah_mutasi.getValueAt(i, 4));
                String satuan = String.valueOf(tbl_tambah_mutasi.getValueAt(i, 4));
                String sql1a = "select kode_konversi from barang_konversi where kode_konversi = (select kode_konversi from konversi where nama_konversi='" + satuan + "') and kode_barang = '" + tbl_tambah_mutasi.getValueAt(i, 1) + "'";
                Statement st1a = con.createStatement();
                java.sql.ResultSet res1a = st1a.executeQuery(sql1a);
                String kode_konversi_cek = "";
                while (res1a.next()) {
                    kode_konversi_cek = res1a.getString(1);
                }
                res1a.close();

                double qty = Double.parseDouble(String.valueOf(tbl_tambah_mutasi.getValueAt(i, 5)));
                double qty_sesudah = qty_konversi(String.valueOf(tbl_tambah_mutasi.getValueAt(i, 1)), kode_konversi_cek, qty);
                qty_sesudah_lama[i] = qty_sesudah;
                kode_barang_lama[i] = String.valueOf(tbl_tambah_mutasi.getValueAt(i, 1));
            }
            con.close();
        } catch (SQLException e) {

        }

    }

    void id_detail_mutasi() {
        try {
            String sql = "select id_detail_mutasi_gudang from mutasi_antar_gudang_detail where no_bukti='" + this.no_bukti + "' order by id_detail_mutasi_gudang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            int i = 0;
            while (res.next()) {
                String id = res.getString("id_detail_mutasi_gudang");
                id_detail_mutasi[i] = id;
                i++;
            }
            res.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void update_data() {
        try {
            //String date = txt_tgl.getText();
            Koneksi Koneksi = new Koneksi();
            Connection con = Koneksi.configDB();
            Statement st = con.createStatement();
            int row = 0;
            int jumlah_data = tbl_tambah_mutasi.getRowCount();
            //mengisi detil mutasi
            id_detail_mutasi = new String[row_update - 1];
            id_detail_mutasi();
            System.out.println(row_update);
            int jumlah_item = 0;
            double jumlah_qty = 0;
            boolean cukup = true;
            String kode_barang = "", nama_gudang = "";
            String kode_konversi = "", lokasi_asal = "", lokasi_tujuan = "";

            //Memasukkan data perbaris ke tabel mutasi detail
            for (int i = 0; i < row_update - 1; i++) {
                //Konversi satuan
                String satuan = String.valueOf(tbl_tambah_mutasi.getValueAt(i, 4));
                String sql1 = "select kode_konversi from barang_konversi where kode_konversi = (select kode_konversi from konversi where nama_konversi='" + satuan + "') and kode_barang = '" + tbl_tambah_mutasi.getValueAt(i, 1) + "'";
                Statement st1 = con.createStatement();
                java.sql.ResultSet res1 = st1.executeQuery(sql1);
                while (res1.next()) {
                    kode_konversi = res1.getString(1);
                }
                res1.close();
                System.out.println("satuan" + satuan);

                //Cek ketersediaan barang di gudang tertentu
                if (i == 0) {
                    for (int j = 0; j < row_update - 1; j++) {
                        //Konversi lokasi cek
                        String sql3a = "select kode_lokasi from lokasi where nama_lokasi = '" + tbl_tambah_mutasi.getValueAt(j, 3) + "'";
                        Statement st3a = con.createStatement();
                        java.sql.ResultSet res3a = st3a.executeQuery(sql3a);
                        String lokasi_cek = "";
                        while (res3a.next()) {
                            lokasi_cek = res3a.getString(1);
                        }
                        res3a.close();

                        //Konversi satuan cek
                        String satuan_cek = String.valueOf(tbl_tambah_mutasi.getValueAt(j, 4));
                        String sql1a = "select kode_konversi from barang_konversi where kode_konversi = (select kode_konversi from konversi where nama_konversi='" + satuan_cek + "') and kode_barang = '" + tbl_tambah_mutasi.getValueAt(i, 1) + "'";
                        System.out.println(sql1a);
                        Statement st1a = con.createStatement();
                        java.sql.ResultSet res1a = st1a.executeQuery(sql1a);
                        String kode_konversi_cek = "";
                        while (res1a.next()) {
                            kode_konversi_cek = res1a.getString(1);
                        }
                        res1a.close();

                        double qty = Double.parseDouble(String.valueOf(tbl_tambah_mutasi.getValueAt(j, 5)));

                        //qty dikali jumlah_konversi
                        double qty_sesudah = qty_konversi(String.valueOf(tbl_tambah_mutasi.getValueAt(j, 1)), kode_konversi_cek, qty);

                        String sql4 = "select * from barang_lokasi where kode_barang ='" + String.valueOf(tbl_tambah_mutasi.getValueAt(j, 1)) + "' and kode_lokasi = '" + lokasi_cek + "'";
                        System.out.println(sql4);
                        java.sql.Statement stm4 = con.createStatement();
                        java.sql.ResultSet res4 = stm4.executeQuery(sql4);
                        System.out.println("qty_sesduah " + qty_sesudah);

                        while (res4.next()) {
                            int stok = Integer.parseInt(res4.getString("jumlah"));
                            System.out.println("stok " + stok);
                            kode_barang = String.valueOf(tbl_tambah_mutasi.getValueAt(j, 1));
                            nama_gudang = String.valueOf(tbl_tambah_mutasi.getValueAt(j, 3));
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

                //Konversi lokasi asal
                String sql2 = "select kode_lokasi from lokasi where nama_lokasi = '" + tbl_tambah_mutasi.getValueAt(i, 3) + "'";
                Statement st2 = con.createStatement();
                java.sql.ResultSet res2 = st2.executeQuery(sql2);
                while (res2.next()) {
                    lokasi_asal = res2.getString(1);
                }
                res2.close();

                //Konversi lokasi tujuan
                String sql3 = "select kode_lokasi from lokasi where nama_lokasi = '" + tbl_tambah_mutasi.getValueAt(i, 6) + "'";
                Statement st3 = con.createStatement();
                java.sql.ResultSet res3 = st3.executeQuery(sql3);
                while (res3.next()) {
                    lokasi_tujuan = res3.getString(1);
                }
                res3.close();

                if (!cukup) {
                    //keluar for untuk input semua baris
                    break;
                } else {
                    //Memasukkan baris ke-i
                    double jumlah = Double.parseDouble(String.valueOf(tbl_tambah_mutasi.getValueAt(i, 5)));
                    double qty_sesudah = qty_konversi(String.valueOf(tbl_tambah_mutasi.getValueAt(i, 1)), kode_konversi, jumlah);
//                    String sql = "insert into mutasi_antar_gudang_detail( no_bukti, kode_barang, nama_barang, kode_lokasi_asal, satuan, qty, kode_lokasi_tujuan)"
//                            + "value('" + txt_noBukti.getText() + "','" + tbl_tambah_mutasi.getValueAt(i, 1) + "','" + tbl_tambah_mutasi.getValueAt(i, 2) + "','"
//                            + lokasi_asal + "','" + kode_konversi + "','" + jumlah
//                            + "','" + lokasi_tujuan + "')";
                    String sql = "update mutasi_antar_gudang_detail set kode_barang ='" + tbl_tambah_mutasi.getValueAt(i, 1) + "', nama_barang='" + tbl_tambah_mutasi.getValueAt(i, 2) + "', kode_lokasi_asal ='" + lokasi_asal + "', satuan='" + kode_konversi + "', qty='" + jumlah + "', kode_lokasi_tujuan = '" + lokasi_tujuan + "' where no_bukti='" + txt_noBukti.getText() + "' and id_detail_mutasi_gudang='" + id_detail_mutasi[i] + "'";
                    System.out.println(sql);
                    row = st.executeUpdate(sql);

                    //mengupdate stok dimasing-masing gudang dengan qty_sesudah lama
                    String mengurangi_lama = "update barang_lokasi set jumlah= jumlah + " + qty_sesudah_lama[i] + " where kode_barang = '" + kode_barang_lama[i] + "' and kode_lokasi = '" + lokasi_asal_lama[i] + "'";
                    Statement st_kurang_lama = con.createStatement();
                    row = st_kurang_lama.executeUpdate(mengurangi_lama);
                    System.out.println(mengurangi_lama);

                    String menambah_lama = "update barang_lokasi set jumlah= jumlah - " + qty_sesudah_lama[i] + " where kode_barang = '" + kode_barang_lama[i] + "' and kode_lokasi = '" + lokasi_tujuan_lama[i] + "'";
                    Statement st_tambah_lama = con.createStatement();
                    row = st_tambah_lama.executeUpdate(menambah_lama);
                    System.out.println(menambah_lama);

                    //mengupdate stok dimasing-masing gudang dengan qty_sesudah baru
                    String mengurangi = "update barang_lokasi set jumlah= jumlah - " + qty_sesudah + " where kode_barang = '" + tbl_tambah_mutasi.getValueAt(i, 1) + "' and kode_lokasi = '" + lokasi_asal + "'";
                    Statement st_kurang = con.createStatement();
                    row = st_kurang.executeUpdate(mengurangi);
                    System.out.println(mengurangi);

                    String menambah = "update barang_lokasi set jumlah= jumlah + " + qty_sesudah + " where kode_barang = '" + tbl_tambah_mutasi.getValueAt(i, 1) + "' and kode_lokasi = '" + lokasi_tujuan + "'";
                    Statement st_tambah = con.createStatement();
                    row = st_tambah.executeUpdate(menambah);
                    System.out.println(menambah);

                    System.out.println("ini i " + i);
                    System.out.println("ini row count " + (row_update - 1));
                    jumlah_item++;
                    jumlah_qty += jumlah; //jumlah qty pada form
                }

            }
            //menambahkan data apabila ada data tambahan
            if (row_update != jumlah_data) {
                update_insert = true;
                update_insert_jumlah_data = jumlah_data - row_update + 1;
                System.out.println("update_insert_jumlah_data adalah " + update_insert_jumlah_data);
                simpan_data();
            } else if (tbl_tambah_mutasi.getValueAt(row_update - 1, 1) != "" && tbl_tambah_mutasi.getValueAt(row_update - 1, 2) != "" && tbl_tambah_mutasi.getValueAt(row_update - 1, 3) != "" && tbl_tambah_mutasi.getValueAt(row_update - 1, 4) != "" && tbl_tambah_mutasi.getValueAt(row_update - 1, 5) != "" && tbl_tambah_mutasi.getValueAt(row_update - 1, 6) != "") {
                System.out.println("asdasdasdasdasdasdasdasdas");
                update_insert = true;
                update_insert_jumlah_data = 1;
                simpan_data();
            }

            //Memasukkan data ke mutasi gudang umum
            if (cukup) {
//                SimpleDateFormat format_tanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String date = format_tanggal.format(System.currentTimeMillis());
//                System.out.println("ini tanggalnya" + date);
//                String sql = "update mutasi_antar_gudang set keterangan='" + txt_keterangan.getText() + "',jumlah_item='" + jumlah_item + "', jumlah_qty='" + jumlah_qty + "' where no_bukti='" + txt_noBukti.getText() + "'";
//                System.out.println(sql);
//                row = st.executeUpdate(sql);

            } else {
                JOptionPane.showMessageDialog(null, "Stok barang " + kode_barang + " di gudang " + nama_gudang + " tidak cukup");
            }
            if (row >= 1) {
                JOptionPane.showMessageDialog(null, "data berhasil diperbarui", "informasi", JOptionPane.INFORMATION_MESSAGE);
                con.close();
            } else {

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "data tidak diperbarui" + e, "informasi", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Tidak ada data diperbarui");
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
            conn.close();

        } catch (NullPointerException ex) {
            txt_noBukti.setText("MG18-00001");
        } catch (Exception ex) {
            System.out.println(ex.toString());
            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);

        }
    }

    public static String rightPadZeros(String str, int num) {
        return String.format("%05d", Integer.parseInt(str));
    }
    private void comKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comKodeBarangActionPerformed
        int kode_barang = 0;
        int baris = tbl_tambah_mutasi.getRowCount();
        TableModel tabelModel;
        tabelModel = tbl_tambah_mutasi.getModel();
        int selectedRow = tbl_tambah_mutasi.getSelectedRow();

        try {
            String sql = "select * from barang where kode_barang = '" + comKodeBarang.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String nama_barang = res.getString(4);
                this.kode_barang = Integer.parseInt(res.getString(1));
                loadNumberTable();
                loadComTableSatuan();
                loadComTableLokasi();

                if (selectedRow != -1) {
                    tbl_tambah_mutasi.setValueAt(nama_barang, selectedRow, 2);
                    tbl_tambah_mutasi.setValueAt(comTableAsal.getItemAt(0), selectedRow, 3);
                    tbl_tambah_mutasi.setValueAt(comTableKonv.getItemAt(0), selectedRow, 4);
//                    nomorTable++;
                }
            }

            res.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_comKodeBarangActionPerformed
    private void removeSelectedRows(JTable table) {
        int Hapus = 1;
        Hapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin mau menghapus baris ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if (Hapus == 0) {
            DefaultTableModel model = (DefaultTableModel) this.tbl_tambah_mutasi.getModel();
            int[] rows = table.getSelectedRows();
            for (int i = 0; i < rows.length; i++) {
                System.out.println("wor ke-i adalah " + rows[i]);
                if (rows[i] == 0 || rows.length == 1) {
                    if (jumlah_item != 0) {
                        jumlah_item--;
                    }
                    if (tbl_tambah_mutasi.getValueAt(rows[i], 5).toString() != "") {
                        jumlah_qty -= Double.parseDouble(tbl_tambah_mutasi.getValueAt(rows[i], 5).toString());
                    }
                    txt_jumlah_item.setText("Jumlah Item = " + String.valueOf(jumlah_item));
                    txt_jumlah_qty.setText("Jumlah Qty = " + String.valueOf(jumlah_qty));
                    model.removeRow(rows[i]);
                    tbl_tambah_mutasi.setRowSelectionInterval(0, 0);
                    System.out.println("if 1");

                } else {
                    System.out.println("if 2");
                    if (i == 0) {
                        if (jumlah_item != 0) {
                            jumlah_item--;
                        }
                        if (tbl_tambah_mutasi.getValueAt(rows[i], 5).toString() != "") {
                            jumlah_qty -= Double.parseDouble(tbl_tambah_mutasi.getValueAt(rows[i], 5).toString());
                        }
                        txt_jumlah_item.setText("Jumlah Item = " + String.valueOf(jumlah_item));
                        txt_jumlah_qty.setText("Jumlah Qty = " + String.valueOf(jumlah_qty));
                        model.removeRow(rows[i]);
                        tbl_tambah_mutasi.setRowSelectionInterval(0, 0);

                    } else {
                        if (jumlah_item != 0) {
                            jumlah_item--;
                        }
                        if (tbl_tambah_mutasi.getValueAt(rows[i], 5).toString() != "") {
                            jumlah_qty -= Double.parseDouble(tbl_tambah_mutasi.getValueAt(rows[i], 5).toString());
                        }
                        txt_jumlah_item.setText("Jumlah Item = " + String.valueOf(jumlah_item));
                        txt_jumlah_qty.setText("Jumlah Qty = " + String.valueOf(jumlah_qty));
                        model.removeRow(rows[i] - 1);
                        tbl_tambah_mutasi.setRowSelectionInterval(0, 0);

                    }
                }

            }

        }
    }

    void menambah_jumlah() {
        jumlah_item++;
        jumlah_qty += Double.parseDouble(tbl_tambah_mutasi.getValueAt(tbl_tambah_mutasi.getSelectedRow(), 5).toString());
    }

    void mengurangi_jumlah() {
        jumlah_item--;
        jumlah_qty += Double.parseDouble(tbl_tambah_mutasi.getValueAt(tbl_tambah_mutasi.getSelectedRow(), 5).toString());
    }
    private void tbl_tambah_mutasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_tambah_mutasiKeyPressed
        DefaultTableModel model = (DefaultTableModel) tbl_tambah_mutasi.getModel();
        int selectedRow = tbl_tambah_mutasi.getSelectedRow();
        System.out.println(selectedRow);
        //int selectedRow = tbl_tambah_mutasi.getSelectedRow();
        //int baris = tbl_tambah_mutasi.getRowCount();

        TableModel tabelModel;
        tabelModel = tbl_tambah_mutasi.getModel();
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter
                if (tabelModel.getValueAt(tbl_tambah_mutasi.getSelectedRow(), 6).toString().equals("") || tabelModel.getValueAt(tbl_tambah_mutasi.getSelectedRow(), 5).toString().equals("") || tabelModel.getValueAt(tbl_tambah_mutasi.getSelectedRow(), 4).toString().equals("") || tabelModel.getValueAt(tbl_tambah_mutasi.getSelectedRow(), 3).toString().equals("") || tabelModel.getValueAt(tbl_tambah_mutasi.getSelectedRow(), 2).toString().equals("") || tabelModel.getValueAt(tbl_tambah_mutasi.getSelectedRow(), 1).toString().equals("")) {
                    throw new NullPointerException();
                } else {
                    menambah_jumlah();
                    txt_jumlah_item.setText("Jumlah Item = " + String.valueOf(jumlah_item));
                    txt_jumlah_qty.setText("Jumlah Qty = " + String.valueOf(jumlah_qty));
                    model.addRow(new Object[]{"", "", "", "", "", "", ""});
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_F5) {
                if (tbl_tambah_mutasi.getRowCount() - 1 == -1) {
                    JOptionPane.showMessageDialog(null, "Data didalam tabel telah tiada.", "", 2);
                } else {
                    removeSelectedRows(tbl_tambah_mutasi);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_F12) {
                int simpan_data = 1;
                simpan_data = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
                if (simpan_data == 0) {
                    simpan_data();
                }

            } else if (evt.getKeyCode() == KeyEvent.VK_F9) {
                clear();
            } else if (evt.getKeyCode() == KeyEvent.VK_INSERT) {
                if (tbl_tambah_mutasi.getRowCount() - 1 == -1) {
                    model.addRow(new Object[]{"", "", "", "", "0", "", "0", "0", "0"});
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_1 && tbl_tambah_mutasi.getSelectedColumn() == 4) {
                System.out.println("ini alt");
                String kode_barang = String.valueOf(tbl_tambah_mutasi.getValueAt(tbl_tambah_mutasi.getSelectedRow(), 1));
                try {
                    String sql = "select nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '1' and bk.kode_barang = '" + kode_barang + "'";
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    while (res.next()) {
                        String sat = res.getString("nama_konversi");
                        String sat2 = sat;
                        tbl_tambah_mutasi.setValueAt(sat2, tbl_tambah_mutasi.getSelectedRow(), 4);
                        System.out.println(sat2);
                    }
                    res.close();
                    conn.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_2 && tbl_tambah_mutasi.getSelectedColumn() == 4) {
                System.out.println("ini alt");
                String kode_barang = String.valueOf(tbl_tambah_mutasi.getValueAt(tbl_tambah_mutasi.getSelectedRow(), 1));
                try {
                    String sql = "select nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '2' and bk.kode_barang = '" + kode_barang + "'";
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    while (res.next()) {
                        String sat = res.getString("nama_konversi");
                        String sat2 = sat;
                        tbl_tambah_mutasi.setValueAt(sat2, tbl_tambah_mutasi.getSelectedRow(), 4);
                        System.out.println(sat2);
                    }
                    res.close();
                    conn.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_3 && tbl_tambah_mutasi.getSelectedColumn() == 4) {
                System.out.println("ini alt");
                String kode_barang = String.valueOf(tbl_tambah_mutasi.getValueAt(tbl_tambah_mutasi.getSelectedRow(), 1));
                try {
                    String sql = "select nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '3' and bk.kode_barang = '" + kode_barang + "'";
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    while (res.next()) {
                        String sat = res.getString("nama_konversi");
                        String sat2 = "2. " + sat;
                        tbl_tambah_mutasi.setValueAt(sat2, tbl_tambah_mutasi.getSelectedRow(), 4);
                        System.out.println(sat2);
                    }
                    res.close();
                    conn.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (tbl_tambah_mutasi.getSelectedColumn() == 1 || tbl_tambah_mutasi.getSelectedColumn() == 2 || tbl_tambah_mutasi.getSelectedColumn() == 3 || tbl_tambah_mutasi.getSelectedColumn() == 6)) {
                InputMap im = tbl_tambah_mutasi.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
                KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
                KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
                im.put(down, im.get(f2));
                System.out.println("asd");

            } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (tbl_tambah_mutasi.getSelectedColumn() == 0 || tbl_tambah_mutasi.getSelectedColumn() == 4 || tbl_tambah_mutasi.getSelectedColumn() == 5)) {
                InputMap im = tbl_tambah_mutasi.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
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
    }//GEN-LAST:event_tbl_tambah_mutasiKeyPressed
    void clear() {
        DefaultTableModel t = (DefaultTableModel) tbl_tambah_mutasi.getModel();
        t.setRowCount(1);
    }
    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        clear();
    }//GEN-LAST:event_jLabel20MouseClicked

    private void comKodeKonvAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_comKodeKonvAncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_comKodeKonvAncestorMoved

    private void txt_tglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tglActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tglActionPerformed

    private void txt_jumlah_qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlah_qtyActionPerformed

    }//GEN-LAST:event_txt_jumlah_qtyActionPerformed

    private void txt_qtyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_qtyMouseExited

    }//GEN-LAST:event_txt_qtyMouseExited

    private void txt_qtyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_qtyFocusLost

    }//GEN-LAST:event_txt_qtyFocusLost

    private void txt_qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_qtyActionPerformed

    }//GEN-LAST:event_txt_qtyActionPerformed

    private void txt_qtyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_qtyFocusGained

    }//GEN-LAST:event_txt_qtyFocusGained

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        } else if (evt.getKeyCode() == KeyEvent.VK_F9) {
            clear();
        } else if (evt.getKeyCode() == KeyEvent.VK_INSERT) {
            if (tbl_tambah_mutasi.getRowCount() - 1 == -1) {
                DefaultTableModel model = (DefaultTableModel) tbl_tambah_mutasi.getModel();
                TableModel tabelModel;
                tabelModel = tbl_tambah_mutasi.getModel();
                model.addRow(new Object[]{"", "", "", "", "0", "", "0", "0", "0"});
            }
        }


    }//GEN-LAST:event_formKeyPressed

    private void txt_qtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_qtyKeyPressed
//        txt_qty.getDocument().addDocumentListener(new DocumentListener() {
//                @Override
//                public void insertUpdate(DocumentEvent e) {
//                    menambah_jumlah();
//                }
//
//                @Override
//                public void removeUpdate(DocumentEvent e) {
//                    mengurangi_jumlah();
//                }
//
//                @Override
//                public void changedUpdate(DocumentEvent e) {
//                    //loadDataKriteria();
//                }
//
//            });  
    }//GEN-LAST:event_txt_qtyKeyPressed

    private void comTableBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comTableBarangKeyPressed

    }//GEN-LAST:event_comTableBarangKeyPressed

    private void comKodeBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comKodeBarangKeyPressed

    }//GEN-LAST:event_comKodeBarangKeyPressed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_jLabel26MouseClicked

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
            java.util.logging.Logger.getLogger(Master_MutasiAntarGudang_DetailNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiAntarGudang_DetailNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiAntarGudang_DetailNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiAntarGudang_DetailNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Master_MutasiAntarGudang_DetailNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comKodeBarang;
    private javax.swing.JComboBox<String> comKodeKonv;
    private javax.swing.JComboBox<String> comTableAsal;
    private javax.swing.JComboBox<String> comTableBarang;
    private javax.swing.JComboBox<String> comTableKonv;
    private javax.swing.JComboBox<String> comTableTujuan;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable tbl_tambah_mutasi;
    private javax.swing.JTextField txt_jumlah_item;
    private javax.swing.JTextField txt_jumlah_qty;
    private javax.swing.JTextField txt_keterangan;
    private javax.swing.JTextField txt_noBukti;
    private javax.swing.JTextField txt_qty;
    private javax.swing.JTextField txt_tgl;
    // End of variables declaration//GEN-END:variables
}
