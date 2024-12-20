package com.cinematheater.config;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(CORSLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Log CORS response headers
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        
        logger.info("CORS Response Headers: Access-Control-Allow-Origin: {}", response.getHeader("Access-Control-Allow-Origin"));
        logger.info("CORS Response Headers: Access-Control-Allow-Methods: {}", response.getHeader("Access-Control-Allow-Methods"));
        logger.info("CORS Response Headers: Access-Control-Allow-Headers: {}", response.getHeader("Access-Control-Allow-Headers"));

        filterChain.doFilter(request, response); // Continue with the filter chain
    }
}
