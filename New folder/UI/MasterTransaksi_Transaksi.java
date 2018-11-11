/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Clock;
import Java.Currency_Column;
import Java.koneksi;
import com.toedter.calendar.JCalendar;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author 7
 */
public class MasterTransaksi_Transaksi extends javax.swing.JFrame {

    private Statement statement;
    ResultSet res;
    static Color[] colors = {Color.WHITE, Color.GRAY, Color.RED};

    //Connection con = koneksi.getKoneksi();
    private static Connection koneksi;

    private static Connection buka_koneksi() {
        if (koneksi == null) {
            try {
                String driver = "com.mysql.jdbc.Driver";
                String url = "jdbc:mysql://localhost:3306/elecomp_cat73"; //nama database 
                String user = "root"; //user mysql
                String password = ""; //password mysql

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);

            } catch (SQLException t) {
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan " + t.getMessage());
            }
        }
        return koneksi;

    }

    public void tampilkandata() {

        Object header[] = {"NO", "TANGGAL", "URAIAN", "POST KEUANGAN", "PEMASUKAN", "PENGELUARAN", "BIAYA", "KETERANGAN"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        jTable1.setModel(data);

        String sql = "select transaksi_master.kode_transaksi_master,transaksi_master.tgl_transaksi_master,transaksi_akun.nama_transaksi_akun,transaksi_nama_keuangan.nama_keuangan,transaksi_master.debit_transaksi_master,transaksi_master.kredit_transaksi_master, transaksi_master.bayar_keuangan,transaksi_master.keterangan_transaksi_master "
                + "from transaksi_master INNER JOIN transaksi_akun ON transaksi_master.no_faktur=transaksi_akun.id_transaksi_akun INNER JOIN transaksi_nama_keuangan on transaksi_master.kode_nama_keuangan = transaksi_nama_keuangan.kode_nama_keuangan ORDER BY transaksi_master.tgl_transaksi_master DESC";
        try {
            buka_koneksi();
            Statement stat = koneksi.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                String kolom1 = res.getString(1);
                String kolom2 = res.getString(2);
                String kolom3 = res.getString(3);
                String kolom4 = res.getString(4);
                int kolom5 = res.getInt(5);
                int kolom6 = res.getInt(6);
                int kolom7 = res.getInt(7);
                String kolom8 = res.getString(8);
                String kolom[] = {kolom1, kolom2, kolom3, kolom4, currency_convert(kolom5), currency_convert(kolom6), currency_convert(kolom7), kolom8};
                data.addRow(kolom);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error :" + e.getMessage());
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

    /**
     * Creates new form MasterTransaksi_Transaksi
     */
    public void calender() {
        Calendar kal = new GregorianCalendar();
        int tahun = kal.get(Calendar.YEAR);
        int bulan = kal.get(Calendar.MONTH) + 1;
        int hari = kal.get(Calendar.DAY_OF_MONTH);
        String tanggal = tahun + "-" + bulan + "-" + hari;
        jLabel10.setText(tanggal);
        jLabel6.setText(tanggal);
    }

    /**
     * Creates new form MasterTransaksi_Transaksi
     */
//untuk perintah penampilan jam
    public void setJam() {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String nol_jam = "", nol_menit = "", nol_detik = "";

                java.util.Date dateTime = new java.util.Date();
                int nilai_jam = dateTime.getHours();
                int nilai_menit = dateTime.getMinutes();
                int nilai_detik = dateTime.getSeconds();

                if (nilai_jam <= 9) {
                    nol_jam = "0";
                }
                if (nilai_menit <= 9) {
                    nol_menit = "0";
                }
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }

                String waktu = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);

                jLabel9.setText(waktu + ":" + menit + ":" + detik + "");
                jLabel7.setText(waktu + ":" + menit + ":" + detik + "");

            }
        };
        new Timer(1000, taskPerformer).start();
    }


    public MasterTransaksi_Transaksi() {
        initComponents();
//        setTanggal();
        setJam();
        calender();
        tampil_combo1();
        tampil_combo2();
        tampilkandata();
        this.setExtendedState(MAXIMIZED_BOTH);
        txt_debit.setVisible(false);
        txt_kredit.setVisible(false);
        txtfield_jenis_keuangan.setVisible(false);
        txtfield_transaksi.setVisible(false);
        txtfield_jenis_keuangan.setVisible(false);
        txtfield_transaksi.setVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    private void jajal() {

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField90 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jComboBox2 = new javax.swing.JComboBox<String>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<String>();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtfield_transaksi = new javax.swing.JTextField();
        txtfield_jenis_keuangan = new javax.swing.JTextField();
        txt_debit = new javax.swing.JTextField();
        txt_kredit = new javax.swing.JTextField();
        jcombo_status = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "NO", "TANGGAL", "URAIAN", "POST KEUANGAN", "PEMASUKAN", "PENGELUARAN", "KETERANGAN"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jTextField90.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cari", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        jTextField90.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField90ActionPerformed(evt);
            }
        });
        jTextField90.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField90KeyReleased(evt);
            }
        });

        jLabel11.setText("Tanggal");

        jDateChooser1.setDateFormatString("yyyy-MM-dd");
        jDateChooser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDateChooser1MouseClicked(evt);
            }
        });

        jDateChooser2.setDateFormatString("yyyy-MM-dd");
        jDateChooser2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDateChooser2MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jDateChooser2MouseReleased(evt);
            }
        });
        jDateChooser2.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jDateChooser2InputMethodTextChanged(evt);
            }
        });
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });
        jDateChooser2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooser2KeyReleased(evt);
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField90, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Jenis Keuangan");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Total");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jComboBox2.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBox2PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Nama Uraian");

        jComboBox1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBox1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Keterangan");

        txtfield_transaksi.setText("0");

        txtfield_jenis_keuangan.setText("0");

        txt_debit.setText("0");

        txt_kredit.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(58, 58, 58)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcombo_status, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_debit, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_kredit, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtfield_jenis_keuangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfield_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtfield_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_debit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_kredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtfield_jenis_keuangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jcombo_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("waktu");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("Terbaru Transaksi");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("tanggal");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(19, 19, 19))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Transaksi pemasukan dan pengeluaran");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("tanggal");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("waktu");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(16, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(686, 724));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Connection c = buka_koneksi(); //panggil function koneksi
        //String sql = "select*from transaksi_master join transaksi_Akun";

        if (jcombo_status.getSelectedItem().equals("Pemasukan")) {
            String sql = "insert into transaksi_master(faktur_bp,seri_bg,no_faktur,tgl_transaksi_master,debit_transaksi_master,kredit_transaksi_master,status_transaksi_master,kebijakan_transaksi_master,keterangan_transaksi_master,kode_nama_keuangan,bayar_keuangan,kode_nama_keuangan2,bayar_keuangan2,kode_pegawai,kode_salesman,tgl_tutup_buku,tgl_filter_tutup_buku,tgl_bg) "
                    + "VALUES ('" + 0 + "','" + 0 + "','" + txtfield_transaksi.getText() + "','" + jLabel10.getText() + " " + jLabel9.getText() + "','" + jTextField1.getText() + "','" + 0 + "','" + 0 + "','" + 0 + "','" + jTextArea1.getText() + "','" + txtfield_jenis_keuangan.getText() + "','" + jTextField1.getText() + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + "0000-00-00 00:00:00" + "','" + "0000-00-00" + "','" + "0000-00-00" + "')";
            int totalakhir;

            String total = jTextField1.getText();
            String debit = txt_debit.getText();
            int total1 = Integer.parseInt(total);
            int debit1 = Integer.parseInt(debit);
            totalakhir = total1 + debit1;

//            String nama_awal = String.valueOf(jComboBox1.getSelectedItem());
//            String[] split = new String[5];        
//            split = nama_awal.split(" ");
//            System.out.println("ID Transaksi " + split[0]);
            String sql_update_tr_akun = "UPDATE transaksi_akun set debit_transaksi_akun = '" + totalakhir + "' WHERE nama_transaksi_akun ='" + jComboBox1.getSelectedItem() + "'";
            System.out.println(sql_update_tr_akun);
            try {
                PreparedStatement q1 = (PreparedStatement) c.prepareStatement(sql);
                q1.executeUpdate();
                q1.close();
                PreparedStatement q2 = (PreparedStatement) c.prepareStatement(sql_update_tr_akun);
                q2.executeUpdate();
                q2.close();
                jTextField1.setText(" ");
                jTextArea1.setText(" ");
                tampilkandata();
                JOptionPane.showMessageDialog(this, "Simpan Berhasil", "Informasi", 1);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan " + ex.getMessage());

            }
        } else {

            String sql = "insert into transaksi_master(faktur_bp,seri_bg,no_faktur,tgl_transaksi_master,debit_transaksi_master,kredit_transaksi_master,status_transaksi_master,kebijakan_transaksi_master,keterangan_transaksi_master,kode_nama_keuangan,bayar_keuangan,kode_nama_keuangan2,bayar_keuangan2,kode_pegawai,kode_salesman,tgl_tutup_buku,tgl_filter_tutup_buku,tgl_bg) "
                    + "VALUES ('" + 0 + "','" + 0 + "','" + txtfield_transaksi.getText() + "','" + jLabel10.getText() + " " + jLabel9.getText() + "','" + 0 + "','" + jTextField1.getText() + "','" + 0 + "','" + 2 + "','" + jTextArea1.getText() + "','" + txtfield_jenis_keuangan.getText() + "','" + jTextField1.getText() + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + "0000-00-00 00:00:00" + "','" + "0000-00-00" + "','" + "2018-06-28" + "')";
            int totalakhir;
            String total = jTextField1.getText();
            String kredit = txt_kredit.getText();
            int total1 = Integer.parseInt(total);
            int kredit1 = Integer.parseInt(kredit);
            totalakhir = total1 + kredit1;

            String nama_awal = String.valueOf(jComboBox1.getSelectedItem());
            String[] split = new String[5];
            split = nama_awal.split(" ");
            System.out.println("ID Transaksi " + split[0]);
            String sql_update_tr_akun = "UPDATE transaksi_akun set kredit_transaksi_akun = '" + totalakhir + "' WHERE id_transaksi_akun ='" + split[0] + "' ";
            System.out.println(sql_update_tr_akun);
            try {
                PreparedStatement q1 = (PreparedStatement) c.prepareStatement(sql);
                q1.executeUpdate();
                q1.close();
                PreparedStatement q2 = (PreparedStatement) c.prepareStatement(sql_update_tr_akun);
                q2.executeUpdate();
                q2.close();
                jTextField1.setText(" ");
                jTextArea1.setText(" ");
                //tampilkan data
                tampilkandata();
                JOptionPane.showMessageDialog(this, "Simpan Berhasil", "Informasi", 1);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan " + ex.getMessage());

            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code her
        tampil();

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        tampilcombo2();
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBox1PopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        jComboBox2.requestFocus();
    }//GEN-LAST:event_jComboBox1PopupMenuWillBecomeInvisible

    private void jTextField90KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField90KeyReleased
        // TODO add your handling code here:
//        jTextField2.setText("");
//        jTextField3.setText("");
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
        if (jTextField90.getText().equals(" ")) {
            tampilkandata();
            jTextField90.requestFocus();
        } else {
            Object header[] = {"NO", "TANGGAL", "URAIAN", "POST KEUANGAN", "PEMASUKAN", "PENGELUARAN", "BIAYA", "KETERANGAN"};
            DefaultTableModel data = new DefaultTableModel(null, header);
            jTable1.setModel(data);

            //String sql = "Select * from transaksi_master where tgl_transaksi_master between '"+jTextField2.getText()+"' AND '"+jTextField3.getText()+"'";
            String sql = "select transaksi_master.kode_transaksi_master,transaksi_master.tgl_transaksi_master,transaksi_akun.nama_transaksi_akun,transaksi_nama_keuangan.nama_keuangan,transaksi_master.debit_transaksi_master,transaksi_master.kredit_transaksi_master,transaksi_master.keterangan_transaksi_master,\n"
                    + "transaksi_master.bayar_keuangan from transaksi_master INNER JOIN transaksi_akun ON transaksi_master.no_faktur=transaksi_akun.id_transaksi_akun INNER JOIN transaksi_nama_keuangan on transaksi_master.kode_nama_keuangan = transaksi_nama_keuangan.kode_nama_keuangan WHERE transaksi_akun.nama_transaksi_akun LIKE '%" + jTextField90.getText() + "%'";
            try {
//            System.out.println(sql);
                buka_koneksi();
                Statement stat = koneksi.createStatement();
                ResultSet res = stat.executeQuery(sql);
                while (res.next()) {
                    String kolom1 = res.getString(1);
                    String kolom2 = res.getString(2);
                    String kolom3 = res.getString(3);
                    String kolom4 = res.getString(4);
                    String kolom5 = res.getString(5);
                    String kolom6 = res.getString(6);
                    String kolom7 = res.getString(8);
                    String kolom8 = res.getString(7);

                    String kolom[] = {kolom1, kolom2, kolom3, kolom4, kolom5, kolom6, kolom7, kolom8};
                    data.addRow(kolom);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "error :" + e.getMessage());
            }
        }
    }//GEN-LAST:event_jTextField90KeyReleased

    private void jTextField90ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField90ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField90ActionPerformed

    private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox1KeyPressed

    private void jComboBox2PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBox2PopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        jTextField1.requestFocus();
    }//GEN-LAST:event_jComboBox2PopupMenuWillBecomeInvisible

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
//        jTextArea1.requestFocus();
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jDateChooser2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooser2KeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jDateChooser2KeyReleased

    private void jDateChooser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateChooser1MouseClicked
        // TODO add your handling code here:
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String folderName = df.format(today);
        jDateChooser1.setDateFormatString(folderName);
    }//GEN-LAST:event_jDateChooser1MouseClicked

    private void jDateChooser2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateChooser2MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jDateChooser2MouseReleased

    private void jDateChooser2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateChooser2MouseClicked
        // TODO add your handling code here:
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String folderName = df.format(today);
        jDateChooser1.setDateFormatString(folderName);


    }//GEN-LAST:event_jDateChooser2MouseClicked

    private void jDateChooser2InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jDateChooser2InputMethodTextChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jDateChooser2InputMethodTextChanged

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        // TODO add your handling code here:
        Date date = jDateChooser2.getDate();
        if (date == null) {
            tampilkandata();
        } else {
            Object header[] = {"NO", "TANGGAL", "URAIAN", "POST KEUANGAN", "PEMASUKAN", "PENGELUARAN", "BIAYA", "KETERANGAN"};
            DefaultTableModel data = new DefaultTableModel(null, header);
            jTable1.setModel(data);

            String sql = "select transaksi_master.kode_transaksi_master,"
                    + "transaksi_master.tgl_transaksi_master,"
                    + "transaksi_akun.nama_transaksi_akun,"
                    + "transaksi_nama_keuangan.nama_keuangan,"
                    + "transaksi_master.debit_transaksi_master,"
                    + "transaksi_master.kredit_transaksi_master,"
                    + "transaksi_master.keterangan_transaksi_master, "
                    + "transaksi_master.bayar_keuangan from transaksi_master INNER JOIN "
                    + "transaksi_akun ON transaksi_master.no_faktur=transaksi_akun.id_transaksi_akun "
                    + "INNER JOIN transaksi_nama_keuangan "
                    + "on transaksi_master.kode_nama_keuangan = transaksi_nama_keuangan.kode_nama_keuangan "
                    + "WHERE transaksi_master.tgl_transaksi_master "
                    + "BETWEEN '" + ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText() + "' AND '" + ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText() + "' ORDER BY transaksi_master.tgl_transaksi_master DESC";
            System.out.println(sql);
            try {

                buka_koneksi();
                Statement stat = koneksi.createStatement();
                ResultSet res = stat.executeQuery(sql);
                while (res.next()) {
                    String kolom1 = res.getString(1);
                    String kolom2 = res.getString(2);
                    String kolom3 = res.getString(3);
                    int kolom4 = res.getInt(4);
                    int kolom5 = res.getInt(5);
                    String kolom6 = res.getString(6);
                    String kolom7 = res.getString(8);
                    String kolom8 = res.getString(7);

                    String kolom[] = {kolom1, kolom2, kolom3, currency_convert(kolom4), currency_convert(kolom5), kolom6, kolom7, kolom8};
                    data.addRow(kolom);
                }
                TableColumnModel m = jTable1.getColumnModel();
                m.getColumn(4).setCellRenderer(new Currency_Column());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "error :" + e.getMessage());
            }
        }
    }//GEN-LAST:event_jDateChooser2PropertyChange

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
            java.util.logging.Logger.getLogger(MasterTransaksi_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MasterTransaksi_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MasterTransaksi_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MasterTransaksi_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MasterTransaksi_Transaksi().setVisible(true);
            }
        });
    }

    public void tampil_combo1() {
        try {
            buka_koneksi();
            Statement stt = koneksi.createStatement();
            String sql = "SELECT * FROM transaksi_akun WHERE NOT status_debit_kredit = 0";
            ResultSet res = stt.executeQuery(sql);
            while (res.next()) {
                Object[] ob = new Object[5];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);
                ob[2] = res.getString(6);
                jComboBox1.addItem((String) ob[1]);

//            jComboBox1.removeItemAt(1);
            }
            res.close();
            stt.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void tampil() {
//        String nama_awal = String.valueOf(jComboBox1.getSelectedItem());
//        String[] split = new String[5];        
//            split = nama_awal.split(" ");
//            System.out.println("nilai comTable barang adalah " + split[1]);

        try {
            buka_koneksi();
            Statement stt = koneksi.createStatement();
            String sql = "select * from transaksi_akun where nama_transaksi_akun = '" + jComboBox1.getSelectedItem() + "'";
            System.out.println(sql);
            ResultSet res = stt.executeQuery(sql);

            while (res.next()) {
                Object[] ob = new Object[5];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);
                ob[2] = res.getString(6);
                ob[3] = res.getString(3);
                ob[4] = res.getString(4);
                if (ob[2].equals("1")) {
                    ob[2] = "Pemasukan";
                } else {
                    ob[2] = "Pengeluaran";
                }
                jcombo_status.removeAllItems();
                jcombo_status.addItem(ob[2]);
                txtfield_transaksi.setText((String) ob[0]);
//            txtfield_status_akun.setText((String) ob[2]);
                txt_debit.setText((String) ob[3]);
                txt_kredit.setText((String) ob[4]);
            }
            res.close();
            stt.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void tampil_combo2() {
        try {
            buka_koneksi();
            Statement stt = koneksi.createStatement();
            String sql = "SELECT * FROM transaksi_nama_keuangan";
            ResultSet res = stt.executeQuery(sql);

            while (res.next()) {
                Object[] ob = new Object[3];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);

                jComboBox2.addItem((String) ob[1]);
            }
            res.close();
            stt.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void tampilcombo2() {
        try {
            buka_koneksi();
            Statement stt = koneksi.createStatement();
            String sql = "select * from transaksi_nama_keuangan where nama_keuangan='" + jComboBox2.getSelectedItem() + "'";
            ResultSet res = stt.executeQuery(sql);

            while (res.next()) {
                Object[] ob = new Object[3];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);
                ob[2] = res.getString(3);

                txtfield_jenis_keuangan.setText((String) ob[0]);
            }
            res.close();
            stt.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField90;
    private javax.swing.JComboBox jcombo_status;
    private javax.swing.JTextField txt_debit;
    private javax.swing.JTextField txt_kredit;
    private javax.swing.JTextField txtfield_jenis_keuangan;
    private javax.swing.JTextField txtfield_transaksi;
    // End of variables declaration//GEN-END:variables
}
