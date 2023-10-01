package com.example.hacka.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.hacka.User.Permission.*;
import static com.example.hacka.User.Role.ADMIN;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()
//                        .requestMatchers("api/v1/admin/**").hasAnyRole(ADMIN.name())
//
//                        .requestMatchers(GET,"api/v1/admin/**").hasAnyAuthority(ADMIN_READ.name())
//                        .requestMatchers(POST,"api/v1/admin/**").hasAnyAuthority(ADMIN_CREATE.name())
//                        .requestMatchers(PUT,"api/v1/admin/**").hasAnyAuthority(ADMIN_UPDATE.name())
//                        .requestMatchers(DELETE,"api/v1/admin/**").hasAnyAuthority(ADMIN_DELETE.name())
                        .anyRequest()
                        .authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
