package com.projekpbo.pemesanan_tiket_bioskop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
            // 1. Matikan CSRF (untuk mempermudah saat development)
            .csrf(csrf -> csrf.disable())
            
            // 2. Atur Izin URL
            .authorizeHttpRequests(authorize -> authorize
                // Admin
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/film").hasRole("ADMIN")

                // User
                .requestMatchers("/reservasi/**", "/riwayat-tiket").hasRole("USER")
                .requestMatchers("/api/reservasi/**").hasRole("USER")

                // Publik
                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/film").permitAll()
                
                // Sisanya harus login
                .anyRequest().authenticated()
            )
            
            // 3. Konfigurasi Login Form
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("email") // Login pakai email
                
                // --- BAGIAN INI YANG DIGANTI (TANPA FILE BARU) ---
                // Kita tulis logika redirect langsung di sini (Inline Lambda)
                .successHandler((request, response, authentication) -> {
                    
                    // Cek apakah user yang login punya role ADMIN
                    boolean isAdmin = authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                    
                    if (isAdmin) {
                        // Jika Admin -> Masuk Dashboard
                        response.sendRedirect("/admin/dashboard");
                    } else {
                        // Jika User Biasa -> Masuk Home
                        response.sendRedirect("/");
                    }
                })
                // -------------------------------------------------
                
                .permitAll()
            )
            
            // 4. Konfigurasi Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .permitAll()
            );

        return http.build();
    }
}