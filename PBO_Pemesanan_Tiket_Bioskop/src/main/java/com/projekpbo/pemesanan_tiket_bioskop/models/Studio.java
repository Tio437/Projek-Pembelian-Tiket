package com.projekpbo.pemesanan_tiket_bioskop.models;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "studios")
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // idStudio

    private int nomorStudio; //
    private int kapasitas; //

    // --- Relasi OOP ---
    // 
    // @OneToMany: Satu Studio memiliki banyak Kursi
    // mappedBy = "studio": Menandakan bahwa di kelas Kursi ada field "studio"
    // cascade = CascadeType.ALL: Jika Studio disimpan/dihapus, Kursi-nya ikut
    // orphanRemoval = true: Jika Kursi dihapus dari list ini, datanya di DB juga terhapus
    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Kursi> daftarKursi = new HashSet<>();

    // Constructor kosong untuk JPA
    public Studio() {
    }

    // Constructor untuk membuat objek
    public Studio(int nomorStudio, int kapasitas) {
        this.nomorStudio = nomorStudio;
        this.kapasitas = kapasitas;
    }

    // --- Helper Method ---
    // Ini adalah implementasi dari "Metode: tambahKursi()" [cite: 17]
    // untuk menjaga konsistensi kedua sisi relasi
    public void addKursi(Kursi kursi) {
        daftarKursi.add(kursi);
        kursi.setStudio(this);
    }

    public void removeKursi(Kursi kursi) {
        daftarKursi.remove(kursi);
        kursi.setStudio(null);
    }

    // --- Getter dan Setter ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNomorStudio() {
        return nomorStudio;
    }

    public void setNomorStudio(int nomorStudio) {
        this.nomorStudio = nomorStudio;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public Set<Kursi> getDaftarKursi() {
        return daftarKursi;
    }

    public void setDaftarKursi(Set<Kursi> daftarKursi) {
        this.daftarKursi = daftarKursi;
    }
}