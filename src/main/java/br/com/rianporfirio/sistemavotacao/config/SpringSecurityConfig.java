package br.com.rianporfirio.sistemavotacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(autorize -> autorize
                        .anyRequest()
                        .permitAll())
                .build();
    }
}
