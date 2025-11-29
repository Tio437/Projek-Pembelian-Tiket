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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

// Menggunakan @Controller karena akan mengembalikan halaman HTML
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private FilmService filmService;
    @Autowired
    private StudioService studioService;
    @Autowired
    private JadwalTayangService jadwalTayangService;
    @Autowired
    private KursiService kursiService;

    // --- DASHBOARD ---
    @GetMapping("/dashboard")
    public String showAdminDashboard() { return "admin-dashboard"; }
    @GetMapping
    public String redirectToDashboard() { return "redirect:/admin/dashboard"; }

    // --- MANAJEMEN FILM ---
    @GetMapping("/films")
    public String showFilmManagementPage(Model model) {
        model.addAttribute("daftarFilm", filmService.findAll());
        model.addAttribute("filmBaru", new Film());
        return "admin-films";
    }
    @PostMapping("/films/save")
    public String saveNewFilm(@ModelAttribute Film filmBaru) {
        filmService.save(filmBaru);
        return "redirect:/admin/films";
    }

    // --- MANAJEMEN STUDIO (KEMBALI KE NORMAL) ---
    @GetMapping("/studios")
    public String showStudioManagementPage(Model model) {
        model.addAttribute("daftarStudio", studioService.findAll());
        model.addAttribute("studioBaru", new Studio());
        return "admin-studios";
    }

    @PostMapping("/studios/save")
    public String saveNewStudio(@ModelAttribute Studio studioBaru) {
        // Simpan studio biasa tanpa generate kursi
        studioService.save(studioBaru);
        return "redirect:/admin/studios";
    }

    // --- FITUR HAPUS STUDIO ---
    @GetMapping("/studios/delete/{id}")
    public String deleteStudio(@PathVariable Long id) {
        // Hapus studio berdasarkan ID
        // Note: Karena CascadeType.ALL pada relasi Kursi, 
        // semua kursi di studio ini juga akan otomatis terhapus.
        studioService.deleteById(id);
        
        return "redirect:/admin/studios";
    }

    // --- MANAJEMEN KURSI (DETAIL STUDIO) ---
    @GetMapping("/studio/{id}")
    public String showStudioDetailPage(@PathVariable Long id, Model model) {
        Optional<Studio> studioOpt = studioService.findById(id);
        if (!studioOpt.isPresent()) return "redirect:/admin/studios";
        
        model.addAttribute("studio", studioOpt.get());
        model.addAttribute("kursiBaru", new Kursi());
        return "admin-studio-detail";
    }

    @PostMapping("/studio/addKursi")
    public String saveNewKursi(@RequestParam Long studioId, @ModelAttribute Kursi kursiBaru) {
        Studio studio = studioService.findById(studioId).orElseThrow();
        kursiBaru.setStudio(studio);
        kursiService.save(kursiBaru);
        return "redirect:/admin/studio/" + studioId;
    }

   /**
     * FITUR: GENERATOR KURSI OTOMATIS
     */
    @PostMapping("/studio/generate")
    public String generateKursiOtomatis(@RequestParam Long studioId, @RequestParam int template) {
        
        Studio studio = studioService.findById(studioId)
                .orElseThrow(() -> new RuntimeException("Studio tidak ditemukan"));

        // 1. Update kapasitas
        studio.setKapasitas(template);
        studioService.save(studio);

        // 2. PERBAIKAN: HAPUS KURSI LAMA DULU!
        // Ini mencegah kursi bertambah terus (duplikat) saat tombol ditekan berkali-kali
        kursiService.deleteAllByStudioId(studioId);

        // 3. Logika Generate Baru
        int jumlahBaris = template / 10; 
        int kursiPerBaris = 10;          

        for (int i = 0; i < jumlahBaris; i++) {
            char namaBaris = (char) ('A' + i); 
            
            for (int j = 1; j <= kursiPerBaris; j++) {
                Kursi kursi = new Kursi();
                kursi.setNomorBaris(String.valueOf(namaBaris));
                kursi.setNomorKursi(j);
                kursi.setJenis("Regular");
                kursi.setStudio(studio);
                
                kursiService.save(kursi);
            }
        }

        return "redirect:/admin/studio/" + studioId;
    }

    // --- MANAJEMEN JADWAL ---
    @GetMapping("/jadwals")
    public String showJadwalManagementPage(Model model) {
        model.addAttribute("daftarJadwal", jadwalTayangService.findAll());
        model.addAttribute("daftarFilm", filmService.findAll());
        model.addAttribute("daftarStudio", studioService.findAll());
        model.addAttribute("jadwalBaru", new JadwalTayang());
        return "admin-jadwals";
    }

    @PostMapping("/jadwals/save")
    public String saveNewJadwal(
            @RequestParam Long filmId, @RequestParam Long studioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime waktuMulai,
            @RequestParam Double hargaTiket) {
        
        Film film = filmService.findById(filmId).orElseThrow();
        Studio studio = studioService.findById(studioId).orElseThrow();
        JadwalTayang jadwal = new JadwalTayang(waktuMulai, waktuMulai.plusMinutes(film.getDurasiMenit()), hargaTiket, film, studio);
        jadwalTayangService.save(jadwal);
        return "redirect:/admin/jadwals";
    }
     // ==========================================
    // FITUR HAPUS & EDIT (TAMBAHAN)
    // ==========================================

    // --- 1. HAPUS FILM ---
    @GetMapping("/films/delete/{id}")
    public String deleteFilm(@PathVariable Long id) {
        // Hapus film berdasarkan ID
        filmService.deleteById(id);
        return "redirect:/admin/films";
    }

    // --- 2. HAPUS JADWAL ---
    @GetMapping("/jadwals/delete/{id}")
    public String deleteJadwal(@PathVariable Long id) {
        // Hapus jadwal berdasarkan ID
        jadwalTayangService.deleteById(id);
        return "redirect:/admin/jadwals";
    }

    // --- 3. TAMPILKAN HALAMAN EDIT JADWAL ---
    @GetMapping("/jadwals/edit/{id}")
    public String showEditJadwalPage(@PathVariable Long id, Model model) {
        // Cari jadwal yang mau diedit
        JadwalTayang jadwal = jadwalTayangService.findById(id)
                .orElseThrow(() -> new RuntimeException("Jadwal tidak ditemukan"));

        // Kirim data jadwal lama + data pendukung (Film & Studio) untuk dropdown
        model.addAttribute("jadwal", jadwal);
        model.addAttribute("daftarFilm", filmService.findAll());
        model.addAttribute("daftarStudio", studioService.findAll());

        return "admin-jadwal-edit"; // Kita akan buat file HTML ini nanti
    }

    // --- 4. SIMPAN PERUBAHAN JADWAL (UPDATE) ---
    @PostMapping("/jadwals/update")
    public String updateJadwal(
            @RequestParam Long id, // ID Jadwal yang diedit
            @RequestParam Long filmId,
            @RequestParam Long studioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime waktuMulai,
            @RequestParam Double hargaTiket) {

        // Ambil jadwal lama dari database
        JadwalTayang jadwal = jadwalTayangService.findById(id)
                .orElseThrow(() -> new RuntimeException("Jadwal tidak ditemukan"));

        // Ambil data baru Film dan Studio
        Film film = filmService.findById(filmId).orElseThrow();
        Studio studio = studioService.findById(studioId).orElseThrow();

        // Update data jadwal yang lama dengan yang baru
        jadwal.setFilm(film);
        jadwal.setStudio(studio);
        jadwal.setWaktuMulai(waktuMulai);
        jadwal.setWaktuSelesai(waktuMulai.plusMinutes(film.getDurasiMenit())); // Hitung ulang waktu selesai
        jadwal.setHargaTiket(hargaTiket);

        // Simpan (JPA otomatis melakukan Update karena ID-nya sudah ada)
        jadwalTayangService.save(jadwal);

        return "redirect:/admin/jadwals";
    }   
}