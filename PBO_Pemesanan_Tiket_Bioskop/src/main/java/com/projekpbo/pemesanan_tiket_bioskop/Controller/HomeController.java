package com.projekpbo.pemesanan_tiket_bioskop.Controller;

import com.projekpbo.pemesanan_tiket_bioskop.models.Film;
import com.projekpbo.pemesanan_tiket_bioskop.models.Pelanggan;
import com.projekpbo.pemesanan_tiket_bioskop.Service.FilmService;
import com.projekpbo.pemesanan_tiket_bioskop.Service.PelangganService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

// Penting: @Controller, BUKAN @RestController
@Controller
public class HomeController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private PelangganService pelangganService;

    // Menangani permintaan GET / (Halaman Utama)
    // Sesuai alur rencana "User membuka GET /" [cite: 69]
    @GetMapping("/")
    public String showHomePage(Model model) {
        
        // 1. Panggil service untuk mengambil data film [cite: 70]
        List<Film> films = filmService.findAll(); 
        
        // 2. Tambahkan daftar film ke "model"
        // "daftarFilm" adalah nama variabel yang akan digunakan di HTML
        model.addAttribute("daftarFilm", films); 
        
        // 3. Kembalikan nama file HTML yang ingin ditampilkan
        // Spring Boot akan otomatis mencari "index.html" di folder templates
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        // Cukup kembalikan nama file HTML-nya
        return "login";
    }

    /**
     * Menangani GET /register
     * Menampilkan halaman registrasi
     */
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        // Siapkan objek Pelanggan kosong untuk diisi form
        model.addAttribute("pelangganBaru", new Pelanggan());
        return "registration";
    }

    /**
     * Menangani POST /register
     * Memproses data form registrasi
     */
    @PostMapping("/register")
    public String processRegistration(
            @ModelAttribute Pelanggan pelangganBaru,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes) {

        // 1. Validasi Password
        if (!pelangganBaru.getPassword().equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Password dan Konfirmasi Password tidak cocok!");
            return "redirect:/register";
        }

        // 2. Validasi Username Duplikat
        if (pelangganService.existsByUsername(pelangganBaru.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "Username sudah terpakai!");
            return "redirect:/register";
        }

        // 3. Validasi Email Duplikat
        if (pelangganService.existsByEmail(pelangganBaru.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email sudah terdaftar!");
            return "redirect:/register";
        }

        // 4. Jika lolos validasi, daftarkan pelanggan
        // (Service akan otomatis mengenkripsi password)
        pelangganService.registerPelanggan(pelangganBaru);
        
        redirectAttributes.addFlashAttribute("success", "Akun berhasil dibuat! Silakan login.");
        return "redirect:/login";
    }
}
