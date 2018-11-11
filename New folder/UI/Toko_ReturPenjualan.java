/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Connect;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.File;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * ab
 *
 * @author Dii
 */
public class Toko_ReturPenjualan extends javax.swing.JFrame {

    private HashMap konversi, idReturn, jmlBarang, hashCode;
    private DefaultTableModel tabelBarang, tabelRetur;
    private ResultSet hasil;
    public Statement stmt;
    private Connect connection;
    private String code_barang, code_konversi, harga;
    private String noNota = "";
    private PreparedStatement PS;
    private String[] konvv;
    private String nofakturnya;
    private int set_harga;

//    private String noreturne = noFakturTPReturn().toString();
    public Toko_ReturPenjualan() {
        initComponents();
//        this.setLocationRelativeTo(null);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        connection = new Connect();
        konversi = new HashMap();
        idReturn = new HashMap();
        jmlBarang = new HashMap();
        hashCode = new HashMap();
        tabelBarang = new DefaultTableModel(new String[]{"No.", "Kode", "Nama Barang"}, 0);
        jTable2.setModel(tabelBarang);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(5);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(5);
        tabelRetur = new DefaultTableModel(new String[]{"No.", "Barang", "Satuan", "Jumlah", "Harga", "Saldo"}, 0);
        jTable3.setModel(tabelRetur);
        jTable3.getColumnModel().getColumn(0).setPreferredWidth(1);
        jTable3.getColumnModel().getColumn(2).setPreferredWidth(5);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(5);
        jTable3.getColumnModel().getColumn(4).setPreferredWidth(5);
        jTable3.getColumnModel().getColumn(5).setPreferredWidth(5);
        showDate();
        isiTabelReturn("*");
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

    public void setPlaceHolder(javax.swing.JTextField a, String b) {
        if (b == null) {
            b = "Search";
        }
        if (a.getText().equals(b)) {
            a.setText("");
        } else if (a.getText() == null || a.getText().equals("")) {
            a.setText(b);
        }
    }

    public void showDate() {
        java.util.Timer t = new java.util.Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                java.util.Date d = new java.util.Date();
                java.text.SimpleDateFormat dt = new java.text.SimpleDateFormat("E dd-MM-yyyy 'at' hh:mm:ss a");
                vdatetime.setText(dt.format(d));
            }
        }, 500, 500);
    }

    private String tampilTabelDataBarang(String search) {
        String data = "";
        try {
            data = "SELECT TPD.kode_barang, B.nama_barang, TPD.jumlah_barang "
                    + "FROM barang B, toko_penjualan_detail TPD "
                    + "WHERE no_faktur_toko_penjualan = '" + noNota + "' AND "
                    + "TPD.kode_barang = B.kode_barang "
                    + (search.equalsIgnoreCase("*") ? "" : "AND B.nama_barang LIKE '%" + search + "%' ") + " "
                    + "ORDER BY TPD.kode_barang";
            hasil = connection.ambilData(data);
            setModelTabelBarang(hasil);
        } catch (Exception e) {
            System.out.println("Error /Toko_ReturPenjualan/tampilTabelDataBarang -> " + e);
        }
        return data;
    }

    private String loadBarang(String search) {
        if (search.equals("*")) {
            search = "";
        }
        String data = "";
        try {
            data = "SELECT kode_barang, nama_barang "
                    + "FROM barang "
                    + "Where nama_barang like '%" + search + "%' "
                    + "ORDER BY nama_barang";
//            System.out.println("sqlloadbarang: " + data);
            hasil = connection.ambilData(data);
            setModelTabelBarang(hasil);
        } catch (Exception e) {
            System.out.println("Error /Toko_ReturPenjualan/tampilTabelDataBarang -> " + e);
        }
        return data;
    }

    public void setModelTabelBarang(ResultSet hasil) {
        try {
            int no = 1;
            while (hasil.next()) {
                String kode = hasil.getString("kode_barang");
                String nama = hasil.getString("nama_barang");
//                System.out.println("kodeee: "+kode);
                tabelBarang.addRow(new Object[]{no, kode, nama});
                jmlBarang.put(kode, 0);
                no++;
            }
        } catch (Exception e) {
            System.out.println("Error /Toko_ReturPenjualan/setModel -> " + e);
        }
    }

    private void deleteTabel(DefaultTableModel NamaTabel) {
        int baris = NamaTabel.getRowCount();
        for (int i = 0; i < baris; i++) {
            NamaTabel.removeRow(0);
        }
    }

    private void isiPilihBarang(String kode) {
        try {
//            String data = "SELECT TPD.kode_barang, B.nama_barang, B.harga_jual_2_barang, TPD.kode_barang_konversi, K.nama_konversi "
//                    + "FROM barang B, toko_penjualan_detail TPD, konversi K "
//                    + "WHERE B.kode_barang = TPD.kode_barang AND TPD.kode_barang_konversi = K.kode_konversi AND "
//                    + "TPD.kode_barang = '"+kode+"' ";
//            String data = "SELECT TPD.kode_barang, B.nama_barang, B.harga_jual_2_barang, TPD.kode_barang_konversi, K.nama_konversi "
//                    + "FROM barang B, barang_konversi BK, toko_penjualan_detail TPD, konversi K "
//                    + "WHERE B.kode_barang = TPD.kode_barang AND TPD.kode_barang_konversi = BK.kode_barang_konversi "
//                    + "AND BK.kode_konversi = k.kode_konversi AND TPD.kode_barang = '" + kode + "' ";
            String data = "SELECT b.kode_barang, b.nama_barang, b.harga_jual_2_barang, bk.kode_barang_konversi, k.nama_konversi "
                    + "FROM barang b, barang_konversi bk, konversi k "
                    + "WHERE b.kode_barang = bk.kode_barang "
                    + "and bk.kode_konversi = k.kode_konversi "
                    + "and b.kode_barang = '" + kode + "' "
                    + "and bk.identitas_konversi = '1'";
            System.out.println("isipilihsql: " + data);
            hasil = connection.ambilData(data);
            while (hasil.next()) {
//                String kode_barang = hasil.getString("kode_barang");
                code_konversi = hasil.getString("kode_barang_konversi");
                vNamaBarang.setText(hasil.getString("nama_barang"));
                harga = hasil.getString("harga_jual_2_barang");
                set_harga = Integer.parseInt(harga);
                txtSatuan.setText(hasil.getString("nama_konversi"));
                vHarga.setText(currency_convert(Math.round(Integer.valueOf(harga))));
//                jLabel8.setText("*Jumlah Maksimal Return : "+jmlBarang.get(kode));
                code_barang = kode;
            }
//            System.out.println("isi pilih barang sukses");
//            selectKonversi(kode_barang);Operation not allowed after ResultSet closed

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Toko_Retur/isiPilihBarang " + e);
        }
    }

    private String selectLastDataDetailReturn(String year) {
        String lastNo = "";
        try {
            String data = "SELECT no_faktur_toko_penjualan_return "
                    + "FROM toko_penjualan_return "
                    + "ORDER BY id_toko_penjualan_return DESC LIMIT 1";
            hasil = connection.ambilData(data);
            if (hasil.next()) {
                String nomor = hasil.getString("no_faktur_toko_penjualan_return");
                if (nomor.substring(2, 4).equalsIgnoreCase(year)) {
                    int noLama = Integer.parseInt(nomor.substring(nomor.length() - 5));
                    System.out.println(nomor);
                    System.out.println(noLama);
                    noLama++;
                    String no = Integer.toString(noLama);
                    if (no.length() == 1) {
                        no = "0000" + no;
                    } else if (no.length() == 2) {
                        no = "000" + no;
                    } else if (no.length() == 3) {
                        no = "00" + no;
                    } else if (no.length() == 4) {
                        no = "0" + no;
                    }
                    lastNo = no;
                } else {
                    lastNo = "00001";
                }

            } else {
                lastNo = "00001";
            }
        } catch (Exception e) {
            System.out.println("TokoRetur/selectLastData - " + e);
        }
        return lastNo;
    }

    private void isiTabelReturn(String search) {
        ResultSet nama_barang, harga_barang;
        try {
            String data = "SELECT TKR.id_toko_keranjang, B.nama_barang, K.nama_konversi, TKR.jumlah_barang, B.harga_jual_2_barang, TKR.harga_barang "
                    + "FROM barang B, konversi K, barang_konversi BK, toko_keranjang_return TKR "
                    + "WHERE TKR.kode_barang = B.kode_barang AND TKR.kode_barang_konversi = BK.kode_barang_konversi "
                    + "AND BK.kode_konversi = K.kode_konversi "
                    + (search.equalsIgnoreCase("*") ? "" : "AND B.nama_barang LIKE '%" + search + "%' ") + " "
                    + "ORDER BY TKR.id_toko_keranjang";
//            String data = "SELECT DT.id_toko_penjualan_return_detail, B.nama_barang, K.nama_konversi, DT.jumlah_barang, B.harga_jual_2_barang, DT.harga_barang "
//                    + "FROM toko_penjualan_detail_return DT, barang B, konversi K "
//                    + "WHERE DT.kode_barang = kode_barang AND DT.kode_barang_konversi = K.kode_konversi "
//                    + (search.equalsIgnoreCase("*") ? "" : "AND B.nama_barang LIKE '%"+search+"%' ")+" "
//                    + "ORDER BY id_toko_penjualan_return_detail";
            hasil = connection.ambilData(data);
            setModelTabelReturn(hasil);
        } catch (Exception e) {
            System.out.println("Toko_Return/isiTabelBarang - " + e);
        }
    }

    private void setModelTabelReturn(ResultSet hasil) {
        try {
            int no = 1;
            while (hasil.next()) {
                String barang = hasil.getString("nama_barang");
                String satuan = hasil.getString("nama_konversi");
                String jumlah = hasil.getString("jumlah_barang");
                String harga = String.valueOf(currency_convert(Math.round(hasil.getInt("harga_jual_2_barang"))));
                String total = currency_convert(hasil.getInt("harga_barang"));
                tabelRetur.addRow(new Object[]{no, barang, satuan, jumlah, harga, total});
                idReturn.put(no, hasil.getString("id_toko_keranjang"));
                no++;
            }
            setTotalReturn();
        } catch (Exception e) {
            System.out.println("Toko_Return/setModelTabelReturn - " + e);
        }
    }
//comment
    private void setTotalReturn() {
        try {
            int total = 0;
            String data = "SELECT harga_barang "
                    + "FROM toko_keranjang_return";
            hasil = connection.ambilData(data);
            while (hasil.next()) {
                total = total + hasil.getInt("harga_barang");
            }
            String harga = Integer.toString(total);
            jTextField5.setText(currency_convert(Integer.parseInt(harga)));
        } catch (Exception e) {
            System.out.println("Toko_Return/setTotalReturn - " + e);
        }
    }

    private void simpan() {
        System.out.println(code_barang);
        if (code_barang != null && !vJumlah.getText().equalsIgnoreCase("") && !vTotalHarga.getText().equalsIgnoreCase("")) {
//            int jumlahStok = Integer.parseInt(jmlBarang.get(code_barang).toString());
            int jml = Integer.parseInt(vJumlah.getText());
            if (jml > 0) {
                inputTokoKeranjangReturn(code_barang);
                hashCode.put(vNamaBarang.getText(), code_barang);
//                konversi.put(idSatuan(vSatuan.getSelectedItem().toString()), code_konversi);
                vNamaBarang.setText("");
//                vSatuan.removeAllItems();
                txtSatuan.setText("");
                vJumlah.setText("0");
                vHarga.setText("");
                vTotalHarga.setText("");
                code_barang = null;
                deleteTabel(tabelRetur);
                isiTabelReturn("*");
//                dPilihBarang.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Jumlah harus Kurang dari atau sama dengan Jumlah maksimal return");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Barang, Jumlah Barang & Total Harga");
        }
    }

    private void inputTokoKeranjangReturn(String codeBarang) {
        int total = Integer.parseInt(vJumlah.getText()) * set_harga;
        try {
            String data = "INSERT INTO toko_keranjang_return(kode_barang, jumlah_barang, harga_barang, kode_barang_konversi) "
                    + "VALUES ('" + codeBarang + "', '" + vJumlah.getText() + "', '" + total + "', '" + code_konversi + "')";
            connection.simpanData(data);
            dPilihBarang.dispose();
        } catch (Exception e) {
            System.out.println("Toko_Retur/inputTokoKeranjangReturn - " + e);
        }
    }

    private String noFakturTPReturn() {
        String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        String lastY = year.substring(year.length() - 2);
        String no = "RJ" + lastY + "-" + selectLastDataDetailReturn(lastY);
        return no;
    }

    private void simpanTokoDetailReturn(String noReturn) {
        PS = null;
        String kode_pegawai = "1";
        java.util.Date d = new java.util.Date();
        java.text.SimpleDateFormat dt = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = dt.format(d);

        try {
            String data = "INSERT INTO toko_penjualan_return(no_faktur_toko_penjualan_return, "
                    + "tgl_toko_penjualan_return, kode_pegawai) "
                    + "VALUES (?, ?, ?)";
            PS = connection.Connect().prepareStatement(data);
            PS.setString(1, noReturn);
            PS.setString(2, date);
            PS.setString(3, kode_pegawai);
            PS.executeUpdate();
            this.nofakturnya = noReturn;
            simpanTokoPenjualanDetailReturn(noReturn);

        } catch (Exception e) {
            System.out.println("Toko_Retur/inputTokoDetailReturn - " + e);
        }
    }
//     private void print(String tunai, String kembalian) {
//        System.out.println("print");
//        try {
//            Connect koneksi = new Connect();
//            Connection conn = koneksi.conn;
//            HashMap params = new HashMap();
//            params.put("tunai", Integer.parseInt(tunai));
//            params.put("kembalian", Integer.parseInt(kembalian));
//            System.out.println(tunai + " = " + kembalian);
//            JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath() + "/src/Laporan/print_toko_transaksi.jrxml");
//            String query = "select tp.no_faktur_toko_penjualan,"
//                    + " tp.tgl_toko_penjualan,"
//                    + " tp.total,"
//                    + " tpd.jumlah_barang,"
//                    + " tpd.harga_barang,"
//                    + " b.nama_barang,"
//                    + " k.nama_konversi,"
//                    + " p.nama_pegawai "
//                    + "from toko_penjualan tp, toko_penjualan_detail tpd, pegawai p, barang b, barang_konversi bk,"
//                    + " konversi k where tp.no_faktur_toko_penjualan = tpd.no_faktur_toko_penjualan and  tp.kode_pegawai = p.kode_pegawai"
//                    + " and tpd.kode_barang_konversi = bk.kode_barang_konversi and tpd.kode_barang = b.kode_barang and "
//                    + "bk.kode_konversi = k.kode_konversi "
//                    + "and tp.no_faktur_toko_penjualan = \"" + noFak.getText().trim() + "\"";
//            JRDesignQuery newQuery = new JRDesignQuery();
//            newQuery.setText(query);
//            jd.setQuery(newQuery);
//            JasperReport jr = JasperCompileManager.compileReport(jd);
//            JasperPrint print = JasperFillManager.fillReport(jr, params, conn);
//            printeran(print);
//            //JasperPrint print = JasperFillManager.fillReport(jr, null,conn);
////            boolean status = JasperPrintManager.printReport(print, false);
////            printjoba(print);
//
////            JasperViewer viewer = new JasperViewer(print);
////            System.out.println("sukses");
////            jPanel2.removeAll();
////            jPanel2.setLayout(new BorderLayout());
////            jPanel2.repaint();
////            jPanel2.add(new JRViewer(print));
////////            show_report.repaint();
////            jPanel2.revalidate();
//        } catch (JRException ex) {
//            Logger.getLogger(Laporan_Pembelian.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    private void simpanTokoPenjualanDetailReturn(String noReturn) {
        PS = null;
        int i = jTable3.getRowCount();
        try {
            for (int j = 0; j < i; j++) {
                String namaB = jTable3.getValueAt(j, 1).toString();
                String kodeB = selectNoBarang(namaB);
                String namaS = jTable3.getValueAt(j, 2).toString();
                String kodeS = selectKodeKonversi(namaS);
                String data2 = "INSERT INTO toko_penjualan_detail_return"
                        + "(no_faktur_toko_penjualan_return, kode_barang, "
                        + "jumlah_barang, harga_barang, kode_barang_konversi) "
                        + "VALUES (?, ?, ?, ?, ?)";
                PS = connection.Connect().prepareStatement(data2);
                PS.setString(1, noReturn);
                PS.setString(2, kodeB);
                PS.setString(3, jTable3.getValueAt(j, 3).toString());
                PS.setString(4, jTable3.getValueAt(j, 5).toString());
                PS.setString(5, kodeS);
                PS.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Return Berhasil Disimpan");
//            vNota.setText("Nomor Nota");
//            vNota.setEditable(true);
//            vOk.setText("OK");
            deleteKeranjangReturn();
            deleteTabel(tabelRetur);
            jTextField5.setText("0");
            print();
        } catch (Exception e) {
            System.out.println("Toko_Retur/simpanTokoPenjualanDetailReturn - " + e);
        }
    }

    private void print() {
        System.out.println("print");

        try {
            Connect koneksi = new Connect();
            Connection conn = koneksi.conn;
            HashMap params = new HashMap();
            JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath() + "/src/Laporan/Toko_Return_Penjualan.jrxml");
            String query = "select tpr.no_faktur_toko_penjualan_return, b.nama_barang, tpdr.jumlah_barang, k.nama_konversi, tpdr.harga_barang, "
                    + "tpr.tgl_toko_penjualan_return, tpr.tgl_toko_penjualan_return,p.nama_pegawai "
                    + "FROM toko_penjualan_return tpr, toko_penjualan_detail_return tpdr, pegawai p, konversi k, barang b "
                    + "WHERE tpr.no_faktur_toko_penjualan_return = tpdr.no_faktur_toko_penjualan_return "
                    + "AND tpr.kode_pegawai = p.kode_pegawai AND tpdr.kode_barang = b.kode_barang "
                    + "AND tpdr.kode_barang_konversi = k.kode_konversi AND tpr.no_faktur_toko_penjualan_return = \"" + nofakturnya + "\"";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(query);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint print = JasperFillManager.fillReport(jr, params, conn);
            printeran(print);
            System.out.println(query);
            System.out.println("nofak??????? : " + nofakturnya);
            //JasperPrint print = JasperFillManager.fillReport(jr, null,conn);
//            boolean status = JasperPrintManager.printReport(print, false);
//            printjoba(print);

//            JasperViewer viewer = new JasperViewer(print);
//            System.out.println("sukses");
//            jPanel2.removeAll();
//            jPanel2.setLayout(new BorderLayout());
//            jPanel2.repaint();
//            jPanel2.add(new JRViewer(print));
//////            show_report.repaint();
//            jPanel2.revalidate();
        } catch (JRException ex) {
            Logger.getLogger(Laporan_Pembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printeran(JasperPrint printan) {
        System.out.println("Printeran");
        try {

//    String report = JasperCompileManager.compileReportToFile(sourceFileName);
//
//    JasperPrint jasperPrint = JasperFillManager.fillReport(report, para, ds);
            PrintService[] services = PrinterJob.lookupPrintServices();
            for (int i = 0; i < services.length; i++) {
                System.out.println(services[i].getName());
            }
            System.out.println("cetakan");
            String printerNameShort = services[4].getName();
            System.out.println(printerNameShort);
            PrinterJob printerJob = PrinterJob.getPrinterJob();

            PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
            printerJob.defaultPage(pageFormat);

            int selectedService = 0;

            AttributeSet attributeSet = new HashPrintServiceAttributeSet(new PrinterName(printerNameShort, null));

            PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, attributeSet);

            try {
                printerJob.setPrintService(printService[selectedService]);

            } catch (Exception e) {

                System.out.println(e);
            }
            JRPrintServiceExporter exporter;
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
            printRequestAttributeSet.add(new Copies(1));

            // these are deprecated
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, printan);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService[selectedService]);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService[selectedService].getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            exporter.exportReport();

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private void deleteKeranjangReturn() {
        try {
            String data = "DELETE FROM toko_keranjang_return";
            connection.simpanData(data);
        } catch (Exception e) {
            System.out.println("Toko_Retur/deleteKeranjangReturn - " + e);
        }
    }

    private String selectNoBarang(String namaB) {
        String noB = "";
        try {
            String data = "SELECT kode_barang "
                    + "FROM barang "
                    + "WHERE nama_barang = ?";
            PS = connection.Connect().prepareStatement(data);
            PS.setString(1, namaB);
            hasil = PS.executeQuery();
            while (hasil.next()) {
                noB = hasil.getString("kode_barang");
            }
        } catch (Exception e) {
            System.out.println("Toko_Retur/selectNoBarang - " + e);
        }
        return noB;
    }

    private String selectKodeKonversi(String namaK) {
        String noK = "";
        try {
            String data = "SELECT kode_konversi "
                    + "FROM konversi "
                    + "WHERE nama_konversi = ?";
            PS = connection.Connect().prepareStatement(data);
            PS.setString(1, namaK);
            hasil = PS.executeQuery();
            while (hasil.next()) {
                noK = hasil.getString("kode_konversi");
            }
        } catch (Exception e) {
            System.out.println("Toko_Retur/selectNoBarang - " + e);
        }
        return noK;
    }

    private void hapusBarang(String kode) {
        try {
            String data = "DELETE FROM toko_keranjang_return "
                    + "WHERE id_toko_keranjang = '" + kode + "' ";
            connection.simpanData(data);
            JOptionPane.showMessageDialog(null, "Barang berhasil dihapus");
            deleteTabel(tabelRetur);
            isiTabelReturn("*");
        } catch (Exception e) {
            System.out.println("Toko_Return/hapusBarang - " + e);
        }
    }

    private void hitungTotalHarga() {
        int jumlah = Integer.parseInt(vJumlah.getText().toString());
        int total = set_harga * jumlah;
        String settotal = String.valueOf(Math.round(Integer.valueOf(total)));
        vTotalHarga.setText(currency_convert(Integer.parseInt(settotal)));
    }

    private String selectNoNota(String nomor) {
        String noNota = "";
        try {
            String data = "SELECT no_faktur_toko_penjualan "
                    + "FROM toko_penjualan "
                    + "WHERE no_faktur_toko_penjualan = '" + nomor + "' ";
            hasil = connection.ambilData(data);
            if (hasil.next()) {
                noNota = hasil.getString("no_faktur_toko_penjualan");
            } else {
                JOptionPane.showMessageDialog(null, "Nomor Nota Tidak ditemukan");
            }
        } catch (Exception e) {
            System.out.println("Toko_Retur/selectNoNota " + e);
        }
        return noNota;
    }

    private void setSatuan(String identitas) {
        try {
            //satuan
            String sqlSatuan = "select k.kode_konversi, nama_konversi, bk.kode_barang_konversi, bk.jumlah_konversi "
                    + "from barang_konversi bk, konversi k "
                    + "where bk.kode_konversi = k.kode_konversi "
                    + "and bk.kode_barang = '" + code_barang + "' "
                    + "and bk.identitas_konversi ='" + identitas + "'";
//            System.out.println("sqlsatuan: " + sqlSatuan);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sqlSatuan);
//            vSatuan.removeAllItems();
            while (res.next()) {
                String name = res.getString("nama_konversi");
                System.out.println("name: " + name);
                code_konversi = res.getString("kode_barang_konversi");
                txtSatuan.setText(name);
                vHarga.setText(String.valueOf(Integer.valueOf(harga) * res.getInt("jumlah_konversi")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Toko_Retur/setSatuan " + e);
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

        dPilihBarang = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        vNamaBarang = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        vHarga = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        vJumlah = new javax.swing.JTextField();
        vTotalHarga = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnBatal = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        txtSatuan = new javax.swing.JTextField();
        dPilihDataBarang = new javax.swing.JDialog();
        vSearch = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jReturPenjualan = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        vdatetime = new javax.swing.JLabel();
        btnTambahBarangRetur = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        btnSimpanPenjualan1 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        vSearchRetur = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        dPilihBarang.setTitle("Pilih Barang");
        dPilihBarang.setBackground(new java.awt.Color(255, 255, 255));
        dPilihBarang.setSize(new java.awt.Dimension(400, 400));
        dPilihBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dPilihBarangKeyPressed(evt);
            }
        });

        jLabel4.setText("Nama Barang");

        vNamaBarang.setEditable(false);
        vNamaBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vNamaBarangMouseClicked(evt);
            }
        });
        vNamaBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vNamaBarangKeyPressed(evt);
            }
        });

        jLabel5.setText("Satuan");

        vHarga.setEditable(false);
        vHarga.setEditable(false);
        vHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vHargaActionPerformed(evt);
            }
        });

        jLabel6.setText("Harga");

        jLabel7.setText("Jumlah");

        vJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                vJumlahKeyReleased(evt);
            }

        });
        vJumlah.setText("0");
        vJumlah.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vJumlahFocusGained(evt);
            }
        });
        vJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                vJumlahKeyTyped(evt);
            }
        });

        vTotalHarga.setEditable(false);
        vTotalHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vTotalHargaActionPerformed(evt);
            }
        });

        jLabel9.setText("Total Harga");

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnSimpan.setBackground(new java.awt.Color(85, 222, 93));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        txtSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSatuanActionPerformed(evt);
            }
        });
        txtSatuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSatuanKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSatuanKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout dPilihBarangLayout = new javax.swing.GroupLayout(dPilihBarang.getContentPane());
        dPilihBarang.getContentPane().setLayout(dPilihBarangLayout);
        dPilihBarangLayout.setHorizontalGroup(
            dPilihBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dPilihBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dPilihBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dPilihBarangLayout.createSequentialGroup()
                        .addGroup(dPilihBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(115, 115, 115)
                        .addGroup(dPilihBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vNamaBarang)
                            .addComponent(vHarga)
                            .addComponent(vJumlah)
                            .addComponent(txtSatuan)))
                    .addGroup(dPilihBarangLayout.createSequentialGroup()
                        .addGroup(dPilihBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(dPilihBarangLayout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(btnSimpan)))
                        .addGap(30, 30, 30)
                        .addGroup(dPilihBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dPilihBarangLayout.createSequentialGroup()
                                .addComponent(btnBatal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE))
                            .addComponent(vTotalHarga))))
                .addContainerGap())
        );
        dPilihBarangLayout.setVerticalGroup(
            dPilihBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dPilihBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dPilihBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(vNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dPilihBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dPilihBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dPilihBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dPilihBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(vTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(dPilihBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dPilihDataBarang.setTitle("Pilih Data Barang");
        dPilihDataBarang.setBackground(new java.awt.Color(255, 255, 255));
        dPilihDataBarang.setSize(new java.awt.Dimension(400, 227));

        vSearch.setText("Search");
        vSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                vSearchFocusLost(evt);
            }
        });
        vSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                vSearchKeyReleased(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        vSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                vSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout dPilihDataBarangLayout = new javax.swing.GroupLayout(dPilihDataBarang.getContentPane());
        dPilihDataBarang.getContentPane().setLayout(dPilihDataBarangLayout);
        dPilihDataBarangLayout.setHorizontalGroup(
            dPilihDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dPilihDataBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dPilihDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dPilihDataBarangLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(vSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        dPilihDataBarangLayout.setVerticalGroup(
            dPilihDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dPilihDataBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(vSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addContainerGap())
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jReturPenjualan.setBackground(new java.awt.Color(255, 255, 255));
        jReturPenjualan.setInheritsPopupMenu(true);
        jReturPenjualan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jReturPenjualanKeyPressed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel14.setText("Return Penjualan Toko");

        vdatetime.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        vdatetime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        vdatetime.setText("T | W");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(vdatetime, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vdatetime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnTambahBarangRetur.setText("Tambah Barang Penjualan");
        btnTambahBarangRetur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahBarangReturActionPerformed(evt);
            }
        });
        btnTambahBarangRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnTambahBarangReturKeyPressed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Barang", "Satuan", "Jumlah", "Harga", "Saldo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable3.setFocusable(false);
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable3KeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        btnSimpanPenjualan1.setText("Simpan Penjualan");
        btnSimpanPenjualan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanPenjualan1ActionPerformed(evt);
            }
        });

        jLabel16.setText("Total");

        jTextField5.setEditable(false);
        jTextField5.setText("0");

        vSearchRetur.setText("Search");
        vSearchRetur.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vSearchReturFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                vSearchReturFocusLost(evt);
            }
        });
        vSearchRetur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vSearchReturActionPerformed(evt);
            }
        });
        vSearchRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                vSearchReturKeyReleased(evt);
            }
        });

        jButton1.setText("Hapus");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jReturPenjualanLayout = new javax.swing.GroupLayout(jReturPenjualan);
        jReturPenjualan.setLayout(jReturPenjualanLayout);
        jReturPenjualanLayout.setHorizontalGroup(
            jReturPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jReturPenjualanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jReturPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                    .addGroup(jReturPenjualanLayout.createSequentialGroup()
                        .addComponent(btnTambahBarangRetur)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(vSearchRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jReturPenjualanLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jReturPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jReturPenjualanLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(jReturPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSimpanPenjualan1)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jReturPenjualanLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(210, 210, 210)))))
                .addContainerGap())
        );
        jReturPenjualanLayout.setVerticalGroup(
            jReturPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jReturPenjualanLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jReturPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahBarangRetur)
                    .addComponent(vSearchRetur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jReturPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSimpanPenjualan1)
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jReturPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jReturPenjualan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void vNamaBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vNamaBarangMouseClicked
        deleteTabel(tabelBarang);
//        tampilTabelDataBarang("*");
        loadBarang("*");
        dPilihDataBarang.show();
        dPilihDataBarang.setLocationRelativeTo(null);
    }//GEN-LAST:event_vNamaBarangMouseClicked

    private void vTotalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vTotalHargaActionPerformed

    }//GEN-LAST:event_vTotalHargaActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        dPilihBarang.dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void vSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vSearchFocusGained
        setPlaceHolder(vSearch, null);
    }//GEN-LAST:event_vSearchFocusGained

    private void vSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vSearchFocusLost
        setPlaceHolder(vSearch, null);
    }//GEN-LAST:event_vSearchFocusLost

    private void vSearchReturFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vSearchReturFocusLost
        setPlaceHolder(vSearchRetur, null);
    }//GEN-LAST:event_vSearchReturFocusLost

    private void vSearchReturFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vSearchReturFocusGained
        setPlaceHolder(vSearchRetur, null);
    }//GEN-LAST:event_vSearchReturFocusGained

    private void btnTambahBarangReturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahBarangReturActionPerformed
        dPilihBarang.show();
        dPilihBarang.setLocationRelativeTo(null);

        //        if(!vNota.getText().equals("Nomor Nota")){
//            dPilihBarang.show();
//            dPilihBarang.setLocationRelativeTo(null);
//            System.out.println("frame");
//            if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
//                System.out.println("frmae yessss");
//    //            deleteTabel(tabelBarang);
//    //    //        tampilTabelDataBarang("*");
//    //            loadBarang("*");
//    //            dPilihDataBarang.show();
//    //            dPilihDataBarang.setLocationRelativeTo(null);
//                dPilihBarang.show();
//                dPilihBarang.setLocationRelativeTo(null);
//            }
//        } else{
//            JOptionPane.showMessageDialog(null, "Silahkan masukan Nomor Nota");
//        }

    }//GEN-LAST:event_btnTambahBarangReturActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int row = jTable3.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Barang yg akan dihapus");
        } else {
            row = row + 1;
            hapusBarang(idReturn.get(row).toString());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnSimpanPenjualan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanPenjualan1ActionPerformed
        if (jTable3.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Tidak ada Barang yang Return");
        } else {
            JOptionPane.showMessageDialog(null, "simpan");
            simpanTokoDetailReturn(noFakturTPReturn());
        }
    }//GEN-LAST:event_btnSimpanPenjualan1ActionPerformed

    private void vSearchReturKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vSearchReturKeyReleased
        deleteTabel(tabelRetur);
        isiTabelReturn(vSearchRetur.getText());
    }//GEN-LAST:event_vSearchReturKeyReleased

    private void jTable3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            int row = jTable3.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Silahkan Pilih Barang yg akan dihapus");
            } else {
                row = row + 1;
                hapusBarang(idReturn.get(row).toString());
            }
        }
    }//GEN-LAST:event_jTable3KeyReleased

    private void vHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vHargaActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private String idSatuan(String nama) {
        String kode = null;
        try {
            String sql = "select kode_barang_konversi "
                    + "from barang_konversi bk, konversi k "
                    + "where bk.kode_konversi = k.kode_konversi "
                    + "and bk.kode_barang = '" + code_barang + "' "
                    + "and k.nama_konversi = '" + nama + "'";
            hasil = connection.ambilData(sql);
            while (hasil.next()) {
                kode = hasil.getString("kode_barang_konversi");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kode;
    }
    private void vSearchReturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vSearchReturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vSearchReturActionPerformed

    private void vSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vSearchKeyReleased
        deleteTabel(tabelBarang);
//        tampilTabelDataBarang("*");
        loadBarang(vSearch.getText().toString());
        dPilihDataBarang.show();
        dPilihDataBarang.setLocationRelativeTo(null);
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jTable2.requestFocus(true);
        }
    }//GEN-LAST:event_vSearchKeyReleased

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
            vSearch.requestFocus(true);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int row = jTable2.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Silahkan Pilih Barang");
            } else {
                System.out.println("row yg di dipilih " + row);
                String kode = jTable2.getModel().getValueAt(row, 1).toString();
                System.out.println("kodeee: " + kode);
                isiPilihBarang(kode);
                dPilihDataBarang.dispose();
                txtSatuan.requestFocus(true);
                hitungTotalHarga();
            }
        }
    }//GEN-LAST:event_jTable2KeyPressed

    private void jReturPenjualanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jReturPenjualanKeyPressed
        System.out.println("frame");
        if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
            System.out.println("frmae yessss");
//            deleteTabel(tabelBarang);
//    //        tampilTabelDataBarang("*");
//            loadBarang("*");
//            dPilihDataBarang.show();
//            dPilihDataBarang.setLocationRelativeTo(null);
            dPilihBarang.show();
            dPilihBarang.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_jReturPenjualanKeyPressed

    private void btnTambahBarangReturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTambahBarangReturKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            dPilihBarang.show();
            dPilihBarang.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_btnTambahBarangReturKeyPressed

    private void vNamaBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vNamaBarangKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
            deleteTabel(tabelBarang);
            //        tampilTabelDataBarang("*");
            loadBarang("*");
            dPilihDataBarang.show();
            dPilihDataBarang.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_vNamaBarangKeyPressed

    private void dPilihBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dPilihBarangKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
            deleteTabel(tabelBarang);
            //        tampilTabelDataBarang("*");
            loadBarang("*");
            dPilihDataBarang.show();
            dPilihDataBarang.setLocationRelativeTo(null);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
//            deleteTabel(tabelBarang);
//            //        tampilTabelDataBarang("*");
//            loadBarang("*");
            dPilihBarang.dispose();
//            dPilihDataBarang.show();
//            dPilihDataBarang.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_dPilihBarangKeyPressed

    private void txtSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSatuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSatuanActionPerformed

    private void txtSatuanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanKeyTyped
        char vChar = evt.getKeyChar();
        evt.consume();
        if (vChar == KeyEvent.VK_1) {
            System.out.println("satu");
            setSatuan("1");
        } else if (vChar == KeyEvent.VK_2) {
            System.out.println("dua");
            setSatuan("2");
        } else if (vChar == KeyEvent.VK_ENTER) {
            System.out.println("enter");
            vJumlah.requestFocus(true);
        } else {
            System.out.println("lain");
            evt.consume();
        }
    }//GEN-LAST:event_txtSatuanKeyTyped

    private void vJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vJumlahKeyTyped
        char vChar = evt.getKeyChar();
        if (!(Character.isDigit(vChar)
                || (vChar == KeyEvent.VK_BACK_SPACE)
                || (vChar == KeyEvent.VK_DELETE))) {
            evt.consume();
        }
        if (vChar == KeyEvent.VK_ENTER) {
            simpan();
        }
    }//GEN-LAST:event_vJumlahKeyTyped

    private void txtSatuanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanKeyReleased

    }//GEN-LAST:event_txtSatuanKeyReleased

    private void vJumlahFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vJumlahFocusGained
        setPlaceHolder(vJumlah, "0");
    }//GEN-LAST:event_vJumlahFocusGained

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        System.out.println("presss: " + evt.getKeyCode());
    }//GEN-LAST:event_formKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        System.out.println("bisa: " + evt.hashCode());
    }//GEN-LAST:event_formWindowActivated

//    private void vSearchKeyReleased(java.awt.event.KeyEvent evt){
////        aa;
//        deleteTabel(tabelBarang);
//        tampilTabelDataBarang(vSearch.getText().toString());
//    }
    private void vJumlahKeyReleased(java.awt.event.KeyEvent evt) {
//        aa
        //set text to input only int
        char vchar = evt.getKeyChar();
        if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE)) {
            evt.consume();
        }

        if (!vJumlah.getText().equalsIgnoreCase("") && !vHarga.getText().equalsIgnoreCase("")) {
            hitungTotalHarga();
        }
    }

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {
//        jTable2.getColumnName(NORMAL);
//        int row = jTable2.getSelectedRow();
//        if(row < 0){
//            JOptionPane.showMessageDialog(null, "Silahkan Pilih Barang");
//        } else{
////            System.out.println("row yg di dipilih "+row);
//            String kode = jTable2.getModel().getValueAt(row, 1).toString(); 
//            
//            isiPilihBarang(kode);
//            dPilihDataBarang.dispose();
//            hitungTotalHarga();
//        }
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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Toko_ReturPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Toko_ReturPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Toko_ReturPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Toko_ReturPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Toko_ReturPenjualan().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnSimpanPenjualan1;
    private javax.swing.JButton btnTambahBarangRetur;
    private javax.swing.JDialog dPilihBarang;
    private javax.swing.JDialog dPilihDataBarang;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jReturPenjualan;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField txtSatuan;
    private javax.swing.JTextField vHarga;
    private javax.swing.JTextField vJumlah;
    private javax.swing.JTextField vNamaBarang;
    private javax.swing.JTextField vSearch;
    private javax.swing.JTextField vSearchRetur;
    private javax.swing.JTextField vTotalHarga;
    private javax.swing.JLabel vdatetime;
    // End of variables declaration//GEN-END:variables
}
