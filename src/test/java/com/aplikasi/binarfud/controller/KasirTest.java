package com.aplikasi.binarfud.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class KasirTest {
    private Kasir kasir;

    @BeforeEach
    void setUp() {
        kasir = new Kasir();
    }

    @Test
    void pesanMenu() {
        // Positive test case
        kasir.pesanMenu(1, 1);
        assertEquals(1, Kasir.getPesanan().size());
}

    @Test
    void reviewPesanan() {
        // Positive test case
        kasir.pesanMenu(1, 1);
        String input = "1\n1\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
        kasir.reviewPesanan();
        assertFalse(Kasir.getPesanan().isEmpty());

        // Negative test case
        Kasir.reset();
        String input1 = "1\n1\n";
        System.setIn(new java.io.ByteArrayInputStream(input1.getBytes()));
        kasir.reviewPesanan();
        assertTrue(Kasir.getPesanan().isEmpty());
    }

    @Test
    void reset() {
        // Positive test case
        kasir.pesanMenu(1, 1);
        Kasir.reset();
        assertTrue(Kasir.getPesanan().isEmpty());

        // Negative test case
        kasir.pesanMenu(1, 1);
        Kasir.reset();
        assertTrue(kasir.getHistoryBaru().isEmpty());
    }

    @Test
    void getHistoryBaru() {
        // Positive test case
        kasir.pesanMenu(1, 1);
        assertFalse(kasir.getHistoryBaru().isEmpty());

        // Negative test case
        Kasir.reset();
        assertTrue(kasir.getHistoryBaru().isEmpty());
    }

    @Test
    void bacaRiwayatDariFile() throws IOException {
        // Positive test case
        String testFileName = "test_history.txt";
        Files.write(new File(testFileName).toPath(), "1000\n2000\n".getBytes());
        List<String> history = Kasir.bacaRiwayatDariFile(testFileName);
        assertEquals(2, history.size());

        // Negative test case
        history = Kasir.bacaRiwayatDariFile("nonexistent.txt");
        assertTrue(history.isEmpty());
    }

    @Test
    void tampilkanRiwayat() {
        // Positive test case
        kasir.pesanMenu(1, 1);
        Kasir.tampilkanRiwayat(kasir.getHistoryBaru());
    }

    @Test
    void cetakRiwayatKeFile() throws IOException {
        // Positive test case
        kasir.pesanMenu(1, 1);
        kasir.cetakRiwayatKeFile();
        File file = new File("history.txt");
        assertTrue(file.exists());
    }

    @Test
    void hitungTotal() {
        // Positive test case
        List<String> history = new ArrayList<>();
        history.add("Operasi1 = 1000");
        history.add("Operasi2 = 2000");
        int total = kasir.hitungTotal(history);
        assertEquals(3000, total);

        // Negative test case
        history.add("Operasi3 = InvalidValue");
        total = kasir.hitungTotal(history);
        assertEquals(3000, total); // Invalid operasi akan diabaikan
    }

    @Test
    void parseTotalFromOperasi() {
        // Positive test case
        String operasi = "Operasi = 1000";
        int total = kasir.parseTotalFromOperasi(operasi);
        assertEquals(1000, total);

        // Negative test case
        operasi = "InvalidOperasi = InvalidValue";
        total = kasir.parseTotalFromOperasi(operasi);
        assertEquals(0, total); // Operasi invalid akan mengembalikan 0
    }
}
