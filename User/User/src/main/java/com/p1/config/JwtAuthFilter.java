package com.p1.config;

import com.p1.config.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Extract the token

            try {
                // Assuming your JWT utility method is correctly extracting claims
                Claims claims = jwtUtil.extractAllClaims(token);

                // Extract the role directly from claims, assuming it's in the "role" claim
                String role = claims.get("role", String.class); // Get the role

                if (role != null) {
                    // Print the role for debugging
                    System.out.println("Extracted Role: " + role);

                    // Strip ROLE_ if it's present
                    if (role.startsWith("ROLE_")) {
                        role = role.substring(5); // Remove 'ROLE_' prefix if present
                    }

                    // Add the role to the granted authorities
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (ExpiredJwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                return;
            } catch (JwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
