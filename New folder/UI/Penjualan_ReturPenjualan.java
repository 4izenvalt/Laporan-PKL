/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Connect;
import Java.Currency_Column;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author USER
 */
public class Penjualan_ReturPenjualan extends javax.swing.JFrame {

    /**
     * Creates new form Penjualan_penjualan
     */
    private int jumlah_item = 0, jumlah_qty = 0, total = 0;
    private Thread jam;
    private DateFormat df1 = new SimpleDateFormat("yy"); // 2 digit tahun
    private String tahun_sekarang = df1.format(Calendar.getInstance().getTime());
    private DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy"); // dd-mm-yyyy
    private String tgl_sekarang = df2.format(Calendar.getInstance().getTime());
    private Connect connection;

    //keterangan per barang
    private List<Integer> jumlah_per_barang = new ArrayList();
    private List<Integer> kode_lokasi_barang = new ArrayList();
    private List<Integer> jumlah_retur_barang = new ArrayList();
    private List<Integer> harga_penjualan_barang = new ArrayList();
    private List<Integer> hpp_barang = new ArrayList();
    private List<Integer> kode_konversi_barang = new ArrayList();
    private List<Integer> kode_barang = new ArrayList();

    private int v_kode_customer, v_kode_salesman, v_id_top, v_status_toko;//kode untuk insert di tabel penjualan_return

    public Penjualan_ReturPenjualan() {

    }

    public Penjualan_ReturPenjualan(Connect connection) {
        this.connection = connection;
        initComponents();
        this.setLocationRelativeTo(null);
        tanggal_jam_sekarang();
        set_no_retur();
        AutoCompleteDecorator.decorate(combo_no_faktur);
        load_faktur();
        remove_row();
    }

    private void set_no_retur() {
        int jumlah_retur = 0;
        try {
            String sql = "SELECT COUNT(*) AS jumlah_retur FROM penjualan_return";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                jumlah_retur = res.getInt("jumlah_retur") + 1;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror1" + e);
        }
        txt_no_retur.setText("RJ" + tahun_sekarang + "-" + jumlah_retur);
    }

    private int get_last_id(String tabel, String kolom) {
        int id_terakhir = 0;
        try {
            String sql = "SELECT MAX(" + kolom + ") AS id_terakhir FROM " + tabel;
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                id_terakhir = res.getInt("id_terakhir") + 1;
            }
            return id_terakhir;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror1" + e);
            return id_terakhir;
        }
    }

    private void load_faktur() {
        try {
            String sql = "SELECT * FROM penjualan";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                if (!res.getString("no_faktur_penjualan").equals("")) {
                    combo_no_faktur.addItem(res.getString("no_faktur_penjualan"));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror1" + e);
        }
    }

    private void cari_faktur(String no_faktur) {
        clear_info_field();
        info_penjualan(no_faktur);
        list_barang(no_faktur);
    }

    private void info_penjualan(String no_faktur) {
        try {
            String sql = "SELECT c.kode_customer, c.nama_customer, c.alamat_customer, s.nama_salesman, s.kode_salesman, p.id_top, p.status_toko "
                    + "FROM penjualan p "
                    + "INNER JOIN customer c ON p.kode_customer = c.kode_customer "
                    + "INNER JOIN salesman s ON p.kode_salesman = s.kode_salesman "
                    + "WHERE p.no_faktur_penjualan = '" + no_faktur + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                txt_customer.setText(res.getString("kode_customer"));
                txt_nama.setText(res.getString("nama_customer"));
                txt_alamat.setText(res.getString("alamat_customer"));
                txt_salesman.setText(res.getString("nama_salesman"));
                v_kode_customer = res.getInt("kode_customer");
                v_kode_salesman = res.getInt("kode_salesman");
                v_id_top = res.getInt("id_top");
                v_status_toko = res.getInt("status_toko");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    private void list_barang(String no_faktur) {
        remove_row();
        jumlah_per_barang.clear();
        kode_barang.clear();
        kode_lokasi_barang.clear();
        harga_penjualan_barang.clear();
        kode_konversi_barang.clear();
        try {
            String sql = "SELECT b.kode_barang, b.nama_barang, l.nama_lokasi, k.nama_konversi, pd.harga_penjualan, pd.jumlah_barang, pd.kode_lokasi, pd.harga_penjualan, pd.kode_barang_konversi "
                    + "FROM penjualan_detail pd "
                    + "INNER JOIN barang b ON pd.kode_barang=b.kode_barang "
                    + "INNER JOIN lokasi l ON pd.kode_lokasi=l.kode_lokasi "
                    + "INNER JOIN barang_konversi bk ON pd.kode_barang_konversi = bk.kode_barang_konversi "
                    + "INNER JOIN konversi k ON bk.kode_konversi = k.kode_konversi "
                    + "WHERE pd.no_faktur_penjualan = '" + no_faktur + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) tabel_barang.getModel();
            int i = 1;
            while (res.next()) {
                model.addRow(new Object[]{
                    i++,
                    res.getString("kode_barang"),
                    res.getString("nama_barang"),
                    res.getString("nama_lokasi"),
                    res.getString("nama_konversi"),
                    0,
                    res.getInt("harga_penjualan"),
                    0
                });
                kode_barang.add(res.getInt("kode_barang"));
                kode_lokasi_barang.add(res.getInt("kode_lokasi"));
                harga_penjualan_barang.add(res.getInt("harga_penjualan"));
                kode_konversi_barang.add(res.getInt("kode_barang_konversi"));
                jumlah_per_barang.add(res.getInt("jumlah_barang"));
            }
            TableColumnModel m = tabel_barang.getColumnModel();
            m.getColumn(6).setCellRenderer(new Currency_Column());
            m.getColumn(7).setCellRenderer(new Currency_Column());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

    private void hitung_jumlah_total() {
        total = 0;
        jumlah_qty = 0;
        jumlah_item = 0;
        for (int i = 0; i < tabel_barang.getRowCount(); i++) {
            total += (int) tabel_barang.getValueAt(i, 7);
            jumlah_qty += (int) tabel_barang.getValueAt(i, 5);
            jumlah_item += (int) tabel_barang.getValueAt(i, 5) > 0 ? 1 : 0;
        }
        txt_total.setText(currency_convert(total));
        txt_jumlah_qty.setText("Jumlah Qty : " + jumlah_qty);
        txt_jumlah_item.setText("Jumlah Item : " + jumlah_item);
    }

    private boolean simpan_retur() {
        if (simpan_penjualan_retur()) {
            for (int i = 0; i < tabel_barang.getRowCount(); i++) {
                if ((int) tabel_barang.getValueAt(i, 7) != 0) {
                    simpan_penjualan_retur_detail(i);
                }
            }
            return true;
        } else {
            return false;
        }

    }

    private boolean simpan_penjualan_retur() {
        try {
            String sql = "INSERT INTO penjualan_return VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.Connect().prepareStatement(sql);
            ps.setInt(1, get_last_id("penjualan_return", "id_return"));//id_return
            ps.setString(2, txt_no_retur.getText());//no_faktur_return
            ps.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));//tgl_return
            ps.setString(4, txt_keterangan.getText());//keterangan_return
            ps.setInt(5, 0);//kode_pegawai
            ps.setInt(6, 0);//jumlah_print
            ps.setInt(7, 0);//no_order
            ps.setInt(8, v_kode_customer);//kode_customer
            ps.setInt(9, v_kode_salesman);//kode_salesman
            ps.setDouble(10, total);//biaya_return
            ps.setDouble(11, total * -1);//bayar_biaya_return
            ps.setInt(12, v_id_top);//id_top
            ps.setInt(13, jenis_retur.getSelectedIndex());//jenis_return
            ps.setInt(14, v_status_toko);//status_toko
            ps.executeUpdate();
            return true;
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }

    private boolean simpan_penjualan_retur_detail(int index) {
        try {
            String sql = "INSERT INTO penjualan_detail_return VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.Connect().prepareStatement(sql);
            ps.setInt(1, get_last_id("penjualan_detail_return", "id_return_detail"));//id_return_detail
            ps.setString(2, (String) combo_no_faktur.getSelectedItem());//no_faktur_penjualan
            ps.setString(3, txt_no_retur.getText());//no_faktur_return
            ps.setInt(4, kode_barang.get(index));//kode_barang
            ps.setInt(5, kode_lokasi_barang.get(index));//kode_lokasi
            ps.setInt(6, (int) tabel_barang.getValueAt(index, 5));//jumlah_barang
            ps.setInt(7, (int) tabel_barang.getValueAt(index, 5));//jumlah_per_pcs
            ps.setDouble(8, harga_penjualan_barang.get(index));//harga_penjualan
            ps.setDouble(9, harga_penjualan_barang.get(index));//harga_jual
            ps.setDouble(10, 0);//hpp
            ps.setInt(11, kode_konversi_barang.get(index));//kode_barang_konversi
            ps.setInt(12, v_status_toko);//status_toko
            ps.executeUpdate();
            return true;
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }

    private void cetak_nota_retur(HashMap params) {
        String query = "SELECT b.kode_barang, b.nama_barang, l.nama_lokasi, k.nama_konversi, pdr.jumlah_barang, pdr.harga_penjualan, pdr.no_faktur_penjualan "
                + "FROM penjualan_detail_return pdr "
                + "INNER JOIN barang b ON pdr.kode_barang = b.kode_barang "
                + "INNER JOIN lokasi l ON pdr.kode_lokasi = l.kode_lokasi "
                + "INNER JOIN barang_konversi bk ON pdr.kode_barang_konversi = bk.kode_barang_konversi "
                + "INNER JOIN konversi k ON bk.kode_konversi = k.kode_konversi "
                + "WHERE pdr.no_faktur_return = '" + params.get("no_faktur_return") + "'";
        String print_name = "/Nota Return Penjualan No. Faktur " + params.get("no_faktur_return");
        try {
            Connect koneksi = new Connect();
            Connection conn = koneksi.conn;
            JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath() + "/src/Laporan/Penjualan_Return.jrxml");
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(query);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint print = JasperFillManager.fillReport(jr, params, conn);
            print.setName(print_name);
            //JasperExportManager.exportReportToPdfFile(print, "C:\\Laporan_Tutup_Transaksi_Harian.pdf");
            JasperPrintManager.printReport(print, true);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

    private void clear_info_field() {
        txt_customer.setText("-");
        txt_nama.setText("-");
        txt_alamat.setText("-");
        txt_salesman.setText("-");
        txt_total.setText("");
        txt_keterangan.setText("");
        txt_jumlah_item.setText("Jumlah Item : ");
        txt_jumlah_qty.setText("Jumlah Qty : ");
        jumlah_item = jumlah_qty = total = 0;
    }

    private void remove_row() {
        DefaultTableModel model = (DefaultTableModel) tabel_barang.getModel();
        int row = tabel_barang.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
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

    private void tanggal_jam_sekarang() {
        jam = new Thread() {
            public void run() {
                for (;;) {
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    txt_tanggal.setText(timeStamp);
                }
            }
        };
        jam.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_barang = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txt_customer = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txt_tanggal = new javax.swing.JTextField();
        txt_keterangan = new javax.swing.JTextField();
        txt_total = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        checkbox_cetak = new javax.swing.JCheckBox();
        txt_jumlah_item = new javax.swing.JTextField();
        txt_jumlah_qty = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jenis_retur = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        txt_no_retur = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txt_alamat = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txt_salesman = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        combo_no_faktur = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 204));
        jLabel14.setText("Jenis");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("No. Jual");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Keterangan");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.white, java.awt.Color.white));

        tabel_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode", "Barang", "Lokasi", "Satuan (1/2/3)", "Jumlah", "Harga", "Sub Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel_barang.getTableHeader().setReorderingAllowed(false);
        tabel_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabel_barangKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabel_barangKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_barang);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText("Tanggal");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setText("Total");

        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        jLabel19.setText("F12 - Save");
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Clear-icon.png"))); // NOI18N
        jLabel20.setText("F9 - Clear");
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_yast_printer_30297.png"))); // NOI18N
        jLabel21.setText("Print");
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        jLabel26.setText("Esc - Exit");
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        txt_customer.setEditable(false);
        txt_customer.setBackground(new java.awt.Color(184, 238, 184));
        txt_customer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        txt_nama.setEditable(false);
        txt_nama.setBackground(new java.awt.Color(184, 238, 184));
        txt_nama.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setText("Customer");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setText("Nama");

        txt_tanggal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        txt_keterangan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        txt_total.setEditable(false);
        txt_total.setBackground(new java.awt.Color(204, 255, 204));
        txt_total.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Kasir");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Nama Kasir");

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));

        checkbox_cetak.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        checkbox_cetak.setForeground(new java.awt.Color(153, 0, 0));
        checkbox_cetak.setText("LGSG CETAK");

        txt_jumlah_item.setEditable(false);
        txt_jumlah_item.setBackground(new java.awt.Color(0, 0, 0));
        txt_jumlah_item.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_jumlah_item.setForeground(new java.awt.Color(255, 204, 0));
        txt_jumlah_item.setText("Jumlah Item :");
        txt_jumlah_item.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        txt_jumlah_qty.setEditable(false);
        txt_jumlah_qty.setBackground(new java.awt.Color(0, 0, 0));
        txt_jumlah_qty.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_jumlah_qty.setForeground(new java.awt.Color(255, 204, 0));
        txt_jumlah_qty.setText("Jumlah Qty :");
        txt_jumlah_qty.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jenis_retur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BY FAKTUR", "RETUR BEBAS" }));
        jenis_retur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jenis_returActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setText("No. Retur");

        txt_no_retur.setEditable(false);
        txt_no_retur.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setText("Alamat");

        txt_alamat.setEditable(false);
        txt_alamat.setBackground(new java.awt.Color(184, 238, 184));
        txt_alamat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel35.setText("Salesman");

        txt_salesman.setEditable(false);
        txt_salesman.setBackground(new java.awt.Color(184, 238, 184));
        txt_salesman.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N
        jLabel1.setText("F5 - Delete");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        combo_no_faktur.setEditable(true);
        combo_no_faktur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--NO FAKTUR--" }));
        combo_no_faktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_no_fakturActionPerformed(evt);
            }
        });

        jMenuBar1.setPreferredSize(new java.awt.Dimension(0, 0));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem1.setText("Exit");
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

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        jMenuItem4.setText("Save");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem5.setText("Delete");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator1)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLabel25)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel24)
                                .addComponent(jLabel16))
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(checkbox_cetak)
                                        .addGap(114, 114, 114)
                                        .addComponent(txt_jumlah_item, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_jumlah_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel34)
                                                .addGap(13, 13, 13)
                                                .addComponent(txt_no_retur))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel14)
                                                .addGap(42, 42, 42)
                                                .addComponent(jenis_retur, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(combo_no_faktur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_tanggal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(30, 30, 30)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel18)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txt_keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel30)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel29)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txt_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel33)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel35)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txt_salesman, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jSeparator4)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jSeparator7)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21)
                                .addComponent(jLabel26))
                            .addComponent(jLabel19))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jenis_retur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel14))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(combo_no_faktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_no_retur, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel30))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33)
                                    .addComponent(txt_salesman, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel35))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(txt_keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(checkbox_cetak)
                        .addComponent(txt_jumlah_item, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_jumlah_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private HashMap set_cetak_params() {
        HashMap params = new HashMap();
        params.put("no_faktur_return", txt_no_retur.getText());
        params.put("kode_customer", txt_customer.getText());
        params.put("nama_customer", txt_nama.getText());
        params.put("alamat", txt_alamat.getText());
        params.put("salesman", txt_salesman.getText());
        params.put("tgl_return", tgl_sekarang);
        return params;
    }

    private boolean cek_data() {
        return total > 0 ? true : false;
    }

    private void event_save() {
        if (cek_data()) {
            if (checkbox_cetak.isSelected()) {
                int simpan = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin Menyimpan dan Mencetak data ini ?", "Konfimasi Simpan dan Cetak Data", JOptionPane.OK_CANCEL_OPTION);
                if (simpan == JOptionPane.OK_OPTION) {
                    if (simpan_retur()) {
                        JOptionPane.showMessageDialog(null, "Data sudah disimpan. Proses cetak . . .");
                        cetak_nota_retur(set_cetak_params());
                        clear_info_field();
                        remove_row();
                        set_no_retur();
                        combo_no_faktur.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(null, "Data gagal disimpan !");
                    }
                }
            } else {
                int simpan = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin Menyimpan data ini ?", "Konfimasi Simpan Data", JOptionPane.OK_CANCEL_OPTION);
                if (simpan == JOptionPane.OK_OPTION) {
                    if (simpan_retur()) {
                        JOptionPane.showMessageDialog(null, "Data sudah disimpan.");
                        clear_info_field();
                        remove_row();
                        set_no_retur();
                        combo_no_faktur.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(null, "Data gagal disimpan !");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data masih kosong ! Mohon cek kembali !", "Simpan dan Cetak Data", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void event_clear() {
        clear_info_field();
        remove_row();
        combo_no_faktur.setSelectedIndex(0);
    }

    private void event_delete() {

    }

    private void event_print() {
        if (cek_data()) {
            int simpan = JOptionPane.showConfirmDialog(null, "Data harus disimpan terlebih dahulu !\n\n Apakah Anda ingin menyimpan data ini kemudian cetak ?", "Konfimasi Cetak Data", JOptionPane.OK_CANCEL_OPTION);
            if (simpan == JOptionPane.OK_OPTION) {
                if (simpan_retur()) {
                    JOptionPane.showMessageDialog(null, "Data sudah disimpan.  Proses cetak . . .");
                    cetak_nota_retur(set_cetak_params());
                    clear_info_field();
                    remove_row();
                    set_no_retur();
                    combo_no_faktur.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(null, "Data gagal disimpan !");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data masih kosong ! Mohon cek kembali !", "Cetak Data", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        event_print();
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        event_save();
    }//GEN-LAST:event_jLabel19MouseClicked

    private void combo_no_fakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_no_fakturActionPerformed
        cari_faktur(String.valueOf(combo_no_faktur.getSelectedItem()));
    }//GEN-LAST:event_combo_no_fakturActionPerformed

    private void jenis_returActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jenis_returActionPerformed
        if (jenis_retur.getSelectedIndex() == 0) {
            combo_no_faktur.setEnabled(true);
        } else {
            combo_no_faktur.setSelectedIndex(0);
            combo_no_faktur.setEnabled(false);
        }
        clear_info_field();
    }//GEN-LAST:event_jenis_returActionPerformed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void tabel_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabel_barangKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            int baris = tabel_barang.getSelectedRow();
            int jumlah = (int) tabel_barang.getValueAt(baris, 5);
            if (jumlah <= jumlah_per_barang.get(baris)) {
                int harga = (int) tabel_barang.getValueAt(baris, 6);
                int sub_total = harga * jumlah;
                tabel_barang.setValueAt(sub_total, baris, 7);
                hitung_jumlah_total();
            } else {
                JOptionPane.showMessageDialog(null, "Jumlah barang retur melebihi jumlah penjualan !");
                tabel_barang.setValueAt(0, baris, 5);
            }
        }
    }//GEN-LAST:event_tabel_barangKeyPressed

    private void tabel_barangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabel_barangKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            int baris = tabel_barang.getSelectedRow() - 1;
            baris = baris > -1 ? baris : tabel_barang.getRowCount() - 1;
            int jumlah = (int) tabel_barang.getValueAt(baris, 5);
            if (jumlah <= jumlah_per_barang.get(baris)) {
                int harga = (int) tabel_barang.getValueAt(baris, 6);
                int sub_total = harga * jumlah;
                tabel_barang.setValueAt(sub_total, baris, 7);
                hitung_jumlah_total();
            } else {
                JOptionPane.showMessageDialog(null, "Jumlah barang retur melebihi jumlah penjualan !");
                tabel_barang.setValueAt(0, baris, 5);
            }
        }
    }//GEN-LAST:event_tabel_barangKeyReleased

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        event_clear();
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        event_clear();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        event_save();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        event_delete();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        event_delete();
    }//GEN-LAST:event_jLabel1MouseClicked

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
            java.util.logging.Logger.getLogger(Penjualan_ReturPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_ReturPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_ReturPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_ReturPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new Penjualan_ReturPenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkbox_cetak;
    private javax.swing.JComboBox<String> combo_no_faktur;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JComboBox<String> jenis_retur;
    private javax.swing.JTable tabel_barang;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_customer;
    private javax.swing.JTextField txt_jumlah_item;
    private javax.swing.JTextField txt_jumlah_qty;
    private javax.swing.JTextField txt_keterangan;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_no_retur;
    private javax.swing.JTextField txt_salesman;
    private javax.swing.JTextField txt_tanggal;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
