package com.pfc.thindesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder codificadorSenha() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filtroSeguranca(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/cadastro", "/css/**", "/js/**",
                        "/imagens/**", "/images/**", "/dist/**", "/plugins/**",
                        "/error", "/acesso-negado").permitAll()
                // Gerente + Admin
                .requestMatchers("/gerente/**", "/ajustes-horarios").hasAnyRole("GERENTE", "ADMINISTRADOR")
                // Área administrativa
                .requestMatchers("/administrador/**", "/api/clientes", "/api/chamados").hasRole("ADMINISTRADOR")
                // Usuário autenticado (dashboard, perfil, chamados, clientes)
                .requestMatchers("/dashboard", "/perfil", "/chamados/**", "/clientes/**").hasAnyRole("USUARIO", "GERENTE", "ADMINISTRADOR")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?erro")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                // Spring Session usa cookie "SESSION"; JSESSIONID mantido por compatibilidade.
                .deleteCookies("SESSION", "JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(ex -> ex.accessDeniedPage("/acesso-negado"));
        return http.build();
    }
}
