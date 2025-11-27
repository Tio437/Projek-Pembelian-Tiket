package com.projekpbo.pemesanan_tiket_bioskop.models;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "kursi")
public class Kursi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // idKursi

    private String nomorBaris; // Misal: 'A', 'B'
    private int nomorKursi; // Misal: 1, 2, 3
    private String jenis; // Misal: Regular, VIP

    // --- Relasi OOP ---
    //
    // @ManyToOne: Banyak Kursi dimiliki oleh Satu Studio
    // FetchType.LAZY: Hanya load data Studio jika kita memanggil getStudio()
    // @JoinColumn: Menentukan nama kolom 'studio_id' sebagai Foreign Key
    // nullable = false: Sebuah Kursi *wajib* terhubung ke sebuah Studio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    // Constructor kosong untuk JPA
    public Kursi() {
    }

    // Constructor untuk membuat objek
    public Kursi(String nomorBaris, int nomorKursi, String jenis, Studio studio) {
        this.nomorBaris = nomorBaris;
        this.nomorKursi = nomorKursi;
        this.jenis = jenis;
        this.studio = studio;
    }

    // --- Getter dan Setter ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomorBaris() {
        return nomorBaris;
    }

    public void setNomorBaris(String nomorBaris) {
        this.nomorBaris = nomorBaris;
    }

    public int getNomorKursi() {
        return nomorKursi;
    }

    public void setNomorKursi(int nomorKursi) {
        this.nomorKursi = nomorKursi;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }
}
