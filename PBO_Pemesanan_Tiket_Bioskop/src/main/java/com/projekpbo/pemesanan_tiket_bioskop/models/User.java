package com.projekpbo.pemesanan_tiket_bioskop.models;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

// @MappedSuperclass menandakan bahwa kelas ini adalah induk
// dan atributnya akan diwariskan ke kelas-kelas Entity (tabel) anaknya.
@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(unique = true) memastikan tidak ada username yang sama
    @Column(unique = true, nullable = false)
    private String username; // [cite: 5]

    @Column(nullable = false)
    private String password; // [cite: 5]

    @Column(unique = true, nullable = false)
    private String email; // [cite: 5]
    
    // (Catatan: Metode login() dan logout() [cite: 6] adalah logika bisnis
    // yang akan ditangani oleh Spring Security[cite: 36], 
    // bukan metode di dalam kelas model data ini.)

    // Constructor kosong untuk JPA
    public User() {
    }

    // Constructor untuk membuat objek
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // --- Getter dan Setter ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
