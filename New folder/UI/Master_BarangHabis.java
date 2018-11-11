package UI;

import Class.Koneksi;
import Java.*;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;


public class Master_BarangHabis extends javax.swing.JDialog {

    private ResultSet hasil1, hasilLokasi;
    private Connect connection;
    private PreparedStatement PS;
    private ArrayList<ListBarang> list;
    private myArrlist myarrlist;
    private ListBarang listBarang;
    private TrBarang TrBarang;
    private TableModel model;
    private MouseAdapter MA;
    private String comboBox;
   
    public Master_BarangHabis() {
        initComponents();
//        this.setLocationRelativeTo(null);
      
    }

    public Master_BarangHabis(java.awt.Frame parent, boolean modal, Connect connection) {
        super(parent, modal);
        initComponents();
//        prep
        this.connection = connection;
        loadComkelompok();
        tampilTabel("","");

    }

     void tampilTabel(String param, String kriteria) {
         
//         if (param.equals("0")) {
//             para
//         }
        String data = "";
        try {
            String sql = "SELECT b.kode_barang, b.nama_barang, k.nama_konversi, b.harga_beli, "
                    + "b.harga_rata_rata_barang, b.status_barang "
                    + "FROM barang b, barang_konversi bk, konversi k, kelompok kl "
                    + "where b.kode_barang=bk.kode_barang "
                    + " and bk.kode_konversi=k.kode_konversi "
                    + "and b.id_kelompok = kl.id_kelompok "
                    + " and bk.identitas_konversi=1 "
                    + "and kl.nama_kelompok like '%"+param+"%' "
                    + "and b.nama_barang like '%"+kriteria+"%'"
//                    + (param.equals("-1") ? "" : (param.equals("1") || param.equals("0") ? "and b.status_barang=" 
//                    + Integer.parseInt(param) : "and b.nama_barang like '%" + param + "%' ")
                    + " limit 100";
            System.out.println("sql: "+sql);
            hasil1 = connection.ambilData(sql);
            setModel(hasil1);
            System.out.println("berhasil tampil");
            TableColumnModel m = jTable12.getColumnModel();
            m.getColumn(8).setCellRenderer(new Currency_Column());
            m.getColumn(9).setCellRenderer(new Currency_Column());
            m.getColumn(10).setCellRenderer(new Currency_Column());
         } catch (Exception e) {
            e.printStackTrace();
           
              System.out.println("ERROR -> " + e.getMessage());
        } } 
     
     private void setModel(ResultSet hasil) {
        try {
            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            list = new ArrayList<>();
            while (hasil.next()) {
                
                this.listBarang = new ListBarang();
                String sqlLokasi = "SELECT * from barang_lokasi where kode_barang='"
                                +hasil.getInt("kode_barang")+"'";  
//                System.out.println("sqllokasi: "+sqlLokasi);
            //lokasi 
                hasilLokasi = stm.executeQuery(sqlLokasi); 
//                hasilstatus = stm.executeQuery(sqlStatus); 
                while(hasilLokasi.next()){
                    if (hasilLokasi.getInt("kode_lokasi")==1) {
                        this.listBarang.setPusat(hasilLokasi.getInt("jumlah"));
                    }else if (hasilLokasi.getInt("kode_lokasi")==2) {
                        this.listBarang.setGUD63(hasilLokasi.getInt("jumlah"));
                    }else if (hasilLokasi.getInt("kode_lokasi")==4) {
                        this.listBarang.setTOKO(hasilLokasi.getInt("jumlah"));
                    }else if (hasilLokasi.getInt("kode_lokasi")==5) {
                        this.listBarang.setTENGAH(hasilLokasi.getInt("jumlah"));
                    }else {
                        this.listBarang.setUTARA(hasilLokasi.getInt("jumlah"));
                    }
                }
                    if (hasil.getInt("status_barang")==0){
                        this.listBarang.setStatus("Tidak aktif");
                    }else if (hasil.getInt("status_barang")==1){
                        this.listBarang.setStatus("aktif");
                    }   
                this.listBarang.setKode_barang(hasil.getInt("kode_barang"));
                this.listBarang.setNama_barang(hasil.getString("nama_barang"));
                this.listBarang.setSatuan(hasil.getString("nama_konversi"));
                this.listBarang.setHarga_beli(hasil.getDouble("harga_beli"));
                this.listBarang.setHarga_rata_rata_barang(hasil.getDouble("harga_rata_rata_barang"));
//               
                list.add(listBarang);
                listBarang = null;
            }
            model = new modelTabelBarang1(list);
            jTable12.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     
     
     void loadComkelompok() {
        try {
            String sql = "select * from kelompok";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            System.out.println("kel: "+sql);
            jComboBox27.removeAllItems();
            while (res.next()) {
                String name = res.getString(3);
               jComboBox27.addItem(name);
//                if (param.equals(res.getString("id_kelompok"))) {
//                    jComboBox27.setSelectedItem(name);
//                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror" + e);
        } }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jTextField22 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jComboBox27 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTable12 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jTextField22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField22KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField22KeyTyped(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setToolTipText("");

        jComboBox27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox27ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel25.setText("Kriteria");

        jTable12.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama", "Pusat", "Gud63", "Toko", "Tengah", "Utara", "Satuan", "Harga Beli", "Harga Rata2", "Status"
            }
        ));
        jTable12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable12MouseClicked(evt);
            }
        });
        jScrollPane17.setViewportView(jTable12);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("DAFTAR BARANG HABIS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox27, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(340, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(329, 329, 329))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane17)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(975, 544));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox27ActionPerformed
//        comboBox = jComboBox27.getSelectedItem().toString();
//        switch (jComboBox27.getSelectedIndex()) {
//            case 0:
//                System.out.println("jc1");
//                tampilTabel("","");
//                break;
//            case 1:
//                System.out.println("jc0");
//                tampilTabel("","");
//                break;
//            default:
//                System.out.println("jc-1");
//                tampilTabel("","");
//                break;
// }        
tampilTabel(jComboBox27.getSelectedItem().toString(),jTextField22.getText());
    }//GEN-LAST:event_jComboBox27ActionPerformed

    private void jTable12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable12MouseClicked
      this.listBarang = list.get(jTable12.getSelectedRow());
//Master_Barang_HistoryStok bhs=new Master_Barang_HistoryStok();
//        bhs.setVisible(true);
//        bhs.setFocusable(true);
    }//GEN-LAST:event_jTable12MouseClicked

    private void jTextField22KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField22KeyReleased
         tampilTabel(jComboBox27.getSelectedItem().toString(),jTextField22.getText());
    }//GEN-LAST:event_jTextField22KeyReleased

    private void jTextField22KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField22KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField22KeyTyped

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
            java.util.logging.Logger.getLogger(Master_BarangHabis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_BarangHabis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_BarangHabis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_BarangHabis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master_BarangHabis().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox27;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable12;
    private javax.swing.JTextField jTextField22;
    // End of variables declaration//GEN-END:variables
}
