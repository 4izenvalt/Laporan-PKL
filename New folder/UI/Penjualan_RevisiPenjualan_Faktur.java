/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Connect;
import Java.koneksi;
//import com.sun.glass.events.KeyEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author USER
 */
public class Penjualan_RevisiPenjualan_Faktur extends javax.swing.JFrame {

    private Java.koneksi koneksi;
    private Connection con;
    private Connect connection;
    public String totalclone;
    public Float Tempharga;
    private String no_faktur_order = "";
    public int subtotalfix = 0;
    int kode_barang = 0;
    String kov;
    public int subtotal1 = 0, hargajadi1 = 0, totalqty = 0, total = 0; //penjumlahan
    public int jumlah = 0, hargaRekom = 0, Jharga = 0, subtotal = 0, hargajadi = 0;//panggil colom tabel
    public int qty = 0;
    public int jumlahqty;

    public Penjualan_RevisiPenjualan_Faktur() {
        initComponents();
        this.setLocationRelativeTo(null);
//        AutoCompleteDecorator.decorate(comCustomer);
////        AutoCompleteDecorator.decorate(comSatuan);
        AutoCompleteDecorator.decorate(comTableBarang);
//        AutoCompleteDecorator.decorate(comTableKonv);
//        AutoCompleteDecorator.decorate(comTableLokasi);
//        AutoCompleteDecorator.decorate(comTableHarga);
        loadCustomer();
        loadComOrder();
        jLabel2.setVisible(false);
//        tanggal_jam_sekarang();
        loadComTableBarang();
        loadComOrder();
        loadComTableLokasi();
        loadLihatDetail();
        loginpegawai();
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);

//        loadNumberTable();
        AturlebarKolom();
        jumlahitem();

    }

    public Penjualan_RevisiPenjualan_Faktur(String no_faktur_order, boolean update) {
        initComponents();
        this.no_faktur_order = no_faktur_order;
        this.setLocationRelativeTo(null);
//        AutoCompleteDecorator.decorate(comCustomer);
//        AutoCompleteDecorator.decorate(comSatuan);
//        AutoCompleteDecorator.decorate(comTableBarang);
//        AutoCompleteDecorator.decorate(comTableKonv);
//        AutoCompleteDecorator.decorate(comTableLokasi);
//        AutoCompleteDecorator.decorate(comTableHarga);
        loadCustomer();
        loadComOrder();
        loginpegawai();
//        tanggal_jam_sekarang();
        loadComTableBarang();
        loadComOrder();
        loadComTableLokasi();
        loadLihatDetail();
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);
//        loadNumberTable();
        AturlebarKolom();
        jumlahitem();

    }

    public Penjualan_RevisiPenjualan_Faktur(Connect connection) {
        this.connection = connection;
        initComponents();
        this.setLocationRelativeTo(null);
        //       AutoCompleteDecorator.decorate(comCustomer);
//        AutoCompleteDecorator.decorate(comSatuan);
//        AutoCompleteDecorator.decorate(comTableBarang);
//        AutoCompleteDecorator.decorate(comTableKonv);
//        AutoCompleteDecorator.decorate(comTableLokasi);
//        AutoCompleteDecorator.decorate(comTableHarga);
        loadCustomer();
        loadComOrder();
        loginpegawai();
//        tanggal_jam_sekarang();
        loadComTableBarang();
        loadComOrder();
        loadLihatDetail();
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);
        loadComTableLokasi();

//        loadNumberTable();
        AturlebarKolom();
        jumlahitem();

    }

    void jumlahitem() {
        try {
            String sql = "SELECT COUNT(no_faktur_order) FROM order_detail WHERE no_faktur_order='" + no_faktur_order + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            System.out.println(sql);

            while (res.next()) {
                jTextField21.setText(res.getString("COUNT(no_faktur_order)"));
            }
        } catch (Exception e) {
        }
    }

    void loadCustomer() {

        try {
            String sql = "select * from customer";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String nama = res.getString(2);
//                comCustomer.addItem(nama);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loginpegawai() {
        try {
            String sql = "SELECT kode_pegawai, nama_pegawai from pegawai where kode_pegawai='3'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                jTextField18.setText(res.getString("nama_pegawai"));
            }
        } catch (Exception e) {
        }
    }

    void total() {
        int jumlahBaris = jTable2.getRowCount();
        int totalBiaya;
        totalBiaya = 0;
        int jumlahtotal, jumlahqty;
        TableModel tabelModel;
        tabelModel = jTable2.getModel();
        for (int i = 0; i < jumlahBaris; i++) {
            jumlahtotal = Integer.parseInt(tabelModel.getValueAt(i, 10).toString());
            jumlahqty = Integer.parseInt(tabelModel.getValueAt(i, 5).toString());
            totalBiaya = totalBiaya + jumlahtotal;
//        qty = qty + jumlahqty ;
//            System.out.println(totalBiaya);

//        tabelModel.setValueAt(i, 10).equals(totalBiaya);
//        tabelModel.setValueAt(totalBiaya, i, 10);
            jTextField20.setText("" + totalBiaya);
            jTextField22.setText("" + qty);
        }
       // jTextField20.setText(String.valueOf(totalBiaya));

    }

    void loadLihatDetail() {

        String jenis_harga = null;
        int rekom_harga = 0;
        int totalBiaya;
        totalBiaya = 0;
        int jumlahBarang, hargaBarang, totalakhir, jumlahtotal, konversi;
        totalakhir = 0;

        try {
            String sql = "SELECT barang_konversi.jumlah_konversi,barang.harga_jual_1_barang,barang.harga_jual_2_barang,barang.harga_jual_3_barang,p.kode_order, order_detail.id_order_detail, "
                    + "order_detail.kode_barang, order_detail.nama_barang_edit, "
                    + "lokasi.nama_lokasi, order_detail.jumlah_barang, "
                    + "order_detail.jenis_harga,order_detail.harga_revisi, barang_konversi.kode_konversi, "
                    + "konversi.nama_konversi, penjualan.tgl_penjualan,penjualan.status_verifikasi,"
                    + " p.no_faktur_order, top.nama_top, salesman.nama_salesman,customer.kota_customer, "
                    + "customer.nama_customer,customer.alamat_customer \n"
                    + "from `order` AS p INNER JOIN penjualan ON penjualan.no_faktur_order = p.no_faktur_order \n"
                    + "LEFT OUTER JOIN top ON p.id_top = top.id_top \n"
                    + "LEFT OUTER JOIN salesman ON penjualan.kode_salesman = salesman.kode_salesman \n"
                    + "LEFT OUTER JOIN customer ON p.kode_customer = customer.kode_customer \n"
                    + "LEFT OUTER JOIN order_detail ON p.no_faktur_order = order_detail.no_faktur_order\n"
                    + "LEFT OUTER JOIN lokasi ON order_detail.kode_lokasi = lokasi.kode_lokasi\n"
                    + "INNER JOIN barang_konversi ON order_detail.kode_barang_konversi = barang_konversi.kode_barang_konversi\n"
                    + "INNER JOIN konversi ON barang_konversi.kode_konversi = konversi.kode_konversi\n"
                    + "INNER JOIN barang ON order_detail.kode_barang = barang.kode_barang\n"
                    + "WHERE order_detail.no_faktur_order LIKE '" + no_faktur_order + "'";
            System.out.println(sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            int i = 0;
            int k = 0;
            int tot = 0;
//            int jumlahBaris = jTable2.getRowCount();
//        for (i=0; i<jumlahBaris; i++){
//            jumlahBarang = Integer.parseInt(model.getValueAt(i, 5).toString());
//                hargaBarang = Integer.parseInt(model.getValueAt(i, 7).toString());
//                totalBiaya = totalBiaya + (jumlahBarang * hargaBarang);
//                System.out.println(totalBiaya);
//        }
            while (res.next()) {
                model.setValueAt(i + 1, i, 0);
                jLabel2.setText(res.getString("status_verifikasi"));
                jTextField17.setText(res.getString("no_faktur_order"));
                jTextField16.setText(res.getString("kode_order"));
                jTextField5.setText(res.getString("nama_customer"));
                jTextField6.setText(res.getString("nama_customer"));
                jTextField7.setText(res.getString("alamat_customer"));
                jTextField14.setText(res.getString("tgl_penjualan"));
                jComboBox1.addItem(res.getString("nama_salesman"));
                jLabel3.setText(res.getString("jenis_harga"));
                jComboBox3.addItem(res.getString("nama_top"));
//                jLabel4.setText(res.getString("identitas_konversi"));
                jLabel5.setText(res.getString("id_order_detail"));

                model.setValueAt(res.getString("kode_barang"), i, 1);
                model.setValueAt(res.getString("nama_barang_edit"), i, 2);
                model.setValueAt(res.getString("nama_lokasi"), i, 3);
                model.setValueAt(res.getString("nama_konversi"), i, 4);
                model.setValueAt(res.getString("jumlah_barang"), i, 5);

                if (jLabel3.getText().equals("1")) {
                    jenis_harga = "harga_jual_1_barang";
                } else if (jLabel3.getText().equals("2")) {
                    jenis_harga = "harga_jual_2_barang";
                } else if (jLabel3.getText().equals("3")) {
                    jenis_harga = "harga_jual_3_barang";
                }

                model.setValueAt(res.getString(jenis_harga), i, 6);
                model.setValueAt(res.getString(jenis_harga), i, 7);
                model.setValueAt(res.getString("harga_revisi"), i, 8);

//                    model.setValueAt(res.getString(rekom_harga), i, 8);
                model.setValueAt(res.getString("jumlah_konversi"), i, 9);
                konversi = Integer.parseInt(model.getValueAt(i, 9).toString());
                jumlahBarang = Integer.parseInt(model.getValueAt(i, 5).toString());
                hargaBarang = Integer.parseInt(model.getValueAt(i, 8).toString());
                totalBiaya = (konversi * jumlahBarang) * hargaBarang;
          
                
//                for (k=0; i<model.getRowCount(); k++){

//        tabelModel.setValueAt(i, 10).equals(totalBiaya);
//                model.setValueAt(totalBiaya, i, 10);
//                jumlahtotal = Integer.parseInt(model.getValueAt(i, 10).toString());
//                 totalakhir = totalBiaya + jumlahtotal;
                model.setValueAt(totalBiaya, i, 10);
                System.out.println(totalBiaya);
                totalBiaya = totalBiaya + (jumlahBarang * hargaBarang);
                jTextField20.setText("" + totalBiaya);
                System.out.println("" + totalakhir);
//                 int jumlahBaris = jTable2.getRowCount();
//         TableModel tabelModel;
//        tabelModel = jTable2.getModel();
//        for (int no=0; no<jumlahBaris; no++){
                jumlahqty = Integer.parseInt(model.getValueAt(i, 5).toString());
                qty = qty + jumlahqty;
                jTextField22.setText("" + qty);
//               int jumlahItem = model.getRowCount();
//        jTextField21.setText(String.valueOf(jumlahItem));

//        }
//                jTextField20.setText(""+totalakhir);
//                for(k = 0; k < model.getColumnCount(); k++){
//                    System.out.println(totalBiaya);
//                    jTextField1.setText(res.getString(kode_barang));
//                }
// for( k = 0; k < model.getColumnCount(); k++) {
//        
//        int Amount =Integer.parseInt(model.getValueAt(i,7)+"");
//        tot +=Amount;
//        System.out.println(tot);
//     }
//                    
//                String satuan = res.getString("nama_konversi");
//                model.setValueAt(satuan, i, 4);
//                model.setValueAt(res.getString("qty"), i, 5);
//                model.setValueAt(res.getString("tujuan"), i, 6);
                model.addRow(new Object[]{"", "", "", "", "", "", "", "", "", ""});
                i++;

            }
            // String row_update = jTable2.getRowCount();
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
        if (jLabel2.getText().equals("1")) {
            jCheckBox3.setSelected(true);
        } else {
            jCheckBox3.setSelected(false);
        }
    }
//    void loadComSatuan(){
//        try{
//            String sql = "select * from konversi";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()){
//                String nama = res.getString(2);
//                comSatuan.addItem(nama);
//            }
//        }catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//        }
//}

    void loadComTableBarang() {
        try {
            String sql = "select * from barang order by kode_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(4);
                comTableBarang.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    public void loadComOrder() {
        try {
            String sql = "SELECT * FROM `order` ORDER BY order.no_faktur_order ASC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                Object[] order = new Object[2];
                order[0] = res.getString(2);
//                comOrder.addItem((String) order[0]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void loadComTableLokasi() {
        try {
            String sql = "select * from lokasi";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(2);
//                comTableLokasi.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

//    void loadNumberTable() {
//        int baris = jTable2.getRowCount();
//        for (int a = 0; a < baris; a++) {
//            String nomor = String.valueOf(a + 1);
//            jTable2.setValueAt(nomor + ".", a, 0);
//        }
//
//    }
    void AturlebarKolom() {
        TableColumn column;
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = jTable2.getColumnModel().getColumn(0);
        column.setPreferredWidth(30);
        column = jTable2.getColumnModel().getColumn(1);
        column.setPreferredWidth(50);
        column = jTable2.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        column = jTable2.getColumnModel().getColumn(3);
        column.setPreferredWidth(70);
        column = jTable2.getColumnModel().getColumn(4);
        column.setPreferredWidth(90);
        column = jTable2.getColumnModel().getColumn(5);
        column.setPreferredWidth(50);
        column = jTable2.getColumnModel().getColumn(6);
        column.setPreferredWidth(90);
        column = jTable2.getColumnModel().getColumn(7);
        column.setPreferredWidth(60);
        column = jTable2.getColumnModel().getColumn(8);
        column.setPreferredWidth(90);
        column = jTable2.getColumnModel().getColumn(9);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setWidth(0);
        column.setPreferredWidth(0);
        column = jTable2.getColumnModel().getColumn(10);
        column.setPreferredWidth(90);

    }

//    void BersihField() {
//        txt_faktur.setText("");
//        txt_keterangan.setText("");
//        txt_Staff.setText("");
//    }
//    public void tanggal_jam_sekarang() {
//        Thread p = new Thread() {
//            public void run() {
//                for (;;) {
//                    GregorianCalendar cal = new GregorianCalendar();
//                    int hari = cal.get(Calendar.DAY_OF_MONTH);
//                    int bulan = cal.get(Calendar.MONTH);
//                    int tahun = cal.get(Calendar.YEAR);
//                    int jam = cal.get(Calendar.HOUR_OF_DAY);
//                    int menit = cal.get(Calendar.MINUTE);
////                  int detik = cal.get(Calendar.SECOND);
//                    jTextField14.setText(tahun + " - " + (bulan + 1) + " - " + hari + " " + jam + ":" + menit);
//
//                }
//            }
//        };
//        p.start();
//    }
    static String rptabel(String b) {
        b = b.replace(",", "");
        b = NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.parseDouble(b));
        return b;
    }

    static String rptabelkembali(String b) {
        b = b.replace(",", "");

        return b;
    }

//    public void HitungSemua() {
//        if (jTable2.getRowCount() >= 1) {
//            subtotalfix = 0;
//            for (int i = jTable2.getRowCount() - 1; i > -1; i--) {
//                int x = Integer.parseInt(jTable2.getValueAt(i, 9).toString());
//                subtotalfix += x;
//            }
//            txt_tbl_total.setText(String.valueOf(subtotalfix));
//        }
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comTableBarang = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        JLunas = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextField18 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jTextField19 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jComboBox3 = new javax.swing.JComboBox<String>();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField6 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jCheckBox3 = new javax.swing.JCheckBox();
        jTextField20 = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jComboBox1 = new javax.swing.JComboBox<String>();
        jLabel17 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        comTableBarang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 204));
        jLabel14.setText("Customer");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setText("Total");

        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));

        JLunas.setBackground(new java.awt.Color(153, 0, 0));
        JLunas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        JLunas.setForeground(new java.awt.Color(255, 255, 51));
        JLunas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JLunas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        JLunas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JLunasMouseClicked(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Kasir");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Clear-icon.png"))); // NOI18N
        jLabel20.setText("F9 - Clear");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        jLabel19.setText("F12 - Save");
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel19MousePressed(evt);
            }
        });

        jTextField16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jButton1.setText(">");
        jButton1.setAlignmentY(0.0F);
        jButton1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jTextField17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTextField19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 204));
        jLabel23.setText("Staff");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Keterangan Verifikasi");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        jLabel26.setText("Esc - Exit");

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/forward-icon.png"))); // NOI18N
        jLabel28.setText("Next");

        jCheckBox2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox2.setForeground(new java.awt.Color(153, 0, 0));
        jCheckBox2.setText("NON DENDA");

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 204));
        jLabel16.setText("No. Order");

        jTextField7.setBackground(new java.awt.Color(184, 238, 184));
        jTextField7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(153, 0, 0));
        jCheckBox1.setText("LGSG CETAK");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.white, java.awt.Color.white));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode", "Barang", "Lokasi", "Satuan (1/2/3)", "Jumlah", "J.Harga (1/2/3)", "Harga", "Rekom Harga", "Jml Konverse", "Sub Total"
            }
        ));
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable2KeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jTextField6.setBackground(new java.awt.Color(184, 238, 184));
        jTextField6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jTextField22.setBackground(new java.awt.Color(0, 0, 0));
        jTextField22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField22.setForeground(new java.awt.Color(255, 204, 0));
        jTextField22.setText("Jumlah Qty");
        jTextField22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jTextField21.setBackground(new java.awt.Color(0, 0, 0));
        jTextField21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField21.setForeground(new java.awt.Color(255, 204, 0));
        jTextField21.setText("Jumlah Item");
        jTextField21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jTextField5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 204));
        jLabel24.setText("Tanggal");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Nama Kasir");

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 204));
        jLabel15.setText("No. Faktur");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 204));
        jLabel31.setText("Salesman");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/back-icon.png"))); // NOI18N
        jLabel27.setText("Prev");

        jTextField14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(204, 0, 0));
        jLabel30.setText("Alamat");

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));

        jCheckBox3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox3.setText("Verifikasi Administrasi");
        jCheckBox3.setMargin(new java.awt.Insets(2, 0, 2, 2));

        jTextField20.setBackground(new java.awt.Color(204, 255, 204));
        jTextField20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 204));
        jLabel17.setText("T.O.P");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(204, 0, 0));
        jLabel29.setText("Nama");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N
        jLabel1.setText("Delete");

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel3");

        jLabel4.setText("jLabel4");

        jLabel5.setText("jLabel5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel30))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1))
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel24)
                                            .addComponent(jLabel16))
                                        .addGap(13, 13, 13))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jCheckBox3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel31)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel17)
                                        .addGap(21, 21, 21)
                                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(69, 69, 69)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel4))
                                            .addComponent(jLabel5))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel23)
                                .addGap(25, 25, 25)
                                .addComponent(jTextField18))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel32)
                        .addGap(180, 180, 180)
                        .addComponent(jCheckBox1)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(10, 10, 10)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel20)
                                .addGap(10, 10, 10)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(131, 131, 131)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel28))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(JLunas, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel28))
                                .addGap(8, 8, 8))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(jButton1)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel23)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox3)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JLunas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBox1)
                        .addComponent(jCheckBox2)
                        .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_namaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_namaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaMouseClicked

    private void txt_alamatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_alamatMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_alamatMouseClicked

    private void txt_fakturMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_fakturMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fakturMouseClicked

    private void txt_StaffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_StaffMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_StaffMouseClicked

    private void txt_keteranganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_keteranganMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_keteranganMouseClicked

    private void txt_tbl_totalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_tbl_totalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tbl_totalMouseClicked

    private void txt_itemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_itemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_itemMouseClicked

    private void txt_jumQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_jumQtyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumQtyMouseClicked

    private void lbl_PrintAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lbl_PrintAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_PrintAncestorAdded

    private void lbl_PrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_PrintMouseClicked
//        Penjualan_RevisiPenjualan_Faktur_Print ppp = new Penjualan_RevisiPenjualan_Faktur_Print();
//        ppp.setVisible(true);
    }//GEN-LAST:event_lbl_PrintMouseClicked

    private void JLunasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JLunasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JLunasMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Penjualan_KotakHistoriPenjualan pkhb = new Penjualan_KotakHistoriPenjualan();
        pkhb.setVisible(true);
        pkhb.setFocusable(true);
        this.setFocusable(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void comCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comCustomerActionPerformed
//        try {
//            String sql = "select * from customer where nama_customer = '" + comCustomer.getSelectedItem() + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
//                String namaSales = res.getString(2);
//                String alamat = res.getString(4);
//                txt_nama.setText(namaSales);
//                txt_alamat.setText(alamat);
////              txt_rekSupply.setText(rek);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//        }
    }//GEN-LAST:event_comCustomerActionPerformed

    private void comTableBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableBarangActionPerformed
//        int baris = jTable2.getRowCount();
//        TableModel tabelModel;
//        tabelModel = jTable2.getModel();
//        int selectedRow = jTable2.getSelectedRow();
//
//        try {
//            String sql = "select * from barang where nama_barang = '" + comTableBarang.getSelectedItem() + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
//                String kode = res.getString(1);
//                String lokasi = "Pusat";
//                String testjumlah = "0";
//                if (selectedRow != -1) {
//                    jTable2.setValueAt(kode, selectedRow, 1);
//                    jTable2.setValueAt(lokasi, selectedRow, 3);
//                    jTable2.setValueAt(testjumlah, selectedRow, 5);
//                    jTable2.setValueAt(testjumlah, selectedRow, 8);
//                    jTable2.setValueAt(testjumlah, selectedRow, 9);
//                }
////                loadComSatuan(jTable2.getValueAt(selectedRow, 1).toString());
////               jTable2.setValueAt(loadComSatuan.getSelectedItem(), selectedRow, 3);
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror aaa" + e);
//        }
//          try{
//            String sql = "select l.kode_lokasi, l.nama_lokasi from lokasi l, barang_lokasi bl where bl.kode_barang = '" + kode_barang + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()){
//                String lokasi = res.getString(2);
//                jTable2.setValueAt(lokasi, selectedRow, 3);
//                System.out.println("ret" +lokasi);
//            }
//        }
//        catch(Exception e){System.out.println(e);}
//        comTableHarga.removeAllItems();
//        try {
//            for (int i = 0; i < baris; i++) {
//                kode_barang = Integer.parseInt(tabelModel.getValueAt(i, 1).toString());
//            }
//            String sql = "SELECT harga_jual_1_barang,harga_jual_2_barang,harga_jual_3_barang FROM barang WHERE kode_barang ='" + kode_barang + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
////          String jharga = res.getString(2);
////          int selectedRow = jTable2.getSelectedRow();
//            while (res.next()) {
//                String Harga1 = res.getString(1);
//                comTableHarga.addItem(Harga1);
//                String Harga2 = res.getString(2);
//                comTableHarga.addItem(Harga2);
//                String Harga3 = res.getString(3);
//                comTableHarga.addItem(Harga3);
//                System.out.println("h2: " + Harga2);
//                Tempharga = Float.valueOf(Harga2);
//                jTable2.setValueAt(Harga2, selectedRow, 6);
//            }
//
//        } catch (Exception e) {
//            //           JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
//            //e.printStackTrace();
//        }
//        comTableKonv.removeAllItems();
//        try {
//            String sql = "select k.nama_konversi, k.kode_konversi from konversi k, barang_konversi bk where k.kode_konversi = bk.kode_konversi and bk.kode_barang = '" + kode_barang + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            String Konv = "";
////            System.out.println("test =" +sql);
////            System.out.println("k =" +kode_barang);
////            System.out.println("Select =" + comTableKonv.getSelectedItem());
//            while (res.next()) {
//                Konv = res.getString(1);
//                comTableKonv.addItem(Konv);
//                System.out.println("Konv =" + Konv);
//                jTable2.setValueAt(comTableKonv.getSelectedItem(), selectedRow, 4);
//
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
//            e.printStackTrace();
//        }
//        try {
//        } catch (Exception e) {
//        }


    }//GEN-LAST:event_comTableBarangActionPerformed

    private void comTableHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableHargaActionPerformed

    }//GEN-LAST:event_comTableHargaActionPerformed

    private void tbl_PenjualanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PenjualanKeyPressed
//        int kode_barang = 0;
//        int baris = jTable2.getRowCount();
//        int selectedRow = jTable2.getSelectedRow();
//
//        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter
//            TableModel tabelModel;
//            tabelModel = jTable2.getModel();
//            jumlah = Integer.parseInt(tabelModel.getValueAt(jTable2.getSelectedRow(), 5).toString());
//            System.out.println("Jumlah :" + jumlah);
//            if (comTableHarga.getSelectedIndex() >= -1) {
//                comTableHarga.removeAllItems();
//                Jharga = Integer.parseInt(tabelModel.getValueAt(jTable2.getSelectedRow(), 8).toString());
//                System.out.println("ambil harga field 8: " + Jharga);
//                subtotal1 = jumlah * Jharga;
//                tabelModel.setValueAt(subtotal1, jTable2.getSelectedRow(), 9);
//            }
//            if (tabelModel.getValueAt(jTable2.getSelectedRow(), 8).toString().equals("0")) {
//                hargaRekom = Integer.parseInt(tabelModel.getValueAt(jTable2.getSelectedRow(), 6).toString());
//                System.out.println("ambil harga field 6: " + hargaRekom);
//                subtotal = jumlah * hargaRekom;
//                // Tempharga = Float.valueOf(harga1);
//                tabelModel.setValueAt(subtotal, jTable2.getSelectedRow(), 9);
//            }
//
//            HitungSemua();
//            //if (Integer.parseInt(tabelModel.getValueAt(jTable2.getRowCount(), 9).toString()) != 0) {
//            totalqty += jumlah;
//            model.addRow(new Object[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0",});
//            //}
////
//            txt_item.setText("" + jTable2.getRowCount());
//            txt_jumQty.setText("" + totalqty);
//        }
//        loadNumberTable();
    }//GEN-LAST:event_tbl_PenjualanKeyPressed

    private void tbl_PenjualanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PenjualanKeyReleased

    }//GEN-LAST:event_tbl_PenjualanKeyReleased

    private void comTableKonvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableKonvActionPerformed

    }//GEN-LAST:event_comTableKonvActionPerformed

    private void comOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comOrderActionPerformed

    }//GEN-LAST:event_comOrderActionPerformed

    private void lbl_SaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_SaveMouseClicked
//        try {
//            Koneksi Koneksi = new Koneksi();
//            Connection con = Koneksi.configDB();
//            
//            Statement st = con.createStatement();
//            String sql = "insert into penjualan( no_faktur_pembelian, no_faktur_supplier_pembelian, tgl_penjualan,  tgl_nota_supplier_pembelian,  discon_persen, discon_rp, keterangan_pembelian)"
//                    + "value('" + txt_noNota.getText() + "','" + txt_inv.getText() + "','" + txt_tgl.getText() + "','" + date + "','" + txt_diskon.getText() + "','" + txt_diskonRp.getText() + "','" + txt_ket.getText() + "');";
//           System.out.println(sql);
//            int row = st.executeUpdate(sql);
//
//            if (row == 1) {
//                JOptionPane.showMessageDialog(null, "data sudah ditambahkan ke database", "informasi", JOptionPane.INFORMATION_MESSAGE);
//                con.close();
//            }
//
//            sql = "insert into supplier( nama_supplier, alamat_supplier, rekening_supplier)"
//                    + "value('" + txt_nmSupply.getText() + "','" + txt_almtSupply.getText() + "','" + txt_rekSupply.getText() + "');";
////            System.out.println(sql);
//            st.executeUpdate(sql);
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "data tidak dimasukkan ke database" + e, "informasi", JOptionPane.INFORMATION_MESSAGE);
//        } finally {
//
//        }

    }//GEN-LAST:event_lbl_SaveMouseClicked

    private void txt_jumQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumQtyActionPerformed

    private void txt_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_itemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_itemActionPerformed

//    private void hitungtotal(){
//        int subtotalfix = 0, grandtotal = 0, discount = 0, jumlahItem = 0, jumlahQty = 0;
//        if (jTable2.getRowCount() >= 1) {
//            for (int i = jTable2.getRowCount() - 1; i > -1; i--) {
//                int x = Integer.parseInt(jTable2.getValueAt(i, 10).toString().replace(".0", ""));
//                int y = Integer.parseInt(jTable2.getValueAt(i, 5).toString().replace(".0", ""));
//                int b = jTable2.getRowCount();
////                System.out.println(x);
//                subtotalfix += x;
//                jumlahQty += y;
////                jTextField20.setText("" + b);
//                System.out.println(""+b);
//
//            }
//        }
////        txt_jumQty.setText(String.valueOf(jumlahQty));
//        jTextField20.setText(String.valueOf(subtotalfix));
//        int i = jTable2.getRowCount();
//
//        for (int j = 0; j < i; j++) {
//            jumlahQty += Integer.parseInt(jTable2.getValueAt(j, 5).toString());
//        }
//        jTextField22.setText(String.valueOf(jumlahQty));
//
//        
//    }
    private void jTable2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyReleased
        // TODO add your handling code here:

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        int selectedRow = jTable2.getSelectedRow();
        int baris = jTable2.getRowCount();
        int jumlah = 0, harga = 0, harga_jadi = 0, diskon = 0, diskon1 = 0, diskonp = 0, diskonp1 = 0;
        int qty = 0;
        int subtotal;
        int totalsemua, konversi;
        totalsemua = 0;

        TableModel tabelModel;
        tabelModel = jTable2.getModel();
//        jumlah = Integer.parseInt(tabelModel.getValueAt(jTable2.getSelectedRow(), 5).toString());
//        harga = Integer.parseInt(tabelModel.getValueAt(jTable2.getSelectedRow(), 6).toString());
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter

        jumlah = Integer.parseInt(tabelModel.getValueAt(jTable2.getSelectedRow(), 5).toString());
        harga = Integer.parseInt(tabelModel.getValueAt(jTable2.getSelectedRow(), 8).toString());
        konversi = Integer.parseInt(model.getValueAt(jTable2.getSelectedRow(), 9).toString());
//        diskon = Integer.parseInt(tabelModel.getValueAt(jTable2.getSelectedRow(), 8).toString());
//        diskon1 = Integer.parseInt(tabelModel.getValueAt(jTable2.getSelectedRow(), 10).toString());
//        diskonp = Integer.parseInt(tabelModel.getValueAt(jTable2.getSelectedRow(), 9).toString());
//        diskonp1 = Integer.parseInt(tabelModel.getValueAt(jTable2.getSelectedRow(), 11).toString());
//        tmppcs = getKonvPcs(jTable2.getSelectedRow());
            subtotal = (konversi * jumlah) * harga;
       
//        int subtotal = jumlah * harga ;
//        int diskonrp = subtotal * diskon / 100;
//        int diskonrp1 = subtotal * diskon1 / 100;
//        int hargajadii = subtotal - diskonrp - diskonrp1;
        tabelModel.setValueAt(subtotal, jTable2.getSelectedRow(), 10);
//        hitungtotal();
        total();

//        tabelModel.setValueAt(diskonrp, jTable2.getSelectedRow(), 9);
//        tabelModel.setValueAt(diskonrp1, jTable2.getSelectedRow(), 11);
//        tabelModel.setValueAt(hargajadii, jTable2.getSelectedRow(), 12);
    }//GEN-LAST:event_jTable2KeyReleased

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jLabel19MouseClicked
    void tes(List<Penjualan_RevisiPenjualan_Faktur> list) {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        String sql = "UPDATE order_detail SET harga_revisi = ? WHERE no_faktur_order = ?";
        PreparedStatement statement = null;
        try {
            PreparedStatement p = (PreparedStatement) koneksi.Connect().prepareStatement(sql);
            for (Penjualan_RevisiPenjualan_Faktur dto : list) {
                statement.setString(1, dto.jTable2.getValueAt(jTable2.getSelectedRow(), 8).toString());
                statement.setString(2, dto.jTextField17.getText());
                statement.addBatch();
            }
            int[] result = statement.executeBatch();
            for (int i = 0; i < result.length; i++) {
                if (result[i] == PreparedStatement.EXECUTE_FAILED) {
                    throw new SQLException(String.format("Entry %d failed to execute in the batch insert with a return code of %d.", i, result[i]));
                }
            }
            p.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        } finally {

        }
    }
    private void jLabel19MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MousePressed
        // TODO add your handling code here:

        int jumlahdata = jTable2.getRowCount();
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

        for (int i = 0; i < jumlahdata; i++) {
            try {
                String sql = "UPDATE order_detail set harga_pengajuan='" + model.getValueAt(i, 7) + "', harga_revisi ='" + model.getValueAt(i, 8) + "' where no_faktur_order='" + jTextField17.getText() + "' AND kode_barang ='" + model.getValueAt(i, 1) + "'";
                koneksi = new koneksi();
                PreparedStatement p = (PreparedStatement) koneksi.Connect().prepareStatement(sql);

                p.executeUpdate();

                System.out.print(p);
//                JOptionPane.showMessageDialog(null,"Data sukses di edit");
//                this.dispose();
            } catch (Exception e) {
                System.out.println(e);
            }
        }


    }//GEN-LAST:event_jLabel19MousePressed

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_F12) {
            JOptionPane.showMessageDialog(null, "Data Disimpan");
            int jumlahdata = jTable2.getRowCount();
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

            for (int i = 0; i < jumlahdata; i++) {
                try {
                    String sql = "UPDATE order_detail set harga_pengajuan='" + model.getValueAt(i, 7) + "', harga_revisi ='" + model.getValueAt(i, 8) + "' where no_faktur_order='" + jTextField17.getText() + "' AND kode_barang ='" + model.getValueAt(i, 1) + "'";
                    koneksi = new koneksi();
                    PreparedStatement p = (PreparedStatement) koneksi.Connect().prepareStatement(sql);

                    p.executeUpdate();

                    System.out.print(p);
//                
//                this.dispose();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }//GEN-LAST:event_jTable2KeyPressed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Penjualan_RevisiPenjualan_Faktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_RevisiPenjualan_Faktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_RevisiPenjualan_Faktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_RevisiPenjualan_Faktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan_RevisiPenjualan_Faktur().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JLunas;
    private javax.swing.JComboBox comTableBarang;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    public javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
