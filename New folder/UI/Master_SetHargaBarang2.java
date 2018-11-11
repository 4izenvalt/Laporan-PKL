/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Java.Connect;
import Java.Currency_Column;
import Java.ListSetBarang;
import Java.modelTabelSetBarang;
import com.placeholder.PlaceHolder;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author 7
 */
public class Master_SetHargaBarang2 extends javax.swing.JDialog {

    private Connect connection;
    private ResultSet hasil;
    private ArrayList<ListSetBarang> list;
    private ListSetBarang listbarang;
    private TableModel model;
    private PreparedStatement PS;
    String sql, kodebrg;
    ResultSet rs;
    private int kode_barang;

    public Master_SetHargaBarang2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.connection = new Connect();
        tampilTabel("*");
        holder();
    }

    public void holder() {
        PlaceHolder holder;
        holder = new PlaceHolder(harga1, "masukan harga 1");
        holder = new PlaceHolder(harga2, "masukan harga 2");
        holder = new PlaceHolder(harga3, "masukan harga 3");
    }

    public void cari() {
        try {
            String sql = "select * from barang where nama_barang like '%" + jcari.getText() + "%'";
            hasil = connection.ambilData(sql);
        } catch (Exception e) {
            System.out.println("gagal query ini" + e);
        }
    }

    private void tampilTabel(String param) {
        String data = "";
        try {
            data = "SELECT kode_barang, nama_barang, harga_jual_1_barang, harga_jual_2_barang, harga_jual_3_barang "
                    + "FROM barang " + (param.equals("*") ? "" : "where nama_barang like '%" + param + "%'");
            hasil = connection.ambilData(data);
            //System.out.println("sukses query tampil tabel");
            setModel(hasil);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR -> " + e.getMessage());
        } finally {
//            System.out.println(data);
        }
    }

    private void setModel(ResultSet hasil) {
        try {
            list = new ArrayList<>();
            int a = 0;
            while (hasil.next()) {
                a++;
                this.listbarang = new ListSetBarang();
                this.listbarang.setNomor(a);
                this.listbarang.setKode_barang(hasil.getInt("kode_barang"));
                this.listbarang.setNama_barang(hasil.getString("nama_barang"));
                this.listbarang.setHarga_jual_1_barang(hasil.getInt("harga_jual_1_barang"));
                this.listbarang.setHarga_jual_2_barang(hasil.getInt("harga_jual_2_barang"));
                this.listbarang.setHarga_jual_3_barang(hasil.getInt("harga_jual_3_barang"));
                list.add(listbarang);
                listbarang = null;
            }
            model = new modelTabelSetBarang(list);
            jTable10.setModel(model);
            TableColumnModel m = jTable10.getColumnModel();
            m.getColumn(3).setCellRenderer(new Currency_Column());
            m.getColumn(4).setCellRenderer(new Currency_Column());
            m.getColumn(5).setCellRenderer(new Currency_Column());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        harga1 = new javax.swing.JTextField();
        harga2 = new javax.swing.JTextField();
        harga3 = new javax.swing.JTextField();
        btsetharga = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        jcari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Set Harga Barang");

        harga1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                harga1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                harga1FocusLost(evt);
            }
        });
        harga1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                harga1MouseClicked(evt);
            }
        });
        harga1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                harga1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                harga1KeyTyped(evt);
            }
        });

        harga2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                harga2MouseClicked(evt);
            }
        });
        harga2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                harga2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                harga2KeyTyped(evt);
            }
        });

        harga3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                harga3MouseClicked(evt);
            }
        });
        harga3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                harga3KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                harga3KeyTyped(evt);
            }
        });

        btsetharga.setText("Set Harga");
        btsetharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsethargaActionPerformed(evt);
            }
        });

        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
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
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No.", "Kode Barang", "Nama", "Harga Jual 1", "Harga Jual 2", "Harga Jual 3"
            }
        ));
        jTable10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable10MouseClicked(evt);
            }
        });
        jTable10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable10KeyReleased(evt);
            }
        });
        jScrollPane14.setViewportView(jTable10);

        jcari.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        jcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcariActionPerformed(evt);
            }
        });
        jcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jcariKeyReleased(evt);
            }
        });

        jLabel2.setText("Kriteria");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(367, 367, 367)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 964, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcari, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(harga1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(harga2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(harga3, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btsetharga)
                .addGap(225, 225, 225))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(harga3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(harga2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(harga1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btsetharga))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcari, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void harga1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_harga1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_harga1MouseClicked

    private void harga1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_harga1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_harga1KeyPressed

    private void harga2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_harga2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_harga2MouseClicked

    private void harga2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_harga2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_harga2KeyPressed

    private void harga3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_harga3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_harga3MouseClicked

    private void harga3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_harga3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_harga3KeyPressed

    private void btsethargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsethargaActionPerformed

        try {
            String sql = "update barang set harga_jual_1_barang=?,harga_jual_2_barang=?,harga_jual_3_barang=? where kode_barang=? ";
            PreparedStatement p = (PreparedStatement) connection.Connect().prepareStatement(sql);
            p.setString(1, harga1.getText().toString());
            p.setString(2, harga2.getText().toString());
            p.setString(3, harga3.getText().toString());
            p.setString(4, kodebrg);
//                p.setInt(2, id);
            p.executeUpdate();
            tampilTabel("*");
            System.out.print(p);
            JOptionPane.showMessageDialog(null, "Data sukses di edit");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btsethargaActionPerformed

    private void jTable10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable10MouseClicked

        try {
            int row = jTable10.getSelectedRow();
            String table_click = (jTable10.getModel().getValueAt(row, 1).toString());
            String sql = "select * from barang where kode_barang='" + table_click + "' ";
            hasil = connection.ambilData(sql);
//            rs = PS.executeQuery();
            System.out.println("sukses query tampil tabel");
            while (hasil.next()) {

                String add1 = hasil.getString("harga_jual_1_barang");
                harga1.setText(add1);
                String add2 = hasil.getString("harga_jual_2_barang");
                harga2.setText(add2);
                String add3 = hasil.getString("harga_jual_3_barang");
                harga3.setText(add3);
                kodebrg = hasil.getString("kode_barang");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTable10MouseClicked

    private void harga1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_harga1KeyTyped


    }//GEN-LAST:event_harga1KeyTyped

    private void harga2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_harga2KeyTyped

    }//GEN-LAST:event_harga2KeyTyped

    private void harga3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_harga3KeyTyped

    }//GEN-LAST:event_harga3KeyTyped

    private void harga1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_harga1FocusGained

    }//GEN-LAST:event_harga1FocusGained

    private void harga1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_harga1FocusLost

    }//GEN-LAST:event_harga1FocusLost

    private void jcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcariKeyReleased

//        DefaultTableModel table = (DefaultTableModel) jTable10.getModel();
//        String Search = jcari.getText().toString();
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table);
//        jTable10.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(Search));
//       cari();
        tampilTabel(jcari.getText().toString());

    }//GEN-LAST:event_jcariKeyReleased

    private void jcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcariActionPerformed

    private void jTable10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable10KeyReleased
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_ENTER:
                try {
                    int row = jTable10.getSelectedRow();
                    String table_click = (jTable10.getModel().getValueAt(row, 1).toString());
                    String sql = "select * from barang where kode_barang='" + table_click + "' ";
                    hasil = connection.ambilData(sql);
//            rs = PS.executeQuery();
                    System.out.println("sukses query tampil tabel");
                    while (hasil.next()) {

                        String add1 = hasil.getString("harga_jual_1_barang");
                        harga1.setText(add1);
                        String add2 = hasil.getString("harga_jual_2_barang");
                        harga2.setText(add2);
                        String add3 = hasil.getString("harga_jual_3_barang");
                        harga3.setText(add3);
                        kodebrg = hasil.getString("kode_barang");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jTable10KeyReleased

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
            java.util.logging.Logger.getLogger(Master_SetHargaBarang2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_SetHargaBarang2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_SetHargaBarang2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_SetHargaBarang2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Master_SetHargaBarang2 dialog = new Master_SetHargaBarang2(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btsetharga;
    private javax.swing.JTextField harga1;
    private javax.swing.JTextField harga2;
    private javax.swing.JTextField harga3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable10;
    private javax.swing.JTextField jcari;
    // End of variables declaration//GEN-END:variables
}
