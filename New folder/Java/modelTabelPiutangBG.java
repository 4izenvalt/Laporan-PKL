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
public class modelTabelPiutangBG extends AbstractTableModel{
    private List<ListPiutangBG> list;

    public modelTabelPiutangBG(List<ListPiutangBG> list) {
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
                return list.get(rowIndex).getNamaCustomer();
            case 4:
                return list.get(rowIndex).getKotaCustomer();
            case 5:
                return list.get(rowIndex).getNamaSalesman();
            case 6:
                return list.get(rowIndex).getKotaSalesman();
            case 7:
                return list.get(rowIndex).getBiaya();
            case 8:
                return list.get(rowIndex).getKode_customer();
            case 9:
                return list.get(rowIndex).getKode_salesman();

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
                return "No Faktur Penjualan";
            case 2:
                return "Tanggal";
            case 3:
                return "Customer";
            case 4:
                return "Kota";
            case 5:
                return "Salesman";
            case 6:
                return "Kota";
            case 7:
                return "Biaya";
            default:
                return null;
        }
    }
}
