/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import static UI.Pembelian_Hutang.dotConverter;
import com.sun.glass.events.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class Master_MutasiAntarKode extends javax.swing.JFrame {

    /**
     * Creates new form Penjualan_penjualanFIT
     */
    public Master_MutasiAntarKode() {
        initComponents();
        this.setLocationRelativeTo(null);
        loadTable();

    }

    public void removeRow() {
        DefaultTableModel model = (DefaultTableModel) tbl_mutasi_kode_daftar.getModel();
        int row = tbl_mutasi_kode_daftar.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    public void loadTable() {
        removeRow();

        DefaultTableModel model = (DefaultTableModel) tbl_mutasi_kode_daftar.getModel();
        int i = 1;
        String sql = "";
        try {
            if (tgl_akhir.isEnabled()) {
                SimpleDateFormat ft_awal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String awal = ft_awal.format(tgl_awal.getDate());

                SimpleDateFormat ft_akhir = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String akhir = ft_akhir.format(tgl_akhir.getDate());

                sql = "select * from mutasi_kode_detail_utama "
                        + "WHERE (no_bukti like '%" + txt_kriteria.getText()
                        + "%' OR keterangan like '%" + txt_kriteria.getText() + "%') "
                        + "AND tgl_mutasi between '" + awal + "' and '" + akhir + "' order by tgl_mutasi DESC";

            } else {
                sql = "select * from mutasi_kode_detail_utama "
                        + "WHERE (no_bukti like '%" + txt_kriteria.getText()
                        + "%' OR keterangan like '%" + txt_kriteria.getText()
                        + "%')order by tgl_mutasi DESC";
            }
            System.out.println(sql);
            System.out.println("sqlll: " + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            int baris = 0;
            while (res.next()) {
                model.addRow(new Object[]{"", "", ""});
                String tanggal = res.getString("tgl_mutasi");
                String no_bukti = res.getString("no_bukti");
                String keterangan = res.getString("keterangan");
                model.setValueAt(tanggal, baris, 0);
                model.setValueAt(no_bukti, baris, 1);
                model.setValueAt(keterangan, baris, 2);
                baris++;
            }
            conn.close();
            res.close();
        } catch (NullPointerException e) {
            System.out.println("tanggal kosong");
//            JOptionPane.showMessageDialog(null, "Tanggal tidak boleh kosong");    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }

    }

    void mutasiBaru() {
        Master_MutasiAntarKode_DetailNew mkdn = new Master_MutasiAntarKode_DetailNew(this);
        mkdn.setVisible(true);
        mkdn.setFocusable(true);
    }

    void hapusMutasi() {
        int hapus = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin Menghapus data ini ?", "Konfimasi Tolak Hapus Data", JOptionPane.OK_CANCEL_OPTION);
        if (hapus == JOptionPane.OK_OPTION) {
            try {
                Connection conn = (Connection) Koneksi.configDB();
                String sql = "delete from mutasi_kode_detail where no_bukti='" + tbl_mutasi_kode_daftar.getValueAt(tbl_mutasi_kode_daftar.getSelectedRow(), 1) + "'";
                PreparedStatement st = conn.prepareStatement(sql);
                st.executeUpdate();
                System.out.println(sql);
                String sql2 = "delete from mutasi_kode_detail_utama where no_bukti='" + tbl_mutasi_kode_daftar.getValueAt(tbl_mutasi_kode_daftar.getSelectedRow(), 1) + "'";
                PreparedStatement st2 = conn.prepareStatement(sql2);
                st2.executeUpdate();
                System.out.println(sql2);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Delete Data Gagal");
            }
            JOptionPane.showMessageDialog(null, "Data sudah dihapus.");
            loadTable();
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

        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_mutasi_kode_daftar = new javax.swing.JTable();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        labelNew = new javax.swing.JLabel();
        labelDelete = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_kriteria = new javax.swing.JTextField();
        tgl_semua = new javax.swing.JRadioButton();
        tgl_rentang = new javax.swing.JRadioButton();
        tgl_awal = new com.toedter.calendar.JDateChooser();
        tgl_akhir = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel14.setText("Kriteria");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.lightGray));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel16.setText("MUTASI BARANG ANTAR KODE");

        tbl_mutasi_kode_daftar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tanggal", "No. Bukti", "Keterangan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_mutasi_kode_daftar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_mutasi_kode_daftarMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tbl_mutasi_kode_daftar);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 992, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(312, 312, 312)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
        );

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator4.setForeground(new java.awt.Color(153, 153, 153));

        labelNew.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        labelNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_manilla-folder-new_23456.png"))); // NOI18N
        labelNew.setText("F2-New");
        labelNew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelNewMouseClicked(evt);
            }
        });

        labelDelete.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        labelDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N
        labelDelete.setText("F5-Delete");
        labelDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelDeleteMouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel15.setText("s.d.");

        txt_kriteria.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_kriteria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_kriteriaKeyReleased(evt);
            }
        });

        tgl_semua.setSelected(true);
        tgl_semua.setText("Semua");
        tgl_semua.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tgl_semuaStateChanged(evt);
            }
        });

        tgl_rentang.setText("Tanggal");
        tgl_rentang.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tgl_rentangStateChanged(evt);
            }
        });

        tgl_awal.setDateFormatString(" yyyy- MM-dd");
        tgl_awal.setEnabled(false);
        tgl_awal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_awalKeyPressed(evt);
            }
        });

        tgl_akhir.setDateFormatString(" yyyy- MM-dd");
        tgl_akhir.setEnabled(false);
        tgl_akhir.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tgl_akhirPropertyChange(evt);
            }
        });
        tgl_akhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tgl_akhirKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_kriteria, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelDelete))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tgl_semua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tgl_rentang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(542, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(labelNew)
                        .addComponent(txt_kriteria, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(labelDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(tgl_semua)
                        .addComponent(tgl_rentang))
                    .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void labelNewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelNewMouseClicked
        //this.dispose();
        mutasiBaru();
    }//GEN-LAST:event_labelNewMouseClicked

    private void tbl_mutasi_kode_daftarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_mutasi_kode_daftarMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            try {
                Master_MutasiAntarKode_DetailNew magdn = new Master_MutasiAntarKode_DetailNew(tbl_mutasi_kode_daftar.getValueAt(tbl_mutasi_kode_daftar.getSelectedRow(), 1).toString(), this);
                magdn.setVisible(true);
                magdn.setFocusable(true);
                //this.dispose();
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Pilih data yang ada");
            }
        }
    }//GEN-LAST:event_tbl_mutasi_kode_daftarMouseClicked

    private void tgl_awalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_awalKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {

        }
    }//GEN-LAST:event_tgl_awalKeyPressed

    private void tgl_rentangStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tgl_rentangStateChanged
        if (tgl_rentang.isSelected()) {
            tgl_semua.setSelected(false);
            tgl_awal.setEnabled(true);
            tgl_akhir.setEnabled(true);
        } else {
            tgl_semua.setSelected(true);
        }
        loadTable();
    }//GEN-LAST:event_tgl_rentangStateChanged

    private void tgl_semuaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tgl_semuaStateChanged
        if (tgl_semua.isSelected()) {
            tgl_rentang.setSelected(false);
            tgl_awal.setEnabled(false);
            tgl_akhir.setEnabled(false);
        } else {
            tgl_rentang.setSelected(true);
        }
        loadTable();
    }//GEN-LAST:event_tgl_semuaStateChanged

    private void txt_kriteriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_kriteriaKeyReleased
        txt_kriteria.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                loadTable();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                loadTable();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                //loadTable();  
            }

        });
    }//GEN-LAST:event_txt_kriteriaKeyReleased

    private void labelDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelDeleteMouseClicked
        hapusMutasi();
    }//GEN-LAST:event_labelDeleteMouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F2) { //saat menekan f2   
            mutasiBaru();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F5) { //saat menekan f5
            hapusMutasi();
        }
    }//GEN-LAST:event_formKeyPressed

    private void tgl_akhirKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_akhirKeyReleased
        loadTable();
    }//GEN-LAST:event_tgl_akhirKeyReleased

    private void tgl_akhirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tgl_akhirPropertyChange
        loadTable();
    }//GEN-LAST:event_tgl_akhirPropertyChange

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
            java.util.logging.Logger.getLogger(Master_MutasiAntarKode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiAntarKode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiAntarKode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiAntarKode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Master_MutasiAntarKode().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel labelDelete;
    private javax.swing.JLabel labelNew;
    private javax.swing.JTable tbl_mutasi_kode_daftar;
    private com.toedter.calendar.JDateChooser tgl_akhir;
    private com.toedter.calendar.JDateChooser tgl_awal;
    private javax.swing.JRadioButton tgl_rentang;
    private javax.swing.JRadioButton tgl_semua;
    private javax.swing.JTextField txt_kriteria;
    // End of variables declaration//GEN-END:variables
}
