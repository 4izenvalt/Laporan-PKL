package Java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Clock implements ActionListener {

    private JLabel jLabel;
    private JTextField jTextField;
    private SimpleDateFormat sdf;

    public Clock(JLabel jLabel, int type) {
        this.jLabel = jLabel;
        sdf = new SimpleDateFormat(type == 1 ? "hh:mm:ss" : type == 2 ? "E, d MMMM yyyy" : type == 3 ? "yyyy-MM-dd hh:mm:ss" :"yyyy-MM-dd");
        Timer t = new Timer(1000, this);
        t.start();
    }

    public Clock(JTextField jTextField, int type) {
        this.jTextField = jTextField;
        sdf = new SimpleDateFormat(type == 1 ? "hh:mm:ss a" : type == 2 ? "E, d MMMM yyyy" : "yyyy-MM-dd hh:mm:ss");
        Timer t = new Timer(1000, this);
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (jLabel != null) {
            jLabel.setText(sdf.format(new Date()));
        } 
        if (jTextField != null) {
            jTextField.setText(sdf.format(new Date()));
        }
    }

}
