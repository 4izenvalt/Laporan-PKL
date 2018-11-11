/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class koneksi {

    public Connection conn;
    public Statement st;

    public Connection Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/elecomp_cat73", "root", "");
            st = conn.createStatement();
//            System.err.println("Koneksi Sukses");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return conn;
    }

    public static void main(String[] args) {
        koneksi c = new koneksi();
//        c.Connect();
    }

    public void simpanData(String sql) {
        try {
            Connect();
            st.executeUpdate(sql);
        } catch (Exception x) {
            JOptionPane.showMessageDialog(null, "Simpan Data Gagal, Pesan error : \n" + x);
        }
    }

    public void tutupKoneksi(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            st.close();
            conn.close();
        } catch (Exception x) {
            JOptionPane.showMessageDialog(null, "Tutup Koneksi Gagal, Pesan error \n" + x);
        }
    }

    public ResultSet ambilData(String sql) {
        ResultSet rs = null;
        try {
            Connect();
            rs = st.executeQuery(sql);
        } catch (Exception x) {
            JOptionPane.showMessageDialog(null, "Ambil Data Gagal, Pesan error : \n" + x);
        }
        return rs;
    }
}
