package com.example.LibraryManager.filters;

import com.example.LibraryManager.services.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain filterchain
    ) throws ServletException, IOException {
        String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterchain.doFilter(req, res);
            return;
        }

        String token = header.substring(7).trim();

        Claims claims = jwtService.parseAccessToken(token);
        String userId = claims.get("id", String.class);
        String role = claims.get("role", String.class);
        String position = claims.get("position", String.class);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userId,
                null,
                jwtService.getAuthorities(role, position)
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterchain.doFilter(req, res);
    }
}
