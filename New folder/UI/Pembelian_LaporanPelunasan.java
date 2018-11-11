/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Clock;
import Java.Currency_Column;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author User
 */
public class Pembelian_LaporanPelunasan extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    private Clock clock;
    private SimpleDateFormat formatTanggal;
    private Date tglHariIni;
    private String sql_table = "SELECT tm.faktur_bp, tm.tgl_transaksi_master, s.nama_supplier, s.kota_supplier, SUM(tm.kredit_transaksi_master) AS pengeluaran "
            + "FROM transaksi_master tm JOIN pembelian p ON tm.no_faktur=p.no_faktur_pembelian "
            + "JOIN supplier s on p.kode_supplier=s.kode_supplier ";
    private String sql_where = "WHERE p.biaya_pembelian = 0 AND tm.faktur_bp != '' ";
    private String sql_group = "GROUP BY tm.faktur_bp ";
    private String sql_order = "ORDER BY tm.tgl_transaksi_master ASC ";

    public Pembelian_LaporanPelunasan() {
        initComponents();
        this.setLocationRelativeTo(null);
        tglHariIni = new Date();
        formatTanggal = new SimpleDateFormat("yyyy-MM-dd");
        tgl_awal.setDate(tglHariIni);
        tgl_akhir.setDate(tglHariIni);

        String sql = sql_table + sql_where + sql_group + sql_order;
        clock = new Clock(jLabel132, 3);
        load_tabel_pelunasan(sql);
        TableColumnModel m = tabel_pelunasan.getColumnModel();
        m.getColumn(5).setCellRenderer(new Currency_Column());
        m.getColumn(6).setCellRenderer(new Currency_Column());
    }

    private void load_tabel_pelunasan(String sql) {
        removeRow(tabel_pelunasan);
        DefaultTableModel model = (DefaultTableModel) tabel_pelunasan.getModel();
        try {
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            int i = 1;
            while (res.next()) {
                model.addRow(new Object[]{
                    i++,
                    res.getString("faktur_bp"),
                    res.getString("tgl_transaksi_master"),
                    res.getString("nama_supplier"),
                    res.getString("kota_supplier"),
                    0,
                    res.getInt("pengeluaran")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error = " + e);
        }
    }

    private void load_pelunasan_rincian() {
        String faktur_bp = tabel_pelunasan.getValueAt(tabel_pelunasan.getSelectedRow(), 1).toString();
        Pembelian_LaporanPelunasan_DetailTransaksi x = new Pembelian_LaporanPelunasan_DetailTransaksi(faktur_bp);
        x.setVisible(true);
        x.setFocusable(true);
    }

    private void removeRow(JTable jtab) {
        DefaultTableModel model = (DefaultTableModel) jtab.getModel();
        int row = jtab.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabel_pelunasan = new javax.swing.JTable();
        txt_cari = new javax.swing.JTextField();
        tgl_awal = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        tgl_akhir = new com.toedter.calendar.JDateChooser();
        cbox_tgl = new javax.swing.JCheckBox();
        proses_tgl = new javax.swing.JButton();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(412, 45));

        jLabel92.setBackground(new java.awt.Color(217, 237, 247));
        jLabel92.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel92.setText("Laporan Pelunasan");

        jLabel132.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel92)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 492, Short.MAX_VALUE)
                .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel132, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addComponent(jLabel92))
                .addGap(13, 13, 13))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tabel_pelunasan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No.", "No Faktur", "Tanggal", "Supplier", "Kota", "Pemasukan", "Pengeluaran"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel_pelunasan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_pelunasanMouseClicked(evt);
            }
        });
        tabel_pelunasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabel_pelunasanKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tabel_pelunasan);

        txt_cari.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cari", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });

        tgl_awal.setDateFormatString("yyyy-MM-dd");
        tgl_awal.setEnabled(false);

        jLabel1.setText("s.d");

        tgl_akhir.setDateFormatString("yyyy-MM-dd");
        tgl_akhir.setEnabled(false);

        cbox_tgl.setText("Tanggal");
        cbox_tgl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbox_tglItemStateChanged(evt);
            }
        });

        proses_tgl.setText("Proses");
        proses_tgl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proses_tglActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbox_tgl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proses_tgl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbox_tgl)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tgl_awal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(3, 3, 3)))
                            .addComponent(proses_tgl, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGap(555, 555, 555))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(60, 60, 60)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(37, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 873, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(925, 758));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tabel_pelunasanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_pelunasanMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            load_pelunasan_rincian();
        }
    }//GEN-LAST:event_tabel_pelunasanMouseClicked

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        String sql = sql_table + sql_where
                + " AND tm.faktur_bp LIKE '%" + txt_cari.getText() + "%' "
                + sql_group + sql_order;
        load_tabel_pelunasan(sql);
    }//GEN-LAST:event_txt_cariActionPerformed

    private void cbox_tglItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbox_tglItemStateChanged
        if (cbox_tgl.isSelected()) {
            tgl_awal.setEnabled(true);
            tgl_akhir.setEnabled(true);
        } else {
            tgl_awal.setEnabled(false);
            tgl_akhir.setEnabled(false);
            tgl_awal.setDate(tglHariIni);
            tgl_akhir.setDate(tglHariIni);
        }
    }//GEN-LAST:event_cbox_tglItemStateChanged

    private void proses_tglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proses_tglActionPerformed
        String sql = "";
        if (cbox_tgl.isSelected()) {
            sql = sql_table + sql_where
                    + " AND tm.tgl_transaksi_master BETWEEN '" + formatTanggal.format(tgl_awal.getDate()) + "' AND '" + formatTanggal.format(tgl_akhir.getDate()) + "' "
                    + sql_group + sql_order;
        } else {
            sql = sql_table + sql_where + sql_group + sql_order;
        }
        load_tabel_pelunasan(sql);
    }//GEN-LAST:event_proses_tglActionPerformed

    private void tabel_pelunasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabel_pelunasanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            evt.consume();
            load_pelunasan_rincian();
        }
    }//GEN-LAST:event_tabel_pelunasanKeyPressed

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
            java.util.logging.Logger.getLogger(Pembelian_LaporanPelunasan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_LaporanPelunasan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_LaporanPelunasan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_LaporanPelunasan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Pembelian_LaporanPelunasan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbox_tgl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JButton proses_tgl;
    private javax.swing.JTable tabel_pelunasan;
    private com.toedter.calendar.JDateChooser tgl_akhir;
    private com.toedter.calendar.JDateChooser tgl_awal;
    private javax.swing.JTextField txt_cari;
    // End of variables declaration//GEN-END:variables
}
