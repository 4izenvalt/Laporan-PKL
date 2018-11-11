package UI;

import javax.swing.JOptionPane;
import Java.Clock;
import java.sql.ResultSet;
import Java.Connect;
import Java.Currency_Column;
import Java.ListLaporanReturn;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class Penjualan_LaporanReturn extends javax.swing.JDialog {

    private Connect connection;
    private Clock clock;
    private ResultSet hasil1;
    private TableModel model;
    private DefaultTableModel tabel;
    private Date date;
    private ArrayList<ListLaporanReturn> list;
    private ListLaporanReturn listLaporanReturn;
    private SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

    public Penjualan_LaporanReturn() {
        initComponents();
        this.setLocationRelativeTo(null);
        System.out.println("sas");
        tampilTabel("", "");
    }

    public Penjualan_LaporanReturn(java.awt.Frame parent, boolean modal, Connect connection) {
        super(parent, modal);
        initComponents();
        AturlebarKolom();
        clock = new Clock(tgl_waktu, 0);
        this.connection = connection;
        tampilTabel("", "");
    }

    private void AturlebarKolom() {
        TableColumn column;
        tblLaporanReturn.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tblLaporanReturn.getColumnModel().getColumn(0);
        column.setPreferredWidth(20);
        column = tblLaporanReturn.getColumnModel().getColumn(1);
        column.setPreferredWidth(80);
        column = tblLaporanReturn.getColumnModel().getColumn(2);
        column.setPreferredWidth(80);
        column = tblLaporanReturn.getColumnModel().getColumn(3);
        column.setPreferredWidth(70);
        column = tblLaporanReturn.getColumnModel().getColumn(4);
        column.setPreferredWidth(100);
        column = tblLaporanReturn.getColumnModel().getColumn(5);
        column.setPreferredWidth(100);
        column = tblLaporanReturn.getColumnModel().getColumn(6);
        column.setPreferredWidth(80);
        column = tblLaporanReturn.getColumnModel().getColumn(7);
        column.setPreferredWidth(80);
        column = tblLaporanReturn.getColumnModel().getColumn(8);
        column.setPreferredWidth(80);
        column = tblLaporanReturn.getColumnModel().getColumn(9);
        column.setPreferredWidth(80);
        column = tblLaporanReturn.getColumnModel().getColumn(10);
        column.setPreferredWidth(80);
    }

    private void setModel(ResultSet hasil) {
        list = new ArrayList<>();
        tabel = new DefaultTableModel(new String[]{"No", "No Faktur Return", "No Faktur Penjualan", "Jenis Return",
            "Tgl Return", "Nama Salesman", "Kota Salesman", "Nama Customer", "Kota Customer", "Total", "Saldo"}, 0);
        int temp = 1;
        int salto = 0;
        try {
            list = new ArrayList<>();
            while (hasil.next()) {
                this.listLaporanReturn = new ListLaporanReturn();
                this.listLaporanReturn.setNo_faktur_return(hasil.getString(1));
                this.listLaporanReturn.setNo_faktur_penjualan(hasil.getString(2));
                this.listLaporanReturn.setJenis_return(hasil.getInt(3));
                this.listLaporanReturn.setTgl_return(hasil.getString(4));
                this.listLaporanReturn.setNama_salesman(hasil.getString(5));
                this.listLaporanReturn.setKota_salesman(hasil.getString(6));
                this.listLaporanReturn.setNama_customer(hasil.getString(7));
                this.listLaporanReturn.setKota_customer(hasil.getString(8));
                this.listLaporanReturn.setTotal(hasil.getInt(9));
                salto += hasil.getInt(9);
                list.add(listLaporanReturn);
                tabel.addRow(new Object[]{
                    temp,
                    listLaporanReturn.getNo_faktur_return(),
                    listLaporanReturn.getNo_faktur_penjualan(),
                    listLaporanReturn.getJenis_return(),
                    listLaporanReturn.getTgl_return(),
                    listLaporanReturn.getNama_salesman(),
                    listLaporanReturn.getKota_salesman(),
                    listLaporanReturn.getNama_customer(),
                    listLaporanReturn.getKota_customer(),
                    listLaporanReturn.getTotal(),
                    salto
                });
                temp++;
                listLaporanReturn = null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            tblLaporanReturn.setModel(tabel);
            TableColumnModel m = tblLaporanReturn.getColumnModel();
            m.getColumn(9).setCellRenderer(new Currency_Column());
            m.getColumn(10).setCellRenderer(new Currency_Column());
        }
    }

    private String tampilTabel(String v1, String v2) {
        String datax = "";
        try {
            datax = "SELECT pd.no_faktur_return, pdr.no_faktur_penjualan, pd.jenis_return, pd.tgl_return, "
                    + "s.nama_salesman, s.kota_salesman, c.nama_customer, c.kota_customer, pd.biaya_return "
                    + "from penjualan_return pd, penjualan_detail_return pdr, customer c, salesman s "
                    + "where pd.kode_customer = c.kode_customer "
                    + "and pd.kode_salesman = s.kode_salesman "
                    + "and pd.no_faktur_return = pdr.no_faktur_return "
                    + (v1.equals("") && v2.equals("") ? "" : "and pd.tgl_return between '" + v1 + "' and '" + v2 + "'");

            hasil1 = connection.ambilData(datax);
            setModel(hasil1);
        } catch (Exception e) {
            System.out.println("Error tampil tabel = "+e.getMessage());
        }
        return datax;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel32 = new javax.swing.JPanel();
        jLabel129 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tblLaporanReturn = new javax.swing.JTable();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jLabel134 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel33 = new javax.swing.JPanel();
        jLabel137 = new javax.swing.JLabel();
        tgl_waktu = new javax.swing.JLabel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jPanel32.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jLabel129.setText("Tanggal");

        tblLaporanReturn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "No. Faktur Return", "No. Faktur Penjualan", "Jenis Return", "Tanggal", "Nama Salesman", "Kota", "Nama Customer", "Kota", "Total", "Saldo"
            }
        ));
        jScrollPane15.setViewportView(tblLaporanReturn);

        jButton31.setBackground(new java.awt.Color(71, 166, 227));
        jButton31.setText("Tampilkan");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton32.setBackground(new java.awt.Color(71, 166, 227));
        jButton32.setText("Detail Return");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jLabel134.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel134.setText("s.d.");

        jDateChooser1.setDateFormatString("dd-MM-yyyy");

        jDateChooser2.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton32))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel129)
                                .addGap(18, 18, 18)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel134)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton31))
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 990, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel134, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel129, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jButton32)
                .addContainerGap())
        );

        jPanel33.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, null));
        jPanel33.setForeground(new java.awt.Color(51, 51, 51));

        jLabel137.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel137.setText("Laporan Return Penjualan");

        tgl_waktu.setText("tanggal_waktu");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel137)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tgl_waktu, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel137, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tgl_waktu))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1052, 568));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        if (jDateChooser1.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Silahkan Input tanggal 1");
        }
        if (jDateChooser2.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Silahkan Input tanggal 2");
        } else {
            String vSearch1 = dt.format(jDateChooser1.getDate());
            String vSearch2 = dt.format(jDateChooser2.getDate());
            tampilTabel(vSearch1, vSearch2);
            JOptionPane.showMessageDialog(null, "Data berhasil di tampilkan");
        }
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        listLaporanReturn = list.get(tblLaporanReturn.getSelectedRow());
        Penjualan_LaporanReturn_Detail plrd = new Penjualan_LaporanReturn_Detail(new Awal(rootPaneCheckingEnabled), rootPaneCheckingEnabled, connection, listLaporanReturn);

        plrd.setVisible(true);
        plrd.setFocusable(true);
    }//GEN-LAST:event_jButton32ActionPerformed

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
            java.util.logging.Logger.getLogger(Penjualan_LaporanReturn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_LaporanReturn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_LaporanReturn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_LaporanReturn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan_LaporanReturn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JTable tblLaporanReturn;
    private javax.swing.JLabel tgl_waktu;
    // End of variables declaration//GEN-END:variables
}
