/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author 7
 */
public class Pembelian_HutangBG_RincianBarang extends javax.swing.JFrame {

    /**
     * Creates new form Pembelian_HutangBG_RincianBarang
     */
    String[] noFaktur;
    int[] hrgItem;
    int jum;
    String kode;
    Integer belumBayar;

    int keuangan1 = 0;
    int keuangan2 = 0;


    public Pembelian_HutangBG_RincianBarang() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public Pembelian_HutangBG_RincianBarang(String Kode, Integer BelumBayar, String[] noFaktur, int[] hrgItem, int jum) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.noFaktur = noFaktur;
        this.hrgItem = hrgItem;
        this.jum = jum;
        this.kode = Kode;
        this.belumBayar = BelumBayar;
        loadform(kode, belumBayar);
    }

    void loadform(String kode, Integer BelumBayar) {
        try {
            txtBelumBayar.setText(String.valueOf(BelumBayar).toString());
            String sql = "SELECT MAX(tm.kode_nama_keuangan) AS kode_nama_keuangan, SUM(tm.bayar_keuangan) AS bayar_keuangan, "
                    + "MAX(tm.kode_nama_keuangan2) AS kode_nama_keuangan2, SUM(tm.bayar_keuangan2) AS bayar_keuangan2, "
                    + "tm.keterangan_transaksi_master "
                    + "FROM transaksi_master tm "
                    + "WHERE faktur_bp = '" + kode + "'";
//            System.out.println("sqlzzxczxc: "+sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            int bayar = 0, bayar2 = 0;
            while (res.next()) {
                bayar = res.getInt("bayar_keuangan");
                bayar2 = res.getInt("bayar_keuangan2");
                textBayar1.setText(res.getString("bayar_keuangan"));
                textBayar2.setText(res.getString("bayar_keuangan2"));
                if (!textBayar1.getText().toString().equals("")) {
                    bayar = Integer.parseInt(textBayar1.getText().toString());
                } else {
                    bayar = 0;
                }
                if (!textBayar2.getText().toString().equals("")) {
                    bayar2 = Integer.parseInt(textBayar2.getText().toString());
                } else {
                    bayar2 = 0;
                }
            }
            System.out.println("Bayar1 = " + bayar);
            System.out.println("Bayar2 = " + bayar2);
            int kembalian = ((bayar + bayar2) + BelumBayar);
            textKembalian.setText(String.valueOf(kembalian));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }

    void loadkeuangan(String kode) {
        try {
            String sql = "SELECT kode_nama_keuangan, nama_keuangan "
                    + "FROM transaksi_nama_keuangan";
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(2);
                comKeuangan1.addItem(name);
                if (res.getString("kode_nama_keuangan").equals(kode)) {
                    comKeuangan1.setSelectedIndex(Integer.valueOf(kode) - 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }

    void loadkeuangan2(String kode) {
        try {
            String sql = "SELECT kode_nama_keuangan, nama_keuangan "
                    + "FROM transaksi_nama_keuangan";
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(2);
                comKeuangan2.addItem(name);
                if (res.getString("kode_nama_keuangan").equals(kode)) {
                    comKeuangan2.setSelectedIndex(Integer.valueOf(kode) - 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }

    void bayar() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        //cek keuangan1 & 2
        if (!textBayar1.getText().toString().equals("")) {
            keuangan1 = Integer.parseInt(textBayar1.getText().toString());
        } else {
            keuangan1 = 0;
        }
        if (!textBayar2.getText().toString().equals("")) {
            keuangan2 = Integer.parseInt(textBayar2.getText().toString());
        } else {
            keuangan2 = 0;
        }
        //cek kembalian
        int kembali = Integer.parseInt(textKembalian.getText().toString());
        if (kembali > 0) {
            if (keuangan2 > 0) {
                keuangan2 -= kembali;
            } else {
                keuangan1 -= kembali;
            }
        }
        try {
            Connection conn = (Connection) Koneksi.configDB();
            Statement stm = conn.createStatement();
            Statement stat = conn.createStatement();
            String kdb1 = "", kdb2 = "", kdsls = "";
            String sqlA = null, sqlB = null;
            for (int i = 0; i < jum; i++) {
                //nobBG
                String sql = "select biaya_pembayaran as sisa, biaya_pembelian as bayar from pembelian "
                        + "WHERE no_faktur_pembelian = '" + noFaktur[i] + "'";
//                    System.out.println("sqla: " + sql);
                ResultSet resnb = stm.executeQuery(sql);
                int byr1 = 0, byr2 = 0;
                String k1 = 0 + "','" + 0 + "','", k2 = 0 + "','" + 0 + "','";
                while (resnb.next()) {
                    int total = resnb.getInt("bayar");
                    int sisa = -1 * resnb.getInt("sisa");
                    if (keuangan1 >= 1) {
                        if (sisa == keuangan1) {
                            k1 = kdb1 + "','" + keuangan1 + "','";
                            byr1 = keuangan1;
                            sisa = 0;
                            keuangan1 = 0;
                        } else if (sisa < keuangan1) {
                            k1 = kdb1 + "','" + sisa + "','";
                            byr1 = sisa;
                            keuangan1 -= sisa;
                            sisa = 0;
                        } else {
                            k1 = kdb1 + "','" + keuangan1 + "','";
                            byr1 = keuangan1;
                            sisa -= keuangan1;
                            keuangan1 = 0;
                        }
                    }
                    if (sisa >= 1 && keuangan2 >= 1) {
                        if (sisa == keuangan2) {
                            k2 = kdb2 + "','" + keuangan2 + "','";
                            byr2 = keuangan2;
                            sisa = 0;
                            keuangan2 = 0;
                        } else if (sisa < keuangan2) {
                            k2 = kdb2 + "','" + sisa + "','";
                            byr2 = sisa;
                            keuangan2 -= sisa;
                            sisa = 0;
                        } else {
                            k2 = kdb2 + "','" + keuangan2 + "','";
                            byr2 = keuangan2;
                            sisa -= keuangan2;
                            keuangan2 = 0;
                        }
                    }
                    //update penjualan
                    sqlA = "update pembelian SET biaya_pembayaran = " + sisa * -1
                            + " WHERE no_faktur_pembelian = '" + noFaktur[i] + "'";
                    stat.executeUpdate(sqlA);
                }
            }
//            System.out.println("Jum = " + jum);
            JOptionPane.showMessageDialog(this, "Sukses");
            dispose();
            Pembelian_HutangBG a = new Pembelian_HutangBG();
            a.tampilTabel("*");

            JFrame frame = Pembelian_HutangBG_DetailFaktur.frameHutangBGDF;
            frame.dispose();

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

        jLabel69 = new javax.swing.JLabel();
        textKembalian = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        textKet = new javax.swing.JTextField();
        textBayar2 = new javax.swing.JTextField();
        comKeuangan1 = new javax.swing.JComboBox<>();
        jLabel70 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        txtBelumBayar = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jLabel81 = new javax.swing.JLabel();
        comKeuangan2 = new javax.swing.JComboBox<>();
        jLabel74 = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        textBayar1 = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel69.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel69.setText("Cara Bayar");

        textKembalian.setEditable(false);
        textKembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textKembalianActionPerformed(evt);
            }
        });

        jLabel75.setForeground(new java.awt.Color(51, 51, 51));
        jLabel75.setText("Bayar");

        textKet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textKetActionPerformed(evt);
            }
        });

        comKeuangan1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BCA | 1", "MANDIRI | 2", "BRI | 3", "CAT73 | 4", "TOKO CAT73 | 5" }));

        jLabel70.setForeground(new java.awt.Color(51, 51, 51));
        jLabel70.setText("Belum Bayar");

        jLabel78.setForeground(new java.awt.Color(51, 51, 51));
        jLabel78.setText("Bayar 2");

        txtBelumBayar.setEditable(false);

        jLabel77.setForeground(new java.awt.Color(51, 51, 51));
        jLabel77.setText("Keuangan 2");

        jLabel83.setForeground(new java.awt.Color(51, 51, 51));
        jLabel83.setText("Kembalian");

        jButton19.setBackground(new java.awt.Color(153, 153, 153));
        jButton19.setText("Close");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jLabel81.setForeground(new java.awt.Color(51, 51, 51));
        jLabel81.setText("Keterangan");

        comKeuangan2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BCA | 1", "MANDIRI | 2", "BRI | 3", "CAT73 | 4", "TOKO CAT73 | 5" }));

        jLabel74.setForeground(new java.awt.Color(51, 51, 51));
        jLabel74.setText("Keuangan");

        jButton18.setBackground(new java.awt.Color(85, 222, 93));
        jButton18.setText("Bayar");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jSeparator11.setForeground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator11)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel69)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 35, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel74)
                                    .addComponent(jLabel75)
                                    .addComponent(jLabel77)
                                    .addComponent(jLabel78))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel70)
                                        .addGap(70, 70, 70)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(comKeuangan1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtBelumBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textBayar1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(comKeuangan2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textBayar2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel81)
                                            .addComponent(jLabel83))
                                        .addGap(74, 74, 74)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(textKet, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton18)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(0, 62, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBelumBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comKeuangan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel74))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textBayar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comKeuangan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel77))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(textBayar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(textKet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel83))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textKembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textKembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textKembalianActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        if (textBayar1.getText().toString().equals("") && textBayar2.getText().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "Kolom bayar jangan kosong.");
            textBayar1.requestFocus(true);
        } else {
            bayar();
            this.dispose();
//            Pembelian_HutangBG_DetailFaktur df = new Pembelian_HutangBG_DetailFaktur();
//            df.dispose();
//            JFrame frameHutangBG = Pembelian_HutangBG.frameHutangBG;
//            frameHutangBG.dispose();
//            JFrame frameHutangBGDF = Pembelian_HutangBG_DetailFaktur.frameHutangBGDF;
//            frameHutangBGDF.dispose();
            Pembelian_HutangBG phbg = new Pembelian_HutangBG();
            phbg.setVisible(true);
            phbg.setLocationRelativeTo(null);
//            this.dispose();
            JOptionPane.showMessageDialog(null, "Pembayaran hutang berhasil.");
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void textKetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textKetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textKetActionPerformed

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
            java.util.logging.Logger.getLogger(Pembelian_HutangBG_RincianBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_HutangBG_RincianBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_HutangBG_RincianBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_HutangBG_RincianBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pembelian_HutangBG_RincianBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comKeuangan1;
    private javax.swing.JComboBox<String> comKeuangan2;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JTextField textBayar1;
    private javax.swing.JTextField textBayar2;
    private javax.swing.JTextField textKembalian;
    private javax.swing.JTextField textKet;
    private javax.swing.JTextField txtBelumBayar;
    // End of variables declaration//GEN-END:variables
}
