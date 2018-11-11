/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;

/**
 *
 * @author USER
 */
public class ListRandom {
    private static String tombol_salesman, kode_salesman, kode_customer;

    public static String getKode_customer() {
        return kode_customer;
    }

    public static void setKode_customer(String kode_customer) {
        ListRandom.kode_customer = kode_customer;
    }

    public String getTombol_salesman() {
        return tombol_salesman;
    }

    public void setTombol_salesman(String tombol_salesman) {
        ListRandom.tombol_salesman = tombol_salesman;
        System.out.println(tombol_salesman);
    }

    public static String getKode_salesman() {
        return kode_salesman;
    }

    public static void setKode_salesman(String kode_salesman) {
        ListRandom.kode_salesman = kode_salesman;
    }

    
    
    
}
