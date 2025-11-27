package com.projekpbo.pemesanan_tiket_bioskop.Controller;


import com.projekpbo.pemesanan_tiket_bioskop.models.Film;
import com.projekpbo.pemesanan_tiket_bioskop.models.JadwalTayang;
import com.projekpbo.pemesanan_tiket_bioskop.Service.FilmService;
import com.projekpbo.pemesanan_tiket_bioskop.Service.JadwalTayangService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Optional;

// Menggunakan @Controller karena akan mengembalikan halaman HTML
@Controller
public class JadwalController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private JadwalTayangService jadwalTayangService;

    /**
     * Menangani alur: "User klik satu film GET /film/{idFilm}"
     [cite_start]* [cite: 73]
     */
    @GetMapping("/film/{id}")
    public String showJadwalForFilm(@PathVariable Long id, Model model) {
        
        // 1. Cari data film berdasarkan ID
        Optional<Film> filmOpt = filmService.findById(id);
        
        if (filmOpt.isPresent()) {
            Film film = filmOpt.get();
            
            // 2. Cari semua jadwal tayang untuk film tersebut
            List<JadwalTayang> daftarJadwal = jadwalTayangService.findByFilmId(id);
            
            // 3. Kirim data film dan data jadwal ke halaman HTML
            model.addAttribute("film", film);
            model.addAttribute("daftarJadwal", daftarJadwal);
            
            // 4. Tampilkan file "detail-film.html"
            return "detail-film";
        } else {
            // Jika film tidak ditemukan, kembalikan ke halaman utama
            return "redirect:/";
        }
    }
}