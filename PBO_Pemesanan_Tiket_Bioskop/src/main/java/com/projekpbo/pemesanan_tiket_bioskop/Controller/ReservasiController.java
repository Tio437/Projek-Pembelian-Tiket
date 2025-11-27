package com.projekpbo.pemesanan_tiket_bioskop.Controller;


import com.projekpbo.pemesanan_tiket_bioskop.models.JadwalTayang;
import com.projekpbo.pemesanan_tiket_bioskop.models.Kursi;
import com.projekpbo.pemesanan_tiket_bioskop.models.Studio;
import com.projekpbo.pemesanan_tiket_bioskop.models.User;
import com.projekpbo.pemesanan_tiket_bioskop.models.Reservasi;
import com.projekpbo.pemesanan_tiket_bioskop.models.Pelanggan;
import com.projekpbo.pemesanan_tiket_bioskop.Service.KursiService;
import com.projekpbo.pemesanan_tiket_bioskop.Service.PelangganService;
import com.projekpbo.pemesanan_tiket_bioskop.Service.JadwalTayangService;
import com.projekpbo.pemesanan_tiket_bioskop.Service.ReservasiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.HashSet;
import java.util.Optional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.List;

@Controller
public class ReservasiController {

    @Autowired
    private ReservasiService reservasiService;

    @Autowired
    private JadwalTayangService jadwalTayangService;

    @Autowired
    private KursiService kursiService;

    @Autowired
    private PelangganService pelangganService;

    /**
     * Menangani alur: "User memilih jadwal GET /reservasi/{idJadwal}"
     *
     */
    @GetMapping("/reservasi/{idJadwal}")
    public String showPilihKursiPage(@PathVariable Long idJadwal, Model model) {
        
        // 1. Ambil data JadwalTayang
        Optional<JadwalTayang> jadwalOpt = jadwalTayangService.findById(idJadwal);

        if (!jadwalOpt.isPresent()) {
            return "redirect:/"; // Jika jadwal tidak ada, kembali ke home
        }

        JadwalTayang jadwal = jadwalOpt.get();
        
        // 2. Ambil data Studio dari Jadwal
        Studio studio = jadwal.getStudio();
        
        // 3. Ambil *semua* kursi yang ada di studio tersebut
        Set<Kursi> semuaKursi = studio.getDaftarKursi();

        // 4. Ambil kursi yang *sudah dipesan* untuk jadwal ini
        Set<Kursi> kursiDipesan = reservasiService.getBookedSeats(idJadwal);

        // 5. Kirim semua data ke halaman HTML
        model.addAttribute("jadwal", jadwal);
        model.addAttribute("semuaKursi", semuaKursi);
        model.addAttribute("kursiDipesan", kursiDipesan);

        // 6. Tampilkan halaman "pilih-kursi.html"
        return "pilih-kursi";
    }

    @PostMapping("/reservasi/pesan")
    public String prosesPemesanan(
            @RequestParam Long jadwalId,
            @RequestParam List<Long> kursiIds,
            Principal principal) { // 'Principal' adalah user yang sedang login

        // --- 1. Validasi Data ---
        // Dapatkan user yang sedang login (Pelanggan)
        String username = principal.getName();
        // (Catatan: kita login pakai email, tapi sesi menyimpan username)
        Pelanggan pelanggan = pelangganService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Pelanggan tidak ditemukan"));
        
        // Dapatkan jadwal
        JadwalTayang jadwal = jadwalTayangService.findById(jadwalId)
                .orElseThrow(() -> new RuntimeException("Jadwal tidak ditemukan"));

        // Dapatkan objek-objek Kursi
        List<Kursi> kursiDipesan = kursiService.findAllById(kursiIds);

        // --- 2. Logika Bisnis (Sesuai Rencana ) ---

        // Menghitung totalHarga
        Double totalHarga = jadwal.getHargaTiket() * kursiDipesan.size();

        // Membuat objek Reservasi baru
        Reservasi reservasi = new Reservasi();
        reservasi.setPelanggan(pelanggan); // Menghubungkan ke Pelanggan
        reservasi.setJadwalTayang(jadwal); // Menghubungkan ke Jadwal
        reservasi.setTotalHarga(totalHarga);
        reservasi.setKursiDipesan(new HashSet<>(kursiDipesan)); // Set kursi yg dipesan
        
        // Buat data atribut lainnya
        reservasi.setKodeBooking(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        reservasi.setStatus("CONFIRMED");
        reservasi.setWaktuReservasi(LocalDateTime.now());

        // --- 3. Simpan ke Database ---
        reservasiService.save(reservasi);

        // Redirect ke halaman sukses (atau halaman utama untuk saat ini)
        return "redirect:/"; // Nanti bisa diubah ke "/reservasi/sukses"
    }
}