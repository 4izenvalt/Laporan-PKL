/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author USER
 */
public class modelTabelKonversi extends AbstractTableModel{
    private List<ListKonversi> list;

    public modelTabelKonversi(List<ListKonversi> list) {
        this.list=list;
    }
   

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return list.get(rowIndex).getKode_konversi();
            case 1:
                return list.get(rowIndex).getNama_konversi();
            default:
                return null;
        }
    }
    
     @Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Kode";
            case 1:
                return "Nama Konversi";
            default:
                return null;
        }
    }
}
