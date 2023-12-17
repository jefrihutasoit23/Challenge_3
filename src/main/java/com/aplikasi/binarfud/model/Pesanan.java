package com.aplikasi.binarfud.model;

public class Pesanan {
    private Menu menu;
    private int jumlah;

    public Pesanan(Menu menu, int jumlah) {
        this.menu = menu;
        this.jumlah = jumlah;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getTotal() {
        return menu.getHarga() * jumlah;
    }

    @Override
    public String toString() {
        return menu.getNama() + " --> " + jumlah + " x " + menu.getHarga() + " = " + getTotal();
    }
}