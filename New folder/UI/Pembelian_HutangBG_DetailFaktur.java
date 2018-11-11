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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author User
 */
public class Pembelian_HutangBG_DetailFaktur extends javax.swing.JFrame {

    static JFrame frameHutangBGDF;
    private int totalHutang = 0;
    private int jumFaktur = 0;
    private String kodeFaktur;
    private String[] noFaktur;
    private int[] hrgItem;

    /**
     * Creates new form NewJFrame
     */
    public Pembelian_HutangBG_DetailFaktur() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public Pembelian_HutangBG_DetailFaktur(String kodeBG) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.kodeFaktur = kodeBG;
        tampilTabel(kodeBG, "*");
    }

    public void tampilTabel(String param, String nama) {
        removeRow();
        if (param.equals("*")) {
            param = "";
        }
        DefaultTableModel model = (DefaultTableModel) tableHutangBGFaktur.getModel();
        int i = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            String sql = "SELECT * "
                    + "FROM pembelian p, supplier s "
                    + "WHERE p.kode_supplier = s.kode_supplier AND "
                    + "faktur_bg='" + param + "'";
            System.out.println("sql: " + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                model.addRow(new Object[]{
                    false,
                    i++,
                    res.getString("no_faktur_pembelian"),
                    sdf.format(res.getDate("tgl_pembelian")),
                    res.getString("nama_supplier"),
                    res.getString("biaya_pembayaran")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }

    public void removeRow() {
        DefaultTableModel model = (DefaultTableModel) tableHutangBGFaktur.getModel();
        int row = tableHutangBGFaktur.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    public void autoSum() {
        int totalHutang = 0;
        int jumlahBaris = tableHutangBGFaktur.getRowCount();
        boolean action;
        int hargaItem = 0;
        int jumFaktur = 0;
        noFaktur = new String[jumlahBaris];
        hrgItem = new int[jumlahBaris];

        TableModel tabelModel;
        tabelModel = tableHutangBGFaktur.getModel();
        for (int i = 0; i < jumlahBaris; i++) {
            action = (boolean) tabelModel.getValueAt(i, 0);
            if (action == true) {
                hargaItem = Integer.valueOf(tabelModel.getValueAt(i, 5).toString());
                System.out.println("hargaitem: " + hargaItem);
                hrgItem[jumFaktur] = hargaItem;
                noFaktur[jumFaktur] = String.valueOf(tabelModel.getValueAt(i, 2));
                jumFaktur++;
            } else {
                hargaItem = 0;
            }
            totalHutang += hargaItem;
        }
        txt_Total.setText("" + totalHutang);

        this.jumFaktur = jumFaktur;
        this.totalHutang = totalHutang;

    }

    void selectAll() {
        DefaultTableModel model = (DefaultTableModel) tableHutangBGFaktur.getModel();
        if (cbAll.isSelected()) {
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txt_Total = new javax.swing.JTextField();
        btBayar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableHutangBGFaktur = new javax.swing.JTable();
        cbAll = new javax.swing.JCheckBox();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel48.setText("Detail Faktur");

        txt_Total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TotalActionPerformed(evt);
            }
        });

        btBayar.setBackground(new java.awt.Color(71, 166, 227));
        btBayar.setText("Bayar");
        btBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBayarActionPerformed(evt);
            }
        });

        tableHutangBGFaktur.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "", "No", "No Faktur Pembelian", "Tanggal", "Supplier", "biaya"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableHutangBGFaktur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableHutangBGFakturMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableHutangBGFaktur);

        cbAll.setText("Pilih Semua");
        cbAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(txt_Total, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btBayar)
                                .addGap(18, 18, 18)
                                .addComponent(cbAll))
                            .addComponent(jLabel48))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btBayar)
                    .addComponent(cbAll))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(804, 454));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_TotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TotalActionPerformed

    private void btBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBayarActionPerformed
        Pembelian_HutangBG_RincianBarang rb = new Pembelian_HutangBG_RincianBarang(kodeFaktur, Integer.valueOf(txt_Total.getText().toString()), noFaktur, hrgItem, jumFaktur);
        rb.setVisible(true);
        rb.setLocationRelativeTo(null);
    }//GEN-LAST:event_btBayarActionPerformed

    private void cbAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAllActionPerformed
        selectAll();
    }//GEN-LAST:event_cbAllActionPerformed

    private void tableHutangBGFakturMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHutangBGFakturMouseClicked
        autoSum();
    }//GEN-LAST:event_tableHutangBGFakturMouseClicked

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
            java.util.logging.Logger.getLogger(Pembelian_HutangBG_DetailFaktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_HutangBG_DetailFaktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_HutangBG_DetailFaktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_HutangBG_DetailFaktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        frameHutangBGDF = new JFrame("HutangBG_DetailFaktur");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pembelian_HutangBG_DetailFaktur().setVisible(true);
            }
        });
    }
    protected void setDispose(){
        this.dispose();
    }
    
    public JFrame getJframe(){
        return Pembelian_HutangBG_DetailFaktur.frameHutangBGDF;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBayar;
    private javax.swing.JCheckBox cbAll;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tableHutangBGFaktur;
    private javax.swing.JTextField txt_Total;
    // End of variables declaration//GEN-END:variables
}
