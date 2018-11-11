/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class modelTabelBarangGroup extends AbstractTableModel {
      private List<ListBarangGroup> list;
      
      public modelTabelBarangGroup(List<ListBarangGroup> list) {
        this.list = list;
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
       switch (columnIndex) {
          case 0: 
               return  list.get(rowIndex).getNo();
          case 1:
                return list.get(rowIndex).getKode_barang();
          case 2:
                return list.get(rowIndex).getKode_group();
          case 3:
                return list.get(rowIndex).getJumlah_group();
          case 4:
                return list.get(rowIndex).getNama_konversi();
          case 5:
                return list.get(rowIndex).getKode_konversi();
        
          default:
                return null;
        }
    
    }
     @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "No.";
            case 1:
                return "Kode Barang";
            case 2:
                return "Nama";
            case 3:
                return "Jumlah";
            case 4:
                return "Satuan";
                
            default:
                return null;
        }

}}


