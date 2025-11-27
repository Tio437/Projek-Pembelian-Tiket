package com.projekpbo.pemesanan_tiket_bioskop.Repositories;

import com.projekpbo.pemesanan_tiket_bioskop.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    // Metode custom untuk Spring Security
    // Mencari Admin berdasarkan username-nya
    Optional<Admin> findByUsername(String email);
}
