package UI;

import Class.Koneksi;
import Java.*;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class Master_Barang extends javax.swing.JDialog {

    private ResultSet hasil1, hasilLokasi;
    private Connect connection;
    private PreparedStatement PS;
    private ArrayList<ListBarang> list;
    private myArrlist myarrlist;
    private ListBarang listBarang;
    private TrBarang TrBarang;
    private TableModel model;
    private MouseAdapter MA;
    private String comboBox;

    public Master_Barang() {
        initComponents();
//        this.setLocationRelativeTo(null);

    }

    public Master_Barang(java.awt.Frame parent, boolean modal, Connect connection) {
        super(parent, modal);
        initComponents();
//        prep
        this.connection = connection;
        tampilTabel("1");
//
    }

    void tampilTabel(String param) {

        String data = "";
        try {
            String sql = "SELECT b.kode_barang, b.nama_barang, k.nama_konversi, b.harga_jual_1_barang, "
                    + "b.harga_jual_2_barang, b.harga_jual_3_barang, b.status_barang "
                    + "FROM barang b, barang_konversi bk, konversi k where "
                    + "b.kode_barang=bk.kode_barang "
                    + " and bk.kode_konversi=k.kode_konversi "
                    + " and bk.identitas_konversi=1 "
                    + (param.equals("-1") ? "" : (param.equals("1") || param.equals("0") ? "and b.status_barang="
                    + Integer.parseInt(param) : "and b.nama_barang like '%" + param + "%' ")
                    + " limit 100");

//            String sqlLokasi = "SELECT * from barang_lokasi";
            System.out.println("sql: " + sql);
            hasil1 = connection.ambilData(sql);

            setModel(hasil1);
            System.out.println("berhasil 69");
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("ERROR -> " + e.getMessage());
        }
    }

    private void deleteData(ListBarang listBarang) {
        int status_hapus = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin Menghapus data ini ?", "Konfimasi Hapus Data", JOptionPane.OK_CANCEL_OPTION);
        if (status_hapus == JOptionPane.OK_OPTION) {
            PS = null;
            try {
                String sql = "delete from barang where kode_barang=?";
                PS = connection.Connect().prepareStatement(sql);
                PS.setInt(1, listBarang.getKode_barang());
                PS.executeUpdate();
                System.out.println(sql + " " + listBarang.getKode_barang());
            } catch (SQLException e) {
                System.out.println("Master_Barang_Line_139_" + e.toString());
            } finally {
                tampilTabel("-1");
            }
            JOptionPane.showMessageDialog(null, "Data sudah dihapus.");
        }
    }

    private void setModel(ResultSet hasil) {
        try {
            java.sql.Connection conn = Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            list = new ArrayList<>();
            while (hasil.next()) {

                this.listBarang = new ListBarang();
                String sqlLokasi = "SELECT * from barang_lokasi where kode_barang='"
                        + hasil.getInt("kode_barang") + "'";
//                System.out.println("sqllokasi: "+sqlLokasi);
                //lokasi 
                hasilLokasi = stm.executeQuery(sqlLokasi);
//                hasilstatus = stm.executeQuery(sqlStatus); 
                while (hasilLokasi.next()) {
                    if (hasilLokasi.getInt("kode_lokasi") == 1) {
                        this.listBarang.setPusat(hasilLokasi.getInt("jumlah"));
                    } else if (hasilLokasi.getInt("kode_lokasi") == 2) {
                        this.listBarang.setGUD63(hasilLokasi.getInt("jumlah"));
                    } else if (hasilLokasi.getInt("kode_lokasi") == 4) {
                        this.listBarang.setTOKO(hasilLokasi.getInt("jumlah"));
                    } else if (hasilLokasi.getInt("kode_lokasi") == 5) {
                        this.listBarang.setTENGAH(hasilLokasi.getInt("jumlah"));
                    } else {
                        this.listBarang.setUTARA(hasilLokasi.getInt("jumlah"));
                    }
                }
                if (hasil.getInt("status_barang") == 0) {
                    this.listBarang.setStatus("Tidak aktif");
                } else if (hasil.getInt("status_barang") == 1) {
                    this.listBarang.setStatus("aktif");
                }
                this.listBarang.setKode_barang(hasil.getInt("kode_barang"));
                this.listBarang.setNama_barang(hasil.getString("nama_barang"));
                this.listBarang.setSatuan(hasil.getString("nama_konversi"));
                this.listBarang.setHarga_jual_1(hasil.getDouble("harga_jual_1_barang"));
                this.listBarang.setHarga_jual_2(hasil.getDouble("harga_jual_2_barang"));
                this.listBarang.setHarga_jual_3(hasil.getDouble("harga_jual_3_barang"));
                list.add(listBarang);
                listBarang = null;
            }
            model = new modelTabelBarang1(list);
            jTable2.setModel(model);
            TableColumnModel m = jTable2.getColumnModel();
            m.getColumn(8).setCellRenderer(new Currency_Column());
            m.getColumn(9).setCellRenderer(new Currency_Column());
            m.getColumn(10).setCellRenderer(new Currency_Column());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

//    private String currency_convert(int nilai) {
//        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
//        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
//        formatRp.setCurrencySymbol("Rp. ");
//        formatRp.setMonetaryDecimalSeparator(',');
//        formatRp.setGroupingSeparator('.');
//        kursIndonesia.setDecimalFormatSymbols(formatRp);
//        return kursIndonesia.format(nilai);
//    }
    private int getNewId() {
        String sql = "SELECT kode_barang FROM barang ORDER BY kode_barang DESC LIMIT 1";
        int id = 0;
        try {
            hasil1 = connection.ambilData(sql);
            while (hasil1.next()) {
                id = hasil1.getInt("kode_barang") + 1;
            }
        } catch (SQLException e) {
        }
        return id;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jTextField14 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LIST BRG ACTIVE", "LIST BRG DEACTIVE", "ALL" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText("Tampilkan");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setText("Kriteria");

        jTextField14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        jTextField14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField14ActionPerformed(evt);
            }
        });
        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField14KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField14KeyReleased(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.lightGray));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel17.setText("DAFTAR BARANG");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama  Barang", "Pusat", "GUD63", "TOKO", "TENGAH", "UTARA", "Satuan", "Harga Jual 1", "Harga Jual 2", "Harga Jual 3", "Status"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMaxWidth(100);
            jTable2.getColumnModel().getColumn(1).setMinWidth(150);
            jTable2.getColumnModel().getColumn(2).setMaxWidth(50);
            jTable2.getColumnModel().getColumn(3).setMaxWidth(50);
            jTable2.getColumnModel().getColumn(4).setMaxWidth(50);
            jTable2.getColumnModel().getColumn(5).setMaxWidth(50);
            jTable2.getColumnModel().getColumn(6).setMaxWidth(50);
            jTable2.getColumnModel().getColumn(7).setMaxWidth(50);
            jTable2.getColumnModel().getColumn(8).setMaxWidth(75);
            jTable2.getColumnModel().getColumn(9).setMaxWidth(75);
            jTable2.getColumnModel().getColumn(10).setMaxWidth(75);
            jTable2.getColumnModel().getColumn(11).setMaxWidth(75);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(285, 285, 285))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_manilla-folder-new_23456.png"))); // NOI18N
        jLabel18.setText("F2-New");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_gtk-edit_20500.png"))); // NOI18N
        jLabel19.setText("F3-Edit");
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });

        jSeparator5.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        jLabel20.setText("F5-Delete");
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });

        jMenuBar1.setPreferredSize(new java.awt.Dimension(0, 0));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem1.setText("New");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem2.setText("Edit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem3.setText("Delete");
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
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jSeparator1))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel20)
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator5)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        comboBox = jComboBox4.getSelectedItem().toString();
        switch (jComboBox4.getSelectedIndex()) {
            case 0:
                System.out.println("jc1");
                tampilTabel("1");
                break;
            case 1:
                System.out.println("jc0");
                tampilTabel("0");
                break;
            default:
                System.out.println("jc-1");
                tampilTabel("-1");
                break;
        }
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        listBarang = new ListBarang();
        System.out.println("lul");
        Master_Barang_TambahEdit mcne = new Master_Barang_TambahEdit(this, rootPaneCheckingEnabled, getNewId(), false);
//        mcne.setLocationRelativeTo(new Awal(rootPaneCheckingEnabled));
        mcne.setVisible(true);
        mcne.setFocusable(true);
//        insertData(listBarang);
    }//GEN-LAST:event_jLabel18MouseClicked

//    (new Awal(rootPaneCheckingEnabled), rootPaneCheckingEnabled, listBarang, getNewId());
    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        if (jTable2.getSelectedRow() >= 0) {
            try {
                int i = jTable2.getSelectedRow();

                TableModel model = jTable2.getModel();
//               kode.setText(model.getValueAt(i,1).toString());

                Master_Barang_TambahEdit mbte = new Master_Barang_TambahEdit(this, rootPaneCheckingEnabled, connection, model.getValueAt(i, 0).toString(), true);
                mbte.setLocationRelativeTo(this);
                mbte.setVisible(true);
                mbte.setFocusable(true);
                System.out.println("berhasil link ");

            } catch (Exception e) {
                System.out.println("master_barang_500_line" + e.toString());
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Data Terlebih Dahulu");
        }

    }//GEN-LAST:event_jLabel19MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        this.listBarang = list.get(jTable2.getSelectedRow());
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            Master_Barang_HistoryStok x = new Master_Barang_HistoryStok(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
            x.setVisible(true);
            x.setFocusable(true);
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
//tampilTabel("1");      
    }//GEN-LAST:event_formWindowActivated

    private void jTextField14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyPressed
//        tampilTabel(jTextField14.getText());      
    }//GEN-LAST:event_jTextField14KeyPressed

    private void jTextField14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField14ActionPerformed
        tampilTabel(jTextField14.getText());
    }//GEN-LAST:event_jTextField14ActionPerformed

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        deleteData(listBarang);
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jTextField14KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyReleased
        tampilTabel(jTextField14.getText());
    }//GEN-LAST:event_jTextField14KeyReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        listBarang = new ListBarang();
        System.out.println("lul");
        Master_Barang_TambahEdit mcne = new Master_Barang_TambahEdit(this, rootPaneCheckingEnabled, getNewId(), false);
//        mcne.setLocationRelativeTo(new Awal(rootPaneCheckingEnabled));
        mcne.setVisible(true);
        mcne.setFocusable(true);
//        insertData(listBarang);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (jTable2.getSelectedRow() >= 0) {
            try {
                int i = jTable2.getSelectedRow();

                TableModel model = jTable2.getModel();
//               kode.setText(model.getValueAt(i,1).toString());

                Master_Barang_TambahEdit mbte = new Master_Barang_TambahEdit(this, rootPaneCheckingEnabled, connection, model.getValueAt(i, 0).toString(), true);
                mbte.setLocationRelativeTo(this);
                mbte.setVisible(true);
                mbte.setFocusable(true);
                System.out.println("berhasil link ");

            } catch (Exception e) {
                System.out.println("master_barang_500_line" + e.toString());
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Data Terlebih Dahulu");
        }

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        deleteData(listBarang);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

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
            java.util.logging.Logger.getLogger(Master_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master_Barang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField14;
    // End of variables declaration//GEN-END:variables
}
