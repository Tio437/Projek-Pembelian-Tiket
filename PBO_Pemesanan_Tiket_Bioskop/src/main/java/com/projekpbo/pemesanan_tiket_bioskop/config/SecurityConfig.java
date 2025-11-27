package com.projekpbo.pemesanan_tiket_bioskop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Import HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Kita tidak lagi "extends WebSecurityConfigurerAdapter"
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Kita tidak perlu lagi meng-inject UserDetailsService atau PasswordEncoder
    // Spring Boot 3 akan mendeteksinya secara otomatis sebagai Bean

    /**
     * Menggantikan metode configure(HttpSecurity http) yang lama.
     * Kita sekarang mendefinisikan SecurityFilterChain sebagai @Bean.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
            
        .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/admin/dashboard", true) // <-- TAMBAHKAN BARIS INI
                .permitAll()
            )
        
        // Konfigurasi CSRF dengan lambda style baru
            .csrf(csrf -> csrf.disable())
            
            // Konfigurasi otorisasi request dengan style baru
            .authorizeHttpRequests(authorize -> authorize
                
                // --- ATURAN UNTUK ADMIN ---
                // .antMatchers() diganti menjadi .requestMatchers()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Ini perbaikan: Hanya POST /api/film yang butuh ADMIN
                .requestMatchers(HttpMethod.POST, "/api/film").hasRole("ADMIN") 

                // --- ATURAN UNTUK PELANGGAN (USER) ---
                .requestMatchers("/reservasi/**").hasRole("USER")
                .requestMatchers("/api/reservasi/**").hasRole("USER")

                // --- ATURAN PUBLIK ---
                .requestMatchers("/").permitAll() 
                .requestMatchers("/login").permitAll()
                .requestMatchers("/register").permitAll() // <-- TAMBAHKAN INI
                .requestMatchers("/api/film").permitAll() // Halaman detail film
                // Ini perbaikan: GET /api/film boleh diakses publik
                .requestMatchers(HttpMethod.GET, "/api/film").permitAll() 
                // (Opsional) Izinkan akses ke file statis jika ada
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                
                // Semua request lain *harus* login
                .anyRequest().authenticated()
            )
            
            // Konfigurasi Form Login dengan lambda style baru
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("email") // <-- TAMBAHKAN INI
                .defaultSuccessUrl("/admin/dashboard", true)
                .permitAll()
            )
            
            // Konfigurasi Logout dengan lambda style baru
            .logout(logout -> logout
                .logoutUrl("/logout")
                .permitAll()
            );

        // Membangun konfigurasi HttpSecurity
        return http.build();
    }
}