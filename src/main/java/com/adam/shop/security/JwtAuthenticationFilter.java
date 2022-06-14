package com.adam.shop.security;

import com.adam.shop.domain.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j //generyczny logger - dostosuje się do implementacji loggera, który mamy dodany do aplikacji
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper; //dostępny z biblioteki Spring

    //konstruktor z ręcznie ustawionym polem dla zmiannej finalnej - nie można użyć adnotacji @RequireArgsConstructor
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/api/login"); //dodana aby uspójnić logowanie w Postamnie
    }


    //do próby zalogowania się przez użytkownika
    //użytkownik podał dane do logowania, program dopiero je przetworzy

    //pobiera Json-a od użytkownika, następnie pobiera z tego Json-a email i password
    //i przekazuje do mechanizmu security, który jest w stanie przekazać email użytkownika do UserDetailsService
    //następnie UserDetailsService zwraca użytkownika z bazy danych i mechnizm Springa sprawdza czy
    //hasło podane przez uzytkownika zgadza się z hasłem z bazy danych
    //na koniec securiti określa czy wszystko poszło ok i wykonuje się metoda succesfulAuthentication
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            log.error("", e);
        }
        throw new AuthenticationServiceException("Not valid parameters");
    }

    //gdy użytkownik zostanie poprawnie zalogowany to wygenerujemy mu token JWT
    //trzeba dodać do pom dependency jjwt -> JSON Web Token Support For The JVM
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // claims - decorator - w środku klasy jest mapa i klasa nadrzędna implementuje też mapę
        // DefeultClaims to builder dlatego wykonujemy na nim kolejne metody - setExpiration i setSubject

        //tworzymy mapę
        Claims claims = new DefaultClaims() // defaultClaims - można powiedzieć że to rodzaj dekoratora w klasie nadrzędnej (JwtMap) jest zaimplmentowana mapa, zatem bedą dostępne wszystkie metody z mapy
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //podajemy czas ile będzie ważny token - tutaj ustawione na 24h, Uwaga tutaj nie można korzystać z klasy LocalDateTime
                .setSubject(((UserDetails) authResult.getPrincipal()).getUsername()); //ustawiamy nazwę użytkownika - w tym przypadku email

        //wypełniamy mapę
        Object authorities = claims.put("authorities", authResult.getAuthorities().stream()// można wywołać funkcję put bo to mapa
                .map(GrantedAuthority::getAuthority) //getAuthority pobiera rolę
                .collect(Collectors.joining(",")));//wartością są wszystkie role użytkownika podane jako String, rozdzielone przecinkami

        //tworzenie tokena
        String token = Jwts.builder() //żeby wygenerować token trzeba wywołać funkcję builder z klasy Jwts
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "haslo") //HS512 jest algorytmem hashującym, jako drugi parametr podajemy klucz
                .compact(); //zwraca zbudowany obiekt, tak jak build

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("token", token));
    }
}
