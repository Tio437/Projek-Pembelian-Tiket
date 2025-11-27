package com.projekpbo.pemesanan_tiket_bioskop.Repositories;

import com.projekpbo.pemesanan_tiket_bioskop.models.Kursi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KursiRepository extends JpaRepository<Kursi, Long> {
    
    // Nanti kita mungkin butuh ini untuk mencari semua kursi di 1 studio
    List<Kursi> findByStudioId(Long studioId);
}