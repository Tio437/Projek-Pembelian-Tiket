package com.projekpbo.pemesanan_tiket_bioskop.Repositories;


import com.projekpbo.pemesanan_tiket_bioskop.models.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {
    // Metode dasar (save, findById, findAll, delete) sudah ada
}
