package com.projekpbo.pemesanan_tiket_bioskop.Repositories;


import com.projekpbo.pemesanan_tiket_bioskop.models.Pelanggan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PelangganRepository extends JpaRepository<Pelanggan, Long> {
    
    // Metode custom untuk Spring Security
    // Mencari Pelanggan berdasarkan username-nya
    Optional<Pelanggan> findByUsername(String email);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
