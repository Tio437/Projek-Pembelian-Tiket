package com.projekpbo.pemesanan_tiket_bioskop.models;


import java.time.LocalDateTime; // Tipe data yang tepat untuk waktu
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "jadwal_tayang")
public class JadwalTayang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // idJadwal

    private LocalDateTime waktuMulai; //
    private LocalDateTime waktuSelesai; //
    private Double hargaTiket; //

    // --- Relasi OOP ---

    // Relasi: JadwalTayang memiliki satu Film [cite: 22]
    // @ManyToOne: Banyak jadwal bisa memiliki satu film yang sama
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    // Relasi: JadwalTayang berada di satu Studio [cite: 23]
    // @ManyToOne: Banyak jadwal bisa berada di satu studio yang sama
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    // Constructor kosong untuk JPA
    public JadwalTayang() {
    }

    // Constructor untuk membuat objek
    public JadwalTayang(LocalDateTime waktuMulai, LocalDateTime waktuSelesai, Double hargaTiket, Film film, Studio studio) {
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
        this.hargaTiket = hargaTiket;
        this.film = film;
        this.studio = studio;
    }

    // --- Getter dan Setter ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(LocalDateTime waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public LocalDateTime getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(LocalDateTime waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public Double getHargaTiket() {
        return hargaTiket;
    }

    public void setHargaTiket(Double hargaTiket) {
        this.hargaTiket = hargaTiket;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }
}
