package com.projekpbo.pemesanan_tiket_bioskop.Controller;


import com.projekpbo.pemesanan_tiket_bioskop.models.Film;
import com.projekpbo.pemesanan_tiket_bioskop.Service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

// @RestController menandakan ini adalah Controller untuk Web (API)
@RestController
// Semua URL di kelas ini akan diawali dengan /api/film
@RequestMapping("/api/film")
public class FilmController {

    // Otomatis menghubungkan ke FilmService
    @Autowired
    private FilmService filmService;

    // Menangani permintaan GET /api/film
    // [cite: 125-130]
    @GetMapping
    public List<Film> getAllFilms() {
        // Memanggil service untuk mengambil semua objek film
        return filmService.findAll();
    }

    // Menangani permintaan POST /api/film
    // [cite: 131-137]
    @PostMapping
    public Film tambahFilmBaru(@RequestBody Film film) {
        // Menerima data JSON dari web,
        // Spring otomatis mengubahnya jadi Objek Film (OOP)
        return filmService.save(film);
    }
}
