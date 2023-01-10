package com.customercvarchive.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class JwtAuthorizationFilter extends OncePerRequestFilter {
        //OncePerRequestFilter  her istek için bu filtreyi bir kere çalıştırır
    @Autowired
    private  JwtProvider jwtProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/api/internal");  // bu adres bu filtre ile filtrelenmemeli
    }

    @Override  // filtreyi çalıştırdığımız method
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication=jwtProvider.getAuthentication(request);

        if(authentication !=null && jwtProvider.validateToken(request)){ // Token varsa ver validate ise if bloğuna girer
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //Eğer token yok ya da valid değil ise  bu filtreyi( Jwt token filtresini atlar ) sıradaki filtreye geçer
        //fitler chain --> filtre zinicirini temsil eder,zinciri sırası ile çalıştırır
        filterChain.doFilter(request,response); // yönetimi spring security e bırakmış oluyoruz
    }
}



