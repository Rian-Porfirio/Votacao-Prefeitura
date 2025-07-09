package br.com.rianporfirio.sistemavotacao.config;

import br.com.rianporfirio.sistemavotacao.service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final AuthenticationService authenticationService;

    public SpringSecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(req -> req
                        .requestMatchers(
                                "/relatorio",
                                "/relatorio/geral",
                                "/relatorio/unico/*",
                                "/generate/password",
                                "/update/*",
                                "/delete/*",
                                "/create",
                                "/dashboard/*").hasRole("ADMIN")
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
                .userDetailsService(authenticationService)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/assets/**");
    }

}
