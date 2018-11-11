package UI;

import Java.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class Master_Supplier extends javax.swing.JDialog {

    private ResultSet hasil;
    private Connect connection;
    private PreparedStatement PS;
    private ArrayList<ListSupplier> list;
    private ListSupplier listSupplier;
    private TableModel model;
    private MouseAdapter MA;
    private String comboBox;
    static int nilai_status;

    public Master_Supplier() {
        initComponents();
    }

    public Master_Supplier(java.awt.Frame parent, boolean modal, Connect connection) {
        super(parent, modal);
        initComponents();
        this.requestFocus();
//        prep
        this.connection = connection;
        tampilTabel("1");
    }

    private void tampilTabel(String param) {
        String datax = "";
        try {
            datax = "SELECT * "
                    + "from supplier "
                    + "" + (param.equals("-1") ? "" : (param.equals("1") || param.equals("0") ? "where aktif_supplier=" + Integer.parseInt(param) : "where nama_supplier like '%" + param + "%' "));
            hasil = connection.ambilData(datax);
            setModel(hasil);
        } catch (Exception e) {
            System.out.println("Error tampil tabel");
        }
    }

    private void updateData(int kodeSupplier) {
        PS = null;
        try {
            String sql = "Update supplier set nama_supplier=?,alamat_supplier=?,"
                    + "kota_supplier=?,telepon_supplier=?,handphone_supplier=?,contact_supplier=?,"
                    + "keterangan_supplier=?,rekening_supplier=?,aktif_supplier=? "
                    + "where kode_Supplier=?";
            PS = connection.Connect().prepareStatement(sql);
            PS.setString(1, listSupplier.getNama_supplier());
            PS.setString(2, listSupplier.getAlamat_supplier());
            PS.setString(3, listSupplier.getKota_supplier());
            PS.setString(4, listSupplier.getTelepon_supplier());
            PS.setString(5, listSupplier.getHp_supplier());
            PS.setString(6, listSupplier.getContact_supplier());
            PS.setString(7, listSupplier.getKeterangan());
            PS.setString(8, listSupplier.getRekening());
            PS.setInt(9, listSupplier.getStatus());
            PS.setInt(10, listSupplier.getKode_supplier());
//            System.out.println(sql);
            PS.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Master_Supplier_Line_50_" + e.toString());
        } finally {
            tampilTabel("-1");
        }
    }

    private void insertData(ListSupplier listSupplier) {
        PS = null;
        if (listSupplier.getKode_supplier() != 0) {
            try {
                String sql = "insert into supplier values (?,?,?,?,?,?,?,?,?,?,?)";
                PS = connection.Connect().prepareStatement(sql);
                PS.setInt(1, listSupplier.getKode_supplier());
                PS.setString(2, listSupplier.getNama_supplier());
                PS.setString(3, listSupplier.getAlamat_supplier());
                PS.setString(4, listSupplier.getKota_supplier());
                PS.setString(5, listSupplier.getKota_supplier());
                PS.setString(6, listSupplier.getTelepon_supplier());
                PS.setString(7, listSupplier.getHp_supplier());
                PS.setString(8, listSupplier.getContact_supplier());
                PS.setString(9, listSupplier.getRekening());
                PS.setInt(10, listSupplier.getStatus());
                PS.setString(11, listSupplier.getKeterangan());
//            System.out.println(sql);
                PS.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Master_Supplier_Line_75_" + e.toString());
            } finally {
                tampilTabel("-1");
            }
        }
    }

    private void deleteData(ListSupplier listSupplier) {
        int status_hapus = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin Menghapus data ini ?", "Konfimasi Hapus Data", JOptionPane.OK_CANCEL_OPTION);
        if (status_hapus == JOptionPane.OK_OPTION) {
            PS = null;
            try {
                String sql = "delete from supplier where kode_supplier=?";
                PS = connection.Connect().prepareStatement(sql);
                PS.setInt(1, listSupplier.getKode_supplier());
                PS.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Master_Supplier_Line_75_" + e.toString());
            } finally {
                tampilTabel("-1");
            }JOptionPane.showMessageDialog(null, "Data sudah dihapus.");
        }
    }

    private void setModel(ResultSet hasil) {
        try {
            list = new ArrayList<>();
            while (hasil.next()) {
                this.listSupplier = new ListSupplier();
                this.listSupplier.setKode_supplier(hasil.getInt("kode_supplier"));
                this.listSupplier.setNama_supplier(hasil.getString("nama_supplier"));
                this.listSupplier.setAlamat_supplier(hasil.getString("alamat_supplier"));
                this.listSupplier.setKota_supplier(hasil.getString("kota_supplier"));
                this.listSupplier.setTelepon_supplier(hasil.getString("telepon_supplier"));
                this.listSupplier.setHp_supplier(hasil.getString("handphone_supplier"));
                this.listSupplier.setContact_supplier(hasil.getString("contact_supplier"));
                this.listSupplier.setRekening(hasil.getString("rekening_supplier"));
                this.listSupplier.setStatus(hasil.getInt("aktif_supplier"));
                this.listSupplier.setKeterangan(hasil.getString("keterangan_supplier"));
                list.add(listSupplier);
                listSupplier = null;
            }
            model = new modelTabelSupplier(list);
            jTable2.setModel(model);
            TableColumnModel m = jTable2.getColumnModel();
            m.getColumn(7).setCellRenderer(new Currency_Column());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private int getNewId() {
        String sql = "SELECT kode_supplier FROM supplier ORDER BY kode_supplier DESC LIMIT 1";
        int id = 0;
        try {
            hasil = connection.ambilData(sql);
            while (hasil.next()) {
                id = hasil.getInt("kode_supplier") + 1;
            }
        } catch (SQLException e) {
        }
        return id;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel25 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField14 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setText("Kriteria");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.lightGray));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DAFTAR SUPPLIER");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode", "Nama", "Contact", "Telepon", "Alamat"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTextField14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField14KeyReleased(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_manilla-folder-new_23456.png"))); // NOI18N
        jLabel20.setText("F2-New");
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_gtk-edit_20500.png"))); // NOI18N
        jLabel21.setText("F3-Edit");
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N
        jLabel22.setText("F5-Delete");
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LIST SUPPLIER ACTIVE", "LIST SUPPLIER DEACTIVE", "ALL" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_gtk-edit_20500.png"))); // NOI18N
        jLabel23.setText("F4-Hutang");
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });

        jMenuBar1.setPreferredSize(new java.awt.Dimension(0, 0));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem1.setText("New");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem2.setText("Edit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem3.setText("Delete");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMenuItem4.setText("Hutang");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addGap(0, 325, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel23))
                                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(979, 562));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        listSupplier = new ListSupplier();
        Master_Supplier_TambahEdit tp = new Master_Supplier_TambahEdit(new Awal(rootPaneCheckingEnabled), rootPaneCheckingEnabled, listSupplier, getNewId());
        tp.setLocationRelativeTo(this);
        tp.setVisible(true);
        insertData(listSupplier);
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        this.listSupplier = list.get(jTable2.getSelectedRow());
//        System.out.println(this.listSupplier.getKode_supplier());

//        jTable2.addMouseListener(MA = new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent mouseEvent) {
//                if (mouseEvent.getClickCount() == 2) {
//                    System.out.println(mouseEvent.getClickCount());
////                    Master_Supplier_KartuHutang kh = new Master_Supplier_KartuHutang();
////                    kh.setVisible(true);
////                    kh.setFocusable(true);
////jTable2.clearSelection();
//                }
//            }
//        });
    }//GEN-LAST:event_jTable2MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        if (jTable2.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Pilih Supplier yang akan diedit");
        } else {
            Master_Supplier_TambahEdit tp = new Master_Supplier_TambahEdit(new Awal(rootPaneCheckingEnabled), rootPaneCheckingEnabled, listSupplier, true);
            tp.setLocationRelativeTo(this);
            tp.setVisible(true);
            updateData(listSupplier.getKode_supplier());
        }
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        comboBox = jComboBox4.getSelectedItem().toString();
        switch (jComboBox4.getSelectedIndex()) {
            case 0:
                tampilTabel("1");
                break;
            case 1:
                tampilTabel("0");
                break;
            default:
                tampilTabel("-1");
                break;
        }
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
//        Master_Supplier_KartuHutang kh = new Master_Supplier_KartuHutang(new Awal(rootPaneCheckingEnabled), rootPaneCheckingEnabled, listSupplier, true);
//        kh.setLocationRelativeTo(this);
//        kh.setVisible(true);
    }//GEN-LAST:event_jLabel23MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        deleteData(listSupplier);
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jTextField14KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyReleased
        tampilTabel(jTextField14.getText());
    }//GEN-LAST:event_jTextField14KeyReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        listSupplier = new ListSupplier();
        Master_Supplier_TambahEdit tp = new Master_Supplier_TambahEdit(new Awal(rootPaneCheckingEnabled), rootPaneCheckingEnabled, listSupplier, getNewId());
        tp.setLocationRelativeTo(this);
        tp.setVisible(true);
        insertData(listSupplier);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (jTable2.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Pilih Supplier yang akan diedit");
        } else {
            Master_Supplier_TambahEdit tp = new Master_Supplier_TambahEdit(new Awal(rootPaneCheckingEnabled), rootPaneCheckingEnabled, listSupplier, true);
            tp.setLocationRelativeTo(this);
            tp.setVisible(true);
            updateData(listSupplier.getKode_supplier());
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        deleteData(listSupplier);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        //        Master_Supplier_KartuHutang kh = new Master_Supplier_KartuHutang(new Awal(rootPaneCheckingEnabled), rootPaneCheckingEnabled, listSupplier, true);
//        kh.setLocationRelativeTo(this);
//        kh.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

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
            java.util.logging.Logger.getLogger(Master_Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master_Supplier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField14;
    // End of variables declaration//GEN-END:variables
}
