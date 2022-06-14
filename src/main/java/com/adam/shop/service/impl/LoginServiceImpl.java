package com.adam.shop.service.impl;

import com.adam.shop.domain.dto.TokenDto;
import com.adam.shop.service.LoginService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenDto login(String email, String passowrd) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(email, passowrd);

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //tworzymy mapę
        Claims claims = new DefaultClaims() // defaultClaims - można powiedzieć że to rodzaj dekoratora w klasie nadrzędnej (JwtMap) jest zaimplmentowana mapa, zatem bedą dostępne wszystkie metody z mapy
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //podajemy czas ile będzie ważny token - tutaj ustawione na 24h, Uwaga tutaj nie można korzystać z klasy LocalDateTime
                .setSubject(((UserDetails) authentication.getPrincipal()).getUsername()); //ustawiamy nazwę użytkownika - w tym przypadku email

        //wypełniamy mapę
        claims.put("authorities", authentication.getAuthorities().stream()// można wywołać funkcję put bo to mapa
                .map(GrantedAuthority::getAuthority) //getAuthority pobiera rolę
                .collect(Collectors.joining(",")));//wartością są wszystkie role użytkownika podane jako String, rozdzielone przecinkami

        //tworzenie tokena
        String token = Jwts.builder() //żeby wygenerować token trzeba wywołać funkcję builder z klasy Jwts
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "haslo") //HS512 jest algorytmem hashującym, jako drugi parametr podajemy klucz
                .compact(); //zwraca zbudowany obiekt, tak jak build

        return new TokenDto(token);
    }
}
