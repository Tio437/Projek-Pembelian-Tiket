package com.projekpbo.pemesanan_tiket_bioskop.Service;

import com.projekpbo.pemesanan_tiket_bioskop.models.Kursi;
import com.projekpbo.pemesanan_tiket_bioskop.models.Pelanggan;
import com.projekpbo.pemesanan_tiket_bioskop.models.Reservasi;
import com.projekpbo.pemesanan_tiket_bioskop.Repositories.ReservasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservasiService {

    @Autowired
    private ReservasiRepository reservasiRepository;

    /**
     * Mengecek kursi mana saja yang sudah dipesan untuk jadwal tersebut.
     */
    public Set<Kursi> getBookedSeats(Long jadwalId) {
        // 1. Cari semua reservasi yang terkait dengan jadwalId ini
        List<Reservasi> reservasiUntukJadwal = reservasiRepository.findByJadwalTayangId(jadwalId);

        // 2. Kumpulkan semua Set<Kursi> dari setiap reservasi
        return reservasiUntukJadwal.stream()
                .flatMap(reservasi -> reservasi.getKursiDipesan().stream())
                .collect(Collectors.toSet());
    }

    /**
     * Menyimpan Reservasi ke database.
     */
    public Reservasi save(Reservasi reservasi) {
        return reservasiRepository.save(reservasi);
    }

    /**
     * PERBAIKAN UTAMA ADA DI SINI
     * Mencari reservasi berdasarkan ID.
     */
    public Optional<Reservasi> findById(Long idReservasi) {
        // HAPUS baris 'throw new ...' dan ganti dengan ini:
        return reservasiRepository.findById(idReservasi);
    }

    /**
     * Mengambil riwayat pesanan pelanggan.
     */
    public List<Reservasi> getRiwayatPelanggan(Pelanggan pelanggan) {
        return reservasiRepository.findByPelangganOrderByWaktuReservasiDesc(pelanggan);
    }

    public List<Reservasi> findAll() {
        return reservasiRepository.findAll();
    }
}