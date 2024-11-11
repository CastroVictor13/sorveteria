package com.estoque.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/produtos/**").authenticated() // Requer autenticação para essas rotas
                        .anyRequest().permitAll() // Permite todas as outras requisições
                )
                .formLogin(form -> form
                        .loginPage("/login") // Página de login personalizada
                        .permitAll() // Permite acesso à página de login para todos
                )
                .logout(LogoutConfigurer::permitAll // Permite logout para todos
                )
                .csrf(csrf -> csrf // Configuração CSRF
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // Usar cookies para tokens CSRF
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usar BCrypt para codificação de senhas
    }

    @Bean
    public void authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("12345")).roles("ADMIN"); // Usuário admin em memória
    }
}
