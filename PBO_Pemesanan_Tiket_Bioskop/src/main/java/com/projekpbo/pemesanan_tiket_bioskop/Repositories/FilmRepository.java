package com.projekpbo.pemesanan_tiket_bioskop.Repositories;

import com.projekpbo.pemesanan_tiket_bioskop.models.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository menandakan ini adalah komponen Spring untuk operasi data
@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    
    // JpaRepository<Film, Long> berarti:
    // "Repository ini mengelola entitas 'Film' 
    //  yang memiliki Primary Key bertipe 'Long'"

    // Spring Data JPA akan otomatis menyediakan metode seperti:
    // - save(Film film)           (Untuk Tambah Film / Update Film)
    // - findById(Long id)        (Untuk mencari film berdasarkan ID)
    // - findAll()                (Untuk mengambil semua film)
    // - deleteById(Long id)      (Untuk menghapus film)
    // ...dan masih banyak lagi, tanpa perlu kita tulis kodenya.
}