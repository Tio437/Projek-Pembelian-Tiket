package com.projekpbo.pemesanan_tiket_bioskop.Service;

import com.projekpbo.pemesanan_tiket_bioskop.models.Pelanggan;
import com.projekpbo.pemesanan_tiket_bioskop.Repositories.PelangganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PelangganService {

    @Autowired
    private PelangganRepository pelangganRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean existsfindByEmail(String email) {
        return pelangganRepository.existsByEmail(email);
    }

    public Boolean existsByUsername(String username) {
        return pelangganRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return pelangganRepository.existsByEmail(email);
    }

    public Optional<Pelanggan> findByUsername(String username) {
        return pelangganRepository.findByUsername(username);
    }

    public void registerPelanggan(Pelanggan pelanggan) {
        // Enkripsi password sebelum disimpan!
        String encodedPassword = passwordEncoder.encode(pelanggan.getPassword());
        pelanggan.setPassword(encodedPassword);
        
        // Set saldo awal atau default lainnya jika ada
        pelanggan.setSaldo(0.0); 
        
        pelangganRepository.save(pelanggan);
    }
}
