package com.projekpbo.pemesanan_tiket_bioskop.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

// @Entity menandakan ini adalah tabel database
@Entity
@Table(name = "pelanggan") // Tabel ini akan bernama 'pelanggan'
public class Pelanggan extends User {

    // Atribut tambahan khusus untuk Pelanggan
    private String nama; //

    // Saldo opsional, bisa kita set default nilainya
    private Double saldo = 0.0; //

    private Integer umur;

    private String email;

    // Constructor kosong untuk JPA
    public Pelanggan() {
        super(); // Memanggil constructor User()
    }

    // Constructor untuk membuat Pelanggan baru
    public Pelanggan(String username, String password, String email, String nama, Double saldo) {
        // Mengisi atribut yang diwarisi dari kelas User
        super(username, password, email); 
        this.nama = nama;
        if (saldo != null) {
            this.saldo = saldo;
        }
        this.umur = umur;
    }

    // --- Getter dan Setter ---

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Integer getUmur() {
        return umur;
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}