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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author User
 */
public final class Pembelian_LaporanFaktur extends javax.swing.JFrame {

    public String txttabel;

    public String gettxttabel() {
        return txttabel;
    }
    public javax.swing.JTextField tabel_faktur;

    public Pembelian_LaporanFaktur() {
        initComponents();
        this.setVisible(true);
        dateChooser();
        loadTable();

    }

    public void dateChooser() {
        Calendar cal = Calendar.getInstance();
        tglAwal.setDate(cal.getTime());
        tglAkhir.setDate(cal.getTime());
    }

    public void loadTable() {
        removeRow();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //  System.out.println("" + sdf.format(tglAwal.getDate()));

        DefaultTableModel model = (DefaultTableModel) tblPembelianLaporanFaktur.getModel();
        int i = 1;
        try {
            String sql = "select * from pembelian where tgl_pembelian BETWEEN '"
                    + sdf.format(tglAwal.getDate())
                    + " 00:00:01' and '"
                    + sdf.format(tglAkhir.getDate())
                    + " 23:59:00' AND no_faktur_pembelian like '"
                    + "%"
                    + txtKriteria.getText()
                    + "%' "
                    + "and biaya_pembayaran =0";

            String a = "SELECT "
                    + "pembelian.no_faktur_pembelian,"
                    + "pembelian.tgl_pembelian,"
                    + "supplier.nama_supplier,"
                    + "supplier.kota_supplier,"
                    + "pembelian.id_top,"
                    + "pembelian.discon_persen,"
                    + "pembelian.discon_rp,"
                    + "pembelian.discon_2persen,"
                    + "pembelian.discon_2rp "
                    + "from pembelian, supplier "
                    + "WHERE supplier.kode_supplier = pembelian.kode_supplier "
                    + "and pembelian.tgl_pembelian BETWEEN '" + sdf.format(tglAwal.getDate()) + "' AND '" + sdf.format(tglAkhir.getDate()) + "' "
                    + "AND supplier.nama_supplier LIKE '%" + txtKriteria.getText() + "%' "
                    + "AND pembelian.biaya_pembayaran = 0";

            System.out.println("SQL " + a);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(a);
            while (res.next()) {
                model.addRow(new Object[]{
                    i++,
                    res.getString("no_faktur_pembelian"),
                    rptabelkembali(res.getString("tgl_pembelian")),
                    res.getString("nama_supplier"),
                    res.getString("kota_supplier"),
                    res.getString("id_top"),
                    res.getString("discon_persen"),
                    res.getString("discon_rp"),
                    res.getString("discon_2persen"),
                    res.getString("discon_2rp")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }

    static String rptabelkembali(String b) {
        b = b.replace(".0", "");

        return b;
    }

    void removeRow() {
        DefaultTableModel model = (DefaultTableModel) tblPembelianLaporanFaktur.getModel();
        int row = tblPembelianLaporanFaktur.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    public static String getDateTime() {
//     String date = null;
//     Date now = new Date();
//
//     SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
//     date = format.format(now);
//         System.out.println("Date -->"+date);
//     return date;
        String tanggal = "2017-11-01";
        return tanggal;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tglAkhir = new com.toedter.calendar.JDateChooser();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPembelianLaporanFaktur = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        tglAwal = new com.toedter.calendar.JDateChooser();
        txtKriteria = new javax.swing.JTextField();
        lbl_refresh = new javax.swing.JLabel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        tglAkhir.setDateFormatString(" yyyy - M - d");
        tglAkhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tglAkhirKeyPressed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.lightGray));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Laporan Faktur Pembelian");

        tblPembelianLaporanFaktur.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "No Faktur", "Tanggal", "Supplier", "Kota", "TOP", "Diskon %", "Diskon Rp", "Diskon-2 %", "Diskon-2 Rp", "Keterangan", "Title 12"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPembelianLaporanFaktur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPembelianLaporanFakturMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPembelianLaporanFaktur);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(321, 321, 321)
                .addComponent(jLabel1)
                .addContainerGap(336, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel24.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel24.setText("Tanggal");

        jLabel25.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel25.setText("s.d.");

        jLabel26.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel26.setText("Kriteria");

        tglAwal.setDateFormatString(" yyyy - M - d");

        txtKriteria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKriteriaKeyReleased(evt);
            }
        });

        lbl_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/005-refresh-3.png"))); // NOI18N
        lbl_refresh.setText("Refresh");
        lbl_refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_refreshMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(tglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(lbl_refresh))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtKriteria, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(391, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKriteria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_refresh)
                                .addComponent(tglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setSize(new java.awt.Dimension(979, 510));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblPembelianLaporanFakturMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPembelianLaporanFakturMouseClicked
        int baris = tblPembelianLaporanFaktur.getSelectedRow();
        int kolom = tblPembelianLaporanFaktur.getSelectedColumn();
        TableModel model = tblPembelianLaporanFaktur.getModel();
        int tabel = tblPembelianLaporanFaktur.getRowCount();

//        model.setValueAt(rptabelkembali(String.valueOf(harga)), i - 1, 6);
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
//            JOptionPane.showMessageDialog(null, "baris : " + baris + " kolom : " + kolom +"nilai : " +tabel);

            txttabel = model.getValueAt(baris, 1).toString();
            String temp = "2017-11-01";
            String temp1 = model.getValueAt(baris, 2).toString();
            String tanggalFix = temp1.substring(0, 10);
//            System.out.println("temp = " + temp + " tanggalfix = " + tanggalFix);
            boolean cb=true;
            if (temp.equals(tanggalFix)) {
                cb=true;
                Pembelian_Transaksi a = new Pembelian_Transaksi(txttabel,cb);
                a.setVisible(true);
            } else{
                cb=false;
                Pembelian_Transaksi a = new Pembelian_Transaksi(txttabel,cb);
                a.setVisible(true);
            }
        }
    }//GEN-LAST:event_tblPembelianLaporanFakturMouseClicked

    private void txtKriteriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKriteriaKeyReleased
        loadTable();
    }//GEN-LAST:event_txtKriteriaKeyReleased

    private void lbl_refreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_refreshMouseClicked
        loadTable();
    }//GEN-LAST:event_lbl_refreshMouseClicked

    private void tglAkhirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tglAkhirKeyPressed
        if (evt.getKeyCode() == 10) {
            lbl_refresh.requestFocus();
        }
    }//GEN-LAST:event_tglAkhirKeyPressed

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
            java.util.logging.Logger.getLogger(Pembelian_LaporanFaktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_LaporanFaktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_LaporanFaktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_LaporanFaktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Pembelian_LaporanFaktur().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl_refresh;
    private javax.swing.JTable tblPembelianLaporanFaktur;
    private com.toedter.calendar.JDateChooser tglAkhir;
    private com.toedter.calendar.JDateChooser tglAwal;
    private javax.swing.JTextField txtKriteria;
    // End of variables declaration//GEN-END:variables
}
