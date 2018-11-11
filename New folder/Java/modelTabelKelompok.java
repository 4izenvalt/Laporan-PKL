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
 * @author Amalia
 */
//tabel
public class modelTabelKelompok extends AbstractTableModel{
    
    private List<ListKelompok> list;

    public modelTabelKelompok(List<ListKelompok> list) {
        this.list=list;
    }
   

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return list.get(rowIndex).getId_kelompok();
            case 1:
                return list.get(rowIndex).getKode_kelompok();
            case 2:
                return list.get(rowIndex).getNama_kelompok();
            default:
                return null;
        }
    }
    
     @Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "No";
            case 1:
                return "Kode Kelompok";
            case 2:
                return "Nama Kelompok";
            default:
                return null;
        }
    }

    
}
