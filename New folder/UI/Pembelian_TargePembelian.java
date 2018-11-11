/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Clock;
import Java.Connect;
import Java.ListBarang;
import Java.ListBarangTarget;
import Java.TrBarang;
import Java.modelTabelBarang1;
import Java.modelTabelBarangTarget;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author 7
 */
public class Pembelian_TargePembelian extends javax.swing.JDialog {

    /**
     * Creates new form Pembelian_TargePembelian
     */
    private ResultSet hasil;
    private PreparedStatement PS;
    private Connect connection;
    private Clock clock;
    private ArrayList<ListBarangTarget> list;
    private ListBarangTarget listBarang;
    private TableModel model;
    private Frame parent;
    
    public Pembelian_TargePembelian() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.parent = parent;
        clock = new Clock(lblTgl, 3);
        this.connection = new Connect();
        //tanggal_jam_sekarang();
        tampilTabel("*");
    }
    
    public Pembelian_TargePembelian(java.awt.Frame parent, boolean modal, Connect connection) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.parent = parent;
        clock = new Clock(lblTgl, 3);
        this.connection = connection;
        //tanggal_jam_sekarang();
        tampilTabel("*");
    }
    
    private void tampilTabel(String param) {
        if (param.equals("*")) {
            param = "";
        }
        String data = "";
        
        try {
            String sql = "select bt.kode_barang_target, bt.jumlah_target, bt.total_target, bt.tgl_target, bt.ket_target, b.kode_barang, b.nama_barang, "
                    + "bk.kode_barang_konversi, k.nama_konversi, bk.identitas_konversi, s.kode_supplier, s.nama_supplier "
                    + "from barang b, barang_target bt, barang_konversi bk, konversi k, supplier s "
                    + "where b.kode_barang = bt.kode_barang "
                    + "and bt.kode_barang_konversi = bk.kode_barang_konversi "
                    + "and bk.kode_konversi = k.kode_konversi "
                    + "and bt.kode_supplier = s.kode_supplier "
                    + "and b.nama_barang like '%" + param + "%'";
            
            System.out.println(sql);
            
            hasil = connection.ambilData(sql);
            setModel(hasil);
        } catch (Exception e) {
            e.printStackTrace();
            
            System.out.println("ERROR -> " + e.getMessage() + " = " + e.toString());
        }
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
                    lblTgl.setText(tahun + "-" + (bulan + 1) + "-" + hari + " " + jam + ":" + menit + ":" + detik);
                    
                }
            }
        };
        p.start();
    }
    
    private void setModel(ResultSet hasil) {
        try {
            list = new ArrayList<>();
            int a = 1;
            while (hasil.next()) {
                
                this.listBarang = new ListBarangTarget();
                this.listBarang.setNo(a);
                
                this.listBarang.setKode_barang(hasil.getInt("kode_barang"));
                this.listBarang.setKode_barang_target(hasil.getString("kode_barang_target"));
                this.listBarang.setKode_barang_konversi(hasil.getInt("kode_barang_konversi"));
                this.listBarang.setKode_supplier(hasil.getInt("kode_supplier"));
                this.listBarang.setJumlah(hasil.getInt("jumlah_target"));
                this.listBarang.setTotal(hasil.getInt("total_target"));
                this.listBarang.setNama_barang(hasil.getString("nama_barang"));
                this.listBarang.setNama_konversi(hasil.getString("nama_konversi"));
                this.listBarang.setNama_supplier(hasil.getString("nama_supplier"));
                this.listBarang.setTgl(hasil.getString("tgl_target"));
                this.listBarang.setKet(hasil.getString("ket_target"));
                this.listBarang.setIdentitas_konversi(hasil.getInt("identitas_konversi"));
                list.add(listBarang);
                listBarang = null;
                a++;
            }
            model = new modelTabelBarangTarget(list);
            jTable1.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        lblTgl = new javax.swing.JLabel();
        comBarang = new javax.swing.JComboBox<String>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_manilla-folder-new_23456.png"))); // NOI18N
        jLabel1.setText("F1- New");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_gtk-edit_20500.png"))); // NOI18N
        jLabel2.setText("F2-Edit");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N
        jLabel3.setText("Delete");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setText("Kriteria");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode", "Nama Barang", "Jumlah Target", "Satuan", "Tanggal", "Total", "Keterangan", "Supplier"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("TARGET PEMBELIAN");

        lblTgl.setText("Tanggal");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator4)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(199, 199, 199)
                                .addComponent(jLabel5)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTgl)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(lblTgl)
                    .addComponent(comBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
        if (evt.getClickCount() == 2) {
            TableModel tabelModel = jTable1.getModel();
            int No = Integer.parseInt(tabelModel.getValueAt(jTable1.getSelectedRow(), 0).toString()) - 1;
            listBarang = list.get(No);
            System.out.println("");
            System.out.println("Yg di klik = " + listBarang.getKode_barang_target());
            Pembelian_TargetPembelian_DetailPembelian dp = new Pembelian_TargetPembelian_DetailPembelian(listBarang);
            dp.setVisible(true);
            dp.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        Pembelian_TargetPembelian_New n = new Pembelian_TargetPembelian_New(parent, rootPaneCheckingEnabled, connection, "*");
        n.setVisible(true);
        n.setLocationRelativeTo(null);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        if (jTable1.getSelectedRow() >= 0) {
            int i = jTable1.getSelectedRow();
            
            TableModel model = jTable1.getModel();
            Pembelian_TargetPembelian_New n = new Pembelian_TargetPembelian_New(parent, rootPaneCheckingEnabled, connection, model.getValueAt(i, 1).toString());
            n.setVisible(true);
            n.setLocationRelativeTo(null);
            //updateData(listBarang);
            listBarang = null;
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Group Terlebih Dahulu");
        }
        System.out.println("Tabel edit = " + jTable1.getSelectedRow());
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        //deleteData(listBarang);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
//        // TODO add your handling code here:
//        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//        int selectedRow = jTable1.getSelectedRow();
//        System.out.println(selectedRow);
//        //int selectedRow = tbl_tambah_mutasi.getSelectedRow();
//        //int baris = tbl_tambah_mutasi.getRowCount();
//
//        TableModel tabelModel;
//        tabelModel = jTable1.getModel();
//        try {
//            if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
//                int hapus = 1;
//                hapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
//                if (hapus == 0) {
//                    hapus_tabel();
//                }
//            } else if (evt.getKeyCode() == KeyEvent.VK_F1) {
//                pop_up();
//            }
//            else if (evt.getKeyCode()== KeyEvent.VK_F2) {
//                
//            }
//        } catch (NullPointerException e) {
//            JOptionPane.showMessageDialog(null, "Test");
//        }
    }//GEN-LAST:event_formKeyPressed

//    private void updateData(ListBarangTarget listBarang) {
//    PS = null;
//        try {
//            String sql = "update barang_target set kode_barang=?,kode_supplier=?,kode_barang_konversi=?,jumlah_target=?,total_target=?,ket_target=?"
//                    + "where kode_barang_target=?";
//            PS = connection.Connect().prepareStatement(sql);
//            PS.setString(1, listBarang.getKode_barang_target());
//            PS.setString(2, listBarang.getNama_barang());
//            PS.setString(3, listBarang.getNama_supplier());
//            PS.setString(4, listBarang.getNama_konversi());
//            PS.setInt(5, listBarang.getJumlah());
//            PS.setInt(6, listBarang.getTotal());
//            PS.setString(7, listBarang.getKet());
//            PS.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("Pembelian" + e.toString());
//        }
//}
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
            java.util.logging.Logger.getLogger(Pembelian_TargePembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_TargePembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_TargePembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_TargePembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pembelian_TargePembelian().setVisible(true);
            }
        });
    }
    
//    private void deleteData(ListBarangTarget listBarang) {
//        PS = null;
//        try {
//            String sql = "delete from barang_target where kode_barang=?";
//            System.out.println(sql);
//            PS = connection.Connect().prepareStatement(sql);
//            PS.setString(1, listBarang.getKode_barang_target());
//            PS.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("pembelian Target" + e.toString());
//        } finally {
//            tampilTabel("-1");
//        }
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comBarang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblTgl;
    // End of variables declaration//GEN-END:variables
}
