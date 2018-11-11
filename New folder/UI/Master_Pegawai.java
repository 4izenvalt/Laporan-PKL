package UI;

import Class.Koneksi;
import Java.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Java.ListPegawai;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

public class Master_Pegawai extends javax.swing.JDialog {

//    Object
    private Connect connection;
    private ResultSet hasil;
    private ArrayList<ListPegawai> list;
    private ListPegawai listPegawai;
    private TableModel model;
    private PreparedStatement PS;

    public Master_Pegawai() {
        initComponents();
        this.setExtendedState(Awal.MAXIMIZED_BOTH);

    }

    public Master_Pegawai(java.awt.Frame parent, boolean modal, Connect connection) {
        super(parent, modal);
//       
        initComponents();
//        prep
        this.connection = connection;

        tampilTabel("*");
        jLabel2.repaint();
        jLabel2.revalidate();
    }

    private void tampilTabel(String param) {
        String data = "";
        try {
//            data = "SELECT kode_pegawai, kode_unik, nama_lokasi, nama_pegawai, alamat_pegawai, kota_pegawai, telepon_pegawai,contact_pegawai, status_pegawai "
//                    + "FROM pegawai, lokasi "
//                    + "WHERE " + (param.equalsIgnoreCase("*") ? "" : "nama_pegawai like '%" + param + "%' and ") + "pegawai.kode_lokasi=lokasi.kode_lokasi "
//                    + "order by kode_pegawai";
            data = "SELECT kode_pegawai, pin, nama_lokasi, nama_pegawai, alias, alamat_pegawai, kota_pegawai, telepon_pegawai,contact_pegawai, status_pegawai "
                    + "FROM elecomp_catftm.emp pcp left join elecomp_cat73.pegawai cp on pcp.pin=cp.kode_unik left join elecomp_cat73.lokasi cl on cp.kode_lokasi=cl.kode_lokasi "
                    + "" + (param.equalsIgnoreCase("*") ? "" : "WHERE cp.nama_pegawai like '%" + param + "%' and ") + ""
                    + "order by pcp.pin";
            hasil = connection.ambilData(data);
//            System.out.println("sukses query tampil tabel");
            setModel(hasil);
            System.out.println("data sql pegawai " + data);

        } catch (Exception e) {
            System.out.println("ERROR -> " + e.getMessage());
        } finally {
//            System.out.println(data);
        }
    }

    private void setModel(ResultSet hasil) {
        try {
            list = new ArrayList<>();
            while (hasil.next()) {
                this.listPegawai = new ListPegawai();
                this.listPegawai.setKode_pegawai(hasil.getInt("kode_pegawai"));
                this.listPegawai.setKode_unik(hasil.getInt("pin"));
                this.listPegawai.setNama_pegawai(hasil.getString("alias"));
                this.listPegawai.setKode_lokasi(hasil.getString("nama_lokasi"));
                this.listPegawai.setAlamat_pegawai(hasil.getString("alamat_pegawai"));
                this.listPegawai.setKota_pegawai(hasil.getString("kota_pegawai"));
                this.listPegawai.setTelepon_pegawai(hasil.getString("telepon_pegawai"));
                this.listPegawai.setContact_pegawai(hasil.getString("contact_pegawai"));
                this.listPegawai.setStatus_pegawai(hasil.getInt("status_pegawai"));
                list.add(listPegawai);
                listPegawai = null;
            }
            model = new modelTabelPegawai(list);
            jTable6.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private int getNewId() {
        String sql = "SELECT kode_pegawai FROM pegawai ORDER BY kode_pegawai DESC LIMIT 1";
        int id = 0;
        try {
            hasil = connection.ambilData(sql);
            while (hasil.next()) {
                id = hasil.getInt("kode_pegawai") + 1;
            }
        } catch (SQLException e) {
        }
        return id;
    }

    private void insertData(ListPegawai listPegawai) {
        PS = null;
        if (listPegawai.getKode_pegawai() != 0) {
            try {
                String sql = "insert into pegawai values (?,?,?,?,?,?,?,?,?,?,?,?)";
                PS = connection.Connect().prepareStatement(sql);
                PS.setInt(1, listPegawai.getKode_pegawai());
                PS.setInt(2, listPegawai.getKode_unik());
                PS.setString(3, listPegawai.getKode_lokasi());
                PS.setInt(4, 0);
                PS.setString(5, listPegawai.getNama_pegawai());
                PS.setString(6, listPegawai.getAlamat_pegawai());
                PS.setString(7, listPegawai.getKota_pegawai());
                PS.setString(8, listPegawai.getTelepon_pegawai());
                PS.setString(9, listPegawai.getContact_pegawai());
                PS.setInt(10, listPegawai.getStatus_pegawai());
                PS.setString(11, "");
                PS.setString(12, "");
                PS.executeUpdate();

                tampilTabel("*");
            } catch (SQLException e) {
                System.out.println("Master_Pegawai_Line_86_" + e.toString());
            }
        }
    }

    private void updateData(int kodePegawai, String kl) {
        PS = null;
        try {
            String sql1 = "select p.kode_unik "
                    + "from pegawai p "
                    + "where p.kode_unik='" + kodePegawai + "'";
            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql1);
//            comTableKonv.removeAllItems();
            String name = "";
            while (res.next()) {
                name = res.getString(1);
            }
            ///----------------------lokasi
            String sqll = "select kode_lokasi from lokasi where nama_lokasi='" + kl + "'";
            java.sql.ResultSet resl = stm.executeQuery(sqll);
            int kode_lokasi = 0;
            while (resl.next()) {
                kode_lokasi = resl.getInt(1);
            }
            String sql = "", sql2 = "";
            int row = 0;
            if (name.equals("")) {
                sql = "insert into pegawai(kode_unik, nama_pegawai, kode_lokasi, alamat_pegawai, kota_pegawai, telepon_pegawai, contact_pegawai)"
                        + "value('" + listPegawai.getKode_unik()
                        + "','" + listPegawai.getNama_pegawai()
                        + "','" + kode_lokasi
                        + "','" + listPegawai.getAlamat_pegawai()
                        + "','" + listPegawai.getKota_pegawai()
                        + "','" + listPegawai.getTelepon_pegawai()
                        + "','" + listPegawai.getContact_pegawai()
                        + "');";
                sql2 = "Update elecomp_catftmp s.emet alias='" + listPegawai.getNama_pegawai() + "'"
                        + "where pin='" + listPegawai.getKode_unik() + "'";
                System.out.println("if1: " + sql);
                System.out.println("if2: " + sql2);
                stm.executeUpdate(sql2);
                row = stm.executeUpdate(sql);
            } else {
                sql = "Update pegawai set nama_pegawai='" + listPegawai.getNama_pegawai() + "',kode_lokasi='" + kode_lokasi + "',alamat_pegawai='" + listPegawai.getAlamat_pegawai() + "',kota_pegawai='" + listPegawai.getKota_pegawai() + "', "
                        + "telepon_pegawai='" + listPegawai.getTelepon_pegawai() + "',contact_pegawai='" + listPegawai.getContact_pegawai() + "',status_pegawai='" + listPegawai.getStatus_pegawai() + "' "
                        + "where kode_unik='" + listPegawai.getKode_unik() + "'";
                sql2 = "Update elecomp_catftm.emp set alias='" + listPegawai.getNama_pegawai() + "'"
                        + "where pin='" + listPegawai.getKode_unik() + "'";
                stm.executeUpdate(sql2);
                row = stm.executeUpdate(sql);
            }
//            String sql = "insert into pembelian( no_faktur_pembelian, no_faktur_supplier_pembelian, tgl_pembelian,  tgl_nota_supplier_pembelian,  discon_persen, discon_rp, keterangan_pembelian)"
//                    + "value('" + txt_noNota.getText() + "','" + txt_inv.getText() + "','" + txt_tgl.getText() + "','" + date + "','" + txt_diskon.getText() + "','" + txt_diskonRp.getText() + "','" + txt_ket.getText() + "');";

            if (row == 1) {
                JOptionPane.showMessageDialog(null, "data sudah ditambahkan ke database", "informasi", JOptionPane.INFORMATION_MESSAGE);
                conn.close();
            }
            PS = connection.Connect().prepareStatement(sql);
            PS.setInt(1, listPegawai.getKode_unik());
            PS.setString(2, listPegawai.getNama_pegawai());
            PS.setString(3, listPegawai.getKode_lokasi());
            PS.setString(4, listPegawai.getAlamat_pegawai());
            PS.setString(5, listPegawai.getKota_pegawai());
            PS.setString(6, listPegawai.getTelepon_pegawai());
            PS.setString(7, listPegawai.getContact_pegawai());
            PS.setInt(8, listPegawai.getStatus_pegawai());
            PS.setInt(9, listPegawai.getKode_pegawai());
            PS.executeUpdate();

//            setModel(null);
//            connection.simpanData(sql);
        } catch (SQLException e) {
            System.out.println("Master_Pegawai_Line_196_" + e.toString());
        }
    }

    private void updateDatalogin(int kodePegawai, String kl) {
        PS = null;
        try {
            String sql1 = "select p.kode_unik "
                    + "from pegawai p "
                    + "where p.kode_unik='" + kodePegawai + "'";
            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql1);
//            comTableKonv.removeAllItems();
            String no = "";
            while (res.next()) {
                no = res.getString(0);
            }

            String sql2 = "";
            int row = 0;
            if (no.equals("0")) {
                JOptionPane.showMessageDialog(null, "Anda Harus Mengisi Data Diri Terlebih Dahulu");

            } else {
                sql2 = "Update pegawai set username='" + listPegawai.getLogin_username() + "',password='" + listPegawai.getLogin_password() + "' "
                        + "where kode_unik='" + listPegawai.getKode_unik() + "'";
                System.out.println("if2: " + sql2);
                row = stm.executeUpdate(sql2);
            }
//            String sql = "insert into pembelian( no_faktur_pembelian, no_faktur_supplier_pembelian, tgl_pembelian,  tgl_nota_supplier_pembelian,  discon_persen, discon_rp, keterangan_pembelian)"
//                    + "value('" + txt_noNota.getText() + "','" + txt_inv.getText() + "','" + txt_tgl.getText() + "','" + date + "','" + txt_diskon.getText() + "','" + txt_diskonRp.getText() + "','" + txt_ket.getText() + "');";
//            

            if (row == 1) {
                JOptionPane.showMessageDialog(null, "data sudah ditambahkan ke database", "informasi", JOptionPane.INFORMATION_MESSAGE);
                conn.close();
            }
            PS = connection.Connect().prepareStatement(sql2);
            PS.setInt(1, listPegawai.getKode_unik());
            PS.setString(2, listPegawai.getNama_pegawai());
            PS.setString(3, listPegawai.getKode_lokasi());
            PS.setString(4, listPegawai.getAlamat_pegawai());
            PS.setString(5, listPegawai.getKota_pegawai());
            PS.setString(6, listPegawai.getTelepon_pegawai());
            PS.setString(7, listPegawai.getContact_pegawai());
            PS.setInt(8, listPegawai.getStatus_pegawai());
            PS.setInt(9, listPegawai.getKode_pegawai());
            PS.executeUpdate();

//            setModel(null);
//            connection.simpanData(sql);
        } catch (SQLException e) {
            System.out.println("Master_Pegawai_Line_246_" + e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode Unik", "Nama ", "Lokasi", "Alamat", "Kota", "Telepon", "Contact"
            }
        ));
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable6MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTable6);

        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Menu");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Kriteria");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel23.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel23.setText("Pegawai form");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator6.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator4.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator7.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_gtk-edit_20500.png"))); // NOI18N
        jLabel3.setText("F3-Edit");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel5.setText("Penggajian");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        jLabel7.setText("Esc-Exit");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jSeparator10.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jMenuBar1.setPreferredSize(new java.awt.Dimension(0, 0));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem1.setText("Edit");
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
            .addComponent(jSeparator2)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(12, 12, 12)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(145, 145, 145)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator6)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator10)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        listPegawai = new ListPegawai();
        Master_Pegawai_TambahPegawai tp = new Master_Pegawai_TambahPegawai(new Awal(rootPaneCheckingEnabled), rootPaneCheckingEnabled, listPegawai, getNewId());
        tp.setLocationRelativeTo(this);
        tp.setVisible(true);
        insertData(listPegawai);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        if (jTable6.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Pilih Pegawai yang akan diedit");
        } else {
            try {
                Master_Pegawai_TambahPegawai tp = new Master_Pegawai_TambahPegawai(new Awal(rootPaneCheckingEnabled), rootPaneCheckingEnabled, listPegawai, true);
                tp.setLocationRelativeTo(this);
                tp.setVisible(true);
                updateData(listPegawai.getKode_unik(), listPegawai.getKode_lokasi());
//                System.out.println(list.get(0).getNama_pegawai());
            } catch (Exception e) {
                System.out.println("Master_Pegawai_Line_360_" + e.toString());
            }
        }
//        tp.setLocationRelativeTo(null);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        Master_LaporanPenggajianKaryawan lpk = new Master_LaporanPenggajianKaryawan();
        lpk.setVisible(true);
        lpk.setLocationRelativeTo(null);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jTable6.getSelectedRow() >= 0) {
            try {
                Master_Login ml = new Master_Login(null, rootPaneCheckingEnabled, connection);
                ml.setLocationRelativeTo(this);
                ml.setVisible(true);
                updateDatalogin(listPegawai.getKode_unik(), listPegawai.getKode_lokasi());
//                System.out.println(list.get(0).getNama_pegawai());
            } catch (Exception e) {
                System.out.println("Master_Pegawai_Line_514_" + e.toString());
            }
//            JOptionPane.showMessageDialog(null, "Pilih Pegawai yang akan diedit");
        } else {
            JOptionPane.showMessageDialog(null, "Pilih Pegawai yang akan diedit");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Master_Pegawai_Menu pm = new Master_Pegawai_Menu();
        pm.setVisible(true);
        pm.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable6MouseClicked
        this.listPegawai = list.get(jTable6.getSelectedRow());
    }//GEN-LAST:event_jTable6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        tampilTabel(jTextField1.getText());
    }//GEN-LAST:event_jTextField1KeyTyped

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampilTabel("*");
    }//GEN-LAST:event_formWindowOpened

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (jTable6.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Pilih Pegawai yang akan diedit");
        } else {
            try {
                Master_Pegawai_TambahPegawai tp = new Master_Pegawai_TambahPegawai(new Awal(rootPaneCheckingEnabled), rootPaneCheckingEnabled, listPegawai, true);
                tp.setLocationRelativeTo(this);
                tp.setVisible(true);
                updateData(listPegawai.getKode_unik(), listPegawai.getKode_lokasi());
//                System.out.println(list.get(0).getNama_pegawai());
            } catch (Exception e) {
                System.out.println("Master_Pegawai_Line_360_" + e.toString());
            }
        }
//        tp.setLocationRelativeTo(null);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
            java.util.logging.Logger.getLogger(Master_Pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master_Pegawai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    private void setExtendedState(int MAXIMIZED_BOTH) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
