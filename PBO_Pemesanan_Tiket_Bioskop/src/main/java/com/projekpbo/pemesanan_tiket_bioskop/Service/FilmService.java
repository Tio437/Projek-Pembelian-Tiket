package com.projekpbo.pemesanan_tiket_bioskop.Service;

import com.projekpbo.pemesanan_tiket_bioskop.models.Film;
import com.projekpbo.pemesanan_tiket_bioskop.Repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// @Service menandakan kelas ini adalah lapisan Logika Bisnis (Business Logic)
@Service
public class FilmService {

    // @Autowired otomatis "menyuntikkan" (inject)
    // instance dari FilmRepository ke dalam service ini
    @Autowired
    private FilmRepository filmRepository;

    // Metode untuk mengambil semua film (seperti di contoh)
    // [cite: 127-129]
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    // Metode untuk menyimpan film baru (seperti di contoh)
    // [cite: 133, 136]
    public Film save(Film film) {
        return filmRepository.save(film);
    }
    
    // --- Metode Tambahan yang Umum ---

    // Metode untuk mencari satu film berdasarkan ID-nya
    public Optional<Film> findById(Long id) {
        return filmRepository.findById(id);
    }
    
    // Metode untuk menghapus film berdasarkan ID
    public void deleteById(Long id) {
        filmRepository.deleteById(id);
    }
}
