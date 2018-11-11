/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Java.Connect;
import Java.ListBarangTarget;
import Java.ListDetailTargetPembelian;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author 7
 */
public class Pembelian_TargetPembelian_DetailPembelian extends javax.swing.JFrame {

    private ListBarangTarget barangTarget;
    private ListDetailTargetPembelian detailBarangTarget;
    private ArrayList<ListDetailTargetPembelian> list,temp;
    
    private ResultSet hasil;
    private Connect connection;
    /**
     * Creates new form Pembelian_TargetPembelian_DetailPembelian
     */
    public Pembelian_TargetPembelian_DetailPembelian() {
        initComponents();
    }

    public Pembelian_TargetPembelian_DetailPembelian(ListBarangTarget barangTarget) {
        initComponents();
        connection = new Connect();
        this.barangTarget = barangTarget;
        
        loadForm();
        
        System.out.println(this.barangTarget.getKode_barang_target());
    }
    
    private void loadForm(){
        jTextField1.setText(barangTarget.getNama_barang());
        jTextField2.setText(barangTarget.getNama_supplier());
        loadSupplierData();
        loadData();
        hitungTabel();
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
    private void loadData(){
        int kodeBarang = barangTarget.getKode_barang();
        String namaSupplier = barangTarget.getNama_supplier();
        String sql = "SELECT s.nama_supplier,\n" +
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
            "WHERE b.kode_barang = "+kodeBarang+
            "\nAND s.nama_supplier = '"+namaSupplier+"'\n"+
            "Group By p.tgl_pembelian";
        System.out.println("Ini sql dari load data\n"+sql);
        hasil = connection.ambilData(sql);
        int a =1;
        int jumlahBarang = -99;
        int jumKonversi = -99;
        int idenKonversi = -99;
        String namaKonversi = "";
        try{
            list = new ArrayList<>();
            while (hasil.next()) {
                this.detailBarangTarget = new ListDetailTargetPembelian();
                this.detailBarangTarget.setNo(a++);
                this.detailBarangTarget.setNoFakturPembelian(hasil.getString("no_faktur_pembelian"));
                this.detailBarangTarget.setNamaBarang(hasil.getString("nama_barang"));
                namaKonversi = hasil.getString("nama_konversi");
                jumlahBarang = hasil.getInt("jumlah_barang");
                
                if (!namaKonversi.equals(barangTarget.getNama_konversi())) {
                    System.out.println(namaKonversi+" berbeda "+barangTarget.getNama_konversi());
                    idenKonversi = hasil.getInt("identitas_konversi");
                    jumKonversi = hasil.getInt("jumlah_konversi");
                    setKonversiUbah(jumlahBarang, jumKonversi, idenKonversi);
                }else{
                    this.detailBarangTarget.setJumlah(jumlahBarang);
                }
                this.detailBarangTarget.setNamaKonversi(barangTarget.getNama_konversi());
                this.detailBarangTarget.setBulanBeli(Integer.parseInt(hasil.getString("bulan_pembelian")));
                this.detailBarangTarget.setTahunBeli(hasil.getString("tahun_pembelian"));
                list.add(detailBarangTarget);
                detailBarangTarget = null;
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
    private void hitungTabel(){
        removeRow();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        System.out.println("hitung");
        for(int i =0; i<list.size();i++){
            int jumm =0;
            temp = list;
            System.out.print(temp.get(i).getNo()+"\t");
            System.out.print(temp.get(i).getNoFakturPembelian()+"\t");
            System.out.print(temp.get(i).getNamaBarang()+"\t");
            System.out.print(temp.get(i).getNamaKonversi()+"\t");
            int jumll = temp.get(i).getJumlah();
            int totalCoba = jumll;
            System.out.print(jumll+"\t");
            int bulan_beli =temp.get(i).getBulanBeli(); 
            System.out.print(bulan_beli+"\t");
            String tahun_beli =temp.get(i).getTahunBeli(); 
            System.out.println(tahun_beli+"\t");
            for (int j = i; j < temp.size(); j++) {
                int bulan_beli_next = temp.get(j).getBulanBeli();
                String tahun_beli_next =temp.get(j).getTahunBeli(); 
                int juml2 = temp.get(j).getJumlah();
                if (bulan_beli == bulan_beli_next && tahun_beli.equals(tahun_beli_next)) {
                    jumm++;
                    temp.remove(j);
                    System.out.print(totalCoba+" + "+juml2);
                    totalCoba = totalCoba+juml2;
                    System.out.println(" = "+totalCoba);
                    j--;
                }
            }System.out.println(jumm);
            model.addRow(new Object[]{
                rubahBulan(bulan_beli),
                tahun_beli,
                totalCoba,
                barangTarget.getJumlah()
                
            });
            System.out.println("Total Coba = "+totalCoba);
        }
        
    }
    
    private void setKonversiUbah(int jumBarang, int jumKonversi, int IdenKonversi){
        int fix = -99;
        System.out.println("Identitas konversi = "+IdenKonversi);
        System.out.println("Jumlah Konversi = "+jumKonversi);
        System.out.println("Jumlah Barang = "+jumBarang);
        if (IdenKonversi>barangTarget.getIdentitas_konversi()) {
            fix = jumBarang * jumKonversi;
        }else{
            fix = (int)Math.floor(jumBarang/jumKonversi);
        }
        this.detailBarangTarget.setJumlah(fix);
    }
    
    private String rubahBulan(int bulan){
        String[] NamaBulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        return NamaBulan[bulan-1];
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
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtNamaSupplier = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtAlamatSupplier = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtRekeningSupplier = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Barang");

        jLabel2.setText("Supplier");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Bulan", "Tahun", "Total", "Target"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
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
        jLabel3.setText("Detail Target");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(47, 47, 47)
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
                                .addComponent(txtRekeningSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        TableModel tabelModel = jTable1.getModel();
        String Bulan = tabelModel.getValueAt(jTable1.getSelectedRow(), 0).toString();
        int Tahun = Integer.parseInt(tabelModel.getValueAt(jTable1.getSelectedRow(), 1).toString());
        System.out.println("setelah klik = "+Bulan);
        Pembelian_TargetPembelian_DetailNota dn=new Pembelian_TargetPembelian_DetailNota(Bulan,Tahun, barangTarget);
        dn.setVisible(true);
        dn.setLocationRelativeTo(null);
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(Pembelian_TargetPembelian_DetailPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_TargetPembelian_DetailPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_TargetPembelian_DetailPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_TargetPembelian_DetailPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pembelian_TargetPembelian_DetailPembelian().setVisible(true);
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
