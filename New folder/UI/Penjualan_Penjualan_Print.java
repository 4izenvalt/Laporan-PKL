/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Java.Connect;
import com.sun.glass.events.KeyEvent;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author USER
 */
public class Penjualan_Penjualan_Print extends javax.swing.JFrame {

    /**
     * Creates new form Penjualan_KotakHistoriBarang
     */
    private String no_order;
    public Penjualan_Penjualan_Print() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    public Penjualan_Penjualan_Print(String no_order) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.no_order = no_order;
    }

    void cetak_nota_toko(){
        String query ="select od.no_faktur_order, c.kode_customer, o.tgl_order, c.nama_customer, c.alamat_customer, c.telepon_customer, b.nama_barang, concat(od.jumlah_barang,' ', k.nama_konversi)as qty, format(od.harga_pengajuan,2) as harga, format(od.harga_pengajuan*od.jumlah_barang,2) as jumlah, s.nama_salesman from customer c join `order` o on o.kode_customer = c.kode_customer join salesman s on s.kode_salesman = o.kode_pegawai join order_detail od on od.no_faktur_order = o.no_faktur_order join barang b on od.kode_barang = b.kode_barang join barang_konversi bk on bk.kode_barang_konversi = od.kode_barang_konversi join konversi k on k.kode_konversi = bk.kode_konversi where od.no_faktur_order = '"+this.no_order+"' and od.harga_revisi = 0 UNION ALL select od.no_faktur_order, c.kode_customer, o.tgl_order, c.nama_customer, c.alamat_customer, c.telepon_customer, b.nama_barang, concat(od.jumlah_barang,' ', k.nama_konversi)as qty, format(od.harga_revisi,2) as harga, format(od.harga_revisi*od.jumlah_barang,2) as jumlah, s.nama_salesman from customer c join `order` o on o.kode_customer = c.kode_customer join salesman s on s.kode_salesman = o.kode_pegawai join order_detail od on od.no_faktur_order = o.no_faktur_order join barang b on od.kode_barang = b.kode_barang join barang_konversi bk on bk.kode_barang_konversi = od.kode_barang_konversi join konversi k on k.kode_konversi = bk.kode_konversi where od.no_faktur_order = '"+this.no_order+"' and od.harga_revisi != 0";
        System.out.println(query);
        String Path_Laporan = "/src/tanda bukti/nota_order.jrxml";
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream(Path_Laporan);
            Connect koneksi = new Connect();
            Connection conn = koneksi.conn;
            JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath() + Path_Laporan);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(query);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint print = JasperFillManager.fillReport(jr, null, conn);
            JasperPrintManager.printReport(print, true);


        } catch (JRException ex) {
            Logger.getLogger(Toko_Laporan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void cetak_nota_gudang(){
        String query = "select od.no_faktur_order, c.kode_customer, o.tgl_order, c.nama_customer, c.alamat_customer, c.telepon_customer, b.nama_barang, concat(od.jumlah_barang,' ', k.nama_konversi)as qty, coalesce(@harga,0) as harga, coalesce(@harga,0) as jumlah, s.nama_salesman from customer c join `order` o on o.kode_customer = c.kode_customer join salesman s on s.kode_salesman = o.kode_pegawai join order_detail od on od.no_faktur_order = o.no_faktur_order join barang b on od.kode_barang = b.kode_barang join barang_konversi bk on bk.kode_barang_konversi = od.kode_barang_konversi join konversi k on k.kode_konversi = bk.kode_konversi where od.no_faktur_order = '"+this.no_order+"' and od.harga_revisi = 0 UNION ALL select od.no_faktur_order, c.kode_customer, o.tgl_order, c.nama_customer, c.alamat_customer, c.telepon_customer, b.nama_barang, concat(od.jumlah_barang,' ', k.nama_konversi)as qty, coalesce(@harga,0) as harga, coalesce(@harga,0) as jumlah, s.nama_salesman from customer c join `order` o on o.kode_customer = c.kode_customer join salesman s on s.kode_salesman = o.kode_pegawai join order_detail od on od.no_faktur_order = o.no_faktur_order join barang b on od.kode_barang = b.kode_barang join barang_konversi bk on bk.kode_barang_konversi = od.kode_barang_konversi join konversi k on k.kode_konversi = bk.kode_konversi where od.no_faktur_order = '"+this.no_order+"' and od.harga_revisi != 0";
        System.out.println(query);
        String Path_Laporan = "/src/tanda bukti/nota_order.jrxml";
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream(Path_Laporan);
            Connect koneksi = new Connect();
            Connection conn = koneksi.conn;
            JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath() + Path_Laporan);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(query);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint print = JasperFillManager.fillReport(jr, null, conn);
            JasperPrintManager.printReport(print, true);


        } catch (JRException ex) {
            Logger.getLogger(Toko_Laporan.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cbx_pilihan = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(312, 201));
        setUndecorated(true);

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.lightGray, null, null));
        jPanel3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel3KeyPressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Tujuan");

        cbx_pilihan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbx_pilihan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nota Toko", "Nota Gudang" }));
        cbx_pilihan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_pilihanItemStateChanged(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_yast_printer_30297.png"))); // NOI18N
        jButton1.setText("OK");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N
        jButton2.setText("Cancel");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(21, 21, 21)
                        .addComponent(cbx_pilihan, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel16))
                    .addComponent(cbx_pilihan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.lightGray, null, null));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Cetak Bon");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/multiply.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
       this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        String pilihan = cbx_pilihan.getSelectedItem().toString();
        if(pilihan.equals("Nota Toko")){
            cetak_nota_toko();
        }else if (pilihan.equals("Nota Gudang")){
            cetak_nota_gudang();
        }
        
    }//GEN-LAST:event_jButton1MouseClicked

    private void cbx_pilihanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_pilihanItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_pilihanItemStateChanged

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        this.dispose();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jPanel3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel3KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_jPanel3KeyPressed

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
            java.util.logging.Logger.getLogger(Penjualan_Penjualan_Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Penjualan_Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Penjualan_Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Penjualan_Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan_Penjualan_Print().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbx_pilihan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
