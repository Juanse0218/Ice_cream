package com.example.IceCream_SpringBoot.config;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.IceCream_SpringBoot.repository.UserRepository;
import com.example.IceCream_SpringBoot.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // DaoAuthenticationProvider es el encargado de autenticar usuarios.
    // Usa el UserDetailsService para cargar los usuarios desde la base de datos.
    // Compara la contraseña ingresada con la encriptada usando BCrypt.
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    // Crea el AuthenticationManager, que maneja la autenticación.
    // Usa el proveedor de autenticación (DaoAuthenticationProvider) para verificar
    // usuarios.
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider(userDetailsService,
                        passwordEncoder))
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // público
                        .requestMatchers("/login", "/register", "/css/**", "/img/**").permitAll()
                        // rutas de ventas: solo USER
                        .requestMatchers("/venderHelados", "/venderHelados/**", "/buscarCliente").hasRole("USER")
                        //Todo lo demas: ADMIN
                        .anyRequest().hasRole("ADMIN"))
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((req, res, auth) -> {
                            auth.getAuthorities().forEach(granted -> {
                                try {
                                    if (granted.getAuthority().equals("ROLE_ADMIN")) {
                                        res.sendRedirect("/home");
                                    } else {
                                        res.sendRedirect("/venderHelados");
                                    }
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        })
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }
}
