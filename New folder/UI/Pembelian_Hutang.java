/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Currency_Column;
import com.sun.glass.events.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author User
 */
public final class Pembelian_Hutang extends javax.swing.JFrame {

    private int totalHutang = 0;
    private int potongan = 0;
    private int jumFaktur = 0;
    private String[] noFaktur;
    private int[] hrgItem;
    private static int item = 0;
    private boolean tampil = true;

    ArrayList<String> kode_nama_arr = new ArrayList();

    public Pembelian_Hutang() {
        initComponents();
        this.setVisible(true);
        kalender();
        loadSupplier();
        loadTable("");
        //JCombobox supplier
        ((JTextComponent) comSupplier.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadSupplier(((JTextComponent) comSupplier.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
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

    private String currency_convert(int nilai) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(nilai);
    }

    public void kalender() {
        Thread p = new Thread() {
            @Override
            public void run() {
                for (;;) {
//                    GregorianCalendar cal = new GregorianCalendar();
//                    int hari = cal.get(Calendar.DAY_OF_MONTH);
//                    int bulan = cal.get(Calendar.MONTH);
//                    int tahun = cal.get(Calendar.YEAR);
//                    int jam = cal.get(Calendar.HOUR);
//                    int menit = cal.get(Calendar.MINUTE);
//                    lbl_tgl.setText(hari + " - " + (bulan + 1) + " - " + tahun + " | " + jam + ":" + menit);
                    String timeStamp = new SimpleDateFormat("dd-MM-yyyy|HH:mm").format(Calendar.getInstance().getTime());
                    lbl_tgl.setText(timeStamp);
                }
            }
        };
        p.start();
    }

    void loadSupplier(String param) {
        if (param.equals("")) {
            param = "";
        }
        try {
//            String sql = "select * from supplier where nama_supplier like '%"+param+"%' or kode_supplier like '%"+param+"%'";
            String sql = "select concat(kode_supplier,\"-\",nama_supplier) as gabung from supplier "
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
                String name = res.getString(1) + "- " + res.getString(2);
                comSupplier.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    public void loadTable() {
        removeRow();
        DefaultTableModel model = (DefaultTableModel) tbl_pembelianHutang.getModel();
        int i = 1;
        try {
            String sql = "select pembelian.no_faktur_pembelian,"
                    + "pembelian.tgl_pembelian,"
                    + "pembelian.kode_supplier,"
                    + "supplier.kota_supplier,"
                    + "supplier.nama_supplier,"
                    + "pembelian.biaya_pembayaran,"
                    + "pembelian.potongan "
                    + "FROM supplier, pembelian "
                    + "WHERE supplier.kode_supplier = pembelian.kode_supplier "
                    + "AND nama_supplier LIKE '%"
                    + comSupplier.getSelectedItem().toString()
                    + "%' "
                    + "AND biaya_pembayaran <0 "
                    + "AND faktur_bg = '' "
                    + "ORDER BY tgl_pembelian DESC";
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                model.addRow(new Object[]{
                    false,
                    i++,
                    res.getString("no_faktur_pembelian"),
                    dotConverter(res.getString("tgl_pembelian")),
                    res.getString("nama_supplier"),
                    res.getString("kota_supplier"),
                    res.getString("biaya_pembayaran"),
                    res.getString("potongan")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }

    }

    public void loadTable(String param) {
        removeRow();
        if (param.equals("-- Pilih Supplier --") || param.equals("")) {
            param = "";
        }
        DefaultTableModel model = (DefaultTableModel) tbl_pembelianHutang.getModel();
        int i = 1;
        try {
            String sql = "select pembelian.no_faktur_pembelian,"
                    + "pembelian.tgl_pembelian,"
                    + "pembelian.kode_supplier,"
                    + "supplier.kota_supplier,"
                    + "supplier.nama_supplier,"
                    + "pembelian.biaya_pembayaran,"
                    + "pembelian.potongan "
                    + "FROM supplier, pembelian "
                    + "WHERE supplier.kode_supplier = pembelian.kode_supplier "
                    //                    + "AND nama_supplier LIKE '%"
                    //                    + param
                    //                    + "%' "
                    + "AND biaya_pembayaran <0 "
                    + "AND faktur_bg = '' "
                    + "ORDER BY tgl_pembelian DESC";
            System.out.println("sqlHutangBG: " + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                int biaya = res.getInt("Biaya_Pembayaran");
                model.addRow(new Object[]{
                    false,
                    i++,
                    res.getString("no_faktur_pembelian"),
                    dotConverter(res.getString("tgl_pembelian")),
                    res.getString("nama_supplier"),
                    res.getString("kota_supplier"),
                    currency_convert(biaya),
                    currency_convert(potongan)
                });
            }
            TableColumnModel m = tbl_pembelianHutang.getColumnModel();
            m.getColumn(6).setCellRenderer(new Currency_Column());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }

    }

    private void loadTableSupplier(String param) {
        removeRow();
        if (param.equals("-- Pilih Supplier --")) {
            param = "";
        }
        DefaultTableModel model = (DefaultTableModel) tbl_pembelianHutang.getModel();
        int i = 1;
        try {
            String sql = "SELECT p.no_faktur_pembelian,\n"
                    + "p.tgl_pembelian,\n"
                    + "p.kode_supplier,\n"
                    + "s.kota_supplier,\n"
                    + "s.nama_supplier,\n"
                    + "p.biaya_pembayaran,\n"
                    + "p.faktur_bg,\n"
                    + "p.potongan,\n"
                    + "CONCAT(s.kode_supplier, '-', s.nama_supplier) AS supp\n"
                    + "FROM pembelian p JOIN supplier s ON p.kode_supplier = s.kode_supplier  \n"
                    + "HAVING supp = '" + param + "'\n"
                    + "AND p.biaya_pembayaran < 0\n"
                    + "AND p.faktur_bg = ''\n"
                    + "ORDER BY p.tgl_pembelian DESC";
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                int biaya = res.getInt("Biaya_Pembayaran");
                model.addRow(new Object[]{
                    false,
                    i++,
                    res.getString("no_faktur_pembelian"),
                    dotConverter(res.getString("tgl_pembelian")),
                    res.getString("nama_supplier"),
                    res.getString("kota_supplier"),
                    currency_convert(biaya),
                    res.getString("potongan")
                });
            }
            TableColumnModel m = tbl_pembelianHutang.getColumnModel();
            m.getColumn(6).setCellRenderer(new Currency_Column());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }

    }

    public void removeRow() {
        DefaultTableModel model = (DefaultTableModel) tbl_pembelianHutang.getModel();
        int row = tbl_pembelianHutang.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    public void autoSum() {
        int totalHutang = 0;
        int jumlahBaris = tbl_pembelianHutang.getRowCount();
        boolean action;
        int hargaItem = 0;
        int totalPotongan = 0;
        int jumFaktur = 0;
        noFaktur = new String[jumlahBaris];
        hrgItem = new int[jumlahBaris];

        TableModel tabelModel;
        tabelModel = tbl_pembelianHutang.getModel();
        for (int i = 0; i < jumlahBaris; i++) {
            action = (boolean) tabelModel.getValueAt(i, 0);
            if (action == true) {
                hargaItem = Integer.valueOf(tabelModel.getValueAt(i, 6).toString());
                hrgItem[jumFaktur] = hargaItem;
                totalPotongan = Integer.valueOf(tabelModel.getValueAt(i, 7).toString());
                noFaktur[jumFaktur] = String.valueOf(tabelModel.getValueAt(i, 2));
                jumFaktur++;
            } else {
                hargaItem = 0;
                totalPotongan = 0;
            }
            totalHutang += hargaItem;
            potongan += totalPotongan;
        }
        txt_total.setText("" + totalHutang);

        for (int i = 0; i < jumFaktur; i++) {
            System.out.println("noFaktur " + i + " " + noFaktur[i] + " Harga = " + hrgItem[i]);
        }
        System.out.println("=================================");
        this.jumFaktur = jumFaktur;
        this.totalHutang = totalHutang;

    }

    void selectAll() {
        DefaultTableModel model = (DefaultTableModel) tbl_pembelianHutang.getModel();
        if (chk_pilihSemua.isSelected()) {
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(true, i, 0);
            }
        } else {
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(false, i, 0);
            }
        }
        autoSum();
    }

    static String dotConverter(String b) {
        b = b.replace(".0", "");
        return b;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        lbl_tgl = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_pembelianHutang = new javax.swing.JTable();
        btn_bayar = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        chk_pilihSemua = new javax.swing.JCheckBox();
        comSupplier = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(412, 45));

        jLabel92.setBackground(new java.awt.Color(217, 237, 247));
        jLabel92.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel92.setText("Tabel Hutang Pembelian");

        lbl_tgl.setBackground(new java.awt.Color(255, 255, 255));
        lbl_tgl.setText("waktu");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 553, Short.MAX_VALUE)
                .addComponent(lbl_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_tgl)
                    .addComponent(jLabel92))
                .addContainerGap())
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tbl_pembelianHutang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Action", "No.", "No.Faktur Pembelian", "Tanggal", "Supplier", "Kota", "Biaya", "Potongan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_pembelianHutang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_pembelianHutangMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_pembelianHutang);
        if (tbl_pembelianHutang.getColumnModel().getColumnCount() > 0) {
            tbl_pembelianHutang.getColumnModel().getColumn(1).setResizable(false);
            tbl_pembelianHutang.getColumnModel().getColumn(2).setResizable(false);
            tbl_pembelianHutang.getColumnModel().getColumn(4).setResizable(false);
            tbl_pembelianHutang.getColumnModel().getColumn(5).setResizable(false);
            tbl_pembelianHutang.getColumnModel().getColumn(6).setResizable(false);
        }

        btn_bayar.setBackground(new java.awt.Color(71, 166, 227));
        btn_bayar.setText("Bayar");
        btn_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bayarActionPerformed(evt);
            }
        });

        jLabel44.setText("Supplier");

        txt_total.setText("0");
        txt_total.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_totalMouseClicked(evt);
            }
        });
        txt_total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_totalKeyPressed(evt);
            }
        });

        chk_pilihSemua.setText("Pilih Semua");
        chk_pilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_pilihSemuaActionPerformed(evt);
            }
        });

        comSupplier.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comSupplier.setEditable(true);
        comSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Supplier --" }));
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel44)
                .addGap(28, 28, 28)
                .addComponent(comSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_bayar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chk_pilihSemua)
                .addGap(394, 394, 394))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_bayar)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44)
                    .addComponent(chk_pilihSemua)
                    .addComponent(comSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 956, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(991, 693));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_totalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_totalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalMouseClicked

    private void txt_totalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_totalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalKeyPressed

    private void chk_pilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_pilihSemuaActionPerformed
        System.out.println("Checked");
        selectAll();

    }//GEN-LAST:event_chk_pilihSemuaActionPerformed

    private void tbl_pembelianHutangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_pembelianHutangMouseClicked
        autoSum();
    }//GEN-LAST:event_tbl_pembelianHutangMouseClicked

    private void btn_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bayarActionPerformed
        Pembelian_Hutang_RincianBarang a = new Pembelian_Hutang_RincianBarang(totalHutang, potongan, noFaktur, hrgItem, jumFaktur);
        a.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_bayarActionPerformed

    private void comSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comSupplierActionPerformed
        if (comSupplier.getSelectedItem().toString() != "") {
            loadTableSupplier(comSupplier.getSelectedItem().toString());
        } else {
            loadTable("");
            System.out.println("ghfghfhgf " + comSupplier.getSelectedItem().toString());
        }

    }//GEN-LAST:event_comSupplierActionPerformed

    private void comSupplierItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comSupplierItemStateChanged
        tampil = false;
    }//GEN-LAST:event_comSupplierItemStateChanged

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
            java.util.logging.Logger.getLogger(Pembelian_Hutang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Hutang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Hutang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Hutang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new Pembelian_Hutang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_bayar;
    private javax.swing.JCheckBox chk_pilihSemua;
    private javax.swing.JComboBox<String> comSupplier;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbl_tgl;
    private javax.swing.JTable tbl_pembelianHutang;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
