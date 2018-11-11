/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class modelTabelSupplier extends AbstractTableModel{
    
    private List<ListSupplier> list;

    public modelTabelSupplier(List<ListSupplier> list) {
        this.list=list;
    }
   

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 10;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return list.get(rowIndex).getKode_supplier();
            case 1:
                return list.get(rowIndex).getNama_supplier();
            case 2:
                return list.get(rowIndex).getAlamat_supplier();
            case 3:
                return list.get(rowIndex).getTelepon_supplier();
            case 4:
                return list.get(rowIndex).getHp_supplier();
            case 5:
                return list.get(rowIndex).getContact_supplier();
            case 6:
                return list.get(rowIndex).getKota_supplier();
            case 7:
                return list.get(rowIndex).getTotal_hutang();
            case 8:
                return list.get(rowIndex).getKeterangan();
            case 9:
                return list.get(rowIndex).getRekening();
            default:
                return null;
        }
    }
    
     @Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Kode Supplier";
            case 1:
                return "Nama Supplier";
            case 2:
                return "Alamat ";
            case 3:
                return "Telepon ";
            case 4:
                return "Handphone ";
            case 5:
                return "Contact ";
            case 6:
                return "Kota ";
            case 7:
                return "Total Hutang ";
            case 8:
                return "Keterangan ";
            case 9:
                return "Rekening ";
            default:
                return null;
        }
    }

    
}
