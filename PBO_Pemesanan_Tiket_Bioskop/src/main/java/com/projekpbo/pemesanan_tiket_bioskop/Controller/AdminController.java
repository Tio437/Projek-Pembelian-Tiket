package com.projekpbo.pemesanan_tiket_bioskop.Controller;


import com.projekpbo.pemesanan_tiket_bioskop.models.Film;
import com.projekpbo.pemesanan_tiket_bioskop.Service.FilmService;
import com.projekpbo.pemesanan_tiket_bioskop.Service.JadwalTayangService;
import com.projekpbo.pemesanan_tiket_bioskop.models.Studio;
import com.projekpbo.pemesanan_tiket_bioskop.Service.StudioService;
import com.projekpbo.pemesanan_tiket_bioskop.models.JadwalTayang;
import com.projekpbo.pemesanan_tiket_bioskop.models.Kursi;
import com.projekpbo.pemesanan_tiket_bioskop.Repositories.JadwalTayangRepository;
import com.projekpbo.pemesanan_tiket_bioskop.Service.KursiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

// Menggunakan @Controller karena akan mengembalikan halaman HTML
@Controller
@RequestMapping("/admin") // Semua URL di sini diawali /admin
public class AdminController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private StudioService studioService;

    @Autowired
    private JadwalTayangService jadwalTayangService;

    @Autowired
    private KursiService kursiService;

    /**
     * Menangani GET /admin/dashboard
     * Menampilkan halaman dashboard utama admin
     */
    @GetMapping("/dashboard")
    public String showAdminDashboard() {
        return "admin-dashboard"; // Nama file HTML yang baru dibuat
    }

    /**
     * Menangani GET /admin
     * Mengarahkan ke halaman dashboard
     */
    @GetMapping
    public String redirectToDashboard() {
        return "redirect:/admin/dashboard";
    }
    
    /**
     * Menangani GET /admin/films
     * Menampilkan halaman manajemen film (daftar film + form tambah)
     */
     @GetMapping("/films")
    public String showFilmManagementPage(Model model) {
        
        // 1. Ambil semua film dari database
        List<Film> daftarFilm = filmService.findAll();
        
        // 2. Kirim daftar film ke halaman HTML
        model.addAttribute("daftarFilm", daftarFilm);
        
        // 3. Siapkan objek Film baru untuk diisi oleh form
        //    (Ini adalah "form-backing object" untuk th:object)
        model.addAttribute("filmBaru", new Film()); 
        
        // 4. Tampilkan halaman "admin-films.html"
        return "admin-films";
    }

    /**
     * Menangani POST /admin/films/save
     * Ini sesuai alur rencana "POST /admin/film/simpan" [cite: 53]
     */
    @PostMapping("/films/save")
    public String saveNewFilm(@ModelAttribute Film filmBaru) {
        
        // @ModelAttribute akan otomatis mengambil data dari form
        // dan membuatnya menjadi objek Film
        
        // Panggil service untuk menyimpan objek film baru
        filmService.save(filmBaru); // [cite: 55-56]
        
        // Redirect kembali ke halaman admin films
        return "redirect:/admin/films";
    }

    @GetMapping("/jadwals")
    public String showJadwalManagementPage(Model model) {
        
        // 1. Ambil data untuk form dropdown
        List<Film> daftarFilm = filmService.findAll();
        List<Studio> daftarStudio = studioService.findAll();
        
        // 2. Ambil data untuk tabel list
        List<JadwalTayang> daftarJadwal = jadwalTayangService.findAll();
        
        // 3. Kirim semua data ke halaman HTML
        model.addAttribute("daftarJadwal", daftarJadwal);
        model.addAttribute("daftarFilm", daftarFilm);
        model.addAttribute("daftarStudio", daftarStudio);
        
        // 4. Siapkan objek JadwalTayang baru untuk form
        model.addAttribute("jadwalBaru", new JadwalTayang());
        
        // 5. Tampilkan halaman "admin-jadwals.html"
        return "admin-jadwals";
    }

    /**
     * Menangani POST /admin/jadwals/save
     * Ini sesuai alur rencana "POST /admin/jadwal/tambah"
     */
    @PostMapping("/jadwals/save")
    public String saveNewJadwal(
            @RequestParam Long filmId,
            @RequestParam Long studioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime waktuMulai,
            @RequestParam Double hargaTiket) {

        // 1. Ambil objek Film dan Studio berdasarkan ID
        Film film = filmService.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film tidak ditemukan"));
        Studio studio = studioService.findById(studioId)
                .orElseThrow(() -> new RuntimeException("Studio tidak ditemukan"));

        // 2. Hitung waktu selesai
        LocalDateTime waktuSelesai = waktuMulai.plusMinutes(film.getDurasiMenit());

        // 3. Buat objek JadwalTayang baru
        JadwalTayang jadwalBaru = new JadwalTayang(
            waktuMulai,
            waktuSelesai,
            hargaTiket,
            film,
            studio
        );

        // 4. Simpan ke database
        jadwalTayangService.save(jadwalBaru);

        // 5. Redirect kembali ke halaman admin jadwals
        return "redirect:/admin/jadwals";
    }

    @PostMapping("/studio/addKursi")
    public String saveNewKursi(
            @RequestParam Long studioId,
            @ModelAttribute Kursi kursiBaru) {

        // 1. Cari studio berdasarkan ID
        Studio studio = studioService.findById(studioId)
                .orElseThrow(() -> new RuntimeException("Studio tidak ditemukan"));

        // 2. Hubungkan Kursi baru ke Studio
        kursiBaru.setStudio(studio);
        
        // 3. Simpan Kursi baru (Repository akan otomatis menyimpan FK studio_id)
        kursiService.save(kursiBaru);

        // 4. Redirect kembali ke halaman detail studio yang sama
        return "redirect:/admin/studio/" + studioId;
    }

}