package com.projekpbo.pemesanan_tiket_bioskop.Service;

import com.projekpbo.pemesanan_tiket_bioskop.models.JadwalTayang;
import com.projekpbo.pemesanan_tiket_bioskop.Repositories.JadwalTayangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JadwalTayangService {

    @Autowired
    private JadwalTayangRepository jadwalTayangRepository;

    /**
     * Metode ini sesuai dengan alur rencana[cite: 74]:
     * "JadwalController memanggil jadwalService.getJadwalByFilm(idFilm)"
     */
    public List<JadwalTayang> findByFilmId(Long filmId) {
        return jadwalTayangRepository.findByFilmId(filmId);
    }
    
    // --- Metode dasar lainnya yang akan berguna ---
    
    public JadwalTayang save(JadwalTayang jadwal) {
        return jadwalTayangRepository.save(jadwal);
    }
    
    public List<JadwalTayang> findAll() {
        return jadwalTayangRepository.findAll();
    }

    public void deleteById(Long id) {
        jadwalTayangRepository.deleteById(id);
    }

    public Optional<JadwalTayang> findById(Long id) {
        return jadwalTayangRepository.findById(id);
    }
}