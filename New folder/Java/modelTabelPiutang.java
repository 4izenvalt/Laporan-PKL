package Java;

import java.util.List;
import javax.swing.table.AbstractTableModel;
//tabel

public class modelTabelPiutang extends AbstractTableModel {

    private List<ListPiutang> list;

    public modelTabelPiutang(List<ListPiutang> list) {
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
                return null;
            case 1:
                return list.get(rowIndex).getNomor();
            case 2:
                return list.get(rowIndex).getNo_faktur();
            case 3:
                return list.get(rowIndex).getTanggal();
            case 4:
                return list.get(rowIndex).getBiaya();
            case 5:
                return list.get(rowIndex).getSisa();

            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "";
            case 1:
                return "No";
            case 2:
                return "No Faktur Penjualan";
            case 3:
                return "Tanggal";
            case 4:
                return "Biaya";
            case 5:
                return "Sisa";
            default:
                return null;
        }
    }

}
