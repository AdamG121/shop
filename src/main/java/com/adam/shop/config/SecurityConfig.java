package com.adam.shop.config;

import com.adam.shop.security.JwtAuthenticationFilter;
import com.adam.shop.security.JwtAuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true) //pozwala na używanie adnotacji @PreAuthorised w metodach - czy można uruchomić metodę na podstawie security
@EnableWebSecurity //włączenie security - zawiera adnotacje rejestrującą klasę jako bean
@RequiredArgsConstructor // wstrzykiwanie obiektów
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder; //sposób enkodowanie hasła
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf() //token zabezpieczający
                .ignoringAntMatchers("/**") //wyłączenie tokena na wszystkich end-pointach - to oznacza /**
                .and()
                .cors() // zabezpieczenie żeby aplikacje trzecie nie mogły się łączyć z naszą  aplikacją, tylko aplikacja na serwerze na którym jest backend będą mogły się łączyć
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), objectMapper))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // dwie powyższe funkcje się ze sobą łączą,
        // aplikacja nie przechowuje żadnego stanu o użytkowyniku odwrotnością jest STATEFULL
        // jeżeli mamy STATEFULL to zachowujemy informacje o ostatnio zalogowanym użytkowniku i
        // np jeśli przyjdzie niezalogowany użytkownik to on zacznie pracować w kontekście tego zalogowanego użytkownika,
        // który był wcześniej bo nie zostanie stworzona nowa sesja dla niezalogowanego użytkownika i mógłby pobrać dane zalogowanego użytkownika
        // a jak mamy STATELESS to za każdym razem jak wykonamy żądanie dla klienta, który jest zalogowany to sesja jest czyszczona
        // w praktyce prawie zawsze korzysta się ze STATELESS
        // STATEFULL można stosować np do narzędzi - open shift - który jest stosowany do zarządzania aplikacjami - przełączą się pomiędzy sesjami
    }

    // konfigurowanie dostarczyciela userów z bazy danych + w jaki sposób enkodujemy hasło
    // userDetailService pobiera z bazy danych usera na podstawie emaila - skonfigurowane w pakiecie security w klasie UserDetailsServiceImpl
    // a passwordEncoder jest skonfigurowany w AppConfig
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
