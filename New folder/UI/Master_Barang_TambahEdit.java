package UI;

import Class.Koneksi;
import java.sql.Connection;
import com.sun.glass.events.KeyEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Java.*;
import javax.swing.JOptionPane;
import Java.ListBarang;
import Java.TrBarang;
import java.sql.PreparedStatement;

public class Master_Barang_TambahEdit extends javax.swing.JDialog {

//    Object
//    private ResultSet hasil1;
    private Connect connection;
    private ResultSet hasil;
    private PreparedStatement PS;
    private ListBarang listBarang;
    private TrBarang TrBarang;
//    Var
    private boolean isEdit = false;
    private int id;

    public Master_Barang_TambahEdit() {
        initComponents();
        this.setVisible(true);
        loadComkelompok("*");
        loadComsatuan1("*");
        loadComsatuan2("*");
        loadComtop("*");
//        this.setLocationRelativeTo(null);
    }

    public Master_Barang_TambahEdit(java.awt.Dialog parent, boolean modal, int id, boolean edit) {
        super(parent, modal);
        initComponents();
//        this.setVisible(true);
        this.id = id;
        this.isEdit = edit;

        loadComkelompok("*");
        loadComsatuan1("*");
        loadComsatuan2("*");
        loadComtop("*");
        txt_kodebarang.setText(String.valueOf(id));
        System.out.println("tambahhhhh");

//        this.setLocationRelativeTo(null);
    }

    Master_Barang_TambahEdit(java.awt.Dialog parent, boolean modal, Connect connection, String kode, boolean edit) {
        super(parent, modal);
        initComponents();
        this.connection = connection;
//        this.setVisible(true);
        this.id = id;
        this.listBarang = listBarang;
        this.isEdit = edit;

        setContent(kode);
        loadComkelompok("*");
        loadComtop("*");
        loadComsatuan1("*");
        loadComsatuan2("*");
        System.out.println("bbbb1: ");
    }

    private void setContent(String kode) {
        try {
            String sqlBarang = "SELECT * from barang where kode_barang ='"
                    + kode + "'";
            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sqlBarang);
            System.out.println("eror a???");

            while (res.next()) {
                txt_kodebarang.setText(kode);
                txt_namabarang.setText(res.getString("nama_barang"));
                pre_order.setText(res.getString("reorder_barang"));
                txt_harga_jual1.setText(res.getString("harga_jual_1_barang"));
                txt_harga_jual2.setText(res.getString("harga_jual_2_barang"));
                txt_harga_jual3.setText(res.getString("harga_jual_3_barang"));
                txt_komisi_hj1.setText(res.getString("komisi_harga_jual_1_barang"));
                txt_komisi_hj2.setText(res.getString("komisi_harga_jual_2_barang"));

                txt_denda_hj1.setText(res.getString("denda_harga_jual_1"));
                txt_denda_hj2.setText(res.getString("denda_harga_jual_2"));

                loadComkelompok(res.getString("id_kelompok"));
                loadComtop(res.getString("id_top"));
                loadComsatuan1(kode);
                loadComsatuan2(kode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e + "ini eror");
        }
    }

    void loadComkelompok(String param) {
        try {
            String sql = "select * from kelompok";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            System.out.println("kel: " + sql);
            com_kelompok.removeAllItems();
            while (res.next()) {
                String name = res.getString(3);
                com_kelompok.addItem(name);
                if (param.equals(res.getString("id_kelompok"))) {
                    com_kelompok.setSelectedItem(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadComtop(String param) {

        try {
            String sql = "select * from top";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            System.out.println("top: " + sql);
            com_top.removeAllItems();
            while (res.next()) {
                String name = res.getString(2);
                com_top.addItem(name);
                if (param.equals(res.getString("id_top"))) {
                    com_top.setSelectedItem(name);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadComsatuan1(String param) {
        try {
            String sql = "select bk.kode_barang,bk.identitas_konversi, bk.kode_barang_konversi,k.nama_konversi from konversi "
                    + "k,barang_konversi bk where bk.kode_konversi = k.kode_konversi";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(4);
                com_satuan1.addItem(name);
                if (param.equals(res.getString("kode_barang")) && res.getString("identitas_konversi").equals("1")) {
                    com_satuan1.setSelectedItem(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadComsatuan2(String param) {

        try {
            String sql = "select bk.kode_barang,bk.identitas_konversi, bk.kode_barang_konversi,k.nama_konversi from konversi "
                    + "k,barang_konversi bk where bk.kode_konversi = k.kode_konversi";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(4);
                com_satuan2.addItem(name);
                if (param.equals(res.getString("kode_barang")) && res.getString("identitas_konversi").equals("2")) {
                    com_satuan2.setSelectedItem(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txt_harga_jual2 = new javax.swing.JTextField();
        txt_harga_jual1 = new javax.swing.JTextField();
        txt_hargarata2 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        txt_komisi_hj1 = new javax.swing.JTextField();
        txt_komisi_hj2 = new javax.swing.JTextField();
        aktif = new javax.swing.JRadioButton();
        jumlah = new javax.swing.JTextField();
        txt_namabarang = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txt_hitungan1 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        com_satuan1 = new javax.swing.JComboBox<>();
        jLabel53 = new javax.swing.JLabel();
        txt_hitungan2 = new javax.swing.JTextField();
        com_satuan2 = new javax.swing.JComboBox<>();
        jLabel56 = new javax.swing.JLabel();
        nonaktif = new javax.swing.JRadioButton();
        com_kelompok = new javax.swing.JComboBox<>();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        com_top = new javax.swing.JComboBox<>();
        txt_kodebarang = new javax.swing.JTextField();
        pre_order = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        txt_harga_beli = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        txt_harga_jual3 = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        simpan = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel69 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        cekbox_group = new javax.swing.JCheckBox();
        txt_denda_hj1 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        txt_denda_hj2 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setSize(new java.awt.Dimension(870, 427));

        txt_harga_jual2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_harga_jual2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_harga_jual2MouseClicked(evt);
            }
        });
        txt_harga_jual2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_harga_jual2KeyPressed(evt);
            }
        });

        txt_harga_jual1.setText("\n");
        txt_harga_jual1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_harga_jual1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_harga_jual1MouseClicked(evt);
            }
        });
        txt_harga_jual1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_harga_jual1KeyPressed(evt);
            }
        });

        txt_hargarata2.setText("0");
        txt_hargarata2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_hargarata2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_hargarata2MouseClicked(evt);
            }
        });
        txt_hargarata2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_hargarata2KeyPressed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel44.setText("Harga Jual 3");

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setText("Harga Jual 2");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Gambar Produk"));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/upload.png"))); // NOI18N

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(226, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel48.setText("Komisi Hj 1 ");

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel49.setText("Komisi Hj 2");

        txt_komisi_hj1.setText("0");
        txt_komisi_hj1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_komisi_hj1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_komisi_hj1MouseClicked(evt);
            }
        });
        txt_komisi_hj1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_komisi_hj1KeyPressed(evt);
            }
        });

        txt_komisi_hj2.setText("0");
        txt_komisi_hj2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_komisi_hj2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_komisi_hj2MouseClicked(evt);
            }
        });
        txt_komisi_hj2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_komisi_hj2ActionPerformed(evt);
            }
        });
        txt_komisi_hj2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_komisi_hj2KeyPressed(evt);
            }
        });

        aktif.setBackground(new java.awt.Color(255, 153, 0));
        buttonGroup2.add(aktif);
        aktif.setSelected(true);
        aktif.setText("ACTIVE");
        aktif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                aktifKeyPressed(evt);
            }
        });

        jumlah.setBackground(new java.awt.Color(0, 0, 0));
        jumlah.setForeground(new java.awt.Color(255, 255, 0));
        jumlah.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        jumlah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jumlahMouseClicked(evt);
            }
        });
        jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jumlahKeyPressed(evt);
            }
        });

        txt_namabarang.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_namabarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_namabarangMouseClicked(evt);
            }
        });
        txt_namabarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_namabarangKeyPressed(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel50.setText("TOP");

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel51.setText("Pre Order");

        txt_hitungan1.setText("0");
        txt_hitungan1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_hitungan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_hitungan1MouseClicked(evt);
            }
        });
        txt_hitungan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_hitungan1KeyPressed(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel52.setText("Satuan 2");

        com_satuan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                com_satuan1KeyPressed(evt);
            }
        });

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel53.setText("Hitungan 2");

        txt_hitungan2.setText("0");
        txt_hitungan2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_hitungan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_hitungan2MouseClicked(evt);
            }
        });
        txt_hitungan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_hitungan2KeyPressed(evt);
            }
        });

        com_satuan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                com_satuan2KeyPressed(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel56.setText("Harga Jual 1");

        nonaktif.setBackground(new java.awt.Color(255, 153, 0));
        buttonGroup2.add(nonaktif);
        nonaktif.setText("DEACTIVE");
        nonaktif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nonaktifMouseClicked(evt);
            }
        });
        nonaktif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nonaktifActionPerformed(evt);
            }
        });

        com_kelompok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                com_kelompokKeyPressed(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel57.setText("Kode");

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel58.setText("Nama");

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel59.setText("Kelompok");

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel60.setText("Satuan 1");

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel62.setText("Hitungan 1");

        com_top.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_topActionPerformed(evt);
            }
        });
        com_top.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                com_topKeyPressed(evt);
            }
        });

        txt_kodebarang.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_kodebarang.setFocusable(false);
        txt_kodebarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_kodebarangMouseClicked(evt);
            }
        });
        txt_kodebarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kodebarangActionPerformed(evt);
            }
        });

        pre_order.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        pre_order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pre_orderMouseClicked(evt);
            }
        });
        pre_order.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pre_orderKeyPressed(evt);
            }
        });

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel63.setText("Harga Rata2");

        txt_harga_beli.setText("0");
        txt_harga_beli.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_harga_beli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_harga_beliMouseClicked(evt);
            }
        });
        txt_harga_beli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_harga_beliKeyPressed(evt);
            }
        });

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel65.setText("Harga Beli");

        txt_harga_jual3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_harga_jual3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_harga_jual3MouseClicked(evt);
            }
        });
        txt_harga_jual3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_harga_jual3KeyPressed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        jLabel64.setText("Esc-Exit");
        jLabel64.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel64MouseClicked(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        simpan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        simpan.setText("F12-Save");
        simpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                simpanMouseClicked(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel69.setText("Jumlah");

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel54.setText("Group");

        cekbox_group.setText("Group");
        cekbox_group.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cekbox_groupKeyPressed(evt);
            }
        });

        txt_denda_hj1.setText("0");
        txt_denda_hj1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_denda_hj1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_denda_hj1MouseClicked(evt);
            }
        });
        txt_denda_hj1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_denda_hj1ActionPerformed(evt);
            }
        });
        txt_denda_hj1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_denda_hj1KeyPressed(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel55.setText("Denda Hj1");

        txt_denda_hj2.setText("0");
        txt_denda_hj2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_denda_hj2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_denda_hj2MouseClicked(evt);
            }
        });
        txt_denda_hj2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_denda_hj2ActionPerformed(evt);
            }
        });
        txt_denda_hj2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_denda_hj2KeyPressed(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel61.setText("Denda Hj2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(com_satuan1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(com_satuan2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(com_top, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(com_kelompok, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                                        .addComponent(pre_order, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel62)
                                    .addComponent(jLabel53)
                                    .addComponent(jLabel63)
                                    .addComponent(jLabel54)
                                    .addComponent(jLabel49)
                                    .addComponent(jLabel48))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(txt_harga_beli, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txt_komisi_hj1)
                                                    .addComponent(txt_hitungan1)
                                                    .addComponent(txt_komisi_hj2)
                                                    .addComponent(txt_hitungan2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(txt_hargarata2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cekbox_group))
                                            .addGap(2, 2, 2))
                                        .addComponent(jumlah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(aktif)
                        .addGap(18, 18, 18)
                        .addComponent(nonaktif)
                        .addGap(41, 41, 41))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51)
                            .addComponent(jLabel59)
                            .addComponent(jLabel56)
                            .addComponent(jLabel57)
                            .addComponent(jLabel58)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel64))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel44)
                                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_harga_jual1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txt_harga_jual3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_harga_jual2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel69)
                                            .addComponent(jLabel65)))))
                            .addComponent(jLabel52)
                            .addComponent(jLabel60))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel55)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_denda_hj1))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel61)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_denda_hj2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
            .addComponent(jSeparator1)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator2)
                    .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_denda_hj2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel61)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_denda_hj1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel55)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel48)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel49)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel54)
                                                .addGap(16, 16, 16)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel62)
                                                    .addComponent(com_satuan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(com_kelompok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pre_order, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(7, 7, 7)
                                                .addComponent(com_top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel53))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel59)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel51)
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel50)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel60)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(com_satuan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel52))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_harga_jual1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel56)
                                    .addComponent(jLabel63))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_harga_jual2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel46)
                                    .addComponent(jLabel65))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel69)
                                    .addComponent(txt_harga_jual3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel44)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(txt_komisi_hj2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_komisi_hj1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cekbox_group)
                                .addGap(5, 5, 5)
                                .addComponent(txt_hitungan1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_hitungan2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_hargarata2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_harga_beli, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(aktif)
                            .addComponent(nonaktif))))
                .addGap(14, 14, 14)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 428, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(882, 467));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_harga_jual2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_harga_jual2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_harga_jual2MouseClicked

    private void txt_harga_jual1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_harga_jual1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_harga_jual1MouseClicked

    private void txt_hargarata2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_hargarata2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargarata2MouseClicked

    private void txt_komisi_hj1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_komisi_hj1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_komisi_hj1MouseClicked

    private void txt_komisi_hj2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_komisi_hj2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_komisi_hj2MouseClicked

    private void txt_komisi_hj2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_komisi_hj2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_komisi_hj2ActionPerformed

    private void jumlahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jumlahMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahMouseClicked

    private void txt_namabarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_namabarangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namabarangMouseClicked

    private void txt_hitungan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_hitungan1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hitungan1MouseClicked

    private void txt_hitungan2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_hitungan2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hitungan2MouseClicked

    private void nonaktifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nonaktifActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nonaktifActionPerformed

    private void com_topActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_topActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_com_topActionPerformed

    private void txt_kodebarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_kodebarangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kodebarangMouseClicked

    private void pre_orderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pre_orderMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pre_orderMouseClicked

    private void txt_harga_beliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_harga_beliMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_harga_beliMouseClicked

    private void txt_harga_jual3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_harga_jual3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_harga_jual3MouseClicked

    private void simpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_simpanMouseClicked
        try {
//            //status barang
            String sttBarang = "0";
            if (aktif.isSelected()) {
                sttBarang = "1";
            }
            Connection conn = (Connection) Koneksi.configDB();
            Statement stm = conn.createStatement();
            Statement stat = conn.createStatement();

            //kelompok
            String sqlKelompok = "select id_kelompok from kelompok where nama_kelompok = '" + com_kelompok.getSelectedItem() + "'";
//            stat.executeQuery(sqlKelompok);
            java.sql.ResultSet res = stm.executeQuery(sqlKelompok);
            String idkelompok = "0";
            while (res.next()) {
                idkelompok = res.getString("id_kelompok");
            }

            //top
            String sqlTop = "select id_top from top where nama_top = '" + com_top.getSelectedItem() + "'";
//            stat.executeQuery(sqlTop);
            java.sql.ResultSet result = stm.executeQuery(sqlTop);
            String idtop = "0";
            while (result.next()) {
                idtop = result.getString("id_top");
            }
            String sqlA = null, sqlB = null;

            if (isEdit) {
                //update           
                sqlA = "update barang set "
                        + "nama_barang = '" + txt_namabarang.getText() + "',"
                        + "nama_foto = 0,"
                        + "id_kelompok = '" + idkelompok + "',"
                        + "harga_jual_1_barang = '" + txt_harga_jual1.getText() + "',"
                        + "harga_jual_2_barang = '" + txt_harga_jual2.getText() + "',"
                        + "harga_jual_3_barang = '" + txt_harga_jual3.getText() + "',"
                        + "harga_rata_rata_barang = 0,"
                        + "harga_beli = 0,"
                        + "id_top = '" + idtop + "',"
                        + "reorder_barang = " + pre_order.getText() + ","
                        + "status_barang = '" + sttBarang + "',"
                        + "komisi_harga_jual_1_barang = '" + txt_komisi_hj1.getText() + "',"
                        + "komisi_harga_jual_2_barang = '" + txt_komisi_hj2.getText() + "',"
                        + "status_group = 0,"
                        + "max_return = 0,"
                        + "is_delete = 0"
                        + " WHERE kode_barang = '" + txt_kodebarang.getText() + "'";
//             stat.executeUpdate(sqlA);
                System.out.println("true: " + sqlA);
                stat.executeUpdate(sqlA);
                System.out.println("true");

            } else {
                //insert
                sqlB = "INSERT INTO barang values ("
                        + "" + null + ","
                        + "'" + txt_kodebarang.getText() + "',"
                        + "0,"
                        + "'" + txt_namabarang.getText() + "',"
                        + "0,"
                        + "'" + idkelompok + "',"
                        + "'" + txt_harga_jual1.getText() + "',"
                        + "'" + txt_harga_jual2.getText() + "',"
                        + "'" + txt_harga_jual3.getText() + "',"
                        + "0,"
                        + "0,"
                        + "'" + idtop + "',"
                        + "'" + pre_order.getText() + "',"
                        + "'" + sttBarang + "',"
                        + "'" + txt_komisi_hj1.getText() + "',"
                        + "'" + txt_komisi_hj2.getText() + "',"
                        + "0,"
                        + "0,"
                        + "0"
                        + ")";

                stat.executeUpdate(sqlB);
                System.out.println("false");
            }
            System.out.println("sqla: " + sqlA + "\nsqlb: " + sqlB);
//                Connection conn = (Connection) Koneksi.configDB();

            JOptionPane.showMessageDialog(this, "Sukses");
            dispose();
            Master_Barang a = new Master_Barang();
            a.tampilTabel("1");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_simpanMouseClicked

    private void nonaktifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nonaktifMouseClicked
//        int deactive = JOptionPane.showConfirmDialog(null,"Apakah Stok di Ubah Menjadi 0 ?", "Konfimasi Perubahan Status" ,JOptionPane.OK_CANCEL_OPTION);
//        if(nonaktif == JOptionPane.OK_OPTION){
//        JOptionPane.showMessageDialog(null, "Data sudah ditolak.");
//        }
    }//GEN-LAST:event_nonaktifMouseClicked

    private void txt_denda_hj1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_denda_hj1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_denda_hj1MouseClicked

    private void txt_denda_hj1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_denda_hj1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_denda_hj1ActionPerformed

    private void txt_denda_hj2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_denda_hj2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_denda_hj2MouseClicked

    private void txt_denda_hj2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_denda_hj2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_denda_hj2ActionPerformed

    private void jLabel64MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel64MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel64MouseClicked

    private void txt_kodebarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kodebarangActionPerformed
        // TODO add your handling code here:
//       txt_kodebarang.setEditable(true);
//       txt_kodebarang.setEditable(false);
    }//GEN-LAST:event_txt_kodebarangActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
//            //status barang
            String sttBarang = "0";
            if (aktif.isSelected()) {
                sttBarang = "1";
            }
            Connection conn = (Connection) Koneksi.configDB();
            Statement stm = conn.createStatement();
            Statement stat = conn.createStatement();

            //kelompok
            String sqlKelompok = "select id_kelompok from kelompok where nama_kelompok = '" + com_kelompok.getSelectedItem() + "'";
//            stat.executeQuery(sqlKelompok);
            java.sql.ResultSet res = stm.executeQuery(sqlKelompok);
            String idkelompok = "0";
            while (res.next()) {
                idkelompok = res.getString("id_kelompok");
            }

            //top
            String sqlTop = "select id_top from top where nama_top = '" + com_top.getSelectedItem() + "'";
//            stat.executeQuery(sqlTop);
            java.sql.ResultSet result = stm.executeQuery(sqlTop);
            String idtop = "0";
            while (result.next()) {
                idtop = result.getString("id_top");
            }
            String sqlA = null, sqlB = null;

            if (isEdit) {
                //update           
                sqlA = "update barang set "
                        + "nama_barang = '" + txt_namabarang.getText() + "',"
                        + "nama_foto = 0,"
                        + "id_kelompok = '" + idkelompok + "',"
                        + "harga_jual_1_barang = '" + txt_harga_jual1.getText() + "',"
                        + "harga_jual_2_barang = '" + txt_harga_jual2.getText() + "',"
                        + "harga_jual_3_barang = '" + txt_harga_jual3.getText() + "',"
                        + "harga_rata_rata_barang = 0,"
                        + "harga_beli = 0,"
                        + "id_top = '" + idtop + "',"
                        + "reorder_barang = " + pre_order.getText() + ","
                        + "status_barang = '" + sttBarang + "',"
                        + "komisi_harga_jual_1_barang = '" + txt_komisi_hj1.getText() + "',"
                        + "komisi_harga_jual_2_barang = '" + txt_komisi_hj2.getText() + "',"
                        + "status_group = 0,"
                        + "max_return = 0,"
                        + "is_delete = 0"
                        + " WHERE kode_barang = '" + txt_kodebarang.getText() + "'";
//             stat.executeUpdate(sqlA);
                System.out.println("true: " + sqlA);
                stat.executeUpdate(sqlA);
                System.out.println("true");

            } else {
                //insert
                sqlB = "INSERT INTO barang values ("
                        + "" + null + ","
                        + "'" + txt_kodebarang.getText() + "',"
                        + "0,"
                        + "'" + txt_namabarang.getText() + "',"
                        + "0,"
                        + "'" + idkelompok + "',"
                        + "'" + txt_harga_jual1.getText() + "',"
                        + "'" + txt_harga_jual2.getText() + "',"
                        + "'" + txt_harga_jual3.getText() + "',"
                        + "0,"
                        + "0,"
                        + "'" + idtop + "',"
                        + "'" + pre_order.getText() + "',"
                        + "'" + sttBarang + "',"
                        + "'" + txt_komisi_hj1.getText() + "',"
                        + "'" + txt_komisi_hj2.getText() + "',"
                        + "0,"
                        + "0,"
                        + "0"
                        + ")";

                stat.executeUpdate(sqlB);
                System.out.println("false");
            }
            System.out.println("sqla: " + sqlA + "\nsqlb: " + sqlB);
//                Connection conn = (Connection) Koneksi.configDB();

            JOptionPane.showMessageDialog(this, "Sukses");
            dispose();
            Master_Barang a = new Master_Barang();
            a.tampilTabel("1");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void txt_namabarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_namabarangKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            com_kelompok.requestFocus(true);
        }
    }//GEN-LAST:event_txt_namabarangKeyPressed

    private void com_kelompokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_com_kelompokKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pre_order.requestFocus(true);
        }
    }//GEN-LAST:event_com_kelompokKeyPressed

    private void pre_orderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pre_orderKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            com_top.requestFocus(true);
        }
    }//GEN-LAST:event_pre_orderKeyPressed

    private void com_topKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_com_topKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            com_satuan1.requestFocus(true);
        }
    }//GEN-LAST:event_com_topKeyPressed

    private void com_satuan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_com_satuan1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            com_satuan2.requestFocus(true);
        }
    }//GEN-LAST:event_com_satuan1KeyPressed

    private void com_satuan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_com_satuan2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_harga_jual1.requestFocus(true);
        }
    }//GEN-LAST:event_com_satuan2KeyPressed

    private void txt_harga_jual1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_harga_jual1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_harga_jual2.requestFocus(true);
        }
    }//GEN-LAST:event_txt_harga_jual1KeyPressed

    private void txt_harga_jual2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_harga_jual2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_harga_jual3.requestFocus(true);
        }
    }//GEN-LAST:event_txt_harga_jual2KeyPressed

    private void txt_harga_jual3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_harga_jual3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_komisi_hj1.requestFocus(true);
        }
    }//GEN-LAST:event_txt_harga_jual3KeyPressed

    private void txt_komisi_hj1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_komisi_hj1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_komisi_hj2.requestFocus(true);
        }
    }//GEN-LAST:event_txt_komisi_hj1KeyPressed

    private void txt_komisi_hj2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_komisi_hj2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_denda_hj1.requestFocus(true);
        }
    }//GEN-LAST:event_txt_komisi_hj2KeyPressed

    private void txt_denda_hj1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_denda_hj1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_denda_hj2.requestFocus(true);
        }
    }//GEN-LAST:event_txt_denda_hj1KeyPressed

    private void txt_denda_hj2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_denda_hj2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cekbox_group.requestFocus(true);
        }
    }//GEN-LAST:event_txt_denda_hj2KeyPressed

    private void cekbox_groupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cekbox_groupKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_hitungan1.requestFocus(true);
        }
    }//GEN-LAST:event_cekbox_groupKeyPressed

    private void txt_hitungan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hitungan1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_hitungan2.requestFocus(true);
        }
    }//GEN-LAST:event_txt_hitungan1KeyPressed

    private void txt_hitungan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hitungan2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_hargarata2.requestFocus(true);
        }
    }//GEN-LAST:event_txt_hitungan2KeyPressed

    private void txt_hargarata2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hargarata2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_harga_beli.requestFocus(true);
        }
    }//GEN-LAST:event_txt_hargarata2KeyPressed

    private void jumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahKeyPressed
if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            aktif.requestFocus(true);
        }
    }//GEN-LAST:event_jumlahKeyPressed

    private void txt_harga_beliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_harga_beliKeyPressed
if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jumlah.requestFocus(true);
        }
    }//GEN-LAST:event_txt_harga_beliKeyPressed

    private void aktifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_aktifKeyPressed
if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            nonaktif.requestFocus(true);
        }
    }//GEN-LAST:event_aktifKeyPressed

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
            java.util.logging.Logger.getLogger(Master_Barang_TambahEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Barang_TambahEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Barang_TambahEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Barang_TambahEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Master_Barang_TambahEdit().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton aktif;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox cekbox_group;
    private javax.swing.JComboBox<String> com_kelompok;
    private javax.swing.JComboBox<String> com_satuan1;
    private javax.swing.JComboBox<String> com_satuan2;
    private javax.swing.JComboBox<String> com_top;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField jumlah;
    private javax.swing.JRadioButton nonaktif;
    private javax.swing.JTextField pre_order;
    private javax.swing.JLabel simpan;
    private javax.swing.JTextField txt_denda_hj1;
    private javax.swing.JTextField txt_denda_hj2;
    private javax.swing.JTextField txt_harga_beli;
    private javax.swing.JTextField txt_harga_jual1;
    private javax.swing.JTextField txt_harga_jual2;
    private javax.swing.JTextField txt_harga_jual3;
    private javax.swing.JTextField txt_hargarata2;
    private javax.swing.JTextField txt_hitungan1;
    private javax.swing.JTextField txt_hitungan2;
    private javax.swing.JTextField txt_kodebarang;
    private javax.swing.JTextField txt_komisi_hj1;
    private javax.swing.JTextField txt_komisi_hj2;
    private javax.swing.JTextField txt_namabarang;
    // End of variables declaration//GEN-END:variables
}
