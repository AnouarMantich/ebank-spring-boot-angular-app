package com.example.ebankingbackend.security;


import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;

/**

@author tensa 

**/

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        PasswordEncoder encoder = passwordEncoder();
        return new InMemoryUserDetailsManager(
                User.withUsername("user").password(encoder.encode("password")).authorities("USER").build(),
                User.withUsername("Admin").password(encoder.encode("password")).authorities("USER","ADMIN").build()
        );
    }
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests ->authorizeRequests.requestMatchers("/auth/login/**").permitAll())
                .authorizeHttpRequests(authorizeRequests ->authorizeRequests.anyRequest().authenticated())
                .oauth2ResourceServer(oa->oa.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        String secretKey = "f4a8cfc8e7a1d4f7e3b4a7b9d2c5e1f8b6c2d3a1e9f0b4c5d7a8e2f4a5b9c6a3";
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
    }

    @Bean
    JwtDecoder jwtDecoder() {
        String secretKey = "f4a8cfc8e7a1d4f7e3b4a7b9d2c5e1f8b6c2d3a1e9f0b4c5d7a8e2f4a5b9c6a3";
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "RSA");
        return  NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsManager userDetailsManager) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsManager);
        return new ProviderManager(daoAuthenticationProvider);
    }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
