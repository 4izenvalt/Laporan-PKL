package Java;

import java.util.ArrayList;

public class myArrlist<E> extends ArrayList<E> {
    private ArrayList<ListCustomer> lc;
    private ArrayList<ListSalesman> ls;

    public myArrlist() {
        lc = new ArrayList<>();
        ls = new ArrayList<>();
//        System.out.println(lc.get(0));
    }

    public ArrayList<ListCustomer> getLc() {
        return lc;
    }

    public ArrayList<ListSalesman> getLs() {
        return ls;
    }

    public int getModCount() {
        return modCount;
    }

    public void setModCount(int modCount) {
        this.modCount = modCount;
    }
}
