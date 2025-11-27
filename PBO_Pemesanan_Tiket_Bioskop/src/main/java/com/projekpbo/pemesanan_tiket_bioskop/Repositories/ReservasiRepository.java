package com.projekpbo.pemesanan_tiket_bioskop.Repositories;


import com.projekpbo.pemesanan_tiket_bioskop.models.Reservasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservasiRepository extends JpaRepository<Reservasi, Long> {
    
    // Nanti kita mungkin butuh ini untuk melihat riwayat reservasi pelanggan
    List<Reservasi> findByPelangganId(Long pelangganId);

    // Nanti kita mungkin butuh ini untuk melihat siapa saja yg pesan di 1 jadwal
    List<Reservasi> findByJadwalTayangId(Long jadwalId);
}
