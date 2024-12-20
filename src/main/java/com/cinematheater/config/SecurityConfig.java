package com.cinematheater.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import com.cinematheater.jwt.JwtRequestFilter;

import java.util.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF
            .authorizeHttpRequests(auth -> auth
                .antMatchers("/user/**").permitAll() // Exclude /user/** from JWT authentication
                .antMatchers("/movie/**").permitAll() // Exclude other public endpoints from JWT authentication
                .anyRequest().authenticated() // All other endpoints require authentication
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before UsernamePasswordAuthenticationFilter

        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        // Ensure no trailing slash
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // Ensure this is set to allow cookies and credentials

        // Log the CORS configuration to confirm the headers
        logger.info("CORS Configuration: Allowed Origins: {}", configuration.getAllowedOrigins());
        logger.info("CORS Configuration: Allowed Methods: {}", configuration.getAllowedMethods());
        logger.info("CORS Configuration: Allowed Headers: {}", configuration.getAllowedHeaders());
        
        // Log a message before applying the CORS configuration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        logger.info("CORS configuration applied to all paths");
        
        return source;
    }

    // Register the CORSLoggingFilter to log the CORS headers
    @Bean
    public FilterRegistrationBean<CORSLoggingFilter> corsLoggingFilter() {
        FilterRegistrationBean<CORSLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CORSLoggingFilter());
        registrationBean.addUrlPatterns("/**"); // Apply the filter to all paths
        registrationBean.setOrder(1); // Set the filter's order
        return registrationBean;
    }
}
