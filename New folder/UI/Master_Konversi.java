
package UI;

import Java.koneksi;
import Java.modelTabelKonversi;
import com.mysql.jdbc.Connection;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import java.util.GregorianCalendar;
import java.util.Calendar;


public class Master_Konversi extends javax.swing.JFrame {

    private Java.koneksi koneksi;
    private Connection con;
    private ResultSet hasil;
    private Statement stat;
    private Java.ListKonversi listKonversi;
    private List<Java.ListKonversi> list; 
    private TableModel model;
    private String query;
    private int id;
    private String kode_konversi;
    private String nama_konversi;
    
    
    public Master_Konversi() {
        initComponents();
        this.setLocationRelativeTo(null);
        kalender();
        tampilTabel();
        fCari.requestFocusInWindow();
        dTambahUpdateKonversi.setLocationRelativeTo(null);
    }
    
      public void kalender() {
        Thread p = new Thread() {
            @Override
            public void run() {
                for (;;) {
                    GregorianCalendar cal = new GregorianCalendar();
                    int hari = cal.get(Calendar.DAY_OF_MONTH);
                    int bulan = cal.get(Calendar.MONTH);
                    int tahun = cal.get(Calendar.YEAR);
                    int jam = cal.get(Calendar.HOUR);
                    int menit = cal.get(Calendar.MINUTE);
                    jLabel28.setText(hari + " - " + (bulan + 1) + " - " + tahun + " | " + jam + ":" + menit);
                }
            }
        };
        p.start();
    }
    
     public String tampilTabel(){
        String data = "";
        koneksi = new koneksi();
        con = (Connection) koneksi.Connect();
        try{
          data ="select *  from konversi";
          hasil = koneksi.ambilData(data);
          System.out.println("sukses query tampil tabel");
          setModel(hasil);
        }catch(Exception e){
            System.out.println("Error tampil tabel");
        }
        return data;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dTambahUpdateKonversi = new javax.swing.JDialog();
        fNama = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        BtnBatal = new javax.swing.JButton();
        BtnSimpan = new javax.swing.JButton();
        jKonversi = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        btnTambahKonversi = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        fTabel = new javax.swing.JTable();
        fCari = new javax.swing.JTextField();
        btnEdit = new javax.swing.JButton();

        dTambahUpdateKonversi.setTitle("Tambah/Update Akun");
        dTambahUpdateKonversi.setBackground(new java.awt.Color(255, 255, 255));
        dTambahUpdateKonversi.setResizable(false);
        dTambahUpdateKonversi.setSize(new java.awt.Dimension(359, 131));

        jLabel34.setText("Nama");

        BtnBatal.setText("Batal");
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });

        BtnSimpan.setText("Simpan");
        BtnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnSimpanMouseClicked(evt);
            }
        });
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout dTambahUpdateKonversiLayout = new javax.swing.GroupLayout(dTambahUpdateKonversi.getContentPane());
        dTambahUpdateKonversi.getContentPane().setLayout(dTambahUpdateKonversiLayout);
        dTambahUpdateKonversiLayout.setHorizontalGroup(
            dTambahUpdateKonversiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dTambahUpdateKonversiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dTambahUpdateKonversiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dTambahUpdateKonversiLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(120, 120, 120))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dTambahUpdateKonversiLayout.createSequentialGroup()
                        .addComponent(BtnSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(dTambahUpdateKonversiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dTambahUpdateKonversiLayout.createSequentialGroup()
                        .addComponent(BtnBatal)
                        .addGap(0, 135, Short.MAX_VALUE))
                    .addComponent(fNama))
                .addContainerGap())
        );
        dTambahUpdateKonversiLayout.setVerticalGroup(
            dTambahUpdateKonversiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dTambahUpdateKonversiLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(dTambahUpdateKonversiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(dTambahUpdateKonversiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnBatal)
                    .addComponent(BtnSimpan))
                .addGap(21, 21, 21))
        );

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jKonversi.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel24.setBackground(new java.awt.Color(217, 237, 247));
        jLabel24.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel24.setText("Konversi Form");

        jLabel28.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Tanggal | Waktu");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnTambahKonversi.setText("Tambah Konversi");
        btnTambahKonversi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKonversiActionPerformed(evt);
            }
        });

        fTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "No", "Nama"
            }
        ));
        fTabel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fTabelKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(fTabel);

        fCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fCariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                fCariFocusLost(evt);
            }
        });
        fCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fCariKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fCariKeyReleased(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jKonversiLayout = new javax.swing.GroupLayout(jKonversi);
        jKonversi.setLayout(jKonversiLayout);
        jKonversiLayout.setHorizontalGroup(
            jKonversiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jKonversiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jKonversiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jKonversiLayout.createSequentialGroup()
                        .addComponent(btnTambahKonversi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fCari, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7))
                .addContainerGap())
        );
        jKonversiLayout.setVerticalGroup(
            jKonversiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jKonversiLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jKonversiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahKonversi)
                    .addComponent(fCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jKonversi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jKonversi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahKonversiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKonversiActionPerformed
        dTambahUpdateKonversi.show();
        dTambahUpdateKonversi.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnTambahKonversiActionPerformed

    private void fCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fCariFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fCariFocusGained

    private void fCariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fCariFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fCariFocusLost

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
       dTambahUpdateKonversi.dispose();
       bersih();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
         simpanAtauEdit();
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        dTambahUpdateKonversi.show();
        dTambahUpdateKonversi.setLocationRelativeTo(null);
        ambilDataTabel();
        fNama.setText(nama_konversi);
    }//GEN-LAST:event_btnEditActionPerformed

    private void fCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fCariKeyReleased
         try{
        query= "SELECT * FROM konversi where kode_konversi like '%"+fCari.getText()+"%' or nama_konversi like '%"+fCari.getText()+"%'";
        hasil = koneksi.ambilData(query);
        setModel(hasil);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {     
            fTabel.requestFocusInWindow();
        }
        }catch (Exception e) {
        }
    }//GEN-LAST:event_fCariKeyReleased

    private void fCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fCariKeyPressed
        fCari.requestFocusInWindow();
    }//GEN-LAST:event_fCariKeyPressed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {     
           simpanAtauEdit();
       }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void fTabelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fTabelKeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {  
          ambilDataTabel();
          dTambahUpdateKonversi.setVisible(true);
          fNama.setText(nama_konversi);
          
       }
    }//GEN-LAST:event_fTabelKeyPressed

    private void BtnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnSimpanMouseClicked
        simpanAtauEdit();
    }//GEN-LAST:event_BtnSimpanMouseClicked

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
            java.util.logging.Logger.getLogger(Master_Konversi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Konversi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Konversi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Konversi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new Master_Konversi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBatal;
    private javax.swing.JButton BtnSimpan;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnTambahKonversi;
    private javax.swing.JDialog dTambahUpdateKonversi;
    private javax.swing.JTextField fCari;
    private javax.swing.JTextField fNama;
    private javax.swing.JTable fTabel;
    private javax.swing.JPanel jKonversi;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane7;
    // End of variables declaration//GEN-END:variables
 private void setModel(ResultSet hasil){
        try {
            list = new ArrayList<Java.ListKonversi>();
            while(hasil.next()){
                listKonversi = new Java.ListKonversi();
                listKonversi.setKode_konversi(hasil.getInt("kode_konversi"));
                listKonversi.setNama_konversi(hasil.getString("nama_konversi"));
                list.add(listKonversi);
            }
            model = new modelTabelKonversi(list);
            fTabel.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
 
   private void simpanAtauEdit(){
        
        if(id > 0){
            edit();
        }else{
            String nama = this.fNama.getText();
            int angka = 0;

            try{
                query= "SELECT kode_konversi FROM konversi ORDER BY kode_konversi DESC LIMIT 1";
                hasil = koneksi.ambilData(query);
                while(hasil.next()){
                    angka = hasil.getInt("kode_konversi");
                    System.out.println(angka);

                    id = angka + 1;
                    System.out.println("id"+id);
                    String sql= "Insert into konversi values ('"+id+"','"+nama+"')";
                    System.out.println(sql);
                    koneksi.simpanData(sql);

                    JOptionPane.showMessageDialog(null,"Data sukses di input");
                    dTambahUpdateKonversi.dispose();

                    tampilTabel();
                    bersih();
                }

            }catch (SQLException e){
                System.out.println();
            }
        }    
    }
    
    public void edit(){
        try {
           String sql= "Update konversi set nama_konversi=? where kode_konversi=?" ;
            PreparedStatement p = (PreparedStatement) koneksi.Connect().prepareStatement(sql);
                p.setString(1, fNama.getText().toString());
                p.setInt(2, id);
                p.executeUpdate();

                dTambahUpdateKonversi.dispose();
                tampilTabel();
                System.out.print(p);
                 JOptionPane.showMessageDialog(null,"Data sukses di edit");
                 bersih();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void ambilDataTabel(){
        int row= fTabel.getSelectedRow();
        id  = ((Integer)fTabel.getValueAt(row, 0));
        nama_konversi = ((String)fTabel.getValueAt(row, 1));
    }
    
    public void bersih(){
        id = 0;
        fNama.setText("");
    }
}
