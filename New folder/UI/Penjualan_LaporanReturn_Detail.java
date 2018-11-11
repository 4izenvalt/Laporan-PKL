package UI;

import Java.Clock;
import Java.Connect;
import Java.Currency_Column;
import Java.ListLaporanReturn;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Penjualan_LaporanReturn_Detail extends javax.swing.JDialog {

    private ResultSet hasil;
    public Statement stmt;
    private Connect connection;
    private DefaultTableModel tabelDetail;
    private ListLaporanReturn listLaporanReturn;

    public Penjualan_LaporanReturn_Detail() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public Penjualan_LaporanReturn_Detail(java.awt.Frame parent, boolean modal, Connect connection, ListLaporanReturn listLaporanReturn) {
        super(parent, modal);
        initComponents();
        this.listLaporanReturn = listLaporanReturn;
        this.connection = new Connect();
        tabelDetail = new DefaultTableModel(new String[]{
            "No.", "Barang", "Lokasi", "Satuan", "Jumlah", "Harga", "Saldo"}, 0);
        tblDetail.setModel(tabelDetail);
        no_faktur_return.setText(this.listLaporanReturn.getNo_faktur_return());
        isiTabelReturnPenjualan(this.listLaporanReturn.getNo_faktur_return());
        AturlebarKolom();
    }

    private void AturlebarKolom() {
        TableColumn column;
        tblDetail.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tblDetail.getColumnModel().getColumn(0);
        column.setPreferredWidth(30);
        column = tblDetail.getColumnModel().getColumn(1);
        column.setPreferredWidth(200);
        column = tblDetail.getColumnModel().getColumn(2);
        column.setPreferredWidth(50);
        column = tblDetail.getColumnModel().getColumn(3);
        column.setPreferredWidth(50);
        column = tblDetail.getColumnModel().getColumn(4);
        column.setPreferredWidth(50);
        column = tblDetail.getColumnModel().getColumn(5);
        column.setPreferredWidth(70);
        column = tblDetail.getColumnModel().getColumn(6);
        column.setPreferredWidth(70);

    }

    private void isiTabelReturnPenjualan(String no_faktur) {
        try {
            String data = "select b.nama_barang, l.nama_lokasi, k.nama_konversi, pdr.jumlah_barang, "
                    + "pdr.harga_penjualan*bk.jumlah_konversi "
                    + "from barang b, lokasi l, konversi k, penjualan_detail_return pdr, barang_konversi bk "
                    + "where b.kode_barang = pdr.kode_barang "
                    + "and l.kode_lokasi = pdr.kode_lokasi "
                    + "and pdr.kode_barang = bk.kode_barang "
                    + "and pdr.kode_barang_konversi = bk.kode_barang_konversi "
                    + "and bk.kode_konversi = k.kode_konversi "
                    + "and pdr.no_faktur_return ='" + no_faktur + "'";
            hasil = connection.ambilData(data);
            setModelTabelReturn(hasil);
        } catch (Exception e) {
            System.out.println("Penjualan Laporan Detail Return/isiTabelBarang - " + e);
        }
    }

    private void setModelTabelReturn(ResultSet hasil) {
        try {
            int saldo = 0;
            int no = 1;
            while (hasil.next()) {
                String nama_barang = hasil.getString(1);
                String nama_lokasi = hasil.getString(2);
                String nama_konversi = hasil.getString(3);
                int jumlah = hasil.getInt(4);
                int harga = hasil.getInt(5);
                saldo += jumlah * harga;
                tabelDetail.addRow(new Object[]{no, nama_barang, nama_lokasi, nama_konversi, jumlah, harga, saldo});
                no++;
            }
            TableColumnModel m = tblDetail.getColumnModel();
            m.getColumn(5).setCellRenderer(new Currency_Column());
            m.getColumn(6).setCellRenderer(new Currency_Column());
        } catch (Exception e) {
            System.out.println("Penjualan Laporan Detail Return/setModelTabelReturn - " + e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel34 = new javax.swing.JPanel();
        jLabel140 = new javax.swing.JLabel();
        jSeparator21 = new javax.swing.JSeparator();
        jScrollPane16 = new javax.swing.JScrollPane();
        tblDetail = new javax.swing.JTable();
        jButton33 = new javax.swing.JButton();
        no_faktur_return = new javax.swing.JLabel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jLabel140.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(102, 102, 102));
        jLabel140.setText("Rincian Barang");

        jSeparator21.setForeground(new java.awt.Color(204, 204, 204));

        tblDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Barang", "Lokasi", "Satuan", "Jumlah", "Harga", "Saldo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane16.setViewportView(tblDetail);

        jButton33.setBackground(new java.awt.Color(153, 153, 153));
        jButton33.setText("Batal");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        no_faktur_return.setText("no_faktur_return");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator21)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel140)
                .addGap(69, 69, 69)
                .addComponent(no_faktur_return)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel140, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(no_faktur_return))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(585, 378));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        int batal = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin akan keluar dari halaman ini?", "Konfimasi Pembatalan", JOptionPane.OK_CANCEL_OPTION);
        if (batal == JOptionPane.OK_OPTION) {
            this.dispose();
        }
    }//GEN-LAST:event_jButton33ActionPerformed

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
            java.util.logging.Logger.getLogger(Penjualan_LaporanReturn_Detail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_LaporanReturn_Detail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_LaporanReturn_Detail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_LaporanReturn_Detail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan_LaporanReturn_Detail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton33;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JLabel no_faktur_return;
    private javax.swing.JTable tblDetail;
    // End of variables declaration//GEN-END:variables
}
