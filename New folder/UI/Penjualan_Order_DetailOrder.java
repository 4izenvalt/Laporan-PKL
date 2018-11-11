/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Connect;
import static UI.Master_MutasiAntarGudang_DetailNew2.rightPadZeros;
import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

/**
 *
 * @author USER
 */
public class Penjualan_Order_DetailOrder extends javax.swing.JFrame {

    private String no_order = "";
    private int jumlah_item = 0;
    private double jumlah_qty = 0;
    private double total_harga = 0;
    Penjualan_Order awal;
    ArrayList<String> kode_nama_arr = new ArrayList();
    private static int item = 0;
    private boolean tampil = true;
    private String[] id_detail_order;
    private int row_lama;

    /**
     * Creates new form Penjualan_penjualan
     */
    public Penjualan_Order_DetailOrder() {
        initComponents();
        this.setLocationRelativeTo(null);

    }

    public Penjualan_Order_DetailOrder(String no_order, Penjualan_Order po) {
        initComponents();
        tbl_order_detail.setRowSelectionInterval(0, 0);
        tbl_order_detail.requestFocus(true);
        this.awal = po;
        this.no_order = no_order;
        loadSalesman();
        loadTop();
        loadData();
        AutoCompleteDecorator.decorate(cbx_salesman);
        this.setLocationRelativeTo(null);

        //JCombobox Nama barang
        ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboNama(((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
                            tbl_order_detail.editCellAt(tbl_order_detail.getSelectedRow(), 2);
                            comTableBarang.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                //((JTextComponent) comTableBarang.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");

            }

        });
        ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
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

    void loadData() {
        total_harga = 0;
        jumlah_qty = 0;
        jumlah_item = 0;
        row_lama = tbl_order_detail.getRowCount();
        try {
            DefaultTableModel model = (DefaultTableModel) tbl_order_detail.getModel();
            model.setRowCount(0);
            String sql = "select o.keterangan_order, c.kode_customer, c.nama_customer, c.alamat_customer, "
                    + "o.tgl_order, o.no_faktur_order, s.nama_salesman, t.nama_top, od.kode_barang, "
                    + "b.nama_barang, od.jumlah_barang, od.harga_pengajuan, od.harga_revisi, od.kode_barang_konversi from customer c join `order` o "
                    + "on o.kode_customer = c.kode_customer join salesman s on s.kode_salesman = o.kode_pegawai "
                    + "join top t on t.id_top = o.id_top join order_detail od "
                    + "on od.no_faktur_order = o.no_faktur_order join barang b on od.kode_barang = b.kode_barang "
                    + "where od.no_faktur_order = '" + this.no_order + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            int baris = 0;
            while (res.next()) {
                if (baris == 0) {
                    txt_kode_customer.setText(res.getString("kode_customer"));
                    txt_nama_customer.setText(res.getString("nama_customer"));
                    txt_alamat_customer.setText(res.getString("alamat_customer"));
                    txt_tgl_order.setText(res.getString("tgl_order"));
                    txt_no_order.setText(res.getString("no_faktur_order"));
                    cbx_salesman.setSelectedItem(res.getString("nama_salesman"));
                    cbx_top.setSelectedItem(res.getString("nama_top"));
                    txt_ket_verifikasi.setText(res.getString("keterangan_order"));
                }
                model.addRow(new Object[]{"", "", "", "", ""});
                model.setValueAt(baris + 1, baris, 0);
                model.setValueAt(res.getString("kode_barang"), baris, 1);
                model.setValueAt(res.getString("nama_barang"), baris, 2);
                model.setValueAt(res.getString("jumlah_barang"), baris, 3);

                //Menghitung total harga
                double harga = 0;
                String harga_revisi = res.getString("harga_revisi");
                if (harga_revisi.equals("0")) {
                    harga = Double.parseDouble(res.getString("harga_pengajuan"));
                } else {
                    harga = Double.parseDouble(res.getString("harga_revisi"));
                }
                model.setValueAt(format_double(String.valueOf(harga), true), baris, 4);
                double jumlah = Double.parseDouble(res.getString("jumlah_barang"));
                total_harga += (jumlah * harga);

                //menghitung qty
                double qty = qty_konversi(res.getString("kode_barang"), res.getString("kode_barang_konversi"), jumlah);
                jumlah_qty += qty;
                baris++;
                jumlah_item++;
            }
            lbl_jumlah_item.setText("Jumlah Item : " + jumlah_item);
            lbl_jumlah_qty.setText("Jumlah qty : " + (String) format_double(String.valueOf(jumlah_qty), true));
            txt_total_harga.setText((String) format_double(String.valueOf(total_harga), true));
            model.addRow(new Object[]{"", "", "", "", ""});
            conn.close();
            res.close();
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    double qty_konversi(String kode_barang, String kode_konversi, double qty) {
        double hasil = 0;

        try {

            String sql = "select * from barang_konversi where kode_barang='" + kode_barang + "' and (kode_barang_konversi ='" + kode_konversi + "' || identitas_konversi='1') order by identitas_konversi asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            System.out.println(sql);
            double[] jumlah_konversi = new double[2];
            int indeks = 0; //0 dg identitas 1, 1 dengan identitas yg dipilih
            int[] identitas_konversi = new int[2];
            while (res.next()) {
                jumlah_konversi[indeks] = Double.parseDouble(res.getString("jumlah_konversi"));
                identitas_konversi[indeks] = Integer.parseInt(res.getString("identitas_konversi"));
                indeks++;
            }

            System.out.println("identitas 1 =" + identitas_konversi[0]);
            System.out.println("identitas dipilih =" + identitas_konversi[1]);
            if (identitas_konversi[1] == 3) {
                hasil = qty * jumlah_konversi[0];
            } else if (identitas_konversi[1] == 2) {
                hasil = qty * jumlah_konversi[1];
            } else {
                hasil = qty;
            }
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
        return hasil;
    }

    public void nextOrder() {
        //mendapatkan angka lama
        String no_order = this.no_order;
        String[] angka = no_order.split("-");
        String belakang = angka[1];
        String angka_aja = belakang.replace("0", "");

        //mengecek apakah angka lama adalah angka maksimal
        boolean terakir = false;
        try {
            String sql = "select max(no_faktur_order) from `order` ORDER BY no_faktur_order DESC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next() && res != null) {
                res.last();
                String no_terakir = res.getString(1);
                int noLama = Integer.parseInt(no_terakir.substring(no_terakir.length() - 4));
                String no = String.valueOf(noLama);
                no = Integer.toString(noLama);
                if (no.equals(angka_aja)) {
                    terakir = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
        if (!terakir) {
            //membuat angka baru
            int angka_baru = Integer.parseInt(angka_aja);
            angka_baru += 1;
            String lastNo = "";
            if (angka_aja.length() == 1) {
                lastNo = "0000" + angka_baru;
            } else if (angka_aja.length() == 2) {
                lastNo = "000" + angka_baru;
            } else if (angka_aja.length() == 3) {
                lastNo = "00" + angka_baru;
            } else if (angka_aja.length() == 4) {
                lastNo = "0" + angka_baru;
            }
            this.no_order = "OP18-" + lastNo;
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Ini order tarakir");
            System.out.println("Ini id terakir");
        }

    }

    public void prevOrder() {
        //mendapatkan angka lama
        String no_order = this.no_order;
        String[] angka = no_order.split("-");
        String belakang = angka[1];
        String angka_aja = belakang.replace("0", "");

        //mengecek apakah angka lama adalah angka minimal
        boolean awal = false;
        try {
            String sql = "select min(no_faktur_order) from `order` ORDER BY no_faktur_order DESC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next() && res != null) {
                res.last();
                String no_terakir = res.getString(1);
                int noLama = Integer.parseInt(no_terakir.substring(no_terakir.length() - 4));
                String no = String.valueOf(noLama);
                no = Integer.toString(noLama);
                if (no.equals(angka_aja)) {
                    awal = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
        if (!awal) {
            //membuat angka baru
            int angka_baru = Integer.parseInt(angka_aja);
            angka_baru -= 1;
            String lastNo = "";
            if (angka_aja.length() == 1) {
                lastNo = "0000" + angka_baru;
            } else if (angka_aja.length() == 2) {
                lastNo = "000" + angka_baru;
            } else if (angka_aja.length() == 3) {
                lastNo = "00" + angka_baru;
            } else if (angka_aja.length() == 4) {
                lastNo = "0" + angka_baru;
            }
            this.no_order = "OP18-" + lastNo;
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Ini order pertama");
            System.out.println("Ini id pertama");
        }

    }

    void loadSalesman() {
        try {
            String sql = "select * from salesman";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String nama = res.getString("nama_salesman");
                cbx_salesman.addItem(nama);
            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    Object format_double(String key, boolean tampil) {
        if (!tampil) {
            //jika ingin menyimpan, hilangkan koma untuk ribuan
            key = key.replaceAll(",", "");
            double convert = Double.parseDouble(key);
            return convert;
        } else {
            //Untuk menampilkan ke JTextbox
            DecimalFormat df = new DecimalFormat();
            DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            dfs.setGroupingSeparator(',');
            df.setDecimalFormatSymbols(dfs);
            double convert = Double.parseDouble(key);
            System.out.println(df.format(convert));
            return df.format(convert);
        }

    }

    void loadTop() {
        try {
            String sql = "select * from top";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String nama = res.getString("nama_top");
                cbx_top.addItem(nama);
            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
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

        comTableBarang = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txt_kode_customer = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cbx_top = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_order_detail = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txt_nama_customer = new javax.swing.JTextField();
        txt_alamat_customer = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txt_tgl_order = new javax.swing.JTextField();
        txt_no_order = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        cbx_salesman = new javax.swing.JComboBox<>();
        txt_total_harga = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        cek_cetak = new javax.swing.JCheckBox();
        lbl_jumlah_item = new javax.swing.JTextField();
        lbl_jumlah_qty = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_ket_verifikasi = new javax.swing.JTextArea();

        comTableBarang.setEditable(true);
        comTableBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comTableBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableBarangActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 204));
        jLabel14.setText("Customer");

        txt_kode_customer.setEditable(false);
        txt_kode_customer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_kode_customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_kode_customerMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 204));
        jLabel16.setText("No. Order");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 204));
        jLabel17.setText("T.O.P");

        cbx_top.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_topKeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Keterangan Verifikasi");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.white, java.awt.Color.white));

        tbl_order_detail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "Kode", "Barang", "Jumlah", "Harga"
            }
        ));
        tbl_order_detail.setCellSelectionEnabled(true);
        tbl_order_detail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_order_detailKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_order_detail);
        if (tbl_order_detail.getColumnModel().getColumnCount() > 0) {
            tbl_order_detail.getColumnModel().getColumn(0).setResizable(false);
            tbl_order_detail.getColumnModel().getColumn(1).setResizable(false);
            tbl_order_detail.getColumnModel().getColumn(2).setResizable(false);
            tbl_order_detail.getColumnModel().getColumn(2).setCellEditor(new ComboBoxCellEditor(comTableBarang));
            tbl_order_detail.getColumnModel().getColumn(3).setResizable(false);
            tbl_order_detail.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 204));
        jLabel24.setText("Tanggal");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setText("Total");

        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        jLabel21.setText("Save");
        jLabel21.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel21AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_yast_printer_30297.png"))); // NOI18N
        jLabel26.setText("Print");
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        jLabel27.setText("Esc-Exit");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/back-icon.png"))); // NOI18N
        jLabel28.setText("Back");
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
        });

        txt_nama_customer.setEditable(false);
        txt_nama_customer.setBackground(new java.awt.Color(184, 238, 184));
        txt_nama_customer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_nama_customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_nama_customerMouseClicked(evt);
            }
        });

        txt_alamat_customer.setEditable(false);
        txt_alamat_customer.setBackground(new java.awt.Color(184, 238, 184));
        txt_alamat_customer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_alamat_customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_alamat_customerMouseClicked(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(204, 0, 0));
        jLabel29.setText("Nama");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(204, 0, 0));
        jLabel30.setText("Alamat");

        txt_tgl_order.setEditable(false);
        txt_tgl_order.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_tgl_order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_tgl_orderMouseClicked(evt);
            }
        });

        txt_no_order.setEditable(false);
        txt_no_order.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_no_order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_no_orderMouseClicked(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 204));
        jLabel31.setText("Salesman");

        cbx_salesman.setEditable(true);
        cbx_salesman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_salesmanKeyPressed(evt);
            }
        });

        txt_total_harga.setEditable(false);
        txt_total_harga.setBackground(new java.awt.Color(204, 255, 204));
        txt_total_harga.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_total_harga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_total_hargaMouseClicked(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Kasir");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Nama Kasir");

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));

        cek_cetak.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cek_cetak.setForeground(new java.awt.Color(153, 0, 0));
        cek_cetak.setText("LGSG CETAK");

        lbl_jumlah_item.setBackground(new java.awt.Color(0, 0, 0));
        lbl_jumlah_item.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_jumlah_item.setForeground(new java.awt.Color(255, 204, 0));
        lbl_jumlah_item.setText("Jumlah Item");
        lbl_jumlah_item.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        lbl_jumlah_item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_jumlah_itemMouseClicked(evt);
            }
        });

        lbl_jumlah_qty.setBackground(new java.awt.Color(0, 0, 0));
        lbl_jumlah_qty.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_jumlah_qty.setForeground(new java.awt.Color(255, 204, 0));
        lbl_jumlah_qty.setText("Jumlah Qty");
        lbl_jumlah_qty.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        lbl_jumlah_qty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_jumlah_qtyMouseClicked(evt);
            }
        });

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/forward-icon.png"))); // NOI18N
        jLabel23.setText("Next");
        jLabel23.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel23AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        txt_ket_verifikasi.setColumns(20);
        txt_ket_verifikasi.setRows(5);
        txt_ket_verifikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ket_verifikasiKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txt_ket_verifikasi);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel32)
                        .addGap(291, 291, 291)
                        .addComponent(cek_cetak)
                        .addGap(47, 47, 47)
                        .addComponent(lbl_jumlah_item, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_jumlah_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_total_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel30))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_alamat_customer, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                                    .addComponent(txt_nama_customer, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                                    .addComponent(txt_kode_customer))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel24)
                                            .addComponent(jLabel16))
                                        .addGap(13, 13, 13)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txt_tgl_order, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel17)
                                                .addGap(21, 21, 21)
                                                .addComponent(cbx_top, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txt_no_order, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel31)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbx_salesman, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)))
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_kode_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(txt_tgl_order, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbx_top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nama_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(txt_no_order, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbx_salesman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel31))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_alamat_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel30)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txt_total_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cek_cetak)
                        .addComponent(lbl_jumlah_item, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_jumlah_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_kode_customerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_kode_customerMouseClicked
//       Penjualan_Penjualan_Pilihcustomer pppc = new Penjualan_Penjualan_Pilihcustomer();
//       pppc.setVisible(true);
//       pppc.setFocusable(true);
    }//GEN-LAST:event_txt_kode_customerMouseClicked

    private void txt_nama_customerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nama_customerMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nama_customerMouseClicked

    private void txt_alamat_customerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_alamat_customerMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_alamat_customerMouseClicked

    private void txt_tgl_orderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_tgl_orderMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tgl_orderMouseClicked

    private void txt_no_orderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_no_orderMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_no_orderMouseClicked

    private void txt_total_hargaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_total_hargaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_hargaMouseClicked

    private void lbl_jumlah_itemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_jumlah_itemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_jumlah_itemMouseClicked

    private void lbl_jumlah_qtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_jumlah_qtyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_jumlah_qtyMouseClicked

    private void jLabel21AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel21AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel21AncestorAdded

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        
        int simpan = 1;
        simpan = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menyimpan data ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if (simpan == 0) {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
            if(cek_cetak.isSelected()){
                //cetak_nota();   
                simpan_data();
                Penjualan_Penjualan_Print ppp = new Penjualan_Penjualan_Print(this.no_order);
                ppp.setVisible(true);
            }else{
                simpan_data();
            }
            
        }
    }//GEN-LAST:event_jLabel21MouseClicked
    

    void simpan_data() {
        String salesman = cbx_salesman.getSelectedItem().toString();
        String top = cbx_top.getSelectedItem().toString();
        String ket_verifikasi = txt_ket_verifikasi.getText();

        try {
            Koneksi Koneksi = new Koneksi();
            Connection con = Koneksi.configDB();
            Statement st = con.createStatement();
            int row = 0;

            //Mendapatkan id salesman
            String sql1a = "select kode_salesman from salesman where nama_salesman = '"+salesman+"'";
            System.out.println(sql1a);
            Statement st1a = con.createStatement();
            java.sql.ResultSet res1a = st1a.executeQuery(sql1a);
            while (res1a.next()) {
                salesman = res1a.getString("kode_salesman");
            }
            res1a.close();
            
            //Mendapatkan id top
            String sql1b = "select id_top from top where nama_top = '"+top+"'";
            System.out.println(sql1b);
            Statement st1b = con.createStatement();
            java.sql.ResultSet res1b = st1b.executeQuery(sql1b);
            while (res1b.next()) {
                top = res1b.getString("id_top");
            }
            res1b.close();

            //Mengupdate order umum
            String sql = "update `order` set keterangan_order='"+ket_verifikasi+"', id_top='"+top+"', kode_pegawai='"+salesman+"' where no_faktur_order='"+this.no_order+"'";
            System.out.println(sql);
            row = st.executeUpdate(sql);
            
            if(row>0){
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf, terjadi kesalahan");
        }

    }
    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        Penjualan_Penjualan_Print ppp = new Penjualan_Penjualan_Print(this.no_order);
                ppp.setVisible(true);
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jLabel23AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel23AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel23AncestorAdded

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        nextOrder();
    }//GEN-LAST:event_jLabel23MouseClicked

    private void jLabel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseClicked
        prevOrder();
    }//GEN-LAST:event_jLabel28MouseClicked

    private void comTableBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableBarangActionPerformed
        load_dari_nama_barang();
    }//GEN-LAST:event_comTableBarangActionPerformed
    void keluar_pop_up() {
        awal.setFocusable(true);
        this.setVisible(false);
        awal.loadData();
    }
    private void tbl_order_detailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_order_detailKeyPressed
        DefaultTableModel model = (DefaultTableModel) tbl_order_detail.getModel();
        int selectedRow = tbl_order_detail.getSelectedRow();
        System.out.println(selectedRow);

        TableModel tabelModel;
        tabelModel = tbl_order_detail.getModel();
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                keluar_pop_up();
            }
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter
                if (tabelModel.getValueAt(tbl_order_detail.getSelectedRow(), 4).toString().equals("") || tabelModel.getValueAt(tbl_order_detail.getSelectedRow(), 3).toString().equals("") || tabelModel.getValueAt(tbl_order_detail.getSelectedRow(), 2).toString().equals("") || tabelModel.getValueAt(tbl_order_detail.getSelectedRow(), 1).toString().equals("")) {
                    throw new NullPointerException();
                } else {
                    if (tbl_order_detail.getSelectedColumn() == 4) {
                        model.addRow(new Object[]{"", "", "", "", "", "", ""});
                    }

                }
            } else if (evt.getKeyCode() == KeyEvent.VK_F12) {
                int simpan_data = 1;
                simpan_data = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menyimpan data ini ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
                if (simpan_data == 0) {
                    //simpan_data();
                }

            } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && tbl_order_detail.getSelectedColumn() == 2) {
                InputMap im = tbl_order_detail.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
                KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
                KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
                im.put(down, im.get(f2));
                System.out.println("asd");

            } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && tbl_order_detail.getSelectedColumn() != 2) {
                InputMap im = tbl_order_detail.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
                KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
                KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
                im.put(f2, null);
                im.put(down, null);
                System.out.println("fgh");
            }

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Data harus lengkap");

        }

        loadNumberTable();
    }//GEN-LAST:event_tbl_order_detailKeyPressed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        keluar_pop_up();
    }//GEN-LAST:event_formWindowClosed

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        keluar_pop_up();
    }//GEN-LAST:event_jLabel27MouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_F12){
            int simpan_data = 1;
                simpan_data = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menyimpan data ini ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
                if (simpan_data == 0) {
                    //simpan_data();
                }
        }
    }//GEN-LAST:event_formKeyPressed

    private void txt_ket_verifikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ket_verifikasiKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_F12){
            int simpan_data = 1;
                simpan_data = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menyimpan data ini ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
                if (simpan_data == 0) {
                    simpan_data();
                }
        }
    }//GEN-LAST:event_txt_ket_verifikasiKeyPressed

    private void cbx_salesmanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_salesmanKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_F12){
            int simpan_data = 1;
                simpan_data = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menyimpan data ini ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
                if (simpan_data == 0) {
                    simpan_data();
                }
        }
    }//GEN-LAST:event_cbx_salesmanKeyPressed

    private void cbx_topKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_topKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_F12){
            int simpan_data = 1;
                simpan_data = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menyimpan data ini ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
                if (simpan_data == 0) {
                    simpan_data();
                }
        }
    }//GEN-LAST:event_cbx_topKeyPressed

    void loadNumberTable() {
        int baris = tbl_order_detail.getRowCount();
        for (int a = 0; a < baris; a++) {
            String nomor = String.valueOf(a + 1);
            tbl_order_detail.setValueAt(nomor + ".", a, 0);
        }

    }

    void load_dari_nama_barang() {
        int selectedRow = tbl_order_detail.getSelectedRow();
        String nama_awal = String.valueOf(comTableBarang.getSelectedItem());
        String[] split = new String[2];
        System.out.println("nilai comTable barang adalah " + comTableBarang.getSelectedItem());
        if (comTableBarang.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }
        try {
            String sql = "select kode_barang,nama_barang from barang where kode_barang = '" + split[0] + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString("kode_barang");
                String nama = res.getString("nama_barang");
                //this.kode_barang = Integer.parseInt(kode);

                if (selectedRow != -1) {
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
                    tbl_order_detail.setValueAt(kode, selectedRow, 1);
                    tbl_order_detail.setValueAt(nama, selectedRow, 2);
                }
            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

    void loadComboNama(String key) {
        Runnable doHighlight = new Runnable() {
            @Override
            public void run() {
                System.out.println("ini load combo nama");
                try {
                    String sql = "select concat(kode_barang,\"-\",nama_barang) as gabung from barang where kode_barang ='" + key + "' OR nama_barang like '%" + key + "%'";
                    System.out.println(sql);
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    System.out.println("ini sql com kode nama " + sql);
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
                    comTableBarang.setModel(new DefaultComboBoxModel(kode_nama_arr.toArray()));
                    ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).setText(key);
                    conn.close();
                    res.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                    e.printStackTrace();
                }

            }
        };
        SwingUtilities.invokeLater(doHighlight);
    }

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
            java.util.logging.Logger.getLogger(Penjualan_Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Penjualan_Order_DetailOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbx_salesman;
    private javax.swing.JComboBox<String> cbx_top;
    private javax.swing.JCheckBox cek_cetak;
    private javax.swing.JComboBox<String> comTableBarang;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField lbl_jumlah_item;
    private javax.swing.JTextField lbl_jumlah_qty;
    private javax.swing.JTable tbl_order_detail;
    private javax.swing.JTextField txt_alamat_customer;
    private javax.swing.JTextArea txt_ket_verifikasi;
    private javax.swing.JTextField txt_kode_customer;
    private javax.swing.JTextField txt_nama_customer;
    private javax.swing.JTextField txt_no_order;
    private javax.swing.JTextField txt_tgl_order;
    private javax.swing.JTextField txt_total_harga;
    // End of variables declaration//GEN-END:variables
}
