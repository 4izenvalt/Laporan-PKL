
package Java;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class modelTabelBarang1 extends AbstractTableModel {
    private List<ListBarang> list;

    public modelTabelBarang1(List<ListBarang> list) {
        this.list=list;
    } 
    
     @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 12;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return list.get(rowIndex).getKode_barang();
            case 1:
                return list.get(rowIndex).getNama_barang();
            case 2:
                return list.get(rowIndex).getPusat();
            case 3:
                return list.get(rowIndex).getGUD63();
            case 4:
                return list.get(rowIndex).getTOKO();
            case 5:
                return list.get(rowIndex).getTENGAH();
            case 6:
                return list.get(rowIndex).getUTARA();
//            case 2:
//                return list.get(rowIndex).getKode_lokasi();
            case 7:
                return list.get(rowIndex).getSatuan();
            case 8:
                return list.get(rowIndex).getHarga_jual_1();
            case 9:
                return list.get(rowIndex).getHarga_jual_2();
            case 10:
                return list.get(rowIndex).getHarga_jual_3();
             case 11:
                return list.get(rowIndex).getStatus();
            default:
                return null;
        }
}
    
 @Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Kode Barang";
            case 1:
                return "Nama Barang";
            case 2:
                return "Pusat"; 
            case 3:
                return "GUD63";
            case 4:
                return "TOKO";
            case 5:
                return "TENGAH";
            case 6:
                return "UTARA";
//           case 2:
//                return "Pusat";
            case 7:
                return "Satuan";
            case 8:
                return "Harga Jual 1 ";
            case 9:
                return "Harga Jual 2 ";
            case 10:
                return "Harga Jual 3 ";
            case 11:
                return "Status ";
            default:
                return null;
        }
    }
}
