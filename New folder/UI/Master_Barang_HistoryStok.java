/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Connect;
import com.sun.glass.events.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author User
 */
public class Master_Barang_HistoryStok extends javax.swing.JDialog {

    /**
     * Creates new form NewJFrame
     */
    String kode_barang="", kode_lokasi="";
    String lokasi;
     ArrayList<String> kode_nama_arr = new ArrayList();
    private static int item = 0;
    private boolean tampil = true;
    
       public Master_Barang_HistoryStok(String kode_barang) {
//        super(parent, modal);
        initComponents();
        this.kode_barang=kode_barang;
//        kodeBarang.setText(kode_barang);
        comKodeBarang.setSelectedItem(kode_barang);
        this.lokasi=comLokasi.getSelectedItem().toString();
        System.out.println("lokasi awal:"+lokasi);
        this.setLocationRelativeTo(null);
        lokasi();
        loadComTableKode();
        loadTable();
        namaBarang();
        
        //JCombobox kode barang
        ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboKode(((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
//                            tbl_mutasi_kode.editCellAt(tbl_mutasi_kode.getSelectedRow(), 1);
                            comKodeBarang.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
                loadTable();
                namaBarang();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).setText(key);
                loadTable();
                namaBarang();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");
                loadTable();
                namaBarang();

            }

        });
        ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
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
                loadTable();
                namaBarang();
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                loadTable();
                namaBarang();

            }
        });
        //kode JCombobox sampai sini

        
    }
    
    
    public Master_Barang_HistoryStok(){
        initComponents();
        this.setLocationRelativeTo(null);
        
        loadTable();
        lokasi();
        
        //JCombobox kode barang
        ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboKode(((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
                            //tbl_mutasi_kode.editCellAt(tbl_mutasi_kode.getSelectedRow(), 1);
                            comKodeBarang.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
                loadTable();
                namaBarang();
                
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).setText(key);
                loadTable();
                namaBarang();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");
                loadTable();
                namaBarang();

            }

        });
        ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
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
                loadTable();
                namaBarang();
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                loadTable();
                namaBarang();

            }
        });
        //kode JCombobox sampai sini
    }
    
    public Master_Barang_HistoryStok(java.awt.Frame parent, boolean modal, String kode_barang) {
        super(parent, modal);
        initComponents();
        this.kode_barang=kode_barang;
//        kodeBarang.setText(kode_barang);
        comKodeBarang.setSelectedItem(kode_barang);
        this.lokasi=comLokasi.getSelectedItem().toString();
        System.out.println("lokasi awal:"+lokasi);
        this.setLocationRelativeTo(null);
        lokasi();
        loadComTableKode();
        loadTable();
        namaBarang();
        
        //JCombobox kode barang
        ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboKode(((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
//                            tbl_mutasi_kode.editCellAt(tbl_mutasi_kode.getSelectedRow(), 1);
                            comKodeBarang.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
                loadTable();
                namaBarang();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).setText(key);
                loadTable();
                namaBarang();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");
                loadTable();
                namaBarang();

            }

        });
        ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
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
                loadTable();
                namaBarang();
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                loadTable();
                namaBarang();

            }
        });
        //kode JCombobox sampai sini

        
    }
    
    public void removeRow() {
        DefaultTableModel model = (DefaultTableModel) tbl_history.getModel();
        int row = tbl_history.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }
    
    void loadComTableKode() {
        try {
            String sql = "select * from barang order by kode_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(1);
                String barang = res.getString(4);
                comKodeBarang.addItem(name);
                // comTableKode.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }
        
    void loadComboKode(String key) {
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
                    comKodeBarang.setModel(new DefaultComboBoxModel(kode_nama_arr.toArray()));
                    ((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).setText(key);
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
        
        
    public void namaBarang() {
        try{
            String sql="SELECT nama_barang FROM barang WHERE kode_barang='"+kode_barang+"'";
            Connection c = (Connection) Koneksi.configDB();
            Statement stat = c.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while(res.next()){
            System.out.println("nama : "+res.getString("nama_barang"));    

            namaBarang.setText(res.getString("nama_barang"));
            lbl_namaBrg.setText(res.getString("nama_barang"));
            //kodeBarang.setText(kode_barang);
            }
            
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Eror = " + e);            
        }
        
            
    }
    
    public void lokasi(){
        try{
             Connection con = Koneksi.configDB();
             Statement st = con.createStatement();
             String sql2 = "select kode_lokasi from lokasi where nama_lokasi= '" + lokasi + "'";
                    Statement st2 = con.createStatement();
                    java.sql.ResultSet res = st2.executeQuery(sql2);
                    while (res.next()) {
                        kode_lokasi = res.getString(1);
                    }
                    res.close();
                    con.close();
                    System.out.println("kode lokasi: "+kode_lokasi);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }
    
    public void loadTable(){
        removeRow();
//        kode_barang=kodeBarang.getText();
        kode_barang=comKodeBarang.getSelectedItem().toString();
        System.out.println("nilai combobox : "+ kode_barang);
        lokasi();
        DefaultTableModel model = (DefaultTableModel) tbl_history.getModel();
        int i = 1;
        double saldo=0;
        String sql = "", sql2="", sql3="";
        try {
            if (tgl_akhir.getDate()!=null) {
                SimpleDateFormat ft_awal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String awal = ft_awal.format(tgl_awal.getDate());

                SimpleDateFormat ft_akhir = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String akhir = ft_akhir.format(tgl_akhir.getDate());
                System.out.println(akhir);
                //mengambil dari detail pembelian            
                sql = "SELECT p.tgl_pembelian,db.nama_barang_edit, db.kode_barang, db.no_faktur_pembelian, db.jumlah_barang, db.hj2 FROM "
                        + "detail_pembelian db JOIN pembelian p WHERE db.no_faktur_pembelian=p.no_faktur_pembelian "
                        + "AND db.kode_barang='" + kode_barang + "'"
                        + "AND db.kode_lokasi='" + kode_lokasi + "'"
                        + "AND p.tgl_pembelian between '"+ awal+"' AND '" + akhir + "'";
                System.out.println(sql);
                
                //mengambil dari detail penjualan
                sql2 = "SELECT p.tgl_penjualan, dj.kode_barang, dj.nama_barang_edit, dj.no_faktur_penjualan, dj.jumlah_barang, dj.harga_jual2 FROM "
                        + "penjualan_detail dj JOIN penjualan p WHERE dj.no_faktur_penjualan=p.no_faktur_penjualan "
                        + "AND dj.kode_barang='" + kode_barang + "'"
                        + "AND dj.kode_lokasi='" + kode_lokasi + "'"
                        + "AND p.tgl_penjualan between '"+ awal+"' AND '" + akhir + "'";
                System.out.println(sql2);
                //mengambil dari return pembelian
                sql3 = "SELECT p.tgl_return_pakai, pr.kode_barang, pr.nama_barang_edit, PR.no_faktur_return_pakai, pr.jumlah_barang, pr.harga_pembelian FROM"
                      +"pembelian_detail_return_pakai pr JOIN pembelian_return_pakai p WHERE p.no_faktur_return_pakai=pr.no_faktur_return_pakai"
                      +"AND pr.kode_barang='" + kode_barang + "'"
                      +"AND pr.kode_lokasi='" + kode_lokasi + "'"
                      + "AND p.tgl_return_pakai between '"+ awal+"' AND '" + akhir + "'";

                //mengambil dari return penjualan
                
            } else {
                //mengambil dari detail pembelian            
                sql = "SELECT p.tgl_pembelian,db.nama_barang_edit, db.kode_barang, db.no_faktur_pembelian, db.jumlah_barang, db.hj2 FROM "
                        + "detail_pembelian db JOIN pembelian p WHERE db.no_faktur_pembelian=p.no_faktur_pembelian "
                        + "AND db.kode_barang='" + kode_barang + "'"
                        + "AND db.kode_lokasi='" + kode_lokasi + "'";
                System.out.println(sql);

                //mengambil dari detail penjualan
                sql2 = "SELECT p.tgl_penjualan, dj.kode_barang, dj.nama_barang_edit, dj.no_faktur_penjualan, dj.jumlah_barang, dj.harga_jual2 FROM "
                        + "penjualan_detail dj JOIN penjualan p WHERE dj.no_faktur_penjualan=p.no_faktur_penjualan "
                        + "AND dj.kode_barang='" + kode_barang + "'"
                        + "AND dj.kode_lokasi='" + kode_lokasi + "'";
                System.out.println(sql2);
                //mengambil dari return pembelian
                sql3 = "SELECT p.tgl_return_pakai, pr.kode_barang, pr.nama_barang_edit, pr.no_faktur_return_pakai, pr.jumlah_barang, pr.harga_pembelian FROM "
                        + "pembelian_detail_return_pakai pr JOIN pembelian_return_pakai p WHERE p.no_faktur_return_pakai=pr.no_faktur_return_pakai "
                        + "AND pr.kode_barang='" + kode_barang + "'"
                        + "AND pr.kode_lokasi='" + kode_lokasi + "'";
                System.out.println(sql3);
                //mengambil dari return penjualan
    
            }
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            int baris = 0;
            while (res.next()) {
                Double jml_beli = Double.parseDouble(res.getString("jumlah_barang"));
                saldo=saldo+jml_beli;
                System.out.println("saldo "+baris+"="+saldo);
                model.addRow(new Object[]{"", "", "","","",""});
                model.setValueAt(res.getString("tgl_pembelian"), baris, 0);
                model.setValueAt(res.getString("no_faktur_pembelian"), baris, 1);
                model.setValueAt(jml_beli, baris, 2);
                model.setValueAt("0", baris, 3);
                model.setValueAt(saldo, baris, 4);
                model.setValueAt(res.getString("hj2"), baris, 5);
                baris++;
            }
            conn.close();
            res.close();
            
            Connection conn2 = (Connection) Koneksi.configDB();
            Statement stat2 = conn2.createStatement();
            ResultSet res2 = stat2.executeQuery(sql2);
            int baris2 = baris;
            while (res2.next()) {
                Double jml_jual = Double.parseDouble(res2.getString("jumlah_barang"));
                saldo=saldo-jml_jual;
                System.out.println("saldo "+baris2+"="+saldo);
                
                model.addRow(new Object[]{"", "", "", "", "", ""});
                model.setValueAt(res2.getString("tgl_penjualan"), baris2, 0);
                model.setValueAt(res2.getString("no_faktur_penjualan"), baris2, 1);
                model.setValueAt(jml_jual, baris2, 3);
                model.setValueAt("0", baris2, 2);
                model.setValueAt(saldo, baris2, 4);
                model.setValueAt(res2.getString("harga_jual2"), baris2, 5);
                baris2++;
            }
            conn2.close();
            res2.close();
            
            Connection conn3 = (Connection) Koneksi.configDB();
            Statement stat3 = conn3.createStatement();
            ResultSet res3 = stat3.executeQuery(sql3);
            int baris3 = 0;
            while (res3.next()) {
                Double jml_beli = Double.parseDouble(res3.getString("jumlah_barang"));
                saldo = saldo - jml_beli;
                System.out.println("saldo " + baris3 + "=" + saldo);
                model.addRow(new Object[]{"", "", "", "", "", ""});
                model.setValueAt(res3.getString("tgl_return_pakai"), baris3, 0);
                model.setValueAt(res3.getString("no_faktur_return_pakai"), baris3, 1);
                model.setValueAt(jml_beli, baris3, 3);
                model.setValueAt("0", baris3, 2);
                model.setValueAt(saldo, baris3, 4);
                model.setValueAt(res3.getString("harga_pembelian"), baris3, 5);
                baris3++;
            }
            conn3.close();
            res3.close();


        } catch (NullPointerException e) {
          //  System.out.println("tanggal kosong");
//            JOptionPane.showMessageDialog(null, "Tanggal tidak boleh kosong");    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror = " + e);
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

        comLokasi = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        lbl_namaBrg = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_history = new javax.swing.JTable();
        namaBarang = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        tgl_akhir = new com.toedter.calendar.JDateChooser();
        tgl_awal = new com.toedter.calendar.JDateChooser();
        comKodeBarang = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        comLokasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TOKO", "PUSAT", "GUD63", "TENGAH", "UTARA" }));
        comLokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comLokasiActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel24.setText("Tanggal");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.lightGray));

        lbl_namaBrg.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lbl_namaBrg.setText("Nama Barang");

        tbl_history.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null, null, null, null},
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
                "Tanggal", "Transaksi", "Masuk", "Keluar", "Saldo", "Harga Jual"
            }
        ));
        tbl_history.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_historyMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_history);
        if (tbl_history.getColumnModel().getColumnCount() > 0) {
            tbl_history.getColumnModel().getColumn(0).setMaxWidth(100);
            tbl_history.getColumnModel().getColumn(1).setMaxWidth(250);
            tbl_history.getColumnModel().getColumn(2).setMaxWidth(100);
            tbl_history.getColumnModel().getColumn(3).setMaxWidth(100);
            tbl_history.getColumnModel().getColumn(4).setMaxWidth(150);
            tbl_history.getColumnModel().getColumn(5).setMaxWidth(150);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(397, 397, 397)
                .addComponent(lbl_namaBrg)
                .addContainerGap(409, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lbl_namaBrg)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                .addContainerGap())
        );

        namaBarang.setBackground(new java.awt.Color(255, 255, 204));
        namaBarang.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        namaBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                namaBarangMouseClicked(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel25.setText("s.d.");

        tgl_akhir.setDateFormatString(" yyyy- MM-dd");
        tgl_akhir.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tgl_akhirPropertyChange(evt);
            }
        });
        tgl_akhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tgl_akhirKeyReleased(evt);
            }
        });

        tgl_awal.setDateFormatString(" yyyy- MM-dd");

        comKodeBarang.setEditable(true);
        comKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comKodeBarangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jSeparator1))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 512, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tgl_akhir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(3, 3, 3)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setSize(new java.awt.Dimension(979, 562));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void namaBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_namaBarangMouseClicked
        //       Penjualan_Penjualan_Pilihcustomer pppc = new Penjualan_Penjualan_Pilihcustomer();
        //       pppc.setVisible(true);
        //       pppc.setFocusable(true);
    }//GEN-LAST:event_namaBarangMouseClicked

    private void comLokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comLokasiActionPerformed
        lokasi=comLokasi.getSelectedItem().toString();
        System.out.println("lokasi " + comLokasi.getSelectedItem().toString());
        loadTable();
    }//GEN-LAST:event_comLokasiActionPerformed

    private void tbl_historyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_historyMouseClicked
        String faktur = tbl_history.getValueAt(tbl_history.getSelectedRow(), 1).toString();
        System.out.println("faktur: "+faktur);
        if (faktur.contains("PJ")) {
            Penjualan_Penjualan pp = new Penjualan_Penjualan(tbl_history.getValueAt(tbl_history.getSelectedRow(), 0).toString(), tbl_history.getValueAt(tbl_history.getSelectedRow(), 1).toString());
            pp.setVisible(true);
            pp.setFocusable(true);
        } else if (faktur.contains("PB")) {
            Pembelian_Transaksi pt = new Pembelian_Transaksi(tbl_history.getValueAt(tbl_history.getSelectedRow(), 0).toString(), tbl_history.getValueAt(tbl_history.getSelectedRow(), 1).toString());
            pt.setVisible(true);
            pt.setFocusable(true);

        } else if (faktur.contains("RB")) {
            Pembelian_Return pr = new Pembelian_Return(tbl_history.getValueAt(tbl_history.getSelectedRow(), 0).toString(), tbl_history.getValueAt(tbl_history.getSelectedRow(), 1).toString());
            pr.setVisible(true);
            pr.setFocusable(true);

        } 
//else if (faktur.contains("RJ")) {
//            Penjualan_ReturPenjualan pj = new Penjualan_ReturPenjualan(tbl_history.getValueAt(tbl_history.getSelectedRow(), 0).toString(), tbl_history.getValueAt(tbl_history.getSelectedRow(), 1).toString());
//            pj.setVisible(true);
//            pj.setFocusable(true);
//        }

    }//GEN-LAST:event_tbl_historyMouseClicked

    private void tgl_akhirKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_akhirKeyReleased
        loadTable();
    }//GEN-LAST:event_tgl_akhirKeyReleased

    private void tgl_akhirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tgl_akhirPropertyChange
        loadTable();
    }//GEN-LAST:event_tgl_akhirPropertyChange

        void load_dari_kode_barang() {
        //int selectedRow = tbl_history.getSelectedRow();
        String nama_awal = String.valueOf(comKodeBarang.getSelectedItem());
        String[] split = new String[2];
        System.out.println("nilai comTable barang adalah " + comKodeBarang.getSelectedItem());
        if (comKodeBarang.getSelectedItem() != null) {
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
                this.kode_barang = kode;
                comKodeBarang.setSelectedItem(kode);
                namaBarang.setText(nama);
                lbl_namaBrg.setText(nama);
     //           loadNumberTable();
     //           loadComTableSatuan();
//                if (selectedRow != -1) {
//                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
//                    tbl_history.setValueAt(kode, selectedRow, 1);
//                    tbl_history.setValueAt(nama, selectedRow, 2);
//                }
            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }
    private void comKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comKodeBarangActionPerformed
        System.out.println("action");
        load_dari_kode_barang();
        
    }//GEN-LAST:event_comKodeBarangActionPerformed

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
            java.util.logging.Logger.getLogger(Master_Barang_HistoryStok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Barang_HistoryStok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Barang_HistoryStok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Barang_HistoryStok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Master_Barang_HistoryStok().setVisible(true);
                new Master_Barang_HistoryStok().setFocusable(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comKodeBarang;
    private javax.swing.JComboBox<String> comLokasi;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl_namaBrg;
    private javax.swing.JTextField namaBarang;
    private javax.swing.JTable tbl_history;
    private com.toedter.calendar.JDateChooser tgl_akhir;
    private com.toedter.calendar.JDateChooser tgl_awal;
    // End of variables declaration//GEN-END:variables
}
