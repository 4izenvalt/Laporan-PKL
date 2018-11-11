package UI;

import Java.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class Master_Salesman extends javax.swing.JDialog {

    private ArrayList<ListSalesman> list;
    private ListSalesman listSalesman;
    private DefaultTableModel tabel;
    private ResultSet hasil;
    private PreparedStatement PS;
    private Connect connection;
    private Clock clock;
    private int dd;

    public Master_Salesman() {
        initComponents();
    }

    public Master_Salesman(java.awt.Frame parent, boolean modal, Connect connection) {
        super(parent, modal);
        initComponents();
        clock = new Clock(lblTanggal, 3);
        this.connection = connection;
//        code here
        jTable9.setModel(new DefaultTableModel(new String[]{"No", "Nama", "Contact", "Telepon", "Alamat", "Kota"}, 0));
        tampilTabel("1");
    }

    private void deleteTabel() {
        for (int i = 0; i < tabel.getRowCount(); i++) {
            tabel.removeRow(0);
        }
    }

    public String tampilTabel(String param) {
        String data = "SELECT kode_salesman, nama_salesman, contact_salesman, telepon_salesman, alamat, kota_salesman, aktif_sales, gaji_salesman "
                + "FROM salesman "
                + (param.equals("-1") ? "" : (param.equals("1") || param.equals("0") ? "WHERE aktif_sales = " + Integer.parseInt(param) : "WHERE nama_salesman like '%" + param + "%'"));
//        System.out.println(data);
        try {
            hasil = connection.ambilData(data);
            setModel(hasil);
        } catch (Exception e) {
            System.out.println("Master_Salesman_Line_43_" + e.toString());
            System.out.println(data);
        }
        return data;
    }

    public void setModel(ResultSet hasil) {
        try {
            list = new ArrayList<>();
            tabel = new DefaultTableModel(new String[]{"No", "Nama", "Contact", "Telepon", "Alamat", "Kota"}, 0);
//            int no = 1;
            while (hasil.next()) {
                listSalesman = new ListSalesman();
                listSalesman.setNo(hasil.getInt("kode_salesman"));
                listSalesman.setNama_salesman(hasil.getString("nama_salesman"));
                listSalesman.setContact_salesman(hasil.getString("contact_salesman"));
                listSalesman.setTelepon_salesman(hasil.getString("telepon_salesman"));
                listSalesman.setAlamat_salesman(hasil.getString("alamat"));
                listSalesman.setKota_salesman(hasil.getString("kota_salesman"));
                listSalesman.setStatus(hasil.getInt("aktif_sales"));
                listSalesman.setGaji(hasil.getDouble("gaji_salesman"));
                list.add(listSalesman);
                tabel.addRow(new Object[]{
                    listSalesman.getNo(),
                    listSalesman.getNama_salesman(),
                    listSalesman.getContact_salesman(),
                    listSalesman.getTelepon_salesman(),
                    listSalesman.getAlamat_salesman(),
                    listSalesman.getKota_salesman()
                });
//                no++;
                listSalesman = null;
            }
        } catch (SQLException e) {
            System.out.println("Master_Salesman_Line_59_" + e.toString());
        } finally {
            jTable9.setModel(tabel);
        }
    }

    private int getNewId() {
        String sql = "SELECT kode_salesman FROM salesman ORDER BY kode_salesman DESC LIMIT 1";
        int id = 0;
        try {
            hasil = connection.ambilData(sql);
            while (hasil.next()) {
                id = hasil.getInt("kode_salesman") + 1;
            }
        } catch (SQLException e) {
        }
        return id;
    }

    private void insertData(ListSalesman listSalesman) {
        PS = null;
        if (listSalesman.getNo() != 0) {
            try {
                String sql = "insert into salesman values (?,?,?,?,?,?,?,?,?,?)";
                PS = connection.Connect().prepareStatement(sql);
                PS.setInt(1, listSalesman.getNo());
                PS.setString(2, listSalesman.getNama_salesman());
                PS.setString(3, listSalesman.getAlamat_salesman());
                PS.setString(4, listSalesman.getKota_salesman());
                PS.setString(5, listSalesman.getTelepon_salesman());
                PS.setString(6, listSalesman.getContact_salesman());
                PS.setInt(7, listSalesman.getStatus());
                PS.setString(8, "");
                PS.setString(9, "");
                PS.setDouble(10, listSalesman.getGaji());
                PS.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Master_Salesman_Line_105_" + e.toString());
            }
        }
    }

    private void updateData(ListSalesman listSalesman) {
        PS = null;
        try {
            String sql = "update salesman set nama_salesman=?,alamat=?,kota_salesman=?,telepon_salesman=?,contact_salesman=?,"
                    + "aktif_sales=?,gaji_salesman=?"
                    + "where kode_salesman=?";
            PS = connection.Connect().prepareStatement(sql);
            PS.setString(1, listSalesman.getNama_salesman());
            PS.setString(2, listSalesman.getAlamat_salesman());
            PS.setString(3, listSalesman.getKota_salesman());
            PS.setString(4, listSalesman.getTelepon_salesman());
            PS.setString(5, listSalesman.getContact_salesman());
            PS.setInt(6, listSalesman.getStatus());
            PS.setDouble(7, listSalesman.getGaji());
            PS.setInt(8, listSalesman.getNo());
            PS.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Master_Salesman_Line_126_" + e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton33 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jComboBox7 = new javax.swing.JComboBox<>();
        jButton29 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jTextField90 = new javax.swing.JTextField();
        jLabel184 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        lblTanggal = new javax.swing.JLabel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jButton33.setBackground(new java.awt.Color(71, 166, 227));
        jButton33.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton33.setText("Edit");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nama", "Contact", "Telepon", "Alamat", "Kota"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(jTable9);

        jComboBox7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktif ", "Tidak Aktif ", "Semua" }));
        jComboBox7.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBox7PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jButton29.setBackground(new java.awt.Color(71, 166, 227));
        jButton29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton29.setText("Tambah Salesman");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jButton34.setBackground(new java.awt.Color(71, 166, 227));
        jButton34.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton34.setText("Login");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        jTextField90.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cari", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        jTextField90.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField90KeyReleased(evt);
            }
        });

        jLabel184.setText("Refresh");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField90, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel184, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel184))
                            .addComponent(jTextField90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton34)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLabel45.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(49, 112, 143));
        jLabel45.setText("Salesman Form");

        lblTanggal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTanggal.setForeground(new java.awt.Color(49, 112, 143));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(832, 592));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        listSalesman = list.get(jTable9.getSelectedRow());
        Master_Salesman_TambahSalesman ts = new Master_Salesman_TambahSalesman(new Awal(rootPaneCheckingEnabled), rootPaneCheckingEnabled, listSalesman, true);
        ts.setLocationRelativeTo(this);
        ts.setVisible(true);
        updateData(listSalesman);
        listSalesman = null;
        tampilTabel("1");
        jTable9.clearSelection();
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        listSalesman = new ListSalesman();
        Master_Salesman_TambahSalesman ts = new Master_Salesman_TambahSalesman(new Awal(rootPaneCheckingEnabled), rootPaneCheckingEnabled, listSalesman, getNewId());
        ts.setLocationRelativeTo(this);
        ts.setVisible(true);
        insertData(listSalesman);
        tampilTabel("1");
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        Master_Login log = new Master_Login();
        log.setVisible(true);
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jComboBox7PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBox7PopupMenuWillBecomeInvisible
        dd = jComboBox7.getSelectedIndex();
        deleteTabel();
        switch (dd) {
            case 0:
                tampilTabel("1");
                break;
            case 1:
                tampilTabel("0");
                break;
            case 2:
                tampilTabel("-1");
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jComboBox7PopupMenuWillBecomeInvisible

    private void jTextField90KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField90KeyReleased
        tampilTabel(jTextField90.getText());
    }//GEN-LAST:event_jTextField90KeyReleased

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
            java.util.logging.Logger.getLogger(Master_Salesman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Salesman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Salesman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Salesman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master_Salesman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JTable jTable9;
    private javax.swing.JTextField jTextField90;
    private javax.swing.JLabel lblTanggal;
    // End of variables declaration//GEN-END:variables
}
