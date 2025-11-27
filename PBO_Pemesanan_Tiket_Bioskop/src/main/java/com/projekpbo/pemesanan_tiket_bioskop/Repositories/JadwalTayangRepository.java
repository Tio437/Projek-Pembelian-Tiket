package com.projekpbo.pemesanan_tiket_bioskop.Repositories;


import com.projekpbo.pemesanan_tiket_bioskop.models.JadwalTayang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JadwalTayangRepository extends JpaRepository<JadwalTayang, Long> {
    
    // Sesuai alur rencana Anda [cite: 74] (User klik film, lalu lihat jadwal)
    // Kita butuh metode untuk mencari semua jadwal berdasarkan ID film
    List<JadwalTayang> findByFilmId(Long filmId);
}
