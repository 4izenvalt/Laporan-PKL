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
 * @author Miracle
 */
public class modelTabelTokoLaporanToko extends AbstractTableModel{
    
    private List<ListTokoLaporanToko> list;

    public modelTabelTokoLaporanToko (List<ListTokoLaporanToko> list) {
        this.list=list;
    }
   

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return list.get(rowIndex).getNo_faktur();
            case 1:
                return list.get(rowIndex).getTanggal();
            case 2:
//                return list.get(rowIndex).getTotal();
            case 3:
//                return list.get(rowIndex).getSaldo();
            default:
                return null;
        }
    }
    
     @Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "No Faktur";
            case 1:
                return "Tanggal";
            case 2:
                return "Total";
            case 3:
                return "Saldo";
            case 4:
                return "Aksi";
            default:
                return null;
        }
    }

    
}

