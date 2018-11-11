
package Java;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class modelsaldokeuangan extends AbstractTableModel{
      private List<ListSaldoKeuangan> list;

    public modelsaldokeuangan(List<ListSaldoKeuangan> list) {
        this.list=list;
    } 
     
     @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return list.get(rowIndex).getNo();
            case 1:
                return list.get(rowIndex).getNama_keuangan();
            case 2:
                return list.get(rowIndex).getNomor_keuangan();
            case 3:
                return list.get(rowIndex).getAtas_nama();
            case 4:
                return list.get(rowIndex).getSaldo_keuangan();
            default:
                return null;
}
    }
    
@Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "NO";
            case 1:
                return "NAMA";
            case 2:
                return "Nomor Rekening"; 
            case 3:
                return "Atas Nama";
            case 4:
                return "Saldo";
            default:
                return null;
        }
    }
}