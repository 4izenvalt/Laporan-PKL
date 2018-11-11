package Java;

import java.util.List;
import javax.swing.table.AbstractTableModel;
//tabel

public class modelTabelGroup extends AbstractTableModel {

    private List<listGroup> list;

    public modelTabelGroup(List<listGroup> list) {
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
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getNomor();
            case 1:
                return list.get(rowIndex).getKode_barang();
            case 2:
                return list.get(rowIndex).getNama_barang();
            case 3:
                return list.get(rowIndex).getHarga_jual_1_barang();
            case 4:
                return list.get(rowIndex).getHarga_jual_2_barang();
            case 5:
                return list.get(rowIndex).getHarga_jual_3_barang();

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
                return "Harga Jual 1";
            case 4:
                return "Harga Jual 2";
            case 5:
                return "Harga Jual 3";
            default:
                return null;
        }
    }

}
