package com.projekpbo.pemesanan_tiket_bioskop.Controller;

import com.projekpbo.pemesanan_tiket_bioskop.models.JadwalTayang;
import com.projekpbo.pemesanan_tiket_bioskop.models.Kursi;
import com.projekpbo.pemesanan_tiket_bioskop.models.Studio;
import com.projekpbo.pemesanan_tiket_bioskop.models.Reservasi;
import com.projekpbo.pemesanan_tiket_bioskop.models.Pelanggan;
import com.projekpbo.pemesanan_tiket_bioskop.Service.KursiService;
import com.projekpbo.pemesanan_tiket_bioskop.Service.PelangganService;
import com.projekpbo.pemesanan_tiket_bioskop.Service.JadwalTayangService;
import com.projekpbo.pemesanan_tiket_bioskop.Service.ReservasiService;
import com.projekpbo.pemesanan_tiket_bioskop.Repositories.KursiRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Optional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.List;

@Controller
public class ReservasiController {

    @Autowired private ReservasiService reservasiService;
    @Autowired private JadwalTayangService jadwalTayangService;
    @Autowired private KursiService kursiService;
    @Autowired private PelangganService pelangganService;
    @Autowired private KursiRepository kursiRepository; // Untuk pengurutan kursi

    // --- 1. HALAMAN PILIH KURSI ---
    @GetMapping("/reservasi/{idJadwal}")
    public String showPilihKursiPage(@PathVariable Long idJadwal, Model model) {
        Optional<JadwalTayang> jadwalOpt = jadwalTayangService.findById(idJadwal);
        if (!jadwalOpt.isPresent()) return "redirect:/";

        JadwalTayang jadwal = jadwalOpt.get();
        Studio studio = jadwal.getStudio();
        
        // Ambil kursi urut (A-Z, 1-10)
        List<Kursi> semuaKursi = kursiRepository.findByStudioIdOrderByNomorBarisAscNomorKursiAsc(studio.getId());
        Set<Kursi> kursiDipesan = reservasiService.getBookedSeats(idJadwal);

        model.addAttribute("jadwal", jadwal);
        model.addAttribute("semuaKursi", semuaKursi);
        model.addAttribute("kursiDipesan", kursiDipesan);

        return "pilih-kursi";
    }

    // --- 2. PROSES PESAN (Masuk ke Konfirmasi Pembayaran) ---
    @PostMapping("/reservasi/pesan")
    public String prosesPemesanan(
            @RequestParam Long jadwalId,
            @RequestParam(required = false) List<Long> kursiIds,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        // Validasi: Harus pilih kursi
        if (kursiIds == null || kursiIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Silakan pilih minimal satu kursi!");
            return "redirect:/reservasi/" + jadwalId;
        }

        String username = principal.getName();
        Pelanggan pelanggan = pelangganService.findByUsername(username).orElseThrow();
        JadwalTayang jadwal = jadwalTayangService.findById(jadwalId).orElseThrow();
        List<Kursi> kursiDipesan = kursiService.findAllById(kursiIds);

        Double totalHarga = jadwal.getHargaTiket() * kursiDipesan.size();

        Reservasi reservasi = new Reservasi();
        reservasi.setPelanggan(pelanggan);
        reservasi.setJadwalTayang(jadwal);
        reservasi.setTotalHarga(totalHarga);
        reservasi.setKursiDipesan(new HashSet<>(kursiDipesan));
        reservasi.setKodeBooking(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        
        // Status awal: BELUM BAYAR
        reservasi.setStatus("MENUNGGU_PEMBAYARAN"); 
        reservasi.setWaktuReservasi(LocalDateTime.now());

        reservasiService.save(reservasi);
        
        // Redirect ke halaman Konfirmasi Pembayaran
        return "redirect:/reservasi/pembayaran/" + reservasi.getId();
    }

    // --- 3. HALAMAN KONFIRMASI PEMBAYARAN ---
    @GetMapping("/reservasi/pembayaran/{idReservasi}")
    public String showHalamanPembayaran(@PathVariable Long idReservasi, Model model) {
        Optional<Reservasi> reservasiOpt = reservasiService.findById(idReservasi);
        if (!reservasiOpt.isPresent()) return "redirect:/";
        
        model.addAttribute("reservasi", reservasiOpt.get());
        return "konfirmasi-pembayaran";
    }

    // --- 4. PROSES BAYAR (Update Status -> Lunas) ---
    @PostMapping("/reservasi/bayar")
    public String prosesBayar(@RequestParam Long reservasiId) {
        Optional<Reservasi> reservasiOpt = reservasiService.findById(reservasiId);
        if (reservasiOpt.isPresent()) {
            Reservasi reservasi = reservasiOpt.get();
            reservasi.setStatus("LUNAS");
            reservasiService.save(reservasi);
            
            // Redirect ke Halaman Barang Bukti (Tiket)
            return "redirect:/reservasi/tiket/" + reservasi.getId();
        }
        return "redirect:/";
    }

    // --- 5. HALAMAN TIKET (Barang Bukti) ---
    @GetMapping("/reservasi/tiket/{idReservasi}")
    public String showTiket(@PathVariable Long idReservasi, Model model) {
        Optional<Reservasi> reservasiOpt = reservasiService.findById(idReservasi);
        if (!reservasiOpt.isPresent()) return "redirect:/";

        model.addAttribute("reservasi", reservasiOpt.get());
        return "tiket-bioskop";
    }

    @GetMapping("/riwayat-tiket") 
    public String showRiwayat(Principal principal, Model model) {
        // ... (kode tetap sama)
        String username = principal.getName();
        Pelanggan pelanggan = pelangganService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        List<Reservasi> daftarReservasi = reservasiService.getRiwayatPelanggan(pelanggan);
        model.addAttribute("daftarReservasi", daftarReservasi);
        
        return "riwayat-tiket";
    }
}