/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;

import java.util.List;
import javax.swing.table.AbstractTableModel;
//tabel
public class modelTabelAkun extends AbstractTableModel{
    
    private List<ListAkun> list;

    public modelTabelAkun(List<ListAkun> list) {
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
                return list.get(rowIndex).getkode_akun();
            case 1:
                return list.get(rowIndex).getNama_akun();
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
                return "Nama";
            default:
                return null;
        }
    }

    
}
