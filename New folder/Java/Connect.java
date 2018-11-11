package Java;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Connect {
    
    public Connection conn;
    public Statement st;

    public Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/elecomp_cat73", "root", "");
            st = conn.createStatement();
            System.out.println("Connection Successful!");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.toString());
            System.exit(1);
        }
    }
    
    public Connection Connect(){
        return conn;
    }
    
    public void simpanData(String sql) {
        try {
            Connect();
            st.executeUpdate(sql);
        } catch (SQLException x) {
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
        } catch (SQLException x) {
            JOptionPane.showMessageDialog(null, "Tutup Koneksi Gagal, Pesan error \n" + x);
        }
    }

    public ResultSet ambilData(String sql) {
        ResultSet rs = null;
        try {
            Connect();
            rs = st.executeQuery(sql);
        } catch (SQLException x) {
            System.out.println(x.toString());
        }
        return rs;
    }

    public Statement createStatement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
