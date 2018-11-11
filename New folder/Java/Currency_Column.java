/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dwiki
 */


/* PENGGUNAAN

TableColumnModel m = jTableX.getColumnModel();
m.getColumn(X).setCellRenderer(new Currency_Column());

 */


public class Currency_Column extends DefaultTableCellRenderer {

    DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

    public void setValue(Object value) {
        //  Format the Object before setting its value in the renderer
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        try {
            if (value != null) {
                value = kursIndonesia.format(value);
            }
        } catch (IllegalArgumentException e) {
        }

        super.setValue(value);
    }
}
