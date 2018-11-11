/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import static UI.Pembelian_Transaksi.rightPadZeros;
import static UI.Pembelian_Transaksi.rptabel;
import com.sun.glass.events.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author User
 */
public class Pembelian_Return extends javax.swing.JFrame {

    public String totalclone, kov;
    ;
    public int tmpnomor;
    public Float Tempharga;
    public float subtotal1 = 0, hargajadi1 = 0, diskonke21 = 0, totalqty = 0, total = 0, diskon = 0, diskonp1 = 0, diskonp11, diskonp21 = 0; //penjumlahan
    public float jumlah = 0, harga = 0, subtotal = 0, diskonp = 0, diskonrp = 0, diskonp2 = 0, diskonrp2 = 0, hargajadi = 0;//panggil colom tabel
    public float qty = 0;
    double totalharga = 0, subtotalfix = 0;
    public int i = 0, kode_barang = 0, nmtotal = 0;
    public TableModel tabelModel;
    String no_retur = "", tmpKonv, tmpKodeKonv;

    public Pembelian_Return() {
        initComponents();
        this.setVisible(true);
        AutoCompleteDecorator.decorate(comFakturBeli);
        AutoCompleteDecorator.decorate(comSupplier);
        AutoCompleteDecorator.decorate(comTableBarang);
        loadComboJenis();
        loadComTableBarang();
        tanggal_jam_sekarang();
        loadComTableLokasi();
        AturlebarKolom();
        autonumber();
        loadNumberTable();

    }

    Pembelian_Return(String tgl, String no_faktur) {
        initComponents();
        this.setVisible(true);
        loadComboJenis();
        loadComTableBarang();
        loadComTableLokasi();
        loadSupplier();
        comFakturBeli.setEnabled(false);
        comFakturBeli.setEditable(false);

        this.no_retur = no_faktur;
        this.txt_tanggal.setText(tgl);
        this.txt_noRetur.setText(no_retur);

        loadLihatDetail();

    }

    public void loadLihatDetail() {
        System.out.println("----------");
        //SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            String sql = "SELECT p.no_faktur_return_pakai,dp.no_faktur_pembelian, p.tgl_return_pakai, l.nama_lokasi,"
                    + "p.kode_supplier, p.keterangan_return_pakai,p.biaya_return, s.nama_supplier, s.alamat_supplier, dp.kode_barang, dp.nama_barang_edit, dp.harga_pembelian, dp.kode_barang_konversi, k.nama_konversi, bk.kode_barang_konversi,"
                    + "dp.kode_lokasi, dp.jumlah_barang "
                    + "FROM pembelian_return_pakai p JOIN supplier s ON p.kode_supplier=s.kode_supplier "
                    + "JOIN pembelian_detail_return_pakai dp ON p.no_faktur_return_pakai=dp.no_faktur_return_pakai "
                    + "JOIN lokasi l ON dp.kode_lokasi=l.kode_lokasi "
                    + "JOIN barang_konversi bk ON bk.kode_barang_konversi=dp.kode_barang_konversi "
                    + "JOIN konversi k ON bk.kode_konversi=k.kode_konversi "
                    + "WHERE p.no_faktur_return_pakai='" + no_retur + "'";

            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            System.out.println(" " + sql);
            DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
            int i = 0;
            double total_harga = 0;
            while (res.next()) {

                model.setValueAt(i + 1, i, 0);
                //comTableLokasi.setSelectedItem(res.getString("nama_lokasi"));
                System.out.println("lokasinya : " + res.getString("nama_lokasi"));
                comSupplier.setSelectedItem(res.getString("nama_supplier"));
                txt_namaSupply.setText(res.getString("nama_supplier"));
                txt_almtSupply.setText(res.getString("alamat_supplier"));
                comFakturBeli.setSelectedItem(res.getString("no_faktur_pembelian"));
//                txt_diskonPersen.setText(res.getString("discon_persen"));
//                txt_diskonRp.setText(res.getString("discon_rp"));
                Double jml_barang = Double.parseDouble(res.getString("jumlah_barang"));
                System.out.println("jml :" + jml_barang);

                txt_Keterangan.setText(res.getString("keterangan_return_pakai"));
                double harga = Double.parseDouble(res.getString("harga_pembelian"));
                double subtotal = 0;
                subtotal += (harga * jml_barang);

//                double diskon1 = Double.parseDouble(res.getString("discon2_persen"));
//                double diskonp = Double.parseDouble(res.getString("discon_rp"));
//                double diskonp1 = Double.parseDouble(res.getString("discon2_rp"));
//                double diskon = Double.parseDouble(res.getString("discon_persen"));
//                double diskonn = ((diskon + diskon1) * subtotal / 100);
//                double hargajadii = subtotal - diskonn - diskonp - diskonp1;
//                total_harga+= hargajadii;
//                txt_total.setText(""+total_harga);
                model.setValueAt(res.getString("kode_barang"), i, 1);
                model.setValueAt(res.getString("nama_barang_edit"), i, 2);
                model.setValueAt(res.getString("nama_lokasi"), i, 3);
                String satuan = res.getString("nama_konversi");
                model.setValueAt(satuan, i, 4);
                model.setValueAt(res.getString("jumlah_barang"), i, 5);
                model.setValueAt(res.getString("harga_pembelian"), i, 6);
                model.setValueAt(subtotal, i, 7);
//                model.setValueAt(res.getString("discon_persen"), i, 8);
//                model.setValueAt(res.getString("discon_rp"), i, 9);
//                model.setValueAt(res.getString("discon2_persen"), i, 10);
//                model.setValueAt(res.getString("discon2_rp"), i, 11);
//                model.setValueAt(hargajadii, i, 12);
//                
                model.addRow(new Object[]{"", "", "", "", "", "", "", "", "", "", "", "", ""});
                i++;
            }
            model.removeRow(i);
            conn.close();
            res.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror 192" + e);
        }

    }

    public void loadComOrder() {
        try {
            String sql = "select * from customer where nama_customer = '" + comFakturBeli.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                Object[] order = new Object[2];
                order[0] = res.getString(2);
                comFakturBeli.addItem((String) order[0]);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    public void loadComFakturBeli() {
        try {
            String sql = "SELECT * FROM `pembelian` ORDER BY `no_faktur_pembelian` ASC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                Object[] order = new Object[3];
                order[0] = res.getString(3);
                comFakturBeli.addItem((String) order[0]);
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
                comTableLokasi.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadComTableBarang() {
        try {
            String sql = "select * from barang order by nama_barang asc";
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

    void loadComboJenis() {
//        System.out.println(comJenis.getSelectedItem());
        if (comJenis.getSelectedItem().equals("BY FAKTUR BELI")) {
            autonumber();
            loadComFakturBeli();
            comSupplier.removeAllItems();
            comSupplier.setEnabled(false);
            txt_namaSupply.setEnabled(false);
            txt_namaSupply.setText("");
            txt_almtSupply.setEnabled(false);
            txt_almtSupply.setText("");
            comFakturBeli.setEnabled(true);
        } else if (comJenis.getSelectedItem().equals("FAKTUR BEBAS")) {
            loadSupplier();
            comFakturBeli.removeAllItems();
            comFakturBeli.setEnabled(false);
            txt_tanggal.setText("");
            txt_noRetur.setText("");
            comSupplier.setEnabled(true);
            txt_namaSupply.setEnabled(true);
            txt_almtSupply.setEnabled(true);

        }
    }

    void loadSupplier() {

        try {
            String sql = "select * from supplier";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(2);
                comSupplier.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    public void tanggal_jam_sekarang() {
        Thread p = new Thread() {
            public void run() {
                for (;;) {
                    GregorianCalendar cal = new GregorianCalendar();
                    int hari = cal.get(Calendar.DAY_OF_MONTH);
                    int bulan = cal.get(Calendar.MONTH);
                    int tahun = cal.get(Calendar.YEAR);
                    int jam = cal.get(Calendar.HOUR_OF_DAY);
                    int menit = cal.get(Calendar.MINUTE);
                    int detik = cal.get(Calendar.SECOND);
                    txt_tanggal.setText(tahun + " - " + (bulan + 1) + " - " + hari + " " + jam + ":" + menit);

                }
            }
        };
        p.start();
    }

    void loadNumberTable() {
        int baris = tbl_Pembelian.getRowCount();
        for (int a = 0; a < baris; a++) {
            String nomor = String.valueOf(a + 1);
            tbl_Pembelian.setValueAt(nomor + ".", a, 0);
        }

    }

    public void autonumber() {
        try {
            String lastNo = "";
            String sql = "select max(no_faktur_return_pakai) from pembelian_detail_return_pakai ORDER BY no_faktur_return_pakai DESC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                if (res.first() == false) {
                    txt_noRetur.setText("RB");

                } else {
                    res.last();
                    String auto_num = res.getString(1);

                    int noLama = Integer.parseInt(auto_num.substring(auto_num.length() - 4));
                    ++noLama;
                    String no = String.valueOf(noLama);
                    no = Integer.toString(noLama);

                    if (no.length() == 1) {
                        lastNo = "0000" + no;
                    } else if (no.length() == 2) {
                        lastNo = "000" + no;
                    } else if (no.length() == 3) {
                        lastNo = "00" + no;
                    } else if (no.length() == 4) {
                        lastNo = "0" + no;
                    } else {
                        lastNo = "00001";
                    }

                    int num = Integer.parseInt(lastNo);
                    String huruf = String.valueOf(auto_num.substring(0, 5));
                    num = Integer.valueOf(auto_num.substring(5)) + 0;
                    String angkapad = rightPadZeros(String.valueOf(++num), 5);
                    txt_noRetur.setText(String.valueOf(huruf + "" + angkapad));

//                    res.last();
//                    String auto_num = res.getString(1);
//                    String no = String.valueOf(auto_num);
//                    //  int noLong = no.length();
//                    //MENGATUR jumlah 0
//                    String huruf = String.valueOf(auto_num.substring(1, 5));
//                    int angka = Integer.valueOf(auto_num.substring(5)) + 1;
//                    txt_noRetur.setText(String.valueOf(huruf + "" + angka));
                }
            }
            res.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }

    void AturlebarKolom() {
        TableColumn column;
        tbl_Pembelian.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tbl_Pembelian.getColumnModel().getColumn(0);
        column.setPreferredWidth(30);
        column = tbl_Pembelian.getColumnModel().getColumn(1);
        column.setPreferredWidth(40);
        column = tbl_Pembelian.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        column = tbl_Pembelian.getColumnModel().getColumn(3);
        column.setPreferredWidth(70);
        column = tbl_Pembelian.getColumnModel().getColumn(4);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(5);
        column.setPreferredWidth(50);
        column = tbl_Pembelian.getColumnModel().getColumn(6);
        column.setPreferredWidth(120);
        column = tbl_Pembelian.getColumnModel().getColumn(7);
        column.setPreferredWidth(140);
        column = tbl_Pembelian.getColumnModel().getColumn(8);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(9);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(10);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(11);
        column.setPreferredWidth(100);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comTableBarang = new javax.swing.JComboBox<>();
        comTableKonv = new javax.swing.JComboBox<>();
        comTableLokasi = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txt_noRetur = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel32 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        labelSave = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel69 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel70 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel71 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        txt_namaSupply = new javax.swing.JTextField();
        txt_almtSupply = new javax.swing.JTextField();
        txt_Keterangan = new javax.swing.JTextField();
        comJenis = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Pembelian = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txt_diskonPersen = new javax.swing.JTextField();
        txt_diskonRp = new javax.swing.JTextField();
        comFakturBeli = new javax.swing.JComboBox<>();
        txt_tanggal = new javax.swing.JTextField();
        comSupplier = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        comTableBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableBarangActionPerformed(evt);
            }
        });

        comTableKonv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableKonvActionPerformed(evt);
            }
        });

        comTableLokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableLokasiActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel22.setText("Supplier");

        txt_noRetur.setEnabled(false);
        txt_noRetur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_noReturMouseClicked(evt);
            }
        });
        txt_noRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_noReturKeyPressed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel24.setText("Alamat");

        jLabel19.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel19.setText("Jenis");

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(153, 0, 0));
        jCheckBox1.setText("Cetak LSG");

        jLabel32.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel32.setText("Nama Kasir");

        jLabel29.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel29.setText("Kasir");

        txt_total.setBackground(new java.awt.Color(204, 255, 204));
        txt_total.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_total.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_totalMouseClicked(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel30.setText("Total");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setText("No.Beli");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setText("Tgl. Retur");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setText("No.Retur");

        jLabel35.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel35.setText("Nama");

        labelSave.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        labelSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        labelSave.setText("F12-Save");
        labelSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelSaveMouseClicked(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel69.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Clear-icon.png"))); // NOI18N
        jLabel69.setText("F9-Clear");
        jLabel69.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel69MouseClicked(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel70.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_yast_printer_30297.png"))); // NOI18N
        jLabel70.setText("Print");

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel71.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        jLabel71.setText("Esc-Exit");
        jLabel71.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel71MouseClicked(evt);
            }
        });

        txt_namaSupply.setBackground(new java.awt.Color(204, 255, 204));
        txt_namaSupply.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_namaSupplyMouseClicked(evt);
            }
        });
        txt_namaSupply.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_namaSupplyKeyPressed(evt);
            }
        });

        txt_almtSupply.setBackground(new java.awt.Color(204, 255, 204));
        txt_almtSupply.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_almtSupplyMouseClicked(evt);
            }
        });
        txt_almtSupply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_almtSupplyActionPerformed(evt);
            }
        });
        txt_almtSupply.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_almtSupplyKeyPressed(evt);
            }
        });

        txt_Keterangan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_KeteranganMouseClicked(evt);
            }
        });
        txt_Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_KeteranganKeyPressed(evt);
            }
        });

        comJenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BY FAKTUR BELI", "FAKTUR BEBAS" }));
        comJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comJenisActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel26.setText("Keterangan");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N
        jLabel1.setText("Delete");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        tbl_Pembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No.", "Kode", "Barang", "Lokasi", "Satuan", "Jumlah", "Harga", "Sub Total", "Diskon %", "Diskon Rp", "Diskon-2 %", "Diskon-2 Rp", "Harga Jadi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false, false, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Pembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_PembelianMouseClicked(evt);
            }
        });
        tbl_Pembelian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_PembelianKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_PembelianKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Pembelian);
        if (tbl_Pembelian.getColumnModel().getColumnCount() > 0) {
            tbl_Pembelian.getColumnModel().getColumn(0).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(1).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comTableBarang));
            tbl_Pembelian.getColumnModel().getColumn(3).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comTableLokasi));
            tbl_Pembelian.getColumnModel().getColumn(4).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comTableKonv));
            tbl_Pembelian.getColumnModel().getColumn(5).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(6).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(7).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(8).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(9).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(10).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(11).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(12).setResizable(false);
        }

        jLabel27.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel27.setText("Diskon Rp");

        jLabel28.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel28.setText("Diskon %");

        txt_diskonPersen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_diskonPersenMouseClicked(evt);
            }
        });
        txt_diskonPersen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_diskonPersenKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_diskonPersenKeyTyped(evt);
            }
        });

        txt_diskonRp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_diskonRpMouseClicked(evt);
            }
        });
        txt_diskonRp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_diskonRpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_diskonRpKeyTyped(evt);
            }
        });

        comFakturBeli.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kode Pembelian" }));
        comFakturBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comFakturBeliActionPerformed(evt);
            }
        });

        txt_tanggal.setEnabled(false);

        comSupplier.setEditable(true);
        comSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Nama Suplier" }));
        comSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comSupplierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_noRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabel19))
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel33))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(comJenis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(comFakturBeli, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel24)
                            .addComponent(jLabel35)
                            .addComponent(jLabel26))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_Keterangan, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_almtSupply, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(comSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_namaSupply, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addGap(19, 19, 19)
                                        .addComponent(txt_diskonRp, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addGap(23, 23, 23)
                                        .addComponent(txt_diskonPersen, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(96, 96, 96))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel69)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel70)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel71)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel32)
                                .addGap(220, 220, 220)
                                .addComponent(jCheckBox1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator2)
                                .addComponent(labelSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator4)
                                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator3))
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel19)
                                .addComponent(comJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(2, 2, 2))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(comSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel28)
                            .addGap(2, 2, 2))
                        .addComponent(txt_diskonPersen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(comFakturBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_noRetur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_namaSupply, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel35)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_diskonRp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_almtSupply, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addGap(13, 13, 13)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel32)
                    .addComponent(jCheckBox1))
                .addGap(46, 46, 46))
        );

        jMenuBar1.setPreferredSize(new java.awt.Dimension(0, 0));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        jMenuItem1.setText("Save");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        jMenuItem2.setText("Clear");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem3.setText("Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 574, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1059, 603));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comFakturBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comFakturBeliActionPerformed
        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        try {
            hapussemuatabel();
            String sql = "SELECT dp.id_detail_pembelian, p.no_faktur_pembelian, p.tgl_pembelian, l.nama_lokasi, p.kode_supplier, "
                    + "p.keterangan_pembelian, p.biaya_pembayaran, s.nama_supplier, s.alamat_supplier, s.rekening_supplier, "
                    + "dp.kode_barang, dp.nama_barang_edit, dp.harga_pembelian, dp.harga_asli,dp.harga_asli_discon, dp.kode_konversi, k.nama_konversi, "
                    + "dp.kode_lokasi, dp.jumlah_barang, p.discon_persen, dp.discon_rp, dp.discon2_persen, dp.discon2_rp, "
                    + "p.biaya_pembelian, p.biaya_pembayaran FROM pembelian p JOIN supplier s ON p.kode_supplier=s.kode_supplier "
                    + "JOIN detail_pembelian dp ON p.no_faktur_pembelian=dp.no_faktur_pembelian "
                    + "JOIN lokasi l ON dp.kode_lokasi=l.kode_lokasi JOIN konversi k ON dp.kode_konversi=k.kode_konversi "
                    + "WHERE p.no_faktur_pembelian='" + comFakturBeli.getSelectedItem().toString() + "'";
//            String sql = "SELECT * FROM detail_pembelian dp, konversi k, lokasi l "
//                    + "WHERE dp.kode_konversi = k.kode_konversi "
//                    + "AND dp.kode_lokasi = l.kode_lokasi "
//                    + "AND dp.no_faktur_pembelian = '" + comFakturBeli.getSelectedItem().toString() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                comSupplier.setSelectedItem(res.getString("nama_supplier"));
                txt_namaSupply.setText(res.getString("nama_supplier"));
                txt_almtSupply.setText(res.getString("alamat_supplier"));
                int tmpnomor = tbl_Pembelian.getRowCount() + 1;
                int kode = res.getInt("kode_barang");
                String nama = res.getString("nama_barang_edit");
                String lokasi = res.getString("nama_lokasi");
                String konv = res.getString("nama_konversi");
                int jml = res.getInt("jumlah_barang");
                int harga = res.getInt("harga_pembelian");
                int subtotal = res.getInt("harga_asli");
                int disc1persen = res.getInt("discon_persen");
                int disc1 = res.getInt("discon_rp");
                int disc2persen = res.getInt("discon_persen");
                int disc2 = res.getInt("discon_rp");
                int hargajadi = res.getInt("harga_asli_discon");

                model.insertRow(tbl_Pembelian.getRowCount(), new Object[]{tmpnomor, kode, nama, lokasi, konv, "0", harga, "0", disc1persen, disc1, disc2persen, disc2, "0",});

//                tbl_Pembelian.setValueAt(kode, selectedRow, 1);
//                tbl_Pembelian.setValueAt(nama, selectedRow, 2);
//                tbl_Pembelian.setValueAt(lokasi, selectedRow, 3);
//                tbl_Pembelian.setValueAt(konv, selectedRow, 4);
//                tbl_Pembelian.setValueAt(jml, selectedRow, 5);
//                tbl_Pembelian.setValueAt(harga, selectedRow, 6);
//                tbl_Pembelian.setValueAt(disc1persen, selectedRow, 8);
//                tbl_Pembelian.setValueAt(disc1, selectedRow, 9);
//                tbl_Pembelian.setValueAt(disc2persen, selectedRow, 10);
//                tbl_Pembelian.setValueAt(disc2, selectedRow, 11);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }

    }//GEN-LAST:event_comFakturBeliActionPerformed

    void hapussemuatabel() {
        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        for (int i = tbl_Pembelian.getRowCount() - 1; i > -1; i--) {
            model.removeRow(i);

        }
    }

    private void txt_diskonRpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonRpKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            txt_Keterangan.requestFocus();
        }
    }//GEN-LAST:event_txt_diskonRpKeyPressed

    private void txt_diskonRpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_diskonRpMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_diskonRpMouseClicked

    private void txt_diskonPersenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonPersenKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            txt_diskonRp.requestFocus();
        }
    }//GEN-LAST:event_txt_diskonPersenKeyPressed

    private void txt_diskonPersenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_diskonPersenMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_diskonPersenMouseClicked

    private void tbl_PembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_PembelianMouseClicked
        if (evt.getClickCount() == 2) {
            Pembelian_KotakHistoriBarang kb = new Pembelian_KotakHistoriBarang();
            kb.setVisible(true);
            kb.setLocationRelativeTo(null);
        }

//        if (kb.isEnabled()) {
//           kb.requestFocus();
//        }
    }//GEN-LAST:event_tbl_PembelianMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        int hapus = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin Menghapus data ini ?", "Konfimasi Tolak Hapus Data", JOptionPane.OK_CANCEL_OPTION);
        if (hapus == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Data sudah dihapus.");
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void txt_KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_KeteranganKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            txt_diskonPersen.requestFocus();
        }
    }//GEN-LAST:event_txt_KeteranganKeyPressed

    private void txt_KeteranganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_KeteranganMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_KeteranganMouseClicked

    private void txt_almtSupplyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_almtSupplyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_almtSupplyKeyPressed

    private void txt_almtSupplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_almtSupplyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_almtSupplyActionPerformed

    private void txt_almtSupplyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_almtSupplyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_almtSupplyMouseClicked

    private void txt_namaSupplyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_namaSupplyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaSupplyKeyPressed

    private void txt_namaSupplyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_namaSupplyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaSupplyMouseClicked

    private String getKodeKonv(int baris) {
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        tmpKonv = tbl_Pembelian.getValueAt(baris, 4).toString();
        try {

            String sql = "SELECT * FROM `barang_konversi` WHERE nama_konversi LIKE '" + tmpKonv + "'";

            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tmpKodeKonv = res.getString("kode_barang_konversi");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
        return tmpKodeKonv;
    }

    private void labelSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelSaveMouseClicked
        int simpan = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin Menyimpan data ini ?", "Konfimasi simpan Data", JOptionPane.OK_CANCEL_OPTION);
        if (simpan == JOptionPane.OK_OPTION) {
            try {
                Koneksi Koneksi = new Koneksi();
                Connection con = Koneksi.configDB();
                Statement st = con.createStatement();
                //memasukkan data
                String sql = "insert into pembelian_return_pakai ( no_faktur_return_pakai, tgl_return_pakai, "
                        //+ "tgl_nota_supplier_pembelian, biaya_pembelian, biaya_pembayaran, discon_persen, discon_rp, "
                        + "keterangan_return_pakai )"
                        + "value('" + txt_noRetur.getText() + "','"
                        + txt_tanggal.getText() + "','"
                        + txt_Keterangan.getText() + "','"
                        + "" + "'"
                        + ");";

                int dbq = st.executeUpdate(sql);

                int baris = tbl_Pembelian.getRowCount();
                System.out.println("baris = " + baris);
                TableModel tabelModel;
                tabelModel = tbl_Pembelian.getModel();

                for (int i = 0; i < baris; i++) {
                    if (tbl_Pembelian.getValueAt(i, 6) != "0") {
                        sql = "insert into pembelian_detail_return_pakai (no_faktur_return_pakai, no_faktur_pembelian, kode_barang, nama_barang_edit, "
                                + "kode_lokasi, kode_barang_konversi, jumlah_barang, jumlah_per_pcs, harga_pembelian "
                                + ")"
                                + "value" + "('" + txt_noRetur.getText() + "','" + comFakturBeli.getSelectedItem().toString() + "','"
                                + tbl_Pembelian.getValueAt(i, 1).toString() + "','"
                                + tbl_Pembelian.getValueAt(i, 2).toString() + "','"
                                //                                + getKodeLokasi(i) + "','"
                                //                                + getKodeKonv(i) + "','"
                                + tbl_Pembelian.getValueAt(i, 5).toString() + "','"
                                //                                + getKonvPcs(i) + "','"
                                + tbl_Pembelian.getValueAt(i, 6).toString() + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 7).toString() + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 12).toString() + "','"
                                //                                + getHargaBeli(i) + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 6).toString() + "','"
                                //                                + tmpHJ1 + "','"
                                //                                + tmpHJ2 + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 8).toString() + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 9).toString() + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 10).toString() + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 11).toString() + "','"
                                + "')";

                        int dbq2 = st.executeUpdate(sql);

                    }
//                    if (dbq2 == 1) {
//                        JOptionPane.showMessageDialog(null, "data barang ke " + (i + 1) + " sudah ditambahkan ke database", "informasi", JOptionPane.INFORMATION_MESSAGE);
//                    }
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "data tidak dimasukkan ke database" + e, "informasi", JOptionPane.INFORMATION_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Data sudah disimpan.");
        }
    }//GEN-LAST:event_labelSaveMouseClicked

    private void txt_totalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_totalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalMouseClicked

    private void txt_noReturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_noReturKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_noReturKeyPressed

    private void txt_noReturMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_noReturMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_noReturMouseClicked

    private void comSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comSupplierActionPerformed
        try {
            String sql = "select * from supplier where nama_supplier = '" + comSupplier.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String nama = res.getString(2);
                String alamat = res.getString(3);
                txt_namaSupply.setText(nama);
                txt_almtSupply.setText(alamat);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
//        if (comSupplier.getSelectedItem().equals("")) {
//            hapussemuatabel();
//        }else {
//        try {
//            hapussemuatabel();
//            String sql = "SELECT * FROM detail_pembelian dp, pembelian p, supplier s, konversi k, lokasi l "
//                    + "WHERE dp.kode_konversi = k.kode_konversi "
//                    + "AND dp.kode_lokasi = l.kode_lokasi "
//                    + "AND dp.no_faktur_pembelian = p.no_faktur_pembelian "
//                    + "AND p.kode_supplier = s.kode_supplier "
//                    + "AND s.nama_supplier LIKE '" + comSupplier.getSelectedItem().toString() + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
//                int tmpnomor = tbl_Pembelian.getRowCount() + 1;
//                int kode = res.getInt("kode_barang");
//                String nama = res.getString("nama_barang_edit");
//                String lokasi = res.getString("nama_lokasi");
//                String konv = res.getString("nama_konversi");
//                int jml = res.getInt("jumlah_barang");
//                int harga = res.getInt("harga_pembelian");
//                int subtotal = res.getInt("harga_asli");
//                int disc1persen = res.getInt("discon_persen");
//                int disc1 = res.getInt("discon_rp");
//                int disc2persen = res.getInt("discon_persen");
//                int disc2 = res.getInt("discon_rp");
//                int hargajadi = res.getInt("harga_asli_discon");
//
//                model.insertRow(tbl_Pembelian.getRowCount(), new Object[]{tmpnomor, kode, nama, lokasi, konv, jml, harga, subtotal, disc1persen, disc1, disc2persen, disc2, hargajadi,});
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//            e.printStackTrace();
//        }
////        }

    }//GEN-LAST:event_comSupplierActionPerformed

    private void comTableBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableBarangActionPerformed
        int baris = tbl_Pembelian.getRowCount();

        tabelModel = tbl_Pembelian.getModel();
        try {
            String sql = "select * from barang where nama_barang = '" + comTableBarang.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString(1);
                String harga = res.getString(11);
                String jumlah = "0";
                //String Konv = "";

                int selectedRow = tbl_Pembelian.getSelectedRow();
                if (selectedRow != -1) {
                    tbl_Pembelian.setValueAt(kode, selectedRow, 1);
                    //tbl_Pembelian.setValueAt(Konv, selectedRow, 4);
                    tbl_Pembelian.setValueAt(jumlah, selectedRow, 5);
                    tbl_Pembelian.setValueAt(harga, selectedRow, 6);
                    tbl_Pembelian.setValueAt(jumlah, selectedRow, 8);
                    tbl_Pembelian.setValueAt(jumlah, selectedRow, 9);
                    tbl_Pembelian.setValueAt(jumlah, selectedRow, 10);
                    tbl_Pembelian.setValueAt(jumlah, selectedRow, 11);
                    Tempharga = Float.valueOf(harga);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror aaa" + e);
        }
        comTableKonv.removeAllItems();
        try {
            for (int i = 0; i < baris; i++) {
                kode_barang = Integer.parseInt(tabelModel.getValueAt(i, 1).toString());
//                if (kode_barang != 0) {
//                    System.out.println("Kosong boss");
//                } else {
//                    System.out.println("eror nggak = " + kode_barang);
//                }

            }
            // System.out.println("ambil kode barang : " + kode_barang);
            String sql = "select k.kode_konversi, k.nama_konversi from konversi k, barang_konversi bk where k.kode_konversi = bk.kode_konversi and bk.kode_barang = '" + kode_barang + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String Konv = res.getString(2);
                comTableKonv.addItem(Konv);
            }
//            if (comTableKonv.getSelectedIndex() == 1) {
//                System.out.println("Satu");
//            }
//            if (comTableKonv.getSelectedIndex() == 2) {
//                System.out.println("Dua ");

        } catch (Exception e) {
//                      System.out.println("Data didalam tabel belum di isi !"+e);
        }

    }//GEN-LAST:event_comTableBarangActionPerformed

    private void comJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comJenisActionPerformed
        loadComboJenis();
    }//GEN-LAST:event_comJenisActionPerformed

    private void txt_diskonPersenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonPersenKeyTyped
        char angka = evt.getKeyChar();
        if (!(Character.isDigit(angka))) {
            evt.consume();
        }
        char enter = evt.getKeyChar();
        if (txt_diskonPersen.getText().length() > 2) {
            evt.consume();
            // JOptionPane.showMessageDialog(null, "Maaf Harus Angka ");
        }
        if (!(Character.isDigit(enter))) {
            evt.consume();
        }

    }//GEN-LAST:event_txt_diskonPersenKeyTyped

    private void txt_diskonRpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonRpKeyTyped
        char angka = evt.getKeyChar();
        if (!(Character.isDigit(angka))) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_diskonRpKeyTyped

    private void tbl_PembelianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PembelianKeyPressed
//        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
//        tabelModel = tbl_Pembelian.getModel();
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter
//
//            float baris = tbl_Pembelian.getRowCount();
//
//            for (i = 0; i < baris; i++) {
//
//                if (tabelModel.getValueAt(i, 8).toString().equals("")) {
//                    diskonp = 0;
//
//                } else {
//                    diskonp = Float.parseFloat(tabelModel.getValueAt(i, 8).toString());
//
//                }
//                if (tabelModel.getValueAt(i, 9).toString().equalsIgnoreCase("")) {
//                    diskonrp = 0;
//                } else {
//                    diskonrp = Float.parseFloat(tabelModel.getValueAt(i, 9).toString());
//                }
//                if (tabelModel.getValueAt(i, 10).toString().equals("")) {
//                    diskonp2 = 0;
//                } else {
//                    diskonp2 = Float.parseFloat(tabelModel.getValueAt(i, 10).toString());
//                }
//                if (tabelModel.getValueAt(i, 11).toString().equals("")) {
//                    diskonrp2 = 0;
//                } else {
//                    diskonrp2 = Float.parseFloat(tabelModel.getValueAt(i, 11).toString());
//                }
//                if (tabelModel.getValueAt(i, 5).toString().equals("")) {
//                    jumlah = 1;
//                } else {
//                    jumlah = Float.parseFloat(tabelModel.getValueAt(i, 5).toString());
//                }
////              
//                harga = Float.parseFloat(tabelModel.getValueAt(i, 6).toString());
//
//                subtotal1 = jumlah * harga;
//
////                    diskonp11 =  ((subtotal * diskonp)/100);
////                    diskonp21 =  ((subtotal * diskonp2)/100);
//                diskon = ((diskonp + diskonp2) * subtotal1 / 100);
//
//                hargajadi1 = subtotal1 - diskon - diskonrp - diskonrp2;
//                System.out.println("diskonp : " + diskonp);
//                total += hargajadi1;
//                totalqty += jumlah;
//            }
//            model.setValueAt(rptabel(String.valueOf(subtotal1)), i - 1, 7);
//            model.setValueAt(rptabel(String.valueOf(hargajadi1)), i - 1, 12);
//            model.setValueAt(rptabel(String.valueOf(harga)), i - 1, 6);
////                model.setValueAt(rptabel(String.valueOf(diskonp)), i-1, 9);
////                 model.setValueAt(rptabel(String.valueOf(diskonp2)), i-1, 11); 
//            model.setValueAt((String.valueOf(harga)), i - 1, 6);
////                   model.setValueAt(rptabelkembali(String.valueOf(diskonp)), i-1, 9); 
////                   model.setValueAt(rptabelkembali(String.valueOf(diskonp2)), i-1, 11); 
////            }
//            model.addRow(new Object[]{"", "", "", "", "", "", "", "", "", "", "", "", ""});
//            txt_total.setText("" + total);
//        }
//        loadNumberTable();
////     
    }//GEN-LAST:event_tbl_PembelianKeyPressed

    private void comTableKonvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableKonvActionPerformed
        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        int baris = tbl_Pembelian.getRowCount();
        tabelModel = tbl_Pembelian.getModel();
        try {
            for (int i = 0; i < baris; i++) {
                kode_barang = Integer.parseInt(tabelModel.getValueAt(i, 1).toString());
            }
            String sql = "select * from barang_konversi where kode_barang ='" + kode_barang + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            System.out.println("Kode" + kode_barang);
            while (res.next()) {
                String konvTemp = res.getString(2);
                int selectedRow = tbl_Pembelian.getSelectedRow();
                if ((comTableKonv.getSelectedIndex() != 0) && ((comTableKonv.getSelectedIndex() + 1) == res.getInt(5))) {
                    float temp = Tempharga * res.getInt(4);
                    model.setValueAt(String.valueOf(temp), 0, 6);
                    System.out.println(">0 " + temp);
                } else {
                    model.setValueAt(String.valueOf(Tempharga), 0, 6);
                    System.out.println("0 " + Tempharga);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }//GEN-LAST:event_comTableKonvActionPerformed

    private void comTableLokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableLokasiActionPerformed
        try {
            String sql = "select * from lokasi where nama_lokasi = '" + comTableLokasi.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String id = res.getString(2);

                int selectedRow = tbl_Pembelian.getSelectedRow();
                if (selectedRow != -1) {
                    tbl_Pembelian.setValueAt(id, selectedRow, 3);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }//GEN-LAST:event_comTableLokasiActionPerformed

    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        int selectedRow = tbl_Pembelian.getSelectedRow();
        int baris = tbl_Pembelian.getRowCount();

        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter
            try {
                String sql = "SELECT * FROM detail_pembelian dp, konversi k, lokasi l "
                        + "WHERE dp.kode_konversi = k.kode_konversi "
                        + "AND dp_kode_lokasi = l.kode_lokasi "
                        + "AND no_faktur_pembelian = '" + comFakturBeli.getSelectedItem().toString() + "'";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    tbl_Pembelian.setValueAt("dp.kode_barang", selectedRow, 1);
                    tbl_Pembelian.setValueAt("dp.nama_barang", selectedRow, 2);
                    tbl_Pembelian.setValueAt("l.nama_lokasi", selectedRow, 3);
                    tbl_Pembelian.setValueAt("k.nama_konversi", selectedRow, 4);
                    tbl_Pembelian.setValueAt("dp.jumlah_barang", selectedRow, 5);
                    tbl_Pembelian.setValueAt("dp.harga_barang", selectedRow, 6);
                    tbl_Pembelian.setValueAt("dp.discon_persen", selectedRow, 8);
                    tbl_Pembelian.setValueAt("dp.discon_rp", selectedRow, 9);
                    tbl_Pembelian.setValueAt("dp.discon_persen", selectedRow, 10);
                    tbl_Pembelian.setValueAt("dp.discon_rp", selectedRow, 11);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eror" + e);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jPanel1KeyPressed

    public void HitungSubTotal() {
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        int baris = tbl_Pembelian.getRowCount();
        int selectedRow = tbl_Pembelian.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();

        jumlah = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 5).toString());
        harga = Integer.parseInt(tabelModel.getValueAt(tbl_Pembelian.getSelectedRow(), 6).toString());
        subtotal = jumlah * harga;
        //totalharga+=subtotal;
        tabelModel.setValueAt(subtotal, tbl_Pembelian.getSelectedRow(), 7);
        tabelModel.setValueAt(subtotal, tbl_Pembelian.getSelectedRow(), 12);
        //txt_total.setText(""+totalharga);
        HitungTotal();
    }

    public void HitungTotal() {
        double totalfix = 0;
        for (int i = tbl_Pembelian.getRowCount() - 1; i > -1; i--) {
            Double x = Double.parseDouble(tbl_Pembelian.getValueAt(i, 12).toString());
            System.out.println("subtotal " + x);
            totalfix += x;
        }
        txt_total.setText(String.valueOf(totalfix));
    }
    private void tbl_PembelianKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PembelianKeyReleased
        // kalo jumlahnya diganti, subtotal sm harga jadinya nanti ngikutin dari jml disini
//        if(tbl_Pembelian.getValueAt(tbl_Pembelian.getSelectedRow(), 0).toString()!="0"){   
//        }
        System.out.println("key released");
        if (tbl_Pembelian.getValueAt(tbl_Pembelian.getSelectedRow(), 5) != "0"
                && tbl_Pembelian.getRowCount() != -1
                && (tbl_Pembelian.getSelectedColumn() == 5
                || tbl_Pembelian.getSelectedColumn() == 1
                || tbl_Pembelian.getSelectedColumn() == 2
                || tbl_Pembelian.getSelectedColumn() == 3
                || tbl_Pembelian.getSelectedColumn() == 4
                || tbl_Pembelian.getSelectedColumn() == 5
                || tbl_Pembelian.getSelectedColumn() == 6
                || tbl_Pembelian.getSelectedColumn() == 7
                || tbl_Pembelian.getSelectedColumn() == 8
                || tbl_Pembelian.getSelectedColumn() == 9
                || tbl_Pembelian.getSelectedColumn() == 10
                || tbl_Pembelian.getSelectedColumn() == 11
                || tbl_Pembelian.getSelectedColumn() == 12
                || tbl_Pembelian.getSelectedColumn() == 13)) {
            HitungSubTotal();
        }


    }//GEN-LAST:event_tbl_PembelianKeyReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int simpan = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin Menyimpan data ini ?", "Konfimasi simpan Data", JOptionPane.OK_CANCEL_OPTION);
        if (simpan == JOptionPane.OK_OPTION) {
            try {
                Koneksi Koneksi = new Koneksi();
                Connection con = Koneksi.configDB();
                Statement st = con.createStatement();
                //memasukkan data
                String sql = "insert into pembelian_return_pakai ( no_faktur_return_pakai, tgl_return_pakai, "
                        //+ "tgl_nota_supplier_pembelian, biaya_pembelian, biaya_pembayaran, discon_persen, discon_rp, "
                        + "keterangan_return_pakai )"
                        + "value('" + txt_noRetur.getText() + "','"
                        + txt_tanggal.getText() + "','"
                        + txt_Keterangan.getText() + "','"
                        + "" + "'"
                        + ");";

                int dbq = st.executeUpdate(sql);

                int baris = tbl_Pembelian.getRowCount();
                System.out.println("baris = " + baris);
                TableModel tabelModel;
                tabelModel = tbl_Pembelian.getModel();

                for (int i = 0; i < baris; i++) {
                    if (tbl_Pembelian.getValueAt(i, 6) != "0") {
                        sql = "insert into pembelian_detail_return_pakai (no_faktur_return_pakai, no_faktur_pembelian, kode_barang, nama_barang_edit, "
                                + "kode_lokasi, kode_barang_konversi, jumlah_barang, jumlah_per_pcs, harga_pembelian "
                                + ")"
                                + "value" + "('" + txt_noRetur.getText() + "','" + comFakturBeli.getSelectedItem().toString() + "','"
                                + tbl_Pembelian.getValueAt(i, 1).toString() + "','"
                                + tbl_Pembelian.getValueAt(i, 2).toString() + "','"
                                //                                + getKodeLokasi(i) + "','"
                                //                                + getKodeKonv(i) + "','"
                                + tbl_Pembelian.getValueAt(i, 5).toString() + "','"
                                //                                + getKonvPcs(i) + "','"
                                + tbl_Pembelian.getValueAt(i, 6).toString() + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 7).toString() + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 12).toString() + "','"
                                //                                + getHargaBeli(i) + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 6).toString() + "','"
                                //                                + tmpHJ1 + "','"
                                //                                + tmpHJ2 + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 8).toString() + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 9).toString() + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 10).toString() + "','"
                                //                                + tbl_Pembelian.getValueAt(i, 11).toString() + "','"
                                + "')";

                        int dbq2 = st.executeUpdate(sql);

                    }
//                    if (dbq2 == 1) {
//                        JOptionPane.showMessageDialog(null, "data barang ke " + (i + 1) + " sudah ditambahkan ke database", "informasi", JOptionPane.INFORMATION_MESSAGE);
//                    }
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "data tidak dimasukkan ke database" + e, "informasi", JOptionPane.INFORMATION_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Data sudah disimpan.");
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jLabel69MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel69MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel69MouseClicked

    private void jLabel71MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel71MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel71MouseClicked

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
            java.util.logging.Logger.getLogger(Pembelian_Return.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Return.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Return.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Return.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pembelian_Return().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comFakturBeli;
    private javax.swing.JComboBox<String> comJenis;
    private javax.swing.JComboBox<String> comSupplier;
    private javax.swing.JComboBox<String> comTableBarang;
    private javax.swing.JComboBox<String> comTableKonv;
    private javax.swing.JComboBox<String> comTableLokasi;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel labelSave;
    private javax.swing.JTable tbl_Pembelian;
    private javax.swing.JTextField txt_Keterangan;
    private javax.swing.JTextField txt_almtSupply;
    private javax.swing.JTextField txt_diskonPersen;
    private javax.swing.JTextField txt_diskonRp;
    private javax.swing.JTextField txt_namaSupply;
    private javax.swing.JTextField txt_noRetur;
    private javax.swing.JTextField txt_tanggal;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables

}
