package com.aplikasi.binarfud.controller;

import com.aplikasi.binarfud.view.View;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Controller {
    public void run() {
        Scanner sc = new Scanner(System.in);
        Kasir kasir = new Kasir();
        View view = new View();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("history.txt", true))) {
            List<String> history = Kasir.bacaRiwayatDariFile("history.txt");
            view.intro();

            while (true) {
                view.displayMenu();
                int menuUtama = sc.nextInt();

                if (menuUtama == 9) {
                    Kasir.reset();
                    break; // Keluar dari program
                }

                if (menuUtama < 1 || menuUtama > 9) {
                    System.out.println("pilihan tidak valid. Silakan coba lagi.");
                    continue;
                }

                if (menuUtama == 7) {
                    Kasir.tampilkanRiwayat(history);
                    List<String> historyBaru = kasir.getHistoryBaru();
                    if (!historyBaru.isEmpty()) {
                        Kasir.tampilkanRiwayat(historyBaru);
                    }
                    System.out.println(" ");
                    continue;
                } else if (menuUtama == 8) {
                    int totalPemasukan = kasir.hitungTotal(history);
                    List<String> historyBaru = kasir.getHistoryBaru();
                    if (!historyBaru.isEmpty()) {
                        totalPemasukan += kasir.hitungTotal(historyBaru);
                    }
                    System.out.println("Total hasil semua transaksi: Rp." + totalPemasukan);
                    System.out.println(" ");
                    continue;
                }
                switch (menuUtama) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        System.out.print("Berapa Pesanan Anda : ");
                        int jumlah = sc.nextInt();
                        kasir.pesanMenu(menuUtama, jumlah);
                        break;
                    case 6:
                        kasir.reviewPesanan();
                        break;
                }
            }
            System.out.println("===Keluar Aplikasi===");
        } catch (IOException e) {
            System.out.println("Gagal menulis ke file history.txt.");
        }
    }
}
