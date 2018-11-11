/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class Penjualan_Piutang_Bayar extends javax.swing.JFrame {

    private int potongan = 0;
    private int totalHutang = 0;
    String[] noFaktur;
    int[] hrgItem;
    int jum;

    String fakturBJ = null;
    String nm_cus;
    String nm_sal;

    int keuangan1 = 0;
    int keuangan2 = 0;
    String bank1 = "";
    String bank2 = "";

    /**
     * Creates new form Penjualan_piutangbayar
     */
    public Penjualan_Piutang_Bayar() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public Penjualan_Piutang_Bayar(int totalHutang, String[] noFaktur, int[] hrgItem, int jum, String nm_cus, String nm_sal) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.potongan = potongan;
        this.totalHutang = totalHutang;
        this.noFaktur = noFaktur;
        this.hrgItem = hrgItem;
        this.jum = jum;
        this.nm_cus = nm_cus;
        this.nm_sal = nm_sal;

        txt_blmBayar.setText("" + this.totalHutang);

        date();
        fakturBJ();
        loadbank("*");
        loadbank2("*");
    }

    void loadbank(String param) {
        try {
            String sql = "";
            if (param.equals("*")) {
                sql = "select nama_keuangan, nomor_keuangan from transaksi_nama_keuangan";
            } else {
                sql = "select nama_keuangan, nomor_keuangan from transaksi_nama_keuangan where nama_keuangan!='" + param + "'";
            }
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            comBank1.removeAllItems();
            while (res.next()) {
                String name = res.getString(1) + " | " + res.getString(2);
                comBank1.addItem(name);
//                System.out.println("param: " + param + " nama: " + res.getString(1));
                if (param.equals(res.getString(1))) {
                    comBank1.setSelectedItem(res.getString(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadbank2(String param) {
        try {
            String sql = "";
            if (param.equals("*")) {
                sql = "select nama_keuangan, nomor_keuangan from transaksi_nama_keuangan";
            } else {
                sql = "select nama_keuangan, nomor_keuangan from transaksi_nama_keuangan where nama_keuangan!='" + param + "'";
            }
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            comBank2.removeAllItems();
            while (res.next()) {
                String name = res.getString(1) + " | " + res.getString(2);
                comBank2.addItem(name);
//                System.out.println("param2: " + param + " nama2: " + res.getString(1));
//                if (param.equals(res.getString(1))) {
//                }
            }
            comBank2.setSelectedIndex(1);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void cariBank(int a, String s) {
        String[] split = s.split(" | ");
        System.out.println("s: " + s + " s+: " + split[0]);
        try {
            String sql = "select * from transaksi_nama_keuangan where nama_keuangan='" + split[0] + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
//                String name = res.getString(1)+" | "+res.getString(2);
                if (a == 1) {
                    comBank1.addItem(res.getString(1));
                } else {
                    comBank2.addItem(res.getString(1));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    public void date() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal_tglBG.setDate(cal.getTime());
    }

    public void fakturBJ() {
        try {
            String sql = "select faktur_bp, kode_transaksi_master from transaksi_master "
                    + "WHERE faktur_bp LIKE 'BP%' ORDER BY kode_transaksi_master desc LIMIT 1";
            Connection conn = (Connection) Koneksi.configDB();
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                if (res.first() == false) {
                    System.out.println("BJ18-1");

                } else {
                    res.last();
                    String auto_num = res.getString(1);
                    String huruf = String.valueOf(auto_num.substring(0, 5));
                    int angka = Integer.valueOf(auto_num.substring(5)) + 1;
                    this.fakturBJ = huruf + angka;
                }
            }
            res.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void autoSum() {
        int bayar = 0;
        int bayar2 = 0;
        if (txt_bayar.getText().equals("")) {
            bayar = 0;
        } else {
            bayar = Integer.parseInt(txt_bayar.getText());
        }
        if (txt_bayar2.getText().equals("")) {
            bayar2 = 0;
        } else {
            bayar2 = Integer.parseInt(txt_bayar2.getText());
        }
        int kembali = bayar + bayar2 + (totalHutang + potongan);
        txt_kembali.setText("" + kembali);
    }

    public void bayar() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        //cek keuangan1 & 2
        if (!txt_bayar.getText().toString().equals("")) {
            keuangan1 = Integer.parseInt(txt_bayar.getText().toString());
        } else {
            keuangan1 = 0;
        }
        if (!txt_bayar2.getText().toString().equals("")) {
            keuangan2 = Integer.parseInt(txt_bayar2.getText().toString());
        } else {
            keuangan2 = 0;
        }
        //cek kembalian
        int kembali = Integer.parseInt(txt_kembali.getText().toString());
        if (kembali > 0) {
            if (keuangan2 > 0) {
                keuangan2 -= kembali;
            } else {
                keuangan1 -= kembali;
            }
        }
        //get kode bank
        String[] bank1 = comBank1.getSelectedItem().toString().split(" | ");
        String[] bank2 = comBank2.getSelectedItem().toString().split(" | ");
//        keuangan2 = Integer.parseInt(txt_bayar2.getText().toString());
//        int byrTotal = keuangan1+keuangan2;
        // System.out.println(sdf.format(cal_tglBG.getDate()));
        try {
            Connection conn = (Connection) Koneksi.configDB();
            Statement stm = conn.createStatement();
            Statement stat = conn.createStatement();
            String kdb1 = "", kdb2 = "", kdsls = "";
            //get kode salesman
            String sb1sales = "select s.kode_salesman from customer c, salesman s "
                    + "WHERE c.kode_salesman = s.kode_salesman and c.nama_customer = '" + nm_cus + "' and s.nama_salesman = '" + nm_sal + "'";
            System.out.println("kssls: " + sb1sales);
            ResultSet ressales = stm.executeQuery(sb1sales);
            while (ressales.next()) {
                kdsls = ressales.getString("kode_salesman");
            }
            //get kode bank1
            String sb1 = "select kode_nama_keuangan from transaksi_nama_keuangan "
                    + "WHERE nama_keuangan = '" + bank1[0] + "'";
            ResultSet ressb1 = stm.executeQuery(sb1);
            while (ressb1.next()) {
                kdb1 = ressb1.getString("kode_nama_keuangan");
            }
            //get kode bank 2
            String sb2 = "select kode_nama_keuangan from transaksi_nama_keuangan "
                    + "WHERE nama_keuangan = '" + bank2[0] + "'";
            ResultSet ressb2 = stm.executeQuery(sb2);
            while (ressb2.next()) {
                kdb2 = ressb2.getString("kode_nama_keuangan");
            }
            String sqlA = null, sqlB = null;
            String id_bp = id_oto();
            String notransaksimaster = id_oto_tk();
            for (int i = 0; i < jum; i++) {
                if (comJenisbayar.getSelectedIndex() == 1) {
                    //bg
                    String sql = "select pembaran_udah_bayar as sisa, pembayaran_aktif as bayar, kode_salesman from penjualan  "
                            + "WHERE no_faktur_penjualan = '" + noFaktur[i] + "'";
                    System.out.println("sqla: " + sql);
                    ResultSet resnb = stm.executeQuery(sql);
                    int byr1 = 0, byr2 = 0;
                    String k1 = 0 + "','" + 0 + "','", k2 = 0 + "','" + 0 + "','";
                    String kodeSalesman = "0";
                    while (resnb.next()) {
                        int total = resnb.getInt("bayar");
                        int sisa = -1 * resnb.getInt("sisa");
                        kodeSalesman = resnb.getString("kode_salesman");
                        if (keuangan1 >= 1) {
                            if (sisa == keuangan1) {
                                k1 = kdb1 + "','" + keuangan1 + "','";
                                byr1 = keuangan1;
                                sisa = 0;
                                keuangan1 = 0;
                            } else if (sisa < keuangan1) {
                                k1 = kdb1 + "','" + sisa + "','";
                                byr1 = sisa;
                                keuangan1 -= sisa;
                                sisa = 0;
                            } else {
                                k1 = kdb1 + "','" + keuangan1 + "','";
                                byr1 = keuangan1;
                                sisa -= keuangan1;
                                keuangan1 = 0;
                            }
                        }
                        if (sisa >= 1 && keuangan2 >= 1) {
                            if (sisa == keuangan2) {
                                k2 = kdb2 + "','" + keuangan2 + "','";
                                byr2 = keuangan2;
                                sisa = 0;
                                keuangan2 = 0;
                            } else if (sisa < keuangan2) {
                                k2 = kdb2 + "','" + sisa + "','";
                                byr2 = sisa;
                                keuangan2 -= sisa;
                                sisa = 0;
                            } else {
                                k2 = kdb2 + "','" + keuangan2 + "','";
                                byr2 = keuangan2;
                                sisa -= keuangan2;
                                keuangan2 = 0;
                            }
                        }
                        //update penjualan
                        sqlA = "update penjualan SET tgl_bg_penjualan = '"
                                + sdf.format(cal.getTime())
                                + "', faktur_tempo_bg_penjualan = '" + sdf.format(cal_tglBG.getDate())
                                + "', faktur_bg_penjualan = '" + id_bp
                                + "', kode_pegawai_bg_penjualan = '" + 3
                                + "', no_seri_bg = '" + txtNoseriBG.getText()
                                + "' WHERE no_faktur_penjualan = '" + noFaktur[i] + "'";
                        stat.executeUpdate(sqlA);
                    }
                    sqlB = "INSERT INTO transaksi_master values (" + null + ",'" + id_bp + "','" + txtNoseriBG.getText() + "','" + noFaktur[i] + "',"
                            + "'" + sdf.format(cal.getTime()) + "'"
                            + ",'0','0','0','0','" + txt_ket.getText() + "','"
                            + k1
                            + k2
                            + "3','" + kodeSalesman + "','0000-00-00 00:00:00','0000-00-00','" + sdfd.format(cal_tglBG.getDate()) + "'"
                            + ",'"+notransaksimaster+"')";
                } else {
                    //nobBG
                    String sql = "select pembaran_udah_bayar as sisa, pembayaran_aktif as bayar from penjualan "
                            + "WHERE no_faktur_penjualan = '" + noFaktur[i] + "'";
                    System.out.println("sqla: " + sql);
                    ResultSet resnb = stm.executeQuery(sql);
                    int byr1 = 0, byr2 = 0;
                    String k1 = 0 + "','" + 0 + "','", k2 = 0 + "','" + 0 + "','";
                    while (resnb.next()) {
                        int total = resnb.getInt("bayar");
                        int sisa = -1 * resnb.getInt("sisa");
                        if (keuangan1 >= 1) {
                            if (sisa == keuangan1) {
                                k1 = kdb1 + "','" + keuangan1 + "','";
                                byr1 = keuangan1;
                                sisa = 0;
                                keuangan1 = 0;
                            } else if (sisa < keuangan1) {
                                k1 = kdb1 + "','" + sisa + "','";
                                byr1 = sisa;
                                keuangan1 -= sisa;
                                sisa = 0;
                            } else {
                                k1 = kdb1 + "','" + keuangan1 + "','";
                                byr1 = keuangan1;
                                sisa -= keuangan1;
                                keuangan1 = 0;
                            }
                        }
                        if (sisa >= 1 && keuangan2 >= 1) {
                            if (sisa == keuangan2) {
                                k2 = kdb2 + "','" + keuangan2 + "','";
                                byr2 = keuangan2;
                                sisa = 0;
                                keuangan2 = 0;
                            } else if (sisa < keuangan2) {
                                k2 = kdb2 + "','" + sisa + "','";
                                byr2 = sisa;
                                keuangan2 -= sisa;
                                sisa = 0;
                            } else {
                                k2 = kdb2 + "','" + keuangan2 + "','";
                                byr2 = keuangan2;
                                sisa -= keuangan2;
                                keuangan2 = 0;
                            }
                        }
                        //update penjualan
                        sqlA = "update penjualan SET pembaran_udah_bayar = " + sisa * -1
                                + " WHERE no_faktur_penjualan = '" + noFaktur[i] + "'";
                        stat.executeUpdate(sqlA);
                    }
                    sqlB = "INSERT INTO transaksi_master"
                            + " values (" + null + ",'','','" + noFaktur[i] + "','" + sdf.format(cal.getTime()) + "'"
                            + ",'0'," + (byr1 + byr2) + ",'0','0','" + txt_ket.getText() + "','"
                            + k1
                            + k2
                            + "3','" + kdsls + "','0000-00-00 00:00:00','0000-00-00','0000-00-00','"+notransaksimaster+"')";
                }
                System.out.println("sqla: " + sqlA + "\nsqlb: " + sqlB);
//                Connection conn = (Connection) Koneksi.configDB();
                stat.executeUpdate(sqlB);
            }
//            System.out.println("Jum = " + jum);
            JOptionPane.showMessageDialog(this, "Sukses");
            dispose();
            Penjualan_Piutang a = new Penjualan_Piutang();
            a.tampilTabel("*");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String id_oto() {
        Calendar call = Calendar.getInstance();
        String str = String.valueOf(call.get(Calendar.YEAR));
        String kode = "BJ";
        String tgl_angk = "", th = str.substring(2);
        try {
            String sql = "select faktur_bg_penjualan from penjualan "
                    + "WHERE faktur_bg_penjualan != '' order by faktur_bg_penjualan desc limit 1";
            Connection conn = (Connection) Koneksi.configDB();
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);
            String angka = "";
            while (res.next()) {
                angka = res.getString(1);
            }
            if (angka.substring(2, 4).equalsIgnoreCase(th)) {
                int angkasub = Integer.parseInt(angka.substring(5)) + 1;
                String angkapad = rightPadZeros(String.valueOf(angkasub), 5);
                tgl_angk = th + "-" + angkapad;
            } else {
                tgl_angk = th + "-00001";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String hasil = kode + tgl_angk;
        return hasil;
    }

    public static String id_oto_tk() {
        Calendar call = Calendar.getInstance();
        String str = String.valueOf(call.get(Calendar.YEAR));
        String kode = "TM";
        String tgl_angk = "", th = str.substring(2);
        try {
            String sql = "select no_transaksi_master from transaksi_master "
                    + "WHERE no_transaksi_master != '' order by no_transaksi_master desc limit 1";
            Connection conn = (Connection) Koneksi.configDB();
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);
            String angka = "";
            while (res.next()) {
                angka = res.getString(1);
            }
            if (angka.substring(2, 4).equalsIgnoreCase(th)) {
                int angkasub = Integer.parseInt(angka.substring(5)) + 1;
                String angkapad = rightPadZeros(String.valueOf(angkasub), 5);
                tgl_angk = th + "-" + angkapad;
            } else {
                tgl_angk = th + "-00001";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String hasil = kode + tgl_angk;
        return hasil;
    }

    public static String rightPadZeros(String str, int num) {
        return String.format("%05d", Integer.parseInt(str));
//        return String.format(str,"%1$-" + num + "s").replace(' ', '0');
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel45 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        jSeparator30 = new javax.swing.JSeparator();
        jLabel90 = new javax.swing.JLabel();
        txt_blmBayar = new javax.swing.JTextField();
        jButton53 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        comJenisbayar = new javax.swing.JComboBox<>();
        jLabel92 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        txtNoseriBG = new javax.swing.JTextField();
        jLabel173 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        comBank1 = new javax.swing.JComboBox<>();
        jLabel175 = new javax.swing.JLabel();
        txt_bayar = new javax.swing.JTextField();
        jLabel176 = new javax.swing.JLabel();
        comBank2 = new javax.swing.JComboBox<>();
        jLabel177 = new javax.swing.JLabel();
        txt_ket = new javax.swing.JTextField();
        jLabel178 = new javax.swing.JLabel();
        txt_bayar2 = new javax.swing.JTextField();
        jLabel180 = new javax.swing.JLabel();
        txt_kembali = new javax.swing.JTextField();
        cal_tglBG = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jLabel89.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(102, 102, 102));
        jLabel89.setText("Cara Bayar");

        jSeparator30.setForeground(new java.awt.Color(204, 204, 204));

        jLabel90.setForeground(new java.awt.Color(51, 51, 51));
        jLabel90.setText("Belum Bayar");

        txt_blmBayar.setEditable(false);

        jButton53.setBackground(new java.awt.Color(85, 222, 93));
        jButton53.setText("Bayar");
        jButton53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton53ActionPerformed(evt);
            }
        });

        jButton54.setBackground(new java.awt.Color(153, 153, 153));
        jButton54.setText("Close");
        jButton54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton54ActionPerformed(evt);
            }
        });

        comJenisbayar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No BG", "BG" }));
        comJenisbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comJenisbayarActionPerformed(evt);
            }
        });

        jLabel92.setForeground(new java.awt.Color(51, 51, 51));
        jLabel92.setText("Jenis Bayar");

        jLabel169.setForeground(new java.awt.Color(51, 51, 51));
        jLabel169.setText("No Seri BG");

        txtNoseriBG.setEditable(false);

        jLabel173.setForeground(new java.awt.Color(51, 51, 51));
        jLabel173.setText("Tanggal BG");

        jLabel174.setForeground(new java.awt.Color(51, 51, 51));
        jLabel174.setText("Keuangan");

        comBank1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comBank1ItemStateChanged(evt);
            }
        });
        comBank1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comBank1ActionPerformed(evt);
            }
        });

        jLabel175.setForeground(new java.awt.Color(51, 51, 51));
        jLabel175.setText("Bayar");

        txt_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_bayarKeyTyped(evt);
            }
        });

        jLabel176.setForeground(new java.awt.Color(51, 51, 51));
        jLabel176.setText("Keuangan2");

        comBank2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comBank2ItemStateChanged(evt);
            }
        });
        comBank2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comBank2ActionPerformed(evt);
            }
        });

        jLabel177.setForeground(new java.awt.Color(51, 51, 51));
        jLabel177.setText("Bayar");

        txt_ket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_ketKeyTyped(evt);
            }
        });

        jLabel178.setForeground(new java.awt.Color(51, 51, 51));
        jLabel178.setText("Keterangan");

        txt_bayar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayar2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_bayar2KeyTyped(evt);
            }
        });

        jLabel180.setForeground(new java.awt.Color(51, 51, 51));
        jLabel180.setText("Kembalian");

        txt_kembali.setEditable(false);

        cal_tglBG.setDateFormatString(" yyyy - M - d");
        cal_tglBG.setEnabled(false);

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator30)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addComponent(jLabel89)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addGap(0, 37, Short.MAX_VALUE)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel92)
                            .addComponent(jLabel169)
                            .addComponent(jLabel173)
                            .addComponent(jLabel174)
                            .addComponent(jLabel175)
                            .addComponent(jLabel176)
                            .addComponent(jLabel177)
                            .addComponent(jLabel178)
                            .addComponent(jLabel180)
                            .addComponent(jLabel90))
                        .addGap(74, 74, 74)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel45Layout.createSequentialGroup()
                                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comBank1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_bayar)
                                    .addComponent(comBank2, 0, 205, Short.MAX_VALUE)
                                    .addComponent(txt_bayar2, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                                    .addComponent(txt_ket)
                                    .addComponent(txt_kembali)
                                    .addGroup(jPanel45Layout.createSequentialGroup()
                                        .addComponent(jButton53)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(50, Short.MAX_VALUE))
                            .addGroup(jPanel45Layout.createSequentialGroup()
                                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comJenisbayar, 0, 205, Short.MAX_VALUE)
                                    .addComponent(txtNoseriBG)
                                    .addComponent(txt_blmBayar)
                                    .addComponent(cal_tglBG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 45, Short.MAX_VALUE))))))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_blmBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel90))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comJenisbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel92))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoseriBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel169))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addComponent(jLabel173)
                        .addGap(14, 14, 14)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comBank1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel174))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel175)
                            .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel176)
                            .addComponent(comBank2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel177)
                            .addComponent(txt_bayar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel178)
                            .addComponent(txt_ket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel180))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton53, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cal_tglBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel45, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton53ActionPerformed
        int a = 0, b = 0;
        if (txt_bayar.getText().toString().equals("") && txt_bayar2.getText().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "Kolom bayar jangan kosong.");
            txt_bayar.requestFocus(true);
        } else {
            bayar();
//            this.dispose();
            JOptionPane.showMessageDialog(null, "Pembayaran hutang berhasil.");
        }
    }//GEN-LAST:event_jButton53ActionPerformed

    private void jButton54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton54ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton54ActionPerformed

    private void comJenisbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comJenisbayarActionPerformed
        if (comJenisbayar.getSelectedItem().toString().equals("BG")) {
            txtNoseriBG.setEditable(true);
            cal_tglBG.setEnabled(true);
        } else {
            txtNoseriBG.setEditable(false);
            cal_tglBG.setEnabled(false);
        }
    }//GEN-LAST:event_comJenisbayarActionPerformed

    private void txt_bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyReleased
        autoSum();
    }//GEN-LAST:event_txt_bayarKeyReleased

    private void txt_bayar2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayar2KeyReleased
        autoSum();
    }//GEN-LAST:event_txt_bayar2KeyReleased

    private void txt_bayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyTyped
        char vChar = evt.getKeyChar();
        if (!(Character.isDigit(vChar)
                || (vChar == KeyEvent.VK_BACK_SPACE)
                || (vChar == KeyEvent.VK_DELETE))) {
            evt.consume();
        }
        if (vChar == KeyEvent.VK_ENTER) {
            comBank2.requestFocus(true);
        }
    }//GEN-LAST:event_txt_bayarKeyTyped

    private void txt_bayar2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayar2KeyTyped
        char vChar = evt.getKeyChar();
        if (!(Character.isDigit(vChar)
                || (vChar == KeyEvent.VK_BACK_SPACE)
                || (vChar == KeyEvent.VK_DELETE))) {
            evt.consume();
        }
        if (vChar == KeyEvent.VK_ENTER) {
            txt_ket.requestFocus(true);
        }
    }//GEN-LAST:event_txt_bayar2KeyTyped

    private void txt_ketKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ketKeyTyped
        char vChar = evt.getKeyChar();
        if (vChar == KeyEvent.VK_ENTER) {
            jButton53.requestFocus(true);
        }
    }//GEN-LAST:event_txt_ketKeyTyped

    private void comBank1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comBank1ItemStateChanged
//        System.out.println("combank11111111: "+comBank1.getItemAt(0));
//        if (comBank1.getItemAt(0) != null) {
//            String[] bank1 = comBank1.getSelectedItem().toString().split(" | ");
//            loadbank2(bank1[0]);
//        } else {
//            loadbank2("*");
//        }
    }//GEN-LAST:event_comBank1ItemStateChanged

    private void comBank2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comBank2ItemStateChanged
//        System.out.println("combank222222222: "+comBank2.getItemAt(0));
//        if (comBank2.getItemAt(0) != null) {
//            String[] bank2 = comBank2.getSelectedItem().toString().split(" | ");
//            loadbank(bank2[0]);
//        } else {
//            loadbank("*");
//        }
    }//GEN-LAST:event_comBank2ItemStateChanged

    private void comBank1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comBank1ActionPerformed
//        System.out.println("combank11111111: "+comBank1.getSelectedItem().toString());
//        if (comBank1.getSelectedItem().toString()!=null) {
//            String[] bank1 = comBank1.getSelectedItem().toString().split(" | ");
//            loadbank2(bank1[0]);
//        }else{
//            loadbank2("*");
//        }
    }//GEN-LAST:event_comBank1ActionPerformed

    private void comBank2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comBank2ActionPerformed
//        System.out.println("combank222222222: "+comBank2.getSelectedItem().toString());
//        if (comBank2.getSelectedItem().toString()!=null) {
//            String[] bank2 = comBank2.getSelectedItem().toString().split(" | ");
//            loadbank(bank2[0]);
//        }else{
//            loadbank("*");
//        }
    }//GEN-LAST:event_comBank2ActionPerformed

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
            java.util.logging.Logger.getLogger(Penjualan_Piutang_Bayar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Piutang_Bayar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Piutang_Bayar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Piutang_Bayar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan_Piutang_Bayar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser cal_tglBG;
    private javax.swing.JComboBox<String> comBank1;
    private javax.swing.JComboBox<String> comBank2;
    private javax.swing.JComboBox<String> comJenisbayar;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JTextField txtNoseriBG;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_bayar2;
    private javax.swing.JTextField txt_blmBayar;
    private javax.swing.JTextField txt_kembali;
    private javax.swing.JTextField txt_ket;
    // End of variables declaration//GEN-END:variables
}
