/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Connect;
import Java.ListBarang;
import Java.ListBarangGroup;
import Java.modelTabelBarangGroup;
import com.sun.glass.events.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import static jdk.nashorn.internal.objects.NativeError.printStackTrace;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

/**
 *
 * @author User
 */
public class Master_Group_DataGroup extends javax.swing.JFrame {

    private Connect connection;
    private ResultSet hasil;
    private ArrayList<ListBarangGroup> list;
    private ListBarangGroup listbaranggroup;
    private TableModel model;
    private PreparedStatement PS;
    private String parent;
    private String parent1;
    ArrayList<String> kode_nama_arra = new ArrayList();
    ArrayList<String> kode_satuan_arr = new ArrayList();
    private static int item1 = 0;
    private boolean tampil1 = true;
    private int row_update;
    private int kode_barang = 1;
    private int satuan1 = 0;

    public Master_Group_DataGroup() {
        initComponents();
    }

    Master_Group_DataGroup(Connect connection, String kode) {
        initComponents();
        loadkodegroupbaru();
//        prep
        this.connection = connection;
        this.parent = kode;
        tampildatagroup(parent);
        jTable10.changeSelection(0, 0, false, false);
        jTable10.setRowSelectionInterval(0, 0);
        jTable10.requestFocus(true);
        this.setVisible(true);

        ((JTextComponent) comtablenamabarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item1 == 0) {
                    loadnamabarang(((JTextComponent) comtablenamabarang.getEditor().getEditorComponent()).getText());
                } else {
                    item1 = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil1) {
                            jTable10.editCellAt(jTable10.getSelectedRow(), 2);
                            comtablenamabarang.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comtablenamabarang.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comtablenamabarang.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                //((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");

            }

        });
        ((JTextComponent) comtablenamabarang.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
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

//    private void updatedatagroup(String kode) {
//        try {
//            String sql = "SELECT * from barang_group where kode_parent ='" + kode + "'";
//            java.sql.Connection conn = Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            int i = jTable10.getRowCount() - 1;
//
//            for (int j = 0; j < i; j++) {
//                while (res.next()) {
//                    if (res.getString("kode_barang") == jTable10.getValueAt(j, 1).toString()) {
//                        //update
//                        String sql1 = "update barang_group set jumlah = '" + jTable10.getValueAt(j, 3) + "set";
//
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//
//        }
//
//    }
    void loadnamabarang(String kode) {
        Runnable doHighlight = new Runnable() {
            @Override
            public void run() {
                System.out.println("ini load combo nama");
                try {
                    String sql = "select concat(kode_barang,\"-\",nama_barang) as gabung from barang where kode_barang ='" + kode + "' OR nama_barang like '%" + kode + "%'";
                    System.out.println(sql);
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    System.out.println("ini sql com kode nama " + sql);
                    kode_nama_arra.clear();
                    kode_nama_arra.add("");
                    while (res.next()) {
                        String nama_barang = res.getString("gabung");
                        kode_nama_arra.add(nama_barang);
                        item1++;
                    }
                    if (item1 == 0) {
                        item1 = 1;
                    }
                    comtablenamabarang.setModel(new DefaultComboBoxModel(kode_nama_arra.toArray()));
                    ((JTextComponent) comtablenamabarang.getEditor().getEditorComponent()).setText(kode);
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

//
    void loadtablenamabarang() {
        try {
            String sql = "select * from barang";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
//            System.out.println("lks: "+sql);
            comtablenamabarang.removeAllItems();
            while (res.next()) {
                String name = res.getString(4);
                comtablenamabarang.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadComTableSatuantable() {
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
            comtablesatuan.removeAllItems();
            int i = 1;
            while (res.next()) {
                String name = res.getString("nama_konversi");
//                if (res.getString("identitas_konversi").toString().equalsIgnoreCase("1")) {
//                    System.out.println("ya: " + name);
//                    comTableKonv.setSelectedItem(name);
//                }
                System.out.println(name);
                comtablesatuan.addItem(name);
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

    private void removeSelectedRows(JTable table, int rows1) {
        int Hapus = 1;
        Hapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin mau menghapus baris ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if (Hapus == 0) {
            String nogroup = jTable10.getValueAt(rows1, 0).toString();
            if (nogroup!="") {
                deleteDataGroup(nogroup);
            }
            DefaultTableModel model = (DefaultTableModel) this.jTable10.getModel();
            int[] rows = table.getSelectedRows();

            for (int i = 0; i < rows.length; i++) {
                model.removeRow(rows[i] - i);
            }
//            int rows1 = jTable10.getRowCount();

            
        }
    }

//    void loadNumberTable() {
//        int baris = jTable10.getRowCount();
//        for (int a = 0; a < baris; a++) {
//            String nomor = String.valueOf(a + 1);
//            jTable10.setValueAt(nomor + ".", a, 0);
//        }
//    }
    private void tampildatagroup(String kode) {
        try {
            String sql = "SELECT bg.kode_group, bg.kode_barang_parent, bg.kode_barang, bg.jumlah_group, bg.kode_konversi, b.nama_barang, k.nama_konversi "
                    + "FROM barang_group bg, barang b, barang_konversi bk, konversi k "
                    + "WHERE bg.kode_barang = b.kode_barang "
                    + "AND bg.kode_konversi = bk.kode_barang_konversi "
                    + "AND bk.kode_konversi = k.kode_konversi "
                    + "AND kode_barang_parent = " + kode + " order by kode_group ";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) jTable10.getModel();
            int i = 0;
            System.out.println("sqllll: " + sql);
            model.removeRow(i);
            int a = 1;
            while (res.next()) {
//                model.setValueAt(res.getString("kode_group"), i, 0);
//                model.setValueAt(res.getString("kode_barang"), i, 1);
//                model.setValueAt(res.getString("nama_barang"), i, 2);
//                model.setValueAt(res.getString("jumlah_group"), i, 3);
//                String satuan = res.getString("nama_konversi");
//                model.setValueAt(satuan, i, 4);

                model.addRow(new Object[]{res.getString("kode_group"), res.getString("kode_barang"), res.getString("nama_barang"),
                    res.getString("jumlah_group"), res.getString("nama_konversi"), res.getString("kode_konversi")});

//                System.out.println("tampil group berhasil");
            }
            model.addRow(new Object[]{"", "", "", "", "", ""});

            row_update = jTable10.getRowCount();
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    private void deleteData(String kode) {
        PS = null;
        try {
            String sql = "delete from barang_group where kode_barang_parent ='" + kode + "'";
            PS = connection.Connect().prepareStatement(sql);
            PS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Master__group_dataGroup_delete_" + e.toString());
        } finally {

        }

    }

    private void deleteDataGroup(String kodeG) {
        PS = null;
        try {
            String sql = "delete from barang_group where kode_group ='" + kodeG + "'";
            System.out.println("deletedatagroup: "+sql);
            PS = connection.Connect().prepareStatement(sql);
            PS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }

    }

    private void simpandatagroup() {
//        deleteData(parent);
        try {

            int rows = jTable10.getRowCount();

            for (int row = 0; row < rows; row++) {
                String nogroup = jTable10.getValueAt(row, 0).toString();
                if (nogroup != "") {
                    String nobarangparent = parent;
                    String kodegroup = jTable10.getValueAt(row, 0).toString();
                    String nokodebarang = (String) jTable10.getValueAt(row, 1);
                    String jumlahgroup = (String) jTable10.getValueAt(row, 3);
                    String kodekonversi = (String) jTable10.getValueAt(row, 4);
                    String kodebarangkonversi = getKodeBarangSatuan(nokodebarang, kodekonversi);
                    if (cekBarang(kodegroup)) {
//                        System.out.println("ubahhhhhhhhhhhh");
                        try {
                            String sql = "update barang_group set kode_barang = '" + nokodebarang + "', jumlah_group='" + jumlahgroup + "', "
                                    + "kode_konversi='" + kodebarangkonversi + "' where kode_group ='" + kodegroup + "'";
                            PS = connection.Connect().prepareStatement(sql);
                            PS.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.out.println("simpandatagroup ubahhh : " + e.toString());
                        }
                    } else {
//                        System.out.println("tambahhhhhhhhhh");
                        try {
                            String sql = "insert into barang_group(kode_barang_parent, kode_barang, jumlah_group, kode_konversi) values "
                                    + "('" + parent + "'"
                                    + ",'" + nokodebarang + "','" + jumlahgroup + "','" + getKodeBarangSatuan(nokodebarang, kodekonversi) + "')";
                            PS = connection.Connect().prepareStatement(sql);
                            PS.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.out.println("simpandatagroup tambah : " + e.toString());
                        }
                    }
//                    System.out.println("nog: " + nogroup + " nbp: " + nobarangparent + " nkb: " + nokodebarang + " jg: " + jumlahgroup + " "
//                            + "kk: " + getKodeBarangSatuan(nokodebarang,kodekonversi));
//                    try {
//                        String sql = "insert into barang_group(kode_barang_parent, kode_barang, jumlah_group, kode_konversi) values ('"+parent+"'"
//                                + ",'"+nokodebarang+"','"+jumlahgroup+"','"+getKodeBarangSatuan(nokodebarang,kodekonversi)+"')";
////                        System.out.println("adddatagroup: \n"+sql);
//                        PS = connection.Connect().prepareStatement(sql);
//                        
//////                    PS.setString(1, nogroup);
////                        PS.setString(1, parent);
////                        PS.setString(2, nokodebarang);
////                        PS.setString(3, jumlahgroup);
////                        PS.setString(4, getKodeBarangSatuan(nokodebarang,kodekonversi));
//////                    System.out.println(sql);
//                        PS.executeUpdate();
////                    System.out.println("simpan");
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                        System.out.println("Toko Transaksi : " + e.toString());
//                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Simpan data group berhasil");
            PS.executeBatch();
            tampildatagroup(parent);
            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private boolean cekBarang(String kodeG) {
        boolean hasil = false;
        try {
            String sql = "select kode_group from barang_group where kode_group='" + kodeG + "'";
//                    + "where kode_barang_parent = '" + parent + "' "
//                    + "and kode_barang = '" + kodeB + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            if (res.first()) {
                hasil = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasil;
    }

    private String getKodeBarangSatuan(String kodeB, String namaK) {
        String satuan = "";
        try {
            String sql = "select bk.kode_barang_konversi from barang b, barang_konversi bk, konversi k "
                    + "where b.kode_barang = bk.kode_barang "
                    + "and bk.kode_konversi = k.kode_konversi "
                    + "and b.kode_barang = '" + kodeB + "' "
                    + "and k.nama_konversi = '" + namaK + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                satuan = res.getString("kode_barang_konversi");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return satuan;
    }

    private void loadkodegroupbaru() {
        String satuan;
        try {
            String sql = "select max(kode_group) as asu from barang_group";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                satuan = res.getString("asu");
                int satuanya = Integer.parseInt(satuan);
                this.satuan1 = satuanya + 1;

            }
        } catch (Exception e) {
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

        comtablenamabarang = new javax.swing.JComboBox<>();
        comtablesatuan = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        comtablenamabarang.setEditable(true);
        comtablenamabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comtablenamabarangActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("DATA GROUP");

        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "No.", "Kode Barang", "Nama", "Jumlah", "Satuan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable10MouseClicked(evt);
            }
        });
        jTable10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable10KeyPressed(evt);
            }
        });
        jScrollPane14.setViewportView(jTable10);
        jTable10.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTable10.getColumnModel().getColumnCount() > 0) {
            jTable10.getColumnModel().getColumn(2).setCellEditor(new ComboBoxCellEditor(comtablenamabarang));
            jTable10.getColumnModel().getColumn(4).setResizable(false);
            jTable10.getColumnModel().getColumn(4).setCellEditor(new ComboBoxCellEditor(comtablesatuan));
        }

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N
        jButton2.setText("Hapus");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 963, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(23, 23, 23))
            .addGroup(layout.createSequentialGroup()
                .addGap(386, 386, 386)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        setSize(new java.awt.Dimension(979, 378));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        simpandatagroup();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable10KeyPressed
        DefaultTableModel model = (DefaultTableModel) jTable10.getModel();
        int selectedRow = jTable10.getSelectedRow();
        int baris = jTable10.getRowCount();
        int no_barang = 0, kode_barang = 0, harga_jadi = 0, diskon = 0, diskon1 = 0, diskonp = 0, diskonp1 = 0;
        int qty = 0;

        TableModel tabelModel;
        tabelModel = jTable10.getModel();

        if (evt.getKeyCode() == KeyEvent.VK_ENTER && jTable10.getSelectedColumn() == 4) {    // Membuat Perintah Saat Menekan Enter
            //cek
            int jumlah = Integer.valueOf(tabelModel.getValueAt(selectedRow, 3).toString());
            System.out.println("jumlah : ------    " + jumlah);
            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(null, "Jumlah tidak boleh kurang dari nol.");
            } else {
                model.addRow(new Object[]{"", "", "", "", ""});
            }

        } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            if (jTable10.getRowCount() - 1 == -1) {
                JOptionPane.showMessageDialog(null, "Data didalam tabel telah tiada.", "", 2);
            } else {
                removeSelectedRows(jTable10, selectedRow);
//                HitungSemua();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_INSERT) {
            if (jTable10.getRowCount() - 1 == -1) {
                model.addRow(new Object[]{"", "", "", "", "0", "", "0", "0", "0"});

            }
        } else if (evt.getKeyCode() == KeyEvent.VK_1 || evt.getKeyCode() == KeyEvent.VK_NUMPAD2 && jTable10.getSelectedColumn() == 4) {
            System.out.println("ini alt");
            String kode = String.valueOf(jTable10.getValueAt(jTable10.getSelectedRow(), 1));
            try {
                String sql = "select nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '1' and bk.kode_barang = '" + kode + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String sat = res.getString("nama_konversi");
                    String sat2 = sat;
                    jTable10.setValueAt(sat2, jTable10.getSelectedRow(), 4);
                    System.out.println(sat2);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_2 && jTable10.getSelectedColumn() == 4) {
            System.out.println("ini alt");
            String kode = String.valueOf(jTable10.getValueAt(jTable10.getSelectedRow(), 1));
            try {
                String sql = "select nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '2' and bk.kode_barang = '" + kode + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    String sat = res.getString("nama_konversi");
                    String sat2 = sat;
                    jTable10.setValueAt(sat2, jTable10.getSelectedRow(), 4);
                    System.out.println(sat2);
                }
                res.close();
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (jTable10.getSelectedColumn() == 2)) {
            InputMap im = jTable10.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
            KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
            im.put(down, im.get(f2));
            System.out.println("asd");

        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (jTable10.getSelectedColumn() != 2)) {
            InputMap im = jTable10.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
            KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
            im.put(f2, null);
            im.put(down, null);
            System.out.println("fgh");
        }

//        loadNumberTable();

    }//GEN-LAST:event_jTable10KeyPressed

    private void jTable10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable10MouseClicked
//      int b = jTable10.getSelectedColumn();
//    
    }//GEN-LAST:event_jTable10MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int row = jTable10.getSelectedRow();
        removeSelectedRows(jTable10, row);
    }//GEN-LAST:event_jButton2ActionPerformed
    void load_dari_nama_barang() {
        int selectedRow = jTable10.getSelectedRow();
        String nama_awal = String.valueOf(comtablenamabarang.getSelectedItem());
        String[] split = new String[2];
        System.out.println("nilai comTable barang adalah " + comtablenamabarang.getSelectedItem());
        if (comtablenamabarang.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }
        try {
            String sql = "select kode_barang,nama_barang from barang where kode_barang = '" + split[0] + "'";
//            String sql2 = "select b.kode_barang, b.nama_barang, bg.kode_group, bg.kode_konversi, bg.kode_barang_parent "
//                    + "from barang b, barang_group bg "
//                    + "where b.kode_barang =  bg.kode_barang "
//                    + "and b.kode_barang = '" + split[0] + "'order by nama_barang";
            System.out.println("saqlasas = " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
//            String sql2 = "SELECT k.nama_konversi "
//                    + "from barang b, barang_konversi bk, konversi k "
//                    + "WHERE b.kode_barang = bk.kode_barang "
//                    + "AND bk.kode_konversi = k.kode_konversi "
//                    + "AND b.nama_barang = '"+split[1]+"'";
//            java.sql.Statement stm2 = conn.createStatement();
//            java.sql.ResultSet res2 = stm2.executeQuery(sql2);
//            int kode_g = 1;
//            while(res2.next()){
//                kode_g += Integer.parseInt(res2.getString("kode"));
//            }
            while (res.next()) {

                String kode = res.getString("kode_barang");
                String nama = res.getString("nama_barang");
                this.kode_barang = Integer.parseInt(kode);
                loadComTableSatuantable();
                //String satuan = res2.getString("nama_konversi");

//                loadNumberTable();
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
//                jTable10.setValueAt(selectedRow + 1, selectedRow, 0);
                jTable10.setValueAt(satuan1, selectedRow, 0);
                jTable10.setValueAt(kode, selectedRow, 1);
                jTable10.setValueAt(nama, selectedRow, 2);
                jTable10.setValueAt("0", selectedRow, 3);
                jTable10.setValueAt(comtablesatuan.getItemAt(0), selectedRow, 4);

            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

    private void comtablenamabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comtablenamabarangActionPerformed

        loadkodegroupbaru();
        load_dari_nama_barang();

//        int kode_barang = 0;
//        int baris = jTable10.getRowCount();
//        TableModel tabelModel;
//        tabelModel = jTable10.getModel();
//        try {
//            String sql = "select b.kode_barang, b.nama_barang, bg.kode_group, bg.kode_konversi "
//                    + "from barang b, barang_group bg "
//                    + "where b.kode_barang =  bg.kode_barang "
//                    + "and b.nama_barang = '" + comtablenamabarang.getSelectedItem() + "'order by nama_barang";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
////                String satuan = res.getString(4);
//                String kodebarang = res.getString(1);
//                String kodegroup = res.getString(3);
//                String kodekonversi = res.getString(4);
//
//                int i = jTable10.getSelectedRow();
//                String kode = res.getString(1);
//                if (i != -1) {
//                    jTable10.setValueAt(kodebarang, i, 1);
//                    jTable10.setValueAt(kodegroup, i, 5);
//                    jTable10.setValueAt(comtablesatuan.getItemAt(0), i, 4);
//                    System.out.println("line 511 = " + comtablenamabarang.getSelectedItem());
//
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//            e.printStackTrace();
//        }
    }//GEN-LAST:event_comtablenamabarangActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        System.out.println("opened");
//        setData(parent);

        //loadnamabarang(parent);
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(Master_Group_DataGroup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Group_DataGroup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Group_DataGroup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Group_DataGroup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Master_Group_DataGroup().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comtablenamabarang;
    private javax.swing.JComboBox<String> comtablesatuan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JTable jTable10;
    // End of variables declaration//GEN-END:variables
}
