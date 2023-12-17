package com.aplikasi.binarfud.view;

public class View {
    public void displayMenu() {
        System.out.println("--------------MENU UTAMA--------------");
        System.out.println("Pilih Makanan Yang Tersedia :\n" +
                "1. Nasi Goreng     | 15.000\n" +
                "2. Mie Goreng      | 13.000\n" +
                "3. Nasi + Ayam     | 18.000\n" +
                "4. Es Teh Manis    | 3.000\n" +
                "5. Es Jeruk        | 5.000\n" +
                "6. Konfirmasi Pesanan\n" +
                "7. Tampilkan Riwayat Transaksi ke terminal\n" +
                "8. Hitung Total Pemasukan\n" +
                "9. Keluar \n");
        System.out.print("MASUKAN PILIHAN ANDA = ");
    }

    public void intro() {
        System.out.println("------Selamat Datang di BinarFud------");
        System.out.println("======================================");
    }
}


//implement stream on menu and lambda
