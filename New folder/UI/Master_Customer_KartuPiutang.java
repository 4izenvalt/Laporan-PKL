/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Connect;
import com.sun.glass.events.KeyEvent;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author USER
 */
public class Master_Customer_KartuPiutang extends javax.swing.JDialog {

    /**
     * Creates new form Master_Customer_KartuPiutang
     */
    private Connect connection;
    private Frame parent;
    String kode, tanggal;
    int seleksi = 0, kode_seleksi = 0, row;
    String[] no_penjualan;
    String[] nama_customer;

    public Master_Customer_KartuPiutang() {
        initComponents();

    }

    public Master_Customer_KartuPiutang(java.awt.Frame parent, boolean modal, Connect connection, String input) {
        super(parent, modal);
        this.parent = parent;
        initComponents();
        AutoCompleteDecorator.decorate(comKodeCustomer);
        this.connection = connection;
        this.kode = input;
        System.out.println("input kode : " + input);
        comKodeCustomer.setSelectedItem(input);
        loadTable(input, "*");
        loadKodeCustomer();
        loadTotal();
        System.out.println("-----------------------------------------------");
        txt_total_jual.setHorizontalAlignment(JTextField.RIGHT);
        txt_total_jumlah.setHorizontalAlignment(JTextField.RIGHT);
        txt_total_pembayaran.setHorizontalAlignment(JTextField.RIGHT);
        txt_total_retur.setHorizontalAlignment(JTextField.RIGHT);
        txt_total_sisa.setHorizontalAlignment(JTextField.RIGHT);
        txt_jumlah2.setHorizontalAlignment(JTextField.RIGHT);
        txt_pembayaran2.setHorizontalAlignment(JTextField.RIGHT);
        txt_retur2.setHorizontalAlignment(JTextField.RIGHT);
        //   lebarKolom();
    }

    void loadKodeCustomer() {
        try {
            String sql = "SELECT * FROM customer ";
            System.out.println("sqlll: " + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                comKodeCustomer.addItem(res.getString("kode_customer"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }

    void loadNamaCustomer() {
        try {
            String sql = "SELECT * FROM customer WHERE kode_customer = '" + comKodeCustomer.getSelectedItem() + "'";
            System.out.println("sqlll: " + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                txt_nama_customer.setText(res.getString("nama_customer"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }

    public void lebarKolom() {
        TableColumn column;
        tbl_Kartu_Piutang.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tbl_Kartu_Piutang.getColumnModel().getColumn(0);
        column.setPreferredWidth(90);
        column = tbl_Kartu_Piutang.getColumnModel().getColumn(1);
        column.setPreferredWidth(120);
        column = tbl_Kartu_Piutang.getColumnModel().getColumn(2);
        column.setPreferredWidth(100);
        column = tbl_Kartu_Piutang.getColumnModel().getColumn(3);
        column.setPreferredWidth(100);
        column = tbl_Kartu_Piutang.getColumnModel().getColumn(4);
        column.setPreferredWidth(100);
        column = tbl_Kartu_Piutang.getColumnModel().getColumn(5);
        column.setPreferredWidth(100);
    }

    Object format_double(String key, boolean tampil) {
        if (tampil) {
            //Untuk menampilkan ke JTextbox
            DecimalFormat df = new DecimalFormat();
            DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            dfs.setGroupingSeparator(',');
            df.setDecimalFormatSymbols(dfs);
            double convert = Double.parseDouble(key);
            System.out.println(df.format(convert));
            return df.format(convert);
        } else {
            //jika ingin menyimpan, hilangkan koma untuk ribuan
            key = key.replaceAll(",", "");
            double convert = Double.parseDouble(key);
            return convert;
        }

    }

    public void loadTable(String kode, String param) {
        removeRow();
        if (param.equals("*")) {
            param = "";
        }
        DefaultTableModel model = (DefaultTableModel) tbl_Kartu_Piutang.getModel();
        double i = 1, sisa, x, temp = 0;
        String sql = "";
        String bahan;
        System.out.println("kodeeee----" + kode);

        try {

            if (tgl_inv_akhir.isEnabled()) {
                SimpleDateFormat ft_awal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String awal = ft_awal.format(tgl_inv_awal.getDate());

                SimpleDateFormat ft_akhir = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String akhir = ft_akhir.format(tgl_inv_akhir.getDate());

                sql = "SELECT c.nama_customer, p.kode_customer, p.no_faktur_penjualan, p.tgl_penjualan, "
                        + "format(SUM(p.pembayaran_aktif),2) AS jumlah, "
                        + "format(SUM(p.pembaran_udah_bayar),2) AS pembayaran "
                        + "FROM penjualan p "
                        + "JOIN penjualan_detail pd "
                        + "ON p.no_faktur_penjualan = pd.no_faktur_penjualan "
                        + "JOIN customer c "
                        + "ON c.kode_customer = p.kode_customer "
                        + "WHERE p.kode_customer = '" + kode + "' "
                        + "AND p.no_faktur_penjualan like '%" + param + "%' "
                        + "AND tgl_penjualan BETWEEN '" + awal + "' and '" + akhir + "' "
                        + "GROUP BY no_faktur_penjualan "
                        + "ORDER By tgl_penjualan DESC";
                System.out.println("kode____ " + kode);
            } else {
                sql = "SELECT c.nama_customer, p.kode_customer, p.no_faktur_penjualan, p.tgl_penjualan, "
                        + "format(SUM(p.pembayaran_aktif),2) AS jumlah, "
                        + "format(SUM(p.pembaran_udah_bayar),2) AS pembayaran "
                        + "FROM penjualan p "
                        + "JOIN penjualan_detail pd "
                        + "ON p.no_faktur_penjualan = pd.no_faktur_penjualan "
                        + "JOIN customer c "
                        + "ON c.kode_customer = p.kode_customer "
                        + "WHERE p.kode_customer = '" + kode + "' "
                        + "AND p.no_faktur_penjualan like '%" + param + "%' "
                        + "GROUP BY no_faktur_penjualan";
                System.out.println("kode cus : " + kode);
            }

            System.out.println("sqlll: " + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                model.addRow(new Object[]{
                    res.getString("no_faktur_penjualan"),
                    res.getString("tgl_penjualan"),
                    res.getString("jumlah"),
                    res.getString("pembayaran"),
                    0
                });
            }

            for (int j = 0; j < tbl_Kartu_Piutang.getRowCount(); j++) {
                double qty = (Double) format_double(String.valueOf(tbl_Kartu_Piutang.getValueAt(j, 3)), false);
                System.out.println("qty : " + qty);
                sisa = ((qty * (-2)) + qty) + temp;
                temp = sisa;
                tbl_Kartu_Piutang.setValueAt(format_double(String.valueOf(sisa), true) + ".00", j, 5);
            }

            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
            tbl_Kartu_Piutang.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
            tbl_Kartu_Piutang.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
            tbl_Kartu_Piutang.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
            tbl_Kartu_Piutang.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
            row = tbl_Kartu_Piutang.getRowCount();
            //   loadTotal();
        } catch (NullPointerException e) {
            System.out.println("tanggal kosong");
            //  JOptionPane.showMessageDialog(null, "Eror = " + e);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }

    }

    void loadTotal() {
        double total_jumlah = 0, total_pembayaran = 0, total_retur = 0, total_sisa = 0;
        double jumlah = 0, pembayaran = 0, retur = 0, sisa1 = 0;

        for (int j = 0; j < row; j++) {
            jumlah = (Double) format_double(String.valueOf(tbl_Kartu_Piutang.getValueAt(j, 2)), false);
            total_jumlah += jumlah;
            System.out.print(kode);
            System.out.println(" total_jumlah : " + total_jumlah);

            pembayaran = (Double) format_double(String.valueOf(tbl_Kartu_Piutang.getValueAt(j, 3)), false);
            total_pembayaran += pembayaran;
            System.out.println("total_prmbayaran : " + total_pembayaran);

            retur = (Double) format_double(String.valueOf(tbl_Kartu_Piutang.getValueAt(j, 4)), false);
            total_retur += retur;
            System.out.println("total_jumlah : " + total_retur);

            sisa1 = (Double) format_double(String.valueOf(tbl_Kartu_Piutang.getValueAt(j, 2)), false);
            total_sisa += sisa1;
            System.out.println("total_jumlah : " + total_sisa);

//            no_penjualan[j] = tbl_Kartu_Piutang.getValueAt(j, 0).toString();
        }
        int a = tbl_Kartu_Piutang.getRowCount() - 1;
        System.out.println(total_jumlah + " - " + total_pembayaran + " - " + total_retur + " - " + total_sisa);
        txt_total_jual.setText(String.valueOf(row));
        txt_total_jumlah.setText((String) format_double(String.valueOf(total_jumlah), true) + ".00");
        txt_total_pembayaran.setText((String) format_double(String.valueOf(total_pembayaran), true) + ".00");
        txt_total_retur.setText((String) format_double(String.valueOf(total_retur), true) + ".00");
        
        if (tbl_Kartu_Piutang.getRowCount() == 0) {
            txt_total_sisa.setText("0.00");
        }else{
                txt_total_sisa.setText(tbl_Kartu_Piutang.getValueAt(a, 5).toString());
        }

        /*
         txt_total_jumlah.setText(String.valueOf(total_jumlah));
         txt_total_pembayaran.setText(String.valueOf(total_pembayaran));
         txt_total_retur.setText(String.valueOf(total_retur));
         txt_total_sisa.setText(String.valueOf(total_sisa));
         */
    }

    void loadTotalDetail() {
        double total_jumlah_detail = 0, total_pembayaran_detail = 0, total_retur_detail = 0, total_sisa_detail = 0;
        double jumlah = 0, pembayaran = 0, retur = 0;
        for (int j = 0; j < row; j++) {
            
            jumlah = (Double) format_double(String.valueOf(tbl_Piutang_Detail.getValueAt(j, 2)), false);
            total_jumlah_detail += jumlah;

            pembayaran = (Double) format_double(String.valueOf(tbl_Piutang_Detail.getValueAt(j, 3)), false);
            total_pembayaran_detail += pembayaran;

            retur = (Double) format_double(String.valueOf(tbl_Piutang_Detail.getValueAt(j, 4)), false);
            total_retur_detail += retur;

            System.out.println(total_jumlah_detail + " - " + total_pembayaran_detail + " - " + total_retur_detail);
        }
        txt_jumlah2.setText((String) format_double(String.valueOf(total_jumlah_detail), true) + ".00");
        txt_pembayaran2.setText((String) format_double(String.valueOf(total_pembayaran_detail), true) + ".00");
        txt_retur2.setText((String) format_double(String.valueOf(total_retur_detail), true) + ".00");

    }

    void loadDetail(String kode, String param) {
        removeRowDetail();
        if (param.equals("*")) {
            param = "";
        }
        int i = 1;

        String[][] data = null;
        DefaultTableModel model = (DefaultTableModel) tbl_Piutang_Detail.getModel();
        try {
            String sql = "SELECT p.kode_customer, p.no_faktur_penjualan, p.tgl_penjualan, "
                    + "format(SUM(p.pembayaran_aktif),2) AS jumlah, "
                    + "format(SUM(p.pembaran_udah_bayar),2) AS pembayaran "
                    + "FROM penjualan p "
                    + "JOIN penjualan_detail pd "
                    + "ON p.no_faktur_penjualan = pd.no_faktur_penjualan "
                    + "WHERE p.kode_customer = '" + kode + "' "
                    + "AND p.no_faktur_penjualan like '%" + param + "%' "
                    + "GROUP BY no_faktur_penjualan";
            System.out.println("param : " + param);
            System.out.println("sqlll: " + sql);
            Connection con = (Connection) Koneksi.configDB();
            Statement stat1 = con.createStatement();
            ResultSet res1 = stat1.executeQuery(sql);

            while (res1.next()) {
                model.addRow(new Object[]{
                    res1.getString("no_faktur_penjualan"),
                    res1.getString("tgl_penjualan"),
                    res1.getString("jumlah"),
                    res1.getString("pembayaran"),
                    0
                });
            }
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
            tbl_Piutang_Detail.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
            tbl_Piutang_Detail.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
            tbl_Piutang_Detail.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
            loadTotalDetail();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror = " + e.getMessage());
        }
    }

    public void removeRow() {
        DefaultTableModel model = (DefaultTableModel) tbl_Kartu_Piutang.getModel();
        int row = tbl_Kartu_Piutang.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    public void removeRowDetail() {
        DefaultTableModel model = (DefaultTableModel) tbl_Piutang_Detail.getModel();
        int row = tbl_Piutang_Detail.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    ChangeListener changeListener = new ChangeListener() {
        public void stateChanged(ChangeEvent changeEvent) {
            JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
            int index = sourceTabbedPane.getSelectedIndex();
            System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
        }
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel14 = new javax.swing.JLabel();
        txt_nama_customer = new javax.swing.JTextField();
        txt_jual = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        pane_Piutang = new javax.swing.JTabbedPane();
        tab1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Kartu_Piutang = new javax.swing.JTable();
        txt_total_jual = new javax.swing.JTextField();
        txt_total_tgl = new javax.swing.JTextField();
        txt_total_jumlah = new javax.swing.JTextField();
        txt_total_pembayaran = new javax.swing.JTextField();
        txt_total_retur = new javax.swing.JTextField();
        txt_total_sisa = new javax.swing.JTextField();
        check_tgl = new javax.swing.JCheckBox();
        jLabel25 = new javax.swing.JLabel();
        btn_proses = new javax.swing.JButton();
        tgl_inv_awal = new com.toedter.calendar.JDateChooser();
        tgl_inv_akhir = new com.toedter.calendar.JDateChooser();
        tab2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<String>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_Piutang_Detail = new javax.swing.JTable();
        txt_jumlah2 = new javax.swing.JTextField();
        txt_pembayaran2 = new javax.swing.JTextField();
        txt_retur2 = new javax.swing.JTextField();
        tgl_inv_detail = new com.toedter.calendar.JDateChooser();
        comKodeCustomer = new javax.swing.JComboBox();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("No. Jual");

        txt_nama_customer.setEditable(false);
        txt_nama_customer.setBackground(new java.awt.Color(255, 255, 204));
        txt_nama_customer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        txt_jual.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_jual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_jualMouseClicked(evt);
            }
        });
        txt_jual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_jualKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jualKeyReleased(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("No. Bayar");

        jTextField8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        jTextField8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField8MouseClicked(evt);
            }
        });

        pane_Piutang.setBackground(new java.awt.Color(255, 255, 255));
        pane_Piutang.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.gray));
        pane_Piutang.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        pane_Piutang.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pane_PiutangStateChanged(evt);
            }
        });

        tab1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, java.awt.Color.white));

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.lightGray));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("KARTU PIUTANG");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel18)
                .addGap(5, 5, 5))
        );

        tbl_Kartu_Piutang.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray));
        tbl_Kartu_Piutang.setModel(new javax.swing.table.DefaultTableModel(
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
                "No. Jual", "Tanggal", "Jumlah", "Pembayaran", "Retur", "Saldo Piutang"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Kartu_Piutang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_Kartu_PiutangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_Kartu_Piutang);

        txt_total_jual.setEditable(false);
        txt_total_jual.setBackground(new java.awt.Color(230, 230, 230));
        txt_total_jual.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_total_jual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_total_tgl.setEditable(false);
        txt_total_tgl.setBackground(new java.awt.Color(230, 230, 230));
        txt_total_tgl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_total_tgl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_total_jumlah.setEditable(false);
        txt_total_jumlah.setBackground(new java.awt.Color(230, 230, 230));
        txt_total_jumlah.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_total_jumlah.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_total_jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total_jumlahActionPerformed(evt);
            }
        });

        txt_total_pembayaran.setEditable(false);
        txt_total_pembayaran.setBackground(new java.awt.Color(230, 230, 230));
        txt_total_pembayaran.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_total_pembayaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_total_pembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total_pembayaranActionPerformed(evt);
            }
        });

        txt_total_retur.setEditable(false);
        txt_total_retur.setBackground(new java.awt.Color(230, 230, 230));
        txt_total_retur.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_total_retur.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_total_retur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total_returActionPerformed(evt);
            }
        });

        txt_total_sisa.setEditable(false);
        txt_total_sisa.setBackground(new java.awt.Color(230, 230, 230));
        txt_total_sisa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_total_sisa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_total_sisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total_sisaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txt_total_jual, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_total_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_total_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_total_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_total_retur, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_total_sisa, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_total_pembayaran)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_total_jual, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_total_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_total_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_total_retur)
                    .addComponent(txt_total_sisa))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel4);

        check_tgl.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        check_tgl.setText("Tanggal");
        check_tgl.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                check_tglStateChanged(evt);
            }
        });
        check_tgl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_tglActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel25.setText("s.d.");

        btn_proses.setText("PROSES");
        btn_proses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_prosesMouseClicked(evt);
            }
        });

        tgl_inv_awal.setDateFormatString(" yyyy- MM-dd");
        tgl_inv_awal.setEnabled(false);
        tgl_inv_awal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_inv_awalKeyPressed(evt);
            }
        });

        tgl_inv_akhir.setDateFormatString(" yyyy- MM-dd");
        tgl_inv_akhir.setEnabled(false);
        tgl_inv_akhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_inv_akhirKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout tab1Layout = new javax.swing.GroupLayout(tab1);
        tab1.setLayout(tab1Layout);
        tab1Layout.setHorizontalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tab1Layout.createSequentialGroup()
                        .addComponent(check_tgl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tgl_inv_awal, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25)
                        .addGap(6, 6, 6)
                        .addComponent(tgl_inv_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_proses)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        tab1Layout.setVerticalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tgl_inv_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_proses)
                    .addComponent(check_tgl)
                    .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tgl_inv_awal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(13, 13, 13)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1)
                .addGap(10, 10, 10))
        );

        pane_Piutang.addTab("Kartu Piutang", tab1);

        jLabel1.setText("s.d.");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LUNAS", "BELUM LUNAS" }));

        tbl_Piutang_Detail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Jual", "Tanggal", "Jumlah", "Pembayaran", "Retur"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbl_Piutang_Detail);

        txt_jumlah2.setEditable(false);
        txt_jumlah2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlah2ActionPerformed(evt);
            }
        });

        txt_pembayaran2.setEditable(false);

        txt_retur2.setEditable(false);

        tgl_inv_detail.setDateFormatString(" yyyy- MM-dd");
        tgl_inv_detail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_inv_detailKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout tab2Layout = new javax.swing.GroupLayout(tab2);
        tab2.setLayout(tab2Layout);
        tab2Layout.setHorizontalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tab2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(tab2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tgl_inv_detail, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(tab2Layout.createSequentialGroup()
                        .addGap(362, 362, 362)
                        .addComponent(txt_jumlah2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_pembayaran2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_retur2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        tab2Layout.setVerticalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(tgl_inv_detail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_jumlah2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_pembayaran2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_retur2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        pane_Piutang.addTab("Detail Piutang", tab2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pane_Piutang)
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pane_Piutang)
                .addGap(10, 10, 10))
        );

        comKodeCustomer.setSelectedItem(this.kode);
        comKodeCustomer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comKodeCustomerItemStateChanged(evt);
            }
        });
        comKodeCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comKodeCustomerActionPerformed(evt);
            }
        });
        comKodeCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comKodeCustomerKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(comKodeCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nama_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_jual, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_jual, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comKodeCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_nama_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_jualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_jualMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jualMouseClicked

    private void jTextField8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8MouseClicked

    private void txt_total_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total_jumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_jumlahActionPerformed

    private void txt_total_pembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total_pembayaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_pembayaranActionPerformed

    private void txt_total_returActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total_returActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_returActionPerformed

    private void txt_total_sisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total_sisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_sisaActionPerformed

    private void tbl_Kartu_PiutangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Kartu_PiutangMouseClicked

        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            int baris = tbl_Kartu_Piutang.getSelectedRow();
            tanggal = tbl_Kartu_Piutang.getValueAt(baris, 1).toString(); 
            String no_faktur = tbl_Kartu_Piutang.getValueAt(baris, 0).toString();
            kode_seleksi = tbl_Kartu_Piutang.getSelectedRow();
            System.out.println("seleksi : " + kode_seleksi);
            Penjualan_Penjualan pp = new Penjualan_Penjualan(tanggal, no_faktur);
            pp.setVisible(true);
            pp.setFocusable(true);

        }
    }//GEN-LAST:event_tbl_Kartu_PiutangMouseClicked

    private void txt_jumlah2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlah2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlah2ActionPerformed

    private void txt_jualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jualKeyPressed

    }//GEN-LAST:event_txt_jualKeyPressed

    private void txt_jualKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jualKeyReleased

        if (tab1.isShowing()) {
            loadTable(comKodeCustomer.getSelectedItem().toString(), txt_jual.getText());
            System.out.println("tab1");
            loadTotal();

        }
        if (tab2.isShowing()) {
            //tbl_Piutang_Detail.removeColumnSelectionInterval(0, tbl_Piutang_Detail.getRowCount());

            loadDetail(comKodeCustomer.getSelectedItem().toString(), txt_jual.getText());
            System.out.println("tab2");
            //   loadTotalDetail();

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jualKeyReleased

    private void tgl_inv_awalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_inv_awalKeyPressed

    }//GEN-LAST:event_tgl_inv_awalKeyPressed

    private void tgl_inv_akhirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_inv_akhirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tgl_inv_akhirKeyPressed

    private void btn_prosesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_prosesMouseClicked
        seleksi = 1;
        loadTable(comKodeCustomer.getSelectedItem().toString(), txt_jual.getText());
    }//GEN-LAST:event_btn_prosesMouseClicked

    private void check_tglStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_check_tglStateChanged

    }//GEN-LAST:event_check_tglStateChanged

    private void check_tglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_tglActionPerformed
        if (check_tgl.isSelected()) {
            tgl_inv_awal.setEnabled(true);
            tgl_inv_akhir.setEnabled(true);
            btn_proses.setEnabled(true);
        } else {
            tgl_inv_awal.setEnabled(false);
            tgl_inv_akhir.setEnabled(false);
            btn_proses.setEnabled(false);
        }
    }//GEN-LAST:event_check_tglActionPerformed

    private void tgl_inv_detailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_inv_detailKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tgl_inv_detailKeyPressed


    private void pane_PiutangStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pane_PiutangStateChanged
        /*
         int x = 0;
         if (pane_Piutang.getSelectedIndex() == 0 && x == 1) {
         loadTable(comKodeCustomer.getSelectedItem().toString(), txt_jual.getText());
         loadTotal();

         }
         if (pane_Piutang.getSelectedIndex() == 1) {

         // tbl_Piutang_Detail.removeColumnSelectionInterval(0, tbl_Piutang_Detail.getRowCount());
         //  tbl_Piutang_Detail.removeAll();
         loadDetail(comKodeCustomer.getSelectedItem().toString(), txt_jual.getText());
         //   loadTotalDetail();
         x = 1;
         }
         */
    }//GEN-LAST:event_pane_PiutangStateChanged

    private void comKodeCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comKodeCustomerActionPerformed

        loadNamaCustomer();
        loadTable(comKodeCustomer.getSelectedItem().toString(), "*");
        loadTotal();
        loadDetail(comKodeCustomer.getSelectedItem().toString(), "*");


    }//GEN-LAST:event_comKodeCustomerActionPerformed

    private void comKodeCustomerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comKodeCustomerKeyPressed


    }//GEN-LAST:event_comKodeCustomerKeyPressed

    private void comKodeCustomerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comKodeCustomerItemStateChanged

    }//GEN-LAST:event_comKodeCustomerItemStateChanged

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
            java.util.logging.Logger.getLogger(Master_Customer_KartuPiutang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Customer_KartuPiutang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Customer_KartuPiutang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Customer_KartuPiutang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master_Customer_KartuPiutang().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_proses;
    private javax.swing.JCheckBox check_tgl;
    private javax.swing.JComboBox comKodeCustomer;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTabbedPane pane_Piutang;
    private javax.swing.JPanel tab1;
    private javax.swing.JPanel tab2;
    private javax.swing.JTable tbl_Kartu_Piutang;
    private javax.swing.JTable tbl_Piutang_Detail;
    private com.toedter.calendar.JDateChooser tgl_inv_akhir;
    private com.toedter.calendar.JDateChooser tgl_inv_awal;
    private com.toedter.calendar.JDateChooser tgl_inv_detail;
    private javax.swing.JTextField txt_jual;
    private javax.swing.JTextField txt_jumlah2;
    private javax.swing.JTextField txt_nama_customer;
    private javax.swing.JTextField txt_pembayaran2;
    private javax.swing.JTextField txt_retur2;
    private javax.swing.JTextField txt_total_jual;
    private javax.swing.JTextField txt_total_jumlah;
    private javax.swing.JTextField txt_total_pembayaran;
    private javax.swing.JTextField txt_total_retur;
    private javax.swing.JTextField txt_total_sisa;
    private javax.swing.JTextField txt_total_tgl;
    // End of variables declaration//GEN-END:variables
}
