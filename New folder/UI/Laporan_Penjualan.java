/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Java.Connect;
import java.awt.BorderLayout;
import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Dii
 */
public class Laporan_Penjualan extends javax.swing.JFrame {

    /**
     * Creates new form laporan_Penjualan
     */
    private SimpleDateFormat formatTanggal;
    private Date tglHariIni, awal,akhir;
    
    public Laporan_Penjualan() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        tglHariIni = new Date();
        formatTanggal = new SimpleDateFormat("yyyy-MM-dd");
        tgl_awal.setDate(tglHariIni);
        tgl_akhir.setDate(tglHariIni);
        cek();
    }
    private void cek(){
        String tgl = formatTanggal.format(tglHariIni);
        String query = "select sl.kode_salesman,\n" +
                    "sl.nama_salesman,\n" +
                    "pj.no_faktur_penjualan,\n" +
                    "pj.kode_customer,\n" +
                    "c.nama_customer,\n" +
                    "pjd.kode_barang,\n" +
                    "b.nama_barang,\n" +
                    "pjd.jumlah_barang,\n" +
                    "pjd.harga_jual,\n" +
                    "pjd.hpp,\n" +
                    "bkv.kode_barang_konversi,\n" +
                    "pjd.total_harga,\n" +
                    "kv.nama_konversi,\n" +
                    "pj.tgl_penjualan\n" +
                    "from penjualan pj\n" +
                    "join penjualan_detail pjd on pjd.no_faktur_penjualan = pj.no_faktur_penjualan\n" +
                    "join customer c on c.kode_customer = pj.kode_customer\n" +
                    "join barang b on b.kode_barang=pjd.kode_barang\n" +
                    "join barang_konversi bkv on bkv.kode_barang_konversi = pjd.kode_barang_konversi\n" +
                    "join konversi kv on kv.kode_konversi=bkv.kode_konversi\n" +
                    "JOIN salesman sl on sl.kode_salesman = pj.kode_salesman\n" +
                    " Where DATE_FORMAT(pj.tgl_penjualan, '%Y-%m-%d') = \"" +tgl+"\""+
                    "order by sl.nama_salesman, c.nama_customer,pj.no_faktur_penjualan asc";
        String path_laporan = "/src/Laporan/Laporan_Penjualan_Detail_Laba_Rugi_1.jrxml";
            loadLaporan(query, null,path_laporan);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        tgl_awal = new com.toedter.calendar.JDateChooser();
        tgl_akhir = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 801, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
        );

        jCheckBox1.setText("Tanggal");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        jLabel1.setText("s.d");

        jButton1.setText("Lihat");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Detail Laba Rugi", "Rekap Setoran Salesman", "Laba Rugi Salesman", "Rekap Omzet Salesman" }));

        tgl_awal.setDateFormatString("dd-MM-yyyy");
        tgl_awal.setEnabled(false);

        tgl_akhir.setDateFormatString("dd-MM-yyyy");
        tgl_akhir.setEnabled(false);
        tgl_akhir.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tgl_akhirPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBox1)
                        .addComponent(jButton1)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tgl_akhirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tgl_akhirPropertyChange
        if ("date".equals(evt.getPropertyName()))
        {

        }
    }//GEN-LAST:event_tgl_akhirPropertyChange

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        if (jCheckBox1.isSelected()) {
            tgl_awal.setEnabled(true);
            tgl_akhir.setEnabled(true);
            System.out.println("cekbox enabled");
        }else{
            String tgl = formatTanggal.format(tglHariIni);
            tgl_awal.setEnabled(false);
            tgl_akhir.setEnabled(false);
            tgl_awal.setDate(tglHariIni);
            tgl_akhir.setDate(tglHariIni);
            String fitur = jComboBox1.getSelectedItem().toString();
            if (fitur.equals("Detail Laba Rugi")) {
                cek();
            }else if(fitur.equals("Laba Rugi Salesman")){
                String query = "select sl.kode_salesman,\n" +
                    "sl.nama_salesman,\n" +
                    "SUM(pjd.jumlah_barang*pjd.harga_jual) total_jual,\n" +
                    "SUM(pjd.jumlah_barang*pjd.hpp) total_hpp,\n" +
                    "pj.tgl_penjualan\n" +
                    "from penjualan pj\n" +
                    "join penjualan_detail pjd on pjd.no_faktur_penjualan = pj.no_faktur_penjualan\n" +
                    "join customer c on c.kode_customer = pj.kode_customer\n" +
                    "\n" +
                    "JOIN salesman sl on sl.kode_salesman = pj.kode_salesman\n"+
                    " Where DATE_FORMAT(pj.tgl_penjualan, '%Y-%m-%d') = \"" +tgl+"\""+
                    " GROUP BY sl.kode_salesman\n" +
                    "order by sl.nama_salesman, c.nama_customer,pj.no_faktur_penjualan asc";
                String path_laporan = "/src/Laporan/penjualan_sales_laba_rugi_final.jrxml";
            loadLaporan(query, null,path_laporan);
            }else if(fitur.equals("Rekap Omzet Salesman")){
                String query = "select sl.kode_salesman,\n" +
                    "sl.nama_salesman,\n" +
                    "SUM(pjd.jumlah_barang*pjd.harga_jual) total_jual,\n" +
                    "SUM(pjd.jumlah_barang*pjd.hpp) total_hpp,\n" +
                    "pj.tgl_penjualan\n" +
                    "from penjualan pj\n" +
                    "join penjualan_detail pjd on pjd.no_faktur_penjualan = pj.no_faktur_penjualan\n" +
                    "join customer c on c.kode_customer = pj.kode_customer\n" +
                    "JOIN salesman sl on sl.kode_salesman = pj.kode_salesman\n" +
                    " Where DATE_FORMAT(pj.tgl_penjualan, '%Y-%m-%d') = \"" +tgl+"\""+
                    "GROUP BY sl.kode_salesman\n" +
                    "order by sl.nama_salesman, c.nama_customer,pj.no_faktur_penjualan asc";
                String path_laporan = "/src/Laporan/penjualan_sales_omzet.jrxml";
            loadLaporan(query, null,path_laporan);
            }
        }
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Date awalDalem = tgl_awal.getDate();
        Date akhirDalem = tgl_akhir.getDate();
        HashMap params = new HashMap<String, Date>();
        String awal1Dalem = formatTanggal.format(awalDalem);
        String akhir1Dalem = formatTanggal.format(akhirDalem);
        boolean status = jCheckBox1.isSelected();
        String query = null;
        params.put("awal", awalDalem);
        params.put("akhir", akhirDalem);
        String path_laporan = null;
        String fitur = jComboBox1.getSelectedItem().toString();
            if(fitur.equals("Detail Laba Rugi")){
                System.out.println(fitur);
                query = "select sl.kode_salesman,\n" +
                    "sl.nama_salesman,\n" +
                    "pj.no_faktur_penjualan,\n" +
                    "pj.kode_customer,\n" +
                    "c.nama_customer,\n" +
                    "pjd.kode_barang,\n" +
                    "b.nama_barang,\n" +
                    "pjd.jumlah_barang,\n" +
                    "pjd.harga_jual,\n" +
                    "pjd.hpp,\n" +
                    "bkv.kode_barang_konversi,\n" +
                    "pjd.total_harga,\n" +
                    "kv.nama_konversi,\n" +
                    "pj.tgl_penjualan\n" +
                    "from penjualan pj\n" +
                    "join penjualan_detail pjd on pjd.no_faktur_penjualan = pj.no_faktur_penjualan\n" +
                    "join customer c on c.kode_customer = pj.kode_customer\n" +
                    "join barang b on b.kode_barang=pjd.kode_barang\n" +
                    "join barang_konversi bkv on bkv.kode_barang_konversi = pjd.kode_barang_konversi\n" +
                    "join konversi kv on kv.kode_konversi=bkv.kode_konversi\n" +
                    "JOIN salesman sl on sl.kode_salesman = pj.kode_salesman\n" +
                    " Where DATE_FORMAT(pj.tgl_penjualan, '%Y-%m-%d') BETWEEN \"" +awal1Dalem+"\" and \""+akhir1Dalem+"\"\n" +
                    "order by sl.nama_salesman, c.nama_customer,pj.no_faktur_penjualan asc";
         path_laporan = "/src/Laporan/Laporan_Penjualan_Detail_Laba_Rugi_1.jrxml";
            loadLaporan(query, params,path_laporan);
            }else if(fitur.equals("Laba Rugi Salesman")){
                System.out.println(fitur);
                query = "select sl.kode_salesman,\n" +
                    "sl.nama_salesman,\n" +
                    "SUM(pjd.jumlah_barang*pjd.harga_jual) total_jual,\n" +
                    "SUM(pjd.jumlah_barang*pjd.hpp) total_hpp,\n" +
                    "pj.tgl_penjualan\n" +
                    "from penjualan pj\n" +
                    "join penjualan_detail pjd on pjd.no_faktur_penjualan = pj.no_faktur_penjualan\n" +
                    "join customer c on c.kode_customer = pj.kode_customer\n" +
                    "JOIN salesman sl on sl.kode_salesman = pj.kode_salesman\n" +
                    "Where DATE_FORMAT(pj.tgl_penjualan, '%Y-%m-%d') BETWEEN \""+awal1Dalem+"\" and \""+akhir1Dalem+"\"\n" +
                    "GROUP BY sl.kode_salesman\n" +
                    "order by sl.nama_salesman, c.nama_customer,pj.no_faktur_penjualan asc";
                path_laporan = "/src/Laporan/penjualan_sales_laba_rugi_final.jrxml";
                loadLaporan(query, params,path_laporan);
            }else if(fitur.equals("Rekap Omzet Salesman")){
                System.out.println(fitur);
                query = "select sl.kode_salesman,\n" +
                    "sl.nama_salesman,\n" +
                    "SUM(pjd.jumlah_barang*pjd.harga_jual) total_jual,\n" +
                    "SUM(pjd.jumlah_barang*pjd.hpp) total_hpp,\n" +
                    "pj.tgl_penjualan\n" +
                    "from penjualan pj\n" +
                    "join penjualan_detail pjd on pjd.no_faktur_penjualan = pj.no_faktur_penjualan\n" +
                    "join customer c on c.kode_customer = pj.kode_customer\n" +
                    "JOIN salesman sl on sl.kode_salesman = pj.kode_salesman\n" +
                    "Where DATE_FORMAT(pj.tgl_penjualan, '%Y-%m-%d') BETWEEN \""+awal1Dalem+"\" and \""+akhir1Dalem+"\"\n" +
                    "GROUP BY sl.kode_salesman\n" +
                    "order by sl.nama_salesman, c.nama_customer,pj.no_faktur_penjualan asc";
                path_laporan = "/src/Laporan/penjualan_sales_omzet.jrxml";
                loadLaporan(query, params,path_laporan);
            }
            else{
                JOptionPane.showConfirmDialog(this, "anda memilih "+fitur);
            }
        
    }//GEN-LAST:event_jButton1ActionPerformed

        private void loadLaporan(String query,HashMap params, String path_laporan) {
        if(params!=null){
            try {
            Connect koneksi = new Connect();
            Connection conn = koneksi.conn;
            JasperDesign jd =JRXmlLoader.load(new File("").getAbsolutePath()+path_laporan);
            
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(query);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint print = JasperFillManager.fillReport(jr, params,conn);;
            JasperViewer viewer = new JasperViewer(print);
            System.out.println("sukses");
            jPanel2.removeAll();
            jPanel2.setLayout(new BorderLayout());
            jPanel2.repaint();
            jPanel2.add(new JRViewer(print));
////            show_report.repaint();
            jPanel2.revalidate();

        } catch (JRException ex) {
                System.out.println("error = "+ex.getMessage());
            Logger.getLogger(Laporan_Penjualan.class.getName()).log(Level.SEVERE, null, ex);
        } catch(Exception ex){
                System.out.println(ex.getMessage());
            Logger.getLogger(Laporan_Penjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else {
            try {
            Connect koneksi = new Connect();
            Connection conn = koneksi.conn;
            JasperDesign jd =JRXmlLoader.load(new File("").getAbsolutePath()+path_laporan);
            
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(query);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint print = JasperFillManager.fillReport(jr, null,conn);;
//            if (params!=null) {
//                print = JasperFillManager.fillReport(jr, params,conn);
//            }else{
//            print = JasperFillManager.fillReport(jr, null,conn);
//            }
            //JasperPrintManager.printReport(print, false);
            JasperViewer viewer = new JasperViewer(print);
            System.out.println("sukses");
            jPanel2.removeAll();
            jPanel2.setLayout(new BorderLayout());
            jPanel2.repaint();
            jPanel2.add(new JRViewer(print));
////            show_report.repaint();
            jPanel2.revalidate();

        } catch (JRException ex) {
            Logger.getLogger(Toko_Laporan.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
    }
        
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
            java.util.logging.Logger.getLogger(Laporan_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Laporan_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Laporan_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Laporan_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Laporan_Penjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private com.toedter.calendar.JDateChooser tgl_akhir;
    private com.toedter.calendar.JDateChooser tgl_awal;
    // End of variables declaration//GEN-END:variables


}
