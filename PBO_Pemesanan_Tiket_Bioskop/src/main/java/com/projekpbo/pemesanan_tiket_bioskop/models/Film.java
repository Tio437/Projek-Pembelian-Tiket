package com.projekpbo.pemesanan_tiket_bioskop.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity menandakan ke JPA bahwa kelas ini adalah tabel di database [cite: 95]
@Entity
@Table(name = "films") // (Opsional) Menentukan nama tabelnya
public class Film {

    // @Id menandakan ini adalah Primary Key [cite: 97, 99]
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatis increment ID
    private Long id; // Primary Key [cite: 99]

    // Atribut-atribut dari dokumen 
    private String judul; // [cite: 100]
    private String sinopsis; // [cite: 101]
    private int durasiMenit; // [cite: 102]
    private String genre;
    private String ratingUsia;
    private String posterUrl;

    // Wajib ada constructor kosong untuk JPA [cite: 103, 104]
    public Film() {
    }

    // Constructor untuk membuat objek baru [cite: 105]
    public Film(String judul, String sinopsis, int durasiMenit, String genre, String ratingUsia, String posterUrl) {
        this.judul = judul; // [cite: 107]
        this.sinopsis = sinopsis; // [cite: 108]
        this.durasiMenit = durasiMenit; // [cite: 109]
        this.genre = genre;
        this.ratingUsia = ratingUsia;
        this.posterUrl = posterUrl;
    }

    // --- Getter dan Setter ---
    // (Dibutuhkan oleh JPA dan Thymeleaf untuk mengakses data)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getDurasiMenit() {
        return durasiMenit;
    }

    public void setDurasiMenit(int durasiMenit) {
        this.durasiMenit = durasiMenit;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRatingUsia() {
        return ratingUsia;
    }

    public void setRatingUsia(String ratingUsia) {
        this.ratingUsia = ratingUsia;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}