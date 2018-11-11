package UI;

import Class.Koneksi;
import Java.Connect;
import Java.ListTokoLaporanToko;
import com.sun.glass.events.KeyEvent;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

class arraysort implements Comparator<ListTokoLaporanToko> {

    @Override
    public int compare(ListTokoLaporanToko a, ListTokoLaporanToko b) {
        return b.getTglsort() - a.getTglsort();
    }
}

public class Toko_Laporan extends javax.swing.JDialog {

    private SimpleDateFormat formatTanggal;
    private Connect connection;

    private Date tempDate, tglHariIni, awal,akhir;
    private String awal1, akhir1;

    public Toko_Laporan() {
//        super(parent, modal);
        initComponents();

        tglHariIni = new Date();
        formatTanggal = new SimpleDateFormat("yyyy-MM-dd");
        tgl_awal.setDate(tglHariIni);
        tgl_akhir.setDate(tglHariIni);
        cek();

    }

    public Toko_Laporan(java.awt.Frame parent, boolean modal, Connect connection) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);

        this.connection = connection;
//        list = new ArrayList<loadq>();


    }

    //tanggal dengan format yyyy-MM-dd (untuk database)
    private String getCurrentDate() {
        String date = "";
        java.util.Date d = new java.util.Date();
        return date;
    }

    //tanggal dengan format dd-MM-yyyy
    private String getCurrentDate2() {
        String date2 = "";
        java.util.Date d = new java.util.Date();
        return date2;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        tgl_awal = new com.toedter.calendar.JDateChooser();
        tgl_akhir = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        comNoFaktur = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1386, 768));

        jCheckBox1.setText("Tanggal");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        jLabel1.setText("s.d");

        jButton1.setText("Lihat");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButton1KeyTyped(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 801, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 391, Short.MAX_VALUE)
        );

        tgl_awal.setDateFormatString("dd-MM-yyyy");
        tgl_awal.getDateEditor().addPropertyChangeListener(new PropertyChangeListener()
            {
                @Override
                public void propertyChange(PropertyChangeEvent e)
                {
                    if ("date".equals(e.getPropertyName()))
                    {
                        tempDate = (Date) e.getNewValue();
                        formatTanggal.format(tempDate);
                        System.out.println(e.getPropertyName()
                            + ": " + (Date) e.getNewValue());
                        comNoFaktur.removeAllItems();
                        comNoFaktur.addItem("-- Masukkan No Faktur Toko --");
                        boolean status_fokus_awal = tgl_akhir.requestFocusInWindow();
                        System.out.print(status_fokus_awal);
                    }
                }
            });
            tgl_awal.setEnabled(false);
            tgl_awal.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    tgl_awalKeyPressed(evt);
                }
            });

            tgl_akhir.setDateFormatString("dd-MM-yyyy");
            tgl_akhir.getJCalendar().setMinSelectableDate(tempDate);
            tgl_akhir.setEnabled(false);
            tgl_akhir.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    tgl_akhirPropertyChange(evt);
                }
            });
            tgl_akhir.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    tgl_akhirKeyPressed(evt);
                }
            });

            jLabel4.setText("Kriteria");

            comNoFaktur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Masukkan No Faktur Toko --" }));
            comNoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    comNoFakturKeyTyped(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jCheckBox1)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tgl_awal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comNoFaktur, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jButton1)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox1)
                            .addComponent(jLabel1)
                            .addComponent(jButton1))
                        .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(comNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        lihat();
        
    }//GEN-LAST:event_jButton1ActionPerformed
/**/
    private void lihat(){
        Date awalDalem = tgl_awal.getDate();
        Date akhirDalem = tgl_akhir.getDate();
        HashMap params = new HashMap<String, Date>();
        String awal1Dalem = formatTanggal.format(awalDalem);
        String akhir1Dalem = formatTanggal.format(akhirDalem);
        boolean status = jCheckBox1.isSelected();
        String query;
        
        if (status) {
            params.put("awal",awalDalem);
            params.put("akhir", akhirDalem);
            String faktur = comNoFaktur.getSelectedItem().toString();
            if(faktur.equals("-- Masukkan No Faktur Toko --")){
                query = "select\n" +
                " tpd.no_faktur_toko_penjualan,\n" +
                " b.kode_barang,\n" +
                " b.nama_barang,\n" +
                " tpd.jumlah_barang,\n" +
                " kv.nama_konversi,\n" +
                " tpd.harga_barang,\n" +
                " dp.hpp,\n" +
                " bkv.jumlah_konversi,\n" +
                " DATE_FORMAT(tp.tgl_toko_penjualan, '%Y-%m-%d') tgl_penjualan1\n" +
                " from toko_penjualan_detail tpd\n" +
                " join toko_penjualan tp on tp.no_faktur_toko_penjualan = tpd.no_faktur_toko_penjualan\n" +
                " join barang b on b.kode_barang=tpd.kode_barang\n" +
                " join barang_konversi bkv on tpd.kode_barang_konversi = bkv.kode_barang_konversi\n" +
                " join konversi kv on kv.kode_konversi=bkv.kode_konversi\n" +
                " JOIN detail_pembelian dp on dp.kode_barang = b.kode_barang\n" +
                " where tp.tgl_toko_penjualan between \""+awal1Dalem+"\" and \""+akhir1Dalem+"\"\n" +
                " GROUP BY tpd.no_faktur_toko_penjualan, b.kode_barang";
            }else{
                query = "select\n" +
                " tpd.no_faktur_toko_penjualan,\n" +
                " b.kode_barang,\n" +
                " b.nama_barang,\n" +
                " tpd.jumlah_barang,\n" +
                " kv.nama_konversi,\n" +
                " tpd.harga_barang,\n" +
                " dp.hpp,\n" +
                " bkv.jumlah_konversi,\n" +
                " DATE_FORMAT(tp.tgl_toko_penjualan, '%Y-%m-%d') tgl_penjualan1\n" +
                " from toko_penjualan_detail tpd\n" +
                " join toko_penjualan tp on tp.no_faktur_toko_penjualan = tpd.no_faktur_toko_penjualan\n" +
                " join barang b on b.kode_barang=tpd.kode_barang\n" +
                " join barang_konversi bkv on tpd.kode_barang_konversi = bkv.kode_barang_konversi\n" +
                " join konversi kv on kv.kode_konversi=bkv.kode_konversi\n" +
                " JOIN detail_pembelian dp on dp.kode_barang = b.kode_barang\n" +
                " where tp.tgl_toko_penjualan between \""+awal1Dalem+"\" and \""+akhir1Dalem+"\"\n" +
                " and tpd.no_faktur_toko_penjualan = \""+faktur+"\"GROUP BY tpd.no_faktur_toko_penjualan, b.kode_barang";
            }
        }else{
            String faktur = comNoFaktur.getSelectedItem().toString();
            if(faktur.equals("-- Masukkan No Faktur Toko --")){
                query = "select\n" +
                " tpd.no_faktur_toko_penjualan,\n" +
                " b.kode_barang,\n" +
                " b.nama_barang,\n" +
                " tpd.jumlah_barang,\n" +
                " kv.nama_konversi,\n" +
                " tpd.harga_barang,\n" +
                " dp.hpp,\n" +
                " bkv.jumlah_konversi,\n" +
                " DATE_FORMAT(tp.tgl_toko_penjualan, '%Y-%m-%d') tgl_penjualan1\n" +
                " from toko_penjualan_detail tpd\n" +
                " join toko_penjualan tp on tp.no_faktur_toko_penjualan = tpd.no_faktur_toko_penjualan\n" +
                " join barang b on b.kode_barang=tpd.kode_barang\n" +
                " join barang_konversi bkv on tpd.kode_barang_konversi = bkv.kode_barang_konversi\n" +
                " join konversi kv on kv.kode_konversi=bkv.kode_konversi\n" +
                " JOIN detail_pembelian dp on dp.kode_barang = b.kode_barang\n" +
                " where DATE_FORMAT(tp.tgl_toko_penjualan, '%Y-%m-%d') = DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d')\n" +
                " GROUP BY tpd.no_faktur_toko_penjualan, b.kode_barang";
            }else{
                query = "select\n" +
                " tpd.no_faktur_toko_penjualan,\n" +
                " b.kode_barang,\n" +
                " b.nama_barang,\n" +
                " tpd.jumlah_barang,\n" +
                " kv.nama_konversi,\n" +
                " tpd.harga_barang,\n" +
                " dp.hpp,\n" +
                " bkv.jumlah_konversi,\n" +
                " DATE_FORMAT(tp.tgl_toko_penjualan, '%Y-%m-%d') tgl_penjualan1\n" +
                " from toko_penjualan_detail tpd\n" +
                " join toko_penjualan tp on tp.no_faktur_toko_penjualan = tpd.no_faktur_toko_penjualan\n" +
                " join barang b on b.kode_barang=tpd.kode_barang\n" +
                " join barang_konversi bkv on tpd.kode_barang_konversi = bkv.kode_barang_konversi\n" +
                " join konversi kv on kv.kode_konversi=bkv.kode_konversi\n" +
                " JOIN detail_pembelian dp on dp.kode_barang = b.kode_barang\n" +
                " where DATE_FORMAT(tp.tgl_toko_penjualan, '%Y-%m-%d') = DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d')\n" +
                " and tpd.no_faktur_toko_penjualan = \""+faktur+"\"GROUP BY tpd.no_faktur_toko_penjualan, b.kode_barang";
            }
        }
        String Path_Laporan = "/src/Laporan/Toko_Laporan_Final.jrxml";
        InputStream is = null;
            try {
                is = getClass().getResourceAsStream(Path_Laporan);
            Connect koneksi = new Connect();
           Connection conn = koneksi.conn;
            JasperDesign jd =JRXmlLoader.load(new File("").getAbsolutePath()+Path_Laporan);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(query);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            loadNoFaktur();
                System.out.println("awaaal ===== "+params.get("awal"));
            JasperPrint print = JasperFillManager.fillReport(jr, params,conn);
            //JasperPrintManager.printReport(print, false);
            JasperViewer viewer = new JasperViewer(print);
            System.out.println("sukses");
            jPanel2.removeAll();
            jPanel2.setLayout(new BorderLayout());
            jPanel2.repaint();
            jPanel2.add(new JRViewer(print));

            jPanel2.revalidate();
                System.out.println(query);
            
        } catch (JRException ex) {
            Logger.getLogger(Toko_Laporan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadNoFaktur(String dt1, String dt2) {
        System.out.println("load faktur");
        try {
            String sql="select\n" +
                "tpd.no_faktur_toko_penjualan\n" +
                "from toko_penjualan_detail tpd\n" +
                "join toko_penjualan tp on tp.no_faktur_toko_penjualan = tpd.no_faktur_toko_penjualan\n" +
                "join barang b on b.kode_barang=tpd.kode_barang\n" +
                "join barang_konversi bkv on tpd.kode_barang_konversi = bkv.kode_barang_konversi\n" +
                "join konversi kv on kv.kode_konversi=bkv.kode_konversi\n" +
                "JOIN detail_pembelian dp on dp.kode_barang = b.kode_barang\n" +
                "where tp.tgl_toko_penjualan between \""+dt1+"\" and \""+dt2+"\"\n" +
                "GROUP BY tpd.no_faktur_toko_penjualan";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(1);
                comNoFaktur.addItem(name);
            }
            boolean status_akhir = comNoFaktur.requestFocusInWindow();
            System.out.println(status_akhir);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }
    private void loadNoFaktur() {
        try {
            String sql="select\n" +
                " tpd.no_faktur_toko_penjualan\n" +
                " from toko_penjualan_detail tpd\n" +
                " join toko_penjualan tp on tp.no_faktur_toko_penjualan = tpd.no_faktur_toko_penjualan\n" +
                " join barang b on b.kode_barang=tpd.kode_barang\n" +
                " join barang_konversi bkv on tpd.kode_barang_konversi = bkv.kode_barang_konversi\n" +
                " join konversi kv on kv.kode_konversi=bkv.kode_konversi\n" +
                " JOIN detail_pembelian dp on dp.kode_barang = b.kode_barang\n" +
                " where DATE_FORMAT(tp.tgl_toko_penjualan, '%Y-%m-%d') = DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d')\n" +
                " GROUP BY tpd.no_faktur_toko_penjualan";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            System.out.println("Sql hari ini = \n"+sql);
            while (res.next()) {
                String name = res.getString(1);
                comNoFaktur.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            System.out.println("error");
        }

    }
    
    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        if (jCheckBox1.isSelected()) {
            tgl_awal.setEnabled(true);
            tgl_akhir.setEnabled(true);
            System.out.println("cekbox enabled");
        }else{
            tgl_awal.setEnabled(false);
            tgl_akhir.setEnabled(false);
            tgl_awal.setDate(tglHariIni);
            tgl_akhir.setDate(tglHariIni);
            cek();
        }
        comNoFaktur.removeAllItems();
        comNoFaktur.addItem("-- Masukkan No Faktur Toko --");
        loadNoFaktur();
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    
    private void cek(){
        String query = "select\n" +
            " tpd.no_faktur_toko_penjualan,\n" +
            " b.kode_barang,\n" +
            " b.nama_barang,\n" +
            " tpd.jumlah_barang,\n" +
            " kv.nama_konversi,\n" +
            " tpd.harga_barang,\n" +
            " dp.hpp,\n" +
            " bkv.jumlah_konversi,\n" +
            " DATE_FORMAT(tp.tgl_toko_penjualan, '%Y-%m-%d') tgl_penjualan1\n" +
            " from toko_penjualan_detail tpd\n" +
            " join toko_penjualan tp on tp.no_faktur_toko_penjualan = tpd.no_faktur_toko_penjualan\n" +
            " join barang b on b.kode_barang=tpd.kode_barang\n" +
            " join barang_konversi bkv on tpd.kode_barang_konversi = bkv.kode_barang_konversi\n" +
            " join konversi kv on kv.kode_konversi=bkv.kode_konversi\n" +
            " JOIN detail_pembelian dp on dp.kode_barang = b.kode_barang\n" +
            " where DATE_FORMAT(tp.tgl_toko_penjualan, '%Y-%m-%d') = DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d')\n" +
            " GROUP BY tpd.no_faktur_toko_penjualan, b.kode_barang";
        System.out.println("Sql hari ini \n"+query);
        loadNoFaktur();
        loadLaporan(query);
    }
    
    
    private void tgl_akhirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tgl_akhirPropertyChange
        if ("date".equals(evt.getPropertyName())) 
        {
            comNoFaktur.removeAllItems();
            comNoFaktur.addItem("-- Masukkan No Faktur Toko --");
            awal = tgl_awal.getDate();
            akhir = (Date) evt.getNewValue();
            awal1 = formatTanggal.format(awal);
            akhir1 = formatTanggal.format(akhir);
            
            System.out.println(evt.getPropertyName()
                + ": " + (Date) evt.getNewValue());
            loadNoFaktur(awal1,akhir1);
            
        }        
    }//GEN-LAST:event_tgl_akhirPropertyChange

    private void tgl_awalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_awalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tgl_akhir.requestFocusInWindow();
            System.out.println(tgl_akhir.requestFocus(true));
        }
    }//GEN-LAST:event_tgl_awalKeyPressed

    private void tgl_akhirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_akhirKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            comNoFaktur.requestFocusInWindow();
        }
    }//GEN-LAST:event_tgl_akhirKeyPressed

    private void comNoFakturKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comNoFakturKeyTyped
        if (evt.getKeyCode()==0) {
            jButton1.requestFocusInWindow();
        }
        System.out.println(evt.getKeyCode()+" = "+KeyEvent.VK_ENTER);
    }//GEN-LAST:event_comNoFakturKeyTyped

    private void jButton1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyTyped
        if (evt.getKeyCode()==0) {
            lihat();
        }
    }//GEN-LAST:event_jButton1KeyTyped

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
            java.util.logging.Logger.getLogger(Toko_Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Toko_Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Toko_Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Toko_Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Toko_Laporan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comNoFaktur;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private com.toedter.calendar.JDateChooser tgl_akhir;
    private com.toedter.calendar.JDateChooser tgl_awal;
    // End of variables declaration//GEN-END:variables

    private void loadLaporan(String query) {
        String PathLaporan = "/src/Laporan/Toko_Laporan_Final.jrxml";
        InputStream is = getClass().getResourceAsStream(PathLaporan);
        try {
            //is = getClass().getResourceAsStream(PathLaporan);
            Connect koneksi = new Connect();
            Connection conn = koneksi.conn;
            System.out.println("File = "+new File("").getAbsolutePath()+PathLaporan);
            JasperDesign jd =JRXmlLoader.load(new File("").getAbsolutePath()+PathLaporan);
            
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(query);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint print = JasperFillManager.fillReport(jr, null,conn);
            //JasperPrintManager.printReport(print, false);
            JasperViewer viewer = new JasperViewer(print);
            System.out.println("sukses");
            jPanel2.removeAll();
            jPanel2.setLayout(new BorderLayout());
            jPanel2.repaint();
            jPanel2.add(new JRViewer(print));
////            show_report.repaint();
            jPanel2.revalidate();

        } catch (JRException ex) {
            Logger.getLogger(Toko_Laporan.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(this, ex.getMessage()+ " file = + "+new File("").getAbsolutePath()+PathLaporan);
        }
    }
}
