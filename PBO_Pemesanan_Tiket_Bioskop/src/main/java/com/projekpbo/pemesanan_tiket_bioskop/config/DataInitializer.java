package com.projekpbo.pemesanan_tiket_bioskop.config;


import com.projekpbo.pemesanan_tiket_bioskop.models.Admin;
import com.projekpbo.pemesanan_tiket_bioskop.Repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Cek apakah sudah ada admin di database
        if (adminRepository.count() == 0) {
            // Jika belum ada, buat admin baru
            String username = "admin";
            String password = "123"; // Ganti dengan password aman!

            Admin admin = new Admin();
            admin.setUsername(username);
            // PENTING: Enkripsi password sebelum disimpan!
            admin.setPassword(passwordEncoder.encode(password));
            admin.setEmail("admin@bioskop.com");

            adminRepository.save(admin);

            System.out.println("==================================================");
            System.out.println("BERHASIL MEMBUAT ADMIN PERTAMA:");
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            System.out.println("==================================================");
        }
    }
}