package com.customercvarchive.security.jwt;


import com.customercvarchive.exception.CustomJwtException;
import com.customercvarchive.security.SecurityUtils;
import com.customercvarchive.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;


    public String generateToken(UserPrincipal userPrincipal) {  // Token üreten method
        String authorities = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder().setSubject(userPrincipal.getUsername())
                .claim("roles", authorities)   // token içinde kullanıcı id ve kullanıcı rollerini depoluyoruz
                .claim("userId", userPrincipal.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS)) // Token expire zamanı belirlenen zaman artı şimdiki zaman
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }


    public Authentication getAuthentication(HttpServletRequest request) {
        Claims claims = extractClaims(request);
        if (claims == null) {
            return null;
        }

        String username = claims.getSubject();
        Integer userId = claims.get("userId", Integer.class);

        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(Collectors.toSet());

        UserDetails userDetails = UserPrincipal.builder()
                .username(username)
                .authorities(authorities)
                .id(userId)
                .build();

        if (username == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);


    }

    private Claims extractClaims(HttpServletRequest request) {
        String token = SecurityUtils.extractAuthTokenFromRequest(request);
        if (token == null) {
            return null;
        }
        return Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(HttpServletRequest request) {
        Claims claims = extractClaims(request);
        if (claims == null) {
            throw new CustomJwtException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (claims.getExpiration().before(new Date())) {
            throw new CustomJwtException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return true;

    }


}

