/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class modelTabelCustomer extends AbstractTableModel{
    
    private List<ListCustomer> list;

    public modelTabelCustomer(List<ListCustomer> list) {
        this.list=list;
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
        switch(columnIndex){
            case 0:
                return list.get(rowIndex).getKode_customer();
            case 1:
                return list.get(rowIndex).getNama_customer();
            case 2:
                return list.get(rowIndex).getContact_customer();
            case 3:
                return list.get(rowIndex).getTelepon_customer();
            case 4:
                return list.get(rowIndex).getProvinsi_customer();
            case 5:
                return list.get(rowIndex).getAlamat_customer();
            default:
                return null;
        }
    }
    
     @Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Kode Customer";
            case 1:
                return "Nama Customer";
            case 2:
                return "Contact";
            case 3:
                return "Telepon";
            case 4:
                return "Wilayah";
            case 5:
                return "Alamat";
            default:
                return null;
        }
    }
}

/*
            case 6:
                return list.get(rowIndex).getKota_customer();
            case 7:
                return list.get(rowIndex).getHari_tagihan();
            case 8:
                return list.get(rowIndex).getStatus_customer();
            case 9:
                return list.get(rowIndex).getKode_salesman();

            case 6:
                return "Kota";
            case 7:
                return "Hari Tagihan";
            case 8:
                return "Status Customer";
            case 9:
                return "Kode Salesman";
*/