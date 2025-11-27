package com.projekpbo.pemesanan_tiket_bioskop.Service;


import com.projekpbo.pemesanan_tiket_bioskop.models.Kursi;
import com.projekpbo.pemesanan_tiket_bioskop.models.Reservasi;
import com.projekpbo.pemesanan_tiket_bioskop.Repositories.ReservasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservasiService {

    @Autowired
    private ReservasiRepository reservasiRepository;

    /**
     * Sesuai rencana: "Service akan mengecek kursi mana saja 
     * yang sudah dipesan untuk jadwal tersebut."
     *
     */
    public Set<Kursi> getBookedSeats(Long jadwalId) {
        
        // 1. Cari semua reservasi yang terkait dengan jadwalId ini
        List<Reservasi> reservasiUntukJadwal = reservasiRepository.findByJadwalTayangId(jadwalId);

        // 2. Kumpulkan semua Set<Kursi> dari setiap reservasi
        //    menjadi satu Set<Kursi> besar

        // 2. Kumpulkan semua Set<Kursi> dari setiap reservasi
        //    menjadi satu Set<Kursi> besar
        Set<Kursi> kursiDipesan = reservasiUntukJadwal.stream()
                .flatMap(reservasi -> reservasi.getKursiDipesan().stream())
                .collect(Collectors.toSet());

        return kursiDipesan;
    }

    /**
     * Sesuai rencana: "Menyimpan Reservasi ke database."
     *
     */
    public Reservasi save(Reservasi reservasi) {
        return reservasiRepository.save(reservasi);
    }
}