/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Clock;
import Java.Connect;
import Java.Currency_Column;
import Java.ListHutangBG;
import Java.modelTabelHutangBG;
import com.sun.glass.events.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author User
 */
public final class Pembelian_HutangBG extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    static JFrame frameHutangBG;
    private Connect connection;
    private ResultSet hasil;
    private Clock clock;
    private List<ListHutangBG> list;
    private TableModel model;
    private ListHutangBG ListHutangBG;
    ArrayList<String> kode_nama_arr = new ArrayList();
    private static int item = 0;
    private boolean tampil = true;

    public Pembelian_HutangBG() {
        initComponents();
        this.setLocationRelativeTo(null);
        kalender();
        loadSupplier();
//        loadTable();
        tampilTabel("");
        
         //JCombobox supplier
        ((JTextComponent) comSupplier.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadSupplier(((JTextComponent) comSupplier.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
                            //tbl_Pembelian.editCellAt(tbl_Pembelian.getSelectedRow(), 2);
                            comSupplier.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comSupplier.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comSupplier.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                //((JTextComponent) comTableBarang.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");

            }

        });
        ((JTextComponent) comSupplier.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                tampil = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tampil = false;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tampil = true;
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

            }
        });
    }

    private String currency_convert(int nilai) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(nilai);
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
                    lbl_tgl.setText(hari + "-" + (bulan + 1) + "-" + tahun + " | " + jam + ":" + menit);
                }
            }
        };
        p.start();
    }
    
    void loadSupplier(String param) {
        if (param.equals("*")) {
            param = "";
        }
        try {
//            String sql = "select * from supplier where nama_supplier like '%"+param+"%' or kode_supplier like '%"+param+"%'";
            String sql = "select concat(kode_supplier,\"-\",nama_supplier) as gabung from supplier "
                    + "where kode_supplier ='" + param + "' OR nama_supplier like '%" + param + "%'";
//            System.out.println("sql: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
//            System.out.println("ini sql com kode nama " + sql);
            kode_nama_arr.clear();
            kode_nama_arr.add("");
            while (res.next()) {
                String gabung = res.getString("gabung");
                kode_nama_arr.add(gabung);
                item++;
            }
            if (item == 0) {
                item = 1;
            }
//            System.out.println("kdenamarr: " + kode_nama_arr);
            comSupplier.setModel(new DefaultComboBoxModel(kode_nama_arr.toArray()));
            //((JTextComponent) comSupplier.getEditor().getEditorComponent()).setText(param);
            conn.close();
            res.close();
//            }
        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Eror -- 190" + e);
        }
    }

//    public void loadTable() {
//        removeRow();
//        DefaultTableModel model = (DefaultTableModel) tbl_hutangBG.getModel();
//        int i = 1;
//        try {
//            String sql = "select "
//                    + "pembelian.tgl_pembelian,"
//                    + "pembelian.faktur_bg,"
//                    + "pembelian.no_seri_bg,"
//                    + "supplier.kota_supplier,"
//                    + "supplier.nama_supplier,"
//                    + "pembelian.biaya_pembayaran,"
//                    + "pembelian.potongan "
//                    + "FROM supplier, pembelian "
//                    + "WHERE supplier.kode_supplier = pembelian.kode_supplier "
//                    + "AND nama_supplier LIKE '%"
//                    + txt_supplier.getText()
//                    + "%' "
//                    + "AND biaya_pembayaran < 0 "
//                    + "AND faktur_bg !='' "
//                    + "AND no_seri_bg !='' "
//                    + "ORDER BY tgl_pembelian DESC";
//            System.out.println(sql);
//            Connection conn = (Connection) Koneksi.configDB();
//            Statement stat = conn.createStatement();
//            ResultSet res = stat.executeQuery(sql);
//            while (res.next()) {
//                model.addRow(new Object[]{
//                    i++,
//                    res.getString("faktur_bg"),
//                    dotConverter(res.getString("tgl_pembelian")),
//                    res.getString("no_seri_bg"),
//                    res.getString("nama_supplier"),
//                    res.getString("kota_supplier"),
//                    res.getString("biaya_pembayaran")
//                });
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Eror = " + e);
//        }
//
//    }
    void loadSupplier() {

        try {
            String sql = "select * from supplier order by nama_supplier";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(1) + "- " + res.getString(2);
                comSupplier.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    public void tampilTabel(String param) {
//        removeRow();
        if (param.equals("-- Pilih Supplier --")) {
            param = "";
        }
//        DefaultTableModel model = (DefaultTableModel) TabelPiutangBG.getModel();
        int i = 1;
        try {
            String sql = "SELECT *, SUM(biaya_pembayaran) as biaya "
                    + "FROM `pembelian`, supplier "
                    + "WHERE pembelian.kode_supplier = supplier.kode_supplier "
                    + "AND `biaya_pembayaran` < 0 "
                    + "AND `faktur_bg` != '' "
                    + "AND supplier.nama_supplier like '%" + param + "%'"
                    + "GROUP BY `faktur_bg` ORDER BY `faktur_bg` DESC";
            System.out.println("sqlHutangBG: " + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            setModel(res);
            TableColumnModel m = tbl_hutangBG.getColumnModel();
            m.getColumn(6).setCellRenderer(new Currency_Column());
            m.getColumn(7).setCellRenderer(new Currency_Column());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }

    private void setModel(ResultSet hasil) {
        try {
            int a = 0;
            list = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            while (hasil.next()) {
                a++;
                this.ListHutangBG = new ListHutangBG();
                this.ListHutangBG.setNomor(a);
                this.ListHutangBG.setNo_fakturBG(hasil.getString("faktur_bg"));
                this.ListHutangBG.setTanggal(sdf.format(hasil.getDate("tgl_pembelian")));
                this.ListHutangBG.setNoSeri(hasil.getString("no_seri_bg"));
                this.ListHutangBG.setKode_supplier(hasil.getInt("kode_supplier"));
                this.ListHutangBG.setNamaSupplier(hasil.getString("nama_supplier"));
                this.ListHutangBG.setKotaSupplier(hasil.getString("kota_supplier"));
                this.ListHutangBG.setBiaya(hasil.getInt("biaya"));
//                int sisa = hasil.getInt("totale") - (-1*hasil.getInt("pembaran_udah_bayar"));
//                this.ListHutangBG.setSisa(hasil.getInt("pembaran_udah_bayar"));
                list.add(ListHutangBG);
                ListHutangBG = null;
            }
            model = new modelTabelHutangBG(list);
            tbl_hutangBG.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void removeRow() {
        DefaultTableModel model = (DefaultTableModel) tbl_hutangBG.getModel();
        int row = tbl_hutangBG.getRowCount();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        lbl_tgl = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_hutangBG = new javax.swing.JTable();
        jLabel44 = new javax.swing.JLabel();
        comSupplier = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(412, 45));

        jLabel92.setBackground(new java.awt.Color(217, 237, 247));
        jLabel92.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel92.setText("Tabel Hutang Pembelian BG");

        lbl_tgl.setBackground(new java.awt.Color(255, 255, 255));
        lbl_tgl.setText("waktu");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_tgl)
                    .addComponent(jLabel92))
                .addGap(117, 117, 117))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tbl_hutangBG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No.", "No Faktur BG", "Tanggal", "No Seri", "Supplier", "Kota", "Biaya", "Sub Total"
            }
        ));
        tbl_hutangBG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_hutangBGMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_hutangBG);

        jLabel44.setText("Supplier");

        comSupplier.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comSupplier.setEditable(true);
        comSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Supplier --" }));
        comSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comSupplierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel44)
                        .addGap(33, 33, 33)
                        .addComponent(comSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(comSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(991, 693));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_hutangBGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_hutangBGMouseClicked
//        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
        int row = tbl_hutangBG.getSelectedRow();
        String table_click = (tbl_hutangBG.getModel().getValueAt(row, 1).toString());
        Pembelian_HutangBG_DetailFaktur df = new Pembelian_HutangBG_DetailFaktur(table_click);
        df.setVisible(true);
        df.setLocationRelativeTo(null);
//        }
//        Pembelian_HutangBG_DetailFaktur df = new Pembelian_HutangBG_DetailFaktur();
//        df.setVisible(true);
//        df.setLocationRelativeTo(null);
    }//GEN-LAST:event_tbl_hutangBGMouseClicked

    private void comSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comSupplierActionPerformed
        // TODO add your handling code here:
        tampilTabel(comSupplier.getSelectedItem().toString());
    }//GEN-LAST:event_comSupplierActionPerformed

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
            java.util.logging.Logger.getLogger(Pembelian_HutangBG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_HutangBG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_HutangBG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_HutangBG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        frameHutangBG = new JFrame("HutangBG");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pembelian_HutangBG().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comSupplier;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lbl_tgl;
    private javax.swing.JTable tbl_hutangBG;
    // End of variables declaration//GEN-END:variables
}
