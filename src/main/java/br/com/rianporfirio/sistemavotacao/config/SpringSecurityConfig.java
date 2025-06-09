package br.com.rianporfirio.sistemavotacao.config;

import br.com.rianporfirio.sistemavotacao.service.FuncionarioDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final FuncionarioDetailsService funcionarioDetailsService;

    public SpringSecurityConfig(FuncionarioDetailsService funcionarioDetailsService) {
        this.funcionarioDetailsService = funcionarioDetailsService;
    }

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/generate-password").permitAll()
                        .requestMatchers(
                                "/relatorio",
                                "/relatorio/geral",
                                "/relatorio/unico/*",
                                "update/*",
                                "delete/*",
                                "create").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/")
                )
                .logout(logout -> logout
                        .permitAll()
                        .logoutSuccessUrl("/login")
                )
                .userDetailsService(funcionarioDetailsService)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/assets/**");
    }

}
