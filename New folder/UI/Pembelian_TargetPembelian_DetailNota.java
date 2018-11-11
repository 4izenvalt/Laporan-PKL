/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Java.Connect;
import Java.ListBarangTarget;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 7
 */
public class Pembelian_TargetPembelian_DetailNota extends javax.swing.JFrame {

    private ListBarangTarget barangTarget;
    private int bulanNo,tahunNo;
    
    private Connect connection;
    private ResultSet hasil;
    /**
     * Creates new form Pembelian_TargetPembelian_DetailNota
     */
    public Pembelian_TargetPembelian_DetailNota() {
        initComponents();
    }
    public Pembelian_TargetPembelian_DetailNota(String Bulan,int tahun, ListBarangTarget barang) {
        initComponents();
        this.barangTarget = barang;
        this.connection = new Connect();
        this.bulanNo = rubahBulan(Bulan);
        this.tahunNo = tahun;        
        
        loadForm();
    }

    private void loadForm(){
        jTextField1.setText(barangTarget.getNama_barang());
        jTextField2.setText(barangTarget.getNama_supplier());
        loadSupplierData();
        loadData();
        System.out.println("=============");
    }
    private void loadSupplierData(){
        try{
        String sql = "SELECT * FROM supplier where nama_supplier = '"+barangTarget.getNama_supplier()+"'";
        ResultSet supp = connection.ambilData(sql);
        while (supp.next()) {
            txtNamaSupplier.setText(supp.getString("nama_supplier"));
            txtAlamatSupplier.setText(supp.getString("alamat_supplier"));
            txtRekeningSupplier.setText(supp.getString("rekening_supplier"));
        }
        }catch(SQLException e){
            System.out.println("ERROR -> "+e.getMessage());
        }
    }
    
    public int rubahBulan(String in_bulan){
        String[] NamaBulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        for (int i = 0; i < NamaBulan.length; i++) {
            if (in_bulan.equals(NamaBulan[i])) {
                return i+1;
            }
        }
        return -99;
        
    }
    
    private void loadData(){
        
/**        String sql = "SELECT s.nama_supplier,\n" +
            "dpp.no_faktur_pembelian,\n" +
            "b.kode_barang,\n" +
            "b.nama_barang,\n" +
            "kv.nama_konversi,\n" +
            "dpp.jumlah_barang,\n" +
            "bkv.jumlah_konversi,\n" +
            "bkv.identitas_konversi,\n" +
            "DATE_FORMAT(p.tgl_pembelian, '%m') bulan_pembelian,\n" +
            "DATE_FORMAT(p.tgl_pembelian, '%Y') tahun_pembelian\n" +
            "FROM detail_pembelian dpp\n" +
            "JOIN pembelian p on p.no_faktur_pembelian=dpp.no_faktur_pembelian\n" +
            "JOIN konversi kv on kv.kode_konversi  = dpp.kode_konversi\n" +
            "JOIN barang_konversi bkv on bkv.kode_konversi=kv.kode_konversi\n" +
            "JOIN barang b on b.kode_barang = dpp.kode_barang\n" +
            "JOIN supplier s ON s.kode_supplier = p.kode_supplier\n"+
            "WHERE b.kode_barang = "+barangTarget.getKode_barang()+
            "\nAND s.nama_supplier = '"+barangTarget.getNama_supplier()+"'\n"+
            "AND DATE_FORMAT(p.tgl_pembelian, '%m') = "+bulanNo+"\n" +
            "AND DATE_FORMAT(p.tgl_pembelian, '%Y') = "+tahunNo+"\n" +
            "Group By p.tgl_pembelian";
        **/
        String sql = "SELECT dpp.no_faktur_pembelian,\n" +
            "p.tgl_pembelian\n" +
            "FROM detail_pembelian dpp\n" +
            "JOIN pembelian p on p.no_faktur_pembelian=dpp.no_faktur_pembelian\n" +
            "JOIN konversi kv on kv.kode_konversi  = dpp.kode_konversi\n" +
            "JOIN barang_konversi bkv on bkv.kode_konversi=kv.kode_konversi\n" +
            "JOIN barang b on b.kode_barang = dpp.kode_barang\n" +
            "JOIN supplier s ON s.kode_supplier = p.kode_supplier\n"+
            "WHERE b.kode_barang = "+barangTarget.getKode_barang()+
            "\nAND s.nama_supplier = '"+barangTarget.getNama_supplier()+"'\n"+
            "AND DATE_FORMAT(p.tgl_pembelian, '%m') = "+bulanNo+"\n" +
            "AND DATE_FORMAT(p.tgl_pembelian, '%Y') = "+tahunNo+"\n" +
            "Group By p.tgl_pembelian";
        System.out.println("Ini sql dari load data\n"+sql);
        hasil = connection.ambilData(sql);
        loadTabel();
    }
    private void loadTabel(){
        try{
            removeRow();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            while(hasil.next()){
                model.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2)
                
            });
            }
        }catch(SQLException e){
            System.out.println("ERROR -> "+e.getMessage());
        }
    }
    public void removeRow() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getRowCount();
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

        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtNamaSupplier = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtAlamatSupplier = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtRekeningSupplier = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("Supplier");

        jLabel1.setText("Barang");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "No Faktur", "Tanggal"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        txtNamaSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNamaSupplierMouseClicked(evt);
            }
        });
        txtNamaSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaSupplierKeyPressed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(204, 0, 0));
        jLabel31.setText("Nama");

        txtAlamatSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAlamatSupplierMouseClicked(evt);
            }
        });
        txtAlamatSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAlamatSupplierKeyPressed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(204, 0, 0));
        jLabel33.setText("Alamat");

        txtRekeningSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtRekeningSupplierMouseClicked(evt);
            }
        });
        txtRekeningSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRekeningSupplierKeyPressed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(204, 0, 0));
        jLabel34.setText("Rekening");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Detail Transaksi ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel33))
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAlamatSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtRekeningSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(47, 47, 47))))
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAlamatSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRekeningSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNamaSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNamaSupplierMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaSupplierMouseClicked

    private void txtNamaSupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaSupplierKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaSupplierKeyPressed

    private void txtAlamatSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAlamatSupplierMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlamatSupplierMouseClicked

    private void txtAlamatSupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlamatSupplierKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlamatSupplierKeyPressed

    private void txtRekeningSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRekeningSupplierMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRekeningSupplierMouseClicked

    private void txtRekeningSupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRekeningSupplierKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRekeningSupplierKeyPressed

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
            java.util.logging.Logger.getLogger(Pembelian_TargetPembelian_DetailNota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_TargetPembelian_DetailNota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_TargetPembelian_DetailNota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_TargetPembelian_DetailNota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pembelian_TargetPembelian_DetailNota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField txtAlamatSupplier;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtRekeningSupplier;
    // End of variables declaration//GEN-END:variables
}
