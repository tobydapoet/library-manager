package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.Session;
import com.example.LibraryManager.enums.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {
    @Value("${JWT_SECRET}")
    private String SECRET;

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private final long accessTokenValidity = 1000 * 60 * 15;
    //    private final long accessTokenValidity = 30L * 24 * 3600 * 1000;
    private final long refreshTokenValidity = 30L * 24 * 3600 * 1000;

    public String generateAccessToken(Session session) {
        Roles role = session.getUser().getRole();
        JwtBuilder builder = Jwts.builder()
                .setSubject(session.getId())
                .claim("id", session.getUser().getId())
                .claim("role", "ROLE_" + role.name().toUpperCase());

        if (Roles.staff.equals(role) && session.getUser().getStaff() != null) {
            builder.claim("position", "POSITION_"
                    + session.getUser()
                    .getStaff()
                    .getPosition()
                    .name().toUpperCase());
        }


        return builder
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(Session session) {
        return Jwts.builder()
                .setSubject(session.getId())
                .setIssuedAt((new Date()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
                .signWith(key)
                .compact();
    }

    public Claims parseAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Collection<GrantedAuthority> getAuthorities(String role, String position) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (role != null && !role.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        if (position != null && !position.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority(position));
        }

        return authorities;
    }
}
