package UI;

import javax.swing.JOptionPane;
import Class.Koneksi;
import Java.Connect;
import static UI.Pembelian_Hutang.dotConverter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author User
 */
public class Master_Mutasi_PermintaanMutasiAntarGudang extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    private Connect connection;
    int lokasi_asal, lokasi_tujuan;
    String lokasi[] = {"PUSAT", "GUD63", "", "TOKO", "TENGAH", "UTARA"};

    public Master_Mutasi_PermintaanMutasiAntarGudang() {
        initComponents();
        this.setLocationRelativeTo(null);
        loadTable("ON PROGRESS", "*");
    }

    public void loadTable(String param, String param2) {
        removeRow();
        if (param.equals("*")) {
            param = "";
        }
        if (param2.equals("*")) {
            param2 = "";
        }
        DefaultTableModel model = (DefaultTableModel) tbl_Permintaan_MutasiAG.getModel();
        int i = 1;
        try {
            String sql = "select distinct g.tanggal,"
                    + "l.nama_lokasi, "
                    + "g.no_bukti,"
                    + "gd.kode_lokasi_tujuan,"
                    + "gd.kode_lokasi_asal,"
                    + "g.status,"
                    + "g.user "
                    + "FROM mutasi_antar_gudang g, mutasi_antar_gudang_detail gd , lokasi l "
                    + "WHERE g.no_bukti = gd.no_bukti "
                    + "AND l.kode_lokasi = gd.kode_lokasi_tujuan "
                    + "AND g.status like '%" + param + "%'"
                    + "AND g.no_bukti like '%" + param2 + "%'"
                    + "ORDER BY g.tanggal DESC";
            System.out.println("sqlll: " + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                lokasi_asal = Integer.parseInt(res.getString("kode_lokasi_asal"));
                lokasi_tujuan = Integer.parseInt(res.getString("kode_lokasi_tujuan"));
                model.addRow(new Object[]{
                    dotConverter(res.getString("tanggal")),
                    res.getString("no_bukti"),
                    lokasi[lokasi_tujuan - 1],
                    lokasi[lokasi_asal - 1],
                    res.getString("status"),
                    res.getString("user")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }

    }

    public void removeRow() {
        DefaultTableModel model = (DefaultTableModel) tbl_Permintaan_MutasiAG.getModel();
        int row = tbl_Permintaan_MutasiAG.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    static String dotConverter(String b) {
        b = b.replace(".0", "");
        return b;
    }

    void NewData() {
        Master_Mutasi_PermintaanMutasiAntarGudang_DetailNew pmag = new Master_Mutasi_PermintaanMutasiAntarGudang_DetailNew(this, comFilter.getSelectedItem().toString());
        pmag.setVisible(true);
        pmag.setFocusable(true);
    }

    void DeleteData() {
        int hapus = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin Menghapus data ini ?", "Konfimasi Tolak Hapus Data", JOptionPane.OK_CANCEL_OPTION);
        if (hapus == JOptionPane.OK_OPTION) {
            try {
                Connection conn = (Connection) Koneksi.configDB();
                String sql = "delete from mutasi_antar_gudang where no_bukti='" + tbl_Permintaan_MutasiAG.getValueAt(tbl_Permintaan_MutasiAG.getSelectedRow(), 1) + "'";
                PreparedStatement st = conn.prepareStatement(sql);
                st.executeUpdate();
                System.out.println(sql);
                String sql2 = "delete from mutasi_antar_gudang_detail where no_bukti='" + tbl_Permintaan_MutasiAG.getValueAt(tbl_Permintaan_MutasiAG.getSelectedRow(), 1) + "'";
                PreparedStatement st2 = conn.prepareStatement(sql2);
                st2.executeUpdate();
                System.out.println(sql2);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Delete Data Gagal");
            }
            JOptionPane.showMessageDialog(null, "Data sudah dihapus.");
            loadTable(comFilter.getSelectedItem().toString(), "*");
        }
    }

    void SelectData() {
        if (tbl_Permintaan_MutasiAG.getSelectedRow() >= 0) {
            try {
                int i = tbl_Permintaan_MutasiAG.getSelectedRow();

                TableModel model = tbl_Permintaan_MutasiAG.getModel();
                System.out.println("dfgdfxvxxvcx");
                System.out.println(tbl_Permintaan_MutasiAG.getValueAt(tbl_Permintaan_MutasiAG.getSelectedRow(), 1).toString());
                Master_MutasiBarangAntarGudangBerdasarkanPermintaan mmg = new Master_MutasiBarangAntarGudangBerdasarkanPermintaan(tbl_Permintaan_MutasiAG.getValueAt(tbl_Permintaan_MutasiAG.getSelectedRow(), 1).toString(), comFilter.getSelectedItem().toString());
                mmg.setLocationRelativeTo(this);
                mmg.setVisible(true);
                mmg.setFocusable(true);
                System.out.println("berhasil link ");
            } catch (Exception e) {
                System.out.println("master_group_line250" + e.toString());
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Group Terlebih Dahulu");
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

        txt_noBukti = new javax.swing.JTextField();
        comFilter = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Permintaan_MutasiAG = new javax.swing.JTable();
        txt_Kriteria = new javax.swing.JTextField();
        label_New = new javax.swing.JLabel();
        label_Delete = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        comFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ON PROGRESS", "OPEN" }));
        comFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comFilterActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setText("Kriteria");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.lightGray));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("PERMINTAAN MUTASI ANTAR GUDANG");

        tbl_Permintaan_MutasiAG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Tanggal", "No.Bukti", "Gudang Peminta", "Gudang Penyedia", "Status", "TUser"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Permintaan_MutasiAG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_Permintaan_MutasiAGMouseClicked(evt);
            }
        });
        tbl_Permintaan_MutasiAG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_Permintaan_MutasiAGKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_Permintaan_MutasiAG);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(229, 229, 229))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txt_Kriteria.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_Kriteria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_KriteriaKeyReleased(evt);
            }
        });

        label_New.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label_New.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_manilla-folder-new_23456.png"))); // NOI18N
        label_New.setText("F2-New");
        label_New.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_NewMouseClicked(evt);
            }
        });

        label_Delete.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label_Delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N
        label_Delete.setText("F5-Delete");
        label_Delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_DeleteMouseClicked(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jMenuBar1.setPreferredSize(new java.awt.Dimension(0, 0));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem1.setText("New");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem2.setText("Delete");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Kriteria, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_New)
                .addGap(5, 5, 5)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_Delete)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_Kriteria, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_New)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_Delete))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(979, 510));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comFilterActionPerformed
        loadTable(comFilter.getSelectedItem().toString(), txt_Kriteria.getText());
    }//GEN-LAST:event_comFilterActionPerformed

    private void label_NewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_NewMouseClicked
        NewData();
    }//GEN-LAST:event_label_NewMouseClicked

    private void tbl_Permintaan_MutasiAGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Permintaan_MutasiAGMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            System.out.println("Double Click");
            SelectData();
        }
    }//GEN-LAST:event_tbl_Permintaan_MutasiAGMouseClicked

    private void label_DeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_DeleteMouseClicked
        DeleteData();
    }//GEN-LAST:event_label_DeleteMouseClicked

    private void txt_KriteriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_KriteriaKeyReleased
        loadTable(comFilter.getSelectedItem().toString(), txt_Kriteria.getText());
    }//GEN-LAST:event_txt_KriteriaKeyReleased

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void tbl_Permintaan_MutasiAGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_Permintaan_MutasiAGKeyPressed
        System.out.println(evt.getKeyCode());
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            NewData();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F5) {
            DeleteData();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            SelectData();
        }

    }//GEN-LAST:event_tbl_Permintaan_MutasiAGKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        NewData();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        DeleteData();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
            java.util.logging.Logger.getLogger(Master_Mutasi_PermintaanMutasiAntarGudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Mutasi_PermintaanMutasiAntarGudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Mutasi_PermintaanMutasiAntarGudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Mutasi_PermintaanMutasiAntarGudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master_Mutasi_PermintaanMutasiAntarGudang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel label_Delete;
    private javax.swing.JLabel label_New;
    private javax.swing.JTable tbl_Permintaan_MutasiAG;
    private javax.swing.JTextField txt_Kriteria;
    private javax.swing.JTextField txt_noBukti;
    // End of variables declaration//GEN-END:variables
}
