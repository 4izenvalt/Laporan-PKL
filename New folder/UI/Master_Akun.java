//note : status belum ditampilkan karena di database tidak ada kolom status
package UI;

import Java.ListAkun;
import Java.koneksi;
import Java.modelTabelAkun;
import Java.modelTabelKelompok;
import static UI.Master_Supplier.nilai_status;
import com.mysql.jdbc.Connection;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 *
 * @author Dii
 */
public class Master_Akun extends javax.swing.JFrame {

    private Java.koneksi koneksi;
    private Connection con;
    private ResultSet hasil;
    private Statement stat;
    private Java.ListAkun listAkun;
    private List<Java.ListAkun> list; 
    private TableModel model;
    private String query;
    private int edithapus=0, nilai_status_akun;
    private int id_akun, status,id,kode_akun;
    private String nama_akun;
    public Master_Akun() {
        initComponents();
        this.setLocationRelativeTo(null);
        tampilTabel();
        fkode.hide();
        fCari.requestFocusInWindow();
        dTambahUpdateAkun.setLocationRelativeTo(null);
    }
        public String tampilTabel(){
            String data = "";
            koneksi = new koneksi();
            con = (Connection) koneksi.Connect();
            try{
              data ="select *  from transaksi_akun";
              hasil = koneksi.ambilData(data);
              System.out.println("sukses query tampil tabel");
              setModel(hasil);
            }catch(Exception e){
                System.out.println("Error tampil tabel");
            }
            return data;
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dTambahUpdateAkun = new javax.swing.JDialog();
        fNama = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        btnBatal2 = new javax.swing.JButton();
        fSimpan = new javax.swing.JButton();
        fpemasukan = new javax.swing.JRadioButton();
        fpengeluaran = new javax.swing.JRadioButton();
        fkode = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        btnTambahAkun = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        fTabel = new javax.swing.JTable();
        fCari = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();

        dTambahUpdateAkun.setTitle("Tambah/Update Akun");
        dTambahUpdateAkun.setBackground(new java.awt.Color(255, 255, 255));
        dTambahUpdateAkun.setMinimumSize(new java.awt.Dimension(264, 165));
        dTambahUpdateAkun.setPreferredSize(new java.awt.Dimension(264, 165));
        dTambahUpdateAkun.setSize(new java.awt.Dimension(264, 165));

        jLabel32.setText("Nama");

        btnBatal2.setText("Batal");
        btnBatal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatal2ActionPerformed(evt);
            }
        });

        fSimpan.setText("Simpan");
        fSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fSimpanActionPerformed(evt);
            }
        });

        fpemasukan.setBackground(new java.awt.Color(255, 153, 0));
        buttonGroup1.add(fpemasukan);
        fpemasukan.setText("Pemasukan ");

        fpengeluaran.setBackground(new java.awt.Color(255, 153, 0));
        buttonGroup1.add(fpengeluaran);
        fpengeluaran.setText("Pengeluaran");

        javax.swing.GroupLayout dTambahUpdateAkunLayout = new javax.swing.GroupLayout(dTambahUpdateAkun.getContentPane());
        dTambahUpdateAkun.getContentPane().setLayout(dTambahUpdateAkunLayout);
        dTambahUpdateAkunLayout.setHorizontalGroup(
            dTambahUpdateAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dTambahUpdateAkunLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fkode, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(140, 140, 140))
            .addGroup(dTambahUpdateAkunLayout.createSequentialGroup()
                .addGroup(dTambahUpdateAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dTambahUpdateAkunLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel32)
                        .addGap(29, 29, 29)
                        .addComponent(fNama, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dTambahUpdateAkunLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(fSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal2))
                    .addGroup(dTambahUpdateAkunLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(fpemasukan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fpengeluaran)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dTambahUpdateAkunLayout.setVerticalGroup(
            dTambahUpdateAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dTambahUpdateAkunLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(fkode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dTambahUpdateAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addGap(18, 18, 18)
                .addGroup(dTambahUpdateAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fpemasukan)
                    .addComponent(fpengeluaran))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dTambahUpdateAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatal2)
                    .addComponent(fSimpan))
                .addGap(21, 21, 21))
        );

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnTambahAkun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_manilla-folder-new_23456.png"))); // NOI18N
        btnTambahAkun.setText("Tambah Akun");
        btnTambahAkun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahAkunActionPerformed(evt);
            }
        });

        fTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "No", "Nama", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(fTabel);

        fCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fCariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                fCariFocusLost(evt);
            }
        });
        fCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fCariActionPerformed(evt);
            }
        });
        fCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fCariKeyReleased(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_gtk-edit_20500.png"))); // NOI18N
        jButton2.setText("Edit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel35.setText("Akun");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTambahAkun)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                        .addComponent(fCari, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane8))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahAkun)
                    .addComponent(fCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        setSize(new java.awt.Dimension(523, 339));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahAkunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahAkunActionPerformed
        dTambahUpdateAkun.show();
        dTambahUpdateAkun.setLocationRelativeTo(null);
        edithapus=1;
    }//GEN-LAST:event_btnTambahAkunActionPerformed

    private void fCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fCariFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fCariFocusGained

    private void fCariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fCariFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_fCariFocusLost

    private void btnBatal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatal2ActionPerformed
            dTambahUpdateAkun.dispose();
    }//GEN-LAST:event_btnBatal2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
  if (fTabel.getSelectedRow()<0) {
            JOptionPane.showMessageDialog(null,"Pilih Akun yang akan diedit");
        } else {        dTambahUpdateAkun.show();
        dTambahUpdateAkun.setLocationRelativeTo(null);
        ambilDataTabel();
        fkode.setText(Integer.toString(kode_akun));
        fNama.setText(nama_akun);
        edithapus=0;
        query = "SELECT status_debit_kredit FROM transaksi_akun where id_transaksi_akun='"+kode_akun+"'";
        System.out.println("query"+query);
        hasil = koneksi.ambilData(query);
        try {
            while (hasil.next()) {
                nilai_status = hasil.getInt("status_debit_kredit");
                System.out.println("nilai status"+nilai_status);
                if (nilai_status == 0) {
                    fpemasukan.setSelected(true);
                    
                } else {
                    fpengeluaran.setSelected(true);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Master_Akun.class.getName()).log(Level.SEVERE, null, ex);
        }
  
  }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void fCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fCariKeyReleased
        try{
        query= "SELECT * FROM transaksi_akun where id_transaksi_akun like '%"+fCari.getText()+"%' or nama_transaksi_akun like '%"+fCari.getText()+"%'";
        hasil = koneksi.ambilData(query);
        setModel(hasil);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {     
            fTabel.requestFocusInWindow();
        }
        }catch (Exception e) {
        }
    }//GEN-LAST:event_fCariKeyReleased

    private void fCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fCariActionPerformed
        fTabel.requestFocusInWindow();
    }//GEN-LAST:event_fCariActionPerformed

    private void fSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fSimpanActionPerformed
         simpanAtauEdit();
    }//GEN-LAST:event_fSimpanActionPerformed

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
            java.util.logging.Logger.getLogger(Master_Akun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Akun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Akun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Akun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master_Akun().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal2;
    private javax.swing.JButton btnTambahAkun;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JDialog dTambahUpdateAkun;
    private javax.swing.JTextField fCari;
    private javax.swing.JTextField fNama;
    private javax.swing.JButton fSimpan;
    private javax.swing.JTable fTabel;
    private javax.swing.JLabel fkode;
    private javax.swing.JRadioButton fpemasukan;
    private javax.swing.JRadioButton fpengeluaran;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JScrollPane jScrollPane8;
    // End of variables declaration//GEN-END:variables
        private void setModel(ResultSet hasil){
        try {
            list = new ArrayList<Java.ListAkun>();
            while(hasil.next()){
                listAkun = new Java.ListAkun();
                listAkun.setkode_akun(hasil.getInt("id_transaksi_akun"));
                listAkun.setNama_akun(hasil.getString("nama_transaksi_akun"));
                list.add(listAkun);
            }
            model = new modelTabelAkun(list);
            fTabel.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void ambilDataTabel() {
        int row = fTabel.getSelectedRow();
        kode_akun = ((Integer) fTabel.getValueAt(row, 0));
        nama_akun = ((String) fTabel.getValueAt(row, 1));
    }
    
        private void simpanAtauEdit(){
        if(edithapus==0){
            edit();
        }else{
            String nama = this.fNama.getText();
            int angka = 0;
            try{
                //membuat kode akun baru
                query= "SELECT id_transaksi_akun FROM transaksi_akun ORDER BY id_transaksi_akun DESC LIMIT 1";
                hasil = koneksi.ambilData(query);
                while(hasil.next()){
                    angka = hasil.getInt("id_transaksi_akun");
                    System.out.println(angka);
                    id = angka + 1;
                    System.out.println("id"+id);
                    //mengambil nilai radio button
                    if (fpemasukan.isSelected()) {
                        nilai_status_akun = 0;
                    }
                    if (fpengeluaran.isSelected()) {
                        nilai_status_akun = 1;
                    }

                    String sql= "Insert into transaksi_akun values ('"+id+"', '"+nama+"',0,0,0,"+nilai_status_akun+",0,0,0,0)";
                    System.out.println(sql);
                    koneksi.simpanData(sql);

                    JOptionPane.showMessageDialog(null,"Data sukses di input");
                    dTambahUpdateAkun.dispose();

                    tampilTabel();
                    bersih();
                }

            }catch (SQLException e){
                System.out.println();
            }
        }
    }

    private void edit() {
        try {
            if (fpemasukan.isSelected()) {
                 nilai_status_akun=0;
            }
            if (fpengeluaran.isSelected()) {
                nilai_status_akun= 1;
            }
            String sql = "Update transaksi_akun set nama_transaksi_akun=?,status_debit_kredit=? where id_transaksi_akun=?";
            PreparedStatement p = (PreparedStatement) koneksi.Connect().prepareStatement(sql);
            p.setString(1, fNama.getText().toString());
            p.setInt(2, nilai_status_akun);
            p.setInt(3, kode_akun);
            p.executeUpdate();

            dTambahUpdateAkun.dispose();
            tampilTabel();
            JOptionPane.showMessageDialog(null, "Data sukses di edit");
            bersih();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void bersih(){
        edithapus = 0;
        fNama.setText("");
        fpemasukan.setSelected(true);
    }
}
