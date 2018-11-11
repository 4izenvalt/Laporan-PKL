/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class modelTabelBarang extends AbstractTableModel {

    private List<ListBarang> list;

    public modelTabelBarang(List<ListBarang> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
     return list.size();    
    }

    @Override
    public int getColumnCount() {
      return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        int a = 0;
        switch (columnIndex) {
          case 0: 
               return  list.get(rowIndex).getNo();
          case 1:
                return list.get(rowIndex).getKode_barang();
          case 2:
                return list.get(rowIndex).getNama_barang();
          case 3:
                return list.get(rowIndex).getHarga_jual_1();
          case 4:
                return list.get(rowIndex).getHarga_jual_2();
          case 5:
                return list.get(rowIndex).getHarga_jual_3();
                
          default:
                return null;
        }
    
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "No";
            case 1:
                return "Kode Barang";
            case 2:
                return "Nama";
            case 3:
                return "Harga Jual 1 Barang";
            case 4:
                return "Harga Jual 2 Barang";
            case 5:
                return "Harga Jual 3 Barang";
            default:
                return null;
        }

}}
