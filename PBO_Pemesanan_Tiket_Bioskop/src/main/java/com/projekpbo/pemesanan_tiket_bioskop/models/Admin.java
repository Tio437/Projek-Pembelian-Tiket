package com.projekpbo.pemesanan_tiket_bioskop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

// @Entity menandakan ini adalah tabel database
@Entity
@Table(name = "admins") // Tabel ini akan bernama 'admins'
public class Admin extends User {

    // Admin tidak memiliki atribut tambahan *khusus* di database
    // menurut desain Anda[cite: 7]. 
    // Metode seperti tambahFilm() atau kelolaStudio() [cite: 8] 
    // adalah logika bisnis yang akan ada di 'Service' atau 'Controller',
    // bukan atribut di model data.

    // Constructor kosong untuk JPA
    public Admin() {
        super(); // Memanggil constructor User()
    }

    // Constructor untuk membuat Admin baru
    public Admin(String username, String password, String email) {
        // Mengisi atribut yang diwarisi dari kelas User
        super(username, password, email); 
    }
}