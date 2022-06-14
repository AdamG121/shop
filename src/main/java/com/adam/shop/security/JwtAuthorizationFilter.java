package com.adam.shop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//sprawdzanie kto jest użytkownikiem w danym request-cie
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // tą metodą sprawdzamy czy użytkownik jest zalogowany
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION); //pobranie tokena requestu (Token zawsze podajemy w headerze requestu)

        if (token == null || !token.startsWith("Bearer ")) {
            chain.doFilter(request, response); //wzorzec chain of responsibility - żeby wywołać kolejną zmienną w łańcuchu trzeba skorzystać z metody doFilter
            return; //do przerwania funkcji
        }

        //rozparsowanie tokena
        Claims claims = Jwts.parser() //tak jak builder
                .setSigningKey("haslo") //podaje sekretny klucz
                .parseClaimsJws(token.replace("Bearer ", ""))//podajemy token tylko trzeba usunąć przedrostek
                .getBody();

        //najpierw pobieramy subject który reprezentuje pole username Usera z pakietu security w Springu - w naszym przypadku to reprezentuje email
        String email = claims.getSubject();

        if (email == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //bo użytkownik podał nam nieprawidłowe dane
            return;//przerwanie funkcji
        }

        String authorities = claims.get("authorities", String.class); //analogicznie jak w metodzie successfulAuthentication

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (authorities != null && !authorities.isEmpty()) {
            grantedAuthorities = Arrays.stream(authorities.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);

        //wstawienie Usera do kontekstu Security
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        chain.doFilter(request, response);
    }
}
