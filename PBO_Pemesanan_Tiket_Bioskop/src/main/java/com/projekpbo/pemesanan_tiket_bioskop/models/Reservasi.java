package com.projekpbo.pemesanan_tiket_bioskop.models;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservasi")
public class Reservasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // idReservasi

    private String kodeBooking; //
    private Double totalHarga; //
    private String status; // Misal: PENDING, CONFIRMED, CANCELED
    private LocalDateTime waktuReservasi;

    // --- Relasi OOP ---


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pelanggan_id", nullable = false)
    private Pelanggan pelanggan;

   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jadwal_id", nullable = false)
    private JadwalTayang jadwalTayang;

   // Relasi: Reservasi memesan banyak Kursi (Many-to-Many) [cite: 28]
    // JPA akan membuat tabel perantara (join table) bernama 'reservasi_kursi'
    // untuk menyimpan pasangan 'reservasi_id' dan 'kursi_id'.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "reservasi_kursi",
        joinColumns = @JoinColumn(name = "reservasi_id"),
        inverseJoinColumns = @JoinColumn(name = "kursi_id")
    )
    private Set<Kursi> kursiDipesan = new HashSet<>();

    // Constructor kosong untuk JPA
    public Reservasi() {
    }

    // Constructor untuk membuat objek
    public Reservasi(String kodeBooking, Double totalHarga, String status, Pelanggan pelanggan, JadwalTayang jadwalTayang) {
        this.kodeBooking = kodeBooking;
        this.totalHarga = totalHarga;
        this.status = status;
        this.pelanggan = pelanggan;
        this.jadwalTayang = jadwalTayang;
        this.waktuReservasi = LocalDateTime.now(); // Set waktu saat dibuat
    }
    
    // Helper method untuk menambah kursi ke reservasi
    public void addKursi(Kursi kursi) {
        this.kursiDipesan.add(kursi);
    }

    // --- Getter dan Setter ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKodeBooking() {
        return kodeBooking;
    }

    public void setKodeBooking(String kodeBooking) {
        this.kodeBooking = kodeBooking;
    }

    public Double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(Double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getWaktuReservasi() {
        return waktuReservasi;
    }

    public void setWaktuReservasi(LocalDateTime waktuReservasi) {
        this.waktuReservasi = waktuReservasi;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public JadwalTayang getJadwalTayang() {
        return jadwalTayang;
    }

    public void setJadwalTayang(JadwalTayang jadwalTayang) {
        this.jadwalTayang = jadwalTayang;
    }

    public Set<Kursi> getKursiDipesan() {
        return kursiDipesan;
    }

    public void setKursiDipesan(Set<Kursi> kursiDipesan) {
        this.kursiDipesan = kursiDipesan;
    }
}