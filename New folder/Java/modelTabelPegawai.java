package Java;

import java.util.List;
import javax.swing.table.AbstractTableModel;
//tabel

public class modelTabelPegawai extends AbstractTableModel {

    private List<ListPegawai> list;

    public modelTabelPegawai(List<ListPegawai> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getKode_pegawai();
            case 1:
                return list.get(rowIndex).getKode_unik();
            case 2:
                return list.get(rowIndex).getNama_pegawai();
            case 3:
                return list.get(rowIndex).getKode_lokasi();
            case 4:
                return list.get(rowIndex).getAlamat_pegawai();
            case 5:
                return list.get(rowIndex).getKota_pegawai();
            case 6:
                return list.get(rowIndex).getTelepon_pegawai();
            case 7:
                return list.get(rowIndex).getContact_pegawai();

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
                return "Kode Unik";
            case 2:
                return "Nama";
            case 3:
                return "Lokasi";
            case 4:
                return "Alamat";
            case 5:
                return "Kota";
            case 6:
                return "Telepon";
            case 7:
                return "Contact";
            default:
                return null;
        }
    }

}
