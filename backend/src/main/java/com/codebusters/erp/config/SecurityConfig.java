package com.codebusters.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/clientes/**", "/fornecedores/**", "/categorias/**", "/produtos/**", "/estoques/**","/movimentacoes-estoque/**","/compras/**","/itens-compra/**").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
}