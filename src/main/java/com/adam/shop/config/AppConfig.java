package com.adam.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //rejestruje klasę jako bean w Springu
public class AppConfig {
    @Bean //rejestruje to co zwróciła metoda jako bean w springu - tak jak RestController/Service/Component tylko że dla metody
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //skrypt do hashowania hasła użytkownika w bazie danych / jeśli zahashujemy to nie bedzie można go odhashować
    }
}
