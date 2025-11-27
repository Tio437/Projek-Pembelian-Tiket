package com.projekpbo.pemesanan_tiket_bioskop.Service;

import com.projekpbo.pemesanan_tiket_bioskop.models.Admin;
import com.projekpbo.pemesanan_tiket_bioskop.models.Pelanggan;
import com.projekpbo.pemesanan_tiket_bioskop.Repositories.AdminRepository;
import com.projekpbo.pemesanan_tiket_bioskop.Repositories.PelangganRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PelangganRepository pelangganRepository;

    /**
     * Metode ini dipanggil oleh Spring Security saat login.
     * PENTING: Meskipun nama metodenya 'loadUserByUsername',
     * kita telah mengonfigurasinya untuk menerima 'email' dari form login.
     *
     * @param email Email yang dimasukkan pengguna di form login
     * @return Objek UserDetails yang berisi data pengguna
     * @throws UsernameNotFoundException jika email tidak ditemukan
     */

    // Ini adalah metode inti yang dipanggil Spring Security saat
    // seseorang mencoba login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        // 1. Coba cari di tabel Admin
        Optional<Admin> adminOpt = adminRepository.findByUsername(email);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_ADMIN")
            );
            
            // Kembalikan objek UserDetails yang dimengerti Spring Security
            return new User(admin.getUsername(), admin.getPassword(), authorities);
        }

        // 2. Jika tidak ada, coba cari di tabel Pelanggan
        Optional<Pelanggan> pelangganOpt = pelangganRepository.findByUsername(email);
        if (pelangganOpt.isPresent()) {
            Pelanggan pelanggan = pelangganOpt.get();
            List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
            );

            return new User(pelanggan.getUsername(), pelanggan.getPassword(), authorities);
        }

        // 3. Ubah pesan error
        throw new UsernameNotFoundException("User tidak ditemukan dengan email: " + email);
    }
}