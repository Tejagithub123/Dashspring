package com.p1.config;

import com.p1.config.JwtAuthFilter;
import com.p1.config.JwtUtil;
import com.p1.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        return auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()).and().build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Enable CORS with specific configuration
        http.cors().configurationSource(request -> {
            CorsConfiguration corsConfig = new CorsConfiguration();
            corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4300")); // Allow the frontend
            corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // Allow
                                                                                                             // HTTP
            // methods
            corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Allow headers like
                                                                                          // Authorization and
                                                                                          // Content-Type
            corsConfig.setAllowCredentials(true); // Allow credentials (cookies, authorization header)
            return corsConfig;
        });

        // Disable CSRF, as it's typically not necessary for APIs with JWTs
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // Permit access to authentication routes (login/register)
                .antMatchers("/admin/**").hasRole("ADMIN") // Protect admin routes
                .anyRequest().authenticated() // Require authentication for any other route
                .and()
                .addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class); // Add JWT
                                                                                                          // filter
                                                                                                          // before
                                                                                                          // authentication

        return http.build();
    }
}
