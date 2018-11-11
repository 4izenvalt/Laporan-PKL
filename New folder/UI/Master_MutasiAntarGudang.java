/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.*;
import com.sun.glass.events.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author USER
 */
public class Master_MutasiAntarGudang extends javax.swing.JFrame {

    /**
     * Creates new form Penjualan_penjualanFIT
     */
    private Connect connection;
    private boolean pencarian_tgl = false;
    private boolean pencarian_permintaan = false;

    public Master_MutasiAntarGudang() {
        initComponents();

        this.setLocationRelativeTo(null);

    }

    public Master_MutasiAntarGudang(Connect connection) {
        initComponents();
        this.connection = connection;
        this.setLocationRelativeTo(null);
        loadData();
        tgl_awal.setEnabled(false);
        tgl_akhir.setEnabled(false);

    }

    public void loadData() {
        try {
            DefaultTableModel model = (DefaultTableModel) tbl_mutasi_gudang.getModel();
            model.setRowCount(0);
            String sql = "select * from mutasi_antar_gudang md join pegawai p on p.kode_pegawai = md.user order by tanggal desc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            int baris = 0;
            while (res.next()) {
                model.addRow(new Object[]{"", "", "", "", "", "", ""});
                String tanggal = res.getString("tanggal");
                String no_bukti = res.getString("no_bukti");
                String no_permintaan = res.getString("no_permintaan");
                String user = res.getString("nama_pegawai");
                String keterangan = res.getString("keterangan");
                model.setValueAt(tanggal, baris, 0);
                model.setValueAt(no_bukti, baris, 1);
                model.setValueAt(no_permintaan, baris, 2);
                model.setValueAt(user, baris, 3);
                model.setValueAt(keterangan, baris, 4);
                baris++;
            }
            conn.close();
            res.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadDataKriteria() {
        try {
            //menghapus isi tabel awal
            DefaultTableModel model = (DefaultTableModel) tbl_mutasi_gudang.getModel();
            model.setRowCount(0);
            String sql = "";

            //menyamakan query
            if (pencarian_tgl || cek_tgl.isSelected()) {
                String awal = "", akhir = "";
                if (tgl_awal.getDate() != null && tgl_akhir.getDate() != null) {
                    SimpleDateFormat ft_awal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    awal = ft_awal.format(tgl_awal.getDate());
                    SimpleDateFormat ft_akhir = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    akhir = ft_akhir.format(tgl_akhir.getDate());
                }
                if (cek_permintaan.isSelected() || pencarian_permintaan) {
                    sql = "select * from mutasi_antar_gudang md join pegawai p on p.kode_pegawai = md.user where no_permintaan != '-' and (no_bukti like '%" + txt_kriteria.getText() + "%' OR keterangan like '%" + txt_kriteria.getText() + "%') AND tanggal between '" + awal + "' and '" + akhir + "' order by tanggal desc";
                } else {
                    sql = "select * from mutasi_antar_gudang md join pegawai p on p.kode_pegawai = md.user where (no_bukti like '%" + txt_kriteria.getText() + "%' OR keterangan like '%" + txt_kriteria.getText() + "%') AND tanggal between '" + awal + "' and '" + akhir + "' order by tanggal desc";
                }

            } else if (pencarian_permintaan) {
                sql = "select * from mutasi_antar_gudang md join pegawai p on p.kode_pegawai = md.user where no_permintaan != '-' and (no_bukti like '%" + txt_kriteria.getText() + "%' OR keterangan like '%" + txt_kriteria.getText() + "%')order by tanggal desc";
            } else {
                if (cek_permintaan.isSelected()) {
                    sql = "select * from mutasi_antar_gudang md join pegawai p on p.kode_pegawai = md.user where no_permintaan != '-' and (no_bukti like '%" + txt_kriteria.getText() + "%' OR keterangan like '%" + txt_kriteria.getText() + "%')order by tanggal desc";
                } else {
                    sql = "select * from mutasi_antar_gudang md join pegawai p on p.kode_pegawai = md.user where (no_bukti like '%" + txt_kriteria.getText() + "%' OR keterangan like '%" + txt_kriteria.getText() + "%')order by tanggal desc";
                }

            }
            System.out.println(sql);

            //menambah isi tabel awal dengan data baru
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            int baris = 0;
            while (res.next()) {
                model.addRow(new Object[]{"", "", "", "", "", "", ""});
                String tanggal = res.getString("tanggal");
                String no_bukti = res.getString("no_bukti");
                String no_permintaan = res.getString("no_permintaan");
                String user = res.getString("nama_pegawai");
                String keterangan = res.getString("keterangan");
                model.setValueAt(tanggal, baris, 0);
                model.setValueAt(no_bukti, baris, 1);
                model.setValueAt(no_permintaan, baris, 2);
                model.setValueAt(user, baris, 3);
                model.setValueAt(keterangan, baris, 4);
                baris++;
            }
            conn.close();
            res.close();

        } catch (NullPointerException e) {
            System.out.println("tanggal kosong");
//            JOptionPane.showMessageDialog(null, "Tanggal tidak boleh kosong");
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

        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_mutasi_gudang = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        cek_tgl = new javax.swing.JCheckBox();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cek_permintaan = new javax.swing.JCheckBox();
        txt_kriteria = new javax.swing.JTextField();
        tgl_awal = new com.toedter.calendar.JDateChooser();
        tgl_akhir = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel14.setText("Kriteria");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.lightGray));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel16.setText("MUTASI BARANG ANTAR GUDANG");

        tbl_mutasi_gudang.setModel(new javax.swing.table.DefaultTableModel(
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
                "Tanggal", "No. Bukti", "No. Permintaan", "User", "Keterangan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_mutasi_gudang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_mutasi_gudangMouseClicked(evt);
            }
        });
        tbl_mutasi_gudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_mutasi_gudangKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tbl_mutasi_gudang);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(268, 268, 268))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
        );

        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));

        cek_tgl.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cek_tgl.setText("Tanggal");
        cek_tgl.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                saat_centang(evt);
            }
        });
        cek_tgl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cek_tglFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cek_tglFocusLost(evt);
            }
        });
        cek_tgl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cek_tglMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cek_tglMouseExited(evt);
            }
        });
        cek_tgl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cek_tglActionPerformed(evt);
            }
        });

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator4.setForeground(new java.awt.Color(153, 153, 153));

        jLabel18.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_manilla-folder-new_23456.png"))); // NOI18N
        jLabel18.setText("F2-New");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N
        jLabel20.setText("F5-Delete");
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel15.setText("s.d.");

        cek_permintaan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cek_permintaan.setText("Mutasi By Permintaan");
        cek_permintaan.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cek_permintaanStateChanged(evt);
            }
        });
        cek_permintaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cek_permintaanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cek_permintaanMouseExited(evt);
            }
        });

        txt_kriteria.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_kriteria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_kriteriaFocusGained(evt);
            }
        });
        txt_kriteria.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txt_kriteriaInputMethodTextChanged(evt);
            }
        });
        txt_kriteria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kriteriaActionPerformed(evt);
            }
        });
        txt_kriteria.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                mengetik(evt);
            }
        });
        txt_kriteria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_kriteriaKeyPressed(evt);
            }
        });
        txt_kriteria.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                txt_kriteriaVetoableChange(evt);
            }
        });

        tgl_awal.setDateFormatString(" yyyy- MM-dd");
        tgl_awal.setEnabled(false);
        tgl_awal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_awalKeyPressed(evt);
            }
        });

        tgl_akhir.setDateFormatString(" yyyy- MM-dd");
        tgl_akhir.setEnabled(false);
        tgl_akhir.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tgl_akhirPropertyChange(evt);
            }
        });
        tgl_akhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_akhirKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cek_tgl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(cek_permintaan))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_kriteria, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)))
                .addContainerGap(452, Short.MAX_VALUE))
            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jLabel18)
                        .addComponent(txt_kriteria, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cek_tgl)
                                .addComponent(jLabel15)
                                .addComponent(cek_permintaan))
                            .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        pop_up();
    }//GEN-LAST:event_jLabel18MouseClicked

    private void tbl_mutasi_gudangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_mutasi_gudangMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                Master_MutasiAntarGudang_DetailNew2 magdn = new Master_MutasiAntarGudang_DetailNew2(tbl_mutasi_gudang.getValueAt(tbl_mutasi_gudang.getSelectedRow(), 1).toString(), true, this);
                magdn.setVisible(true);
                magdn.setFocusable(true);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Pilih data yang ada");
            }

        }


    }//GEN-LAST:event_tbl_mutasi_gudangMouseClicked

    private void tgl_awalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_awalKeyPressed

    }//GEN-LAST:event_tgl_awalKeyPressed

    private void tgl_akhirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_akhirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tgl_akhirKeyPressed

    private void cek_tglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cek_tglActionPerformed

    }//GEN-LAST:event_cek_tglActionPerformed

    private void saat_centang(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_saat_centang
        if (tgl_awal.isEnabled()) {
            tgl_awal.setEnabled(false);
            tgl_akhir.setEnabled(false);
        } else {
            tgl_awal.setEnabled(true);
            tgl_akhir.setEnabled(true);
        }

    }//GEN-LAST:event_saat_centang

    private void mengetik(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_mengetik

    }//GEN-LAST:event_mengetik

    private void txt_kriteriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kriteriaActionPerformed

    }//GEN-LAST:event_txt_kriteriaActionPerformed

    private void txt_kriteriaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txt_kriteriaInputMethodTextChanged

    }//GEN-LAST:event_txt_kriteriaInputMethodTextChanged

    private void txt_kriteriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_kriteriaKeyPressed
        txt_kriteria.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadDataKriteria();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadDataKriteria();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //loadDataKriteria();
            }

        });
    }//GEN-LAST:event_txt_kriteriaKeyPressed

    private void txt_kriteriaVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txt_kriteriaVetoableChange

    }//GEN-LAST:event_txt_kriteriaVetoableChange

    private void txt_kriteriaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_kriteriaFocusGained

    }//GEN-LAST:event_txt_kriteriaFocusGained

    private void tgl_akhirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tgl_akhirPropertyChange
        loadDataKriteria();
    }//GEN-LAST:event_tgl_akhirPropertyChange

    private void cek_permintaanStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cek_permintaanStateChanged
        loadDataKriteria();
    }//GEN-LAST:event_cek_permintaanStateChanged

    private void tbl_mutasi_gudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_mutasi_gudangKeyPressed
        DefaultTableModel model = (DefaultTableModel) tbl_mutasi_gudang.getModel();
        int selectedRow = tbl_mutasi_gudang.getSelectedRow();
        System.out.println(selectedRow);
        //int selectedRow = tbl_tambah_mutasi.getSelectedRow();
        //int baris = tbl_tambah_mutasi.getRowCount();

        TableModel tabelModel;
        tabelModel = tbl_mutasi_gudang.getModel();
        try {
            if (evt.getKeyCode() == KeyEvent.VK_F5) {
                int hapus = 1;
                hapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
                if (hapus == 0) {
                    hapus_tabel();
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_F2) {
                pop_up();
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Data harus lengkap");

        }
    }//GEN-LAST:event_tbl_mutasi_gudangKeyPressed
    void hapus_tabel() {
        if (tbl_mutasi_gudang.getRowCount() - 1 == -1) {
            JOptionPane.showMessageDialog(null, "Data didalam tabel telah tiada.", "", 2);
        } else {
            removeSelectedRows(tbl_mutasi_gudang);
        }
    }
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            pop_up();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.tbl_mutasi_gudang.setFocusable(true);
        }


    }//GEN-LAST:event_formKeyPressed

    private void cek_tglFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cek_tglFocusGained

    }//GEN-LAST:event_cek_tglFocusGained

    private void cek_tglMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cek_tglMouseEntered
        pencarian_tgl = true;
        loadDataKriteria();
    }//GEN-LAST:event_cek_tglMouseEntered

    private void cek_tglMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cek_tglMouseExited
        pencarian_tgl = false;
        loadDataKriteria();
    }//GEN-LAST:event_cek_tglMouseExited

    private void cek_tglFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cek_tglFocusLost

    }//GEN-LAST:event_cek_tglFocusLost

    private void cek_permintaanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cek_permintaanMouseEntered
        pencarian_permintaan = true;
        loadDataKriteria();
    }//GEN-LAST:event_cek_permintaanMouseEntered

    private void cek_permintaanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cek_permintaanMouseExited
        pencarian_permintaan = false;
        loadDataKriteria();
    }//GEN-LAST:event_cek_permintaanMouseExited

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        int hapus = 1;
        hapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if (hapus == 0) {
            hapus_tabel();
        }
    }//GEN-LAST:event_jLabel20MouseClicked
    void pop_up() {
        Master_MutasiAntarGudang_DetailNew2 magdn = new Master_MutasiAntarGudang_DetailNew2(connection, this);
        magdn.setVisible(true);
        magdn.setFocusable(true);
    }

    void update_stok(String no_bukti) {
        try {
            String sql = "select mdt.kode_barang, mdt.satuan, mdt.qty, mdt.kode_lokasi_asal, mdt.kode_lokasi_tujuan from mutasi_antar_gudang md join mutasi_antar_gudang_detail mdt on mdt.no_bukti = md.no_bukti where mdt.no_bukti='" + no_bukti + "' order by tanggal desc";
            java.sql.Connection con = (Connection) Koneksi.configDB();
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            int row = 0;
            while (res.next()) {
                double qty_sesudah = qty_konversi(res.getString("kode_barang"), res.getString("satuan"), Double.parseDouble(res.getString("qty")));
                //mengupdate stok dimasing-masing gudang dengan qty_sesudah baru
                String menambah = "update barang_lokasi set jumlah= jumlah + " + qty_sesudah + " where kode_barang = '" + res.getString("kode_barang") + "' and kode_lokasi = '" + res.getString("kode_lokasi_asal") + "'";
                Statement st_tambah = con.createStatement();
                row = st_tambah.executeUpdate(menambah);
                System.out.println(menambah);

                String mengurangi = "update barang_lokasi set jumlah= jumlah - " + qty_sesudah + " where kode_barang = '" + res.getString("kode_barang") + "' and kode_lokasi = '" + res.getString("kode_lokasi_tujuan") + "'";
                Statement st_kurang = con.createStatement();
                row = st_kurang.executeUpdate(mengurangi);
                System.out.println(mengurangi);
            }
            con.close();
            res.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    double qty_konversi(String kode_barang, String kode_konversi, double qty) {
        double hasil = 0;

        try {

            String sql = "select * from barang_konversi where kode_barang='" + kode_barang + "' and (kode_konversi ='" + kode_konversi + "' || identitas_konversi='1') order by identitas_konversi asc";
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
            System.out.println("identitas 2 =" + identitas_konversi[1]);
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

    private void removeSelectedRows(JTable table) {
        int Hapus = 1;
        try {
            Koneksi Koneksi = new Koneksi();
            Connection con = Koneksi.configDB();
            Statement st = con.createStatement();
            int row = 0;
            Hapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin mau menghapus baris ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
            if (Hapus == 0) {
                DefaultTableModel model = (DefaultTableModel) this.tbl_mutasi_gudang.getModel();
                int[] rows = table.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    System.out.println("row ke-i adalah " + rows[i]);
                    if (rows[i] == 0 || rows.length == 1) {
                        String no_bukti = String.valueOf(model.getValueAt(rows[i], 1));
                        //update_stok(no_bukti);
                        String sql = "delete from mutasi_antar_gudang where no_bukti='" + no_bukti + "'";
                        System.out.println(sql);
                        String sql2 = "delete from mutasi_antar_gudang_detail where no_bukti='" + no_bukti + "'";
                        row = st.executeUpdate(sql);
                        row = st.executeUpdate(sql2);
                        System.out.println("if" + (rows[i]));
                        model.removeRow(rows[i]);

                    } else {
                        System.out.println("if 2");
                        if (i == 0) {
                            String no_bukti = String.valueOf(model.getValueAt(rows[i], 1));
                            //update_stok(no_bukti);
                            String sql = "delete from mutasi_antar_gudang where no_bukti='" + no_bukti + "'";
                            System.out.println(sql);
                            String sql2 = "delete from mutasi_antar_gudang_detail where no_bukti='" + no_bukti + "'";
                            row = st.executeUpdate(sql);
                            row = st.executeUpdate(sql2);
                            model.removeRow(rows[i]);
                        } else {
                            String no_bukti = String.valueOf(model.getValueAt(rows[i] - i, 1));
                            //update_stok(no_bukti);
                            String sql = "delete from mutasi_antar_gudang where no_bukti='" + no_bukti + "'";
                            System.out.println(sql);
                            String sql2 = "delete from mutasi_antar_gudang_detail where no_bukti='" + no_bukti + "'";
                            row = st.executeUpdate(sql);
                            row = st.executeUpdate(sql2);
                            model.removeRow(rows[i] - i);
                        }
                    }

                }

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "data tidak dimasukkan ke database" + e, "informasi", JOptionPane.INFORMATION_MESSAGE);
        }

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
            java.util.logging.Logger.getLogger(Master_MutasiAntarGudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiAntarGudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiAntarGudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiAntarGudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master_MutasiAntarGudang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cek_permintaan;
    private javax.swing.JCheckBox cek_tgl;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable tbl_mutasi_gudang;
    private com.toedter.calendar.JDateChooser tgl_akhir;
    private com.toedter.calendar.JDateChooser tgl_awal;
    private javax.swing.JTextField txt_kriteria;
    // End of variables declaration//GEN-END:variables
}
