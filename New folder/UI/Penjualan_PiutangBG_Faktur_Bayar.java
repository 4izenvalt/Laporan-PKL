/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import static UI.Penjualan_Piutang_Bayar.id_oto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class Penjualan_PiutangBG_Faktur_Bayar extends javax.swing.JFrame {

    /**
     * Creates new form Penjualan_piutangBGFakturBayar
     */
    
    String[] noFaktur;
    int[] hrgItem;
    int jum;
    
    int keuangan1 = 0;
    int keuangan2 = 0;
    public Penjualan_PiutangBG_Faktur_Bayar() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    public Penjualan_PiutangBG_Faktur_Bayar(String kode, Integer BelumBayar, String[] noFaktur, int[] hrgItem, int jum) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.noFaktur = noFaktur;
        this.hrgItem = hrgItem;
        this.jum = jum;
        loadform(kode, BelumBayar);
    }
    void loadform(String kode, Integer BelumBayar){
        try {
            txtBelumBayar.setText(String.valueOf(BelumBayar));
            String sql = "SELECT MAX(tm.kode_nama_keuangan) AS kode_nama_keuangan, SUM(tm.bayar_keuangan) AS bayar_keuangan, "
                    + "MAX(tm.kode_nama_keuangan2) AS kode_nama_keuangan2, SUM(tm.bayar_keuangan2) AS bayar_keuangan2, "
                    + "tm.keterangan_transaksi_master "
                    + "FROM transaksi_master tm "
                    + "WHERE faktur_bp = '"+kode+"'";
//            System.out.println("sqlzzxczxc: "+sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            int bayar = 0, bayar2 = 0;
            while (res.next()) {
                bayar = Integer.valueOf(res.getString("bayar_keuangan"));
                bayar2 = Integer.valueOf(res.getString("bayar_keuangan2"));
                txtBayar1.setText(res.getString("bayar_keuangan"));
                txtBayar2.setText(res.getString("bayar_keuangan2"));
                loadkeuangan(res.getString("kode_nama_keuangan"));
                loadkeuangan2(res.getString("kode_nama_keuangan2"));
                txtKet.setText(res.getString("keterangan_transaksi_master"));
            }
            int kembalian = ((bayar+bayar2)+BelumBayar);
            txtKembalian.setText(String.valueOf(kembalian));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }
    void loadkeuangan(String kode){
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
                    comKeuangan1.setSelectedIndex(Integer.valueOf(kode)-1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }
    void loadkeuangan2(String kode){
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
                    comKeuangan2.setSelectedIndex(Integer.valueOf(kode)-1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }
    void bayar(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        //cek keuangan1 & 2
        if (!txtBayar1.getText().toString().equals("")) {
            keuangan1 = Integer.parseInt(txtBayar1.getText().toString());
        } else {
            keuangan1 = 0;
        }
        if (!txtBayar2.getText().toString().equals("")) {
            keuangan2 = Integer.parseInt(txtBayar2.getText().toString());
        } else {
            keuangan2 = 0;
        }
        //cek kembalian
        int kembali = Integer.parseInt(txtKembalian.getText().toString());
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
                    String sql = "select pembaran_udah_bayar as sisa, pembayaran_aktif as bayar from penjualan "
                            + "WHERE no_faktur_penjualan = '" + noFaktur[i] + "'";
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
                        sqlA = "update penjualan SET pembaran_udah_bayar = " + sisa * -1
                                + " WHERE no_faktur_penjualan = '" + noFaktur[i] + "'";
                        stat.executeUpdate(sqlA);
                    }
            }
//            System.out.println("Jum = " + jum);
            JOptionPane.showMessageDialog(this, "Sukses");
            dispose();
            Penjualan_Piutang a = new Penjualan_Piutang();
            a.tampilTabel("*");

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

        jPanel18 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel70 = new javax.swing.JLabel();
        txtBelumBayar = new javax.swing.JTextField();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        comKeuangan1 = new javax.swing.JComboBox<>();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        txtBayar1 = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        comKeuangan2 = new javax.swing.JComboBox<>();
        jLabel78 = new javax.swing.JLabel();
        txtBayar2 = new javax.swing.JTextField();
        txtKet = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        txtKembalian = new javax.swing.JTextField();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jLabel69.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(102, 102, 102));
        jLabel69.setText("Cara Bayar");

        jSeparator11.setForeground(new java.awt.Color(204, 204, 204));

        jLabel70.setForeground(new java.awt.Color(51, 51, 51));
        jLabel70.setText("Belum Bayar");

        txtBelumBayar.setEditable(false);

        jButton18.setBackground(new java.awt.Color(85, 222, 93));
        jButton18.setText("Bayar");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setBackground(new java.awt.Color(153, 153, 153));
        jButton19.setText("Close");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        comKeuangan1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BCA | 1", "MANDIRI | 2", "BRI | 3", "CAT73 | 4", "TOKO CAT73 | 5" }));

        jLabel74.setForeground(new java.awt.Color(51, 51, 51));
        jLabel74.setText("Keuangan");

        jLabel75.setForeground(new java.awt.Color(51, 51, 51));
        jLabel75.setText("Bayar");

        jLabel77.setForeground(new java.awt.Color(51, 51, 51));
        jLabel77.setText("Keuangan 2");

        comKeuangan2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BCA | 1", "MANDIRI | 2", "BRI | 3", "CAT73 | 4", "TOKO CAT73 | 5" }));

        jLabel78.setForeground(new java.awt.Color(51, 51, 51));
        jLabel78.setText("Bayar 2");

        jLabel81.setForeground(new java.awt.Color(51, 51, 51));
        jLabel81.setText("Keterangan");

        jLabel83.setForeground(new java.awt.Color(51, 51, 51));
        jLabel83.setText("Kembalian");

        txtKembalian.setEditable(false);
        txtKembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKembalianActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator11)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel69)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(0, 37, Short.MAX_VALUE)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel74)
                                    .addComponent(jLabel75)
                                    .addComponent(jLabel77)
                                    .addComponent(jLabel78))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                                        .addComponent(jLabel70)
                                        .addGap(70, 70, 70)
                                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(comKeuangan1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtBelumBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtBayar1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(comKeuangan2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtBayar2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel83, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                                                .addGap(130, 130, 130)
                                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                                        .addComponent(jButton18)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(jPanel18Layout.createSequentialGroup()
                                            .addComponent(jLabel81)
                                            .addGap(74, 74, 74)
                                            .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 65, Short.MAX_VALUE))))))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBelumBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comKeuangan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel74))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBayar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comKeuangan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel77))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(txtBayar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel83))
                .addGap(28, 28, 28)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtKembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKembalianActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
       if (txtBayar1.getText().toString().equals("") && txtBayar2.getText().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "Kolom bayar jangan kosong.");
            txtBayar1.requestFocus(true);
        } else {
            bayar();
//            this.dispose();
            JOptionPane.showMessageDialog(null, "Pembayaran hutang berhasil.");
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
         this.dispose();
    }//GEN-LAST:event_jButton19ActionPerformed

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
            java.util.logging.Logger.getLogger(Penjualan_PiutangBG_Faktur_Bayar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_PiutangBG_Faktur_Bayar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_PiutangBG_Faktur_Bayar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_PiutangBG_Faktur_Bayar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan_PiutangBG_Faktur_Bayar().setVisible(true);
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
    private javax.swing.JPanel jPanel18;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JTextField txtBayar1;
    private javax.swing.JTextField txtBayar2;
    private javax.swing.JTextField txtBelumBayar;
    private javax.swing.JTextField txtKembalian;
    private javax.swing.JTextField txtKet;
    // End of variables declaration//GEN-END:variables
}
