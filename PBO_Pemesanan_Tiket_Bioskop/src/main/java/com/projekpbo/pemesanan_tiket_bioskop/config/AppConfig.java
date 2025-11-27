package com.projekpbo.pemesanan_tiket_bioskop.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    // @Bean menandakan bahwa metode ini akan membuat
    // sebuah objek (Bean) yang akan dikelola oleh Spring.
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Kita menggunakan BCrypt, standar industri untuk hashing password
        return new BCryptPasswordEncoder();
    }
}