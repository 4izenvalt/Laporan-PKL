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
 * @author tam
 */
public class modelTabelBarangTarget extends AbstractTableModel {

    private List<ListBarangTarget> list;

    public modelTabelBarangTarget(List<ListBarangTarget> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
     return list.size();    
    }

    @Override
    public int getColumnCount() {
      return 9;
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
                return list.get(rowIndex).getJumlah();
          case 4:
                return list.get(rowIndex).getNama_konversi();
          case 5:
                return list.get(rowIndex).getTgl();
          case 6:
                return list.get(rowIndex).getTotal();
          case 7:
                return list.get(rowIndex).getKet();
          case 8:
                return list.get(rowIndex).getNama_supplier();
          case 9:
                return list.get(rowIndex).getKode_barang_target();
          case 10:
                return list.get(rowIndex).getKode_barang_konversi();
          case 11:
                return list.get(rowIndex).getKode_supplier();
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
                return "Nama Barang";
            case 3:
                return "Jumlah Target";
            case 4:
                return "Satuan";
            case 5:
                return "Tanggal";
            case 6:
                return "Total";
            case 7:
                return "Keterangan";
            case 8:
                return "Supplier";
            default:
                return null;
        }

}}
