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
 * @author Yuel
 */
public class modelTabelHutangBG extends AbstractTableModel {
        private List<ListHutangBG> list;

    public modelTabelHutangBG(List<ListHutangBG> list) {
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
                return list.get(rowIndex).getNomor();
            case 1:
                return list.get(rowIndex).getNo_fakturBG();
            case 2:
                return list.get(rowIndex).getTanggal();
            case 3:
                return list.get(rowIndex).getNoSeri();
            case 4:
                return list.get(rowIndex).getNamaSupplier();
            case 5:
                return list.get(rowIndex).getKotaSupplier();
            case 6:
                return list.get(rowIndex).getBiaya();
            case 7:
                return list.get(rowIndex).getBiaya();

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
                return "No Faktur Pembelian";
            case 2:
                return "Tanggal";
            case 3:
                return "No Seri";
            case 4:
                return "Supplier";
            case 5:
                return "Kota";
            case 6:
                return "Biaya";
            case 7:
                return "Sub Total";
            default:
                return null;
        }
    }
}
