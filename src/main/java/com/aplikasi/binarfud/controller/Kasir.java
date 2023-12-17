package com.aplikasi.binarfud.controller;

import com.aplikasi.binarfud.model.Menu;
import com.aplikasi.binarfud.model.Pesanan;
import lombok.Getter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Kasir {
    private List<Menu> daftarMenu;
    @Getter
    private static List<Pesanan> pesanan;
    private static List<String> historyBaru;

    public Kasir() {
        daftarMenu = new ArrayList<>();
        pesanan = new ArrayList<>();
        historyBaru = new ArrayList<>();

        // Inisialisasi daftar menu
        daftarMenu.add(new Menu("Nasi Goreng", 15000));
        daftarMenu.add(new Menu("Mie Goreng", 13000));
        daftarMenu.add(new Menu("Nasi + Ayam", 18000));
        daftarMenu.add(new Menu("Es Teh Manis", 3000));
        daftarMenu.add(new Menu("Es Jeruk", 5000));
    }

    public void pesanMenu(int menuUtama, int jumlah) {
        int indeks = menuUtama - 1;

        Menu menu = daftarMenu.get(indeks);

        if (jumlah <= 0) {
            System.out.println("Jumlah pesanan harus lebih dari 0.");
            return;
        }

        Pesanan pesananBaru = new Pesanan(menu, jumlah);
        pesanan.add(pesananBaru);

        String operasi = pesananBaru.toString();
        historyBaru.add(operasi);

        System.out.println("==kembali ke manu utama== \n");
    }

    public void reviewPesanan() {
        System.out.println("==Pemilihan menu selesai== \n");
        System.out.println("REVIEW PESANAN:");

        int totalHarga = pesanan.stream()
                .mapToInt(Pesanan::getTotal)
                .sum();

        pesanan.forEach(p -> System.out.println(p.toString()));

        System.out.println("---------------------------------------------------=");
        System.out.println("Total harga       = Rp. " + totalHarga);
        System.out.println(" ");

        Scanner sc = new Scanner(System.in);
        System.out.println("\n" +
                "1. Konfirmasi & Bayar\n" +
                "2. Kembali Ke Menu Utama\n" +
                "3. Keluar Aplikasi\n");
        System.out.print("MASUKAN PILIHAN ANDA = ");
        int option = sc.nextInt();

        if (option == 1) {
            try {
                FileWriter fileWriter = new FileWriter("struk_belanja.txt");
                PrintWriter printWriter = new PrintWriter(fileWriter);

                printWriter.println("\n===============================================");
                printWriter.println("____________________BinarFud___________________");
                printWriter.println("===============================================\n");
                printWriter.println("Berikut adalah pesanan anda :");

                pesanan.forEach(p -> printWriter.println(p.toString()));

                printWriter.println("---------------------------------------------------=");
                printWriter.println("Total harga       = Rp. " + totalHarga);
                printWriter.println("\nPembayaran        : BinarCash\n");
                printWriter.println("===============================================");
                printWriter.println("___simpan struk ini sebagai bukti pembayaran___");
                printWriter.println("===============================================");

                printWriter.close();
                System.out.println("Struk belanja telah disimpan ke dalam file struk_belanja.txt");
            } catch (IOException e) {
                System.out.println("Terjadi kesalahan saat menyimpan file.");
            }
            cetakRiwayatKeFile();
            System.out.println("\n" +
                    "1. Kembali ke menu utama\n" +
                    "2. Keluar Aplikasi\n");
            System.out.print("MASUKAN PILIHAN ANDA = ");
            int option1 = sc.nextInt();
            if (option1 == 1) {
                return;
            } else if (option1 == 2) {
                System.exit(0);
            }
        }else if (option == 3) {
            System.exit(0);
        }
    }

    public static void reset() {
        pesanan.clear();
        historyBaru.clear();
    }
    public static List<String> getHistoryBaru() {
        return historyBaru;
    }

    public static List<String> bacaRiwayatDariFile(String fileName) {
        List<String> history = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            history = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Gagal membaca file " + fileName);
        }
        return history;
    }

    public static void tampilkanRiwayat(List<String> history) {
        System.out.println("Riwayat Transaksi:");
        history.forEach(System.out::println);
    }

    public void cetakRiwayatKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("history.txt", true))) {
            System.out.println("Menyimpan riwayat ke file history.txt...");
            historyBaru.forEach(operasi -> {
                try {
                    writer.write(operasi + "\n");
                } catch (IOException e) {
                    System.out.println("Gagal mencetak riwayat ke file history.txt.");
                }
            });
            System.out.println("Riwayat telah dicetak ke dalam file history.txt.");
        } catch (IOException e) {
            System.out.println("Gagal mencetak riwayat ke file history.txt.");
        }
    }
    public int hitungTotal(List<String> history) {
        return history.stream()
                .map(this::parseTotalFromOperasi)
                .reduce(0, Integer::sum);
    }

    public int parseTotalFromOperasi(String operasi) {
        String[] parts = operasi.split("=");
        if (parts.length == 2) {
            try {
                return Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }


}